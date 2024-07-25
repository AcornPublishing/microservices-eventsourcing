package io.cosmos.catalog.endpoint;

import io.cosmos.catalog.aggregate.Catalog;
import io.cosmos.catalog.service.CatalogService;
import io.cosmos.catalog.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/catalog")
public class CatalogEndpoint {
    //
    private final CatalogService catalogService;

    public CatalogEndpoint(CatalogService catalogService) {
        //
        this.catalogService = catalogService;
    }

    @GetMapping(headers = { "query=QueryCatalogs"} )
    public List<Catalog> queryCatalogs() {
        //
        return this.catalogService.queryCatalogs();
    }

}
