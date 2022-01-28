/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scraggle;

import dictionary.Dictionary;
import game.Game;
import userInterface.ScraggleUi;
/**
 *
 * @author leonardomuntaner
 */
public class Scraggle 
{

    /**
     * @param args the command line arguments
     */
    //updated main method to do the following
    public static void main(String[] args) 
    {
        Dictionary dictionary = new Dictionary();
        Game game = new Game(dictionary);
        game.displayGrid();
        
        //instatiated an instance of class ScraggleUi and passed refrence object game
        ScraggleUi ui = new ScraggleUi(game);
    }
    
}
