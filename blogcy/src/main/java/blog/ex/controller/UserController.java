package blog.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import blog.ex.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	//自动装配 不用new 直接使用对象
	@Autowired
	private UserService userService;
	
	//
	@GetMapping("/register")
	public String getUserRegisterPage() {
		return "register.html";
		
	}
	@PostMapping("/register/process")
	public String register(@RequestParam String userName,@RequestParam String email, @RequestParam String password) {
		
		if(this.userService.createAccount(userName, email, password)) {
			return "redirect:/user/login";
		}
		
		return "register.html";
		
	}
	
	
	@GetMapping("/login")
	public String getUserLoginPage() {
		return "login.html";
	}
	

}
