package org.mos91.restfulexample.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Created by Oleg.Meleshin on 10/18/2016.
 */
@Data
public class Greeting {

    @NonNull
    private long id;

    @NonNull
    private String content;

}
