package com.example.schedule.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor //생성자만듬
public class Schedule {

    @Setter
    private Long id;
    private String title;
    private String content;
    private Date createDate;
    private Date updateDate;
    private String password;
    private String username;


    public Schedule(Long id, String title, String content, Date createDate, Date updateDate,String password, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.password =password;
        this.username=username;
    }
    public Schedule(Schedule saveSchedule) {
        this.id = saveSchedule.getId();
        this.title = saveSchedule.getTitle();
        this.content = saveSchedule.getContent();
        this.createDate = saveSchedule.getCreateDate();
        this.updateDate = saveSchedule.getUpdateDate();
        this.password = saveSchedule.getPassword();
        this.username =saveSchedule.getUsername();
    }


}
