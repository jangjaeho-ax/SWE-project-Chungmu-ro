package Chungmu_ro.schoolManagement.form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class HandInForm {

    private Long hid;

    private Long aid;

    private Long eid;

    private Integer score;


    private String fileName;

    private String fileOriginName;

    private String fileURL;
}
