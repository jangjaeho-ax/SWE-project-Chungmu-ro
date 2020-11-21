package Chungmu_ro.schoolManagement.domain;

import org.springframework.web.bind.annotation.Mapping;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ASSIGNMENT")
public class Assignment {
    @Id @Column(name ="AssignmentID") @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long aid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CourseID")
    private Course course;

    @OneToMany(mappedBy = "assignment",fetch = FetchType.LAZY)//mappedBy는 가짜 맴버로 이 맴버로 수정을 할 수 없다.
    private List<Grade>  gradeList =new ArrayList<>();

    @Column(name = "AssignmentTitle")
    private String title;

    @Column(name ="StarDate")
    private LocalDateTime starDate;
    @Column(name ="DueDate")
    private LocalDateTime dueDate;

    @Column(name ="PerfectScore")
    private Integer perfectScore;

    @Lob
    @Column(name ="Description")
    private String description;

    public Long getAid() {
        return aid;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {

        if(this.course != null)
            this.course.getAssignmentList().remove(this);
        this.course = course;
        course.getAssignmentList().add(this);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStarDate() {
        return starDate;
    }

    public void setStarDate(LocalDateTime starDate) {
        this.starDate = starDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getPerfectScore() {
        return perfectScore;
    }

    public void setPerfectScore(Integer perfectScore) {
        this.perfectScore = perfectScore;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    public Assignment() {
    }

    public Assignment(Long aid) {
        this.aid = aid;
    }

    public Assignment(Course course, String title, LocalDateTime starDate, LocalDateTime dueDate, Integer perfectScore, String description) {
        this.course = course;
        this.title = title;
        this.starDate = starDate;
        this.dueDate = dueDate;
        this.perfectScore = perfectScore;
        this.description = description;
    }
}
