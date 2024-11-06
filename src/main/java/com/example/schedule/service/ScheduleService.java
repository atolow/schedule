package com.example.schedule.service;

import com.example.schedule.domain.Schedule;
import com.example.schedule.dto.ScheduleDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ScheduleService {
    ScheduleDto joinSchedule(ScheduleDto schedule) throws ChangeSetPersister.NotFoundException;
    List<ScheduleDto> findAllSchedules(String username);
    ScheduleDto findScheduleById(Long id);
    List<ScheduleDto> findDateSchedules(LocalDateTime updateDate);
    List<ScheduleDto> findScheduleByUsernameOrByUpdateElseThrow(String username, LocalDateTime updateDate);
    ScheduleDto updateSchedule(Long id, String title, String content,String password,String username);


    ScheduleDto updateTitle(Long id, String title, LocalDateTime updateTime,String password,String username);

    void deleteSchedule(Long id,String password);

}
