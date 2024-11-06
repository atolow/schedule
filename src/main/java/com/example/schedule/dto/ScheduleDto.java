package com.example.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String password;
    private String username;
    private Long userId;


    public ScheduleDto(Long id, String title, String content,String password, String username,Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.password = password;
        this.username = username;
        this.userId = userId;
    }


    public ScheduleDto(long id, String title, String content, LocalDateTime createDate, LocalDateTime updateDate, String password, String username, long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate=updateDate;
        this.password = password;
        this.username = username;
        this.userId = userId;
    }

    public ScheduleDto(ScheduleDto schedule) {
        this.id=schedule.id;
        this.title=schedule.title;
        this.content=schedule.content;
        this.createDate=schedule.createDate;
        this.updateDate=schedule.updateDate;
        this.password=schedule.password;
        this.username=schedule.username;
        this.userId=schedule.userId;
    }
}
