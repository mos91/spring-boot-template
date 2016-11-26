package org.mos91.restfulexample.commons.queries;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg.Meleshin on 10/24/2016.
 */
public class YamlQueries {

    private String resourcePath;

    private Map<String, String> queries = new HashMap();

    protected YamlQueries(String resourcePath) throws IOException {
        this.resourcePath = resourcePath;
        Yaml yaml = new Yaml();

        this.queries = (Map) yaml.load(new ClassPathResource(resourcePath).getInputStream());
    }

    protected String query(String key) {
        return queries.get(key);
    }

    protected String query(String key, String paramKey, Object paramValue) {
        String query = queries.get(key);
        JtwigTemplate template = JtwigTemplate.inlineTemplate(query);
        JtwigModel model = JtwigModel.newModel().with(paramKey, paramValue);

        return template.render(model);
    }

    protected String query(String key, Map params) {
        String query = queries.get(key);
        JtwigTemplate template = JtwigTemplate.inlineTemplate(query);
        JtwigModel model = JtwigModel.newModel(params);

        return template.render(model);
    }
}
