package org.mos91.restfulexample.model;

import lombok.Data;

/**
 * Created by Oleg.Meleshin on 10/18/2016.
 */
@Data
public class Value {

    private String quote;

    public Value copyOf() {
        Value copy = new Value();
        copy.setQuote(quote);
        return copy;
    }
}
