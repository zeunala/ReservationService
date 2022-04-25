package zeunala.reservation.dao;

public class ProductImageDaoSqls {
	// 타입이 "th"인 것을 제외하고 "ma"부터 시작하여 최대 2개까지만 가져온다.
	public static final String SELECT_PRODUCT_IMAGE_BY_ID = 
			"SELECT file_info.content_type AS contentType, "
				+ "file_info.create_date AS createDate, "
				+ "file_info.delete_flag AS deleteFlag, "
				+ "file_info.id AS fileInfoId, "
				+ "file_info.file_name AS fileName, "
				+ "file_info.modify_date AS modifyDate, "
				+ "product.id AS productId, "
				+ "product_image.id AS productImageId, "
				+ "file_info.save_file_name AS saveFileName, "
				+ "product_image.type AS type "
			+ "FROM display_info "
				+ "INNER JOIN product ON product.id = display_info.product_id "
				+ "INNER JOIN product_image ON product_image.product_id = product.id "
				+ "INNER JOIN file_info ON product_image.file_id = file_info.id "
			+ "WHERE display_info.id = :displayInfoId and product_image.type != \"th\" "
			+ "ORDER BY product_image.type DESC LIMIT 2;";
}
