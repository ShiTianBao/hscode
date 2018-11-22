package com.td.handlers;

import com.td.model.SearchModel;
import com.td.util.BaiduAPI;
import com.td.util.DBUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Controller
public class RetrieveController {

    @RequestMapping("retrieve")
    @ResponseBody
    public SearchModel retrieve(String srcResult, String srcRegion, String tgtRegion){

        System.out.println(srcResult);
        Connection conn = null;
        PreparedStatement pred;
        SearchModel sm = new SearchModel();
        String[] srcResults = srcResult.trim().split(":|：");
        String hscode = srcResults[0];
        String srcDes = "default text";
        String head = hscode.substring(0, 4);
        Double maxScore = 0.0;
        String resultHscode = "";

        try{
            conn = DBUtils.getConnection();
            StringBuilder srcBuilder = new StringBuilder("");
            switch (srcRegion.toLowerCase()) {
                case "chn" :
                    srcBuilder.append("select description from intl_chn where hscode = ?");
                    break;
                case "usa" :
                    srcBuilder.append("select description from intl_usa where hscode = ?");
                    break;
                case "deu" :
                    srcBuilder.append("select description from intl_deu where hscode = ?");
                    break;
                case "sgp" :
                    srcBuilder.append("select description from intl_sgp where hscode = ?");
                    break;
            }
            pred = conn.prepareStatement(srcBuilder.toString());
            pred.setObject(1, hscode);
            ResultSet srcRes = pred.executeQuery();
            if(srcRes.next()) {
                srcDes = srcRes.getString("description");
            }

            StringBuilder tgtBuilder = new StringBuilder("");
            StringBuilder tgtBuilder2 = new StringBuilder("");
            switch (tgtRegion.toLowerCase()) {
                case "chn" :
                    tgtBuilder.append("select * from intl_chn where hscode like ?");
                    tgtBuilder2.append("select * from local_chn where hscode = ?");
                    break;
                case "usa" :
                    tgtBuilder.append("select * from intl_usa where hscode like ?");
                    tgtBuilder2.append("select * from local_usa where hscode = ?");
                    break;
                case "deu" :
                    tgtBuilder.append("select * from intl_deu where hscode like ?");
                    tgtBuilder2.append("select * from local_deu where hscode = ?");
                    break;
                case "sgp" :
                    tgtBuilder.append("select * from intl_sgp where hscode like ?");
                    tgtBuilder2.append("select * from local_sgp where hscode = ?");
                    break;
            }
            pred = conn.prepareStatement(tgtBuilder.toString());
            pred.setObject(1, head + "%");
            ResultSet tgtRes = pred.executeQuery();
            while(tgtRes.next()) {
                String tgtDes = tgtRes.getString("description");
                String tgtHscode = tgtRes.getString("hscode");
                if(tgtHscode.length()>=8) {
                    try{
                        JSONObject result = BaiduAPI.baiduAPI(srcDes, tgtDes);
                        System.out.println(result.toString(2));
                        Double score = result.getDouble("score");
                        if(score >= maxScore) {
                            maxScore = score;
                            resultHscode = tgtRes.getString("hscode");
                        }
                    }catch (Exception e) {
                        continue;
                    }
                }
            }
            pred = conn.prepareStatement(tgtBuilder2.toString());
            pred.setObject(1, resultHscode);
            System.out.println("target"+pred.toString());
            ResultSet tgtRes2 = pred.executeQuery();
            if(tgtRes2.next()) {
                sm.setHscode(tgtRes2.getString("hscode"));
                sm.setDescription(tgtRes2.getString("description"));

                /*
                可分离出方法
                 */
                switch (tgtRegion.toLowerCase()) {
                    case "chn" :
                        sm.setAdded(tgtRes2.getString("added"));
                        sm.setFavor(tgtRes2.getString("most_favor"));
                        sm.setGeneral(tgtRes2.getString("general"));
                        break;
                    case "usa" :
                        sm.setAdded(tgtRes2.getString("column_2_rate_of_duty"));
                        sm.setFavor(tgtRes2.getString("special_rate_of_duty"));
                        sm.setGeneral(tgtRes2.getString("general_rate_of_duty"));
                        break;
                    case "deu" :
                        sm.setAdded("");
                        sm.setFavor(tgtRes2.getString("supplementary_unit"));
                        sm.setGeneral(tgtRes2.getString("conventional_rate_of_duty"));
                        break;
                    case "sgp" :
                        sm.setAdded(tgtRes2.getString("customs_full"));
                        sm.setFavor(tgtRes2.getString("duty_pref"));
                        sm.setGeneral(tgtRes2.getString("gst"));
                        break;
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("sm"+sm.toString());
        return sm;
    }

}
