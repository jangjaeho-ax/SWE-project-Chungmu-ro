package Chungmu_ro.schoolManagement.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "ATTENDANCE")
public class Attendance {

    @Id @Column(name = "AttendanceID") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Aid;

    @ManyToOne
    @JoinColumn(name ="EnlistID")
    private Enlist enlist;

    @Column(name =  "Date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING) @Column(name = "Status")
    private AttendanceStatus attendanceStatus =AttendanceStatus.YET;

    public Long getAid() {
        return Aid;
    }

    public Enlist getEnlist() {
        return enlist;
    }

    public void setEnlist(Enlist enlist) {
        if(this.enlist != null)
            this.enlist.getAttendanceList().remove(this);
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

    public Attendance( Enlist enlist, LocalDateTime date, AttendanceStatus attendanceStatus) {

        this.enlist = enlist;
        this.date = date;
        this.attendanceStatus = attendanceStatus;
    }
}
