package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Grade;
import Chungmu_ro.schoolManagement.domain.QA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class QARepository {

    @PersistenceContext
    private EntityManager em;

    public void save(QA qa){
        em.persist(qa);
    }

    public Optional<QA> findByGid(Long qid){
        return Optional.ofNullable(em.find(QA.class, qid));

    }
    public List<QA> findAll(){
        return em.createQuery("select q from QA q",QA.class).getResultList();
    }
    @Autowired
    public QARepository(EntityManager em) {
        this.em = em;
    }

}
