package cs350project.screens.login;

import cs350project.menu.BackButtonListener;

public interface LoginInputListener extends BackButtonListener {
    void login(String username,char[] password);
}
