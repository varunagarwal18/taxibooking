package com.formTest.SubmitForm.controller;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formTest.SubmitForm.entities.Email;
import com.formTest.SubmitForm.entities.Passangers;
import com.formTest.SubmitForm.services.MailService;
import com.formTest.SubmitForm.services.MailServiceImp;
import com.formTest.SubmitForm.services.PassangerServices;

@Controller
public class FormController {
	
	@Autowired
	private PassangerServices passangerService;
	
	@RequestMapping("/")
	public String home(HttpServletRequest req)
	{
		System.out.println("Home page is requested");
		HttpSession session=req.getSession();
		session.setAttribute("msg","");
		return "login.jsp";
	}
	
	@PostMapping(value="addCust")
	public String addCustomer(HttpServletRequest req)
	{
		MailService mailService=new MailServiceImp();
		Email email = new Email();
		email.setFrom("shubhamgupta20041998@gmail.com");
		email.setSubject("Welcome to our family");
		email.setTo("shubhamgupta20041998@gmail.com");
		email.setMessageText("We are glad to add you to our ever growing family and hope to surve you well");
		//mailService.sendMail(email);
		
		Random rnd = new Random();
	    int number = rnd.nextInt(999999);
		Passangers passangers = new Passangers();
		passangers.setName(req.getParameter("uname"));
		passangers.setPhoneNumber(req.getParameter("mnumber"));
		passangers.setEmail(req.getParameter("email"));
		passangers.setPassangerId(String.format("%06d", number));
		passangers.setPassword(req.getParameter("pass"));
		System.out.println(passangers);
		try {
			passangerService.savePassangerDetails(passangers);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		HttpSession session=req.getSession();
		session.setAttribute("msg","You have been successfully enrolled please login!");
		return "login.jsp";
	}
	@PostMapping("logincust")
	public String loginCustomer(HttpServletRequest req)
	{
		try 
		{
			Passangers passanger = passangerService.getPassangerDetails(req.getParameter("email"));
			
			if(passanger==null)
			{
				HttpSession session=req.getSession();
				session.setAttribute("msg","email or password is wrong, please try again!");
				return "login.jsp";
				
			}
			return "index.jsp";
		} 
		catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpSession session=req.getSession();
		session.setAttribute("msg","email or password is wrong, please try again!");
		return "login.jsp";
		
	}
	
	@RequestMapping("result")
	public String booked(HttpServletRequest req)
	{
		String name = req.getParameter("customer_name");
		String phone = req.getParameter("phone_number");
		String email = req.getParameter("email_address");
		String taxi = req.getParameter("taxi");
		String extras = req.getParameter("extras");
		String pickupDate = req.getParameter("pickup_time");
		String pickupPlace = req.getParameter("pickup_place");
		String dropOff = req.getParameter("dropoff_place");
		String comments = req.getParameter("comments");
		
		HttpSession session = req.getSession();
		session.setAttribute("name",name);
		session.setAttribute("taxt",taxi);
		session.setAttribute("date",pickupDate);
		
		return "result.jsp";
	}
}