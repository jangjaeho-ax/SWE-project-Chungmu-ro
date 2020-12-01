package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Assignment;
import Chungmu_ro.schoolManagement.domain.Attendance;
import Chungmu_ro.schoolManagement.domain.Enlist;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AttendanceRepository {

//    @PersistenceContext
    private final EntityManager em;
    public void init(){
        em.createQuery("select a from Attendance a join fetch  a.enlist ");
    }
    public void save(Attendance attendance){
        if(attendance.getAid() == null)
            em.persist(attendance);
        else
            em.merge(attendance);

    }

    public Optional<Attendance> findByAid(Long aid){
        return Optional.ofNullable(em.find(Attendance.class, aid));
    }
    public List<Attendance> findAll(){
        return em.createQuery("select a from Attendance a", Attendance.class).getResultList();
    }
    public List<Attendance> findByEid(Long eid){
        return em.createQuery("select a from Attendance a join a.enlist e " +
                "on e.eid = :eid", Attendance.class)
                .setParameter("eid", eid)
                .getResultList();
    }

    public void delete(Attendance attendance){
        attendance.setEnlist(null);
        em.remove(attendance);
    }
//    public AttendanceRepository(EntityManager em) {
//        this.em = em;
//    }

}
