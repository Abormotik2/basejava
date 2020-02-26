package com.urise.webapp.sql;

import java.sql.SQLException;

public interface ConnectionFactory {
    ConnectionFactory getConnection() throws SQLException;
}
