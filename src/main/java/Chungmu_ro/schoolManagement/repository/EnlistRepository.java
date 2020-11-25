package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Attendance;
import Chungmu_ro.schoolManagement.domain.Course;
import Chungmu_ro.schoolManagement.domain.Enlist;
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
public class EnlistRepository {

//    @PersistenceContext
    private final EntityManager em;

    public void save(Enlist enlist){
        if(enlist.getEid() ==null)
            em.persist(enlist);
        else
            em.merge(enlist);
    }

    public Optional<Enlist> findByEid(Long eid){
        return Optional.ofNullable(em.find(Enlist.class, eid));

    }
    public List<Enlist> findAll(){
        return em.createQuery("select a from Enlist a",Enlist.class).getResultList();
    }
    public void delete(Enlist enlist){
        enlist.setCourse(null);
        enlist.setStudent(null);
        List<Attendance> attendances = enlist.getAttendanceList();
        List<HandIn> handIns = enlist.getHandInList();
        for(Attendance a :attendances)
            em.remove(a);
        for(HandIn h : handIns){
            h.setAssignment(null);
            em.remove(h);
        }
        em.remove(enlist);
    }
//    public EnlistRepository(EntityManager em) {
//        this.em = em;
//    }

}

