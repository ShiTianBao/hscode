package com.td.controller;

import com.td.model.SearchModel;
import com.td.model.SelectModel;
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
public class SelectController {

    @RequestMapping("section")
    @ResponseBody
    public List<SelectModel> sectionSelect(String section) {
        System.out.println(section);

        Connection conn = null;
        PreparedStatement pred = null;
        List<SelectModel> list = new ArrayList<>();

        try{
            conn = DBUtils.getConnection();
            String sql = "select * from chapter where hscode in (select chapter from section_to_chapter where section = ?)";
            pred = conn.prepareStatement(sql);
            pred.setObject(1,section);
            ResultSet res = pred.executeQuery();

            while(res.next()) {
                SelectModel sm = new SelectModel();
                sm.setHscode(res.getString("hscode"));
                sm.setDescription(res.getString("description"));
                list.add(sm);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @RequestMapping("chapter")
    @ResponseBody
    public List<SelectModel> chapterSelect(String chapter) {
        System.out.println(chapter);

        Connection conn = null;
        PreparedStatement pred = null;
        List<SelectModel> list = new ArrayList<>();

        try{
            conn = DBUtils.getConnection();
            String sql = "select * from chapter where hscode like ? and length(hscode) = 4";
            pred = conn.prepareStatement(sql);
            pred.setObject(1, chapter + "%");
            System.out.println(pred.toString());
            ResultSet res = pred.executeQuery();

            while (res.next()) {
                SelectModel sm = new SelectModel();
                sm.setHscode(res.getString("hscode"));
                sm.setDescription(res.getString("description"));
                list.add(sm);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @RequestMapping("heading")
    @ResponseBody
    public List<SelectModel> headingSelect(String heading) {
        System.out.println(heading);

        Connection conn = null;
        PreparedStatement pred = null;
        List<SelectModel> list = new ArrayList<>();

        try {
            conn = DBUtils.getConnection();
            String sql = "select * from chapter where hscode like ? and length(hscode) = 6";
            pred = conn.prepareStatement(sql);
            pred.setObject(1, heading + "%");
            System.out.println(pred.toString());
            ResultSet res = pred.executeQuery();

            while (res.next()) {
                SelectModel sm = new SelectModel();
                sm.setHscode(res.getString("hscode"));
                sm.setDescription(res.getString("description"));
                list.add(sm);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @RequestMapping("sub_heading")
    @ResponseBody
    public List<SearchModel> subHeadingSelect(String subHeading) {
        System.out.println(subHeading);

        Connection conn = null;
        PreparedStatement pred = null;
        List<SearchModel> list = new ArrayList<>();

        try {
            conn = DBUtils.getConnection();
            String sql = "select * from local_chn where hscode like ? and length(hscode) > 6";
            pred = conn.prepareStatement(sql);
            pred.setObject(1, subHeading + "%");
            System.out.println(pred.toString());
            ResultSet res = pred.executeQuery();

            while (res.next()) {
                SearchModel sm = new SearchModel();
                sm.setHscode(res.getString("hscode"));
                sm.setDescription(res.getString("description"));
                sm.setAdded(res.getString("added"));
                sm.setFavor(res.getString("most_favor"));
                sm.setGeneral(res.getString("general"));
                list.add(sm);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
