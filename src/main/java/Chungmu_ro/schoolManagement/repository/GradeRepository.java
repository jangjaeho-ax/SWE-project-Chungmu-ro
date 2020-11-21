package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Assignment;
import Chungmu_ro.schoolManagement.domain.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class GradeRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Grade assignment){
        em.persist(assignment);
    }

    public Optional<Grade> findByGid(Long gid){
        return Optional.ofNullable(em.find(Grade.class, gid));

    }
    public List<Grade> findAll(){
        return em.createQuery("select g from Grade g",Grade.class).getResultList();
    }
    @Autowired
    public GradeRepository(EntityManager em) {
        this.em = em;
    }

}
