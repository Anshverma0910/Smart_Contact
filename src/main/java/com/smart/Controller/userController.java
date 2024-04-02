package com.smart.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.Dao.ContactRepository;
import com.smart.Dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user")
public class userController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;

	private String name;
	
	@ModelAttribute
	public void addcommon(Model model, Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("user",user);
	}
	
	@GetMapping("/index")
	public String dashboard(Model model) {
		model.addAttribute("title","User Dashboard");
		return "normal/user_dashboard";
	}
	
	@GetMapping("/add-contact")
	public String addContact(Model model) {
		model.addAttribute("title","Add Contact");
		model.addAttribute("contact", new Contact());
		System.out.println("inside add contact");
		return "normal/add_contact";
		
	}
	
	@PostMapping("/proccess_contact")
	public String formproccess(@Valid @ModelAttribute("contact") Contact contact,
			BindingResult result,
			@RequestParam("image") MultipartFile file,
			Principal principal,
			HttpSession session,
			Model model)
	{
		try {
		System.out.println("form process");
		System.out.println("Data  "+contact);
		System.out.println("getname :  "+principal.getName());
		
		// file uploading
		if(file.isEmpty()) {
			contact.setProfileimage("bydefault.png");
		}
		else {
			contact.setProfileimage(file.getOriginalFilename());
			File savefile = new ClassPathResource("static/img").getFile();
			Path path = Paths.get(savefile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
			
		}
		
		
		
		User user = userRepository.findByEmail(principal.getName());
		
		contact.setUser(user);
		
		user.getContacts().add(contact);
		
		this.userRepository.save(user);
		session.setAttribute("message", new Message("Contact added Successfully !!","success"));
		model.addAttribute("contact",new Contact());
		return "normal/add_contact";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Error"+e.getMessage());
			session.setAttribute("message", new Message(" Something went wrong !!","danger"));
			model.addAttribute("contact",contact);
			return "normal/add_contact";
		}
	}
	
	@GetMapping("/view-contacts/{page}")
	public String viewContact(@PathVariable("page") Integer  page, Model m, Principal principal)
	{
		m.addAttribute("title","view-user-contacts");
		
		String uname = principal.getName();
		User user = this.userRepository.findByEmail(uname);
		
		Pageable pageable = PageRequest.of(page,6);
		
		Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(),pageable);
		
		
		m.addAttribute("contacts",contacts);
		m.addAttribute("currentPage",page);
		m.addAttribute("totalPage",contacts.getTotalPages());
		return "normal/view_contacts";
	}
}
