package org.mos91.restfulexample.repository.config;

import org.mos91.restfulexample.commons.queries.YamlQueries;

import java.io.IOException;

/**
 * Created by Oleg.Meleshin on 10/25/2016.
 */
public class TemplatesRepositoryTestQueries extends YamlQueries {

    protected TemplatesRepositoryTestQueries(String resourcePath) throws IOException {
        super(resourcePath);
    }

    public String selectAll() {
        return query("selectAll");
    }

    public String insert() {
        return query("insert");
    }
}
