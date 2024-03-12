package com.user.bloggs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.bloggs.payloads.ApiResponse;
import com.user.bloggs.payloads.CategoryDto;
import com.user.bloggs.services.CategoryServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryServices categoryServices;
	
	
	
	
	
	// create category of perticular type
	@PostMapping("/")
	ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto createCategoryDto = this.categoryServices.createCategory(categoryDto);
		
		return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
	}
	
	// update category type
	@PutMapping("/{categoryId}")
	ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		
		CategoryDto updatedCategoryDto = this.categoryServices.updateCategory(categoryDto, categoryId);
		
		return new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
	}
	
	// get category by id
	@GetMapping("/{categoryId}")
	ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer categoryID){
		
		
		return new ResponseEntity<>(this.categoryServices.getCategoryById(categoryID), HttpStatus.OK);
	}
	
	// get list of all category
	@GetMapping("/")
	ResponseEntity<List<CategoryDto>> getAllCategory(){
		
		return  ResponseEntity.ok(this.categoryServices.getAllCategory());
	}
	
	// delete category by id 
	@DeleteMapping("/{categoryId}")
	ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId){
		
		this.categoryServices.deleteCategory(categoryId);
		ApiResponse response = new ApiResponse("Category deleted Succesfully" , true);
		return ResponseEntity.ok(response);
	}
	
	
}
