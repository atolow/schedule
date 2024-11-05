package com.example.schedule.repository;

import com.example.schedule.domain.Schedule;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) throws ChangeSetPersister.NotFoundException {
        LocalDateTime now2 = LocalDateTime.now();
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<>();
        params.put("title", schedule.getTitle());
        params.put("content", schedule.getContent());
        params.put("createDate",now2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd ")));
        params.put("updateDate",now2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd ")));
        params.put("password",schedule.getPassword());
        params.put("username", schedule.getUsername());
//저장 후 생선된 key값 Number 타입으로 변환되는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));


        return findScheduleById(key.longValue()).orElseThrow(ChangeSetPersister.NotFoundException::new);

    }

    @Override
    public List<Schedule> findAllSchedules() {
        return jdbcTemplate.query("select * from schedule", scheduleRowMapper());
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id){
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id=?", scheduleRowMapperV2(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Schedule> findScheduleByUsernameOrElseThrow(String username){
        return jdbcTemplate.query("Select * from schedule where username=?", scheduleRowMapperV2(), username);
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id=?", scheduleRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public int updateSchedule(Long id, String title, String content, Date updateDate, String password) {
        LocalDateTime now2= LocalDateTime.now();
        int updatedRow = jdbcTemplate.update("update schedule set title = ?, content = ?, updateDate =? where id = ?", title, content, now2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss")) ,id);

        return updatedRow;
    }

    @Override
    public int updateTitle(Long id, String title,Date updateDate) {
        LocalDateTime now2= LocalDateTime.now();
        int updatetitle = jdbcTemplate.update("update schedule set title = ?,updateDate =? where id = ?", title,now2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss")), id);

        return updatetitle;
    }


    @Override
    public int deleteSchedule(Long id) {
        int updatedRow = jdbcTemplate.update("delete from schedule where id = ?", id);
        return updatedRow;
    }


    private RowMapper<Schedule> scheduleRowMapper(){
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getDate("createDate"),
                        rs.getDate("updateDate"),
                        rs.getString("password"),
                        rs.getString("username")
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2(){
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getDate("createDate"),
                        rs.getDate("updateDate"),
                        rs.getString("password"),
                        rs.getString("username")
                );
            }
        };
    }
}


