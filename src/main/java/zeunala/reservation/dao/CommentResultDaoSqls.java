package zeunala.reservation.dao;

public class CommentResultDaoSqls {
	public static final String SELECT_COMMENT_RESULT_BY_ID =
			"SELECT comment, "
				+ "id AS commentId, "
				+ "create_date AS createDate, "
				+ "modify_date AS modifyDate, "
				+ "product_id AS productId, "
				+ "reservation_info_id AS reservationInfoId, "
				+ "score "
			+ "FROM reservation_user_comment "
			+ "WHERE id = :commentId;";
}
