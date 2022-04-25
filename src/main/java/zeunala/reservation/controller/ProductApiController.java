package zeunala.reservation.controller;

import zeunala.reservation.dto.ProductByDisplayInfoId;
import zeunala.reservation.dto.SomeProducts;
import zeunala.reservation.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class ProductApiController {
	@Autowired
	ProductService productService;
	
	@GetMapping("/products")
	public SomeProducts products(@RequestParam(defaultValue="0") Integer categoryId, @RequestParam(defaultValue="0") Integer start) {
		SomeProducts result = new SomeProducts();
		
		if (categoryId == 0) { // category를 따로 지정안한 경우 디폴트값인 0으로 세팅되며 이 경우 전체 목록을 대상으로 함
			result.setItems(productService.getSomeProducts(start));
			result.setTotalCount(productService.getCount());
		} else {
			result.setItems(productService.getSomeProductsByCategory(categoryId, start));
			result.setTotalCount(productService.getCountByCategory(categoryId));
		}
		
		return result;
	}
	
	@GetMapping("/products/{displayInfoId}")
	public ProductByDisplayInfoId productByDisplayInfoId(@PathVariable Integer displayInfoId) {
		ProductByDisplayInfoId result = new ProductByDisplayInfoId();
		
		result.setAverageScore(productService.getAverageScore(displayInfoId));
		result.setComments(productService.getComments(displayInfoId));
		result.setDisplayInfo(productService.getDisplayInfo(displayInfoId));
		result.setDisplayInfoImage(productService.getDisplayInfoImage(displayInfoId));
		result.setProductImages(productService.getProductImages(displayInfoId));
		result.setProductPrices(productService.getProductPrices(displayInfoId));
		
		return result;
	}
	
}
