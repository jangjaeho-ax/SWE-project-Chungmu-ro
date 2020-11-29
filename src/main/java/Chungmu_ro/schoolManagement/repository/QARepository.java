package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.QA;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QARepository {

//    @PersistenceContext
    private final EntityManager em;
    public void init(){
        em.createQuery("select q from QA q join fetch q.student");
        em.createQuery("select q from QA q join fetch q.course");
        em.createQuery("select q from QA q join fetch q.professor");
    }
    public void save(QA qa){
        if(qa.getQid()==null)
            em.persist(qa);
        else
            em.merge(qa);
    }

    public Optional<QA> findByGid(Long qid){
        return Optional.ofNullable(em.find(QA.class, qid));

    }
    public List<QA> findAll(){
        return em.createQuery("select q from QA q",QA.class).getResultList();
    }
    public void delete(QA qa){
        qa.setStudent(null);
        qa.setProfessor(null);
        qa.setCourse(null);
        em.remove(qa);
    }
//    public QARepository(EntityManager em) {
//        this.em = em;
//    }

}
