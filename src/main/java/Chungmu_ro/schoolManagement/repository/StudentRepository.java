package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);
    Optional<Student> findById(String id);
    Optional<Student> findByName(String firstName,String lastName);
    Optional<Student> findBySid(int sid);
    List<Student> findAll();
}
