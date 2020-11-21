package Chungmu_ro.schoolManagement.domain;

import org.hibernate.annotations.Columns;

import javax.persistence.*;

@Entity
@Table(name ="GRADE")
public class Grade {

    @Id @Column(name = "GradeID") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="AssignmentID")
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="EnlistID")
    private Enlist enlist;

    @Column(name ="Score")
    private Integer score;

    @Column(name ="Description")
    private String description;

    public Long getGid() {
        return gid;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        if(this.assignment != null)
            this.assignment.getGradeList().remove(this);
        this.assignment = assignment;
        assignment.getGradeList().add(this);
    }

    public Enlist getEnlist() {
        return enlist;
    }

    public void setEnlist(Enlist enlist) {
        if(this.enlist != null)
            this.enlist.getGradeList().remove(this);
        this.enlist = enlist;
        enlist.getGradeList().add(this);
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Grade() {
    }

    public Grade(Assignment assignment, Enlist enlist, Integer score, String description) {
        this.assignment = assignment;
        this.enlist = enlist;
        this.score = score;
        this.description = description;
    }
}
