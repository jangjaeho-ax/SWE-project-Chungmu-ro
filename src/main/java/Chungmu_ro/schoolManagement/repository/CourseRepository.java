package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Course;
import Chungmu_ro.schoolManagement.domain.Professor;
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
    }

    public Optional<Course> findByCid(Integer cid){
        return Optional.ofNullable(em.find(Course.class, cid));
    }
    public List<Course> findByPid(Integer pid){
        return em.createQuery("select c from Course c join c.professor p " +
                "on p.pid = :pid", Course.class)
                .setParameter("pid", pid)
                .getResultList();
    }
    public Professor getProfessor(Integer cid) {
        Course firstResult = (Course)em.createQuery("select c from Course c " +
                "join c.professor p on c.cid =:cid")
                .setParameter("cid", cid).getResultStream().findAny().get();
        return firstResult.getProfessor();
    }
    public List<Course> findAll(){
        return em.createQuery("select a from Course a",Course.class).getResultList();
    }

//    public CourseRepository(EntityManager em) {
//        this.em = em;
//    }

}

