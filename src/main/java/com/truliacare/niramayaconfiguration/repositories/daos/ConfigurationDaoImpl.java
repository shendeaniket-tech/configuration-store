package com.truliacare.niramayaconfiguration.repositories.daos;

import com.google.gson.Gson;
import com.truliacare.niramayaconfiguration.util.GeneralApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConfigurationDaoImpl implements ConfigurationDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String processQuery(String query) {
        try {
            Object result = jdbcTemplate.query(query, (rs, rowNum) -> {
                Map<String, Object> resultMap = new HashMap<>();
                for (int index = 1; index <= rs.getMetaData().getColumnCount(); index++) {
                    String columnName = rs.getMetaData().getColumnLabel(index);
                    Object columnValue = rs.getObject(index);
                    resultMap.put(columnName, columnValue);
                }
                return resultMap;
            });
            return new Gson().toJson(result);
        } catch (Exception e) {
            return GeneralApplicationUtils.getErrorResponse(e.getMessage(), 500);
        }
    }
}
