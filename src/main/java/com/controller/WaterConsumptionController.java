package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.dbUtil.DBConnect;
import com.dbUtil.WaterConDAO;
import com.model.WaterValidation;

@Controller
public class WaterConsumptionController {

    @RequestMapping("/waterConsumption")
    protected ModelAndView getWaterConsumptionPage(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        int userID = (Integer) session.getAttribute("userID");

        try (Connection conn = DBConnect.openConnection()) {
            LocalDate currentDate = LocalDate.now();
            String sql = "SELECT * FROM application WHERE userID = ? AND DATE_FORMAT(`date`, '%Y') = ? AND DATE_FORMAT(`date`, '%m') = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                stmt.setInt(2, currentDate.getYear());
                stmt.setInt(3, currentDate.getMonthValue());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        if (rs.getInt("waterID") > 0) {
                            // Water consumption already submitted
                            WaterConDAO waterConDAO = new WaterConDAO();
                            WaterValidation waterCon = waterConDAO.getWaterConDetails(rs.getInt("waterID"));
                            ModelAndView model = new ModelAndView("waterConsumption");
                            model.addObject("userID", userID);
                            model.addObject("waterCon", waterCon);
                            return model;
                        } else {
                            // Redirect to submission form
                            ModelAndView model = new ModelAndView(new RedirectView("waterConsumptionForm"));
                            model.setViewName("redirect:/waterConsumptionForm");
                            return model;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ModelAndView model = new ModelAndView(new RedirectView("waterConsumptionForm"));
        model.setViewName("redirect:/waterConsumptionForm");
        return model;
    }

    @RequestMapping("/waterConsumptionForm")
    protected ModelAndView getWaterConsumptionFormPage(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("waterConsumptionForm");
        HttpSession session = request.getSession();
        model.addObject("userID", (Integer) session.getAttribute("userID"));
        return model;
    }

    @RequestMapping("/waterConsumptionEdit")
    protected ModelAndView getWaterConsumptionEditPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userID = (Integer) session.getAttribute("userID");

        try (Connection conn = DBConnect.openConnection()) {
            LocalDate currentDate = LocalDate.now();
            String sql = "SELECT * FROM application WHERE userID = ? AND DATE_FORMAT(`date`, '%Y') = ? AND DATE_FORMAT(`date`, '%m') = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                stmt.setInt(2, currentDate.getYear());
                stmt.setInt(3, currentDate.getMonthValue());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        if (rs.getInt("waterID") > 0) {
                            // Water consumption already submitted
                            WaterConDAO waterConDAO = new WaterConDAO();
                            WaterValidation waterCon = waterConDAO.getWaterConDetails(rs.getInt("waterID"));
                            ModelAndView model = new ModelAndView("waterConsumptionEdit");
                            model.addObject("userID", userID);
                            model.addObject("waterCon", waterCon);
                            return model;
                        } else {
                            // Redirect to submission form
                            ModelAndView model = new ModelAndView(new RedirectView("waterConsumptionForm"));
                            model.setViewName("redirect:/waterConsumptionForm");
                            return model;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ModelAndView model = new ModelAndView(new RedirectView("waterConsumptionForm"));
        model.setViewName("redirect:/waterConsumptionForm");
        return model;
    }

    @RequestMapping("/waterSubmit")
    protected ModelAndView getWaterSubmitPage(HttpServletRequest request,
            @RequestParam("proportionalFactor") float proportionalFactor,
            @RequestParam("waterUsageRM") float waterUsageRM,
            @RequestParam("waterUsageM3") float waterUsageM3, @RequestParam MultipartFile billImage)
            throws SQLException, IOException {

        HttpSession session = request.getSession();
        int userID = (Integer) session.getAttribute("userID");
        Connection conn = DBConnect.openConnection();
        LocalDate currentDate = LocalDate.now();
        String applicationSql = "SELECT * FROM application WHERE userID = ? AND DATE_FORMAT(`date`, '%Y') = ? AND DATE_FORMAT(`date`, '%m') = ?";
        PreparedStatement stmt = conn.prepareStatement(applicationSql);
        stmt.setInt(1, userID);
        stmt.setInt(2, currentDate.getYear());
        stmt.setInt(3, currentDate.getMonthValue());

        byte[] fileBytes = null;
        if (!billImage.isEmpty()) {
            fileBytes = billImage.getBytes();
        }

        String message = null;

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                if (rs.getInt("waterID") > 0) {
                    // Update water consumption
                    WaterConDAO waterConDAO = new WaterConDAO();
                    if (fileBytes != null) {
                        waterConDAO.updateWaterCon(proportionalFactor, waterUsageRM, waterUsageM3, fileBytes,
                                rs.getInt("waterID"));
                        message = "Update successfully";
                    } else {
                        waterConDAO.updateWaterConNoProof(proportionalFactor, waterUsageRM, waterUsageM3,
                                rs.getInt("waterID"));
                        message = "Update successfully";
                    }
                } else {
                    // Insert water consumption
                    WaterConDAO waterConDAO = new WaterConDAO();
                    waterConDAO.insertWaterConAndUpdateApplication(proportionalFactor, waterUsageRM, waterUsageM3,
                            fileBytes, rs.getInt("applicationID"));
                    message = "Submit successfully";
                }
            } else {
                // Create water consumption
                WaterConDAO waterConDAO = new WaterConDAO();
                waterConDAO.insertWaterConAndCreateApplication(proportionalFactor, waterUsageRM, waterUsageM3,
                        fileBytes, userID, currentDate);
                message = "Submit successfully";
            }
        }

        ModelAndView model = new ModelAndView("waterSubmitResponse");
        model.addObject("message", message);
        return model;
    }
}
