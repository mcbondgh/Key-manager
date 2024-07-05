package app.models;

import app.conf.DAO;
import app.entities.BlocksAndRoomsEntity;

public class BlocksAndRoomsModel extends DAO {

    protected int saveBlock(BlocksAndRoomsEntity entity) {
        try{
            String query = "INSERT INTO blocks(block_name, room_count, block_alias, notes)\n" +
                    "VALUES(?, ?, ?, ?)";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, entity.getBlock_name());
            preparedStatement.setInt(2, entity.getRoom_count());
            preparedStatement.setString(3, entity.getBlock_alias());
            preparedStatement.setString(4, entity.getNotes());
            return preparedStatement.executeUpdate();
        }catch (Exception ignore){};
        return 0;
    }

    protected int updateBlock(BlocksAndRoomsEntity entity){
        try {
            String query = """
                    UPDATE blocks SET block_name = ?, room_count = ?, block_alias = ?, date_modified = DEFAULT, notes = ?, modified_by = ?\s
                    WHERE(block_id = ?);
                    """;
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, entity.getBlock_name());
            preparedStatement.setInt(2, entity.getRoom_count());
            preparedStatement.setString(3, entity.getBlock_alias());
            preparedStatement.setString(4, entity.getNotes());
            preparedStatement.setInt(5, entity.getModified_by());
            preparedStatement.setInt(6, entity.getBlock_id());
            return preparedStatement.executeUpdate();
        }catch (Exception ignore){ignore.printStackTrace();}
        return 0;
    }

    protected int insertIntoRoomsTable(BlocksAndRoomsEntity entity) {
        try {
//            room_id, room_number, room_type, room_status, block_id, date_modified
            String query = """
                    INSERT INTO rooms(room_number, room_type, room_status, block_id)
                    VALUES(?, ?, ?, ?);
                    """;
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, entity.getRoom_number());
            preparedStatement.setString(2, entity.getRoom_type());
            preparedStatement.setBoolean(3, entity.isRoom_status());
            preparedStatement.setInt(4, entity.getBlock_id());
            return preparedStatement.executeUpdate();
        }catch (Exception ignore){ignore.printStackTrace();}
        return 0;
    }

    protected int updateRoom(BlocksAndRoomsEntity entity)
    {
        try {
            String query = """
                    UPDATE rooms SET room_number = ?, room_type = ?,
                     room_status = ?, notes = ?,\s
                     block_id = ?, date_modified = DEFAULT\s
                    WHERE room_id = ?;
                    """;
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, entity.getRoom_number());
            preparedStatement.setString(2, entity.getRoom_type());
            preparedStatement.setBoolean(3, entity.isRoom_status());
            preparedStatement.setString(4, entity.getNotes());
            preparedStatement.setInt(5, entity.getBlock_id());
            preparedStatement.setInt(6, entity.getRoom_id());
            return preparedStatement.executeUpdate();
        }catch(Exception ignore) {ignore.printStackTrace();}

        return 0;
    }
}//end of class...
