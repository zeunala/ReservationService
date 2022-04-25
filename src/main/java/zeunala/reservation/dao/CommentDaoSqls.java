package zeunala.reservation.dao;

public class CommentDaoSqls {
	public static final String SELECT_COMMENT_BY_ID = 
			"SELECT reservation_user_comment.comment AS comment, "
				+ "reservation_user_comment.id AS commentId, "
				+ "reservation_user_comment.create_date AS createDate, "
				+ "reservation_user_comment.modify_date AS modifyDate, "
				+ "reservation_user_comment.product_id AS productId, "
				+ "reservation_info.reservation_date AS reservationDate, "
				+ "reservation_info.reservation_email AS reservationEmail, "
				+ "reservation_info.id AS reservationInfoId, "
				+ "reservation_info.reservation_name AS reservationName, "
				+ "reservation_info.reservation_tel AS reservationTelephone, "
				+ "reservation_user_comment.score AS score "
			+ "FROM display_info "
				+ "INNER JOIN product ON product.id = display_info.product_id "
				+ "INNER JOIN reservation_user_comment ON reservation_user_comment.product_id = product.id "
				+ "INNER JOIN reservation_info ON reservation_info.id = reservation_user_comment.reservation_info_id "
			+ "WHERE display_info.id = :displayInfoId;";
			
	public static final String SELECT_AVERAGE_SCORE_BY_ID = 
			"SELECT IFNULL(AVG(reservation_user_comment.score), 0) AS averageScore "
			+ "FROM display_info "
				+ "INNER JOIN product ON product.id = display_info.product_id "
				+ "INNER JOIN reservation_user_comment ON reservation_user_comment.product_id = product.id "
			+ "WHERE display_info.id = :displayInfoId;";
}
