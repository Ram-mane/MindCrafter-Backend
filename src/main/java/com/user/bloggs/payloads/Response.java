package com.user.bloggs.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T>  {

	
	private String message;
	private boolean succes;
	private T UserDetails;
	
	public Response(String message, boolean succes) {
		
		this.message = message;
		this.succes = succes;
	}
}
