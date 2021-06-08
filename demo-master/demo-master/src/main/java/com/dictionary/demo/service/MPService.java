package com.dictionary.demo.service;

import com.dictionary.demo.domain.Bill;
import com.dictionary.demo.domain.MP;
import com.dictionary.demo.domain.News;
import com.dictionary.demo.repository.MPRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Transactional
public class MPService {
    private final MPRepository mpRepository;

    public MPService(MPRepository mpRepository) { this.mpRepository = mpRepository; }

    public void join(MP mp) {
        if(!mpRepository.findByMona(mp.getMONA_CD()).isPresent()) {
            mpRepository.save(mp);
        }
    }

    public Optional<MP> findOne(String s) {
        return mpRepository.findByMona(s);
    }

    public Model getApiForLocationAndParty(String s, Model model, String kind) {
        StringBuffer sb = new StringBuffer();

        if(kind.equals("location")) {
            model.addAttribute("location", s);
            sb.append("&ORIG_NM=");
        }
        else if(kind.equals("party")) {
            model.addAttribute("party", s);
            sb.append("&POLY_NM=");
        }

        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        String temp = DatatypeConverter.printHexBinary(bytes);

        for(int i = 0; i < temp.length(); i++) {
            if(i%2==0) sb.append("%");
            sb.append(temp.charAt(i));
        }

        StringBuffer result = new StringBuffer();
        try{
            String urlStr = "https://open.assembly.go.kr/portal/openapi/nwvrqwxyaytdsfvhu?" +
                    "Key=fc034b86fe884eb299b8fc089cdc78d4" +
                    "&Type=json" +
                    sb.toString();
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
            JSONObject jObj = (JSONObject) jsonParser.parse(result.toString());

            System.out.println("sibal!!!!!"+jObj.toString());
            JSONArray MPArray = (JSONArray) jObj.get("nwvrqwxyaytdsfvhu");
            JSONObject row = (JSONObject) MPArray.get(1);
            JSONArray rowArray = (JSONArray) row.get("row");

            List<MP> mpList = new ArrayList<>();
            for(int i=0; i < rowArray.size();i++) {
                JSONObject obj = (JSONObject) rowArray.get(i);
                MP mp = new MP();
                mp.setPOLY_NM(obj.get("POLY_NM").toString());
                if(obj.get("ORIG_NM")!=null) mp.setORIG_NM(obj.get("ORIG_NM").toString());
                else mp.setORIG_NM("비례대표");
                mp.setHG_NM(obj.get("HG_NM").toString());
                mpList.add(mp);
            }

            model.addAttribute("MPList", mpList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }

    public ModelAndView getApiForMPPage(String s) {
        String tmp = "국회의원";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        byte[] bytes1 = tmp.getBytes(StandardCharsets.UTF_8);
        ModelAndView mv = new ModelAndView("MPs/MPPage");

        String temp = DatatypeConverter.printHexBinary(bytes);
        String temp1 = DatatypeConverter.printHexBinary(bytes1);
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();

        for(int i = 0; i < temp1.length(); i++) {
            if(i%2==0) sb1.append("%");
            sb1.append(temp1.charAt(i));
        }
        for(int i = 0; i < temp.length(); i++) {
            if(i%2==0) sb.append("%");
            sb.append(temp.charAt(i));
        }

        StringBuffer result = new StringBuffer();
        StringBuffer result1 = new StringBuffer();
        String news_result = "";
        try{
            String urlStr = "https://open.assembly.go.kr/portal/openapi/nwvrqwxyaytdsfvhu?" +
                    "Key=fc034b86fe884eb299b8fc089cdc78d4" +
                    "&Type=json" +
                    "&pIndex=1" +
                    "&pSize=1" +
                    "&HG_NM=" +
                    sb.toString();

            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));

            String returnLine = "";
            while((returnLine = br.readLine()) != null) {
                result.append(returnLine);
            }
            urlConnection.disconnect();

            urlStr = "https://open.assembly.go.kr/portal/openapi/nzmimeepazxkubdpn?" +
                    "Key=fc034b86fe884eb299b8fc089cdc78d4" +
                    "&Type=json" +
                    "&pIndex=1" +
                    "&pSize=10" +
                    "&AGE=21" +
                    "&PROPOSER=" +
                    sb.toString();

            url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));

            returnLine = "";
            while((returnLine = br.readLine()) != null) {
                result1.append(returnLine);
            }
            urlConnection.disconnect();

            String clientId = "w_WAhW_450lXf_sGGz0z";
            String clientSecret = "BlsP42er0x";
            urlStr = "https://openapi.naver.com/v1/search/news.json?query=" +
                    sb1.toString() +
                    sb.toString();

            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);
            news_result = get(urlStr, requestHeaders);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jObj = (JSONObject) jsonParser.parse(result.toString());
            JSONArray MPArray = (JSONArray) jObj.get("nwvrqwxyaytdsfvhu");
            if(MPArray == null) {
                ModelAndView mv1 = new ModelAndView("home");
                mv1.addObject("message", "error");
                return mv1;
            }
            JSONObject row = (JSONObject) MPArray.get(1);
            JSONArray rowArray = (JSONArray) row.get("row");
            JSONObject obj = (JSONObject) rowArray.get(0);

            MP mp = new MP();
            if(obj.get("TEL_NO")!=null) mp.setTEL_NO(obj.get("TEL_NO").toString());
            else mp.setTEL_NO("없음");
            if(obj.get("BTH_GBN_NM")!=null) mp.setBTH_GBN_NM(obj.get("BTH_GBN_NM").toString());
            else mp.setBTH_GBN_NM("없음");
            if(obj.get("ASSEM_ADDR")!=null) mp.setASSEM_ADDR(obj.get("ASSEM_ADDR").toString());
            else mp.setASSEM_ADDR("없음");
            if(obj.get("HJ_NM")!=null) mp.setHJ_NM(obj.get("HJ_NM").toString());
            else mp.setHJ_NM("없음");
            if(obj.get("HG_NM")!=null) mp.setHG_NM(obj.get("HG_NM").toString());
            else mp.setHG_NM("없음");
            if(obj.get("BTH_DATE")!=null) mp.setBTH_DATE(obj.get("BTH_DATE").toString());
            else mp.setBTH_DATE("없음");
            if(obj.get("ELECT_GBN_NM")!=null) mp.setELECT_GBN_NM(obj.get("ELECT_GBN_NM").toString());
            else mp.setELECT_GBN_NM("없음");
            if(obj.get("POLY_NM")!=null) mp.setPOLY_NM(obj.get("POLY_NM").toString());
            else mp.setPOLY_NM("없음");
            if(obj.get("REELE_GBN_NM")!=null) mp.setREELE_GBN_NM(obj.get("REELE_GBN_NM").toString());
            else mp.setREELE_GBN_NM("없음");
            if(obj.get("CMITS")!=null) mp.setCMITS(obj.get("CMITS").toString());
            else mp.setCMITS("없음");
            if(obj.get("MEM_TITLE")!=null) mp.setMEM_TITLE(obj.get("MEM_TITLE").toString());
            else mp.setMEM_TITLE("없음");
            if(obj.get("ENG_NM")!=null) mp.setENG_NM(obj.get("ENG_NM").toString());
            else mp.setENG_NM("없음");
            if(obj.get("SEX_GBN_NM")!=null) mp.setSEX_GBN_NM(obj.get("SEX_GBN_NM").toString());
            else mp.setSEX_GBN_NM("없음");
            if(obj.get("E_MAIL")!=null) mp.setE_MAIL(obj.get("E_MAIL").toString());
            else mp.setE_MAIL("없음");
            if(obj.get("MONA_CD")!=null) mp.setMONA_CD(obj.get("MONA_CD").toString());
            else mp.setMONA_CD("없음");
            if(obj.get("SECRETARY2")!=null) mp.setSECRETARY2(obj.get("SECRETARY2").toString());
            else mp.setSECRETARY2("없음");
            if(obj.get("JOB_RES_NM")!=null) mp.setJOB_RES_NM(obj.get("JOB_RES_NM").toString());
            else mp.setJOB_RES_NM("없음");
            if(obj.get("STAFF")!=null) mp.setSTAFF(obj.get("STAFF").toString());
            else mp.setSTAFF("없음");
            if(obj.get("HOMEPAGE")!=null) mp.setHOMEPAGE(obj.get("HOMEPAGE").toString());
            else mp.setHOMEPAGE("없음");
            if(obj.get("CMIT_NM")!=null) mp.setCMIT_NM(obj.get("CMIT_NM").toString());
            else mp.setCMIT_NM("없음");
            if(obj.get("SECRETARY")!=null) mp.setSECRETARY(obj.get("SECRETARY").toString());
            else mp.setSECRETARY("없음");

            if(obj.get("ORIG_NM")!=null) mp.setORIG_NM(obj.get("ORIG_NM").toString());
            else mp.setORIG_NM("비례대표");

            if(obj.get("UNITS")!=null) mp.setUNITS(obj.get("UNITS").toString());
            else mp.setUNITS("없음");
            //mpService.join(mp);

            mv.addObject("MP", mp);

            List<Bill> billList = new ArrayList<>();
            JSONObject jObj1 = (JSONObject) jsonParser.parse(result1.toString());
            JSONArray BillArray = (JSONArray) jObj1.get("nzmimeepazxkubdpn");
            JSONObject rowI = (JSONObject) BillArray.get(1);
            JSONArray rowIArray = (JSONArray) rowI.get("row");
            for(int i=0; i<rowIArray.size();i++) {
                JSONObject objI = (JSONObject) rowIArray.get(i);

                Bill bill = new Bill();

                if(objI.get("BILL_NO")!=null) bill.setBILL_NO(objI.get("BILL_NO").toString());
                else bill.setBILL_NO("미처리");

                if(objI.get("BILL_NAME")!=null) bill.setBILL_NAME(objI.get("BILL_NAME").toString());
                else bill.setBILL_NAME("미처리");

                if(objI.get("COMMITTEE")!=null) bill.setCOMMITTEE(objI.get("COMMITTEE").toString());
                else bill.setCOMMITTEE("미처리");

                if(objI.get("PROPOSE_DT")!=null) bill.setPROPOSE_DT(objI.get("PROPOSE_DT").toString());
                else bill.setPROPOSE_DT("미처리");

                if(objI.get("PROC_RESULT")!=null) bill.setPROC_RESULT(objI.get("PROC_RESULT").toString());
                else bill.setPROC_RESULT("미처리");

                if(objI.get("AGE")!=null) bill.setAGE(objI.get("AGE").toString());
                else bill.setAGE("미처리");

                if(objI.get("DETAIL_LINK")!=null) bill.setDETAIL_LINK(objI.get("DETAIL_LINK").toString());
                else bill.setDETAIL_LINK("미처리");

                if(objI.get("PROPOSER")!=null) bill.setPROPOSER(objI.get("PROPOSER").toString());
                else bill.setPROPOSER("미처리");

                if(objI.get("MEMBER_LIST")!=null) bill.setMEMBER_LIST(objI.get("MEMBER_LIST").toString());
                else bill.setMEMBER_LIST("미처리");

                if(objI.get("PUBL_PROPOSER")!=null) bill.setPUBL_PROPOSER(objI.get("PUBL_PROPOSER").toString());
                else bill.setPUBL_PROPOSER("미처리");

                if(objI.get("COMMITTEE_ID")!=null) bill.setCOMMITTEE_ID(objI.get("COMMITTEE_ID").toString());
                else bill.setCOMMITTEE_ID("미처리");

                billList.add(bill);
            }
            mv.addObject("Bill", billList);
            mv.addObject("newLineChar", '\n');

            List<News> newsList = new ArrayList<>();
            JSONObject jObj2 = (JSONObject) jsonParser.parse(news_result);

            JSONArray item = (JSONArray) jObj2.get("items");
            for(int i=0; i<item.size();i++) {
                JSONObject newsObj = (JSONObject) item.get(i);
                News news = new News();
                news.setTitle(newsObj.get("title").toString());
                news.setOriginallink(newsObj.get("originallink").toString());
                news.setLink(newsObj.get("link").toString());
                news.setDescription(newsObj.get("description").toString());
                news.setPubDate(newsObj.get("pubDate").toString());

                newsList.add(news);
            }
            mv.addObject("News", newsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }


}