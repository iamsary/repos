package com.dictionary.demo.controller;

import com.dictionary.demo.service.LoginService;
import com.dictionary.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    private final LoginService loginService;

    public MemberController(MemberService memberService, LoginService loginService) {
        this.memberService = memberService;
        this.loginService = loginService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public ModelAndView create(MemberForm form) throws ParseException, IOException {
        return memberService.errorCheck(form);
    }

    @GetMapping("/members/login")
    public String login() {
        return "members/login";
    }

    @GetMapping("/recommend/{email}")
    public String recommend(@PathVariable("email") String s, Model model) {
        model = memberService.recommend(s, model);
        return "/members/recommend";
    }

    @PostMapping("/loginProcess")
    public ModelAndView loginProcess(LoginForm loginForm, HttpSession session) {
        return loginService.login(loginForm, session);
    }

    @GetMapping("/logoutProcess")
    public String logoutProcess(HttpSession session) {
        loginService.logout(session);
        return "home";
    }
}