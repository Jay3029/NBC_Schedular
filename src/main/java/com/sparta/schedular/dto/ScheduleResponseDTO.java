package com.sparta.schedular.dto;

import com.sparta.schedular.entity.Schedule;
import lombok.Getter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
public class ScheduleResponseDTO {

    private long id;
    private String writer;
    private String password;
    private String contents;
    private Timestamp posting_date;
    private Timestamp editing_date;


    public ScheduleResponseDTO(Schedule schedule) {
        this.id = schedule.getId();
        this.writer = schedule.getWriter();
        this.password = schedule.getPassword();
        this.contents = schedule.getContents();
        this.posting_date = schedule.getPosting_date();
        this.editing_date = schedule.getEditing_date();
    }

}
