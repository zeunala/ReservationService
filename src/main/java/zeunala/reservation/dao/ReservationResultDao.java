package zeunala.reservation.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import zeunala.reservation.dto.ReservationResult;

import javax.sql.DataSource;
import java.util.Collections;

import static zeunala.reservation.dao.ReservationResultDaoSqls.SELECT_RESERVATION_RESULT_BY_ID;

@Repository
public class ReservationResultDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationResult> rowMapper = BeanPropertyRowMapper.newInstance(ReservationResult.class);
	
	public ReservationResultDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public ReservationResult selectReservationResult(Integer reservationId) { 
		return jdbc.queryForObject(SELECT_RESERVATION_RESULT_BY_ID, Collections.singletonMap("reservationId", reservationId), rowMapper);
	}

}
