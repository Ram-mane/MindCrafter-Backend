package com.user.bloggs.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Data
public class JwtAuthResponse {

	
	private String token;
}
