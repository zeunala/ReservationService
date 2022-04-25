package zeunala.reservation.dao;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import zeunala.reservation.dto.Comment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static zeunala.reservation.dao.CommentDaoSqls.SELECT_AVERAGE_SCORE_BY_ID;
import static zeunala.reservation.dao.CommentDaoSqls.SELECT_COMMENT_BY_ID;

@Repository
public class CommentDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Comment> rowMapper = BeanPropertyRowMapper.newInstance(Comment.class);
	
	public CommentDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Comment> selectComments(Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		List<Comment> returnList;
		
		params.put("displayInfoId", displayInfoId);
		returnList = jdbc.query(SELECT_COMMENT_BY_ID, params, rowMapper);
		
		return returnList;
	}
	
	public double selectAverageScore(Integer displayInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("displayInfoId", displayInfoId);
		return jdbc.queryForObject(SELECT_AVERAGE_SCORE_BY_ID, params, Double.class);
	}	
	
}
