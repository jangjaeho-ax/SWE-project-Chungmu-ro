package Chungmu_ro.schoolManagement.service;

import Chungmu_ro.schoolManagement.domain.Course;
import Chungmu_ro.schoolManagement.domain.Student;
import Chungmu_ro.schoolManagement.repository.AssignmentRepository;
import Chungmu_ro.schoolManagement.repository.CourseRepository;
import Chungmu_ro.schoolManagement.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentServiceIntergrateTest {

    @Autowired
    private  StudentService studentService;
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void 가입(){

        Student student =new Student("test2","2345","kim","ko","exa@dgu.kr",2004000000, (byte) 2);
        Student newStudent = studentService.studentJoin(student);


        assertEquals(student,newStudent);

    }
    @Test
    void 로그인(){

        Student student =new Student("test2","2345","kim","ko","exa@dgu.kr",2004000000, (byte) 2);
        studentService.studentJoin(student);
        try {
            Student newStudent = studentService.studentLogin("test2", "2345");
            Student newStudent2 = studentService.studentLogin("test3", "2345");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
    @Test
    void 강의목록검색(){
        try {
            Student student = studentService.studentLogin("studentid1", "studentpw1");
            List<Course> courseList = studentService.getCourseList(student);
            List<Course> courses = courseRepository.findAll();

            assertEquals(courseList,courses);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}