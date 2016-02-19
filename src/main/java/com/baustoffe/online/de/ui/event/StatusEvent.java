package com.baustoffe.online.de.ui.event;

/**
 * @author Benjamin Wilms (xd98870)
 */
public class StatusEvent {

    private String system;

    private String status;

    public StatusEvent(String status, String system) {
        this.status = status;
        this.system = system;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }
}
