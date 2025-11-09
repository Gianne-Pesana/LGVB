package com.leshka_and_friends.lgvb.notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager implements Observable {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void removeAllObserver() {
        observers.clear();
    }

    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }
}
