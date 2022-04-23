package zeunala.reservation.dao;

public class ProductDaoSqls {
	// 주어진 sql문에서 product.id는 같으나 개최지 등이 다른 경우가 있어서 product.id가 같아도 개최지 등이 다르다면 서로 다른 product로 보았다(display_info를 기준으로 함)
	public static final String SELECT_PRODUCT = "SELECT display_info.id AS displayInfoId, "
			+ "display_info.place_name AS placeName, "
			+ "product.content AS productContent, "
			+ "product.description AS productDescription, "
			+ "product.id AS productId, "
			+ "file_info.save_file_name AS productImageUrl "
		+ "FROM product "
			+ "INNER JOIN display_info ON product.id = display_info.product_id "
			+ "INNER JOIN product_image ON product.id = product_image.product_id "
			+ "INNER JOIN file_info ON product_image.file_id = file_info.id "
		+ "WHERE product_image.type = 'th'"
		+ "ORDER BY display_info.id ASC LIMIT :start, :count;";
	
	// 위 SQL문에서 카테고리 지정하는 부분 추가
		public static final String SELECT_PRODUCT_BY_CATEGORY = "SELECT display_info.id AS displayInfoId, "
				+ "display_info.place_name AS placeName, "
				+ "product.content AS productContent, "
				+ "product.description AS productDescription, "
				+ "product.id AS productId, "
				+ "file_info.save_file_name AS productImageUrl "
			+ "FROM product "
				+ "INNER JOIN display_info ON product.id = display_info.product_id "
				+ "INNER JOIN product_image ON product.id = product_image.product_id "
				+ "INNER JOIN file_info ON product_image.file_id = file_info.id "
			+ "WHERE product_image.type = 'th' AND product.category_id = :category "
			+ "ORDER BY display_info.id ASC LIMIT :start, :count;";
	
		public static final String SELECT_COUNT = "SELECT COUNT(*) FROM display_info;";
		
		public static final String SELECT_COUNT_BY_CATEGORY = "SELECT COUNT(*) "
				+ "FROM display_info INNER JOIN product ON product.id = display_info.product_id "
				+ "WHERE product.category_id = :category;";
}
