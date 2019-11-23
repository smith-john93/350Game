/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;
import cs350project.screens.KeyMap;
import java.util.HashMap;

/**
 *
 * @author Mark Masone
 */

/*
Key bindings for the fight screen.
*/
public class MatchKeyMap extends KeyMap<MatchInputListener> {
    
    private final ChatKeyMap chatKeyMap;
    private final MovementKeyMap movementKeyMap;
    private final MatchMenuKeyMap menuKeyMap;
    private final AttackKeyMap attackKeyMap;
    
    public MatchKeyMap(HashMap<Integer, Integer> keyMappings) {
        chatKeyMap = new ChatKeyMap();
        movementKeyMap = new MovementKeyMap(keyMappings);
        menuKeyMap = new MatchMenuKeyMap();
        attackKeyMap = new AttackKeyMap();
        addKeyMap(chatKeyMap);
        addKeyMap(movementKeyMap);
        addKeyMap(menuKeyMap);
        addKeyMap(attackKeyMap);
    }
    
    @Override
    public void addInputListener(MatchInputListener matchInputListener) {
        if(matchInputListener instanceof ChatInputListener) {
            chatKeyMap.addInputListener((ChatInputListener)matchInputListener);
        } 
        if(matchInputListener instanceof MovementInputListener) {
            movementKeyMap.addInputListener((MovementInputListener)matchInputListener);
        } 
        if(matchInputListener instanceof MatchMenuInputListener) {
            menuKeyMap.addInputListener((MatchMenuInputListener)matchInputListener);
        } 
        if(matchInputListener instanceof AttackInputListener) {
            attackKeyMap.addInputListener((AttackInputListener)matchInputListener);
        }
    }
}
