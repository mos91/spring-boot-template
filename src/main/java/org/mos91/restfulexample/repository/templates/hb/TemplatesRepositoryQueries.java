package org.mos91.restfulexample.repository.templates.hb;

import org.mos91.restfulexample.commons.queries.YamlQueries;

import java.io.IOException;

/**
 * Created by Oleg.Meleshin on 10/27/2016.
 */
public class TemplatesRepositoryQueries extends YamlQueries {

    public TemplatesRepositoryQueries(String resourcePath) throws IOException {
        super(resourcePath);
    }

    public String delete() {
        return query("delete");
    }

    public String deleteAll() {
        return query("deleteAll");
    }

}
