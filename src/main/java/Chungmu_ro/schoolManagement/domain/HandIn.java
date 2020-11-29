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

    @Column(name ="FileName")
    private String fileName;
    @Column(name ="FileOriginName")
    private String fileOriginName;
    @Column(name ="FileURL")
    private String fileURL;

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

    public HandIn() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileOriginName() {
        return fileOriginName;
    }

    public void setFileOriginName(String fileOriginName) {
        this.fileOriginName = fileOriginName;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public HandIn(Assignment assignment, Enlist enlist, Integer score, String fileName, String fileOriginName, String fileURL) {
        this.assignment = assignment;
        this.enlist = enlist;
        this.score = score;
        this.fileName = fileName;
        this.fileOriginName = fileOriginName;
        this.fileURL = fileURL;
    }
}
