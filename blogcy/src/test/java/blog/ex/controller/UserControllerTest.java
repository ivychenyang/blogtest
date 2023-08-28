package blog.ex.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blog.ex.service.UserService;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	@BeforeEach
	public void prepareData() {
		when(userService.createAccount(eq("Ana"), eq("alice1234"),any())).thenReturn(false);
		when(userService.createAccount("Alice", "alice1234","123456")).thenReturn(true);

	}//页面成功登录
	@Test
	public void testGetRegisterPage_Succeed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.get("/user/register");
		
			mockMvc.perform(request)
			.andExpect(view().name("register.html"));
	}
	//when(userService.createAccount("Alice", "alice1234","123456")).thenReturn(true);
	//全部输入正确登录
	@Test
	public void testRegister_Succeed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.post("/user/register/process")
				.param("userName","Alice")
				.param("email","alice1234")
				.param("password", "123456");
		
			mockMvc.perform(request)
			.andExpect(redirectedUrl("/user/login")).andReturn();;
	}
	
	//username,password not null,email null
		@Test
		public void testRegister_Fail() throws Exception{
			RequestBuilder request = MockMvcRequestBuilders
					.post("/user/register/process")
					.param("userName","Alice")
					.param("email","123@123.com")
					.param("password", "123");
			
				mockMvc.perform(request)
				.andExpect(view().name("register.html"));
		}
		
	//email null，email and username not null
	@Test
	public void testRegister_emailFail() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.post("/user/register/process")
				.param("userName","Alice")
				.param("email","")
				.param("password", "123");
		
			mockMvc.perform(request)
			.andExpect(view().name("register.html"));
	}
	// password null，email and username not null
			@Test
			public void testRegister_usernameFail() throws Exception{
				RequestBuilder request = MockMvcRequestBuilders
						.post("/user/register/process")
						.param("userName","Alice")
						.param("email","123@123.com")
						.param("password", "");
				
					mockMvc.perform(request)
					.andExpect(view().name("register.html"));
			}
}
