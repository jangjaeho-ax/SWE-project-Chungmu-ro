package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Assignment;
import Chungmu_ro.schoolManagement.domain.Student;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AssignmentRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Assignment assignment){
        em.persist(assignment);
    }

    public Optional<Assignment> findByAid(Long aid){
        return Optional.ofNullable(em.find(Assignment.class, aid));

    }
    public List<Assignment> findAll(){
        return em.createQuery("select a from Assignment a",Assignment.class).getResultList();
    }
    @Autowired
    public AssignmentRepository(EntityManager em) {
        this.em = em;
    }

}
