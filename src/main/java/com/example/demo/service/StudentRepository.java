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



    /*



    final private JdbcTemplate jdbcTemplate;
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    StudentService(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private Student mapRowToPatient(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
                rs.getLong("patient_id"),
                rs.getString("surname"),
                rs.getString("name"),
                rs.getString("middleName"),
                rs.getString("symptoms"),
                rs.getString("isHavingTipAbroad"),
                rs.getString("contactWithPatients")
        );
    }

    private Map<String, Object> mapPatientToParams(Student student) {
        Map<String, Object> params = new HashMap<>();
        params.put("surname", student.getSurname());
        params.put("name", student.getName());
        params.put("middleName", student.getMiddleName());
        params.put("symptoms", student.getSymptoms());
        params.put("isHavingTipAbroad", student.getIsHavingDept());
        params.put("contactWithPatients", student.getContactWithPatients());
        return params;
    }

    public List<Student> findAll() {
        return new ArrayList<>(jdbcTemplate.query("select * from patient", this::mapRowToPatient));
    }

    public Student find(Long id) throws EmptyResultDataAccessException {
        return namedParameterJdbcTemplate.queryForObject("select * from patient where patient_id = (:id)",
                Collections.singletonMap("id", id), this::mapRowToPatient);

    }

    public Long savePatient(Student student) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("patient")
                .usingGeneratedKeyColumns("patient_id");
        return simpleJdbcInsert.executeAndReturnKey(mapPatientToParams(student)).longValue();
    }

    public int delete(Long id) {
        return namedParameterJdbcTemplate.update("delete from patient where patient_id = (:id)",
                Collections.singletonMap("id", id));
    }

    public Long put(Long id, Student student) {
        if (delete(id) == 0)
            throw new RuntimeException("Patient with id " + id + " not found");
        return savePatient(student);
    }

    public void patch(Long id, Student student) {
        Student studentBase = find(id);
        if (studentBase == null) {
            throw new RuntimeException("Patient with id " + id + " not found");
        }

        // Формирование sql запроса
        StringBuilder sqlBuilder = new StringBuilder();
        Map<String, Object> values = new HashMap<>();
        boolean first = true;
        boolean changed = false;

        // Валидация введенных данных
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Student>> constraintViolations;

        if (student.getName() != null) {
            String name = student.getName();
            constraintViolations = validator.validateValue(Student.class, "name", name);
            if (constraintViolations.iterator().hasNext()) {
                throw new RuntimeException("Patient has no valid name");
            }
            if (!studentBase.getName().equals(name)) {
                if (!first) {
                    sqlBuilder.append(", ");
                }
                first = false;
                changed = true;
                values.put("name", name);
                sqlBuilder.append("name = :name");
            }
        }

        if (student.getSurname() != null) {
            String surname = student.getSurname();
            constraintViolations = validator.validateValue(Student.class, "surname", surname);
            if (constraintViolations.iterator().hasNext()) {
                throw new RuntimeException("Patient has no valid surname");
            }
            if (!studentBase.getSurname().equals(surname)) {
                if (!first) {
                    sqlBuilder.append(", ");
                }
                first = false;
                changed = true;
                values.put("surname", surname);
                sqlBuilder.append("surname = :surname");
            }
        }

        if (student.getMiddleName() != null) {
            String middleName = student.getMiddleName();
            constraintViolations = validator.validateValue(Student.class, "middleName", middleName);
            if (constraintViolations.iterator().hasNext()) {
                throw new RuntimeException("Patient has no valid middleName");
            }
            if (!studentBase.getMiddleName().equals(middleName)) {
                if (!first) {
                    sqlBuilder.append(", ");
                }
                first = false;
                changed = true;
                values.put("middleName", middleName);
                sqlBuilder.append("middleName = :middleName");
            }
        }

        if (student.getSymptoms() != null) {
            String symptoms = student.getSymptoms();
            constraintViolations = validator.validateValue(Student.class, "symptoms", symptoms);
            if (constraintViolations.iterator().hasNext()) {
                throw new RuntimeException("Patient has no valid symptoms");
            }
            if (!studentBase.getSymptoms().equals(symptoms)) {
                if (!first) {
                    sqlBuilder.append(", ");
                }
                first = false;
                changed = true;
                values.put("symptoms", symptoms);
                sqlBuilder.append("symptoms = :symptoms");
            }
        }

        if (student.getIsHavingDept() != null) {
            String isHavingTipAbroad = student.getIsHavingDept();
            constraintViolations = validator.validateValue(Student.class, "isHavingTipAbroad", isHavingTipAbroad);
            if (constraintViolations.iterator().hasNext()) {
                throw new RuntimeException("Patient has no valid isHavingTipAbroad");
            }
            if (!studentBase.getIsHavingDept().equals(isHavingTipAbroad)) {
                if (!first) {
                    sqlBuilder.append(", ");
                }
                first = false;
                changed = true;
                values.put("isHavingTipAbroad", isHavingTipAbroad);
                sqlBuilder.append("isHavingTipAbroad = :isHavingTipAbroad");
            }
        }

        if (student.getContactWithPatients() != null) {
            String contactWithPatients = student.getContactWithPatients();
            constraintViolations = validator.validateValue(Student.class, "contactWithPatients", contactWithPatients);
            if (constraintViolations.iterator().hasNext()) {
                throw new RuntimeException("Patient has no valid contactWithPatients");
            }
            if (!studentBase.getContactWithPatients().equals(contactWithPatients)) {
                if (!first) {
                    sqlBuilder.append(", ");
                }
                first = false;
                changed = true;
                values.put("contactWithPatients", contactWithPatients);
                sqlBuilder.append("contactWithPatients = :contactWithPatients");
            }
        }

        if (changed) {
            values.put("id", id);
            String sql = "update patient set " + sqlBuilder.toString() + " where patient_id = :id";
            namedParameterJdbcTemplate.update(sql, values);
        }
    }

     */

