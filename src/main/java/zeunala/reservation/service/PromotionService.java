package zeunala.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeunala.reservation.dao.PromotionDao;
import zeunala.reservation.dto.Promotion;

import java.util.List;

@Service
public class PromotionService {
	@Autowired
	PromotionDao promotionDao;
	
	public List<Promotion> getAllPromotions() {
		return promotionDao.selectAll();
	}

}
