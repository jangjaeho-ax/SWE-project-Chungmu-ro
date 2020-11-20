package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Assignment;
import Chungmu_ro.schoolManagement.domain.Course;

import java.util.List;

public interface CourseRepository {
    Course save(Course course);
    boolean update(Course course);
    boolean delete(Course course);

    List<Course> findBySid(int Sid);
    List<Course> findByPid(int Pid);
    List<Course> findByCid(int Cid);
    List<Course> findAll();

}
