package com.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dbUtil.DBConnect;
import com.dbUtil.WaterValidateDAO;
import com.model.WaterValidation;

@Controller
public class WaterValidationController {
    @RequestMapping("/waterValidation")
    protected ModelAndView getWaterValidationPage() throws SQLException {

        ModelAndView model = new ModelAndView("waterValidation");
        ArrayList<WaterValidation> waterValidationList = new ArrayList<WaterValidation>();
        Connection conn = DBConnect.openConnection();
        String sql = "SELECT * FROM waterconsumption WHERE status ='PENDING';";
        try (ResultSet rs = conn.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                WaterValidation waterValidation = new WaterValidation();
                waterValidation.setWaterID(rs.getInt("waterID"));
                waterValidation.setWaterProportionalFactor(rs.getFloat("waterProportionalFactor"));
                waterValidation.setWaterUsageValueRM(rs.getFloat("waterUsageValueRM"));
                waterValidation.setWaterUsageValueM3(rs.getFloat("waterUsageValueM3"));
                waterValidation.setWaterConsumptionProof(rs.getBytes("waterConsumptionProof"));
                waterValidation.setStatus(rs.getString("status"));
                waterValidationList.add(waterValidation);
            }
        }

        model.addObject("waterValidationList", waterValidationList);
        return model;
    }

    @RequestMapping("/waterValidationApprove")
    protected ModelAndView waterValidationApprove(@RequestParam("waterID") int waterID) throws SQLException {

        WaterValidateDAO waterValidateDAO = new WaterValidateDAO();
        waterValidateDAO.approveWater(waterID);

        ModelAndView model = new ModelAndView("waterValidationResponse");
        model.addObject("message", "Approve successfully");
        return model;
    }

    @RequestMapping("/waterValidationDisapprove")
    protected ModelAndView waterValidationDisapprove(@RequestParam("waterID") int waterID) throws SQLException {

        WaterValidateDAO waterValidateDAO = new WaterValidateDAO();
        waterValidateDAO.disapproveWater(waterID);

        ModelAndView model = new ModelAndView("waterValidationResponse");
        model.addObject("message", "Disapprove successfully");
        return model;
    }

    @RequestMapping("/waterValidationDelete")
    protected ModelAndView waterValidationDelete(@RequestParam("waterID") int waterID) throws SQLException {

        WaterValidateDAO waterValidateDAO = new WaterValidateDAO();
        waterValidateDAO.deleteWater(waterID);

        ModelAndView model = new ModelAndView("waterValidationResponse");
        model.addObject("message", "Delete successfully");
        return model;
    }
}