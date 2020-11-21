package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.QA;
import Chungmu_ro.schoolManagement.domain.Tutoring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TutoringRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Tutoring tutoring){
        em.persist(tutoring);
    }

    public Optional<Tutoring> findByGid(Long tid){
        return Optional.ofNullable(em.find(Tutoring.class, tid));

    }
    public List<Tutoring> findAll(){
        return em.createQuery("select t from Tutoring t",Tutoring.class).getResultList();
    }
    @Autowired
    public TutoringRepository(EntityManager em) {
        this.em = em;
    }

}
