package blog.ex.model.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

//在@data Lombok Getter、Setter、toString、equals、hashCode方法自动生成
@Data

//@NoArgsConstructor注解 与Lombok库一起使用，可以自动为类生成无参构造函数
@NoArgsConstructor

//@AllArgsConstructor注解 与Lombok库一起使用，用于自动生成一个包含所有成员变量的构造函数
@AllArgsConstructor

//@RequiredArgsConstructor是lombok库提供的一个注解，用于自动生成构造函数，该构造函数包含所有被标记为final或被@NonNull注解修饰的成员变量作为参数
@RequiredArgsConstructor

//@Entity是java Persistence API（JAP）的注解，通常在使用ORM（对象关系映射）框架（如Hibernate）时用于标识一个类作为持久化实体。
//在JPA中，@Entity注解将一个普通的java类映射到数据库中的表。这意味着该类的实例可以在数据库中存储和检索，并且表的结构和数据与类的属性和对象之间存在映射关系。
@Entity

//@Table 是java Persistence API（JPA）的注解之一，用于指定实体类与数据库表之间的映射关系，通过@Table注解，你可以定义表的名称，模式，索引等信息。

@Table(name="blogs")
public class BlogEntity {

	//@Id是Java Persistence API（JPA）的注解之一，用于标识实体类的一个属性作为主键。
	//在JPA中，每个实体类必须有一个唯一的标识符（主键），用于在数据库中唯一标识该实体。
	//@Id注解被用于标识实体类的一个属性作为主键，以便JPA框架知道哪个属性应该被映射到数据库表的主键列。
	
	@Id
	//@Column是java persistence API（JPA）的注解之一，用于指定实体类属性与数据库表列之间的映射关系。
	//通过@Column注解，你可以自定义列的名称、类型、长度、是否为空等信息
	@Column(name="blog_id")
	
	//@GeneratedValue 是java persistence API（JPA）的注解之一，用于指定主键生成策略，在实体类中，当你希望主键的值由数据库自动生成时，可以使用@GeneratedValue注解。
	//在jpa注解中，自动生成主密钥的方法
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long blogId;
	//@NonNull是Lombok库中的一个注解，用于标识一个字段或方法参数不能为空。
	//通过使用@NonNull注解，你可以在编译时自动生成非空检查的代码，以避免在运行时出现空指针异常。
	
	@NonNull
	@Column(name="blog_title")
	private String blogTitle;

	@NonNull
	//	@DateTimeFormat是Spring框架中的注解，用于在实体类属性上指定日期格式，以便在对象和字符串之间进行转换。
	//这在出来前端输入、数据库存储和显示非常有用
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name="register_date")
	private LocalDate registerDate;
	
	@NonNull
	@Column(name="blog_detail")
	private String blogDetail;
	
	@NonNull
	@Column(name="category")
	private String category;
	
	@NonNull
	@Column(name="user_id")
	private long userId;
	
	@Column(name="blog_image")
	private String blogImage;

	public BlogEntity(@NonNull String blogTitle, @NonNull LocalDate registerDate, @NonNull String blogDetail,
			@NonNull String category, @NonNull long userId, String blogImage) {
		super();
		this.blogTitle = blogTitle;
		this.registerDate = registerDate;
		this.blogDetail = blogDetail;
		this.category = category;
		this.userId = userId;
		this.blogImage = blogImage;
	}

	public BlogEntity() {
		super();
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	public String getBlogDetail() {
		return blogDetail;
	}

	public void setBlogDetail(String blogDetail) {
		this.blogDetail = blogDetail;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getBlogImage() {
		return blogImage;
	}

	public void setBlogImage(String blogImage) {
		this.blogImage = blogImage;
	}

}
