package io.cosmos.catalog.endpoint;

import io.cosmos.catalog.aggregate.Item;
import io.cosmos.catalog.query.QueryItem;
import io.cosmos.catalog.query.QueryItems;
import io.cosmos.catalog.service.CatalogService;
import io.cosmos.catalog.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemEndpoint {
    //
    private final CatalogService catalogService;
    private final ItemService itemService;

    public ItemEndpoint(CatalogService catalogService,
                        ItemService itemService) {
        //
        this.catalogService = catalogService;
        this.itemService = itemService;
    }

    @GetMapping(value = "/{catalogId}", headers = { "query=QueryItems" })
    public List<Item> queryItems(@PathVariable String catalogId) {
        //
        QueryItems query = new QueryItems(catalogId);
        return this.itemService.queryItems(query);
    }

    @GetMapping(value = "/{itemNo}", headers = { "query=QueryItem" })
    public Item queryItem(@PathVariable String itemNo) {
        //
        QueryItem query = new QueryItem(itemNo);
        return this.itemService.queryItem(query);
    }
}
