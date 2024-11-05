package com.example.schedule.repository;

import com.example.schedule.domain.Schedule;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule) throws ChangeSetPersister.NotFoundException;

    List<Schedule> findAllSchedules();

    Optional<Schedule> findScheduleById(Long id);

    List<Schedule> findScheduleByUsernameOrElseThrow(String username);

    Schedule findScheduleByIdOrElseThrow(Long id);
    int updateSchedule(Long id, String title, String content, Date updateDate, String password);

    int updateTitle(Long id, String title,Date updateDate);

    int deleteSchedule(Long id);
}
