package nl.blikslager.Accelgor;

import nl.blikslager.Weedle.MijnLieffBoard;
import nl.blikslager.Weedle.Pieces;
import nl.blikslager.Weedle.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author 
 *
 */
public class MijnLieffPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private MijnLieffBoard board;
    private Player currentPlayer;
    private Player player_1;

    private String currentColour;
    private String colour_p1;
    private String colour_p2;

    // left column board
    private JLabel turnLabel = new JLabel("Turn player 1");
    private GameButton straightButton;
    private GameButton diagonalButton;
    private GameButton pullerButton;
    private GameButton pusherButton;
    private JButton resetButton;

    //right column board
    private JLabel scoreLabel = new JLabel("0 - 0");
    private BoardButton boardButtons[][] = new BoardButton[4][4];
    private JLabel adviceLabel = new JLabel("Select a piece to play");
    private JButton playButton;

    private GameButton selectedPiece;
    private BoardButton selectedSquare;

    protected MijnLieffPanel() {
    	
        String[] colours = {"Red", "Green", "Blue", "Yellow"};

        colour_p1 = (String) JOptionPane.showInputDialog(null,"Player 1, choose your colour:",
                "Colour player 1", JOptionPane.QUESTION_MESSAGE, null, colours, colours[0]);
        colour_p2 = (String) JOptionPane.showInputDialog(null,"Player 2, choose your colour:",
                "Colour player 2", JOptionPane.QUESTION_MESSAGE, null, colours, colours[1]);

        board = MijnLieffBoard.getNewBoard();
        currentPlayer = board.getCurrentPlayer();
        player_1 = currentPlayer;

        straightButton = new GameButton(this, "straight", colour_p1);
        straightButton.addActionListener((event) -> {
        	
            selectPiece((GameButton) event.getSource());
            ((GameButton) event.getSource()).setEnabled(false);
        });
        
        diagonalButton = new GameButton(this, "diagonal", colour_p1);
        diagonalButton.addActionListener((event) -> {
        	
            selectPiece((GameButton) event.getSource());
            ((GameButton) event.getSource()).setEnabled(false);
        });
        
        pullerButton = new GameButton(this, "puller", colour_p1);
        pullerButton.addActionListener((event) -> {
        	
            selectPiece((GameButton) event.getSource());
            ((GameButton) event.getSource()).setEnabled(false);
        });
        
        pusherButton = new GameButton(this, "pusher", colour_p1);
        pusherButton.addActionListener((event) -> {
        	
            selectPiece((GameButton) event.getSource());
            ((GameButton) event.getSource()).setEnabled(false);
        });

        resetButton = new JButton("Reset game");
        resetButton.addActionListener(event -> {
        	
            board = MijnLieffBoard.getNewBoard();
            currentPlayer = board.getCurrentPlayer();
            player_1 = currentPlayer;
            selectedPiece = null;
            selectedSquare = null;
            updatePanel();
            resetBoard();
        });

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        Font font = new Font("Sherif", Font.PLAIN, 16);

        constraints.insets = new Insets(0,0,20,100);
        constraints.gridx = 0;
        constraints.gridy = 0;

        turnLabel.setFont(font);
        add(turnLabel,constraints);

        constraints.insets = new Insets(0,0,0,100);
        constraints.gridy = 1;
        add(straightButton,constraints);
        constraints.gridy = 2;
        add(diagonalButton,constraints);
        constraints.gridy = 3;
        add(pullerButton,constraints);
        constraints.gridy = 4;
        add(pusherButton,constraints);

        constraints.insets = new Insets(20,0,0,100);
        constraints.gridy = 5;
        resetButton.setFont(font);
        add(resetButton, constraints);

        constraints.insets = new Insets(0,0,0,0);
        for(int iRow = 1; iRow<5; iRow++) {
            constraints.gridy = iRow;
            for(int iCol = 1; iCol<5; iCol++) {
                constraints.gridx = iCol;
                boardButtons[iRow-1][iCol-1] = new BoardButton(this);
                boardButtons[iRow-1][iCol-1].setEnabled(board.playable(iRow,iCol));
                add(boardButtons[iRow-1][iCol-1], constraints);
            }
        }

        constraints.gridx = 2;
        constraints.gridwidth = 2;
        constraints.gridy = 0;
        constraints.insets = new Insets(0,0,20,0);
        scoreLabel.setFont(font);
        add(scoreLabel,constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.insets = new Insets(20,0,0,0);
        adviceLabel.setFont(font);
        add(adviceLabel,constraints);

        constraints.gridx = 4;
        constraints.gridwidth = 1;
        add(playButton = new JButton("Play piece"), constraints);
        playButton.setFont(font);
        playButton.setEnabled(false);
        playButton.addActionListener((event) -> {
            playPiece();
            playButton.setEnabled(false);
        });
    }

    public void selectPiece(GameButton pieceButton) {
        if(pieceIsSelected()) {
            selectedPiece.deselect();
        }

        selectedPiece = pieceButton;
        Pieces piece = identifyPiece();
        currentPlayer.selectPiece(piece);
        enablePlayButton();
        updateAdviceLabel();
    }

    private boolean pieceIsSelected() {
        return selectedPiece != null;
    }

    private Pieces identifyPiece() {
    	
        if(selectedPiece == straightButton)
            return Pieces.STRAIGHT;
        else if (selectedPiece == diagonalButton)
            return Pieces.DIAGONAL;
        else if (selectedPiece == pullerButton)
            return Pieces.PULLER;
        else
            return Pieces.PUSHER;
    }

    private void enablePlayButton() {
        if(pieceIsSelected() && squareIsSelected()) {
            playButton.setEnabled(true);
        }
    }

    private boolean squareIsSelected() {

        return selectedSquare != null;
    }

    private void updateAdviceLabel() {
        if(squareIsSelected() && pieceIsSelected()) {
            adviceLabel.setText("Click the 'Play button'" );
        }
        else if (pieceIsSelected()) {
            adviceLabel.setText("Select a square to play");
        } else {
            adviceLabel.setText("Select a piece to play");
        }
    }

    private void updatePanel() {
        updateScore();
        updatePlayer();
        updatePieces();
        updateBoard();
        updateAdviceLabel();
    }

    private void updateScore() {
        int score[] = board.getScore();
        scoreLabel.setText(score[0] + " - " + score[1]);
    }

    private void updatePlayer() {
        currentPlayer = board.getCurrentPlayer();
        
        if(currentPlayer == player_1) {
            turnLabel.setText("Turn player 1");
            currentColour = colour_p1;
        } else {
            turnLabel.setText("Turn player 2");
            currentColour = colour_p2;
        }
    }

    private void updatePieces() {
        List<Pieces> pieces = currentPlayer.getPieces();
        checkGameHasEnded(pieces);
        
        if(pieces.contains(Pieces.STRAIGHT)) {
            straightButton.setEnabled(true);
        } else {
            straightButton.setEnabled(false);
        }
        straightButton.setColourButton(currentColour);
        
        if(pieces.contains(Pieces.DIAGONAL)) {
            diagonalButton.setEnabled(true);
        } else {
            diagonalButton.setEnabled(false);
        }
        diagonalButton.setColourButton(currentColour);
        
        if (pieces.contains(Pieces.PULLER)) {
            pullerButton.setEnabled(true);
            
        } else {
        	pullerButton.setEnabled(false);
        }
        pullerButton.setColourButton(currentColour);
        
        if(pieces.contains(Pieces.PUSHER)) {
            pusherButton.setEnabled(true);
        } else {
        	pusherButton.setEnabled(false);
        }
        pusherButton.setColourButton(currentColour);
    }

    private void checkGameHasEnded(List<Pieces> pieces) {
        if (pieces.size() == 0) {
            int[] score = board.getScore();
            String message;

            if (score[0]>score[1]) {
                message = "Player 1 wins!";
            } else if (score[0]<score[1]) {
                message = "Player 2 wins";
            } else {
            	message = "It is a draw";
            }
            
            JOptionPane.showMessageDialog(null, message, "Game result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateBoard() {
        for (int iRow = 0; iRow<4; iRow++)
            for (int iCol = 0; iCol < 4; iCol++)
                boardButtons[iRow][iCol].setEnabled(board.playable(iRow+1,iCol+1));
    }

    private void resetBoard() {
        for (int iRow = 0; iRow<4; iRow++)
            for (int iCol = 0; iCol < 4; iCol++)
                boardButtons[iRow][iCol].resetSquare();
    }

    private void playPiece() {
        currentPlayer.playPiece();
        Color colour;

        if (currentPlayer == player_1) {

            colour = setColour(colour_p1);
        } else {
            colour = setColour(colour_p2);
        }

        selectedSquare.setBackground(colour);
        selectedSquare.setIcon(selectedPiece.getIcon());
        selectedPiece = null;
        selectedSquare = null;
        updatePanel();
    }

    private Color setColour(String colour) {
        if(colour.equals("Red")) {
            return new Color(255,0,0);
        } else if(colour.equals("Blue")) {
            return new Color(0,0,255);
        } else if(colour.equals("Yellow")) {
            return new Color(255,255,0);
        } else {
            return new Color(0,255,0);
        }
    }

    public void selectSquare(BoardButton squareButton) {
        if(squareIsSelected()) {
            selectedSquare.deselect();
        }

        selectedSquare = squareButton;
        int square[] = identifySquare();
        currentPlayer.selectSquare(square[0],square[1]);
        enablePlayButton();
        updateAdviceLabel();
    }

    private int[] identifySquare() {
        int square[] = new int[2];
        for (int iRow = 0; iRow<4; iRow++) {
            for (int iCol = 0; iCol<4; iCol++) {
                if(selectedSquare == boardButtons[iRow][iCol]) {
                    square[0] = iRow+1;
                    square[1] = iCol+1;
                    return square;
                }
            }
        }
        return null;
    }
}
