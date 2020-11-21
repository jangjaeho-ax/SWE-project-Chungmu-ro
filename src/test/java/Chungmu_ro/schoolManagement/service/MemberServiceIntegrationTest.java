package Chungmu_ro.schoolManagement.service;

import Chungmu_ro.schoolManagement.domain.Professor;
import Chungmu_ro.schoolManagement.domain.Student;
import Chungmu_ro.schoolManagement.repository.ProfessorRepository;
import Chungmu_ro.schoolManagement.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfessorRepository professorRepository;

    @Test
    void 학생회원가입() throws Exception {
    //Given
        Optional<Student> savedStudent = memberService.joinStudent("coblin", "1234", "jang", "jaeho", "email", (int)2015112117, (byte) 3);
        //When

    //Then
        Student findStudent = studentRepository.findBySid((int)2015112117).get();
        assertEquals(savedStudent.get().getFisrtName(),findStudent.getFisrtName());
    }

    @Test
    void 교수회원가입() throws Exception  {
        //Given
        Optional<Professor> savedStudent = memberService.joinProfessor("coblin", "1234", "jang", "jaeho", "email", (int)2004000000);
        //When

        //Then
        Professor findStudent = professorRepository.findByPid((int)2004000000).get();
        assertEquals(savedStudent.get().getFisrtName(),findStudent.getFisrtName());
    }

    @Test
    void 학생로그인() throws Exception {
        Optional<Student> savedStudent = memberService.joinStudent("coblin", "1234", "jang", "jaeho", "email", (int)2015112117, (byte) 3);

        Optional<Student> logOnUser = memberService.studentLogin("coblin", "1234");

        assertEquals(savedStudent,logOnUser);
    }

    @Test
    void 교수로그인() throws Exception {
        Optional<Professor> savedProfessor = memberService.joinProfessor("coblin", "1234", "jang", "jaeho", "email", (int)2004000000);

        Optional<Professor> logOnUser = memberService.professorLogin("coblin", "1234");

        assertEquals(savedProfessor,logOnUser);
    }

}