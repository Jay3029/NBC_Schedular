package com.sparta.schedular.controller;

import com.sparta.schedular.dto.ScheduleRequestDTO;
import com.sparta.schedular.dto.ScheduleResponseDTO;
import com.sparta.schedular.service.ScheduleService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    // JDBCTemplate 변수 선언
    private final JdbcTemplate jdbcTemplate;

    // 외부에서 만들어진 JDBCTemplate를 넣어줌 (이는 Spring에서 관리를 하고 있음)
    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    // CREATE
    @PostMapping("/schedules")
    public ScheduleResponseDTO createSchedule(@RequestBody ScheduleRequestDTO scheduleRequestDTO) {

        // Service
        ScheduleService schedularService = new ScheduleService(jdbcTemplate);
        return schedularService.createSchedule(scheduleRequestDTO);

    }



    // READ (단일, ID값으로 조회)
    @GetMapping("/schedules/{id}")
    public ScheduleResponseDTO getSchedule(@PathVariable Long id) {

        // Service
        ScheduleService schedularService = new ScheduleService(jdbcTemplate);
        return schedularService.getSchedule(id);

    }
    // READ (전체, 수정일과 작성자로 조회)
    @GetMapping("/schedules")
    public List<ScheduleResponseDTO> getAllSchedules(
            @RequestParam(value = "editing_date", required = false) String editing_date,
            @RequestParam(value = "writer", required = false) String writer) {

        // Service
        ScheduleService schedularService = new ScheduleService(jdbcTemplate);
        return schedularService.getAllSchedules(editing_date, writer);

    }



    // UPDATE (비밀번호 요구)
    @PutMapping("/schedules/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDTO scheduleRequestDTO) {

        // Service
        ScheduleService schedularService = new ScheduleService(jdbcTemplate);
        return schedularService.updateSchedule(id, scheduleRequestDTO);

    }



    // DELETE (비밀번호 요구)
    @DeleteMapping("/schedules/{id}")
    public Long deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDTO scheduleRequestDTO) {

        // Service
        ScheduleService schedularService = new ScheduleService(jdbcTemplate);
        return schedularService.deleteSchedule(id, scheduleRequestDTO);

    }


}
