package com.dictionary.demo.service;

import com.dictionary.demo.controller.MemberForm;
import com.dictionary.demo.domain.Bill;
import com.dictionary.demo.domain.Member;
import com.dictionary.demo.repository.MemberRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Optional<Member> findOneByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public List<Integer> recommendBillIdx(String s) {
        Member target = memberRepository.findByEmail(s).orElseThrow();

        /*
         * 유저 유사도 판별
         * */
        List<Member> members = memberRepository.findAll();
        List<Double> similarity = new ArrayList<>();
        for(int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            if(target.getEmail().equals(member.getEmail())) {
                similarity.add((double)0);
                continue;
            }

            double k = cos(target, member);
            similarity.add(k);
        }

        /*
         * 유사도로 점수 근사치 찾음
         * */
        double demo = 0;
        for(int i = 0; i < similarity.size(); i++) demo += similarity.get(i);
        List<Double> numer = new ArrayList<>();
        for(int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            String[] str = member.getScore().split(",");
            for(int j = 0; j < str.length; j++) {
                int a = str[j].charAt(0) - '0';
                if(i == 0) numer.add(similarity.get(i) * a);
                else {
                    double temp = numer.get(j) + similarity.get(i) * a;
                    numer.set(j, temp);
                }
            }
        }

        List<Integer> indexList = new ArrayList<>();
        List<Double> scores = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            double maxScore = 0;
            int maxIdx = 0;
            for(int j = 0; j < numer.size(); j++) {
                scores.add(numer.get(j) / demo);

                boolean chk = false;
                for(int k : indexList) {
                    if(k == j) chk = true;
                }

                if(chk) continue;

                if (maxScore < scores.get(j)) {
                    maxScore = scores.get(j);
                    maxIdx = j;
                }
            }
            indexList.add(maxIdx);
        }

        return indexList;
    }

    private double cos(Member target, Member member) {
        int multi = 0, temp1 = 0, temp2 = 0;
        String[] score1 = target.getScore().split(",");
        String[] score2 = member.getScore().split(",");

        for(int i = 0; i < score1.length; i++) {
            int a = score1[i].charAt(0) - '0';
            int b = score2[i].charAt(0) - '0';
            multi += a*b;
            temp1 += a*a;
            temp2 += b*b;
        }

        return multi/(Math.sqrt(temp1)*Math.sqrt(temp2));
    }

    public Model recommend(String s, Model model) {
        List<Integer> indexList = recommendBillIdx(s);
        List<Bill> billList = new ArrayList<>();

        for(int i : indexList) {
            int billNum = 2100000 + i + 1;
            StringBuffer result = new StringBuffer();
            try{
                String urlStr = "https://open.assembly.go.kr/portal/openapi/nzmimeepazxkubdpn?" +
                        "Key=fc034b86fe884eb299b8fc089cdc78d4" +
                        "&Type=json" +
                        "&pIndex=1" +
                        "&pSize=1" +
                        "&AGE=21" +
                        "&BILL_NO=" +
                        Integer.toString(billNum);
                URL url = new URL(urlStr);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));

                String returnLine = "";
                while((returnLine = br.readLine()) != null) {
                    result.append(returnLine);
                }
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
                JSONParser jsonParser = new JSONParser();

                JSONObject jobj = (JSONObject) jsonParser.parse(result.toString());
                JSONArray BillArray = (JSONArray) jobj.get("nzmimeepazxkubdpn");
                if(BillArray == null) continue;
                JSONObject row = (JSONObject) BillArray.get(1);
                JSONArray rowArray = (JSONArray) row.get("row");
                JSONObject obj = (JSONObject) rowArray.get(0);

                Bill bill = new Bill();
                bill.setBILL_NO(obj.get("BILL_NO").toString());
                bill.setBILL_NAME(obj.get("BILL_NAME").toString());
                bill.setCOMMITTEE(obj.get("COMMITTEE").toString());
                bill.setPROPOSE_DT(obj.get("PROPOSE_DT").toString());
                if(obj.get("PROC_RESULT")!=null) bill.setPROC_RESULT(obj.get("PROC_RESULT").toString());
                else bill.setPROC_RESULT("미처리");
                bill.setAGE(obj.get("AGE").toString());
                bill.setDETAIL_LINK(obj.get("DETAIL_LINK").toString());
                bill.setPROPOSER(obj.get("PROPOSER").toString());
                bill.setMEMBER_LIST(obj.get("MEMBER_LIST").toString());
                bill.setRST_PROPOSER(obj.get("RST_PROPOSER").toString());
                bill.setPUBL_PROPOSER(obj.get("PUBL_PROPOSER").toString());
                bill.setCOMMITTEE_ID(obj.get("COMMITTEE_ID").toString());

                billList.add(bill);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("Bill", billList);

        return model;
    }

    public ModelAndView errorCheck(MemberForm form) {
        Member member = new Member();
        member.setName(form.getFname()+form.getLname());
        member.setEmail(form.getEmail());

        if(form.isSamePassword()) {
            member.setPassword(form.getPassword());
        }
        else{
            try{throw new IOException("비밀번호가 다릅니다."); }
            catch (IOException e) {
                ModelAndView mv = new ModelAndView("/members/createMemberForm");
                mv.addObject("message2", "error");
                return mv;
            }
        }
        member.setEmail(form.getEmail());

        member.setBirthday(form.getBirthday());
        member.setGender(form.getGender());

        try { join(member); }
        catch (IllegalStateException e) {
            ModelAndView mv = new ModelAndView("/members/createMemberForm");
            mv.addObject("message", "error");
            return mv;
        }

        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
    }
}