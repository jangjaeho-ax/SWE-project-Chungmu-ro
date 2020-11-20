package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Assignment;
import Chungmu_ro.schoolManagement.domain.Attendance;

import java.util.List;

public interface AttendanceRepository {
    Attendance save(Attendance attendance);
    boolean update(Attendance attendance);
    boolean delete(Attendance attendance);

    List<Attendance> findBySid(int Sid);
    List<Attendance> findByPid(int Pid);
    List<Attendance> findByCid(int Cid);
    List<Attendance> findAll();

}
