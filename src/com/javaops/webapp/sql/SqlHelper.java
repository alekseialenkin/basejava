package com.javaops.webapp.sql;

import com.javaops.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper<T> {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    public T make(String sqlCommand, Helper<T> helper) {
        try (Connection conn = connectionFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            return helper.doCommand(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
