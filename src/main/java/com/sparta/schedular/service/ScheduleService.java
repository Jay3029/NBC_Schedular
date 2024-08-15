package com.sparta.schedular.service;

import com.sparta.schedular.dto.ScheduleRequestDTO;
import com.sparta.schedular.dto.ScheduleResponseDTO;
import com.sparta.schedular.entity.Schedule;
import com.sparta.schedular.repository.ScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ScheduleService {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // CREATE Schedule Service
    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO scheduleRequestDTO) {
        // RequestDTO -> Entity
        Schedule schedule = new Schedule(scheduleRequestDTO);

        // DB에 저장
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule savedSchedule = scheduleRepository.save(schedule);

        System.out.println(schedule.getId());
        System.out.println(schedule.getWriter());
        System.out.println(schedule.getPassword());
        System.out.println(schedule.getContents());
        System.out.println(schedule.getPosting_date());
        System.out.println(schedule.getEditing_date());


        // Entity -> ResponseDTO
        ScheduleResponseDTO scheduleResponseDTO = new ScheduleResponseDTO(schedule);

        return scheduleResponseDTO;
    }


    // READ One Schedule Service
    public ScheduleResponseDTO getSchedule(Long id) {

        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.findOne(id);

    }
    // READ All Schedule Service
    public List<ScheduleResponseDTO> getAllSchedules(String editing_date, String writer) {
        // DB 전체 조회
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        return scheduleRepository.findAll(editing_date, writer);
    }


    // UPDATE Schedule Service
    public Long updateSchedule(Long id, ScheduleRequestDTO scheduleRequestDTO) {
        // 해당 작성자의 일정이 존재하는지 확인
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule schedule = scheduleRepository.findById(id);

        if(schedule != null) {
            boolean passwordIsCorrect = scheduleRepository.isPasswordCorrect(id, scheduleRequestDTO.getPassword());
            if(passwordIsCorrect) {
                // 일정이 존재할 시, 해당 일정을 갖고와서 수정 진행
                scheduleRepository.update(id, scheduleRequestDTO);
                return id;
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("Schedule not found");
        }
    }


    // DELETE Schedule Service
    public Long deleteSchedule(Long id, ScheduleRequestDTO scheduleRequestDTO) {
        // 해당 작성자의 일정이 존재하는지 확인
        ScheduleRepository scheduleRepository = new ScheduleRepository(jdbcTemplate);
        Schedule schedule = scheduleRepository.findById(id);

        if(schedule != null) {
            boolean passwordIsCorrect = scheduleRepository.isPasswordCorrect(id, scheduleRequestDTO.getPassword());
            if(passwordIsCorrect) {
                // 일정이 존재할 시, 해당 일정을 갖고와서 수정 진행
                scheduleRepository.delete(id);
                return id;
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException("Schedule not found");
        }
    }


}
