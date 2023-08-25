package blog.ex.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blog.ex.model.entity.UserEntity;
import blog.ex.service.UserService;
import jakarta.servlet.http.HttpSession;


@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@BeforeEach
	public void prepareDate() {
		UserEntity userEntity = new UserEntity("Akemi","a@b.com","123456",LocalDateTime.now());
		when(userService.loginAccount(eq("a@b.com"), eq("123456"))).thenReturn(userEntity);
		when(userService.loginAccount(eq("a@b.com.com"), eq("abcd"))).thenReturn(null);
	}
	//login page
		@Test
		public void testGetUserLoginPage_succeed() throws Exception{
			RequestBuilder request = MockMvcRequestBuilders
					.get("/user/login");
			mockMvc.perform(request)
			.andExpect(view().name("login.html"));
		}
		//login成功场合
		@Test
		public void testLogin_Successful() throws Exception{
			RequestBuilder request = MockMvcRequestBuilders
					.post("/user/login/process")
					.param("email", "a@a.com")
					.param("password","123456");
			MvcResult result = mockMvc.perform(request)
					.andExpect(redirectedUrl("user/blog/list")).andReturn();
			//register实行结果返回方法
			//Session的取得
			HttpSession session = result.getRequest().getSession();
			
			//sessionがちんと设定 确定
			UserEntity userList = (UserEntity) session.getAttribute("user");
			assertNotNull(userList);
		
			assertEquals ("a@a.com",userList.getEmail());
			assertEquals ("123456",userList.getPassword());
		}
		//email 不对 失败的例子
				@Test
				public void testLogin_WrongEmail() throws Exception{
					RequestBuilder request = MockMvcRequestBuilders
							.post("/user/login/process")
							.param("email", "bbbbbb.com")
							.param("password","123456");
					MvcResult result = mockMvc.perform(request)
							.andExpect(redirectedUrl("/user/login")).andReturn();
					//register实行结果返回方法
					//Session的取得
					HttpSession session = result.getRequest().getSession();
					
					//sessionがちんと设定 确定
					UserEntity userList = (UserEntity) session.getAttribute("user");
					assertNull(userList);
					
				}
		//possword 不对 失败的例子
		@Test
		public void testLogin_WrongPassword() throws Exception{
			RequestBuilder request = MockMvcRequestBuilders
					.post("/user/login/process")
					.param("email", "a@a.com")
					.param("password","abcd");
			MvcResult result = mockMvc.perform(request)
					.andExpect(redirectedUrl("/user/login")).andReturn();
			//register实行结果返回方法
			//Session的取得
			HttpSession session = result.getRequest().getSession();
			
			//sessionがちんと设定 确定
			UserEntity userList = (UserEntity) session.getAttribute("user");
			assertNull(userList);
			
		}
		//possword null
				@Test
				public void testLogin_NullPassword() throws Exception{
					RequestBuilder request = MockMvcRequestBuilders
							.post("/user/login/process")
							.param("email", "a@a.com")
							.param("password","");
					MvcResult result = mockMvc.perform(request)
							.andExpect(redirectedUrl("/user/login")).andReturn();
					//register实行结果返回方法
					//Session的取得
					HttpSession session = result.getRequest().getSession();
					
					//sessionがちんと设定 确定
					UserEntity userList = (UserEntity) session.getAttribute("user");
					assertNull(userList);
					
				}
				//Email null
				@Test
				public void testLogin_NullEmail() throws Exception{
					RequestBuilder request = MockMvcRequestBuilders
							.post("/user/login/process")
							.param("email", "")
							.param("password","123456");
					MvcResult result = mockMvc.perform(request)
							.andExpect(redirectedUrl("/user/login")).andReturn();
					//register实行结果返回方法
					//Session的取得
					HttpSession session = result.getRequest().getSession();
					
					//sessionがちんと设定 确定
					UserEntity userList = (UserEntity) session.getAttribute("user");
					assertNull(userList);
					
				}
	}