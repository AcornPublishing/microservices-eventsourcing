package io.cosmos.catalog.store;

import io.cosmos.catalog.aggregate.Item;
import io.cosmos.catalog.store.jpa.ItemJpo;
import io.cosmos.catalog.store.jpa.ItemRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ItemStore {
    //
    private final ItemRepository itemRepository;

    public ItemStore(ItemRepository itemRepository) {
        //
        this.itemRepository = itemRepository;
    }

    public List<Item> retrieveByCatalog(String catalogId) {
        //
        List<ItemJpo> itemJpos = this.itemRepository.findAllByCatalogId(catalogId);
        return itemJpos.stream().map(ItemJpo::toItem).collect(Collectors.toList());
    }

    public Item retrieve(String itemNo) {
        //
        Optional<ItemJpo> itemJpo = this.itemRepository.findById(itemNo);
        if (itemJpo.isEmpty()) {
            throw new NoSuchElementException();
        }
        return itemJpo.get().toItem();
    }

}
