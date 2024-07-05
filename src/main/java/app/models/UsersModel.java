package app.models;

import app.conf.DAO;
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

}
