package zeunala.reservation.dao;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import zeunala.reservation.dto.Reservation;
import zeunala.reservation.dto.ReservationParam;
import zeunala.reservation.dto.ReservationPrice;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static zeunala.reservation.dao.ReservationDaoSqls.CANCEL_RESERVATION_BY_ID;
import static zeunala.reservation.dao.ReservationDaoSqls.SELECT_RESERVATION_BY_EMAIL;

@Repository
public class ReservationDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Reservation> rowMapper = BeanPropertyRowMapper.newInstance(Reservation.class);
	private SimpleJdbcInsert jdbcInsertReservation;
	private SimpleJdbcInsert jdbcInsertReservationPrice;
	
	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcInsertReservation = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_info")
				.usingGeneratedKeyColumns("id");
		this.jdbcInsertReservationPrice = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_info_price")
				.usingGeneratedKeyColumns("id");
	}
	
	public List<Reservation> selectReservations(String reservationEmail) {
		Map<String, String> params = new HashMap<>();
		params.put("reservationEmail", reservationEmail);
		return jdbc.query(SELECT_RESERVATION_BY_EMAIL, params, rowMapper);
	}
	
	public int addReservation(ReservationParam reservationParam) { // 결과로 insert에 성공한 id값을 리턴함
		Map<String, Object> params = new HashMap<>();
		Date datetimeNow = new Date();
		SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDatetimeNow = datetimeFormat.format(datetimeNow);
		
		params.put("product_id", reservationParam.getProductId());
		params.put("display_info_id", reservationParam.getDisplayInfoId());
		params.put("reservation_name", reservationParam.getReservationName());
		params.put("reservation_tel", reservationParam.getReservationTelephone());
		params.put("reservation_email", reservationParam.getReservationEmail());
		params.put("reservation_date", reservationParam.getReservationYearMonthDay());
		params.put("cancel_flag", 0);
		params.put("create_date", formattedDatetimeNow);
		params.put("modify_date", formattedDatetimeNow);
		return jdbcInsertReservation.executeAndReturnKey(params).intValue();
	}
	
	public int addReservationPrice(ReservationPrice reservationPrice) { // 결과로 insert에 성공한 id값을 리턴함
		Map<String, Integer> params = new HashMap<>();
		params.put("reservation_info_id", reservationPrice.getReservationInfoId());
		params.put("product_price_id", reservationPrice.getProductPriceId());
		params.put("count", reservationPrice.getCount());
		return jdbcInsertReservationPrice.executeAndReturnKey(params).intValue();
	}
	
	public int cancelReservation(Integer reservationId) {
		Map<String, Integer> params = new HashMap<>();
		params.put("reservationId", reservationId);
		return jdbc.update(CANCEL_RESERVATION_BY_ID, params);
	}
	
}
