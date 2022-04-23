package zeunala.reservation.dto;

public class Category {
	private Integer count; // 카테고리에 속한 전시상품 수
	private Integer id; // 카테고리 Id
	private String name; // 카테고리 이름
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Category [count=" + count + ", id=" + id + ", name=" + name + "]";
	}
}
