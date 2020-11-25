package Chungmu_ro.schoolManagement.controller;

import Chungmu_ro.schoolManagement.form.LoginForm;
import Chungmu_ro.schoolManagement.service.ProfessorService;
import Chungmu_ro.schoolManagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping(value = "/professor/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "professor/loginForm";
    }
    @PutMapping(value = "/professor/login")
    public String login(@Valid LoginForm loginForm, BindingResult result) {
        if(result.hasErrors()){
            return "professor/loginForm";
        }

        String id = loginForm.getId();
        String passWord = loginForm.getPassWord();
        professorService.professorLogin(id,passWord);
        return "redirect:/";
    }
}
