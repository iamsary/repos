package com.dictionary.demo;

import com.dictionary.demo.domain.Member;
import com.dictionary.demo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Connection;
import java.lang.String;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    private final MemberService memberService;

    @Autowired
    DataSource dataSource;

    public DemoApplication(MemberService memberService) {
        this.memberService = memberService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        try(Connection connection = dataSource.getConnection()){
            System.out.println(connection.getMetaData().getURL());
            System.out.println(connection.getMetaData().getUserName());

            // table 생성
            Statement statement = (Statement) connection.createStatement();
            String sql = "truncate table member";
            statement.executeUpdate(sql);
        }

        for (int i = 1; i <= 10; i++) {
            Member member = new Member();
            member.setEmail("a" + Integer.toString(i) + "@gmail.com");
            member.setPassword("a");
            member.setName("멤버" + Integer.toString(i));
            String s = "2000-10-1" + Integer.toString(i - 1);
            LocalDate date = LocalDate.parse(s, DateTimeFormatter.ISO_DATE);
            member.setBirthday(date);
            member.setGender("남");
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < 100; j++) {
                int a = (int) (Math.random() * 10000) % 5;
                sb.append(Integer.toString(a));
                if (j == 99) break;
                sb.append(",");
            }

            member.setScore(sb.toString());
            memberService.join(member);
        }
    }
}

