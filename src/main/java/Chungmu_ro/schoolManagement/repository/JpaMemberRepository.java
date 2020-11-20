package Chungmu_ro.schoolManagement.repository;

import Chungmu_ro.schoolManagement.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements  MemberRepository {

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        return null;
    }

    @Override
    public Optional<Member> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByName(String firstName, String lastName) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findBySid(int sid) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByPid(int pid) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }
}
