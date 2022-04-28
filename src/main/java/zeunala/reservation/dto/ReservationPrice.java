package zeunala.reservation.dto;

public class ReservationPrice {
	private Integer count;
	private Integer productPriceId;
	private Integer reservationInfoId;
	private Integer reservationInfoPriceId;
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getProductPriceId() {
		return productPriceId;
	}
	public void setProductPriceId(Integer productPriceId) {
		this.productPriceId = productPriceId;
	}
	public Integer getReservationInfoId() {
		return reservationInfoId;
	}
	public void setReservationInfoId(Integer reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}
	public Integer getReservationInfoPriceId() {
		return reservationInfoPriceId;
	}
	public void setReservationInfoPriceId(Integer reservationInfoPriceId) {
		this.reservationInfoPriceId = reservationInfoPriceId;
	}
	
	@Override
	public String toString() {
		return "ReservationPrice [count=" + count + ", productPriceId=" + productPriceId + ", reservationInfoId="
				+ reservationInfoId + ", reservationInfoPriceId=" + reservationInfoPriceId + "]";
	}
	
}
