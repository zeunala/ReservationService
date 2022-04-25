package zeunala.reservation.dao;

public class DisplayInfoImageDaoSqls {
	public static final String SELECT_DISPLAY_INFO_IMAGE_BY_ID = 
			"SELECT file_info.content_type AS contentType, "
				+ "file_info.create_date AS createDate, "
				+ "file_info.delete_flag AS deleteFlag, "
				+ "display_info.id AS displayInfoId, "
				+ "display_info_image.id AS displayInfoImageId, "
				+ "file_info.id AS fileId, "
				+ "file_info.file_name AS fileName, "
				+ "file_info.modify_date AS modifyDate, "
				+ "file_info.save_file_name AS saveFileName "
			+ "FROM display_info "
				+ "INNER JOIN display_info_image ON display_info_image.display_info_id = display_info.id "
				+ "INNER JOIN file_info ON display_info_image.file_id = file_info.id "
			+ "WHERE display_info.id = :displayInfoId;";
}
