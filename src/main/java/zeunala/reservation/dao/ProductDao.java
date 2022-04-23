package zeunala.reservation.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import zeunala.reservation.dto.Product;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static zeunala.reservation.dao.ProductDaoSqls.*;

@Repository
public class ProductDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);

	public ProductDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	
	public List<Product> selectSome(Integer start, Integer count) {
		Map<String, Integer> params = new HashMap<>();
		params.put("start", start);
		params.put("count", count);
		return jdbc.query(SELECT_PRODUCT, params, rowMapper);
	}
	
	public List<Product> selectSomeByCategory(Integer category, Integer start, Integer count) {
		Map<String, Integer> params = new HashMap<>();
		params.put("category", category);
		params.put("start", start);
		params.put("count", count);
		return jdbc.query(SELECT_PRODUCT_BY_CATEGORY, params, rowMapper);
	}
	
	public int selectCount() {
		return jdbc.queryForObject(SELECT_COUNT, Collections.emptyMap(), Integer.class);
	}
	
	public int selectCountByCategory(Integer category) {
		Map<String, Integer> params = new HashMap<>();
		params.put("category", category);
		return jdbc.queryForObject(SELECT_COUNT_BY_CATEGORY, params, Integer.class);
	}
}
