package Chungmu_ro.schoolManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityTransaction;

@Service
public class StudentService {

    CourseRepository courseRepository;
    AssignmentRepository assignmentRepository;
    GradeRepository gradeRepository;
    AttendanceRepository attendanceRepository;

    EntityTransaction transaction;

    @Autowired
    public StudentService(CourseRepository courseRepository, AssignmentRepository assignmentRepository, GradeRepository gradeRepository) {
        this.courseRepository = courseRepository;
        this.assignmentRepository = assignmentRepository;
        this.gradeRepository = gradeRepository;
    }
/*
    public void addCourseList(Student student){
        student.setCourseList(courseRepository.findBySid(student.getSid()));
    }
    public void addAssignmentList(Student student){
        student.setAssignmentList(assignmentRepository.findBySid(student.getSid()));
    }
    public void addAttendacneList(Student student){
        student.setAttendanceList(attendanceRepository.findBySid(student.getSid()));
    }
    public void addGradeList(Student student){
        student.setGradeList(gradeRepository.findBySid(student.getSid()));
    }
*/

}
