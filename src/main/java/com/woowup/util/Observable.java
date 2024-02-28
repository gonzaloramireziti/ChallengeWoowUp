package com.woowup.util;

import com.woowup.entities.Alert;
import com.woowup.entities.User;

public interface Observable {

    void addObserver(User user);

    void deleteObserver(User user);

    void notifyObservers(Alert alert);

}
