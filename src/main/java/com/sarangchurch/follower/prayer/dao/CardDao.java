package com.sarangchurch.follower.prayer.dao;

import com.sarangchurch.follower.member.domain.Gender;
import com.sarangchurch.follower.prayer.dao.dto.CardList;
import com.sarangchurch.follower.prayer.dao.dto.MyCardDetails;
import com.sarangchurch.follower.prayer.dao.dto.MyCardDetails.MyPrayerList;
import com.sarangchurch.follower.prayer.domain.Week;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CardDao {

    private final NamedParameterJdbcTemplate template;

    public CardDao(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<CardList> findCardsByTeamAndWeek(Long teamId, Week week) {
        String sql = "select c.id as card_id, c.update_date as card_update_date, " +
                "p.id as prayer_id, p.content as prayer_content, p.responded as prayer_responded, " +
                "m.id as member_id, m.name as member_name, m.gender as member_gender, m.birth_date as member_birth_date  " +
                "from team_member tm " +
                "join member m on(m.id = tm.member_id and tm.team_id = :teamId) " +
                "join card c on(c.member_id = m.id and c.week = FORMATDATETIME(:date, 'yyyy-MM-dd')) " +
                "join card_prayer cp on(cp.card_id = c.id) " +
                "join prayer p on(p.id = cp.prayer_id) " +
                "order by card_update_date desc;";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("teamId", teamId)
                .addValue("date", week.toString());

        return template
                .query(sql, param, prayerRowMapper())
                .stream()
                .collect(Collectors.groupingBy(CardList.PrayerList::getCardId))
                .entrySet()
                .stream()
                .map(it -> {
                    List<CardList.PrayerList> prayers = it.getValue();
                    CardList.MemberDetails memberDetails = new CardList.MemberDetails(
                            prayers.get(0).getMemberId(),
                            prayers.get(0).getMemberName(),
                            prayers.get(0).getGender(),
                            prayers.get(0).getBirthDate()
                    );
                    return new CardList(it.getKey(), prayers.get(0).getCardUpdateTime(), prayers, memberDetails);
                })
                .collect(Collectors.toList());
    }

    private RowMapper<CardList.PrayerList> prayerRowMapper() {
        return (rs, rowNum) -> new CardList.PrayerList(
                rs.getLong("card_id"),
                rs.getTimestamp("card_update_date").toLocalDateTime(),
                rs.getLong("prayer_id"),
                rs.getString("prayer_content"),
                rs.getBoolean("prayer_responded"),
                rs.getLong("member_id"),
                rs.getString("member_name"),
                Gender.valueOf(rs.getString("member_gender")),
                rs.getDate("member_birth_date").toLocalDate()
        );
    }

    public Optional<MyCardDetails> findCardByMemberAndWeek(Long memberId, Week week) {
        String sql = "select c.id as card_id, c.update_date as card_update_date, " +
                "p.id as prayer_id, p.content as prayer_content, p.responded as prayer_responded " +
                "from card c " +
                "join card_prayer cp on(cp.card_id = c.id and c.member_id = :memberId and c.week = FORMATDATETIME(:date, 'yyyy-MM-dd')) " +
                "join prayer p on(p.id = cp.prayer_id);";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("date", week.toString());

        List<MyPrayerList> prayers = template.query(sql, param, myPrayerRowMapper());
        if (prayers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new MyCardDetails(prayers.get(0).getCardId(), prayers.get(0).getCardUpdateTime(), prayers));
    }

    public Optional<MyCardDetails> findLatestPastCardByMember(Long memberId) {
        String sql = "select c.id as card_id, c.update_date as card_update_date, c.week as card_week, " +
                "p.id as prayer_id, p.content as prayer_content, p.responded as prayer_responded " +
                "from " +
                "   ( " +
                "       select * " +
                "       from card cSub " +
                "       where cSub.member_id = :memberId " +
                "       order by cSub.week desc " +
                "       limit 1 offset 1 " +
                "   ) as c " +
                "join card_prayer cp on(cp.card_id = c.id) " +
                "join prayer p on(p.id = cp.prayer_id);";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        List<MyPrayerList> prayers = template.query(sql, param, myPrayerRowMapper());
        if (prayers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new MyCardDetails(prayers.get(0).getCardId(), prayers.get(0).getCardUpdateTime(), prayers));
    }


    private RowMapper<MyPrayerList> myPrayerRowMapper() {
        return (rs, rowNum) -> new MyPrayerList(
                rs.getLong("card_id"),
                rs.getTimestamp("card_update_date").toLocalDateTime(),
                rs.getLong("prayer_id"),
                rs.getString("prayer_content"),
                rs.getBoolean("prayer_responded")
        );
    }
}
