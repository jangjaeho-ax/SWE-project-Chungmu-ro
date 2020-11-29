package Chungmu_ro.schoolManagement.controller;

import Chungmu_ro.schoolManagement.domain.*;
import Chungmu_ro.schoolManagement.form.HandInForm;
import Chungmu_ro.schoolManagement.form.LoginForm;
import Chungmu_ro.schoolManagement.form.QaForm;
import Chungmu_ro.schoolManagement.service.MemberService;
import Chungmu_ro.schoolManagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @GetMapping(value = "/student/login")
    public String loginForm(Model model,RedirectAttributes redirectAttributes) {
        //studentService.init();
        model.addAttribute("loginForm", new LoginForm());
        return "student/loginForm";
    }
    @PostMapping(value = "/student/login")
    public String login(@Valid LoginForm loginForm, BindingResult result,HttpSession session,Model model,RedirectAttributes redirectAttributes) {

        if(result.hasErrors()){
            return "redirect:/student/loginForm";
        }

        String id = loginForm.getId();
        String passWord = loginForm.getPassWord();
        try {
            Student student = studentService.studentLogin(id, passWord);
            session.setAttribute("user", student);
           // session.setAttribute("sid", student.getSid());
        }
        catch(Exception e){
            //redirectAttributes.addFlashAttribute("message",e.getMessage());
           // redirectAttributes.addFlashAttribute("alertClass","alert-danger");
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
            return "student/loginForm";
        }
        redirectAttributes.addFlashAttribute("message","로그인 성공");
        redirectAttributes.addFlashAttribute("alertClass","alert-success");
        return "redirect:/student/main";

    }

    @GetMapping(value = "/student/main")
    public String CourseList(Model model,HttpSession session) {

        Student user = (Student)session.getAttribute("user");

        try {
            List<Course> courses = studentService.getCourseList(user);
            model.addAttribute("courses",courses);
        }
        catch(Exception e){
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }

        return "student/main";
    }

    @GetMapping(value = "/student/{cid}/room")
    public String classRoomDetail(Model model,HttpSession session,@PathVariable("cid") Integer cid) {
        Course course = null;
        try {
           course = studentService.findCourse(cid);
        } catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        session.setAttribute("course",course);
        model.addAttribute("course",course);
        return "student/classRoom";
    }

    /*
    @GetMapping(value = "/student/{cid}/assignment")
    public String showAssignmentList(Model model,HttpSession session,@PathVariable("cid") Integer cid) {
        Course course = null;
        List<Assignment> assignments= new ArrayList<>();
        try {
            course = studentService.findCourse(cid);

            model.addAttribute("course",course);

            assignments= studentService.getAssignmentList(course);

            model.addAttribute("assignments",assignments);
        }
        catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        return "student/assignment";
    }

     */
    @GetMapping(value = "/student/{cid}/assignment")
    public String assignmentList(Model model,HttpSession session, @PathVariable("cid") Integer cid) {
        Course course = null;
        List<Assignment> assignments= new ArrayList<>();
        try {
            course =studentService.findCourse(cid);

            model.addAttribute("course",course);

            assignments= studentService.getAssignmentList(course);

            model.addAttribute("assignments",assignments);
        }
        catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        return "student/assignment";
    }

    @GetMapping(value = "/student/{cid}/{aid}/assignmentDetail")
    public String assignmentDetail(Model model,HttpSession session,@PathVariable("cid") Integer cid, @PathVariable("aid") Long aid) {
        Course course = null;
        try {

            course =studentService.findCourse(cid);

            Student user = (Student) session.getAttribute("user");

            model.addAttribute("course",course);

            Assignment assignment = studentService.findAssignment(aid);

            model.addAttribute("assignment",assignment);

            HandIn handIn = studentService.findHandIn(user.getSid(),cid, aid);

            model.addAttribute("handIn",handIn);
        }
        catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        return "student/assignmentInfo";
    }
    @GetMapping(value = "/student/{cid}/{aid}/assignmentUpdate")
    public String assignmentUpdateForm(Model model, HttpSession session,@PathVariable("cid") Integer cid, @PathVariable("aid") Long aid) {
        Course course = null;
        HandIn handIn =null;
        try {

            course =studentService.findCourse(cid);

            Student user = (Student) session.getAttribute("user");

            model.addAttribute("course",course);

            Assignment assignment = studentService.findAssignment(aid);

            model.addAttribute("assignment",assignment);

            handIn = studentService.findHandIn(user.getSid(),cid, aid);

            model.addAttribute("handIn",handIn);
        }
        catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
            return "student/assignmentInfo";
        }
         return "student/assignmentUpdate";
    }
    @PostMapping(value = "/student/{cid}/{aid}/fileInsert")
    public String assignmentUpdate(@RequestParam("file") MultipartFile files,Model model, HttpSession session,@PathVariable("cid") Integer cid, @PathVariable("aid") Long aid) {
        try {
            Course course =studentService.findCourse(cid);
            model.addAttribute("course",course);
            Assignment assignment = studentService.findAssignment(aid);
            model.addAttribute("assignment",assignment);

            Student user = (Student) session.getAttribute("user");
            HandIn handIn = studentService.findHandIn(user.getSid(),cid, aid);
            model.addAttribute("handIn",handIn);
            if (files.isEmpty()) {
                studentService.updateHandIn(handIn.getHid(),"","","");
            } else {
                String fileName = files.getOriginalFilename();

                String fileNameExtension = StringUtils.getFilenameExtension(fileName);
                File destinationFile;
                String destinationFileName;

                String fileUrl = "E:\\testFile";

                do {
                    int leftLimit = 48; // numeral '0'
                    int rightLimit = 122; // letter 'z'
                    int targetStringLength = 32;
                    Random random = new Random();

                    destinationFileName = random.ints(leftLimit, rightLimit + 1)
                            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                            .limit(targetStringLength)
                            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                            .toString();
                    destinationFileName = destinationFileName + "." + fileNameExtension;
                    destinationFile = new File(fileUrl + destinationFileName);
                } while (destinationFile.exists());

                studentService.updateHandIn(handIn.getHid(),destinationFileName,fileName,fileUrl);
            }
        }catch(Exception e){
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
            return "student/assignmentUpdate";
        }
        return "student/assignmentInfo";

    }
    @RequestMapping("/student/fileDown/{hid}")
    private void fileDown(Model model, @PathVariable("hid") Long hid, HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            HandIn handIn = studentService.findHandIn(hid);

            String fileUrl = handIn.getFileURL();
            fileUrl+="/";
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
    @GetMapping(value = "/student/{cid}/attendance")
    public String attendanceList(Model model,HttpSession session, @PathVariable("cid") Integer cid) {
        Course course = null;
        try {
            course =studentService.findCourse(cid);

            model.addAttribute("course",course);

            Student user = (Student) session.getAttribute("user");

            List<Attendance> attendances = studentService.getMyAttendanceList(cid, user.getSid());

            model.addAttribute("attendances",attendances);
        }
        catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        return "student/attendance";
    }
    @GetMapping(value = "/student/{cid}/qa")
    public String qaList(Model model,HttpSession session, @PathVariable("cid") Integer cid) {
        Course course = null;

        try {
            course =studentService.findCourse(cid);

            model.addAttribute("course",course);

            Student user = (Student) session.getAttribute("user");

            List<QA> qas = studentService.getQAList(cid);

            model.addAttribute("qas",qas);
        }
        catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        return "student/qa";
    }
    @GetMapping(value = "/student/{cid}/{qid}/qaDetail")
    public String qaDetail(Model model,HttpSession session, @PathVariable("cid") Integer cid, @PathVariable("qid") Long qid) {
        Course course = null;

        try {
            course =studentService.findCourse(cid);

            model.addAttribute("course",course);

            Student user = (Student) session.getAttribute("user");

            QA qa = studentService.findQA(qid);

            model.addAttribute("qa",qa);
        }
        catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        return "student/qaDetail";
    }
    @GetMapping(value = "/student/{cid}/{qid}/qaUpdate")
    public String qaUpdateForm(Model model,HttpSession session, @PathVariable("cid") Integer cid, @PathVariable("qid") Long qid){
        Course course = null;
        try {
            QaForm qaForm = new QaForm();

            model.addAttribute(qaForm);

            course =studentService.findCourse(cid);

            model.addAttribute("course",course);

            Student user = (Student) session.getAttribute("user");

            QA qa = studentService.findQA(qid);

            model.addAttribute("qa",qa);

        }
        catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        return "student/qaForm";
    }
    @PostMapping(value = "/student/{cid}/{qid}/qaUpdate")
    public String qaUpdate(Model model,HttpSession session, @PathVariable("cid") Integer cid, @PathVariable("qid") Long qid){
        Course course = null;
        try {
            QaForm qaForm = new QaForm();

            model.addAttribute(qaForm);

            course =studentService.findCourse(cid);

            model.addAttribute("course",course);

            Student user = (Student) session.getAttribute("user");

            QA qa = studentService.findQA(qid);

            model.addAttribute("qa",qa);

        }
        catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("alertClass","alert-danger");
        }
        return "student/qaForm";
    }
}
