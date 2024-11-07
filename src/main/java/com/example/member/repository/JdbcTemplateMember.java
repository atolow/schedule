package com.example.member.repository;

import com.example.member.dto.MemberDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateMember implements MemberRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMember(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public MemberDto joinMember(MemberDto memberDto) throws ChangeSetPersister.NotFoundException{
        LocalDateTime now2 = LocalDateTime.now();
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        Map<String, Object> params = new HashMap<>();
        params.put("username", memberDto.getUserName());
        params.put("email", memberDto.getEmail());
        params.put("createDate", now2);
        params.put("updateDate", now2);
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));

        return findMemberById(key.longValue()).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }
    @Override
    public Optional<MemberDto> findMemberById(Long id) {
        List<MemberDto> result = jdbcTemplate.query("select * from member where id=?", memberDtoRowMapperV2(), id);
        return result.stream().findAny();
    }
    @Override
    public MemberDto findMemberByIdOrElseThrow(Long id) {
        List<MemberDto> result = jdbcTemplate.query("select * from member where id=?", memberDtoRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public List<MemberDto> findAllMembers() {
        List<MemberDto> memberDto = jdbcTemplate.query("Select * from member INNER JOIN schedule ON member.id = schedule.userId", memberDtoRowMapperV2());
        return memberDto;
    }



    private RowMapper<MemberDto> memberDtoRowMapperV2() {
        return new RowMapper<MemberDto>() {
            @Override
            public MemberDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MemberDto(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getTimestamp("createDate").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime()
                );
            }
        };
    }

}
