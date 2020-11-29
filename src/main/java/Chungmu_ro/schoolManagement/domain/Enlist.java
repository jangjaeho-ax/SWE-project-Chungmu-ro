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

    @OneToMany(fetch = FetchType.LAZY)
    private List<Attendance> attendanceList =new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<HandIn> handInList =new ArrayList<>();

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

    public List<HandIn> getHandInList() {
        return handInList;
    }

    public void setHandInList(List<HandIn> handInList) {
        this.handInList = handInList;
    }

    public Enlist() {
    }


    public Enlist(Student student, Course course, List<Attendance> attendanceList, List<HandIn> handInList) {
        this.student = student;
        this.course = course;
        this.attendanceList = attendanceList;
        this.handInList = handInList;
    }
}
