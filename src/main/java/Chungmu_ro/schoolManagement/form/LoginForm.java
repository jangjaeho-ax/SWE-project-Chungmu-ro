package Chungmu_ro.schoolManagement.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter @Setter
public class LoginForm {

    @NotEmpty(message = "아이디는 필수적입니다.")
    private String id;

    @NotEmpty(message = "비밀번호는 필수적입니다.")
    private String passWord;
}
