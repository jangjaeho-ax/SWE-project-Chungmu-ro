package Chungmu_ro.schoolManagement.controller;

import Chungmu_ro.schoolManagement.domain.*;
import Chungmu_ro.schoolManagement.form.LoginForm;
import Chungmu_ro.schoolManagement.service.ProfessorService;
import Chungmu_ro.schoolManagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping(value = "/professor/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "professor/loginForm";
    }
    @PostMapping(value = "/professor/login")
    public String login(@Valid LoginForm loginForm, BindingResult result, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            return "professor/loginForm";
        }

        String id = loginForm.getId();
        String passWord = loginForm.getPassWord();
        try {
            Professor professor = professorService.professorLogin(id, passWord);
            session.setAttribute("user",professor);
        }catch(Exception e){
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
            return "professor/loginForm";
        }
        redirectAttributes.addFlashAttribute("message","로그인 성공");
        redirectAttributes.addFlashAttribute("alertClass","alert-success");
        return "redirect:/professor/main";
    }
    @GetMapping(value = "/professor/main")
    public String CourseList(Model model,HttpSession session) {

        Professor user = (Professor)session.getAttribute("user");

        try {
            List<Course> courses = professorService.getCourseList(user);
            model.addAttribute("courses",courses);
        }
        catch(Exception e){
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }

        return "professor/main";
    }
    @GetMapping(value = "/professor/{cid}/room")
    public String classRoomDetail(Model model,HttpSession session,@PathVariable("cid") Integer cid) {
        Course course = null;
        try {
            course = professorService.findCourse(cid);
        } catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        session.setAttribute("course",course);
        model.addAttribute("course",course);
        return "professor/classRoom";
    }
    @GetMapping(value = "/professor/{cid}/assignment")
    public String assignmentList(Model model,HttpSession session, @PathVariable("cid") Integer cid) {
        Course course = null;
        List<Assignment> assignments= new ArrayList<>();
        try {
            course =professorService.findCourse(cid);

            model.addAttribute("course",course);

            assignments= professorService.getAssignmentList(course);

            model.addAttribute("assignments",assignments);
        }
        catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        return "professor/assignment";
    }
    @GetMapping(value = "/professor/{cid}/{aid}/assignmentDetail")
    public String assignmentDetail(Model model,HttpSession session,@PathVariable("cid") Integer cid, @PathVariable("aid") Long aid) {
        Course course = null;
        try {

            course =professorService.findCourse(cid);

            Professor user = (Professor) session.getAttribute("user");

            model.addAttribute("course",course);

            Assignment assignment = professorService.findAssignment(aid);

            model.addAttribute("assignment",assignment);

            List<HandIn> handIns= professorService.findHandInList(aid);

            model.addAttribute("handIns",handIns);
        }
        catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        return "professor/assignmentInfo";
    }
}
