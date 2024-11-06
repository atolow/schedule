package com.example.schedule.repository;
import com.example.schedule.dto.ScheduleDto;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleDto saveSchedule(ScheduleDto schedule) throws ChangeSetPersister.NotFoundException {

        LocalDateTime now2 = LocalDateTime.now();
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<>();
        params.put("title", schedule.getTitle());
        params.put("content", schedule.getContent());
        params.put("createDate", now2);
        params.put("updateDate", now2);
        params.put("password", schedule.getPassword());
        params.put("username", schedule.getUsername());
        params.put("userId", schedule.getUserId());
//저장 후 생선된 key값 Number 타입으로 변환되는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));


        return findScheduleById(key.longValue()).orElseThrow(ChangeSetPersister.NotFoundException::new);

    }

    @Override
    public List<ScheduleDto> findAllSchedules() {
        return jdbcTemplate.query("select * from schedule", scheduleRowMapperV2());
    }

    @Override
    public Optional<ScheduleDto> findScheduleById(Long id) {
        List<ScheduleDto> result = jdbcTemplate.query("select * from schedule where id=?", scheduleRowMapperV2(), id);
        return result.stream().findAny();
    }

    @Override
    public ScheduleDto findScheduleByIdOrElseThrow(Long id) {
        List<ScheduleDto> result = jdbcTemplate.query("select * from schedule where id=?", scheduleRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public List<ScheduleDto> findScheduleByUsernameOrElseThrow(String username) {
        return jdbcTemplate.query("Select * from schedule where username=?", scheduleRowMapperV2(), username);
    }


    @Override
    public List<ScheduleDto> findScheduleByDateOrElseThrow(LocalDateTime updateDate) {
        List<ScheduleDto> scheduleList = jdbcTemplate.query("Select * from schedule where updateDate=?", scheduleRowMapperV2(), updateDate);
        return scheduleList;
    }

    @Override
    public List<ScheduleDto> findScheduleByUsernameOrByUpdateElseThrow(String username, LocalDateTime updateDate) {
        List<ScheduleDto> scheduleList = jdbcTemplate.query("Select * from schedule where username=? or updateDate=?", scheduleRowMapperV2(), username, updateDate);
        return scheduleList;
    }



    @Override
    public int updateSchedule(Long id, String title, String content,  String password, String username) {
        LocalDateTime now2 = LocalDateTime.now();
        int updatedRow = jdbcTemplate.update("update schedule set title = ?, content = ?, updateDate =?,username = ? where id = ?", title, content, now2, username, id);

        return updatedRow;
    }

    @Override
    public int updateTitle(Long id, String title, LocalDateTime updateDate,String password, String username) {
        LocalDateTime now2 = LocalDateTime.now();
        int updatetitle = jdbcTemplate.update("update schedule set title = ?,updateDate =? where id = ?", title, now2, id);

        return updatetitle;
    }


    @Override
    public int deleteSchedule(Long id) {
        int updatedRow = jdbcTemplate.update("delete from schedule where id = ?", id);
        return updatedRow;
    }



//    private RowMapper<Schedule> scheduleRowMapper(){
//        return new RowMapper<Schedule>() {
//            @Override
//            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return new Schedule(
//                        rs.getLong("id"),
//                        rs.getString("title"),
//                        rs.getString("content"),
//                        rs.getTimestamp("createDate").toLocalDateTime(),
//                        rs.getTimestamp("updateDate").toLocalDateTime(),
//                        rs.getString("password"),
//                        rs.getString("username"),
//                        rs.getLong("userId")
//                );
//            }
//        };
//    }

    private RowMapper<ScheduleDto> scheduleRowMapperV2() {
        return new RowMapper<ScheduleDto>() {
            @Override
            public ScheduleDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("createDate").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime(),
                        rs.getString("password"),
                        rs.getString("username"),
                        rs.getLong("userId")
                );
            }
        };
    }
}


