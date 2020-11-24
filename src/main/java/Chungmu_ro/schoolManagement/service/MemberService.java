package Chungmu_ro.schoolManagement.service;

import Chungmu_ro.schoolManagement.domain.Professor;
import Chungmu_ro.schoolManagement.domain.Student;
import Chungmu_ro.schoolManagement.repository.ProfessorRepository;
import Chungmu_ro.schoolManagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    @Transactional
    public Optional<Student> joinStudent(String id, String pw, String firstName, String lastName,String email, int school_id, Byte year){
        createValidCheck(id, school_id, 0);
        Student student = new Student(id, pw, firstName, lastName, email, school_id, year);
        studentRepository.save(student);
        return Optional.ofNullable(student);
    }
    @Transactional
    public  Optional<Professor> joinProfessor(String id, String pw, String firstName, String lastName,String email, int school_id){
        createValidCheck(id, school_id, 1);
        Professor professor = new Professor(id, pw, firstName, lastName, email, school_id);
        professorRepository.save(professor);
        return Optional.ofNullable(professor);
    }

    private void createValidCheck(String id, int school_id,int mode) {
        studentRepository.findByAccountId(id).ifPresent(m -> {
            throw new IllegalStateException("중복된 계정명.");
        });
        switch (mode) {
            case 0:
                studentRepository.findBySid(school_id).ifPresent(m -> {
                    throw new IllegalStateException("중복된 학번.");
                });
                break;
            case 1:
                professorRepository.findByPid(school_id).ifPresent(m -> {
                    throw new IllegalStateException("중복된 교수번호.");
                });
        }
    }
    public Optional<Student> studentLogin(String id, String pw) {
        Student student = studentRepository.findByAccountId(id).get();
        if(student.getAccountPw().equals(pw) ==false) {
            throw new IllegalStateException("비밀번호가 틀림");
        }
        return Optional.ofNullable(student);
    }
    public Optional<Professor> professorLogin(String id, String pw) {
        Professor professor = professorRepository.findByAccountId(id).get();
        if(professor.getAccountPw().equals(pw) ==false) {
            throw new IllegalStateException("비밀번호가 틀림");
        }
        return Optional.ofNullable(professor);
    }

}
