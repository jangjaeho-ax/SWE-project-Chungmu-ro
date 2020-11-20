package Chungmu_ro.schoolManagement.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name ="TUTORING")
public class Tutoring {

    @Id @Column(name ="TutoringID") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProfessorID")
    private Professor professor;

    @Column(name = "StartTime")
    private LocalDateTime startTime;

    @Column(name ="EndTime")
    private LocalDateTime endTime;

    @Column(name ="Description")
    @Lob
    private String description;


    public Long getTid() {
        return tid;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {

        this.student = student;
        student.getTutoringList().add(this);
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
        professor.getTutoringList().add(this);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tutoring() {
    }

    public Tutoring(Long tid) {
        this.tid = tid;
    }

    public Tutoring(Long tid, Student student, Professor professor, LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.tid = tid;
        this.student = student;
        this.professor = professor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }
}
