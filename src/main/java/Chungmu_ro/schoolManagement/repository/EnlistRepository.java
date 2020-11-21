package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Course;
import Chungmu_ro.schoolManagement.domain.Enlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class EnlistRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Enlist enlist){
        em.persist(enlist);
    }

    public Optional<Enlist> findByEid(Long eid){
        return Optional.ofNullable(em.find(Enlist.class, eid));

    }
    public List<Enlist> findAll(){
        return em.createQuery("select a from Enlist a",Enlist.class).getResultList();
    }
    @Autowired
    public EnlistRepository(EntityManager em) {
        this.em = em;
    }

}

