package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentRepository {

//    @PersistenceContext
    private final EntityManager em;
    public void init(){

        em.createQuery("select s from Student s join fetch s.enlistList");
        em.createQuery("select s from Student s join fetch s.tutoringList");
        em.createQuery("select s from Student s join fetch s.qaList");
    }
    public void save(Student student){
        if(findBySid(student.getSid()).isPresent())
            em.merge(student);
        else
            em.persist(student);

    }

    public Optional<Student> findBySid(Integer sid){

        return Optional.ofNullable(em.find(Student.class, sid));

    }
    public List<Student> findAll(){
        return em.createQuery("select s from Student s",Student.class).getResultList();
    }

    public Optional<Student> findByAccountId(String AccountId){
        return  em.createQuery("select s from Student s join fetch s.enlistList" +
                " where s.AccountId =:AccountId",Student.class)
                .setParameter("AccountId",AccountId)
                .getResultList().stream().findAny();
    }
    public void delete(Student student){
        List<Enlist> enlists = student.getEnlistList();
        for(Enlist e: enlists) {
            List<Attendance> attendances = e.getAttendanceList();
            List<HandIn> handIns = e.getHandInList();
            for(Attendance a : attendances)
                em.remove(a);
            for(HandIn h : handIns){
                h.setAssignment(null);
                em.remove(h);
            }
            em.remove(e);
        }
        em.remove(student);
    }
//    public StudentRepository(EntityManager em) {
//        this.em = em;
//    }

}
