package Chungmu_ro.schoolManagement.service;

import Chungmu_ro.schoolManagement.domain.Member;
import Chungmu_ro.schoolManagement.domain.Professor;
import Chungmu_ro.schoolManagement.domain.Student;
import Chungmu_ro.schoolManagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<Student> joinStudent(String id, String pw, String firstName, String lastName,String email, int school_id, Byte year){
        createValidCheck(id, school_id, 0);
        Student student = new Student(id, pw, firstName, lastName, email, school_id, year);
        return Optional.ofNullable(student);
    }
    public  Optional<Professor> joinProfessor(String id, String pw, String firstName, String lastName,String email, int school_id){
        createValidCheck(id, school_id, 1);
        Professor professor = new Professor(id, pw, firstName, lastName, email, school_id);
        return Optional.ofNullable(professor);
    }

    private void createValidCheck(String id, int school_id,int mode) {
        memberRepository.findById(id).ifPresent(m -> {
            throw new IllegalStateException("중복된 계정명.");
        });
        switch (mode) {
            case 0:
                memberRepository.findBySid(school_id).ifPresent(m -> {
                    throw new IllegalStateException("중복된 학번.");
                });
                break;
            case 1:
                memberRepository.findByPid(school_id).ifPresent(m -> {
                    throw new IllegalStateException("중복된 교수번호.");
                });
        }
    }

    public Optional<Member> login(String id, String pw) {
        Member user = memberRepository.findById(id).get();
        if(user.getAccountPw().equals(pw) ==false) {
            throw new IllegalStateException("비밀번호가 틀림");
        }
        return Optional.ofNullable(user);
    }
}
