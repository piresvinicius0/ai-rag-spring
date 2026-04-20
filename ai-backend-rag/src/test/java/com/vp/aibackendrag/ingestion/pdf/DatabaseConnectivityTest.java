package com.vp.aibackendrag.ingestion.pdf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class DatabaseConnectivityTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void testDatabaseConnection() {
        String sql = "SELECT 1";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        assert result != null && result == 1 : "Database connection failed or returned unexpected result.";
    }
}
