package com.example.schedule.controller;

import com.example.schedule.domain.Schedule;
import com.example.schedule.service.ScheduleService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule dto) throws ChangeSetPersister.NotFoundException {
        //Service Layer 호출,응답

        return new ResponseEntity<>(scheduleService.joinSchedule(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Schedule> findAllSchedules(
            @RequestParam(required = false, value ="username") String username) {

        return scheduleService.findAllSchedules(username);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> findScheduleById(
            @PathVariable(value = "id") Long id) {

        Schedule schedule = scheduleService.findScheduleById(id);
        if(schedule==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }
    @GetMapping("/date")
    public List<Schedule> findScheduleByDate(
            @RequestParam(required = true, value = "updateDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime updateDate){
        return scheduleService.findDateSchedules(updateDate);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable(value = "id") Long id,
            @RequestBody Schedule dto
    ) {

        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getTitle(), dto.getContent(),dto.getUpdateDate(),dto.getPassword(), dto.getUsername()), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Schedule> updateScheduleById(
            @PathVariable(value = "id") Long id,
            @RequestBody Schedule dto
    ) {
        return new ResponseEntity<>(scheduleService.updateTitle(id, dto.getTitle(), dto.getContent(),dto.getUpdateDate(), dto.getPassword()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule
            (@PathVariable(value = "id") Long id,
             @RequestBody Schedule dto
            ) {
        scheduleService.deleteSchedule(id,dto.getPassword());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
