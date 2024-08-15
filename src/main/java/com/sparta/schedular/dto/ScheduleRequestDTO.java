package com.sparta.schedular.dto;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ScheduleRequestDTO {

    private int id;
    private String writer;
    private String password;
    private String contents;
    private Timestamp posting_date;
    private Timestamp editing_date;
}
