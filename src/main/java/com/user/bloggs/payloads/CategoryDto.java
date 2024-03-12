package com.user.bloggs.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	
	private Integer categoryId;
	
	@NotNull
	@Size(min=8)
	private String categoryName;
	
	private String categoryDescription;
}
