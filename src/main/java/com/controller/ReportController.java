package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {
	@RequestMapping("/report")
	protected ModelAndView getReportPage() {
		ModelAndView model = new ModelAndView("report");
		return model;
	}
	
	@RequestMapping("/dashboardAdmin")
	protected ModelAndView getDashboardAdminPage() {
		ModelAndView model = new ModelAndView("dashboardAdmin");
		return model;
	}
}
