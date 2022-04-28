package zeunala.reservation.dto;

import java.util.List;

public class ReservationByEmail {
	private List<Reservation> reservations;
	private Integer size;
	
	public ReservationByEmail() {
		
	}
	
	public ReservationByEmail(List<Reservation> reservations, Integer size) {
		super();
		this.reservations = reservations;
		this.size = size;
	}
	
	public List<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "ReservationByEmail [reservations=" + reservations + ", size=" + size + "]";
	}
	
}
