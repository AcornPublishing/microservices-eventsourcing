package io.cosmos.cart.endpoint;

import io.cosmos.cart.aggregate.Cart;
import io.cosmos.cart.command.*;
import io.cosmos.cart.query.QueryCart;
import io.cosmos.cart.query.QueryCartView;
import io.cosmos.cart.service.CartService;
import io.cosmos.cart.view.itemview.ItemView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/cart")
public class CartEndpoint {
    //
    @Value("${session}")
    private String userId;
    //
    private final HttpSession httpSession;
    private final CartService cartService;

    public CartEndpoint(HttpSession httpSession,
                        CartService cartService) {
        //
        this.httpSession = httpSession;
        this.cartService = cartService;
    }

    @PostMapping(headers = { "command=CreateCart" })
    public void createCart(@RequestBody CreateCart command) {
        //
        command.setCartId(userId);
        this.cartService.createCart(command);
    }

    @GetMapping(headers = { "query=QueryCart" })
    public Cart queryCart() {
        //
        QueryCart query = new QueryCart(userId);
        return this.cartService.queryCart(query);
    }

    @PutMapping(headers = { "command=AddItem" })
    public void addItem(@RequestBody AddItem command) {
        //
        command.setCartId(userId);
        this.cartService.addItem(command);
    }

    @PutMapping(headers = { "command=ChangeQuantity" })
    public void changeQuantity(@RequestBody ChangeQuantity command) {
        //
        command.setCartId(userId);
        this.cartService.changeQuantity(command);
    }

    @DeleteMapping(headers = { "command=RemoveItem" })
    public void removeItem(@RequestBody RemoveItem command) {
        //
        command.setCartId(userId);
        this.cartService.removeItem(command);
    }

    @GetMapping(headers = { "query=QueryCartView" })
    public List<ItemView> queryCartView() {
        //
        QueryCartView query = new QueryCartView(userId);
        return this.cartService.queryCartView(query);
    }

    @DeleteMapping(headers = { "command=RemoveCart" })
    public void removeCart() {
        //
        RemoveCart command = new RemoveCart(userId);
        this.cartService.removeCart(command);
    }
}
