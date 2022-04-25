package zeunala.reservation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zeunala.reservation.dao.*;
import zeunala.reservation.dto.*;
import zeunala.reservation.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao productDao;
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	CommentImageDao commentImageDao;
	
	@Autowired
	DisplayInfoDao displayInfoDao;
	
	@Autowired
	DisplayInfoImageDao displayInfoImageDao;
	
	@Autowired
	ProductImageDao productImageDao;
	
	@Autowired
	ProductPriceDao productPriceDao;
	
	@Override
	public List<Product> getSomeProducts(Integer start) {
		return productDao.selectSome(start, COUNT);
	}
	
	@Override
	public List<Product> getSomeProductsByCategory(Integer category, Integer start) {
		return productDao.selectSomeByCategory(category, start, COUNT);
	}
	
	@Override
	public Integer getCount() {
		return productDao.selectCount();
	}
	
	@Override
	public Integer getCountByCategory(Integer category) {
		return productDao.selectCountByCategory(category);
	}

	@Override
	public List<Comment> getComments(Integer displayInfoId) {
		List<Comment> resultList = commentDao.selectComments(displayInfoId);
		for (Comment i : resultList) {
			i.setCommentImages(commentImageDao.selectCommentImages(i.getCommentId()));
		}
		return resultList;
	}
	
	@Override
	public List<CommentImage> getCommentImages(Integer commentId) {
		return commentImageDao.selectCommentImages(commentId);
	}

	@Override
	public Double getAverageScore(Integer displayInfoId) {
		return commentDao.selectAverageScore(displayInfoId);
	}
	
	@Override
	public DisplayInfo getDisplayInfo(Integer displayInfoId) {
		return displayInfoDao.selectDisplayInfo(displayInfoId);
	}

	@Override
	public DisplayInfoImage getDisplayInfoImage(Integer displayInfoId) {
		return displayInfoImageDao.selectDisplayInfoImage(displayInfoId);
	}

	@Override
	public List<ProductImage> getProductImages(Integer displayInfoId) {
		return productImageDao.selectProductImages(displayInfoId);
	}

	@Override
	public List<ProductPrice> getProductPrices(Integer displayInfoId) {
		return productPriceDao.selectProductPrices(displayInfoId);
	}

	
}
