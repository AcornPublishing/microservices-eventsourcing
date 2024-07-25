package io.cosmos.catalog.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    //
    private String itemNo;
    private String itemName;
    private int price;
    private String imagePath;
    //
    private String catalogId;
}
