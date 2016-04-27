/*
 * Copyright (c) 2015-2016, Pradeeban Kathiravelu and others. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.bmi.datacafe.mysql;

import edu.emory.bmi.datacafe.conf.ConfigReader;
import edu.emory.bmi.datacafe.constants.SqlConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Connects to the MySQL data server.
 */
public class MySQLConnector {
    private static Logger logger = LogManager.getLogger(MySQLConnector.class.getName());

    /**
     * Gets all the values from the given table for any attribute. Used to get the Ids.
     *
     * @param database        the database name
     * @param table           the table in the given database.
     * @param idAttribute     the attribute key.
     * @param limitingClauses WHERE<..>
     * @return idList the list of ids.
     */
    public List getAllIDs(String database, String table, String idAttribute, String... limitingClauses) {
        Connection con = null;
        List idList = new ArrayList<>();
        try {
            getMySQLDriver();
            con = DriverManager.getConnection(SqlConstants.MYSQL_URL_PREFIX + ConfigReader.getCompleteMySQLUrl() + "/" +
                            database + ConfigReader.getAdditionalMySQLConf(),
                    ConfigReader.getMySQLUserName(), ConfigReader.getMySQLPassword()
            );

            Statement st = con.createStatement();
            String sql = ("SELECT " + idAttribute + " FROM " + table);
            if (limitingClauses.length == 1) {
                sql += " " + limitingClauses[0];
            }
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString(idAttribute);
                idList.add(id);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception in initiating the connection", e);

        } finally {
            closeConnection(con);
        }
        return idList;
    }

    public void closeConnection(Connection con) {
        try {
            assert con != null;
            con.close();
            if (logger.isDebugEnabled()) {
                logger.debug("SQL Connection Closed..");
            }
        } catch (SQLException e) {
            logger.error("Exception in closing the connection");
        }
    }

    public void getMySQLDriver() {
        try {
            Class.forName(SqlConstants.MYSQL_DRIVER).newInstance();
        } catch (InstantiationException e) {
            logger.error("Exception initiating the instance", e);
        } catch (IllegalAccessException e) {
            logger.error("Illegal Access Exception", e);
        } catch (ClassNotFoundException e) {
            logger.error("Class not found Exception", e);
        }
    }
}
