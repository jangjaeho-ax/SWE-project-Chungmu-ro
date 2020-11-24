package Chungmu_ro.schoolManagement.controller;

import Chungmu_ro.schoolManagement.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class LoginPageController {

    Member user;

    @RequestMapping("/")
    public String mainPage(Model model) {
        log.info("Login controller");
        return "login";
    }
}
