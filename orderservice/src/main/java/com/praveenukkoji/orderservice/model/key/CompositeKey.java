package com.praveenukkoji.orderservice.model.key;

import java.io.Serializable;
import java.util.UUID;

public class CompositeKey implements Serializable {
    private UUID order_id;
    private UUID product_id;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
