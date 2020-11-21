package Chungmu_ro.schoolManagement.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ENLIST")
public class Enlist {

    @Id @Column(name ="EnlistID") @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long eid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="StudentID")
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CourseID")
    private Course course;

    @OneToMany(mappedBy = "enlist",fetch = FetchType.LAZY)
    private List<Attendance> attendanceList =new ArrayList<>();

    @OneToMany(mappedBy = "enlist",fetch = FetchType.LAZY)
    private List<Grade> gradeList =new ArrayList<>();

    public Long getEid() {
        return eid;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        if(this.student != null)
            this.student.getEnlistList().remove(this);
        this.student = student;
        student.getEnlistList().add(this);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if(this.course != null)
            this.course.getEnlistList().remove(this);
        this.course = course;
        course.getEnlistList().add(this);
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    public Enlist() {
    }

    public Enlist(Long eid) {
        this.eid = eid;
    }

    public Enlist(Long eid, Student student, Course course, List<Attendance> attendanceList, List<Grade> gradeList) {
        this.eid = eid;
        this.student = student;
        this.course = course;
        this.attendanceList = attendanceList;
        this.gradeList = gradeList;
    }
}
