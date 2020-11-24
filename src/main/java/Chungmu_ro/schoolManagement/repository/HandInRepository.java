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

    public void save(HandIn handIn){
        if(handIn.getHid() ==null)
            em.persist(handIn);
        else
            em.merge(handIn);
    }

    public Optional<HandIn> findByHid(Long hid){
        return Optional.ofNullable(em.find(HandIn.class, hid));

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
