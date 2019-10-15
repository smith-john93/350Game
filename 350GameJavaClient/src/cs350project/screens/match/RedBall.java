package cs350project.screens.match;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import cs350project.Command;
import cs350project.OutgoingCommandListener;
import cs350project.characters.*;
import cs350project.screens.keymaps.MovementKeyMapListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComponent;

public class RedBall extends JComponent implements ActionListener, MovementKeyMapListener {

    private int fuel = 0;
    private int x = 150;
    private int y = 30;
    private double ySpeed = 0.0;
    private double xSpeed = 0.0;

    private boolean jumpingLastTick = false;
    private boolean jumping = false;

    private boolean movingLeft = false;
    private boolean movingRight = false;

    private final Platform[] platArray = new Platform[7];
    private final int width = 50;
    private final int height = 50;
    private final PlayerCharacter character;

    private final ArrayList<OutgoingCommandListener> outgoingCommandListeners = new ArrayList<>();

    public void addOutgoingCommandListener(OutgoingCommandListener outgoingCommandListener) {
        outgoingCommandListeners.add(outgoingCommandListener);
    }

    private void sendCommand(Command command) {
        for(OutgoingCommandListener outgoingCommandListener : outgoingCommandListeners) {
            outgoingCommandListener.sendCommand(command);
        }
    }

    @Override
    public void startMoveLeft() {
        sendCommand(Command.START_MOVE_LEFT);
        movingLeft = true;
    }

    @Override
    public void startMoveRight() {
        sendCommand(Command.START_MOVE_RIGHT);
        movingRight = true;
    }

    @Override
    public void startJump() {
        sendCommand(Command.START_JUMP);
        if(!jumping) {
            jumping = true;
        }
    }

    @Override
    public void endMoveLeft() {
        sendCommand(Command.END_MOVE_LEFT);
        movingLeft = false;
    }

    @Override
    public void endMoveRight() {
        sendCommand(Command.END_MOVE_RIGHT);
        movingRight = false;
    }

    @Override
    public void endJump() {
        sendCommand(Command.END_JUMP);
        jumping = false;
    }

    public RedBall(PlayerCharacter character)
    {
        platArray[0] = new Platform(50,300,150,200);
        platArray[1] = new Platform(150,450,450,50);
        platArray[2] = new Platform(600,400,100,100);
        platArray[3] = new Platform(700,100,50,400);
        platArray[4] = new Platform(550,100,50,250);
        platArray[5] = new Platform(300,150,100,25);
        platArray[6] = new Platform(150,150,100,25);

        this.character = character;
        add(character);
    }
    
    @Override
    public Dimension getPreferredSize() {

        return new Dimension(800, 600);
    }

    @Override
    protected void paintComponent(Graphics g) {

        //draw the sky
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.GREEN);
        g.fillRect(0, 550, 800, 100);

        //draw plat
        for (Platform platform : platArray) {
            platform.paint(g);
        }

        if(character != null) {
            character.setBounds(x,y,width,height);
            if(movingRight)
                character.setDirection(1);
            if(movingLeft)
                character.setDirection(-1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        boolean jumpPressed = (jumping && !jumpingLastTick);


        if(movingLeft && movingRight)
        {
            if(xSpeed>1) xSpeed--;
            else if(xSpeed<-1) xSpeed++;
            else xSpeed=0;
        }
        else if(movingRight)
        {
            if(xSpeed>6) xSpeed--;
            else if(xSpeed<4) xSpeed++;
            else xSpeed=5;
        }
        else if(movingLeft)
        {
            if(xSpeed>-4) xSpeed--;
            else if(xSpeed<-4) xSpeed++;
            else xSpeed=-5;
        }
        else
        {
            if(xSpeed>1) xSpeed--;
            else if(xSpeed<-1) xSpeed++;
            else xSpeed=0;
        }

        x = x + (int)Math.ceil(xSpeed);

        boolean touchingWall = false;
        int xsign = (int) Math.signum(xSpeed);
        if(xsign!=0) {
            while (isTouchingAny()){
                x -= xsign;
                xSpeed = 0;

                touchingWall =true;
            }
        }

        ySpeed = ySpeed + 1;


        y = y + (int)Math.ceil(ySpeed);
        boolean touchingGround = false;
        int ysign = (int) Math.signum(ySpeed);
        if(ysign!=0) {
            while (isTouchingAny()) {
                y -= ysign;
                ySpeed = 0;

                if(ysign==1)
                {
                    touchingGround =true;
                    fuel = 100;
                }
            }
        }

        if(jumpPressed)
        {
            System.out.println("jump");

            if(touchingGround)
            {
                System.out.println("jumpy jump");
                ySpeed = -13;
            }
        }

        if (y >= 700 ) {

            y = 30;
        }


        if(character.getCharacterClass() == CharacterClass.MAGE)
        {
            if(fuel>0 && jumping)
            {
                if(ySpeed > -2)
                {
                    ySpeed = -2;
                    fuel-=1;

                    if(xSpeed>.5) xSpeed=.5;
                    else if(xSpeed<-.5)  xSpeed=-.5;
                }

            }
        }

        if(character.getCharacterClass() == CharacterClass.NINJA) {
            if (touchingWall) {
                if (jumpPressed && ySpeed > 0) {
                    ySpeed = -10;
                    xSpeed = 10 * xsign * -1;

                } else if (ySpeed > 0) {
                    ySpeed = 1;
                }
            }
            else if(fuel>0 && jumpPressed && !touchingGround)
            {
                ySpeed = -10;
                fuel=0;
            }
        }

        jumpingLastTick = jumping;

        repaint();
    }

    private boolean isTouching(Platform p) {
        return (x < p.x + p.width &&
                x + width > p.x &&
                y < p.y + p.height &&
                y + height > p.y);
            // collision detected
    }

    private boolean isTouchingAny() {
        for (Platform platform : platArray) {
            if (isTouching(platform)) return true;
        }
        return false;
    }
}

