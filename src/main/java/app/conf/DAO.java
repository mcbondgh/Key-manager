package app.conf;

import app.entities.*;
import io.github.palexdev.materialfx.collections.ObservableStack;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DAO extends DbConfig {

    public ObservableList<UsersEntity> fetchAllUsers() {
        ObservableList<UsersEntity> data = new ObservableStack<>();
        try {
            String query = "SELECT * FROM users;";
            resultSet = getConnection().createStatement().executeQuery(query);
//          user_id, username, password, is_active, is_deleted, role_name, date_created, date_modified, modified_by
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String name = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role_name");
                boolean active = resultSet.getBoolean("is_active");
                boolean deleted = resultSet.getBoolean("is_deleted");
                Timestamp create = resultSet.getTimestamp("date_created");
                Timestamp modified = resultSet.getTimestamp("date_modified");
                int by = resultSet.getInt("modified_by");
                data.add(new UsersEntity(id, name, password, active, deleted, role, create, modified, by));
            }
            resultSet.close();
            getConnection().close();
        } catch (SQLException ignored) {
        }
        return data;
    }

    public int fetchUserIdByName(String username) {
        try {
            String query = "SELECT user_id FROM users WHERE(username = '" + username + "')";
            resultSet = getConnection().createStatement().executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            resultSet.close();
            getConnection().close();
        } catch (Exception ignore) {

        }
        return 0;
    }

    public ObservableList<BlocksAndRoomsEntity> fetchAllBlocks() {
        ObservableList<BlocksAndRoomsEntity> data = new ObservableStack<>();
        try {
            String query = "SELECT * FROM blocks";
            resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
//                block_id, block_name, room_count, block_alias, notes, date_modified, modified_by
                int id = resultSet.getInt("block_id");
                String name = resultSet.getString("block_name");
                String alias = resultSet.getString("block_alias");
                int count = resultSet.getInt("room_count");
                String notes = resultSet.getString("notes");
                Timestamp date = resultSet.getTimestamp("date_modified");
                int userId = resultSet.getInt("modified_by");
                data.add(new BlocksAndRoomsEntity(id, count, userId, name, alias, notes, date));
            }
            resultSet.close();
            getConnection().close();
        } catch (Exception ignore) {
        }
        return data;
    }

    public ObservableList<BlocksAndRoomsEntity> fetchAllRooms() {
        ObservableList<BlocksAndRoomsEntity> data = new ObservableStack<>();
        try {
            String query = """
                    SELECT room_id, room_number, room_type, room_status, r.notes, block_alias
                    FROM rooms AS r
                    INNER JOIN blocks AS b USING(block_id);
                    """;
            resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
//              room_id, room_number, room_type, room_status, notes, block_id, date_modified
                int id = resultSet.getInt("room_id");
                String room = resultSet.getString("room_number");
                String type = resultSet.getString("room_type");
                boolean status = resultSet.getBoolean("room_status");
                String alias = resultSet.getString("block_alias");
                String notes = resultSet.getString("r.notes");
                data.add(new BlocksAndRoomsEntity(room, type, status, id, alias, notes));
            }
            resultSet.close();
            getConnection().close();
        } catch (SQLException ignore) {
        }
        return data;
    }

    public ObservableList<String> fetchRoomsByBlockAlias(String input) {
        ObservableList<String> data = new ObservableStack<>();
        try {
            String query = "SELECT room_number FROM rooms AS r\n" +
                    "WHERE (r.room_status = true AND block_id = (SELECT block_id FROM blocks WHERE block_alias = '" + input + "'));";
            resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                data.add(resultSet.getString(1));
            }
            resultSet.close();
            getConnection().close();
        } catch (Exception ignore) {
        }
        return data;
    }

    public ObservableList<KeysEntity> fetchAllKeys() {
        ObservableList<KeysEntity> data = new ObservableStack<>();
        try {
            String query = """
                    SELECT key_id, key_code, key_count, room_number, block_alias,\s
                    		key_status, k.notes, is_booked,
                            k.date_modified, k.modified_by FROM `keys` AS k
                    INNER JOIN blocks AS b USING(block_id)\s
                    INNER JOIN rooms AS r USING(room_id);
                    """;
            resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("key_id");
                String code = resultSet.getString("key_code");
                int counter = resultSet.getInt("key_count");
                String room = resultSet.getString("room_number");
                String alias = resultSet.getString("block_alias");
                boolean status = resultSet.getBoolean("key_status");
                String note = resultSet.getString("k.notes");
                boolean booked = resultSet.getBoolean("is_booked");
                Timestamp modified_date = resultSet.getTimestamp("k.date_modified");
                int userId = resultSet.getInt("k.modified_by");
                data.add(new KeysEntity(id, code, counter, alias, status, note, booked, modified_date, userId, room));
            }
            resultSet.close();
            getConnection().close();
        } catch (Exception e) {
        }
        return data;
    }

    public ObservableList<Object> fetchAllDepartments() {
        ObservableList<Object> data = new ObservableStack<>();
        try {
            resultSet = getConnection().createStatement().executeQuery("SELECT * FROM departments");
            while (resultSet.next()) {
                data.add(resultSet.getString("department"));
            }
            resultSet.close();
            getConnection().close();
        } catch (Exception ignored) {
        }

        return data;
    }

    public ObservableList<PeopleEntity> fetchAllPeople() {
        ObservableList<PeopleEntity> data = new ObservableStack<>();
        try {
            String query = "SELECT * FROM people";
            resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
//                record_id, fullname, mobile_number, email, card_number, date_issued, expiry_date, image, title, department, is_allowed, date_modified, modified_by
                int id = resultSet.getInt("record_id");
                String name = resultSet.getString("fullname");
                String number = resultSet.getString("mobile_number");
                String cardNo = resultSet.getString("card_number");
                Date issueDate = resultSet.getDate("date_issued");
                Date expiry = resultSet.getDate("expiry_date");
                byte[] image = resultSet.getBytes("image");
                String title = resultSet.getString("title");
                String department = resultSet.getString("department");
                boolean status = resultSet.getBoolean("is_allowed");
                data.add(new PeopleEntity(id, name, number, cardNo, title, department, issueDate, expiry, status, image));
            }
            resultSet.close();
            getConnection().close();
        } catch (SQLException e) {
        }
        return data;
    }

    protected ObservableList<PeopleEntity> getPersonByCardNumber(String cardNumber) {
        ObservableList<PeopleEntity> data = new ObservableStack<>();
        try {
            String query = "SELECT * FROM people WHERE card_number = '" + cardNumber + "'";
            resultSet = getConnection().createStatement().executeQuery(query);
            if (resultSet.next()) {
                String name = resultSet.getString("fullname");
                String title = resultSet.getString("title");
                String department = resultSet.getString("department");
                byte[] image = resultSet.getBytes("image");
                boolean isAllowed = resultSet.getBoolean("is_allowed");
                Date date = resultSet.getDate("expiry_date");
                data.add(new PeopleEntity(name, title, department, date, image, isAllowed));
            }
        } catch (Exception ignore) {}
        return data;
    }

    public ObservableList<KeyTransactionsEntity> fetchKeyTransactions() {
        ObservableList<KeyTransactionsEntity> data = new ObservableStack<>();
        try {
            String query = """
                    SELECT kt.id, kt.key_code, block_alias, is_booked,
                           kt.card_number, purpose,\s
                           transaction_date, return_status, returned_date, username\s
                           FROM key_transaction AS kt
                           INNER JOIN people AS p USING(card_number)\s
                           INNER JOIN users AS u USING(user_id)
                           INNER JOIN `keys` AS k USING(key_code)
                    WHERE((is_booked = TRUE OR DATE(transaction_date) = CURRENT_DATE) AND return_status = 0);
                    """;
            resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("kt.id");
                String code = resultSet.getString("kt.key_code");
                String alias = resultSet.getString("block_alias");
                boolean booked = resultSet.getBoolean("is_booked");
                String indexNumber = resultSet.getString("kt.card_number");
                String purpose = resultSet.getString("purpose");
                Timestamp issuedDate = resultSet.getTimestamp("transaction_date");
                data.add(new KeyTransactionsEntity(id, code, indexNumber, alias, purpose, booked, issuedDate));
            }
            resultSet.close();
            getConnection().close();
        } catch (Exception ignore) {
        }
        return data;
    }

    public ObservableList<KeyTransactionsEntity> fetchTransactionHistory(LocalDate start, LocalDate end) {
        ObservableList<KeyTransactionsEntity> data = new ObservableStack<>();
        try {
            String query = """
                    SELECT kt.id, kt.key_code, block_alias,
                     kt.card_number, purpose,\s
                     transaction_date, returned_date, returned_by, username\s
                     FROM key_transaction AS kt
                    INNER JOIN people AS p USING(card_number)\s
                    INNER JOIN users AS u USING(user_id)
                    INNER JOIN `keys` AS k USING(key_code)
                    WHERE (DATE(transaction_date) BETWEEN ? AND ? );
                    """;
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(start));
            preparedStatement.setDate(2, Date.valueOf(end));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("kt.id");
                String code = resultSet.getString("kt.key_code");
                String alias = resultSet.getString("block_alias");
                String indexNumber = resultSet.getString("kt.card_number");
                String purpose = resultSet.getString("purpose");
                Timestamp issuedDate = resultSet.getTimestamp("transaction_date");
                Timestamp returnedDate = resultSet.getTimestamp("returned_date");
                String returnedBy = resultSet.getString("returned_by");
                String username = resultSet.getString("username");
                data.add(new KeyTransactionsEntity(id, code, indexNumber, alias, purpose, returnedBy, issuedDate, returnedDate, username));
            }
            resultSet.close();
            getConnection().close();
        } catch (SQLException ignore) {
        }
        return data;
    }

    public ObservableList<SettingsEntity> fetchAccessControlList() {
        ObservableList<SettingsEntity> data = new ObservableStack<>();
        try {
            resultSet = getConnection().createStatement().executeQuery("SELECT * FROM access_control");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String viewId = resultSet.getString("view_id");
                String role = resultSet.getString("role_name");
                boolean status = resultSet.getBoolean("is_allowed");
                data.add(new SettingsEntity(id, viewId, role, status));
            }
            resultSet.close();
            getConnection().close();
        } catch (SQLException ignore) {
        }

        return data;
    }

    public Map<String, Object> getDashboardObjects() {
        Map<String, Object> data = new HashMap<>();
        try {
            String query1 = "SELECT COUNT(*) FROM blocks;";
            resultSet = getConnection().createStatement().executeQuery(query1);
            if (resultSet.next()) {
                data.put("totalBlocks", resultSet.getInt(1));
            }
            String query2 = "SELECT COUNT(*) FROM rooms;";
            resultSet = getConnection().createStatement().executeQuery(query2);
            if (resultSet.next()) {
                data.put("totalRooms", resultSet.getInt(1));
            }
            String query3 = "SELECT COUNT(*) FROM `keys`;";
            resultSet = getConnection().createStatement().executeQuery(query3);
            if (resultSet.next()) {
                data.put("totalKeys", resultSet.getInt(1));
            }
            String query4 = "SELECT COUNT(*) FROM `keys` WHERE is_booked = true;";
            resultSet = getConnection().createStatement().executeQuery(query4);
            if (resultSet.next()) {
                data.put("bookedKeys", resultSet.getInt(1));
            }
            String query5 = "SELECT COUNT(*) FROM `keys` WHERE is_booked = false;";
            resultSet = getConnection().createStatement().executeQuery(query5);
            if (resultSet.next()) {
                data.put("freeKeys", resultSet.getInt(1));
            }
            String query6 = "SELECT COUNT(*) FROM people WHERE is_allowed = 1;";
            resultSet = getConnection().createStatement().executeQuery(query6);
            if (resultSet.next()) {
                data.put("approvedPersons", resultSet.getInt(1));
            }
            getConnection().close();
            resultSet.close();
        } catch (Exception ignore) {

        }
        return data;
    }

    public ObservableList<UsersEntity> fetchActivityLogs(LocalDate start, LocalDate end) {
        ObservableList<UsersEntity> data = new ObservableStack<>();
        try {
            String query = "  SELECT log_id, username, DATE(log_date) AS date, TIME(log_date) AS time FROM activity_logs " +
                    "INNER JOIN users AS u ON u.user_id = performed_by WHERE(DATE(log_date) BETWEEN '"+start+"' AND '"+end+"')";
            resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Date date = resultSet.getDate(3);
                Time time = resultSet.getTime(4);
                String statement = "user logged into the system today " + date.toString() + " at " + time.toString();
                data.add(new UsersEntity(id, name,statement));
            }
        } catch (Exception ignore) {};


        return data;
    }

}//end of class
