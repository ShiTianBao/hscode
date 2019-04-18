package com.td.controller;

import com.td.model.SearchModel;
import com.td.util.CommonUtils;
import com.td.util.DBUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    private List<SearchModel> s(String srcRegion, String srcKeyWord, String subHeading) {
        System.out.println(srcRegion);
        System.out.println(srcKeyWord);
        Connection conn = null;
        PreparedStatement pred;
        List<SearchModel> resultList = new ArrayList<>();


        //去掉首尾空格
        srcKeyWord = srcKeyWord.trim();
        //assKeyWord = assKeyWord.trim();

        //将关键词分开
        String[] srcKeyWords = srcKeyWord.split(";|,|\\s+");
        //String[] assKeyWords = assKeyWord.split(";|,|\\s+");


        System.out.println(srcRegion);


        try{
            conn = DBUtils.getConnection();
            StringBuilder sbuilder = new StringBuilder("");
            switch (srcRegion.toLowerCase()) {
                case "chn" :
                    sbuilder.append("select * from local_chn where ");
                    break;
                case "usa" :
                    sbuilder.append("select * from local_usa where ");
                    break;
                case "deu" :
                    sbuilder.append("select * from local_deu where ");
                    break;
                case "sgp" :
                    sbuilder.append("select * from local_sgp where ");
                    break;

            }
            for(int i=0; i<srcKeyWords.length; i++) {
                if(i == 0) {
                    sbuilder.append("description like ? ");
                }else{
                    sbuilder.append("and description like ?");
                }
            }
            sbuilder.append("and length(hscode)>6");
            System.out.println(sbuilder.toString());
            pred = conn.prepareStatement(sbuilder.toString());
            for(int i=0; i<srcKeyWords.length; i++) {
                pred.setObject(i+1, "%" +srcKeyWords[i] + "%");
            }
            // pred.setObject(srcKeyWords.length + 1, subHeading + "%");
            System.out.println(pred.toString());
            ResultSet res = pred.executeQuery();
            while(res.next()){
                String hscode = res.getString("hscode");
                if(hscode.length()>=8) {
                    SearchModel sm = new SearchModel();
                    sm.setHscode(res.getString("hscode"));
                    sm.setDescription(res.getString("description"));

                    switch (srcRegion.toLowerCase()) {
                        case "chn" :
                            sm.setAdded(res.getString("added"));
                            sm.setFavor(res.getString("most_favor"));
                            sm.setGeneral(res.getString("general"));
                            break;
                        case "usa" :
                            sm.setAdded(res.getString("column_2_rate_of_duty"));
                            sm.setFavor(res.getString("special_rate_of_duty"));
                            sm.setGeneral(res.getString("general_rate_of_duty"));
                            break;
                        case "deu" :
                            sm.setAdded("");
                            sm.setFavor(res.getString("supplementary_unit"));
                            sm.setGeneral(res.getString("conventional_rate_of_duty"));
                            break;
                        case "sgp" :
                            sm.setAdded(res.getString("customs_full"));
                            sm.setFavor(res.getString("duty_pref"));
                            sm.setGeneral(res.getString("gst"));
                            break;

                    }

                    resultList.add(sm);
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    @RequestMapping("search")
    @ResponseBody
    public List<SearchModel> search(String srcRegion, String srcKeyWord, String assKeyWord, String subHeading) {
        System.out.println(assKeyWord);
        List<SearchModel> l1 = s(srcRegion,srcKeyWord,subHeading);
        List<SearchModel> l2 = new ArrayList<>();
//        if(assKeyWord.length()>0) {
//            l2 = s(srcRegion, assKeyWord,subHeading);
//        }
        boolean isSame = false;
        for(SearchModel sm2 : l2) {
            isSame = false;
            String hscode = sm2.getHscode();
            for(SearchModel sm1 : l1) {
                if(hscode.equals(sm1.getHscode())){
                    isSame = true;
                }
            }
            if(!isSame){
                l1.add(sm2);
            }
        }
        return l1;
    }

    @RequestMapping("/search-p")
    @ResponseBody
    public List<SearchModel> searchP(String srcRegion, String srcKeyWord, String knownHscode) {
        //srcKeyWord = "ducks";
        //srcRegion = "chn";
        System.out.println(knownHscode);
        Boolean hasHscode = Boolean.FALSE;
        String countryIndex = "0";
        if(knownHscode.length()>0) {
            hasHscode = Boolean.TRUE;
        }
        System.out.println(hasHscode);
        switch (srcRegion) {
            case "chn" : countryIndex = "38";
            break;
            case "usa" : countryIndex = "0";
            break;
            case "deu" : countryIndex = "1";
            break;
            case "sgp" : countryIndex = "2";
        }
        String cmdStr = "python D:/dnr/python/hscode/empiria_ltt.py" + srcKeyWord + hasHscode + knownHscode + countryIndex;
        try{
            Process pr = Runtime.getRuntime().exec(cmdStr);
            pr.waitFor();
        }catch (Exception e) {
            e.printStackTrace();
        }
        String jsonString = CommonUtils.readJsonFile("D:/dnr/python/hscode/results/result.json");
        System.out.println(jsonString);
        //jsonString = jsonString.substring(1, jsonString.length()-1);
        //jsonString = "[" + jsonString + "]";
        System.out.println(jsonString);
        JSONObject jsonArray = new JSONObject(jsonString);
        List<SearchModel> searchModelList = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(String.valueOf(i));
            SearchModel sm = new SearchModel();
            sm.setHscode(json.getString("HS CODE"));
            sm.setDescription(json.getString("DESCRIPTION"));
            sm.setFavor(String.valueOf(json.getBigDecimal("RELATEDNESS")));
            searchModelList.add(sm);
        }


        return searchModelList;
    }
}
