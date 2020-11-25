package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Assignment;
import Chungmu_ro.schoolManagement.domain.Course;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AssignmentRepositoryTest {


    @Autowired
    AssignmentRepository assignmentRepository;


    @Test
    void 저장테스트() throws Exception {

        Course course= new Course(1234);
        Assignment assignment =new Assignment(course,"test", LocalDateTime.now(),LocalDateTime.now(),100,"this is test");
        assignmentRepository.save(assignment);

    }

}