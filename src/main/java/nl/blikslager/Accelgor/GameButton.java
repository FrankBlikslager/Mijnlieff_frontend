package nl.blikslager.Accelgor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GameButton extends JButton implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private MijnLieffPanel panel;
    protected ImageIcon buttonIcon;
    private String colour;
    private String piece;

    public GameButton() {
    	
    }

    public GameButton(MijnLieffPanel panel, String piece, String colour) {
        this.panel = panel;
        this.colour = colour;
        this.piece = piece;
        loadImage();
    }

    private void loadImage() {
        String filename = "" + piece + "_" + colour + ".png";
        try {
            Image image = ImageIO.read(getClass().getClassLoader().getResource(filename));
            buttonIcon = new ImageIcon(image);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        setIcon(buttonIcon);
    }

    protected void loadImage(String filename) {
    	
        try {
            Image image = ImageIO.read(getClass().getClassLoader().getResource(filename));
            buttonIcon = new ImageIcon(image);
        }
        catch (IOException e) {
        	
            e.printStackTrace();
        }
        
        setIcon(buttonIcon);
    }

    public void actionPerformed(ActionEvent clickEvent) {
        panel.selectPiece(this);
        this.setEnabled(false);
    }

    public void deselect() {
        setEnabled(true);
    }

    public void setColourButton(String colour) {
        this.colour = colour;
        loadImage();
    }
    
}
