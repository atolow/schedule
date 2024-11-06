package com.example.member.repository;

import com.example.member.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcTemplateMember implements MemberRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMember(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public MemberDto joinMember(MemberDto memberDto) {
        LocalDateTime now2 = LocalDateTime.now();
        String sql = "INSERT INTO member(username,email,createDate,updateDate) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                memberDto.getUsername(),
                memberDto.getEmail(),
                now2,
                now2);

        Long id = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);

        return MemberDto.builder()
                .id(id)
                .username(memberDto.getUsername())
                .email(memberDto.getEmail())
                .createDate(memberDto.getCreateDate())
                .updateDate(memberDto.getUpdateDate())
                .build();
    }

    @Override
    public MemberDto findById(Long id) {
        List<MemberDto> result = jdbcTemplate.query("select * from member where=id?", memberDtoRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
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
