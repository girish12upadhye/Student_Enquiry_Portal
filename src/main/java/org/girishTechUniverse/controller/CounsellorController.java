package org.girishTechUniverse.controller;

import org.girishTechUniverse.binding.DashboardResponse;
import org.girishTechUniverse.entity.Counsellor;
import org.girishTechUniverse.service.CounsellorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	
	
	@Autowired
	private CounsellorService counsellorService;
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);
		
		session.invalidate();
		
		
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("counsellor", new Counsellor());
		return "loginView";
	}
	
	@GetMapping("/dashboard")
	public String buildDashboard(HttpServletRequest req, Model model) {
		
		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("CID");
		
		DashboardResponse dashboardInfo = counsellorService.getDashboardInfo(cid);
		
		model.addAttribute("dashboard",dashboardInfo);
		
		return "dashboardView";
	}
	
	@GetMapping("/register")
	public String regPage(Model model) {
		model.addAttribute("counsellor", new Counsellor());
		return "registerView";
	}
	
	@PostMapping("/register")
	public String handleRegistration(Counsellor c ,Model model) {
		
		String msg = counsellorService.saveCounseller(c);
		
		model.addAttribute("msg", msg);
		return "registerView";
	}
	
	@PostMapping("/login")
	public String handleLogin(Counsellor c, HttpServletRequest req, Model model) {
		
		Counsellor obj = counsellorService.loginCheck(c.getEmail(), c.getPwd());
		
		if(obj == null) {
			model.addAttribute("errMsg","Invalid Credentials");
			return "loginView";
		}
		
		HttpSession session = req.getSession(true);
		
		session.setAttribute("CID", obj.getCid());
		
		
		return "redirect:dashboard";
	}
	
	@GetMapping("/forget-pwd")
	public String recoverPwdPage(Model model) {
		return "forgotPwdView";
	}
	
	@GetMapping("/recover-pwd")
	public String recoverPwd(@RequestParam String email, Model model) {
		boolean status = counsellorService.recoverPwd(email);
		if(status) {
			model.addAttribute("smsg", "Pwd sent to your email");
		}else {
			model.addAttribute("errmsg", "Invalid email");
		}
		return "forgotPwdView";
	}
}
