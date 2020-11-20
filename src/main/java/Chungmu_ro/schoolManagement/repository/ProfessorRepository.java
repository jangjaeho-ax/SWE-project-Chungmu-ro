package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Professor;
import Chungmu_ro.schoolManagement.domain.Student;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository {
    Professor save(Professor professor);
    Optional<Professor> findById(String id);
    Optional<Professor> findByName(String firstName,String lastName);
    Optional<Professor> findBySid(int sid);
    List<Professor> findAll();
}
