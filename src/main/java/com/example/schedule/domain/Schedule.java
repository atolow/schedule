package com.example.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor //생성자만듬
public class Schedule {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String password;
    private String username;
    private Long userId;


    public Schedule(Long id, String title, String content, LocalDateTime createDate, LocalDateTime updateDate,String password, String username,Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.password =password;
        this.username=username;
        this.userId=userId;

    }
    public Schedule(Schedule saveSchedule) {
        this.id = saveSchedule.getId();
        this.title = saveSchedule.getTitle();
        this.content = saveSchedule.getContent();
        this.createDate = saveSchedule.getCreateDate();
        this.updateDate = saveSchedule.getUpdateDate();
        this.password = saveSchedule.getPassword();
        this.username =saveSchedule.getUsername();
        this.userId = saveSchedule.getUserId();
    }


}
