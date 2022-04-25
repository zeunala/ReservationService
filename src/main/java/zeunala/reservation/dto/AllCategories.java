package zeunala.reservation.dto;

import java.util.List;

public class AllCategories {
    private List<Category> items;

    public AllCategories() {

    }

    public AllCategories(List<Category> items) {
        super();
        this.items = items;
    }

    public List<Category> getItems() {
        return items;
    }

    public void setItems(List<Category> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "AllCategories [items=" + items + "]";
    }

}
