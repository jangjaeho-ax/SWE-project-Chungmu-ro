package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.QA;
import Chungmu_ro.schoolManagement.domain.Tutoring;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TutoringRepository {

//    @PersistenceContext
    private final EntityManager em;

    public void save(Tutoring tutoring){
        if(tutoring.getTid()==null)
            em.persist(tutoring);
        else
            em.merge(tutoring);
    }

    public Optional<Tutoring> findByGid(Long tid){
        return Optional.ofNullable(em.find(Tutoring.class, tid));

    }
    public void delete(Tutoring tutoring){
        tutoring.setStudent(null);
        tutoring.setProfessor(null);
        em.remove(tutoring);
    }
    public List<Tutoring> findAll(){
        return em.createQuery("select t from Tutoring t",Tutoring.class).getResultList();
    }

//    public TutoringRepository(EntityManager em) {
//        this.em = em;
//    }

}
