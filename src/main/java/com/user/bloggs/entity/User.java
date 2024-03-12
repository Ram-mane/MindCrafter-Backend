package com.user.bloggs.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;


@Component
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name ="user_basic_info")
@Getter
@Setter
public class User implements UserDetails{

	/*
	@Transient
	 private PasswordEncoder passwordEncoder;
	 */

	    
	   
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	
	 private String name;
	
	
	 private String username;
	 
	 private String email;
	 
	 private String about;
	 
	 
	 private String password;
	 
	 private String phone_no;
	 
	 private String gender;
	 
	 @CreatedBy
	 private String createdBy;
	 
	 @LastModifiedBy
	 private String updatedBy;
	 
	 @PrePersist
	    @PreUpdate
	    private void setEmailAsUsernameAndBCryptPass() {
	        this.username = this.email;
//	        this.password = this.passwordEncoder(this.password);// Set username as email
	    }
	 
	 
	 
	 
//	 @JsonManagedReference
	 @JsonIgnore
	 @OneToMany(mappedBy = "user" , cascade=CascadeType.ALL)
	 private List<Post> posts = new ArrayList<>();
	 
	 @JsonIgnore
	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	 private Set<Comment> comments = new HashSet<>();
	 
	 
	 @JsonIgnore
	 @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	 @JoinTable(name="uesr_role_relation",
	 joinColumns = @JoinColumn(name = "user(id)", referencedColumnName = "id"),
	 inverseJoinColumns = @JoinColumn(name="role(id)", referencedColumnName = "id"))
	 private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<SimpleGrantedAuthority> authority=this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
		return authority;
	}


	@Override
	public boolean isAccountNonExpired() {

		return true;
	}


	@Override
	public boolean isAccountNonLocked() {

		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}


	@Override
	public boolean isEnabled() {

		return true;
	}
}
