package Chungmu_ro.schoolManagement.controller;


import Chungmu_ro.schoolManagement.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberServicee;


}
