package io.cosmos.catalog.store.jpa;

import io.cosmos.catalog.aggregate.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ITEM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemJpo {
    //
    @Id
    private String itemNo;
    private String itemNamee;
    private int price;
    private String imagePath;
    //
    private String catalogId;

    public ItemJpo(Item item) {
        //
        this.itemNo = item.getItemNo();
        this.itemNamee = item.getItemName();
        this.price = item.getPrice();
        this.imagePath = item.getImagePath();
        this.catalogId = item.getCatalogId();
    }

    public Item toItem() {
        //
        return new Item(this.itemNo, this.itemNamee, this.price, this.imagePath, this.catalogId);
    }
}
