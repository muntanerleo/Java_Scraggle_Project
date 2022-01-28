/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import model.WordResult;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author leonardomuntaner
 */
public class UserResult //Declare member variable, data type int, to store user score (totalScore)
{
    //Declare member variable to store the user words
    private final Map<String, WordResult> wordToResultMap = new LinkedHashMap<>();
    private int totalScore = 0;
    
    //Generate a getter for member variable totalScore
    public void add(String word, WordResult result)
    {
        this.wordToResultMap.put(word, result);
        this.totalScore += result.getScore();
    }
    
    //from here down all the methods return wordToResultMap 
    public WordResult get(String word)
    {
        return this.wordToResultMap.get(word);
    }
    
    public Map<String, WordResult> all()
    {
        return this.wordToResultMap;
    }
    
    public boolean exists(String word)
    {
        return this.wordToResultMap.containsKey(word);
    }
    
    public int getWordCount()
    {
        return this.wordToResultMap.size();
    }

    /**
     * @return the totalScore
     */
    public int getTotalScore() {
        return totalScore;
    }
}
