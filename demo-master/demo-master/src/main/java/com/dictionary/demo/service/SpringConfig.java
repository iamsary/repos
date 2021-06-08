package com.dictionary.demo.service;

import com.dictionary.demo.repository.BillRepository;
import com.dictionary.demo.repository.JpaMemberRepository;
import com.dictionary.demo.repository.MPRepository;
import com.dictionary.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;
    private final MPRepository mpRepository;
    private final BillRepository billRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository, MPRepository mpRepository, BillRepository billRepository) {
        this.memberRepository = memberRepository;
        this.mpRepository = mpRepository;
        this.billRepository = billRepository;
    }

    @Bean
    public MemberService memberService() { return new MemberService(memberRepository); }

    @Bean
    public MPService mpService() { return new MPService(mpRepository); }

    @Bean
    public BillService billService() { return new BillService(billRepository); }

    @Bean
    MappingJackson2JsonView jsonView() { return new MappingJackson2JsonView(); }

    /*@Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(em);
    }*/
}