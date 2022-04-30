package zeunala.reservation.dao;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import zeunala.reservation.dto.Comment;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static zeunala.reservation.dao.CommentDaoSqls.SELECT_AVERAGE_SCORE_BY_ID;
import static zeunala.reservation.dao.CommentDaoSqls.SELECT_COMMENT_BY_ID;

@Repository
public class CommentDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Comment> rowMapper = BeanPropertyRowMapper.newInstance(Comment.class);
	private SimpleJdbcInsert jdbcInsertComment;
	private SimpleJdbcInsert jdbcInsertFileInfo;
	private SimpleJdbcInsert jdbcInsertCommentImage;

	public CommentDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsertComment = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_user_comment")
				.usingGeneratedKeyColumns("id");
		this.jdbcInsertFileInfo = new SimpleJdbcInsert(dataSource)
				.withTableName("file_info")
				.usingGeneratedKeyColumns("id");
		this.jdbcInsertCommentImage = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_user_comment_image")
				.usingGeneratedKeyColumns("id");
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

	public int addComment(Integer reservationInfoId, String comment, Integer productId, Double score) {
		Map<String, Object> params = new HashMap<>();
		Date datetimeNow = new Date();
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDatetimeNow = datetimeFormat.format(datetimeNow);

		params.put("product_id", productId);
		params.put("reservation_info_id", reservationInfoId);
		params.put("score", score);
		params.put("comment", comment);
		params.put("create_date", formattedDatetimeNow);
		params.put("modify_date", formattedDatetimeNow);

		return jdbcInsertComment.executeAndReturnKey(params).intValue();
	}

	public int addFileInfo(String fileName) {
		Map<String, Object> params = new HashMap<>();
		Date datetimeNow = new Date();
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDatetimeNow = datetimeFormat.format(datetimeNow);
		String fileNameExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

		// fileName 예시: sample.png
		params.put("file_name", fileName);
		params.put("save_file_name", "img_comment/" + fileName);
		if (fileNameExtension.equals("jpg") || fileNameExtension.equals("jpeg")) {
			params.put("content_type", "image/jpeg");
		} else if (fileNameExtension.equals("png")) {
			params.put("content_type", "image/png");
		} else { // 비정상적인 방법으로 잘못된 확장자 파일을 올린 경우
			return -1;
		}
		params.put("delete_flag", 0);
		params.put("create_date", formattedDatetimeNow);
		params.put("modify_date", formattedDatetimeNow);

		return jdbcInsertFileInfo.executeAndReturnKey(params).intValue();
	}

	public int addCommentImage(Integer reservationInfoId, Integer commentId, Integer fileId) {
		Map<String, Object> params = new HashMap<>();

		params.put("reservation_info_id", reservationInfoId);
		params.put("reservation_user_comment_id", commentId);
		params.put("file_id", fileId);

		return jdbcInsertCommentImage.executeAndReturnKey(params).intValue();
	}
}
