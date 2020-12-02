package Chungmu_ro.schoolManagement.service;

import Chungmu_ro.schoolManagement.domain.*;
import Chungmu_ro.schoolManagement.form.AssignmentForm;
import Chungmu_ro.schoolManagement.form.AttendanceForm;
import Chungmu_ro.schoolManagement.form.QaForm;
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
public class ProfessorService {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final AssignmentRepository assignmentRepository;
    private final AttendanceRepository attendanceRepository;
    private final HandInRepository handInRepository;
    private final QARepository qaRepository;
    private final TutoringRepository tutoringRepository;
    private final EnlistRepository enlistRepository;
    private final CourseRepository courseRepository;

    public Professor professorLogin(String id, String pw) {
        Optional<Professor> professor = professorRepository.findByAccountId(id);
        if(!professor.isPresent()){
            throw new IllegalStateException("아이디가 틀림");
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
        Optional<Course> course = courseRepository.findByCid(cid);
        if(!course.isPresent())
            throw new NoSuchElementException("선택한 강의가 없습니다.");//예외 발생
        return course.get();
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
    @Transactional
    public Assignment setAssignment(Course course, Assignment assignment, AssignmentForm assignmentForm){
        //과제 추가 수정 함수

        List<Enlist> enlists = enlistRepository.findByCid(course.getCid());
        List<HandIn> handIns = handInRepository.findByAid(assignment.getAid());
        if(enlists.isEmpty()){
            throw new NoSuchElementException("등록된 수강생이 없습니다.");
        }

        if(handIns.isEmpty()){
            for(Enlist e :enlists){
                HandIn newHandIn =new HandIn(assignment,e,0,"","","");
                handIns.add(newHandIn);
            }
        }
        if(assignment == null)
            throw new NoSuchElementException("추가하고자 하는 과제가 없습니다.");
        assignment.setTitle(assignmentForm.getTitle());
        assignment.setPerfectScore(assignmentForm.getPerfectScore());
        assignment.setStarDate(assignmentForm.getStarDate());
        assignment.setDueDate(assignmentForm.getDueDate());
        assignment.setDescription(assignmentForm.getDescription());
        assignmentRepository.save(assignment);
        return assignment;
    }
    @Transactional
    public Assignment addAssignment(Course course, AssignmentForm assignmentForm){
        //과제 추가 수정 함수

        Assignment assignment = new Assignment(course,assignmentForm.getTitle(), assignmentForm.getStarDate(),
                assignmentForm.getDueDate(),assignmentForm.getPerfectScore(),assignmentForm.getDescription());
        List<Enlist> enlists = enlistRepository.findByCid(course.getCid());
        List<HandIn> handIns = new ArrayList<>();
        if(enlists.isEmpty()){
            throw new NoSuchElementException("등록된 수강생이 없습니다.");
        }

        for(Enlist e :enlists){
                HandIn newHandIn =new HandIn(assignment,e,0,"","","");
                handInRepository.save(newHandIn);
                handIns.add(newHandIn);
        }
        assignment.setHandInList(handIns);
        assignmentRepository.save(assignment);
        return assignment;
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
    @Transactional
    public void addHandInList(HandIn handIn){
        if(handIn == null)
            throw new NoSuchElementException("해당 과제가 없습니다.");
        handInRepository.save(handIn);
    }
    public List<HandIn> findHandInList(Long aid) {
        //해당 강좌에서 해당 과제의 모든 학생의 제출 정보를 가져오는 함수
        //제출 엔티티를 통해 저장된 점수를 얻을 수 있다.
        List<HandIn> handInList = handInRepository.findByAid(aid);
        if(handInList.isEmpty())
            throw new NoSuchElementException("제출 정보가 없습니다.");
        return handInList;
    }
    public HandIn findHandIn(Long hid) throws  Exception{
        Optional<HandIn> handIn = handInRepository.findByHid(hid);
        if(!handIn.isPresent())
            throw new NoSuchElementException("제출 정보가 없습니다.");//예외 발생
        return handIn.get();
    }
    @Transactional
    public HandIn setHandIn(HandIn handIn) throws Exception{
        if(! handInRepository.findByHid(handIn.getHid()).isPresent())
            throw new NoSuchElementException("제출 정보가 없습니다.");//예외 발생
        handInRepository.save(handIn);
        return handIn;
    }
    //***출석 관련 함수***
    public List<Attendance> getAttendanceList(Course course){
        List<Attendance> result = new ArrayList<>();
        List<Enlist> enlists = enlistRepository.findByCid(course.getCid());
        for (Enlist e : enlists) {//Enlist 클래스를 통해 제출 엔티티를 가져옴
            List<Attendance> attendances = attendanceRepository.findByEid(e.getEid());
            result.addAll(attendances);
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
    public Attendance findAttendance(Long aid){
        Optional<Attendance> attendance = attendanceRepository.findByAid(aid);
        if(!attendance.isPresent())
            throw new NoSuchElementException("출석정보가 비어있습니다.");
        return attendance.get();
    }
    @Transactional
    public Attendance updateAttendance(Long aid, AttendanceForm attendanceForm){
        Optional<Attendance> attendance = attendanceRepository.findByAid(aid);
        if(!attendance.isPresent())
            throw new NoSuchElementException("업데이트 하고자 하는 출석정보가 없습니다.");
        attendance.get().setAttendanceStatus(attendanceForm.getAttendanceStatus());
        attendanceRepository.save(attendance.get());
        return attendance.get();
    }
    @Transactional
    public void addAttendance(Attendance attendance){
        if(attendance == null)
            throw new NoSuchElementException("해당 출석정보가 없습니다.");
        attendanceRepository.save(attendance);
    }
    public List<QA> getQAList(Integer cid) throws  Exception {
        //내 QA 리스트를 가져오는 함수, 학생 엔티티 즉 자기 자신 엔티티를 입력값으로 받는다.
        Optional<Course> course = courseRepository.findByCid(cid);
        if(!course.isPresent())
            throw new NoSuchElementException("선택한 강의가 없습니다.");

        List<QA> qaList = qaRepository.findByCid(cid);
        if(qaList.isEmpty())
            throw new NoSuchElementException("Qa 리스트가 없습니다.");
        return qaList;
    }
    public QA findQA(Long qid) throws  Exception{
        Optional<QA> qa = qaRepository.findByQid(qid);
        if(!qa.isPresent())
            throw new NoSuchElementException("선택한 QA 정보가 없습니다.");
        return qa.get();
    }
    @Transactional
    public QA updateQA(Long qid, QaForm qaForm) throws  Exception{
        QA qa = findQA(qid);
        qa.setAnswer(qaForm.getAnswer());
        qa.setDateTime(LocalDateTime.now());
        qaRepository.save(qa);
        return qa;
    }
    //***튜토링 관련 함수***
    public List<Tutoring> getTutoringList(Professor professor){
        if(professor == null)
            throw new NoSuchElementException("강의를 담당하는 교수님이 없습니다.");
        return professor.getTutoringList();
    }
    @Transactional
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
