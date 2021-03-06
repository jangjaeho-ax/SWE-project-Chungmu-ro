package Chungmu_ro.schoolManagement.form;

import Chungmu_ro.schoolManagement.domain.Course;
import Chungmu_ro.schoolManagement.domain.HandIn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentForm {

    private Long aid;

    private Course course;

    private List<HandIn> handInList =new ArrayList<>();

    private String title;

    private LocalDateTime starDate;

    private LocalDateTime dueDate;

    private Integer perfectScore;


    private String description;
}
