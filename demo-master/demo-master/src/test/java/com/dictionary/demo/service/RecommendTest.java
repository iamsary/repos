package com.dictionary.demo.service;

import com.dictionary.demo.domain.Bill;
import com.dictionary.demo.domain.Member;
import com.dictionary.demo.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@AutoConfigureMockMvc
public class RecommendTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired MockMvc mvc;

    @Test
    public void 추천() throws Exception {
        //Given
        for(int i=1;i<=10;i++) {
            Member member = new Member();
            member.setEmail("a" + Integer.toString(i) + "@gmail.com");
            member.setPassword("a");
            member.setName("멤버" + Integer.toString(i));
            String s = "2000-10-1" + Integer.toString(i-1);
            LocalDate date = LocalDate.parse(s, DateTimeFormatter.ISO_DATE);
            member.setBirthday(date);
            member.setGender("남");
            StringBuffer sb = new StringBuffer();
            for(int j = 0; j < 100; j++) {
                int a = (int) (Math.random()*10000) % 5;
                sb.append(Integer.toString(a));
                if(j==99) break;
                sb.append(",");
            }

            member.setScore(sb.toString());
            memberService.join(member);
        }

        String target = "a1@gmail.com";
        mvc.perform(get("/recommend/" + target))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
