package com.leshka_and_friends.lgvb.preferences;

public class NotificationPreferences {
    private boolean email = true;
    private boolean sms = true;
    private boolean push = true;

    public NotificationPreferences() {
    }

    public NotificationPreferences(boolean email, boolean sms, boolean push) {
        this.email = email;
        this.sms = sms;
        this.push = push;
    }

    // Getters & setters
    public boolean isEmail() { return email; }
    public void setEmail(boolean email) { this.email = email; }

    public boolean isSms() { return sms; }
    public void setSms(boolean sms) { this.sms = sms; }

    public boolean isPush() { return push; }
    public void setPush(boolean push) { this.push = push; }
}
