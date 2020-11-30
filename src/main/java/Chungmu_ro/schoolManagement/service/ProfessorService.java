package Chungmu_ro.schoolManagement.service;

import Chungmu_ro.schoolManagement.domain.*;
import Chungmu_ro.schoolManagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Store;
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
public class ProfessorService {

    private StudentRepository studentRepository;
    private ProfessorRepository professorRepository;
    private AssignmentRepository assignmentRepository;
    private AttendanceRepository attendanceRepository;
    private HandInRepository handInRepository;
    private QARepository qaRepository;
    private TutoringRepository tutoringRepository;
    private EnlistRepository enlistRepository;
    private CourseRepository courseRepository;

    public Professor professorLogin(String id, String pw) {
        Optional<Professor> professor = professorRepository.findByAccountId(id);
        if(!professor.isPresent()){
            throw new  IllegalStateException("아이디가 틀림");
        }
        else if(!professor.get().getAccountPw().equals(pw)) {
            throw new IllegalStateException("비밀번호가 틀림");
        }
        return professor.get();
    }

    @Transactional
    public List<Course> getCourseList(Professor professor) throws Exception{
        //자신이 수강하고 있는 전체 강의 정보를 가져오는 함수, 학생 엔티티 즉 유저 엔티티를 입력값으로 받는다.
        // studentRepository.init();
        List<Course> courses = courseRepository.findByPid(professor.getPid());
        if (courses.isEmpty())//비어있는 경우
            throw new NoSuchElementException("담당하고 있는 강의가 없습니다.");//예외 발생
        return courses;
    }

    public Course findCourse(Integer cid) throws Exception{
        Course course = courseRepository.findByCid(cid).get();
        if(course ==null)
            throw new NoSuchElementException("선택한 강의가 없습니다.");//예외 발생
        return course;
    }

    //***학생 관련 함수***
    public List<Student> getStudentList(Course course){
        List<Student> result =new ArrayList<>();
        List<Enlist> enlists = course.getEnlistList();
        if (enlists.isEmpty())//비어있는 경우
            throw new NoSuchElementException("강의를 수강하고 있는 학생이 없습니다.");//예외 발생
        for (Enlist e : enlists) {//Enlist 클래스를 통해 학생 엔티티를 가져옴
            result.add(e.getStudent());
        }
        return result;
    }
    //***과제 관련 함수***
    public List<Assignment> getAssignmentList(Course course) throws  Exception{
        //과제 리스트를 가져오는 함수, 과제 엔티티를 입력으로 받음
        if(course == null)
            throw new NoSuchElementException("선택한 강의가 없습니다.");//예외 발생
        //List<Assignment> assignmentList = course.getAssignmentList();
        List<Assignment> assignmentList = assignmentRepository.findByCid(course.getCid());
        if(assignmentList.isEmpty())
            throw new NoSuchElementException("선택한 과제가 없습니다.");//예외 발생
        return assignmentList;
    }
    public Assignment findAssignment(Long aid) throws  Exception {
        Optional<Assignment> assignment = assignmentRepository.findByAid(aid);
        if(!assignment.isPresent())
            throw new NoSuchElementException("선택한 과제가 없습니다.");//예외 발생
        return assignment.get();
    }
    public void addAssignment(Course course,Assignment assignment){
        //과제 추가 수정 함수
        List<HandIn> handIns = assignment.getHandInList();
        List<Enlist> enlists = course.getEnlistList();
        if(handIns.isEmpty()){
            for(Enlist e :enlists){
                HandIn newHandIn =new HandIn(assignment,e,0,"","","");
                handIns.add(newHandIn);
            }
        }
        if(assignment == null)
            throw new NoSuchElementException("추가하고자 하는 과제가 없습니다.");
        assignmentRepository.save(assignment);
    }
    public List<HandIn> findHandInList(Long aid) {
        //해당 강좌에서 해당 과제의 모든 학생의 제출 정보를 가져오는 함수
        //제출 엔티티를 통해 저장된 점수를 얻을 수 있다.
        List<HandIn> handInList = handInRepository.findByAid(aid);
        if(handInList.isEmpty())
            throw new NoSuchElementException("제출 정보가 없습니다.");
        return handInList;
    }
    public void subAssignment(Assignment assignment){
        assignmentRepository.delete(assignment);
    }
    //***성적(과제물) 관련 함수***
    public List<HandIn> getHandInList(Assignment assignment) {
        //해당 강좌에서 해당 과제의 모든 학생의 제출 정보를 가져오는 함수
        //제출 엔티티를 통해 저장된 점수를 얻을 수 있다.
        List<HandIn> result = new ArrayList<>();
        //Enlist를 통해 HandIn에 접근한다.
        assignment.getHandInList();
        return result;
    }
    public void addHandInList(HandIn handIn){
        if(handIn == null)
            throw new NoSuchElementException("해당 과제가 없습니다.");
        handInRepository.save(handIn);
    }

    //***출석 관련 함수***
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
    public List<Attendance> getAttendanceList(Course course,Student student){
        List<Attendance> result = new ArrayList<>();
        List<Enlist> enlists = student.getEnlistList();
        for (Enlist e : enlists) {//Enlist 클래스를 통해 제출 엔티티를 가져옴
            if(e.getCourse().equals(course))
                result.addAll(e.getAttendanceList());
        }
        if(result.isEmpty())
            throw new NoSuchElementException("출석정보가 비어있습니다.");
        return result;
    }
    public void addAttendance(Attendance attendance){
        if(attendance == null)
            throw new NoSuchElementException("해당 출석정보가 없습니다.");
        attendanceRepository.save(attendance);
    }
    //***튜토링 관련 함수***
    public List<Tutoring> getTutoringList(Professor professor){
        if(professor == null)
            throw new NoSuchElementException("강의를 담당하는 교수님이 없습니다.");
        return professor.getTutoringList();
    }
    public Optional<Tutoring> addTutoring(Professor professor,Tutoring tutoring){
        //튜터링 정보 수정 함수
        List<Tutoring> tutorings = professor.getTutoringList();
        Student student = tutoring.getStudent();
        LocalDateTime startTime = tutoring.getStartTime();
        LocalDateTime endTime = tutoring.getEndTime();
        if(!tutorings.contains(tutoring))
            throw new IllegalStateException("튜터링의 교수 정보와 입력된 교수 정보가 다릅니다");
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
    public void subTutoring(Tutoring tutoring){
        tutoringRepository.delete(tutoring);
    }
}
