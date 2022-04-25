package zeunala.reservation.dto;

import java.util.List;

public class SomeProducts {
	public List<Product> items;
	public Integer totalCount;
	
	public SomeProducts() {
		
	}
	
	public SomeProducts(List<Product> items, Integer totalCount) {
		super();
		this.items = items;
		this.totalCount = totalCount;
	}

	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		return "SomeProducts [list=" + items + ", totalCount=" + totalCount + "]";
	}
	
}
