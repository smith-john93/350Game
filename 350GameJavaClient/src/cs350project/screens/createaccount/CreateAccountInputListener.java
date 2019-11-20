package cs350project.screens.createaccount;

import cs350project.menu.BackButtonListener;

public interface CreateAccountInputListener extends BackButtonListener {
    void createAccount(String username, char[] password);
}
