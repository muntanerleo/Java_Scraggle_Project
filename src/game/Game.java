/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import dictionary.Alphabet;
import dictionary.Dictionary;
import model.GridPoint;
import model.GridUnit;
/**
 *
 * @author leonardomuntaner
 */
public class Game 
{
    private final GridUnit[][] grid;
    private final Dictionary dictionary;
    //Declared member variable to store a 4 x 4 array of GridUnit objects
    public Game(Dictionary dictionary)
    {
        this.dictionary = dictionary;
        this.grid = new GridUnit[4][4];
        this.populateGrid();
    }
    
    public GridUnit[][] getGrid()
    {
        return grid;
    }
    //Generate a getter for member variable grid
    public GridUnit getGridUnit(GridPoint point)
    {
        return grid[point.x][point.y];
    }
    //nested for loop to loop through the 4 x 4 array. gets the letters when you run program
    public void populateGrid()
    {
        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                grid[i][j] = new GridUnit(Alphabet.newRandom(), new GridPoint(i,j));
            }
        }
    }
    //nested for loop to loop through the 4 x 4 array. puts the letters in the spaces and looks like a box
    public void displayGrid()
    {
        System.out.println("-------------------------");
        for (int i = 0; i < 4; i++)
        {
            System.out.print("|   ");
            
            for (int j = 0; j < 4; ++j)
            {
                System.out.print(grid[i][j].getLetter());
                System.out.print("  |  ");
            }
            
            System.out.println("\n-------------------------");
        }
    }

    /**
     * @return the dictionary
     */
    public Dictionary getDictionary() {
        //Update class Game to do the following:
        //
        return dictionary;
    }
    
}   



