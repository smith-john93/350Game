/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project.characters;

import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

/**
 *
 * @author Mark Masone
 */
public class CharacterResource {
    
    private final String fileName;
    private final boolean loop;
    private final boolean once;
    private Image[] frames;
    private int[] stateCodes;
    
    public CharacterResource(String fileName, Type type, int[] stateCodes) {
        this.once = type == Type.PLAYS_ONCE;
        this.loop = type == Type.LOOPS;
        this.fileName = fileName;
        this.stateCodes = stateCodes;
    }

    public CharacterResource(String fileName, Type type, int stateCode) {
        this(fileName,type,new int[]{stateCode});
    }

    public int[] getStateCodes() {
        return stateCodes;
    }

    public void loadResource() throws IOException {
        URL url = getClass().getResource("/resources/" + fileName);
        if(url != null) {
            String contentType = url.openConnection().getContentType();
            if (contentType.equals("image/gif")) {
                ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
                ImageInputStream stream = ImageIO.createImageInputStream(url.openStream());
                reader.setInput(stream);
                int frameCount = reader.getNumImages(true);
                frames = new Image[frameCount];
                for (int i = 0; i < frameCount; i++) {
                    frames[i] = reader.read(i);
                }
            } else {
                ImageIcon characterImageIcon = new ImageIcon(url);
                frames = new Image[1];
                frames[0] = characterImageIcon.getImage();
            }
        } else {
            throw new FileNotFoundException(fileName);
        }
    }
    
    public enum Type {
        STILL,
        PLAYS_ONCE,
        LOOPS
    }
    
    public Image[] getFrames() {
        return frames;
    }
    
    public int getFramesCount() {
        if(frames == null) {
            return 0;
        }
        return frames.length;
    }
    
    public boolean loops() {
        return loop;
    }
    
    public boolean playsOnce() {
        return once;
    }
    
    public String getFileName() {
        return fileName;
    }
}
