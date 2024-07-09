package app.entities;

public class SettingsEntity {
    private int id;
    private String view_id, role_name;
    boolean allowed;

    public SettingsEntity(int id, String view_id, String role_name, boolean allowed) {
        this.id = id;
        this.view_id = view_id;
        this.role_name = role_name;
        this.allowed = allowed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getView_id() {
        return view_id;
    }

    public void setView_id(String view_id) {
        this.view_id = view_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
}//end of class...
