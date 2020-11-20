package Chungmu_ro.schoolManagement.domain;

import javax.persistence.*;

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

    public QA() {
    }

    public QA(Long qid, Student student, Course course, Professor professor) {

        this.qid = qid;
        this.student = student;
        this.course = course;
        this.professor = professor;
    }
}
