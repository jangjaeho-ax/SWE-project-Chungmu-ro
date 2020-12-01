package Chungmu_ro.schoolManagement.form;

import Chungmu_ro.schoolManagement.domain.AttendanceStatus;
import Chungmu_ro.schoolManagement.domain.Course;
import Chungmu_ro.schoolManagement.domain.Enlist;
import Chungmu_ro.schoolManagement.domain.HandIn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceForm {


    private Long Aid;


    private Enlist enlist;


    private LocalDateTime date;


    private AttendanceStatus attendanceStatus =AttendanceStatus.YET;
}
