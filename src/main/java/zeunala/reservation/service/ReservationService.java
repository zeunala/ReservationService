package zeunala.reservation.service;

import zeunala.reservation.dto.CommentResult;
import zeunala.reservation.dto.Reservation;
import zeunala.reservation.dto.ReservationParam;
import zeunala.reservation.dto.ReservationResult;

import java.util.List;

public interface ReservationService {
	List<Reservation> getReservations(String reservationEmail);
	ReservationResult addReservation(ReservationParam reservationParam);
	ReservationResult cancelReservation(Integer reservationId);
	CommentResult addComment(Integer reservationId, String comment, Integer productId, Double score, String imageFileName);
}
