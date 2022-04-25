package zeunala.reservation.service;

import org.springframework.stereotype.Service;
import zeunala.reservation.dto.Category;

import java.util.List;

@Service
public interface CategoryService {
	List<Category> getAllCategories();
}
