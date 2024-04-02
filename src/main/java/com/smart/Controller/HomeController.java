package com.smart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.smart.Dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home Page");
		return "home";
	}
	@GetMapping("/home")
	public String home2(Model model) {
		model.addAttribute("title", "Home Page");
		return "home";
	}


	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About Page");
		return "about";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "SignUp Page");
		model.addAttribute("user", new User());
		return "signup";
	}

	@PostMapping("/do_register")
	public String datainput(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,HttpSession session) {
		
			System.out.println("tere maa ke jai");
			System.out.println(result);
		try {
			if(result.hasErrors()) 
				{
					throw new Exception();
				}
			
			String msg="M";
			if(userRepository.findByEmail(user.getEmail()) != null) {
				result.rejectValue("email", "error.email.duplicate", "Email address is already registered");
			}
			
			System.out.println("user"+user);
			
			user.setEnabled(true);
			user.setRole("ROLE_USER");
			user.setImageUrl("userDefault.jpg");
			String str = passwordEncoder.encode(user.getPassword());
			user.setPassword(str);
			
			System.out.println("before result:=>       "+str);
			User result1 = userRepository.save(user);
			System.out.println("mid result");
			System.out.println("result"+result1);
			System.out.println("after result");
			
			session.setAttribute("message",  new Message("Successfully Registered !!", "alert-success"));
			model.addAttribute("user",new User());
			return "signup";
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("inside catch");
			model.addAttribute("user",user);
			session.setAttribute("message",  new Message("Something went Wrong !!", "alert-danger"));
			return "signup";
		}
		
	}
	@GetMapping("/login")
	public String getMethodName(Model model) {
		model.addAttribute("title", "login Page");
		return "login";
	}
	

}
