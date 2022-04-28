package zeunala.reservation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zeunala.reservation.dao.DisplayInfoDao;
import zeunala.reservation.dao.ReservationDao;
import zeunala.reservation.dao.ReservationPriceDao;
import zeunala.reservation.dao.ReservationResultDao;
import zeunala.reservation.dto.Reservation;
import zeunala.reservation.dto.ReservationParam;
import zeunala.reservation.dto.ReservationPrice;
import zeunala.reservation.dto.ReservationResult;
import zeunala.reservation.service.ReservationService;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ReservationServiceImpl implements ReservationService{
	@Autowired
	ReservationDao reservationDao;
	
	@Autowired
	ReservationPriceDao reservationPriceDao;
	
	@Autowired
	ReservationResultDao reservationResultDao;
	
	@Autowired
	DisplayInfoDao displayInfoDao;
	
	private boolean checkReservationValid(ReservationParam reservationParam) { // Reservation을 Dao로 추가하기 전 유효성을 한 번 더 검증함
		if (reservationParam.getPrices() != null
				&& reservationParam.getPrices().size() > 0
				&& Pattern.matches("^0\\d{1,2}-\\d{3,4}-\\d{4}$", reservationParam.getReservationTelephone())
				&& Pattern.matches("^[\\w+_]\\w+@\\w+\\.(\\w+\\.)?\\w+$", reservationParam.getReservationEmail())
				&& reservationParam.getReservationEmail().length() <= 50) {
			return true;
		} else {
			return false;
		}
	}

	private ReservationResult getReservationResult(Integer reservationId) {
		ReservationResult result = reservationResultDao.selectReservationResult(reservationId);
		result.setPrices(reservationPriceDao.selectReservationPrices(reservationId));
		return result;
	}
	
	@Override
	public List<Reservation> getReservations(String reservationEmail) {
		List<Reservation> resultList = reservationDao.selectReservations(reservationEmail);
		for (Reservation i : resultList) {
			i.setDisplayInfo(displayInfoDao.selectDisplayInfo(i.getDisplayInfoId()));
		}
		return resultList;
	}

	@Override
	@Transactional
	public ReservationResult addReservation(ReservationParam reservationParam) {
		if (checkReservationValid(reservationParam) == false) {
			return null;
		}
		
		Integer reservationId = reservationDao.addReservation(reservationParam); // 추가한 reservationId를 기억해두었다 ReservationPrice추가/결과반환에 사용
		for (ReservationPrice i : reservationParam.getPrices()) {
			i.setReservationInfoId(reservationId);
			reservationDao.addReservationPrice(i);
		}
		return getReservationResult(reservationId);
	}

	@Override
	@Transactional
	public ReservationResult cancelReservation(Integer reservationId) {
		reservationDao.cancelReservation(reservationId);
		return getReservationResult(reservationId);
	}
	
}
