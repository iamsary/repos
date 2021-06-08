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
public class LocationAndPartyTest {
    @Autowired MockMvc mvc;

    @Test
    public void 국민의힘() throws Exception {
        String target = "국민의힘";
        mvc.perform(get("/party/" + target))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 더불어민주당() throws Exception {
        String target = "더불어민주당";
        mvc.perform(get("/party/" + target))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 정의당() throws Exception {
        String target = "정의당";
        mvc.perform(get("/party/" + target))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 서울() throws Exception {
        String target = "서울";
        mvc.perform(get("/location/" + target))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 경기() throws Exception {
        String target = "경기";
        mvc.perform(get("/location/" + target))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 강원() throws Exception {
        String target = "강원";
        mvc.perform(get("/location/" + target))
                .andExpect(status().isOk())
                .andDo(print());
    }
}