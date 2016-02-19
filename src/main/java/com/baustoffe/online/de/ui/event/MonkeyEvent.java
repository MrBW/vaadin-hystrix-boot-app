package com.baustoffe.online.de.ui.event;

import java.util.Date;

/**
 * @author Benjamin Wilms (xd98870)
 */
public class MonkeyEvent {

    private Date time;

    private String message;

    public MonkeyEvent(String message, Date time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
