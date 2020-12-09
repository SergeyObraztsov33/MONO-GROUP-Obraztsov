package com.example.demo.service;

import com.example.demo.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class StudentRepository {
    final private JdbcTemplate jdbcTemplate;
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Autowired
    StudentRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //переводит из строк в объекты
    private Student mapRowToStudent(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
                rs.getInt("student_id"),
                rs.getString("surname"),
                rs.getString("name"),
                rs.getString("course"),
                rs.getString("isHavingDept"),
                rs.getString("awards")
        );
    }

    public List<Student> findAll() {
        return new ArrayList<>(jdbcTemplate.query("select * from student", this::mapRowToStudent));
    }

    public int deleteStudent(int id) {
        String changedId = Integer.toString(id);
        return namedParameterJdbcTemplate.update("delete from student where student_id = (:id)",
                //тут подсмотрел у Саши не понял для чего мы это делаем, без него ошибку выдает
                Collections.singletonMap("id", id));
    }

    public Student findById (Integer id){
        String sql = "SELECT * FROM student where student_id = ?";
        try {
            return (Student) this.jdbcTemplate.queryForObject(
                       sql, new Object[] {id}, this:: mapRowToStudent);
        }
        catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public Boolean addStudent (Student student){
        String query="insert into user values(?,?,?,?,?,?,?)";
        return jdbcTemplate.execute(query,new PreparedStatementCallback<Boolean>(){
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {

                ps.setInt(1,student.getId());
                ps.setString(2,student.getSurname());
                ps.setString(3,student.getName());
                ps.setString(4,student.getCourse());
                ps.setString(5,student.getIsHavingDept());
                ps.setString(6,student.getAwards());
                return ps.execute();
            }
        });
    }

}




