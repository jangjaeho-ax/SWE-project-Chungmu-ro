package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Student;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaStudentRepository implements  StudentRepository{
    private EntityManager em;

    public JpaStudentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Student save(Student student) {


    }

    @Override
    public Optional<Student> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> findByName(String firstName, String lastName) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> findBySid(int sid) {
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        return null;
    }
}
