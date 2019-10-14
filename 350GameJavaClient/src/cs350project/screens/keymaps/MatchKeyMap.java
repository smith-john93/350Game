/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.keymaps;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Mark Masone
 */

/*
Key bindings for the fight screen.
*/
public class MatchKeyMap extends KeyMap<MatchKeyMapListener> {
    
    private final ChatKeyMap chatKeyMap;
    private final MovementKeyMap movementKeyMap;
    private final MenuKeyMap menuKeyMap;
    
    public MatchKeyMap() {
        chatKeyMap = new ChatKeyMap();
        movementKeyMap = new MovementKeyMap();
        menuKeyMap = new MenuKeyMap();
        addKeyMap(chatKeyMap);
        addKeyMap(movementKeyMap);
        addKeyMap(menuKeyMap);
    }
    
    @Override
    public void addKeyMapListener(MatchKeyMapListener matchKeyMapListener) {
        if(matchKeyMapListener instanceof ChatKeyMapListener) {
            chatKeyMap.addKeyMapListener((ChatKeyMapListener)matchKeyMapListener);
        } else if(matchKeyMapListener instanceof MovementKeyMapListener) {
            movementKeyMap.addKeyMapListener((MovementKeyMapListener)matchKeyMapListener);
        } else if(matchKeyMapListener instanceof MenuKeyMapListener) {
            menuKeyMap.addKeyMapListener((MenuKeyMapListener)matchKeyMapListener);
        } else {
            super.addKeyMapListener(matchKeyMapListener);
        }
    }
}
