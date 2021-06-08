package com.dictionary.demo.controller;

import com.dictionary.demo.domain.Bill;
import com.dictionary.demo.domain.MP;
import com.dictionary.demo.service.MPService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.min;

@Controller
public class MPController {
    private final MPService mpService;

    public MPController(MPService mpService) { this.mpService = mpService; }

    @GetMapping("/party")
    public String party() {
        return "/MPs/party";
    }

    @GetMapping("/location")
    public String location() {
        return "/MPs/location";
    }

    @GetMapping("/location/{ORIG_NM}")
    public String locationMembers(@PathVariable("ORIG_NM") String s, Model model) {
        model = mpService.getApiForLocationAndParty(s, model, "location");
        return "MPs/location_page";
    }

    @GetMapping("/party/{POLY_NM}")
    public String partyMembers(@PathVariable("POLY_NM") String s, Model model) {
        model = mpService.getApiForLocationAndParty(s, model, "party");
        return "MPs/party_page";
    }

    @GetMapping("/MPPage/{HG_NM}")
    public ModelAndView getApi(@PathVariable("HG_NM") String s) {
        ModelAndView mv = mpService.getApiForMPPage(s);
        return mv;
    }


    @RequestMapping("/MPPage/search")
    private ModelAndView search(HttpServletRequest request){
        String s = request.getParameter("mpname");
        ModelAndView mv = mpService.getApiForMPPage(s);
        return mv;
    }
}