package com.javaops.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Helper <T> {
     T doCommand(PreparedStatement ps) throws SQLException;
}
