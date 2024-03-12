package com.user.bloggs.payloads;



import java.util.ArrayList;
import java.util.List;
import com.user.bloggs.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UserDto {

	
  private int id;
	
  
	private String name;

    
	private String email;

    
	private String password;

    
	private String about;

	private String username;

	 
	private String gender;
	
	
	private String phone_no;
	
	private List<Comment> comments = new ArrayList<>();
	
	private List<RoleDto> roles = new ArrayList<>();


	
}
