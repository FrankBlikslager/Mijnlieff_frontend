package nl.blikslager.Accelgor;

import javax.swing.*;
import java.awt.*;

public class MijnLieffFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	MijnLieffPanel panel;

    MijnLieffFrame() {
    	
        super("Mijnlieff");

        setSize(1000,800);
        setResizable(true);
        setMinimumSize(new Dimension(800,600));
        setLocation(500, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new MijnLieffPanel();
        add(panel);

        setVisible(true);
    }

    public static void main(String args[]) {
        new MijnLieffFrame();
    }

}
