package zeunala.reservation.dao;

public class CommentImageDaoSqls {
	public static final String SELECT_COMMENT_IMAGE_BY_ID = 
			"SELECT file_info.content_type AS contentType, "
				+ "file_info.create_date AS createDate, "
				+ "file_info.delete_flag AS deleteFlag, "
				+ "file_info.id AS fileId, "
				+ "file_info.file_name AS fileName, "
				+ "reservation_user_comment_image.id AS imageId, "
				+ "file_info.modify_date AS modifyDate, "
				+ "reservation_user_comment_image.reservation_info_id AS reservationInfoId, "
				+ "reservation_user_comment.id AS reservationUserCommentId, "
				+ "file_info.save_file_name AS saveFileName "
			+ "FROM reservation_user_comment "
				+ "INNER JOIN reservation_user_comment_image ON reservation_user_comment_image.reservation_user_comment_id = reservation_user_comment.id " // commentId에 대해 commentImage 없는 경우를 제거위함
				+ "INNER JOIN file_info ON reservation_user_comment_image.file_id = file_info.id "
			+ "WHERE reservation_user_comment.id = :commentId;";
}
