package com.leshka_and_friends.lgvb.notification;

public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(String message);
}
