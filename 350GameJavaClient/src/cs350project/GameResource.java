/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
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
public class GameResource {
    private final String fileName;
    private final boolean loop;
    private final boolean once;
    private final Image[] frames;
    
    public GameResource(String fileName, Type type, int width, int height) throws IOException {
        this.once = type == Type.PLAYS_ONCE;
        this.loop = type == Type.LOOPS;
        this.fileName = fileName;
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
                    frames[i] = getScaledImage(reader.read(i),width,height);
                }
            } else {
                ImageIcon characterImageIcon = new ImageIcon(url);
                frames = new Image[1];
                frames[0] = getScaledImage(characterImageIcon.getImage(),width,height);
            }
        } else {
            throw new FileNotFoundException(fileName);
        }
    }
    
    private Image getScaledImage(Image srcImg, int width, int height){
        
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        int scaledImageX = 0;
        
        g2.drawImage(srcImg, scaledImageX, 0, width, height, null);
        g2.dispose();

        return resizedImg;
    }

    public enum Type {
        STILL,
        PLAYS_ONCE,
        LOOPS
    }
    
    public Image[] getFrames() {
        return frames;
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
