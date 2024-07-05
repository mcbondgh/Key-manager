package app.models;

import app.conf.DAO;
import app.entities.KeysEntity;

public class KeyModel extends DAO {

    protected int saveKey(KeysEntity entity)
    {
        try {
            String query = """
                    INSERT INTO `keys`(key_code, key_count, room_id, block_id, key_status, notes)
                    VALUES(?, ?, ?, ?, ?, ?);
                    """;
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, entity.getKey_code());
            preparedStatement.setInt(2, entity.getKey_count());
            preparedStatement.setInt(3, entity.getRoom_id());
            preparedStatement.setInt(4, entity.getBlock_id());
            preparedStatement.setBoolean(5, entity.isKey_status());
            preparedStatement.setString(6, entity.getNotes());
            return preparedStatement.executeUpdate();
        }catch (Exception ignore){}
        return 0;
    }

    protected int updateKey(KeysEntity entity) {
        try {
            String query = """
                        UPDATE `keys` SET key_code = ?, key_count = ?, room_id =?,\s
                        block_id = ?, notes = ?, date_modified = DEFAULT, modified_by =?, key_status = ?
                        WHERE key_id = ?;
                    """;
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, entity.getKey_code());
            preparedStatement.setInt(2, entity.getKey_count());
            preparedStatement.setInt(3, entity.getRoom_id());
            preparedStatement.setInt(4, entity.getBlock_id());
            preparedStatement.setString(5, entity.getNotes());
            preparedStatement.setInt(6, entity.getModified_by());
            preparedStatement.setBoolean(7, entity.isKey_status());
            preparedStatement.setInt(8, entity.getKey_id());
            return preparedStatement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
        return 0;
    }
}
