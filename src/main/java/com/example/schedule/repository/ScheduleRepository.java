package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleDto saveSchedule(ScheduleDto schedule) throws ChangeSetPersister.NotFoundException;

    List<ScheduleDto> findAllSchedules();

    Optional<ScheduleDto> findScheduleById(Long id);

    ScheduleDto findScheduleByIdOrElseThrow(Long id);

    List<ScheduleDto> findScheduleByUsernameOrElseThrow(String username);


    List<ScheduleDto> findScheduleByDateOrElseThrow(LocalDateTime updateDate);

    List<ScheduleDto> findScheduleByUsernameOrByUpdateElseThrow(String username,LocalDateTime updateDate);



    int updateSchedule(Long id, String title, String content, String password, String username);

    int updateTitle(Long id, String title,LocalDateTime updateDate,String password,String username);

    int deleteSchedule(Long id);
}
