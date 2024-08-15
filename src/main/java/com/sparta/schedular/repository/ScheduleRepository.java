package com.sparta.schedular.repository;

import com.sparta.schedular.dto.ScheduleRequestDTO;
import com.sparta.schedular.dto.ScheduleResponseDTO;
import com.sparta.schedular.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    // CREATE DB
    public Schedule save(Schedule schedule) {
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본키 반환을 위한 객체

        // INSERT, ?로 동적으로 값을 둠
        String sql = "INSERT INTO schedule (writer, password, contents) VALUE (?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,schedule.getWriter());
            preparedStatement.setString(2, schedule.getPassword());
            preparedStatement.setString(3, schedule.getContents());
            System.out.println(preparedStatement);
            return preparedStatement;
        },keyHolder);

        // DB에 INSERT 후 받아온 기본키
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        return schedule;
    }



    // READ DB 2 Type
    // READ One
    public ScheduleResponseDTO findOne(Long id) {
        // DB 조회
        String sql = "SELECT * FROM schedule WHERE id = ?";

        // RowMapper를 사용해 ResultSet에서 데이터를 읽어 ScheduleResponseDTO 객체로 매핑
        RowMapper<ScheduleResponseDTO> rowMapper = new RowMapper<>() {
            @Override
            public ScheduleResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String writer = rs.getString("writer");
                String contents = rs.getString("contents");
                Timestamp posting_date = rs.getTimestamp("posting_date");
                Timestamp editing_date = rs.getTimestamp("editing_date");

                Schedule schedule = new Schedule();
                schedule.setId(id);
                schedule.setWriter(writer);
                schedule.setContents(contents);
                schedule.setPosting_date(posting_date);
                schedule.setEditing_date(editing_date);

                return new ScheduleResponseDTO(schedule);
            }
        };

        // 단건 조회용 메서드 queryForObject(sql문, 매개변수, RowMapper)
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    }
    // READ All
    public List<ScheduleResponseDTO> findAll(String editing_date, String writer) {

        // 수정일, 작성자 둘 다로 조회 , 혹은 둘 중 하나로만 조회, 혹은 둘다 없이 조회, 수정일 기준 내림차순 정렬
        // String sql = "SELECT * FROM schedule WHERE (editing_date = ? OR ? IS NULL ) AND (writer = ? OR ? IS NULL ) ORDER BY editing_date DESC";

        StringBuffer sql = new StringBuffer("SELECT * FROM schedule WHERE 1=1");

        List<Object> params = new ArrayList<>();
        // 2024-08-14
        if(editing_date != null) {
            sql.append(" AND DATE(editing_date) = ?");
            params.add(editing_date);
        }
        if(writer != null) {
            sql.append(" AND writer = ?");
            params.add(writer);
        }
        sql.append(" ORDER BY editing_date DESC");


        return jdbcTemplate.query(sql.toString(), params.toArray(), new RowMapper<ScheduleResponseDTO>() {
            @Override
            public ScheduleResponseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL의 결과로 받아온 Schedule 데이터들을 ScheduleResponseDTO 타입으로 변환하는 메서드
                Long id = rs.getLong("id");
                String writer = rs.getString("writer");
                String contents = rs.getString("contents");
                Timestamp posting_date = rs.getTimestamp("posting_date");
                Timestamp editing_date = rs.getTimestamp("editing_date");

                Schedule schedule = new Schedule();
                schedule.setId(id);
                schedule.setWriter(writer);
                schedule.setContents(contents);
                schedule.setPosting_date(posting_date);
                schedule.setEditing_date(editing_date);

                return new ScheduleResponseDTO(schedule);
            }
        });
    }



    // UPDATE DB
    public void update(Long id, ScheduleRequestDTO scheduleRequestDTO) {
        // 비밀번호가 일치하는 지 확인
        String checkSql = "SELECT password FROM schedule WHERE id = ?";

        String sql = "UPDATE schedule SET writer = ?, contents = ? WHERE id = ?";
        jdbcTemplate.update(sql, scheduleRequestDTO.getWriter(), scheduleRequestDTO.getContents(), id);
    }



    // DELETE DB
    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }



    // id 해당하는 값 가져오기..
    public Schedule findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM schedule WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setWriter(resultSet.getString("writer"));
                schedule.setPassword(resultSet.getString("password"));
                schedule.setContents(resultSet.getString("contents"));

                return schedule;
            } else {
                return null;
            }
        }, id);
    }

    public boolean isPasswordCorrect(Long id, String password) {

        String sql = "SELECT password FROM schedule WHERE id = ?";

        String pw = jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);

        if(pw != null) {
            return pw.equals(password);
        } else {
            return false;
        }
    }



}
