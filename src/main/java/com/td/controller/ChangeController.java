package com.td.controller;

import com.td.model.SearchModel;
import com.td.util.DBUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChangeController {

    @RequestMapping("/change")
    @ResponseBody
    public List<SearchModel> changeHscode(String headHscode, String srcRegion) {
        System.out.println(headHscode);
        System.out.println(srcRegion);
        Connection conn = null;
        PreparedStatement pred;
        List<SearchModel> resultList = new ArrayList<>();

        try{
            conn = DBUtils.getConnection();
            StringBuilder sbuilder = new StringBuilder("");
            switch (srcRegion.toLowerCase()) {
                case "chn" :
                    sbuilder.append("select * from local_chn where hscode like ?");
                    break;
                case "usa" :
                    sbuilder.append("select * from local_usa where hscode like ?");
                    break;
                case "deu" :
                    sbuilder.append("select * from local_deu where hscode like ?");
                    break;
                case "sgp" :
                    sbuilder.append("select * from local_sgp where hscode like ?");
                    break;
            }
            pred = conn.prepareStatement(sbuilder.toString());
            pred.setObject(1, headHscode+"%");
            ResultSet res = pred.executeQuery();
            while(res.next()) {
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return  resultList;
    }

}
