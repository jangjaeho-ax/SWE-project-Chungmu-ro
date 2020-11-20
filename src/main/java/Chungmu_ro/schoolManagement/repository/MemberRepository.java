package Chungmu_ro.schoolManagement.repository;


import Chungmu_ro.schoolManagement.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {


    Member save(Member member);
    Optional<Member> findById(String id);
    Optional<Member> findByName(String firstName,String lastName);
    Optional<Member> findBySid(int sid);
    Optional<Member> findByPid(int pid);
    List<Member> findAll();
}
