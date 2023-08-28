package blog.ex.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.ex.model.dao.BlogDao;
import blog.ex.model.entity.BlogEntity;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;

	public List<BlogEntity> findAllBlogPost(Long userId) {
		if (userId == null) {
			return null;

		} else {
			return blogDao.findByUserId(userId);
		}

	}

	// 调用dao里面的save方法，把entity里的值插入到数据库里
	public int addBlog(String title, String content, String image, LocalDate localDate, Long userid, String category) {
		int result = 0;
		if (this.blogDao.save(new BlogEntity(title, localDate, content, category, userid, image)) != null) {
			result = 1;
		}
		return result;
	}

	public boolean deleteBlog(Long blogId) {
		if (blogId == null) {
			return false;
		} else {
		}
		blogDao.deleteByBlogId(blogId);
		return true;

	}

	public BlogEntity getBlogPost(Long blogId) {
		if (blogId == null) {
			return null;
		} else {
			return blogDao.findByBlogId(blogId);
		}

	}


	public boolean editBlogPost(String blogTitle, String image, LocalDate registerDate, String blogDetail, String category,
			Long userId, Long blogId) {
		BlogEntity blogList = blogDao.findByBlogId(blogId);
		if (userId == null) {
			return false;
		} else {
			blogList.setBlogId(blogId);
			blogList.setBlogTitle(blogTitle);
			blogList.setBlogImage(image);
			blogList.setBlogDetail(blogDetail);
			blogList.setCategory(category);
			blogList.setRegisterDate(registerDate);
//			blogList.getRegisterDate();
//			blogList.getCategory();
//			blogList.getBlogDetail();
			blogDao.save(blogList);
			return true;

		}
	}
}