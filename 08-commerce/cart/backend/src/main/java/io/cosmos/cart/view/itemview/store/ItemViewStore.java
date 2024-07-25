package io.cosmos.cart.view.itemview.store;

import io.cosmos.cart.view.itemview.ItemView;
import io.cosmos.cart.view.itemview.store.jpa.ItemViewJpo;
import io.cosmos.cart.view.itemview.store.jpa.ItemViewRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class ItemViewStore {
    //
    private ItemViewRepository itemViewRepository;

    public ItemViewStore(ItemViewRepository itemViewRepository) {
        //
        this.itemViewRepository = itemViewRepository;
    }

    public void create(ItemView itemView) {
        //
        this.itemViewRepository.save(new ItemViewJpo(itemView));
    }
    
    public void update(ItemView itemView) {
        //
        this.itemViewRepository.save(new ItemViewJpo(itemView));
    }

    public List<ItemView> retrieve(String cartId) {
        //
        List<ItemViewJpo> itemViewJpos = this.itemViewRepository.findByItemViewKeyCartId(cartId);
        return itemViewJpos.stream().map(ItemViewJpo::toView).collect(Collectors.toList());
    }
    
    public ItemView retrieve(String cartId, String productNo) {
        //
        Optional<ItemViewJpo> itemViewJpo = this.itemViewRepository.findById(new ItemViewJpo.ItemViewKey(cartId, productNo));
        if (itemViewJpo.isEmpty()) {
            throw new NoSuchElementException(String.format("ItemView(%s) is not found.", cartId));
        }
        return itemViewJpo.get().toView();
    }

    public void delete(String cartId, String productNo) {
        //
        this.itemViewRepository.deleteById(new ItemViewJpo.ItemViewKey(cartId, productNo));
    }

    public void delete(String cartId) {
        //
        this.itemViewRepository.deleteByItemViewKeyCartId(cartId);
    }
}
