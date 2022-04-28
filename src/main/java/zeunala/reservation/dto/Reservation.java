package zeunala.reservation.dto;

public class Reservation {
	private boolean cancelYn;
	private String createDate;
	private DisplayInfo displayInfo;
	private Integer displayInfoId;
	private String modifyDate;
	private Integer productId;
	private String reservationDate;
	private String reservationEmail;
	private Integer reservationInfoId;
	private String reservationName;
	private String reservationTelephone;
	private Integer totalPrice;
	
	public boolean isCancelYn() {
		return cancelYn;
	}
	public void setCancelYn(boolean cancelYn) {
		this.cancelYn = cancelYn;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public DisplayInfo getDisplayInfo() {
		return displayInfo;
	}
	public void setDisplayInfo(DisplayInfo displayInfo) {
		this.displayInfo = displayInfo;
	}
	public Integer getDisplayInfoId() {
		return displayInfoId;
	}
	public void setDisplayInfoId(Integer displayInfoId) {
		this.displayInfoId = displayInfoId;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(String reservationDate) {
		this.reservationDate = reservationDate;
	}
	public String getReservationEmail() {
		return reservationEmail;
	}
	public void setReservationEmail(String reservationEmail) {
		this.reservationEmail = reservationEmail;
	}
	public Integer getReservationInfoId() {
		return reservationInfoId;
	}
	public void setReservationInfoId(Integer reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}
	public String getReservationName() {
		return reservationName;
	}
	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}
	public String getReservationTelephone() {
		return reservationTelephone;
	}
	public void setReservationTelephone(String reservationTelephone) {
		this.reservationTelephone = reservationTelephone;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Override
	public String toString() {
		return "Reservation [cancelYn=" + cancelYn + ", createDate=" + createDate + ", displayInfo=" + displayInfo
				+ ", displayInfoId=" + displayInfoId + ", modifyDate=" + modifyDate + ", productId=" + productId
				+ ", reservationDate=" + reservationDate + ", reservationEmail=" + reservationEmail
				+ ", reservationInfoId=" + reservationInfoId + ", reservationName=" + reservationName
				+ ", reservationTelephone=" + reservationTelephone + ", totalPrice=" + totalPrice + "]";
	}
	
}
