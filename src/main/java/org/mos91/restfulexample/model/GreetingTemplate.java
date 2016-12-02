package org.mos91.restfulexample.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by Oleg.Meleshin on 10/24/2016.
 */
@Data
@Entity
@Table(name = "TEMPLATE_TBL", schema = "PUBLIC")
@SequenceGenerator(name = "template_id_gen", sequenceName = "template_tbl_seq")
public class GreetingTemplate {

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "template_id_gen")
    private Long id;

    @NonNull
    private String content;

    public GreetingTemplate() {

    }

    public GreetingTemplate(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
