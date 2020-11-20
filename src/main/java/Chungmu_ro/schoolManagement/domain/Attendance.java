package Chungmu_ro.schoolManagement.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ATTENDANCE")
public class Attendance {

    @Id @Column(name = "AttendanceID") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Aid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="EnlistID")
    private Enlist enlist;

    @Column(name =  "Date")
    private LocalDateTime date;

    @Enumerated(EnumType.ORDINAL.STRING) @Column(name = "Status")
    private AttendanceStatus attendanceStatus;

    public Long getAid() {
        return Aid;
    }

    public Enlist getEnlist() {
        return enlist;
    }

    public void setEnlist(Enlist enlist) {
        this.enlist = enlist;
        enlist.getAttendanceList().add(this);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public Attendance() {
    }

    public Attendance(Long aid) {
        Aid = aid;
    }

    public Attendance(Long aid, Enlist enlist, LocalDateTime date, AttendanceStatus attendanceStatus) {
        Aid = aid;
        this.enlist = enlist;
        this.date = date;
        this.attendanceStatus = attendanceStatus;
    }
}
