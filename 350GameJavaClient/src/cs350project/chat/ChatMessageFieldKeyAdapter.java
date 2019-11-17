package cs350project.chat;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatMessageFieldKeyAdapter extends KeyAdapter {
    private final ChatMessageFieldKeyListener chatMessageFieldKeyListener;

    public ChatMessageFieldKeyAdapter(ChatMessageFieldKeyListener chatMessageFieldKeyListener) {
        this.chatMessageFieldKeyListener = chatMessageFieldKeyListener;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                chatMessageFieldKeyListener.messageCommit();
                break;
            case KeyEvent.VK_C:
                if(ke.isControlDown())
                    chatMessageFieldKeyListener.messageCancel();
                break;
        }
    }
}
