package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Assignment;
import Chungmu_ro.schoolManagement.domain.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AttendanceRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Attendance attendance){
        em.persist(attendance);
    }

    public Optional<Attendance> findByAid(Long aid){
        return Optional.ofNullable(em.find(Attendance.class, aid));

    }
    public List<Attendance> findAll(){
        return em.createQuery("select a from Attendance a", Attendance.class).getResultList();
    }
    @Autowired
    public AttendanceRepository(EntityManager em) {
        this.em = em;
    }

}
