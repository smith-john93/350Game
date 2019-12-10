/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;

import cs350project.communication.IncomingCommandListener;
import cs350project.communication.ServerCommand;
import cs350project.screens.MessageDialog;
import java.awt.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Mark Masone
 */
public class Settings implements IncomingCommandListener {
    
    private static Settings settings;
    private Dimension screenDimension = new Dimension(1366,768);
    private HashMap<Integer, Integer> keyMappings = new HashMap<>();
    private HashMap<String,String> settingsHashMap;
    private final String settingsFileName = "settings.txt";
    
    public static final Dimension[] SCREEN_DIMENSIONS = {
        new Dimension(1366,768),
        new Dimension(1600,900),
        new Dimension(1920,1080)
    };
    
    public static final String GAME_TITLE = "Intergalactic Fighting League";
    
    public static final String MENU_BACKGROUND_FILE = "menu/background.jpg";
    public static final String TITLE_IMAGE_FILE = "menu/welcomeAfter.png";
    public static final String TITLE_ANIMATION_FILE = "menu/welcome.gif";
    
    public static final String FIELD_NAME_USERNAME = "Username";
    public static final String FIELD_NAME_PASSWORD = "Password";
    
    public static final Font BUTTON_FONT = new Font(Font.MONOSPACED,Font.BOLD,32);
    public static final Font BUTTON_FONT_MEDIUM = new Font(Font.MONOSPACED,Font.BOLD,24);
    public static final Font LIST_FONT = new Font(Font.MONOSPACED,Font.BOLD,32);
    public static final Font FONT_SETTING = new Font(Font.MONOSPACED,Font.PLAIN,24);
    public static final Font FONT_MENU_HEADING = new Font("Arial",Font.BOLD,40);
    public static final Font HEADING1_FONT = new Font("Arial",Font.BOLD,24);
    public static final Font FONT_MENU_FIELD = new Font("Arial",Font.BOLD,16);

    public static final Color TRANSPARENT = new Color(0,0,0,0);
    public static final Color MENU_BACKGROUND_COLOR = Color.black;
    public static final Color MENU_FOREGROUND_COLOR = Color.white;
    
    public static final Dimension MENU_BUTTON_DIMENSION = new Dimension(500,100);
    public static final Dimension MENU_BUTTON_SMALL_DIMENSION = new Dimension(150,50);
    public static final Dimension DIMENSION_MENU_TEXT_FIELD = new Dimension(300,30);
    
    public static final Border BORDER_MENU = new LineBorder(Color.white, 5);

    public static final int PADDING_MENU = 15;
    public static final Insets INSETS_MENU_NO_TOP = new Insets(0,PADDING_MENU,PADDING_MENU,PADDING_MENU);
    public static final Insets INSETS_MENU_NO_BOTTOM = new Insets(PADDING_MENU,PADDING_MENU,0,PADDING_MENU);
    public static final Insets INSETS_MENU_NO_RIGHT = new Insets(PADDING_MENU,PADDING_MENU,PADDING_MENU,0);
    public static final Insets INSETS_MENU_TOP = new Insets(PADDING_MENU,0,0,0);
    public static final Insets INSETS_MENU_LEFT = new Insets(0,PADDING_MENU,0,0);
    public static final Insets INSETS_MENU_RIGHT = new Insets(0,0,0,PADDING_MENU);
    public static final Insets INSETS_MENU_TOP_LEFT = new Insets(PADDING_MENU,PADDING_MENU,0,0);
    public static final Insets INSETS_MENU_LEFT_BOTTOM = new Insets(0,PADDING_MENU,PADDING_MENU,0);
    public static final Insets INSETS_MENU_LEFT_RIGHT = new Insets(0,PADDING_MENU,0,PADDING_MENU);
    public static final Insets INSETS_MENU_ALL = new Insets(PADDING_MENU,PADDING_MENU,PADDING_MENU,PADDING_MENU);
    public static final Insets INSETS_NONE = new Insets(0,0,0,0);
    
    public static final String SETTING_SERVER_ADDRESS = "server address";
    
    private Settings() {
        /*keyMappings.put(KeyEvent.VK_W,CharacterState.JUMPING);
        keyMappings.put(KeyEvent.VK_D,CharacterState.MOVING_RIGHT);
        keyMappings.put(KeyEvent.VK_A,CharacterState.MOVING_LEFT);
        keyMappings.put(KeyEvent.VK_S,CharacterState.CROUCHING);
        keyMappings.put(KeyEvent.VK_P,CharacterState.PUNCH);
        keyMappings.put(KeyEvent.VK_K,CharacterState.HIGH_KICK);
        keyMappings.put(KeyEvent.VK_L,CharacterState.LOW_KICK);
        keyMappings.put(KeyEvent.VK_B,CharacterState.BLOCKING);*/
        loadSettings();
    }

    @Override
    public void commandReceived(ServerCommand serverCommand, DataInputStream dataInputStream) {
        switch(serverCommand) {
            case USER_AUTH_PASS:
                setKeyMappings(dataInputStream);
                break;
        }
    }
    
    public class ActionMapping {
        
        public final int stateCode;
        public final ArrayList<Integer> keyCodes;
        private String keyCodesCSV;
        
        public ActionMapping(int stateCode, ArrayList<Integer> keyCodes) {
            this.stateCode = stateCode;
            this.keyCodes = keyCodes;
        }
        
        public String getKeyCodesCSV() {
            if(keyCodesCSV == null) {
                StringBuilder sb = new StringBuilder();
                for(int i = 0; i < keyCodes.size(); i++) {
                    if(i != 0) {
                        sb.append(",");
                    }
                    sb.append(keyCodes.get(i));
                }
                keyCodesCSV = sb.toString();
            }
            return keyCodesCSV;
        }
    }
    
    private void loadSettings() {
        if(settingsHashMap == null ) {
            settingsHashMap = new HashMap<>();
            File settingsFile = new File(settingsFileName);

            if(settingsFile.exists()) {
                try(
                        FileReader fr = new FileReader(settingsFile);
                        BufferedReader br = new BufferedReader(fr);
                ) {
                    String line;
                    int keyIndex = 0;
                    int valueIndex = 1;
                    int limit = 2;
                    while((line = br.readLine()) != null) {
                        String[] setting = line.split(":",limit);
                        settingsHashMap.put(setting[keyIndex], setting[valueIndex]);
                        System.out.println("loaded setting from file: " + setting[keyIndex]);
                        System.out.println("value: " + setting[valueIndex]);
                    }
                } catch(IOException e) {
                    MessageDialog.showErrorMessage("Unable to read from settings file.", getClass());
                }
            }
        }
    }
    
    public void saveSetting(String key, String value) {
        settingsHashMap.put(key,value);
        saveSettings();
    }
    
    private void saveSettings() {
        File settingsFile = new File(settingsFileName);
        try(FileWriter fw = new FileWriter(settingsFile)) {
            for(String key : settingsHashMap.keySet()) {
                fw.write(key + ":" + settingsHashMap.get(key));
            }
        } catch (IOException ex) {
            MessageDialog.showErrorMessage("Unable to write to settings file.", getClass());
        }
    }
    
    public String getSetting(String key) {
        return settingsHashMap.get(key);
    }
    
    public static Settings getSettings() {
        if(settings == null) {
            settings = new Settings();
        }
        return settings;
    }
    
    public Dimension getScreenDimension() {
        return screenDimension;
    }
    
    public void setScreenDimension(Dimension screenDimension) {
        this.screenDimension = screenDimension;
    }
    
    public Rectangle getScreenBounds() {
        return new Rectangle(0,0,screenDimension.width,screenDimension.height);
    }
    
    public void setKeyMappings(HashMap<Integer, Integer> keyMappings) {
        this.keyMappings = keyMappings;
    }
    
    public HashMap<Integer, Integer> getKeyMappings() {
        return keyMappings;
    }
    
    public ActionMapping[] getActionMappings() {
        return getActionMappings(keyMappings);
    }
    
    public static ActionMapping[] getActionMappings(HashMap<Integer, Integer> keyMappings) {
        HashMap<Integer, ActionMapping> actionMappings = new HashMap<>();
        System.out.println("key mappings size in getActionMappings: " + keyMappings.size());
        for(Map.Entry<Integer, Integer> entry : keyMappings.entrySet()) {
            int stateCode = entry.getValue();
            ActionMapping actionMapping = actionMappings.get(stateCode);
            if(actionMapping == null) {
                actionMapping = Settings.getSettings().new ActionMapping(
                        stateCode,
                        new ArrayList<>()
                );
                actionMappings.put(stateCode, actionMapping);
            }
            actionMapping.keyCodes.add(entry.getKey());
        }
        int size = actionMappings.size();
        return actionMappings.values().toArray(new ActionMapping[size]);
    }
    
    public void setKeyMappings(DataInputStream dataInputStream) {
        try {
            for(int i = 0; i < 8; i++) {
                int stateCode = dataInputStream.readByte();
                if(stateCode < 0) {
                    stateCode ^= 0xffffff00;
                }
                System.out.println("state code " + stateCode);
                byte b;
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                while((b = dataInputStream.readByte()) != 0) {
                    buffer.write(b);
                }
                String[] keyCodes = new String(buffer.toByteArray()).split(",");
                System.out.println("received state code " + stateCode);
                for(String keyCode : keyCodes) {
                    System.out.println("got key code " + keyCode);
                    keyMappings.put(Integer.parseInt(keyCode),stateCode);
                }
            }
        } catch(IOException e) {
            MessageDialog.showErrorMessage("Failed to receive key mappings from server.", getClass());
        }
    }
}
