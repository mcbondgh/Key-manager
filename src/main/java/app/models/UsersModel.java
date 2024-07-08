package app.models;

import app.conf.DAO;
import app.entities.KeyTransactionsEntity;
import app.entities.PeopleEntity;
import app.entities.UsersEntity;

import java.sql.SQLException;

public class UsersModel extends DAO {

    protected int saveUser(UsersEntity entity) {
        try {
            String query = """
                    INSERT INTO users(username, password, role_name, modified_by)
                    VALUES(?, ?, ?, ?);
                    """;
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getRole_name());
            preparedStatement.setInt(4, entity.getModified_by());
            return preparedStatement.executeUpdate();
        } catch (Exception ignore) {
        }
        return 0;
    }

    protected int updateUserDetails(UsersEntity entity) {
        try {
            String query = """
                    UPDATE users\s
                    	SET username = ?,\s
                    	password = ?, role_name = ?,
                    	date_modified = CURRENT_TIMESTAMP(),\s
                        modified_by = ?
                    WHERE(user_id = ?);
                    """;
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setString(3, entity.getRole_name());
            preparedStatement.setInt(4, entity.getModified_by());
            preparedStatement.setInt(5, entity.getUser_id());
            return preparedStatement.executeUpdate();
        } catch (SQLException ignored) {
        }
        return 0;
    }

    protected void updateUserStatus(int userId, byte status) {
        try {
            getConnection().createStatement().execute("UPDATE users SET is_active = '" + status + "' WHERE(user_id = '" + userId + "')");
            getConnection().close();
        } catch (SQLException ignore) {
        }
    }

    protected int savePerson(PeopleEntity entity) {
        try {
            String query = "INSERT INTO people(fullname, mobile_number, card_number, date_issued, expiry_date, image, title, department)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getMobile());
            preparedStatement.setString(3, entity.getCardNumber());
            preparedStatement.setDate(4, entity.getIssueDate());
            preparedStatement.setDate(5, entity.getExpiryDate());
            preparedStatement.setBytes(6, entity.getImageData());
            preparedStatement.setString(7, entity.getTitle());
            preparedStatement.setString(8, entity.getDepartment());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected int updatePerson(PeopleEntity entity) {
        try {
            String query = """
                    UPDATE people SET fullname = ?, mobile_number = ?, card_number = ?, date_issued = ?,
                    expiry_date = ?, image = ?, title = ?, department = ?, date_modified = DEFAULT,\s
                    modified_by = ? WHERE(record_id = ?);
                    """;
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getMobile());
            preparedStatement.setString(3, entity.getCardNumber());
            preparedStatement.setDate(4, entity.getIssueDate());
            preparedStatement.setDate(5, entity.getExpiryDate());
            preparedStatement.setBytes(6, entity.getImageData());
            preparedStatement.setString(7, entity.getTitle());
            preparedStatement.setString(8, entity.getDepartment());
            preparedStatement.setInt(9, entity.getModifiedBy());
            preparedStatement.setInt(10, entity.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException ignored) {
        }
        return 0;
    }

    protected int updatePersonStatusById(int personId, boolean statusValue) {
        try {
            String query = "UPDATE people SET is_allowed = ? WHERE(record_id = ?)";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setBoolean(1, statusValue);
            preparedStatement.setInt(2, personId);
            return preparedStatement.executeUpdate();
        } catch (SQLException ignore) {}
        return 0;
    }

    protected int issueKey(KeyTransactionsEntity entity) {
        try {
            String query = "INSERT INTO key_transaction(key_code, block_alias, card_number, purpose, user_id)\n" +
                    "VALUES(?, ?, ?, ?, ?);";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, entity.getKeyCode());
            preparedStatement.setString(2, entity.getBlockAlias());
            preparedStatement.setString(3, entity.getCardNumber());
            preparedStatement.setString(4, entity.getPurpose());
            preparedStatement.setInt(5, entity.getUserId());
            return preparedStatement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
        return 0;
    }

    protected int updateBookedKeyStatus(boolean status, String keyCode, int blockId) {
        try {
            String query2 = "UPDATE `keys` SET is_booked = ? WHERE(key_code = ? AND block_id = ?);";
            preparedStatement = getConnection().prepareStatement(query2);
            preparedStatement.setBoolean(1, status);
            preparedStatement.setString(2, keyCode);
            preparedStatement.setInt(3, blockId);
           return preparedStatement.executeUpdate();
        }catch (Exception ignore){}
        return 0;
    }

    protected void updateReturnedDate(int transId, String returnedBy) {
        try {
            String query = "UPDATE key_transaction SET returned_date = CURRENT_TIMESTAMP(), returned_by = ? WHERE id = ?;";
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, returnedBy);
            preparedStatement.setInt(2, transId);
            preparedStatement.execute();
        }catch (SQLException ignore){}
    }

}//end of class...
