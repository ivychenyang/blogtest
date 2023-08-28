package blog.ex.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.ex.model.dao.UserDao;
import blog.ex.model.entity.UserEntity;

@Service
public class UserService {

@Autowired
private UserDao userDao;

public boolean createAccount (String userName,String email,String password) {
	UserEntity userEntity = userDao.findByEmail(email);
	if(userEntity==null) {
		LocalDateTime registerDate = LocalDateTime.now();
		userDao.save(new UserEntity(userName,email,password,registerDate));
		return true;
	}else {
		return false;
	}

}

public UserEntity loginAccount(String email,String password) {
	
	UserEntity userEntity = userDao.findByEmailAndPassword(email,password);
	if(userEntity==null) {
		return null;
		
	}else {
		return userEntity;
	}
}


}