package blog.ex.model.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.ex.model.entity.BlogEntity;
import jakarta.transaction.Transactional;

//JpaRepository 是Spring Date JPA提供的一个接口，用于简化数据库操作和数据库访问的代码。
//它继承PagingAndSo
public interface BlogDao extends JpaRepository<BlogEntity, Long>{
	
	
	List<BlogEntity> findByUserId(Long userId);
	//把blogeEtity里的值插入到数据库，保存
	BlogEntity save(BlogEntity blogEntity);
	
	//按照blogTitle和registerDate来查询对应的值
	BlogEntity findByBlogTitleAndRegisterDate(String blogTitle,LocalDate registerDate);
	
	//按照id来查值
	BlogEntity findByBlogId(Long blogId);
	
	//根据blogid 删除一个blog
	@Transactional
    List<BlogEntity> deleteByBlogId(Long blogId);
	
}
