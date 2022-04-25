package zeunala.reservation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeunala.reservation.dao.PromotionDao;
import zeunala.reservation.dto.Promotion;
import zeunala.reservation.service.PromotionService;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
	@Autowired
	PromotionDao promotionDao;
	
	@Override
	public List<Promotion> getAllPromotions() {
		return promotionDao.selectAll();
	}

}
