package zeunala.reservation.dao;

public class ReservationPriceDaoSqls {
	public static final String SELECT_RESERVATION_INFO_PRICE_BY_ID =
			"SELECT count, "
				+ "product_price_id AS productPriceId, "
				+ "id AS reservationInfoPriceId, "
				+ "reservation_info_id AS reservationInfoId "
			+ "FROM reservation_info_price "
			+ "WHERE reservation_info_id = :reservationId";
}
