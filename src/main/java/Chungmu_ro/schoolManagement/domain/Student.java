package Chungmu_ro.schoolManagement.domain;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STUDENT")
public class Student extends Member {

    @Id @Column(nullable = false, name = "StudentID")
    private Integer sid;

    @Column(name = "Year")
    private Byte year;

    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY)
    private List<Enlist> enlistList =new ArrayList<>();

    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY)
    private List<Tutoring> tutoringList=new ArrayList<>();

    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY)
    private List<QA> qaList=new ArrayList<>();

    public Integer getSid() {
        return sid;
    }

    public Byte getYear() {
        return year;
    }

    public void setYear(Byte year) {
        this.year = year;
    }

    public List<Enlist> getEnlistList() {
        return enlistList;
    }

    public void setEnlistList(List<Enlist> enlistList) {
        this.enlistList = enlistList;
    }

    public List<Tutoring> getTutoringList() {
        return tutoringList;
    }

    public void setTutoringList(List<Tutoring> tutoringList) {
        this.tutoringList = tutoringList;
    }

    public List<QA> getQaList() {
        return qaList;
    }

    public void setQaList(List<QA> qaList) {
        this.qaList = qaList;
    }

    public Student() {
    }

    public Student(Integer sid) {
        this.sid = sid;
    }

    public Student(String accountId, String accountPw, String fisrtName, String lastName, String email, Integer sid, Byte year) {
        super(accountId, accountPw, fisrtName, lastName, email);
        this.sid = sid;
        this.year = year;
    }

    public Student(String accountId, String accountPw, String fisrtName, String lastName, String email, Integer sid, Byte year, List<Enlist> enlistList, List<Tutoring> tutoringList, List<QA> qaList) {
        super(accountId, accountPw, fisrtName, lastName, email);
        this.sid = sid;
        this.year = year;
        this.enlistList = enlistList;
        this.tutoringList = tutoringList;
        this.qaList = qaList;
    }
}
