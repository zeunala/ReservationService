package zeunala.reservation.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import zeunala.reservation.dto.Promotion;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static zeunala.reservation.dao.PromotionDaoSqls.SELECT_PROMOTION;

@Repository
public class PromotionDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Promotion> rowMapper = BeanPropertyRowMapper.newInstance(Promotion.class);
	
	public PromotionDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Promotion> selectAll() {
		return jdbc.query(SELECT_PROMOTION, Collections.emptyMap(), rowMapper);
	}
}
