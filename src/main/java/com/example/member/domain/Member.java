package com.example.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor //생성자만듬
public class Member {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Member(Long id, String username, String email, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
