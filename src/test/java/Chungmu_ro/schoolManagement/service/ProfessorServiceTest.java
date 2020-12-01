package Chungmu_ro.schoolManagement.service;

import Chungmu_ro.schoolManagement.domain.Professor;
import Chungmu_ro.schoolManagement.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProfessorServiceTest {

    @Autowired
    private ProfessorService professorService;
    @Autowired
    private CourseRepository courseRepository;

    @Test
    void 로그인(){

        Professor professor = professorService.professorLogin("proid1", "propw1");
    }
}
