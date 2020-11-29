package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CourseRepository {

    private final EntityManager em;
    public void init(){

        em.createQuery("select c from Course c join fetch c.professor ");
        em.createQuery("select c from Course c join fetch c.enlistList ");
        em.createQuery("select c from Course c join fetch c.qaList ");
        em.createQuery("select c from Course c join fetch c.assignmentList ");
    }
    public void save(Course course){
        if(findByCid(course.getCid()).isPresent())
            em.merge(course);
        else
            em.persist(course);
        em.flush();
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

