package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Enlist;
import Chungmu_ro.schoolManagement.domain.QA;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
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
        em.flush();
    }

    public Optional<QA> findByQid(Long qid){
        return Optional.ofNullable(em.find(QA.class, qid));

    }
    public List<QA> findByCid(Integer cid){
        return em.createQuery("select q from QA q join q.course c " +
                "on c.cid = :cid", QA.class)
                .setParameter("cid", cid)
                .getResultList();
    }
    public List<QA> findBySid(Integer sid){
        return em.createQuery("select q from QA q join q.student s " +
                "on s.sid = :sid", QA.class)
                .setParameter("sid", sid)
                .getResultList();
    }
    public List<QA> findByPid(Integer pid){
        return em.createQuery("select q from QA q join q.professor p " +
                "on p.pid = :pid", QA.class)
                .setParameter("pid", pid)
                .getResultList();
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
