/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.screens.match;

import cs350project.characters.PlayerCharacter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Mark Masone
 */
public class Combat implements AttackInputListener {

    private final ArrayList<Attack> attacks;
    private Timer comboTimer;
    private final PlayerCharacter character;
    
    public Combat(PlayerCharacter character) {
        attacks = new ArrayList<>();
        this.character = character;
    }
    
    private void addAttack(Attack attack) {
        if(comboTimer != null) {
            comboTimer.cancel();
        }
        //System.out.println(attack);
        comboTimer = new Timer();
        comboTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                attacks.clear();
                //System.out.println("combo canceled");
            }
        }, 1000);
        attacks.add(attack);
        int comboLength = attacks.size();
        Attack[] attacksArray = new Attack[comboLength];
        attacks.toArray(attacksArray);
        Attack[] combo1 = {Attack.PUNCH,Attack.PUNCH,Attack.HIGH_KICK,Attack.BLOCK};
        if(compareCombos(attacksArray,combo1)) {
            attacks.clear();
            System.out.println("HADOKEN!");
        }
    }
    
    private boolean compareCombos(Attack[] combo1, Attack[] combo2) {
        return combo1.length >= combo2.length && Arrays.compare(
                combo1, combo1.length - combo2.length, combo1.length - 1, 
                combo2, 0, combo2.length - 1
        ) == 0;
    }
    
    @Override
    public void punch() {
        addAttack(Attack.PUNCH);
    }

    @Override
    public void highKick() {
        addAttack(Attack.HIGH_KICK);
    }
    
    @Override
    public void lowKick() {
        addAttack(Attack.LOW_KICK);
    }
}
