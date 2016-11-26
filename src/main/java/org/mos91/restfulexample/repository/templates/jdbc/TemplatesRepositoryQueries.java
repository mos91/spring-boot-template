package org.mos91.restfulexample.repository.templates.jdbc;

import org.mos91.restfulexample.commons.queries.YamlQueries;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg.Meleshin on 10/24/2016.
 */
public class TemplatesRepositoryQueries extends YamlQueries {

    public TemplatesRepositoryQueries(String resourcePath) throws IOException {
        super(resourcePath);
    }

    public String insert() {
        return query("insert");
    }

    public String selectById() {
        return query("selectById");
    }

    public String exists() {
        return query("exists");
    }

    public String selectAll() {
        return query("selectAll");
    }

    public String selectAllByIds(Iterable<Long> ids) {
        Map<String, Iterable<Long>> params = new HashMap();
        params.put("ids", ids);
        return query("selectAllByIds", params);
    }

    public String count() {
        return query("count");
    }

    public String deleteById() {
        return query("deleteById");
    }

    public String deleteByIds(Iterable<Long> ids) {
        Map<String, Iterable<Long>> params = new HashMap();
        params.put("ids", ids);
        return query("deleteByIds", params);
    }

    public String deleteAll() {
        return query("deleteAll");
    }

}
