package app.conf;

import app.entities.BlocksAndRoomsEntity;
import app.entities.KeysEntity;
import app.entities.UsersEntity;
import io.github.palexdev.materialfx.collections.ObservableStack;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.sql.Timestamp;

public class DAO extends DbConfig{

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
        }catch (SQLException e){e.printStackTrace();}
        return data;
    }

    public int fetchUserIdByName(String username) {
        try{
            String query = "SELECT user_id FROM users WHERE(username = '"+username+"')";
            resultSet = getConnection().createStatement().executeQuery(query);
            if (resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (Exception ignore) {

        }
        return 0;
    }

    public ObservableList<BlocksAndRoomsEntity> fetchAllBlocks() {
        ObservableList<BlocksAndRoomsEntity> data = new ObservableStack<>();
        try {
            String query = "SELECT * FROM blocks";
            resultSet = getConnection().createStatement().executeQuery(query);
            while(resultSet.next()){
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
            getConnection().close();
        }catch (Exception ignore){}
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
            while(resultSet.next()){
//              room_id, room_number, room_type, room_status, notes, block_id, date_modified
                int id = resultSet.getInt("room_id");
                String room = resultSet.getString("room_number");
                String type = resultSet.getString("room_type");
                boolean status = resultSet.getBoolean("room_status");
                String alias = resultSet.getString("block_alias");
                String notes = resultSet.getString("r.notes");
            data.add(new BlocksAndRoomsEntity(room, type, status, id, alias, notes));
            }
        }catch (SQLException ignore){}
        return data;
    }
    public ObservableList<String> fetchRoomsByBlockAlias(String input)
    {
        ObservableList<String> data = new ObservableStack<>();
        try{
            String query = "SELECT room_number FROM rooms AS r\n" +
                    "WHERE (r.room_status = true AND block_id = (SELECT block_id FROM blocks WHERE block_alias = '"+input+"'));";
            resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                data.add(resultSet.getString(1));
            }
            getConnection().close();
        }catch (Exception ignore){}
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
            while(resultSet.next()) {
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
        }catch (Exception e){}
        return data;
    }
    public ObservableList<Object>fetchAllDepartments() {
        ObservableList<Object> data = new ObservableStack<>();
        try {
            resultSet = getConnection().createStatement().executeQuery("SELECT * FROM departments");
            while(resultSet.next()) {
                data.add(resultSet.getString("department"));
            }
        }catch (Exception ignored){}

        return data;
    }

}//end of class
