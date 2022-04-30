package zeunala.reservation.dao;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import zeunala.reservation.dto.CommentImage;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static zeunala.reservation.dao.CommentImageDaoSqls.SELECT_COMMENT_IMAGE_BY_ID;

@Repository
public class CommentImageDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<CommentImage> rowMapper = BeanPropertyRowMapper.newInstance(CommentImage.class);
	
	public CommentImageDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public CommentImage selectCommentImage(Integer commentId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("commentId", commentId);
		try {
			return jdbc.queryForObject(SELECT_COMMENT_IMAGE_BY_ID, params, rowMapper);
		} catch (IncorrectResultSizeDataAccessException error) {
			return null;
		}
	}

	public List<CommentImage> selectCommentImages(Integer commentId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("commentId", commentId);
		return jdbc.query(SELECT_COMMENT_IMAGE_BY_ID, params, rowMapper);
	}
}
