package blog.ex.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.ex.model.dao.UserDao;
import blog.ex.model.entity.BlogEntity;
import blog.ex.model.entity.UserEntity;
import blog.ex.service.BlogService;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/user/blog")
@Controller
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private HttpSession session;
	
	@GetMapping("/list")
	public String getBlogListPage(Model model) {
		UserEntity userList = null;
		if (session.getAttribute("user") == null) {
			return "redirect:/user/login";// 重定向，重新向user/login发送一个全新请求
		}

		userList = (UserEntity) session.getAttribute("user");
		Long userId = userList.getUserId();
		String userName = userList.getUserName();
		model.addAttribute("userName", userName);
		List<BlogEntity> blogList = this.blogService.findAllBlogPost(userId);
		model.addAttribute("blogList", blogList);
		return "blogView.html";// 转发，

	}

	//記事登録の処理
	@GetMapping("/register")
	public String getBlogRegisterPage(Model model) {
		UserEntity userList = null;
		if (session.getAttribute("user") == null) {
			return "redirect:/user/login";
		}
		return "blogLogin.html";

	}
	
	@PostMapping("/register/process")
	public String blogRegistProcess(@RequestParam String title, @RequestParam MultipartFile image,
			@RequestParam String category, @RequestParam String content,@RequestParam LocalDate registerDate) {
		UserEntity userEntity = null;
		if (session.getAttribute("user") == null) {
			return "redirect:/user/login";
		}
		userEntity = (UserEntity) session.getAttribute("user");
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + image.getOriginalFilename();
		try {
			Files.copy(image.getInputStream(), Path.of("src/main/resources/static/img/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.blogService.addBlog(title, content, fileName, registerDate, userEntity.getUserId(), category);
		return "redirect:/user/blog/list";
	}
	@GetMapping("/delete/{blogId}")
	public String getBlogDeleteDetailPage(@PathVariable Long blogId,Model model) {
		UserEntity userList = (UserEntity) session.getAttribute("user");
		Long userId = userList.getUserId();
		String userName = userList.getUserName();
		model.addAttribute("userName",userName);
		 blogService.deleteBlog(blogId);
			return"redirect:/user/blog/list";
	}
	//修改 編集
	@GetMapping("/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId,Model model) {
		UserEntity userList = (UserEntity) session.getAttribute("user");
		String userName = userList.getUserName();
		model.addAttribute("userName",userName);
		BlogEntity blogList = blogService.getBlogPost(blogId);
		if(blogList == null) {
			return"redirect:/user/blog/list";
		}else {
			model.addAttribute("blogList", blogList);
			model.addAttribute("editMessage", "記事編集");
			return"blogEdit.html";
		}
		
	}
	

//	
//	@PostMapping("/update")
//	public String blogUpdate(@RequestParam String blogTitle,
//			@RequestParam LocalDate registerDate,
//			@RequestParam String category,
//			@RequestParam String blogDetail,
//			@RequestParam Long blogId,Model model){
//		UserEntity userList = (UserEntity) session.getAttribute("user");
//		Long userLd = userList.getUserId();
//		if(blogService.editBlogPost(blogTitle, registerDate, blogDetail, category, userLd, blogId)) {
//			return "redirect:/user/blog/list";
//		}else {
//			model.addAttribute("registerMessage","更新に失敗しました");
//			return "blogLogin.html";
//		}
//		
//	}
//	
//	//图片更新
//	@PostMapping("/image/update")
//	public String blogImgUpdate(
//			@RequestParam MultipartFile blogImage,
//			@RequestParam Long blogId,Model model) {
//		UserEntity userList = (UserEntity) session.getAttribute("user");
//		Long userId = userList.getUserId();
//		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + blogImage.getOriginalFilename();
//		try {
//			Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		if(blogService.editBlogImage(blogId, fileName, userId)) {
//			return "blogEdit.html";
//		}else {
//			BlogEntity blogList = blogService.getBlogPost(blogId);
//			model.addAttribute("blogList",blogList);
//			model.addAttribute("editImageMessage", "更新失敗です");
//			return "blogView.html";
//		}
//
//	}
	
	@PostMapping("/update")
	public String blogUpdate(@RequestParam String blogTitle,
			@RequestParam MultipartFile image,
			@RequestParam LocalDate registerDate,
			@RequestParam String category,
			@RequestParam String blogDetail,
			@RequestParam Long blogId,Model model){
		UserEntity userList = (UserEntity) session.getAttribute("user");
		Long userLd = userList.getUserId();
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + image.getOriginalFilename();
		try {
			Files.copy(image.getInputStream(), Path.of("src/main/resources/static/img/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(blogService.editBlogPost(blogTitle, fileName, registerDate, blogDetail, category, userLd, blogId)) {
			return "redirect:/user/blog/list";
		}else {
			model.addAttribute("registerMessage","更新に失敗しました");
			return "blogLogin.html";
		}
		
	}

	@GetMapping("/logout")
	 public String logout() {
		 session.invalidate();
		 return "redirect:/user/login";
		   }
}
