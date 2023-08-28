package blog.ex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.ex.model.entity.UserEntity;
import blog.ex.service.UserService;
import jakarta.servlet.http.HttpSession;

/* RequestMapping 是Spring Framework中的一个注解，用于将一个方法映射到特定的URL路径和HTTP请求方法。它是Spring MVC框架中最常用的注解之一，
 * 用于处理不同类型的HTTP请求。
使用@RequestMapping注解，你可以将一个方法映射到多个URL路径，并指定支持的HTTP请求方法（GET、POST、PUT、DELETE等）
*/
@RequestMapping("/user")
@Controller
public class UserLoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;

	@GetMapping("/login")
	public String getUserLoginPage() {
		return "login.html";
	}

	/*
	 * @PostMapping是SpringFramework中的一个注解，用于处理HTTP
	 * POST请求。它通常用于在控制器类中标识处理POST请求方法。使用@PostMapping注解，你可以将方法映射到特定的URL路径，
	 * 并在接收到POST请求时执行该方法。
	 * 
	 * @PostMapping注解使你可以轻松地将方法与POST请求关联起来，并处理接受的数据，这是Spring
	 * MVC框架中用于处理不同类型HTTP请求的一种方式。
	 */
	// login的处理
	@PostMapping("/login/process")
	public String login(@RequestParam String email, @RequestParam String password) {

		UserEntity userList = userService.loginAccount(email, password);

		if (userList == null) {
			return "redirect:/user/login";

		} else {
			session.setAttribute("user", userList);
			return "redirect:/user/blog/list";
		}
	}

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/user/login";

	}
}
