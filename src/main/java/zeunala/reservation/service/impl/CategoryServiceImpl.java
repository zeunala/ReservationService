package zeunala.reservation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeunala.reservation.dao.CategoryDao;
import zeunala.reservation.dto.Category;
import zeunala.reservation.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDao categoryDao;

	@Override
	public List<Category> getAllCategories() {
		return categoryDao.selectAll();
	}

}
