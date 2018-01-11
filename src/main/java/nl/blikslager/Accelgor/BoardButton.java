package nl.blikslager.Accelgor;

import java.awt.*;
import java.awt.event.ActionEvent;

public class BoardButton extends GameButton {
	
	private static final long serialVersionUID = 1L;
	private MijnLieffPanel panel;
    private Color basicColour;

    public BoardButton(MijnLieffPanel panel) {
    	
        this.panel = panel;
        loadImage("empty_field.png");
        this.addActionListener(this);
        basicColour = this.getBackground();
    }

    @Override
    public void actionPerformed(ActionEvent clickEvent) {
    	
        panel.selectSquare(this);
        loadImage("selected.png");
        setEnabled(false);
    }

    public void deselect() {
    	
        loadImage("empty_field.png");
        setEnabled(true);
    }

    public void resetSquare() {
    	
        loadImage("empty_field.png");
        setBackground(basicColour);
    }
    
}
