package zeunala.reservation.controller;

import zeunala.reservation.dto.AllPromotions;
import zeunala.reservation.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class PromotionApiController {
	@Autowired
	PromotionService promotionService;
	
	@GetMapping("/promotions")
	public AllPromotions promotions() {
		AllPromotions result = new AllPromotions(promotionService.getAllPromotions());

		return result;
	}
}
