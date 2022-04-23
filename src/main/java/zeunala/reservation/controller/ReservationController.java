package zeunala.reservation.controller;

import zeunala.reservation.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {
	@Autowired
	ProductService reservationService;

	@GetMapping("/")
	public String index() {
		return "redirect:/mainpage";
	}

	@GetMapping("/mainpage")
	public String mainpage() {
		return "mainpage";
	}
}
