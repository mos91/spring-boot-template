package org.mos91.restfulexample.repository.templates.jdbc;

import org.mos91.restfulexample.model.GreetingTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Oleg.Meleshin on 10/24/2016.
 */
public class GreetingTemplateMapper implements RowMapper<GreetingTemplate> {

    public static final String TABLE_NAME = "TEMPLATE_TBL";

    @Override
    public GreetingTemplate mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong(1);
        String content = resultSet.getString(2);

        return new GreetingTemplate(id, content);
    }
}
