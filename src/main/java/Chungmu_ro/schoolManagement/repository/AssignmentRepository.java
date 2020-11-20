package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Assignment;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository {

    Assignment save(Assignment assignment);
    boolean update(Assignment assignment);
    boolean delete(Assignment assignment);

    List<Assignment> findBySid(int Sid);
    List<Assignment> findByPid(int Pid);
    List<Assignment> findByCid(int Cid);
    List<Assignment> findAll();


}
