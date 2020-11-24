package Chungmu_ro.schoolManagement.domain;

import org.hibernate.annotations.Columns;

import javax.persistence.*;

//***해당 과제에 대한 제출 정보와 점수를 가지고 있는 엔티티***
@Entity
@Table(name ="HANDIN")
public class HandIn {

    @Id @Column(name = "HandInID") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="AssignmentID")
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="EnlistID")
    private Enlist enlist;

    @Column(name ="Score")
    private Integer score;

    @Lob
    @Column(name ="Description")
    private String description;

    public Long getHid() {
        return hid;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        if(this.assignment != null)
            this.assignment.getHandInList().remove(this);
        this.assignment = assignment;
        assignment.getHandInList().add(this);
    }

    public Enlist getEnlist() {
        return enlist;
    }

    public void setEnlist(Enlist enlist) {
        if(this.enlist != null)
            this.enlist.getHandInList().remove(this);
        this.enlist = enlist;
        enlist.getHandInList().add(this);
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

    public HandIn() {
    }

    public HandIn(Assignment assignment, Enlist enlist, Integer score, String description) {
        this.assignment = assignment;
        this.enlist = enlist;
        this.score = score;
        this.description = description;
    }
}
