package com.smart.entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contact")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	
	@NotBlank(message="Name should not be Empty !!")
	@Size(min=3,max=20,message="Character should be between 3 - 20 !! ")
	private String name;
	private String secondName;
	private String work;
	
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "must be a well formed email address !!")
	private String email;
	private String profileimage;
	
	@Size(min=10,max=10,message = "Enter a valid phone number !!")
	private String phone;
	@Column(length = 1000)
	private String description;
	
	@ManyToOne
	private User user;

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	/*
	 * @Override public String toString() { return "Contact [cId=" + cId + ", name="
	 * + name + ", secondName=" + secondName + ", work=" + work + ", email=" + email
	 * + ", profileimage=" + profileimage + ", phone=" + phone + ", description=" +
	 * description + ", user=" + user + "]"; }
	 */


	



	public String getProfileimage() {
		return profileimage;
	}




	public void setProfileimage(String profileimage) {
		this.profileimage = profileimage;
	}




	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
