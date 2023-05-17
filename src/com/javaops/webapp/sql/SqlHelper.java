package com.javaops.webapp.sql;

import com.javaops.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper<T> {
    private final String sqlCommand;
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String sqlCommand, ConnectionFactory connectionFactory) {
        this.sqlCommand = sqlCommand;
        this.connectionFactory = connectionFactory;
    }

    public String getSqlCommand() {
        return sqlCommand;
    }

    public T make(Helper<T> helper) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(getSqlCommand())
        ) {
            return helper.doCommand(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
