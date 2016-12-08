package dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by hochulshin on 12/7/16.
 */
public interface ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
