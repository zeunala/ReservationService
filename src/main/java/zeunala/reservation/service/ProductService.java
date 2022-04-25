package zeunala.reservation.service;

import zeunala.reservation.dto.*;

import java.util.List;

public interface ProductService {
	Integer COUNT = 4;
	List<Product> getSomeProducts(Integer start);
	List<Product> getSomeProductsByCategory(Integer category, Integer start);
	Integer getCount();
	Integer getCountByCategory(Integer category);
	List<Comment> getComments(Integer displayInfoId);
	List<CommentImage> getCommentImages(Integer displayInfoId);
	Double getAverageScore(Integer displayInfoId);
	DisplayInfo getDisplayInfo(Integer displayInfoId);
	DisplayInfoImage getDisplayInfoImage(Integer displayInfoId);
	List<ProductImage> getProductImages(Integer displayInfoId);
	List<ProductPrice> getProductPrices(Integer displayInfoId);
}
