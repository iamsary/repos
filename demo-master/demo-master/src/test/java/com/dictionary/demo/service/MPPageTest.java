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
public class MPPageTest {
    @Autowired MockMvc mvc;

    @Test
    public void 정상동작() throws Exception {
        String target = "강기윤";
        mvc.perform(get("/MPPage/" + target))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 잘못된검색() throws Exception {
        /*
        * 잘못된 이름을 검색했기 때문에 home.html로 보내는 modelAndView가 출력된다.
        * ModelAndView:
        *         View name = home
        *              View = null
        *         Attribute = message
        *             value = error
        *
        * */
        String target = "김치볶음밥";
        mvc.perform(get("/MPPage/" + target))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
