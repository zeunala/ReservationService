package zeunala.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zeunala.reservation.dto.Category;
import zeunala.reservation.dto.Product;
import zeunala.reservation.dto.Promotion;
import zeunala.reservation.service.CategoryService;
import zeunala.reservation.service.ProductService;
import zeunala.reservation.service.PromotionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReservationApiController {
	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	PromotionService promotionService;
	
	@GetMapping("/products")
	public Map<String, Object> products(@RequestParam(defaultValue="0") Integer categoryId, @RequestParam(defaultValue="0") Integer start) {
		List<Product> list;
		Integer totalCount;
		
		if (categoryId == 0) { // category를 따로 지정안한 경우 디폴트값인 0으로 세팅되며 이 경우 전체 목록을 대상으로 함
			list = productService.getSomeProducts(start);
			totalCount = productService.getCount();
		} else {
			list = productService.getSomeProductsByCategory(categoryId, start);
			totalCount = productService.getCountByCategory(categoryId);
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("items", list);
		map.put("totalCount", totalCount);
		
		return map;
	}
	
	@GetMapping("/categories")
	public Map<String, Object> categories() {
		List<Category> list = categoryService.getAllCategories();
		
		Map<String, Object> map = new HashMap<>();
		map.put("items", list);

		return map;
	}
	
	@GetMapping("/promotions")
	public Map<String, Object> promotions() {
		List<Promotion> list = promotionService.getAllPromotions();
		
		Map<String, Object> map = new HashMap<>();
		map.put("items", list);

		return map;
	}
}
