package zeunala.reservation.dao;

public class ReservationResultDaoSqls {
	public static final String SELECT_RESERVATION_RESULT_BY_ID =
			"SELECT cancel_flag AS cancelYn, "
				+ "create_date AS createDate, "
				+ "display_info_id AS displayInfoId, "
				+ "modify_date AS modifyDate, "
				+ "product_id AS productId, "
				+ "DATE_FORMAT(reservation_date, \"%Y-%m-%d\") AS reservationDate, "
				+ "reservation_email AS reservationEmail, " 
				+ "id AS reservationInfoId, "
				+ "reservation_name AS reservationName, "
				+ "reservation_tel AS reservationTelephone "
			+ "FROM reservation_info "
			+ "WHERE id = :reservationId";
}
