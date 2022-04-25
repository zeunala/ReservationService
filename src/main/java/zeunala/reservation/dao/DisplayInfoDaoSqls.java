package zeunala.reservation.dao;

public class DisplayInfoDaoSqls {
	public static final String SELECT_DISPLAY_INFO_BY_ID =
			"SELECT category.id AS categoryId, "
				+ "category.name AS categoryName, "
				+ "display_info.create_date AS createDate, "
				+ "display_info.id AS displayInfoId, "
				+ "display_info.email AS email, "
				+ "display_info.homepage AS homepage, "
				+ "display_info.modify_date AS modifyDate, "
				+ "display_info.opening_hours AS openingHours, "
				+ "display_info.place_lot AS placeLot, "
				+ "display_info.place_name AS placeName, "
				+ "display_info.place_street AS placeStreet, "
				+ "product.content AS productContent, "
				+ "product.description AS productDescription, "
				+ "product.event AS productEvent, "
				+ "product.id AS productId, "
				+ "display_info.tel AS telephone "
			+ "FROM display_info "
				+ "INNER JOIN product ON display_info.product_id = product.id "
				+ "INNER JOIN category ON product.category_id = category.id "
			+ "WHERE display_info.id = :displayInfoId;";
}
