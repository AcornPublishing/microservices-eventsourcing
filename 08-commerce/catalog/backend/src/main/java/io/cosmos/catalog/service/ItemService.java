package io.cosmos.catalog.service;

import io.cosmos.catalog.aggregate.Item;
import io.cosmos.catalog.query.QueryItem;
import io.cosmos.catalog.query.QueryItems;
import io.cosmos.catalog.store.ItemStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    //
    private final ItemStore itemStore;

    public ItemService(ItemStore itemStore) {
        //
        this.itemStore = itemStore;
    }

    public List<Item> queryItems(QueryItems query) {
        //
        return this.itemStore.retrieveByCatalog(query.getCatalogId());
    }

    public Item queryItem(QueryItem query) {
        //
        return this.itemStore.retrieve(query.getItemNo());
    }
}
