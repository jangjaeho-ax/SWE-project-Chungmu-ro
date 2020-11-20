package Chungmu_ro.schoolManagement.controller;


import Chungmu_ro.schoolManagement.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberServicee;

    @Autowired
    public MemberController(MemberService memberServicee) {
        this.memberServicee = memberServicee;
    }
}
