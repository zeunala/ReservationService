package zeunala.reservation.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import zeunala.reservation.dto.ReservationPrice;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

import static zeunala.reservation.dao.ReservationPriceDaoSqls.SELECT_RESERVATION_INFO_PRICE_BY_ID;

@Repository
public class ReservationPriceDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ReservationPrice> rowMapper = BeanPropertyRowMapper.newInstance(ReservationPrice.class);
	
	public ReservationPriceDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<ReservationPrice> selectReservationPrices(Integer reservationId) {
		return jdbc.query(SELECT_RESERVATION_INFO_PRICE_BY_ID, Collections.singletonMap("reservationId", reservationId), rowMapper);
	}
}
