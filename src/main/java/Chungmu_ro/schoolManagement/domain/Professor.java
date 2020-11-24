package Chungmu_ro.schoolManagement.domain;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="PROFESSOR")
public class Professor extends Member {

    @Id
    @Column(name = "ProfessorID")
    private int pid;


    @OneToMany(mappedBy = "professor",fetch = FetchType.LAZY)
    private List<QA> qaList= new ArrayList<>();

    @OneToMany(mappedBy = "professor",fetch = FetchType.LAZY)
    private List<Course> courseList = new ArrayList<>();

    @OneToMany(mappedBy = "professor",fetch = FetchType.LAZY)
    private  List<Tutoring> tutoringList = new ArrayList<>();

    public int getPid() {
        return pid;
    }

    public List<QA> getQaList() {
        return qaList;
    }

    public void setQaList(List<QA> qaList) {
        this.qaList = qaList;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public List<Tutoring> getTutoringList() {
        return tutoringList;
    }

    public void setTutoringList(List<Tutoring> tutoringList) {
        this.tutoringList = tutoringList;
    }

    public Professor() {
    }

    public Professor(int pid) {
        this.pid = pid;
    }

    public Professor(String id, String passWord, String fisrtName, String email, String lastName, int pid) {
        super(id, passWord, fisrtName, lastName,email);
        this.pid = pid;
    }

    public Professor(String accountId, String accountPw, String fisrtName, String lastName, String email, int pid, List<QA> qaList, List<Course> courseList, List<Tutoring> tutoringList) {
        super(accountId, accountPw, fisrtName, lastName, email);
        this.pid = pid;
        this.qaList = qaList;
        this.courseList = courseList;
        this.tutoringList = tutoringList;
    }
}

