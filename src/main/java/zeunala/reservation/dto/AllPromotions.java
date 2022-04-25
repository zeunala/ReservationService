package zeunala.reservation.dto;

import java.util.List;

public class AllPromotions {
    private List<Promotion> items;

    public AllPromotions() {

    }

    public AllPromotions(List<Promotion> items) {
        super();
        this.items = items;
    }

    public List<Promotion> getItems() {
        return items;
    }

    public void setItems(List<Promotion> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "AllPromotions [items=" + items + "]";
    }
}
