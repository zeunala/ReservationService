package zeunala.reservation.dto;

public class Promotion {
	private Integer id;
	private Integer productId;
	private String productImageUrl;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductImageUrl() {
		return productImageUrl;
	}
	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}
	
	@Override
	public String toString() {
		return "Promotion [id=" + id + ", productId=" + productId + ", productImageUrl=" + productImageUrl + "]";
	}
}
