package tictactoe;

import java.util.ArrayList;

public class JSON {
    private int size;
    private char[][] tictactoe;
    private char[] characters;
    private String[] colors;
    ArrayList<Integer> movesX = new ArrayList<>();
    ArrayList<Integer> movesY = new ArrayList<>();
    
    JSON() {
        // No args for now
    }
    
    JSON(int size,char[][] tictactoe,char[] characters, String[] colors, ArrayList<Integer> movesX, ArrayList<Integer> movesY) {
        this.size = size;
        this.tictactoe = tictactoe;
        this.characters = characters;
        this.colors = colors;
        this.movesX = movesX;
        this.movesY = movesY;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public char[][] getTicTacToe() {
        return this.tictactoe;
    }
    
    public char[] getcharacters() {
        return this.characters;
    }
    
    public String[] getColors() {
        return this.colors;
    }
    
    public ArrayList<Integer> getMovesX() {
        return this.movesX;
    }
    
    public ArrayList<Integer> getMovesY() {
        return this.movesY;
    }    
}
