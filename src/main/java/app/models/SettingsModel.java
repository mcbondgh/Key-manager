package app.models;

import app.conf.DAO;

public class SettingsModel extends DAO {

    protected int saveConfiguration(String viewId, String role, boolean status ) {
        try {
            String query = """
                    INSERT INTO access_control(view_id, role_name, is_allowed)
                    VALUES(?, ?, ?)\s
                    ON DUPLICATE KEY UPDATE
                    is_allowed = ?;
                    """;
            preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, viewId);
            preparedStatement.setString(2, role);
            preparedStatement.setBoolean(3, status);
            preparedStatement.setBoolean(4, status);
            return preparedStatement.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
     return 0;
    }

}
