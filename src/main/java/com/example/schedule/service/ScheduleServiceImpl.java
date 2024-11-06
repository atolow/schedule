package com.example.schedule.service;

import com.example.schedule.domain.Schedule;
import com.example.schedule.dto.ScheduleDto;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleDto joinSchedule(ScheduleDto dto) throws ChangeSetPersister.NotFoundException {
        ScheduleDto schedule = new ScheduleDto(dto.getId(), dto.getTitle(), dto.getContent(), dto.getPassword(), dto.getUsername(), dto.getUserId());
        //DB 저장
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleDto> findAllSchedules(String username) {
        if (StringUtils.isEmpty(username)){
            return scheduleRepository.findAllSchedules();
        }
        return scheduleRepository.findScheduleByUsernameOrElseThrow(username);
    }

    @Override
    public ScheduleDto findScheduleById(Long id) {
        ScheduleDto schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        // NPE 방지

        return new ScheduleDto(schedule);
    }

    @Override
    public List<ScheduleDto> findDateSchedules(LocalDateTime updateDate) {

        return scheduleRepository.findScheduleByDateOrElseThrow(updateDate);
    }

    @Override
    public List<ScheduleDto> findScheduleByUsernameOrByUpdateElseThrow(String username, LocalDateTime updateDate) {
        if(username==null && updateDate==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The username or update is required values.");
        }
        else if(username!=null && updateDate==null){
            return scheduleRepository.findScheduleByUsernameOrElseThrow(username);
        }
        else if(username==null && updateDate!=null){
            return scheduleRepository.findScheduleByDateOrElseThrow(updateDate);
        }
        else {
            return scheduleRepository.findScheduleByUsernameOrByUpdateElseThrow(username, updateDate);
        }
    }


    @Transactional
    @Override
    public ScheduleDto updateSchedule(Long id, String title, String content, String password,String username) {

        if (title == null || content == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title or content is required values.");
        }

        ScheduleDto schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if(!schedule.getPassword().equals(password)){
            throw new RuntimeException("비밀번호가 다릅니다.");

        }

        int updateRow = scheduleRepository.updateSchedule(id, title, content, password,username);

        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
        schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

//        if ( optionalMemo.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
//        }
        return new ScheduleDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleDto updateTitle(Long id, String title, LocalDateTime updateDate,String password,String username) {
        if (title == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title or content is required values.");
        }

        ScheduleDto schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if(!schedule.getPassword().equals(password)){
            throw new RuntimeException("비밀번호가 다릅니다.");

        }

        int updateRow = scheduleRepository.updateTitle(id, title,updateDate, password,username);

        if (updateRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
        schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        return new ScheduleDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, String password) {

        ScheduleDto schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        if(!schedule.getPassword().equals(password)){
            throw new RuntimeException("비밀번호가 다릅니다.");
        }
        int deletedRow = scheduleRepository.deleteSchedule(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title or content is required values.");
        }

    }
}