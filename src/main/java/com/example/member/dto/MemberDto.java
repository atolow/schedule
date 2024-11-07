package com.example.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String userName;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public MemberDto(Long id, String userName, String email, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
