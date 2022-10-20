package com.sarangchurch.follower.prayer.dao;

import com.sarangchurch.follower.common.types.dto.OffsetBasedPageRequest;
import com.sarangchurch.follower.member.domain.model.Gender;
import com.sarangchurch.follower.prayer.dao.dto.CardDetails;
import com.sarangchurch.follower.prayer.dao.dto.ChildCommentDetails;
import com.sarangchurch.follower.prayer.dao.dto.CommentDetails;
import com.sarangchurch.follower.prayer.dao.dto.MemberDetails;
import com.sarangchurch.follower.prayer.dao.dto.MyCardDetails;
import com.sarangchurch.follower.prayer.dao.dto.MyPrayerDetails;
import com.sarangchurch.follower.prayer.dao.dto.PersonalCardDetails;
import com.sarangchurch.follower.prayer.dao.dto.PersonalPrayerDetails;
import com.sarangchurch.follower.prayer.dao.dto.PrayerDetails;
import com.sarangchurch.follower.prayer.domain.model.Week;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Repository
public class CardDao {

    private final NamedParameterJdbcTemplate template;

    public CardDao(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<CardDetails> findCardsByTeamAndWeek(Long teamId, Week week) {
        String sql = "" +
                "select c.id as card_id, c.updated_at as card_updated_at, " +
                "   p.id as prayer_id, p.content as prayer_content, p.responded as prayer_responded, " +
                "   m.id as member_id, m.name as member_name, m.gender as member_gender, m.birth_date as member_birth_date  " +
                "from team_member tm " +
                "join member m on(m.id = tm.member_id and tm.team_id = :teamId) " +
                "join card c on(c.member_id = m.id and c.week = FORMATDATETIME(:date, 'yyyy-MM-dd')) " +
                "join card_prayer cp on(cp.card_id = c.id) " +
                "join prayer p on(p.id = cp.prayer_id) " +
                "order by card_updated_at desc";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("teamId", teamId)
                .addValue("date", week.toString());

        return template
                .query(sql, param, prayerRowMapper())
                .stream()
                .collect(groupingBy(PrayerDetails::getCardId))
                .entrySet()
                .stream()
                .map(it -> {
                    List<PrayerDetails> prayers = it.getValue();
                    return new CardDetails(
                            it.getKey(),
                            prayers.get(0).getCardUpdatedAt(),
                            prayers,
                            new MemberDetails(
                                    prayers.get(0).getMemberId(),
                                    prayers.get(0).getMemberName(),
                                    prayers.get(0).getGender(),
                                    prayers.get(0).getBirthDate()
                            ),
                            Collections.emptyList()
                    );
                })
                .collect(toList());
    }

    public List<PersonalCardDetails> findCardsByMemberAndYear(Long memberId, Integer year, OffsetBasedPageRequest pageRequest) {
        String cardSql = "" +
                "select c.id as card_id, c.week as card_week " +
                "from card c " +
                "where c.member_id = :memberId and extract(year from c.week) = :year " +
                "order by c.week desc " +
                "limit :limit " +
                "offset :offset";

        SqlParameterSource cardParam = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("year", year)
                .addValue("limit", pageRequest.getPageSize() + 1)
                .addValue("offset", pageRequest.getOffset());

        List<PersonalCardDetails> cards = template.query(cardSql, cardParam, personalCardRowMapper());

        String prayerSql = "" +
                "select cp.card_id as card_id, cp.seq as prayer_seq, " +
                "   p.id as prayer_id, p.content as prayer_content, p.responded as prayer_responded " +
                "from card_prayer cp " +
                "join prayer p on(p.id = cp.prayer_id and cp.card_id in (:ids))";

        MapSqlParameterSource prayerParam = new MapSqlParameterSource()
                .addValue("ids", cards.stream().map(PersonalCardDetails::getCardId).collect(toList()));

        Map<Long, List<PersonalPrayerDetails>> cardIdToPrayers = template.query(prayerSql, prayerParam, personalPrayerDetailsRowMapper())
                .stream()
                .collect(groupingBy(PersonalPrayerDetails::getCardId));

        cards.forEach(it -> it.setPrayers(cardIdToPrayers.get(it.getCardId())));
        return cards;
    }

    public Optional<CardDetails> findCardById(Long cardId) {
        String prayerSql = "" +
                "select c.id as card_id, c.updated_at as card_updated_at, c.week as card_week, " +
                "   m.id as member_id, m.name as member_name, m.gender as member_gender, m.birth_date as member_birth_date, " +
                "   p.id as prayer_id, p.content as prayer_content, p.responded as prayer_responded " +
                "from card c " +
                "join member m on(m.id = c.member_id) " +
                "join card_prayer cp on(cp.card_id = c.id and cp.card_id = :cardId) " +
                "join prayer p on(p.id = cp.prayer_id)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("cardId", cardId);

        List<PrayerDetails> prayers = template.query(prayerSql, param, prayerRowMapper());
        if (prayers.isEmpty()) {
            return Optional.empty();
        }

        String commentsSql = "select c.id as comments_id, c.created_at as comments_created_at, c.card_id as comments_card_id, c.parent_id as comments_parent_id, c.content as comments_content, " +
                "m.id as member_id, m.name as member_name, m.gender as member_gender, m.birth_date as member_birth_date " +
                "from comments c " +
                "join member m on c.card_id = :cardId and m.id = c.member_id " +
                "order by c.parent_id, c.created_at";


        List<CommentDetails> comments = template.query(commentsSql, param, childCommentRowMapper())
                .stream()
                .collect(groupingBy(ChildCommentDetails::getParentId))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(it -> new CommentDetails(
                        it.getValue().get(0).getCommentId(),
                        it.getValue().get(0).getCreatedAt(),
                        it.getValue().get(0).getCardId(),
                        it.getValue().get(0).getParentId(),
                        it.getValue().get(0).getContent(),
                        it.getValue().get(0).getMember(),
                        it.getValue().subList(1, it.getValue().size())
                ))
                .collect(toList());

        return Optional.of(new CardDetails(
                prayers.get(0).getCardId(),
                prayers.get(0).getCardUpdatedAt(),
                prayers,
                new MemberDetails(
                        prayers.get(0).getMemberId(),
                        prayers.get(0).getMemberName(),
                        prayers.get(0).getGender(),
                        prayers.get(0).getBirthDate()
                ),
                comments
        ));
    }

    public Optional<MyCardDetails> findCardByMemberAndWeek(Long memberId, Week week) {
        String sql = "" +
                "select c.id as card_id, c.updated_at as card_updated_at, " +
                "   p.id as prayer_id, p.content as prayer_content, p.responded as prayer_responded " +
                "from card c " +
                "join card_prayer cp on(cp.card_id = c.id and c.member_id = :memberId and c.week = FORMATDATETIME(:date, 'yyyy-MM-dd')) " +
                "join prayer p on(p.id = cp.prayer_id)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("date", week.toString());

        List<MyPrayerDetails> prayers = template.query(sql, param, myPrayerRowMapper());
        if (prayers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new MyCardDetails(prayers.get(0).getCardId(), prayers.get(0).getCardUpdateTime(), prayers));
    }

    public Optional<MyCardDetails> findLatestPastCardByMember(Long memberId) {
        String sql = "" +
                "select c.id as card_id, c.updated_at as card_updated_at, c.week as card_week, " +
                "   p.id as prayer_id, p.content as prayer_content, p.responded as prayer_responded " +
                "from " +
                "   ( " +
                "       select * " +
                "       from card cSub " +
                "       where cSub.member_id = :memberId " +
                "       order by cSub.week desc " +
                "       limit 1 offset 1 " +
                "   ) as c " +
                "join card_prayer cp on(cp.card_id = c.id) " +
                "join prayer p on(p.id = cp.prayer_id)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        List<MyPrayerDetails> prayers = template.query(sql, param, myPrayerRowMapper());
        if (prayers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new MyCardDetails(prayers.get(0).getCardId(), prayers.get(0).getCardUpdateTime(), prayers));
    }

    private RowMapper<PersonalCardDetails> personalCardRowMapper() {
        return (rs, rowNum) -> new PersonalCardDetails(
                rs.getLong("card_id"),
                rs.getDate("card_week").toLocalDate()
        );
    }

    private RowMapper<PersonalPrayerDetails> personalPrayerDetailsRowMapper() {
        return (rs, rowNum) -> new PersonalPrayerDetails(
                rs.getLong("card_id"),
                rs.getLong("prayer_seq"),
                rs.getLong("prayer_id"),
                rs.getString("prayer_content"),
                rs.getBoolean("prayer_responded")
        );
    }

    private RowMapper<PrayerDetails> prayerRowMapper() {
        return (rs, rowNum) -> new PrayerDetails(
                rs.getLong("card_id"),
                rs.getTimestamp("card_updated_at").toLocalDateTime(),
                rs.getLong("prayer_id"),
                rs.getString("prayer_content"),
                rs.getBoolean("prayer_responded"),
                rs.getLong("member_id"),
                rs.getString("member_name"),
                Gender.valueOf(rs.getString("member_gender")),
                rs.getDate("member_birth_date").toLocalDate()
        );
    }

    private RowMapper<ChildCommentDetails> childCommentRowMapper() {
        return (rs, rowNum) -> new ChildCommentDetails(
                rs.getLong("comments_id"),
                rs.getTimestamp("comments_created_at").toLocalDateTime(),
                rs.getLong("comments_card_id"),
                rs.getLong("comments_parent_id"),
                rs.getString("comments_content"),
                new MemberDetails(
                        rs.getLong("member_id"),
                        rs.getString("member_name"),
                        Gender.valueOf(rs.getString("member_gender")),
                        rs.getDate("member_birth_date").toLocalDate()
                )
        );
    }

    private RowMapper<MyPrayerDetails> myPrayerRowMapper() {
        return (rs, rowNum) -> new MyPrayerDetails(
                rs.getLong("card_id"),
                rs.getTimestamp("card_updated_at").toLocalDateTime(),
                rs.getLong("prayer_id"),
                rs.getString("prayer_content"),
                rs.getBoolean("prayer_responded")
        );
    }
}
