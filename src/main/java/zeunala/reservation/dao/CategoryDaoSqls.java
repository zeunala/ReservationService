package zeunala.reservation.dao;

public class CategoryDaoSqls {
	// GROUP BY를 이용해 category.id 별 display_info 개수를 구함
	public static final String SELECT_CATEGORY = "SELECT distinct(category.id), COUNT(*) AS \"count\", name from category INNER JOIN product ON category.id = product.category_id INNER JOIN display_info ON product.id = display_info.product_id GROUP BY category.id;";
}
