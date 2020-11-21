package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Student student){
        em.persist(student);
    }

    public Optional<Student> findBySid(Integer sid){
        return Optional.ofNullable(em.find(Student.class, sid));

    }
    public List<Student> findAll(){
        return em.createQuery("select s from Student s",Student.class).getResultList();
    }

    public Optional<Student> findByAccountId(String AccountId){
        return  em.createQuery("select s from Student s where s.AccountId =:AccountId",Student.class)
                .setParameter("AccountId",AccountId)
                .getResultList().stream().findAny();
    }
    @Autowired
    public StudentRepository(EntityManager em) {
        this.em = em;
    }
}
