package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Assignment;
import Chungmu_ro.schoolManagement.domain.Attendance;
import Chungmu_ro.schoolManagement.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseRepository {

    private final EntityManager em;

    public void save(Course course){
        if(findByCid(course.getCid()).isPresent())
            em.merge(course);
        else
            em.persist(course);
    }

    public Optional<Course> findByCid(Integer cid){
        return Optional.ofNullable(em.find(Course.class, cid));

    }
    public List<Course> findAll(){
        return em.createQuery("select a from Course a",Course.class).getResultList();
    }

//    public CourseRepository(EntityManager em) {
//        this.em = em;
//    }

}

