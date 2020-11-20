package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Assignment;
import Chungmu_ro.schoolManagement.domain.Grade;

import java.util.List;

public interface GradeRepository {
    Grade save(Grade grade);
    boolean update(Grade grade);
    boolean delete(Grade grade);

    List<Grade> findBySid(int Sid);
    List<Grade> findByPid(int Pid);
    List<Grade> findByCid(int Cid);
    List<Grade> findAll();

}
