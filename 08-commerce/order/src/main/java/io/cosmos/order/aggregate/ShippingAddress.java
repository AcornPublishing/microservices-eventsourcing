package io.cosmos.order.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShippingAddress {
    //
    private String zipCode;
    private String baseAddress;
    private String detailAddress;
}
