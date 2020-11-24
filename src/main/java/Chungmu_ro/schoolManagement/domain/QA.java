package Chungmu_ro.schoolManagement.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name ="QA")
public class QA {

    @Id @Column(name ="QaID") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="StudentID")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="CourseID")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="ProfessorID")
    private Professor professor;

    @Lob
    @Column(name = "Question")
    private String question;

    @Lob
    @Column(name = "Answer")
    private String answer;

    @Column(name = "DateTime")
    private LocalDateTime dateTime;


    public Long getQid() {
        return qid;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setStudent(Student student) {
        if(this.student != null)
            this.student.getQaList().remove(this);
        this.student = student;
        student.getQaList().add(this);
    }

    public void setCourse(Course course) {
        if(this.course != null)
            this.course.getQaList().remove(this);
        this.course = course;
        course.getQaList().add(this);
    }

    public void setProfessor(Professor professor) {
        if(this.professor != null)
            this.professor.getQaList().remove(this);
        this.professor = professor;
        professor.getQaList().add(this);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public QA() {
    }

    public QA( Student student, Course course, Professor professor) {

        this.student = student;
        this.course = course;
        this.professor = professor;
    }
}
