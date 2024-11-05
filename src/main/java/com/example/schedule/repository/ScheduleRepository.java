package com.example.schedule.repository;

import com.example.schedule.domain.Schedule;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule) throws ChangeSetPersister.NotFoundException;

    List<Schedule> findAllSchedules();

    Optional<Schedule> findScheduleById(Long id);

    Schedule findScheduleByIdOrElseThrow(Long id);

    List<Schedule> findScheduleByUsernameOrElseThrow(String username);


    List<Schedule> findScheduleByDateOrElseThrow(LocalDateTime updateDate);
    int updateSchedule(Long id, String title, String content, LocalDateTime updateDate, String password, String username);

    int updateTitle(Long id, String title,LocalDateTime updateDate);

    int deleteSchedule(Long id);
}
