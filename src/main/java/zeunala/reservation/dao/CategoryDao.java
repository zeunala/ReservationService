package zeunala.reservation.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import zeunala.reservation.dto.Category;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static zeunala.reservation.dao.CategoryDaoSqls.SELECT_CATEGORY;

@Repository
public class CategoryDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Category> rowMapper = BeanPropertyRowMapper.newInstance(Category.class);
	
	public CategoryDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Category> selectAll() {
		return jdbc.query(SELECT_CATEGORY, Collections.emptyMap(), rowMapper);
	}
}
