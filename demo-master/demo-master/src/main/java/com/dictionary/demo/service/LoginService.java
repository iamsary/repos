package com.dictionary.demo.service;

import com.dictionary.demo.controller.LoginForm;
import com.dictionary.demo.domain.Member;
import com.dictionary.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private final MemberRepository memberRepository;

    public LoginService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ModelAndView login(LoginForm loginForm, HttpSession session){
        String email = loginForm.getEmail();
        String pw = loginForm.getPw();

        if(loginCheck(email, pw)){ // id,pw검사를 통해 True,false를 return
            session.setAttribute("loginCheck",true);
            session.setAttribute("id",email);
            ModelAndView mv = new ModelAndView("home");
            return mv;
        }else{
            ModelAndView mv = new ModelAndView("/members/login");
            mv.addObject("message","error");
            return mv;
        }
    }

    public void logout(HttpSession session){
        session.setAttribute("loginCheck",null);
        session.setAttribute("id",null);
    }
    public boolean loginCheck(String email, String pw){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(!member.isEmpty()){
            Member m= member.get();
            if(m.getPassword().equals(pw)){
                return true;
            }
            else return false;
        }
        return false;
    }
}
