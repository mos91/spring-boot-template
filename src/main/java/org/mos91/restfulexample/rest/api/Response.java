package org.mos91.restfulexample.rest.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

/**
 * Created by Oleg.Meleshin on 10/21/2016.
 */
@Data
@Builder
@JsonPropertyOrder({"success", "payload", "error"})
public class Response {

    private boolean success;

    private Error error;

    private Object payload;

    public static ResponseEntity success(Object payload) {
        ResponseBuilder builder = Response.builder();
        Response response = builder.success(true).payload(payload).build();
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity error(Throwable throwable, int status, String message) {
        ResponseBuilder builder = Response.builder();
        Error error = new Error();
        error.setThrowable(throwable.getClass().getName());
        error.setMessage(message);
        error.setTimestamp(new Date().getTime());
        error.setStatus(status);

        Response response = builder.success(false).error(error).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
    }

}
