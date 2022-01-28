/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import game.Game;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Leonardo Muntaner
 */
public class ScraggleUi 
{
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem exit;
    private JMenuItem newGame;
    
    // Scraggle board
    private JPanel scragglePanel;
    private JButton[][] diceButtons;

    // Enter found words
    private JPanel wordsPanel;
    private JScrollPane scrollPane;
    private JTextPane wordsArea;
    
    // time label 
    private JLabel timeLabel;
    private JButton shakeDice;

    // Enter current word
    private JPanel currentPanel;
    private JLabel currentLabel;
    private JButton currentSubmit;
        
    // player's score
    private JLabel scoreLabel;
    private int score;
    
    //Timer
    private Timer timer;
    private int minutes = 3;
    private int seconds = 0;
    
    private Game game;
    private ArrayList<String> foundWords = new ArrayList<String>();
    
    private final int GRID = 4;
    
    
    
    //Data type ResetGameListener, event handler for resetting the board for a new game
    private ResetGameListener resetGameListener;
            
    public ScraggleUi(Game inGame)
    {
        game = inGame;
        //Instantiate member variable resetGameListener before calling method initComponents()
        resetGameListener = new ResetGameListener();
        
        initComponents();
    }
    
    //Timer object by doing the following. Instantiate timer; pass the following arguments to the constructor
    private void initComponents()
    {
        timer = new Timer(1000,new TimerListener());
        timer.start();


     // Initialize the JFrame
        frame = new JFrame("Scraggle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(660, 500);
        
        // Initialize the JMenuBar and add to the JFrame
        createMenu();   

        // Initialize the JPane for the current word
        setupCurrentPanel();
        
        // Initialize the JPanel for the word entry
        setupWordPanel();
        
        // Initialize the JPanel for the Scraggle dice
        setupScragglePanel();        
        
        // Add everything to the JFrame
        frame.setJMenuBar(menuBar);
        frame.add(scragglePanel, BorderLayout.WEST);
        frame.add(wordsPanel, BorderLayout.CENTER);
        frame.add(currentPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    private void createMenu()
    {
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Scraggle");
        gameMenu.setMnemonic('S');
        
        newGame = new JMenuItem("New Game");
        newGame.setMnemonic('N');
        newGame.addActionListener(resetGameListener);

        exit = new JMenuItem("Exit");
        exit.setMnemonic('E');
        exit.addActionListener(new ExitListener());
        
        gameMenu.add(newGame);    
        gameMenu.add(exit);    
        
        menuBar.add(gameMenu);
    }

    private void setupCurrentPanel()
    {
        currentPanel = new JPanel();
        currentPanel.setBorder(BorderFactory.createTitledBorder("Current Word"));

        currentLabel = new JLabel();
        currentLabel.setBorder(BorderFactory.createTitledBorder("Current Word"));
        currentLabel.setMinimumSize(new Dimension(300, 50));
        currentLabel.setPreferredSize(new Dimension(300,50));
        currentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        currentSubmit = new JButton("Submit Word");
        currentSubmit.setMinimumSize(new Dimension(200, 100));
        currentSubmit.setPreferredSize(new Dimension(200, 50));
        currentSubmit.addActionListener(new SubmitListener());
        
        scoreLabel = new JLabel();
        scoreLabel.setBorder(BorderFactory.createTitledBorder("Score"));
        scoreLabel.setMinimumSize(new Dimension(100, 50));
        scoreLabel.setPreferredSize(new Dimension(100,50));
        
        currentPanel.add(currentLabel);
        currentPanel.add(currentSubmit);
        currentPanel.add(scoreLabel);
    }

    private void setupWordPanel()
    {
        wordsPanel = new JPanel();
        wordsPanel.setLayout(new BoxLayout(wordsPanel, BoxLayout.Y_AXIS));
        wordsPanel.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));
        
        wordsArea = new JTextPane();
        scrollPane = new JScrollPane(wordsArea);
        scrollPane.setPreferredSize(new Dimension(180, 330));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        timeLabel = new JLabel("3:00");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        timeLabel.setPreferredSize(new Dimension(240, 100));
        timeLabel.setMinimumSize(new Dimension(240, 100));
        timeLabel.setMaximumSize(new Dimension(240, 100));
        timeLabel.setBorder(BorderFactory.createTitledBorder("Time Left"));
        
        shakeDice = new JButton("Shake Dice");
        shakeDice.setPreferredSize(new Dimension(240, 100));
        shakeDice.setMinimumSize(new Dimension(240, 100));
        shakeDice.setMaximumSize(new Dimension(240, 100));
        shakeDice.addActionListener(new ResetGameListener());
        
        wordsPanel.add(scrollPane);
        wordsPanel.add(timeLabel);
        wordsPanel.add(shakeDice);
    }

    private void setupScragglePanel()
    {   
        scragglePanel = new JPanel();
        scragglePanel.setLayout(new GridLayout(4, 4));
        scragglePanel.setMinimumSize(new Dimension(400, 400));
        scragglePanel.setPreferredSize(new Dimension(400, 400));
        scragglePanel.setBorder(BorderFactory.createTitledBorder("Scraggle Board"));
        
        diceButtons = new JButton[GRID][GRID];
        
        for(int row = 0; row < GRID; row++)
            
            for(int col = 0; col < GRID; col++)
        {
            //get correct image for the letters
            URL imgURL = getClass().getResource(game.getGrid()[row][col].getImgPath());
            ImageIcon icon = new ImageIcon(imgURL);
            //icon = imageResize(icon);
            diceButtons[row][col] = new JButton(icon);
            
            
            //add client properties
            diceButtons[row][col].putClientProperty("row", row);
            diceButtons[row][col].putClientProperty("col", col);
            diceButtons[row][col].putClientProperty("letter", game.getGrid()[row][col].getLetter());
            
            scragglePanel.add(diceButtons[row][col]);
           
            //instantiate the event handlers
            TileListener tileListener = new TileListener();
            LetterListener letterListener = new LetterListener();
            
            
            diceButtons[row][col].addActionListener(letterListener);
            diceButtons[row][col].addActionListener(tileListener);
        }
    }
    
    
    
    private class ExitListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
           int response = JOptionPane.showConfirmDialog(frame, "Confirm to exit scraggle?", "Exit?", JOptionPane.YES_NO_OPTION);
           
           if (response == JOptionPane.YES_OPTION)
               System.exit(0);
        }
     
    }
    
    private class ResetGameListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            score = 0;
            game.populateGrid();
            
            frame.remove(scragglePanel);
            
            scragglePanel.removeAll();
            setupScragglePanel();
            scragglePanel.revalidate();
            scragglePanel.repaint();
            frame.add(scragglePanel, BorderLayout.WEST);
            
            timeLabel.setText("3:00"); //time reset
            
            //clear the foundWords ArrayList
            foundWords.removeAll(foundWords);
            
            //stop timer
            timer.stop();
            
            //reset time
            minutes = 3;
            seconds = 0;
            
            //restart timer
            timer.start();
        }
    
    }
    
    private class SubmitListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            //compare words to the ones in the dictionary
            int wordScore = game.getDictionary().search(currentLabel.getText().toLowerCase());
            
            //check if the word is already used 
            if(foundWords.contains(currentLabel.getText()))
            {
                JOptionPane.showMessageDialog(frame, "Word found already");
            }
            else
            {
                if(wordScore == 0)
                {
                    JOptionPane.showMessageDialog(frame, "Word not found");
                }
                else if(wordScore > 0)
                {
                    wordsArea.setText(wordsArea.getText() + "\n" + currentLabel.getText());
                    wordsArea.setCaretPosition(wordsArea.getDocument().getLength());
                    foundWords.add(currentLabel.getText().toLowerCase());
                    score += wordScore;
                    scoreLabel.setText(String.valueOf(score));
                }
            }
            
            currentLabel.setText("");
            
            for(int row = 0; row < GRID; row++)
            {
                for(int col = 0; col < GRID; col++)
                {
                    diceButtons[row][col].setEnabled(true);
                }
            }
            
        }
    
    }
    
    private class LetterListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(e.getSource() instanceof JButton)
            {
                JButton button = (JButton)e.getSource();
                String letter = (String)button.getClientProperty("letter");
                currentLabel.setText(currentLabel.getText() + letter);
            }
        }
        
    }
    
    private class TileListener implements ActionListener
    {
        
        @Override
        public void actionPerformed(ActionEvent e) 
        {
           if(e.getSource() instanceof JButton)
           {
               JButton button = (JButton)e.getSource();
               
               int row = (int)button.getClientProperty("row");
               int col = (int)button.getClientProperty("col");
               
               //disable all the JButtons
               for(int i = 0; i < 4; i++)
               {
                   for(int j = 0; j < 4; j++)
                   {
                       diceButtons[i][j].setEnabled(false);
                   }
               }
               
               //enable JButtons
               if(row == 0 && col == 0)
               {
                   //enable surrounding position 0/0
                   diceButtons[row + 1][col].setEnabled(true);
                   diceButtons[row + 1][col + 1].setEnabled(true);
                   diceButtons[row][col + 1].setEnabled(true);
               }
               
               //this logic works for row 1,2 and col 1,2
               else if(row > 0 && col > 0)
               {
                   diceButtons[row - 1][col - 1].setEnabled(true);
                   diceButtons[row - 1][col].setEnabled(true);
                   diceButtons[row - 1][col + 1].setEnabled(true);
                   
                   diceButtons[row][col - 1].setEnabled(true);
                   diceButtons[row][col + 1].setEnabled(true);
                   
                   diceButtons[row + 1][col - 1].setEnabled(true);
                   diceButtons[row + 1][col].setEnabled(true);
                   diceButtons[row + 1][col + 1].setEnabled(true);
               }
               if(row == 3 && col == 3)
               {
                   //enable surrounding
                   diceButtons[row - 1][col].setEnabled(true);
                   diceButtons[row - 1][col - 1].setEnabled(true);
                   diceButtons[row][col - 1].setEnabled(true);
               }
               else if(row == 3 && col > 0)
               {
                   diceButtons[row][col + 1].setEnabled(true);
                   diceButtons[row - 1][col + 1].setEnabled(true);
                   diceButtons[row - 1][col].setEnabled(true);
                   diceButtons[row - 1][col - 1].setEnabled(true);
                   diceButtons[row][col - 1].setEnabled(true);
               }
               else if(row > 0 && col == 3)
               {
                   diceButtons[row - 1][col].setEnabled(true);
                   diceButtons[row + 1][col].setEnabled(true);
                   diceButtons[row + 1][col - 1].setEnabled(true);
                   diceButtons[row][col - 1].setEnabled(true);
                   diceButtons[row - 1][col - 1].setEnabled(true);
               }
               else if(row > 0 && col == 0)
               {
                   diceButtons[row - 1][col + 1].setEnabled(true);
                   diceButtons[row][col + 1].setEnabled(true);
                   diceButtons[row + 1][col + 1].setEnabled(true);
                   diceButtons[row + 1][col].setEnabled(true);
                   diceButtons[row - 1][col].setEnabled(true);
               }
               else if(row == 0 && col > 0)
               {
                   diceButtons[row][col + 1].setEnabled(true);
                   diceButtons[row + 1][col + 1].setEnabled(true);
                   diceButtons[row][col].setEnabled(true);
                   diceButtons[row + 1][col - 1].setEnabled(true);
                   diceButtons[row][col - 1].setEnabled(true);
               }
               else if(row == 3 && col == 0)
               {
                   diceButtons[row - 1][col + 1].setEnabled(true);
                   diceButtons[row - 1][col].setEnabled(true);
                   diceButtons[row][col + 1].setEnabled(true);
               }
           }
        
        }
        
    }
    
    private class TimerListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(seconds == 0 && minutes == 0)
            {
                timer.stop();
                JOptionPane.showMessageDialog(frame, "Time is up! GameOver");
            }
            else
            {
                if(seconds == 0)
                {
                    seconds = 59;
                    minutes--;
                }
                else
                {
                    seconds--;
                }
            }
            if(seconds < 10)
            {
                String strSeconds = "0" + String.valueOf(seconds);
                timeLabel.setText(String.valueOf(minutes) + ":" + strSeconds);
            }
            else
            {
                timeLabel.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
            }
        }
        
    }
} 





