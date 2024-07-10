package app.entities;

public class AuthenticationData {
    private String viewId;
    private boolean status;

    public AuthenticationData(String viewId, boolean status) {
        this.viewId = viewId;
        this.status = status;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
