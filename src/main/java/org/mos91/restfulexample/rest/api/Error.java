package org.mos91.restfulexample.rest.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * Created by Oleg.Meleshin on 10/21/2016.
 */
@Data
@JsonPropertyOrder({"message", "throwable", "status", "timestamp"})
public class Error {

    private String throwable;

    private String message;

    private int status;

    private long timestamp;

}
