package Chungmu_ro.schoolManagement.service;

import Chungmu_ro.schoolManagement.domain.*;
import Chungmu_ro.schoolManagement.exception.TimeOutException;
import Chungmu_ro.schoolManagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {

    //*** My가 붙은 함수는 자신 엔티티 즉 Student 엔티티를 입력값 받는다.
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final AssignmentRepository assignmentRepository;
    private final HandInRepository handInRepository;
    private final QARepository qaRepository;
    private final TutoringRepository tutoringRepository;
    private final EnlistRepository enlistRepository;


    public Optional<Student> studentLogin(String id, String pw) {
        Student student = studentRepository.findByAccountId(id).get();
        if(student ==null){
            throw new  IllegalStateException("아이디가 틀림");
        }
        if(student.getAccountPw().equals(pw) ==false) {
            throw new IllegalStateException("비밀번호가 틀림");
        }
        return Optional.ofNullable(student);
    }
    //***강의실 선택 함수***
    public List<Course> getCourseList(Student student){
        //자신이 수강하고 있는 전체 강의 정보를 가져오는 함수, 학생 엔티티 즉 유저 엔티티를 입력값으로 받는다.
        List<Course> result = new ArrayList<>();
        List<Enlist> enlists = student.getEnlistList();
        if (enlists.isEmpty())//비어있는 경우
            throw new NoSuchElementException("수강하고 있는 강의가 없습니다.");//예외 발생
        for (Enlist e : enlists) //Enlist 클래스를 통해 수강 강의 엔티티를 가져옴
            result.add(e.getCourse());
        return result;
    }


    //***과제 관련 함수***
    public List<HandIn> getMyHandInList(Student student) {
        //자신이 제출한 제출 정보를 가져오는 함수, 학생 엔티티 즉 유저 엔티티를 입력값으로 받는다.
        //제출 엔티티를 통해 저장된 점수를 얻을 수 있다.
        List<HandIn> result = new ArrayList<>();
        //Enlist를 통해 HandIn에 접근한다.
        List<Enlist> enlists = student.getEnlistList();
        if (enlists.isEmpty())//비어있는 경우
            throw new NoSuchElementException("수강하고 있는 강의가 없습니다.");//예외 발생
        for (Enlist e : enlists) {//Enlist 클래스를 통해 제출 엔티티를 가져옴
            List<HandIn> handIns = e.getHandInList();
            result.addAll(handIns);
        }
        return result;
    }
    public List<HandIn> getMyHandInList(Course course,Student student) {
        //해당 강좌에서 자신이 제출한 제출 정보를 가져오는 함수,
        //제출 엔티티를 통해 저장된 점수를 얻을 수 있다.
        List<HandIn> result = new ArrayList<>();
        //Enlist를 통해 HandIn에 접근한다.
        List<Enlist> enlists = student.getEnlistList();
        if (enlists.isEmpty())//비어있는 경우
            throw new NoSuchElementException("수강하고 있는 강의가 없습니다.");//예외 발생
        for (Enlist e : enlists) {//Enlist 클래스를 통해 제출 엔티티를 가져옴
            List<HandIn> handIns = e.getHandInList();
            result.addAll(handIns);
        }
        return result;
    }
    public List<Assignment> getAssignmentList(Course course){
        //과제 리스트를 가져오는 함수, 과제 엔티티를 입력으로 받음
        return course.getAssignmentList();
    }

    public void addAssignment(Course course,Student student,Assignment assignment,HandIn handIn){
        //과제 제출 함수, 과제 엔티티와 제출물 엔티티를 입력으로 받음
        Enlist enlist=null;
        if(assignment == null)
            throw new NoSuchElementException("과제가 없습니다!");
        if(handIn == null)
            throw new NoSuchElementException("제출물이 없습니다!");
        if(LocalDateTime.now().isBefore(assignment.getStarDate()))
            throw new TimeOutException("아직 과제 제출 시간 아닙니다.");
        else if(LocalDateTime.now().isAfter(assignment.getDueDate()))
            throw new TimeOutException("과제 제출 기간이 지났습니다.");
        List<Enlist> enlists = course.getEnlistList();
        for (Enlist e : enlists) {//Enlist 클래스를 통해 제출 엔티티를 가져옴
            if(e.getStudent().equals(student)) {
                enlist = e;
                break;
            }
        }
        if(enlist==null)
            throw new IllegalStateException("해당 강의를 수강하고 있지 않았습니다.");
        handIn.setEnlist(enlist);
        handIn.setAssignment(assignment);//assignment는 자동으로 추가가 됨
        enlistRepository.save(enlist);
        assignmentRepository.save(assignment);
        handInRepository.save(handIn);
    }


    //***QA 관련 함수***
    public List<QA> getMyQAList(Student student){
        //내 QA 리스트를 가져오는 함수, 학생 엔티티 즉 자기 자신 엔티티를 입력값으로 받는다.
        return student.getQaList();
    }
    public List<QA> getMyQAList(Course course,Student student){
        //내 QA 리스트를 가져오는 함수, 학생 엔티티 즉 자기 자신 엔티티를 입력값으로 받는다.
        List<QA> result = new ArrayList<>();
        List<Course> courses = getCourseList(student);
        if(courses.contains(course)==false)
            throw new IllegalStateException("해당 강의를 수강하고 있지 않았습니다.");
        List<QA> qas = student.getQaList();
        for(QA q : qas){
            if(q.getCourse().equals(course))
                result.add(q);
        }
        return result;
    }
    public List<QA> getQAList(Course course) {
        //내 QA 리스트를 가져오는 함수, 학생 엔티티 즉 자기 자신 엔티티를 입력값으로 받는다.
        return course.getQaList();
    }

    public void addQA(Course course,Student student, QA qa){
        //QA 수정 등록 함수, course
        Professor professor = course.getProfessor();
        if(professor ==null)
            throw new NoSuchElementException("강의를 담당하는 교수님이 없습니다.");
        qa.setStudent(student);
        qa.setCourse(course);
        qa.setProfessor(professor);
        qa.setDateTime(LocalDateTime.now());
        professorRepository.save(professor);
        qaRepository.save(qa);
    }

    //***출석 관련 함수
    public List<Attendance> getMyAttendanceList(Student student){
        //출석 리스트를 가져오는 함수,자신의 엔티티를 입력값으로 받는다.
        List<Attendance> result= new ArrayList<>();
        List<Enlist> enlists = student.getEnlistList();
        if (enlists.isEmpty())//비어있는 경우
            throw new NoSuchElementException("수강하고 있는 강의가 없습니다.");//예외 발생
        for (Enlist e : enlists) {//Enlist 클래스를 통해 출석 엔티티를 가져옴
            List<Attendance> attendances = e.getAttendanceList();
            result.addAll(attendances);
        }
        return result;
    }
    public List<Attendance> getMyAttendanceList(Course course, Student student){
        //출석 리스트를 가져오는 함수,강의 엔티티를 입력으로 받는다.
        List<Attendance> result= new ArrayList<>();
        List<Enlist> enlists = student.getEnlistList();
        if (enlists.isEmpty())//비어있는 경우
            throw new NoSuchElementException("수강하고 있는 강의가 없습니다.");//예외 발생
        for (Enlist e : enlists) {//Enlist 클래스를 통해 출석 엔티티를 가져옴
            if(e.getCourse().equals(course)) {
                result.addAll(e.getAttendanceList());
            }
        }
        if(result.isEmpty())
            throw new IllegalStateException("출석 정보가 비어 있습니다.");
        return result;
    }
    public List<Attendance> getAttendanceList(Course course){
        List<Attendance> result = new ArrayList<>();
        List<Enlist> enlists = course.getEnlistList();
        for (Enlist e : enlists) {//Enlist 클래스를 통해 제출 엔티티를 가져옴
            result.addAll(e.getAttendanceList());
        }
        if(result.isEmpty())
            throw new NoSuchElementException("출석정보가 비어있습니다.");
        return result;
    }
    //*** 튜토링 관련 함수***
    public List<Tutoring> getTutoringList(Course course){
        Professor professor = course.getProfessor();
        if(professor == null)
            throw new NoSuchElementException("강의를 담당하는 교수님이 없습니다.");
        return professor.getTutoringList();
    }
    public Optional<Tutoring> addTutoring(Course course, Tutoring tutoring){
        Student student = tutoring.getStudent();
        Professor professor = course.getProfessor();
        List<Tutoring> tutorings = professor.getTutoringList();
        List<Course> courses = getCourseList(student);
        LocalDateTime startTime = tutoring.getStartTime();
        LocalDateTime endTime = tutoring.getEndTime();
        if(courses.contains(course)==false)
            throw new IllegalStateException("해당 강의를 수강하고 있지 않았습니다.");

        for(Tutoring t: tutorings){
            if(startTime.isAfter(t.getStartTime())&&startTime.isBefore(t.getEndTime())) {
                return Optional.ofNullable(t);
            }
            else if(endTime.isAfter(t.getStartTime())&&endTime.isBefore(t.getEndTime())) {
                return Optional.ofNullable(t);
            }
        }

        studentRepository.save(student);
        professorRepository.save(professor);
        tutoringRepository.save(tutoring);
        return Optional.ofNullable(tutoring);
    }

}
