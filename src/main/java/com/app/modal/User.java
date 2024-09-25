package com.app.modal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.management.Notification;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String full_name;
	private String email;
	private String profile_picture;
	private String password;
	
//	@OneToMany(mappedBy = "User", cascade = CascadeType.ALL)
//	private List<Notification> notifications = new ArrayList<>();
	
	public User() {
		
	}

	public User(Integer id, String full_name, String email, String profile_picture, String password) {
		super();
		this.id = id;
		this.full_name = full_name;
		this.email = email;
		this.profile_picture = profile_picture;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, full_name, id, password, profile_picture);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(full_name, other.full_name)
				&& Objects.equals(id, other.id) && Objects.equals(password, other.password)
				&& Objects.equals(profile_picture, other.profile_picture);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", full_name=" + full_name + ", email=" + email + ", profile_picture="
				+ profile_picture + ", password=" + password + "]";
	}
	
	
	
	
	
	
	
}
