package zeunala.reservation.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import zeunala.reservation.dto.CommentResult;

import javax.sql.DataSource;
import java.util.Collections;

import static zeunala.reservation.dao.CommentResultDaoSqls.SELECT_COMMENT_RESULT_BY_ID;

@Repository
public class CommentResultDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<CommentResult> rowMapper = BeanPropertyRowMapper.newInstance(CommentResult.class);
	
	public CommentResultDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public CommentResult selectCommentResult(Integer commentId) { 
		return jdbc.queryForObject(SELECT_COMMENT_RESULT_BY_ID, Collections.singletonMap("commentId", commentId), rowMapper);
	}
}
