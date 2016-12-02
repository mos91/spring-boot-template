package org.mos91.restfulexample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Oleg.Meleshin on 10/18/2016.
 */
@JsonIgnoreProperties
@Data
@SequenceGenerator(name = "quote_id_gen", sequenceName = "quote_tbl_seq")
@Entity
@Table(name = "QUOTE_TBL", schema = "PUBLIC")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_id_gen")
    private Long id;

    private String type;

    @Embedded
    private Value value;

    public Quote copyOf() {
        Quote copy = new Quote();
        copy.setId(id);
        copy.setType(type);
        copy.setValue(value.copyOf());
        return copy;
    }
}
