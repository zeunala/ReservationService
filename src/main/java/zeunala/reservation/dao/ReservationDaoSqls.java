package zeunala.reservation.dao;

public class ReservationDaoSqls {
	public static final String SELECT_RESERVATION_BY_EMAIL = 
			"SELECT reservation_info.cancel_flag AS cancelYn, "
				+ "reservation_info.create_date AS createDate, "
				+ "reservation_info.display_info_id AS displayInfoId, "
				+ "reservation_info.modify_date AS modifyDate, "
				+ "reservation_info.product_id AS productId, "
				+ "DATE_FORMAT(reservation_info.reservation_date, \"%Y-%m-%d\") AS reservationDate, "
				+ "reservation_info.reservation_email AS reservationEmail, "
				+ "reservation_info.id AS reservationInfoId, "
				+ "reservation_info.reservation_name AS reservationName, "
				+ "reservation_info.reservation_tel AS reservationTelephone, "
				+ "(SELECT SUM(product_price.price * reservation_info_price.count) "
				+ "FROM reservation_info_price INNER JOIN product_price ON reservation_info_price.product_price_id = product_price.id "
				+ "WHERE reservation_info_price.reservation_info_id = reservation_info.id) AS totalPrice "
			+ "FROM reservation_info "
			+ "WHERE reservation_info.reservation_email = :reservationEmail";
	
	public static final String CANCEL_RESERVATION_BY_ID =
			"UPDATE reservation_info SET cancel_flag = 1, modify_date = now() WHERE id = :reservationId";
	
}
