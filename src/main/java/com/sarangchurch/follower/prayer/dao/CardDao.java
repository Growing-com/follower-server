package com.sarangchurch.follower.prayer.dao;

import com.sarangchurch.follower.member.domain.Gender;
import com.sarangchurch.follower.prayer.dao.dto.CardInfo;
import com.sarangchurch.follower.prayer.dao.dto.MemberInfo;
import com.sarangchurch.follower.prayer.dao.dto.PrayerInfo;
import com.sarangchurch.follower.prayer.domain.Week;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CardDao {

    private final NamedParameterJdbcTemplate template;

    public CardDao(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<CardInfo> findCardsByTeamAndWeek(Long teamId, Week week) {
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
                .addValue("date", week.getDate().toString());

        return template
                .query(sql, param, prayerRowMapper())
                .stream()
                .collect(Collectors.groupingBy(PrayerInfo::getCardId))
                .entrySet()
                .stream()
                .map(it -> {
                    PrayerInfo prayerInfo = it.getValue().get(0);
                    MemberInfo memberInfo = new MemberInfo(
                            prayerInfo.getMemberId(),
                            prayerInfo.getMemberName(),
                            prayerInfo.getGender(),
                            prayerInfo.getBirthDate()
                    );
                    return new CardInfo(it.getKey(), prayerInfo.getCardUpdateTime(), it.getValue(), memberInfo);
                })
                .collect(Collectors.toList());
    }

    private RowMapper<PrayerInfo> prayerRowMapper() {
        return (rs, rowNum) -> new PrayerInfo(
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
}
