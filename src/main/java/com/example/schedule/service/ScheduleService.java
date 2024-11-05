package com.example.schedule.service;

import com.example.schedule.domain.Schedule;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ScheduleService {
    Schedule joinSchedule(Schedule schedule) throws ChangeSetPersister.NotFoundException;
    List<Schedule> findAllSchedules(String username);
    Schedule findScheduleById(Long id);
    List<Schedule> findDateSchedules(LocalDateTime updateDate);
    Schedule updateSchedule(Long id, String title, String content, LocalDateTime updateTime,String password,String username);
    Schedule updateTitle(Long id, String title, String content, LocalDateTime updateTime,String password);

    void deleteSchedule(Long id,String password);

}
