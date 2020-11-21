package Chungmu_ro.schoolManagement.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "COURSE")
public class Course {
    @Id @Column(name = "CourseID")
    private Integer cid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="ProfessorID")
    private Professor professor;

    @Column(name = "CourseName")
    private String courseName;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private List<Enlist> enlistList;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private List<QA> qaList;

    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private  List<Assignment> assignmentList;

    public int getCid() {
        return cid;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        if(this.professor != null)
            this.professor.getCourseList().remove(this);
        this.professor = professor;
        professor.getCourseList().add(this);
    }

    public List<Enlist> getEnlistList() {
        return enlistList;
    }

    public void setEnlistList(List<Enlist> enlistList) {
        this.enlistList = enlistList;
    }

    public List<QA> getQaList() {
        return qaList;
    }

    public void setQaList(List<QA> qaList) {
        this.qaList = qaList;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    public Course() {
    }

    public Course(Integer cid) {
        this.cid = cid;
    }

    public Course(Integer cid, Professor professor, String courseName, List<Enlist> enlistList, List<QA> qaList, List<Assignment> assignmentList) {
        this.cid = cid;
        this.professor = professor;
        this.courseName = courseName;
        this.enlistList = enlistList;
        this.qaList = qaList;
        this.assignmentList = assignmentList;
    }
}
