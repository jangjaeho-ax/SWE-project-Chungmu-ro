package Chungmu_ro.schoolManagement.controller;

import Chungmu_ro.schoolManagement.domain.Member;
import Chungmu_ro.schoolManagement.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginPageController {

    private final MemberService memberService;

    @RequestMapping("/")
    public String mainPage(Model model) {
        log.info("Login controller");
        return "main";
    }
}
