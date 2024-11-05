package com.example.schedule.service;

import com.example.schedule.domain.Schedule;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Date;
import java.util.List;

public interface ScheduleService {
    Schedule joinSchedule(Schedule schedule) throws ChangeSetPersister.NotFoundException;
    List<Schedule> findAllSchedules();
    Schedule findScheduleById(Long id);
    Schedule updateSchedule(Long id, String title, String content, Date updateTime,String password);
    Schedule updateTitle(Long id, String title, String content, Date updateTime,String password);

    void deleteSchedule(Long id,String password);

}
