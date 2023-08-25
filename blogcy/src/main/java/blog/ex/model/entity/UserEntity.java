package blog.ex.model.entity;

import java.time.LocalDateTime;

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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name="users")
public class UserEntity {
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userId;
	
	@NonNull
	@Column(name = "user_name")
	private String userName;
	
	@NonNull
	@Column(name = "email")
	 //フィールド変数で、メールアドレスを表します。 private String email
	 
	private String email;
	@NonNull
	@Column(name = "password")
	private String password;
	
	@NonNull
	@Column(name = "register_date")
	private LocalDateTime register_date;

	public UserEntity() {
		super();
	}

	public UserEntity(@NonNull String userName, @NonNull String email, @NonNull String password,
			@NonNull LocalDateTime register_date) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.register_date = register_date;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getRegister_date() {
		return register_date;
	}

	public void setRegister_date(LocalDateTime register_date) {
		this.register_date = register_date;
	}

	
}
