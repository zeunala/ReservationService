package zeunala.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeunala.reservation.dao.CategoryDao;
import zeunala.reservation.dto.Category;

import java.util.List;

@Service
public class CategoryService {
	@Autowired
	CategoryDao categoryDao;
	
	public List<Category> getAllCategories() {
		return categoryDao.selectAll();
	}

}
