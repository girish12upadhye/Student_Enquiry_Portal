package org.girishTechUniverse.controller;

import java.util.List;

import org.girishTechUniverse.binding.SearchCriteria;
import org.girishTechUniverse.entity.StudentEnq;
import org.girishTechUniverse.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	
	@Autowired
	private EnquiryService enquiryService;
	
	@GetMapping("/enquiry")
	public String enqPage(Model model) {
		model.addAttribute("enq", new StudentEnq());
		return "addEnqView";
	}
	
	@PostMapping("/enquiry")
	public String addEnquiry(@ModelAttribute("enq")  StudentEnq enq, HttpServletRequest req, Model model) {
		
	
		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("CID");

		if (cid == null) {
			return "redirect:/";
		}

		enq.setCid(cid);
		
		boolean addEnq = enquiryService.addEnq(enq);
		
		if (addEnq) {
			model.addAttribute("smsg", "Enquiry Added Successfully !!");
		} else {
			model.addAttribute("errmsg", "Enquiry Failed !!");
		}
		return "addEnqView";
	}
	
	@GetMapping("/enquiries")
	public String viewEnquiries(HttpServletRequest req,Model model) {
		

		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("CID");

		System.out.println("Cid : " + cid);
		
		if (cid == null) {
			return "redirect:logout";
		}
		
		model.addAttribute("sc", new SearchCriteria());
		
		List<StudentEnq> enquiriesList = enquiryService.getEnquiries(cid, new SearchCriteria());
		
		model.addAttribute("enquiries", enquiriesList);
		return "displayEnqView";
	}
	
	@PostMapping("/filter-enquiries")
	public String filterEnquiries(@ModelAttribute("sc") SearchCriteria sc, HttpServletRequest req, Model model) {
		

		HttpSession session = req.getSession(false);
		Integer cid = (Integer) session.getAttribute("CID");

		if (cid == null) {
			return "redirect:logout";
		}
		
		List<StudentEnq> enquiriesList = enquiryService.getEnquiries(cid, sc);
		model.addAttribute("enquiries", enquiriesList);
		
		return "filterEnqView";
	}

}
