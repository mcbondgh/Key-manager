package app.conf.security;

import app.conf.DAO;
import app.entities.AuthenticationData;
import app.entities.SettingsEntity;
import app.entities.UsersEntity;
import javafx.beans.NamedArg;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class UserAuthentication {

    DAO dao_obj = new DAO();
    private String roleName;
    private boolean status;

    public List<AuthenticationData> hasAccess(@NamedArg("role name") String roleName) {
        List<AuthenticationData>   data = new ArrayList<>();
        for(SettingsEntity items : dao_obj.fetchAccessControlList()) {
            if (items.getRole_name().equalsIgnoreCase(roleName)) {
                data.add(new AuthenticationData(items.getView_id(), items.isAllowed()));
            }
        }
        return data;
    }

    public boolean authenticateUser(String username, String password) {
        for (UsersEntity item : dao_obj.fetchAllUsers()) {
            if (Objects.equals(item.getUsername(), username)) {
                setRoleName(item.getRole_name());
                return DataEncryption.checkPwd(item.getPassword(), password);
            }
        }
        return false;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



}//end of class...
