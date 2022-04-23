package zeunala.reservation.dao;

public class PromotionDaoSqls {
	// promotion 테이블에 있는 id와 product_id, 그리고 해당 product_id에 대응하는 이미지파일 주소를 product_image 테이블에서 가져온다.
	public static final String SELECT_PROMOTION = 
			"SELECT promotion.id, promotion.product_id AS productId, file_info.save_file_name AS productImageUrl "
			+ "FROM promotion "
				+ "INNER JOIN product ON promotion.product_id = product.id "
				+ "INNER JOIN product_image ON product.id = product_image.product_id "
				+ "INNER JOIN file_info ON product_image.file_id = file_info.id "
			+ "WHERE product_image.type = 'th'";

}
