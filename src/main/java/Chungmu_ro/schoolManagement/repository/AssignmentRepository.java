package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Assignment;
import Chungmu_ro.schoolManagement.domain.HandIn;
import Chungmu_ro.schoolManagement.domain.Student;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceProperty;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AssignmentRepository {


    private final EntityManager em;

    public void save(Assignment assignment){
        if(assignment.getAid() ==null)
            em.persist(assignment);
        else
            em.merge(assignment);
    }

    public Optional<Assignment> findByAid(Long aid){
        return Optional.ofNullable(em.find(Assignment.class, aid));

    }

    public List<Assignment> findAll(){
        return em.createQuery("select a from Assignment a",Assignment.class).getResultList();
    }
    public void delete(Assignment assignment){
        assignment.setCourse(null);
        List<HandIn> handIns = assignment.getHandInList();
        for(HandIn h : handIns) {
            h.setEnlist(null);
            em.remove(h);
        }
        em.remove(assignment);
    }
}
