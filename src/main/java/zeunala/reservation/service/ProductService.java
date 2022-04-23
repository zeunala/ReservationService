package zeunala.reservation.service;

import java.util.List;

import zeunala.reservation.dao.ProductDao;
import zeunala.reservation.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	public static final Integer COUNT = 4;
	
	@Autowired
	ProductDao productDao;
	
	public List<Product> getSomeProducts(Integer start) {
		return productDao.selectSome(start, COUNT);
	}
	
	public List<Product> getSomeProductsByCategory(Integer category, Integer start) {
		return productDao.selectSomeByCategory(category, start, COUNT);
	}
	
	public Integer getCount() {
		return productDao.selectCount();
	}
	
	public Integer getCountByCategory(Integer category) {
		return productDao.selectCountByCategory(category);
	}
	
}
