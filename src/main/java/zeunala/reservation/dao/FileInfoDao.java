package zeunala.reservation.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static zeunala.reservation.dao.FileInfoDaoSqls.SELECT_SAVE_FILE_NAME_BY_ID;

@Repository
public class FileInfoDao {
	private NamedParameterJdbcTemplate jdbc;
	
	public FileInfoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public String selectSaveFileNameById(Integer fileInfoId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("fileInfoId", fileInfoId);
		try {
			return jdbc.queryForObject(SELECT_SAVE_FILE_NAME_BY_ID, params, String.class);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
