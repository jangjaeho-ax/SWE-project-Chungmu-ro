package Chungmu_ro.schoolManagement.form;

import Chungmu_ro.schoolManagement.domain.Course;
import Chungmu_ro.schoolManagement.domain.Professor;
import Chungmu_ro.schoolManagement.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class QaForm {


    private Long qid;

    private Student student;

    private Course course;

    private Professor professor;

    private String question;

    private String answer;

    private LocalDateTime dateTime;
}
