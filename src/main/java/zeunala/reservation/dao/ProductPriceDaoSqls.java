package zeunala.reservation.dao;

public class ProductPriceDaoSqls {
	public static final String SELECT_PRODUCT_PRICE_BY_ID = 
			"SELECT product_price.create_date AS createDate, "
				+ "product_price.discount_rate AS discountRate, "
				+ "product_price.modify_date AS modifyDate, "
				+ "product_price.price AS price, "
				+ "product_price.price_type_name AS priceTypeName, "
				+ "product_price.product_id AS productId, "
				+ "product_price.id AS productPriceId "
			+ "FROM display_info "
				+ "INNER JOIN product ON product.id = display_info.product_id "
				+ "INNER JOIN product_price ON product_price.product_id = product.id "
			+ "WHERE display_info.id = :displayInfoId;";
}
