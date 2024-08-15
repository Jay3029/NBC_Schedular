package com.sparta.schedular.entity;

import com.sparta.schedular.dto.ScheduleRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    // DB에 들어있는 데이터
    private long id;
    private String writer;
    private String password;
    private String contents;
    private Timestamp posting_date;
    private Timestamp editing_date;

    public Schedule(ScheduleRequestDTO scheduleRequestDTO) {
        this.id = scheduleRequestDTO.getId();
        this.writer = scheduleRequestDTO.getWriter();
        this.password = scheduleRequestDTO.getPassword();
        this.contents = scheduleRequestDTO.getContents();
        this.posting_date = scheduleRequestDTO.getPosting_date();
        this.editing_date = scheduleRequestDTO.getEditing_date();
    }

}
