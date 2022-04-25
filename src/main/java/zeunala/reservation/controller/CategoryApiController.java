package zeunala.reservation.controller;

import zeunala.reservation.dto.AllCategories;
import zeunala.reservation.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class CategoryApiController {
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/categories")
	public AllCategories categories() {
		AllCategories result = new AllCategories(categoryService.getAllCategories());

		return result;
	}

}
