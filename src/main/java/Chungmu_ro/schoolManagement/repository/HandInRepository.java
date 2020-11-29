package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.HandIn;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HandInRepository {

//    @PersistenceContext
    private final EntityManager em;
    public void init(){
        em.createQuery("select h from HandIn h join fetch h.assignment");
        em.createQuery("select h from HandIn h join fetch h.enlist ");
    }
    public void save(HandIn handIn){
        if(handIn.getHid() ==null)
            em.persist(handIn);
        else
            em.merge(handIn);
    }

    public Optional<HandIn> findByHid(Long hid){
        return Optional.ofNullable(em.find(HandIn.class, hid));

    }
    public List<HandIn> findByEid(Long eid){
        return em.createQuery("select h from HandIn h join h.enlist e" +
                "on e.eid = :eid",HandIn.class)
                .setParameter("eid",eid)
                .getResultList();

    }
    public List<HandIn> findByAid(Long aid){
        return em.createQuery("select h from HandIn h join h.assignment a" +
                "on a.aid = :aid",HandIn.class)
                .setParameter("aid",aid)
                .getResultList();

    }
    public  Optional<HandIn> findByAidEid(Long aid,Long eid){
        return em.createQuery("select h from HandIn h join h.assignment a " +
                "on a.aid = :aid " +
                "where exists(select h from HandIn h join h.enlist e on e.eid = :eid)",HandIn.class)
                .setParameter("aid",aid)
                .setParameter("eid",eid)
                .getResultList().stream().findAny();

    }
    public List<HandIn> findAll(){
        return em.createQuery("select h from HandIn h",HandIn.class).getResultList();
    }
    public  void delete(HandIn handIn){
        handIn.setEnlist(null);
        handIn.setAssignment(null);
        em.remove(handIn);
    }
//    public GradeRepository(EntityManager em) {
//        this.em = em;
//    }

}
