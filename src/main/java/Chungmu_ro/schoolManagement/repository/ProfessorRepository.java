package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Professor;
import Chungmu_ro.schoolManagement.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProfessorRepository {

    private final EntityManager em;


    public void save(Professor professor){
        if(findByPid(professor.getPid()).isPresent())
            em.merge(professor);
        else
            em.persist(professor);

    }

    public Optional<Professor> findByPid(Integer pid){
        return Optional.ofNullable(em.find(Professor.class, pid));

    }
    public List<Professor> findAll(){
        return em.createQuery("select p from Professor p",Professor.class).getResultList();
    }

    public Optional<Professor> findByAccountId(String AccountId){
        return  em.createQuery("select p from Professor p join fetch p.courseList " +
                "where p.AccountId =:AccountId",Professor.class)
                .setParameter("AccountId",AccountId)
                .getResultList().stream().findAny();
    }


//    public ProfessorRepository(EntityManager em) {
//        this.em = em;
//    }
}
