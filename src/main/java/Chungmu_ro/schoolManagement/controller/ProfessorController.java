package Chungmu_ro.schoolManagement.controller;

import Chungmu_ro.schoolManagement.domain.*;
import Chungmu_ro.schoolManagement.form.*;
import Chungmu_ro.schoolManagement.service.ProfessorService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
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
            session.setAttribute("authority", "professor");
        }catch(Exception e){
            log.error(e.getMessage());
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "professor/loginForm";
        }
        redirectAttributes.addFlashAttribute("message","로그인 성공");
        redirectAttributes.addFlashAttribute("alertClass","alert-success");
        return "redirect:/professor/main";
    }
    @GetMapping(value = "/professor/main")
    public String CourseList(Model model,HttpSession session,RedirectAttributes redirectAttributes) {

        Professor user = (Professor)session.getAttribute("user");

        try {
            List<Course> courses = professorService.getCourseList(user);
            model.addAttribute("courses",courses);
        }
        catch(Exception e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }

        return "professor/main";
    }
    @GetMapping(value = "/professor/{cid}/room")
    public String classRoomDetail(Model model,HttpSession session,@PathVariable("cid") Integer cid,RedirectAttributes redirectAttributes) {
        Course course = null;
        try {
            course = professorService.findCourse(cid);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        session.setAttribute("course",course);
        model.addAttribute("course",course);
        return "professor/classRoom";
    }
    @GetMapping(value = "/professor/{cid}/assignment")
    public String assignmentList(Model model,HttpSession session, @PathVariable("cid") Integer cid,RedirectAttributes redirectAttributes) {
        Course course = null;
        List<Assignment> assignments= new ArrayList<>();
        try {
            course =professorService.findCourse(cid);

            model.addAttribute("course",course);

            assignments= professorService.getAssignmentList(course);

            model.addAttribute("assignments",assignments);
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/assignment";
    }
    @GetMapping(value = "/professor/{cid}/{aid}/assignmentDetail")
    public String assignmentDetail(Model model,HttpSession session,@PathVariable("cid") Integer cid, @PathVariable("aid") Long aid,RedirectAttributes redirectAttributes) {
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
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/assignmentInfo";
    }
    @GetMapping(value = "/professor/{cid}/{aid}/assignmentUpdate")
    public String assignmentUpdateForm(Model model, HttpSession session,@PathVariable("cid") Integer cid, @PathVariable("aid") Long aid,RedirectAttributes redirectAttributes) {
        Course course = null;
        try {

            AssignmentForm assignmentForm = new AssignmentForm();

            course =professorService.findCourse(cid);

            Professor user = (Professor) session.getAttribute("user");

            model.addAttribute("course",course);

            Assignment assignment = professorService.findAssignment(aid);

            model.addAttribute("assignment",assignment);

            assignmentForm.setAid(assignment.getAid());
            assignmentForm.setStarDate(assignment.getStarDate());
            assignmentForm.setDueDate(assignment.getDueDate());
            assignmentForm.setTitle(assignment.getTitle());
            assignmentForm.setPerfectScore(assignment.getPerfectScore());
            assignmentForm.setDescription(assignment.getDescription());

            model.addAttribute("assignmentForm",assignmentForm);


        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/assignmentForm";
    }
    @PostMapping(value = "/professor/{cid}/{aid}/assignmentUpdate")
    public String assignmentUpdate(Model model,AssignmentForm assignmentForm, HttpSession session,
                                   @PathVariable("cid") Integer cid, @PathVariable("aid") Long aid,RedirectAttributes redirectAttributes) {
        Course course = null;
        try {

            course =professorService.findCourse(cid);

            Professor user = (Professor) session.getAttribute("user");

            model.addAttribute("course",course);

            Assignment assignment = professorService.findAssignment(aid);

            assignment = professorService.setAssignment(course,assignment,assignmentForm);

            model.addAttribute("assignment",assignment);

            List<HandIn> handIns= professorService.findHandInList(aid);
            model.addAttribute("handIns",handIns);
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/assignmentInfo";
    }
    @PostMapping(value = "/professor/{cid}//assignmentUpdate")
    public String assignmentUpdate(Model model,AssignmentForm assignmentForm, HttpSession session,
                                   @PathVariable("cid") Integer cid,RedirectAttributes redirectAttributes) {
        Course course = null;
        try {

            course =professorService.findCourse(cid);

            Professor user = (Professor) session.getAttribute("user");

            model.addAttribute("course",course);

            Assignment assignment = professorService.addAssignment(course,assignmentForm);

            model.addAttribute("assignment",assignment);

            List<HandIn> handIns= professorService.findHandInList(assignment.getAid());
            model.addAttribute("handIns",handIns);
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/assignmentInfo";
    }
    @GetMapping(value = "/student/{cid}/assignmentAddForm")
    public String assignmentAddForm(Model model, HttpSession session,@PathVariable("cid") Integer cid,RedirectAttributes redirectAttributes) {
        Course course = null;
        try {

            AssignmentForm assignmentForm = new AssignmentForm();

            course = professorService.findCourse(cid);

            Professor user = (Professor) session.getAttribute("user");

            model.addAttribute("course", course);

            Assignment assignment = new Assignment();

            model.addAttribute("assignment", assignment);

            assignmentForm.setStarDate(LocalDateTime.now());
            assignmentForm.setDueDate(LocalDateTime.now());
            assignmentForm.setTitle("");
            assignmentForm.setPerfectScore(0);
            assignmentForm.setDescription("");

            model.addAttribute("assignmentForm", assignmentForm);


        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/assignmentForm";
    }
    @GetMapping(value = "/professor/{cid}/{aid}/{hid}/updateScore")
    public String scoreUpdateForm(Model model, HttpSession session,
                                  @PathVariable("cid") Integer cid, @PathVariable("aid") Long aid, @PathVariable("hid") Long hid,RedirectAttributes redirectAttributes) {
        Course course = null;
        HandIn handIn =null;
        try {

            HandInForm handInForm = new HandInForm();

            course =professorService.findCourse(cid);

            Professor user = (Professor) session.getAttribute("user");

            model.addAttribute("course",course);

            Assignment assignment = professorService.findAssignment(aid);

            model.addAttribute("assignment",assignment);

            handIn = professorService.findHandIn(hid);

            model.addAttribute("handIn",handIn);
            handInForm.setHid(handIn.getHid());
            handInForm.setScore(handIn.getScore());



            model.addAttribute("handInForm",handInForm);
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/handInUpdate";
    }
    @PostMapping(value = "/professor/{cid}/{aid}/{hid}/updateScore")
    public String scoreUpdate(Model model, HttpSession session, HandInForm handInForm,
                              @PathVariable("cid") Integer cid, @PathVariable("aid") Long aid, @PathVariable("hid") Long hid,RedirectAttributes redirectAttributes) {
        Course course = null;
        HandIn handIn =null;
        try {

            course =professorService.findCourse(cid);

            Professor user = (Professor) session.getAttribute("user");

            model.addAttribute("course",course);

            Assignment assignment = professorService.findAssignment(aid);

            model.addAttribute("assignment",assignment);

            handIn = professorService.findHandIn(hid);

            handIn.setScore(handInForm.getScore());

            professorService.setHandIn(handIn);

            model.addAttribute("handIn",handIn);

            List<HandIn> handIns= professorService.findHandInList(aid);
            model.addAttribute("handIns",handIns);

        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/assignmentInfo";
    }
    @RequestMapping("/professor/fileDown/{hid}")
    private void fileDown(Model model, @PathVariable("hid") Long hid, HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            HandIn handIn = professorService.findHandIn(hid);

            String fileUrl = handIn.getFileURL();
            fileUrl+="\\";
            String savePath = fileUrl;
            String fileName = handIn.getFileName();

            String originFileName = handIn.getFileOriginName();
            InputStream in = null;
            OutputStream os = null;
            File file =null;
            Boolean skip = false;
            String client ="";

            try{
                file =new File(savePath,fileName);
                in = new FileInputStream((file));
            }catch(FileNotFoundException e){ skip =true;}

            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description","HTML Generated Data");

            if(!skip){
                if(client.indexOf("Trident") != -1){
                    response.setHeader("Content-Disposition","attachment; filename=\""+
                            java.net.URLEncoder.encode(originFileName,"UTF-8").replaceAll("\\+","\\")+"\"");
                }
                else if(client.indexOf("MSIE") != -1){
                    response.setHeader("Content-Disposition","attachment; filename=\""+
                            java.net.URLEncoder.encode(originFileName,"UTF-8").replaceAll("\\+","\\")+"\"");
                }
                else{
                    response.setHeader("Content-Disposition","attachment; filename=\""+
                            new String(originFileName.getBytes("UTF-8"),"ISO8859_1")+"\"");
                    response.setHeader("Content-Type","application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length",""+file.length());
                os= response.getOutputStream();
                byte b[] = new byte[(int) file.length()];
                int leng =0;
                while((leng = in.read(b))>0) {
                    os.write(b, 0, leng);
                }
            }
            else{
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out =response.getWriter();
                out.println("<script> alert('파일을 찾을 수 없습니다.'); history.back();</script>");
                out.flush();
            }

        } catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
    }
    @GetMapping(value = "/professor/{cid}/attendance")
    public String attendanceList(Model model,HttpSession session, @PathVariable("cid") Integer cid,RedirectAttributes redirectAttributes) {
        Course course = null;
        try {
            course =professorService.findCourse(cid);

            model.addAttribute("course",course);

            Professor user = (Professor) session.getAttribute("user");

            List<Attendance> attendances = professorService.getAttendanceList(course);

            model.addAttribute("attendances",attendances);
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/attendance";
    }
    @GetMapping(value = "/professor/{cid}/{aid}/attendanceUpdate")
    public String attendanceUpdateForm(Model model,HttpSession session, @PathVariable("cid") Integer cid,@PathVariable("aid") Long aid,RedirectAttributes redirectAttributes){
        Course course = null;
        try {
            AttendanceForm attendanceForm =new AttendanceForm();

            course = professorService.findCourse(cid);

            model.addAttribute("course", course);

            Professor user = (Professor) session.getAttribute("user");

            List<Attendance> attendances = professorService.getAttendanceList(course);

            model.addAttribute("attendances", attendances);


            Attendance attendance = professorService.findAttendance(aid);
            model.addAttribute("attendance", attendance);

            attendanceForm.setAid(attendance.getAid());
            attendanceForm.setDate(attendance.getDate());
            attendanceForm.setAttendanceStatus(attendance.getAttendanceStatus());

            model.addAttribute("attendanceForm",attendanceForm);


        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/attendanceForm";
    }
    @PostMapping(value = "/professor/{cid}/{aid}/attendanceUpdate")
    public String attendanceUpdate(Model model,HttpSession session,AttendanceForm attendanceForm,
                                   @PathVariable("cid") Integer cid,@PathVariable("aid") Long aid,RedirectAttributes redirectAttributes){
        Course course = null;
        try {

            course = professorService.findCourse(cid);

            model.addAttribute("course", course);

            Professor user = (Professor) session.getAttribute("user");

            Attendance attendance = professorService.updateAttendance(aid,attendanceForm);

            model.addAttribute("attendance", attendance);

            List<Attendance> attendances = professorService.getAttendanceList(course);

            model.addAttribute("attendances", attendances);

        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/attendance";
    }
    @GetMapping(value = "/professor/{cid}/qa")
    public String qaList(Model model,HttpSession session, @PathVariable("cid") Integer cid,RedirectAttributes redirectAttributes) {
        Course course = null;

        try {
            course =professorService.findCourse(cid);

            model.addAttribute("course",course);

            Professor user = (Professor) session.getAttribute("user");

            List<QA> qas = professorService.getQAList(cid);

            model.addAttribute("qas",qas);
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/qa";
    }
    @GetMapping(value = "/professor/{cid}/{qid}/qaDetail")
    public String qaDetail(Model model,HttpSession session, @PathVariable("cid") Integer cid, @PathVariable("qid") Long qid,RedirectAttributes redirectAttributes) {
        Course course = null;

        try {
            course =professorService.findCourse(cid);

            model.addAttribute("course",course);

            Professor user = (Professor) session.getAttribute("user");

            QA qa = professorService.findQA(qid);

            model.addAttribute("qa",qa);
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/qaDetail";
    }
    @GetMapping(value = "/professor/{cid}/{qid}/qaUpdateForm")
    public String qaUpdateForm(Model model,HttpSession session, @PathVariable("cid") Integer cid, @PathVariable("qid") Long qid,RedirectAttributes redirectAttributes){
        Course course = null;
        try {

            course =professorService.findCourse(cid);

            model.addAttribute("course",course);

            Professor user = (Professor) session.getAttribute("user");

            QA qa = professorService.findQA(qid);

            model.addAttribute("qa",qa);

            QaForm qaForm = new QaForm();
            qaForm.setQid(qa.getQid());
            qaForm.setQuestion(qa.getQuestion());
            qaForm.setAnswer(qa.getAnswer());

            model.addAttribute(qaForm);
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/qaForm";
    }
    @PostMapping(value = "/professor/{cid}/{qid}/qaUpdate")
    public String qaUpdate(QaForm qaForm,Model model,HttpSession session, @PathVariable("cid") Integer cid, @PathVariable("qid") Long qid,RedirectAttributes redirectAttributes){
        Course course = null;
        try {

            model.addAttribute(qaForm);

            course =professorService.findCourse(cid);

            model.addAttribute("course",course);

            Professor user = (Professor) session.getAttribute("user");

            QA qa = professorService.updateQA(qid, qaForm);

            List<QA> qas = professorService.getQAList(cid);

            model.addAttribute("qas",qas);

        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/professor/main";
        }
        return "professor/qa";
    }
}
