package zeunala.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zeunala.reservation.dto.ProductByDisplayInfoId;
import zeunala.reservation.dto.SomeProducts;
import zeunala.reservation.service.ProductService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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

	@GetMapping("/product-reservation-date/{displayInfoId}") // 해당 작품의 예약 가능일을 반환
	public Map<String, String> productReservationDate(@PathVariable Integer displayInfoId) {
		Calendar reservationDate = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy.M.d");
		Random random = new Random();

		reservationDate.setTime(new Date());

		reservationDate.add(Calendar.DATE, random.nextInt(4)); // 오늘 포함해서 1~5일 중 랜덤으로 설정함

		return Collections.singletonMap("reservationDate", dateFormat.format(reservationDate.getTime()));
	}
}
