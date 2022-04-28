package zeunala.reservation.controller;

import zeunala.reservation.dto.ReservationByEmail;
import zeunala.reservation.dto.ReservationParam;
import zeunala.reservation.dto.ReservationResult;
import zeunala.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class ReservationApiController {
	@Autowired
	ReservationService reservationService;
	
	@GetMapping("/reservations")
	public ReservationByEmail reservations(@RequestParam String reservationEmail) {
		ReservationByEmail result = new ReservationByEmail();
		
		result.setReservations(reservationService.getReservations(reservationEmail));
		result.setSize(reservationService.getReservations(reservationEmail).size());
		
		return result;
	}
	
	@PostMapping("/reservations")
	public ReservationResult addReservation(@RequestBody ReservationParam reservationParam) {
		return reservationService.addReservation(reservationParam);
	}
	
	@PutMapping("/reservations/{reservationId}")
	public ReservationResult cancelReservation(@PathVariable Integer reservationId) {
		return reservationService.cancelReservation(reservationId);
	}
	
}
