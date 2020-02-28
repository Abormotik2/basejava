package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public interface Executor<T> {
        void execute(PreparedStatement ps) throws SQLException;
    }

    public <T> void execute(String sql, Executor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            executor.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}