package cs350project.screens.login;

import cs350project.screens.InputListener;

public interface LoginInputListener extends InputListener {
    void login(String username,char[] password);
}
