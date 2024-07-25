package io.cosmos.cart.store;

import io.cosmos.cart.aggregate.Cart;
import io.cosmos.cart.store.jpa.CartEventJpo;
import io.cosmos.cart.store.jpa.CartEventRepository;
import io.cosmos.cart.store.jpa.CartJpo;
import io.cosmos.cart.store.jpa.CartRepository;
import io.cosmos.eventsourcing.AggregateAlreadyChangedException;
import io.cosmos.eventsourcing.Event;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CartStore {
    //
    private final CartRepository cartRepository;
    private final CartEventRepository cartEventRepository;

    public CartStore(CartRepository cartRepository,
                     CartEventRepository cartEventRepository) {
        //
        this.cartRepository = cartRepository;
        this.cartEventRepository = cartEventRepository;
    }

    public Cart load(String cartId) {
        //
        Optional<CartJpo> cartJpo = this.cartRepository.findById(cartId);
        if (cartJpo.isEmpty() || cartJpo.get().isDeleted()) {
            throw new NoSuchElementException(String.format("Cart(%s) is not found.", cartId));
        }

        List<CartEventJpo> eventJpos = new ArrayList<>();

        /*
        if (cartJpo.get().getSnapshotTime() > 0) {
            eventJpos = this.cartEventRepository.findAllByCartIdAndDeletedAndSequenceGreaterThanOrderBySequenceAsc(cartId, false, cartJpo.get().getSequence());
        } else {
            eventJpos = this.cartEventRepository.findAllByCartIdAndDeletedOrderBySequenceAsc(cartId, false);
        }
        */
        eventJpos = this.cartEventRepository.findAllByCartIdAndDeletedOrderBySequenceAsc(cartId, false);
        List<Event> events = eventJpos.stream().map(CartEventJpo::toEvent).collect(Collectors.toList());

        Cart cart = cartJpo.get().toCart();
        for (Event event: events) {
            cart.apply(event, false);
        }
        cart.setVersion(cartJpo.get().getVersion());
        return cart;
    }

    public void save(Cart cart) {
        //
        //cart.takeSnapshot();

        List<CartEventJpo> eventJpos = cart.events().stream().map(event -> new CartEventJpo(cart, event)).collect(Collectors.toList());
        try {
            this.cartRepository.save(new CartJpo(cart));
            this.cartEventRepository.saveAll(eventJpos);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new AggregateAlreadyChangedException();
        }
    }
}
