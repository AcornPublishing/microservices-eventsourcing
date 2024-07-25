package io.cosmos.order.endpoint;

import io.cosmos.order.aggregate.Order;
import io.cosmos.order.aggregate.Orderer;
import io.cosmos.order.command.CancelOrder;
import io.cosmos.order.command.CompleteOrder;
import io.cosmos.order.command.PlaceOrder;
import io.cosmos.order.query.QueryOrder;
import io.cosmos.order.query.QueryOrders;
import io.cosmos.order.service.OrderService;
import io.cosmos.order.view.orderview.OrderView;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/order")
public class OrderEndpoint {
    //
    private final OrderService orderService;
    private final HttpSession httpSession;

    public OrderEndpoint(OrderService orderService,
                         HttpSession httpSession) {
        //
        this.orderService = orderService;
        this.httpSession = httpSession;
    }

    @PostMapping(headers = { "command=PlaceOrder" })
    public String placeOrder(@RequestBody PlaceOrder command) {
        //
        String orderNo = UUID.randomUUID().toString().split("-")[0];
        command.setNo(orderNo);

        String userNo = this.httpSession.getAttribute("no").toString();
        String userName = this.httpSession.getAttribute("name").toString();
        command.setOrderer(new Orderer(userNo, userName));

        return this.orderService.place(command);
    }

    @GetMapping(value = "/{no}", headers = { "query=QueryOrder" })
    public Order queryOrder(@PathVariable String no) {
        //
        QueryOrder query = new QueryOrder(no);
        return this.orderService.queryOrder(query);
    }

    @GetMapping(headers = { "query=QueryOrders" })
    public List<OrderView> queryOrders() {
        //
        String userNo = this.httpSession.getAttribute("no").toString();
        QueryOrders query = new QueryOrders(userNo);
        return this.orderService.queryOrders(query);
    }

    @PutMapping(value = "/{no}", headers = { "command=CancelOrder" })
    public void cancelOrder(@PathVariable String no, @RequestBody CancelOrder command) {
        //
        command.setNo(no);
        this.orderService.cancel(command);
    }

    @PutMapping(value = "/{no}", headers = { "command=CompleteOrder" })
    public void completeOrder(@PathVariable String no) {
        //
        CompleteOrder command = new CompleteOrder(no);
        this.orderService.complete(command);
    }
}