/**
 * Copyright (C) 2018, Manjot Sidhu <manjot.techie@gmail.com>
 */
package tictactoe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import static tictactoe.Colors.*;

/**
 * TicTacToe Object is the main and the only component in the functionality of
 * this TicTacToe game
 *
 * @version 0.1
 * @author ManjotSidhu
 */
public class TicTacToe {

    /**
     * empty
     */
    public TicTacToe() {
        // empty
    }

    // Parameters
    
    // board predefined
    /* private int[][] board = {{1,2,1,1,1,2},
                                {7,1,9,1,1,2},
                                {1,8,9,1,1,2},
                                {7,8,9,1,1,2},
                                {7,8,9,1,1,2},
                                {7,8,9,1,1,2}}; */
    private char[][] board;
    private int boardSize;

    private char[] playerChar;
    private String[] playerColor;

    private char turnChar;
    private String turnColor;
    private int head;
    private boolean doNextMove = true;
    private final int pattern = 3;
    private boolean restart = true;
    private boolean importJson = true;
    private boolean exportJson = true;

    ArrayList<Integer> input1 = new ArrayList<>();
    ArrayList<Integer> input2 = new ArrayList<>();

    // Scanner
    Scanner scan = new Scanner(System.in);

    /**
     * Get player's input to import/export JSON files
     */
    public void jsonInit() {
        System.out.print(GREEN + "Do you want to load game params from Json:[Y/n] " + RESET);
        char in = scan.next(".").charAt(0);
        switch (in) {
            case 'Y':
                this.importJson = true;
                break;
            case 'n':
                this.importJson = false;
                break;
            default:
                System.out.println(RED + "Enter correct response" + RESET);
                jsonInit();
                break;
        }

        System.out.print(GREEN + "Do you want to export game params to Json:[Y/n] " + RESET);
        char out = scan.next(".").charAt(0);
        if (out == 'Y') {
            this.exportJson = true;
        } else if (out == 'n') {
            this.exportJson = false;
        } else {
            System.out.println(RED + "Enter correct response dumb" + RESET);
            jsonInit();
        }
    }

    /**
     * Reads the <b>input.json</b> file, parses it to a String and sets variables
     * to their superclass variables
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void jsonRead() throws FileNotFoundException, IOException {
        String str = FileUtils.readFileToString(new File("input.json"));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JSON size = gson.fromJson(str, JSON.class);
        this.boardSize = size.getSize();
        //this.board = size.getTicTacToe();
        this.playerChar = size.getcharacters();
        this.playerColor = size.getColors();
        this.input1 = size.getMovesX();
        this.input2 = size.getMovesY();
    }

    /**
     * Inputs size/order of board array and initialize the array
     */
    public void setSize() {
        System.out.print(GREEN + "Enter Size/Order: " + RESET);

        this.boardSize = scan.nextInt();
        // boardSize predefined
        // this.boardSize = 6;

        board = new char[boardSize][boardSize];
    }

    /**
     * Inputs number of players playing this game
     */
    public void setUsers() {
        System.out.print(GREEN + "Number of players: " + RESET);
        int players = scan.nextInt();
        playerChar = new char[players];
        playerColor = new String[players];

        this.head = 0;
    }

    /**
     * Inputs player-wise characters and colors to choose from
     */
    public void setUserProp() {
        for (int i = 0; i < playerChar.length; i++) {
            int n = i + 1;
            System.out.print(GREEN + "Player " + n + ", enter your character: " + RESET);
            this.playerChar[i] = scan.next(".").charAt(0);

            System.out.print(GREEN + "Player " + n + ", enter your color: " + RESET);
            this.playerColor[i] = scan.next();
        }
    }

    /**
     * Sets the player's color to the ANSI color variable referred in Colors
     * class
     */
    public void playerColors() {
        for (int i = 0; i < playerColor.length; i++) {
            switch (playerColor[i].toUpperCase()) {
                case "BLACK":
                    playerColor[i] = BLACK;
                    break;
                case "RED":
                    playerColor[i] = RED;
                    break;
                case "GREEN":
                    playerColor[i] = GREEN;
                    break;
                case "YELLOW":
                    playerColor[i] = YELLOW;
                    break;
                case "BLUE":
                    playerColor[i] = BLUE;
                    break;
                case "PURPLE":
                    playerColor[i] = PURPLE;
                    break;
                case "CYAN":
                    playerColor[i] = CYAN;
                    break;
                case "WHITE":
                    playerColor[i] = WHITE;
                    break;
            }
        }
    }

    /**
     * Runs all the methods used to fetch all the needed params
     *
     * @throws IOException
     */
    // Init in-game parameters
    public void init() throws IOException {
        jsonInit();
        if (importJson) {
            jsonRead();
            this.board = new char[boardSize][boardSize];
        } else {
            setSize();
            setUsers();
            setUserProp();
            playerColors();
        }

        // Game starts with first player
        this.turnChar = playerChar[0];
        this.turnColor = playerColor[0];
    }

    /**
     * Validates the <b>boardSize</b> param inputted by the player
     */
    public void validateStructure() {
        if (this.boardSize < 3 || this.boardSize > 9) {
            System.out.println(RED + "TicTacToe size cannot be less than 3 and more than 9" + RESET);
            System.out.println(RED + "Please try again" + RESET);
            setSize();
        }
    }

    /**
     * Renders/Creates the main game <b>board</b>
     */
    public void renderStructure() {
        System.out.print(new String(new char[this.boardSize]).replace("\0", GREEN_BG + BLACK + "+---"));
        System.out.println("+" + RESET);

        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if (board[i][j] == 0) {
                    System.out.print(GREEN_BG + BLACK + "| " + "  ");
                } else {
                    System.out.print(GREEN_BG + BLACK + "| " + board[i][j] + " ");
                }
            }
            System.out.println("|" + RESET);
        }

        System.out.print(new String(new char[this.boardSize]).replace("\0", GREEN_BG + BLACK + "+---"));
        System.out.println("+" + RESET);

    }

    /**
     * Validates the index params inputted from the player
     *
     * @param i Integer index at X<sup>th</sup> position
     * @param j Integer index at Y<sup>th</sup> position
     */
    public void validateInput(int i, int j) {
        if (i > boardSize - 1 || j > boardSize - 1) {
            System.out.println(RED + "Please input valid index number" + RESET);
            this.restart = false;
        } // foreground fix :( to not start indexes from 0  
        /*else if(i == 0 && j == 0) {
            System.out.println(RED + "Invalid index, indexes starts from 1(inclusive)" + RESET);
            this.restart = false;
        }*/ else if (board[i][j] != 0) {
            // foreground fix :( to not start indexes from 0
            int fix1 = i + 1;
            int fix2 = i + 1;
            System.out.println(RED + fix1 + " " + fix2 + " index already has " + board[i][j] + " value." + RESET);
            this.restart = false;
        } else {
            this.restart = true;
        }
    }

    /**
     * Simply prints the <i> make your move </i> to System.out console
     */
    public void askInput() {
        System.out.printf("%sPlayer %s, make your move: %s \n", turnColor, this.turnChar, RESET);
    }

    /**
     * Sets values inputted from the player to the main board
     *
     * @param i Integer index at i<sup>th</sup>row
     * @param j Integer index at j<sup>th</sup>column
     */
    public void setValue(int i, int j) {
        board[i][j] = this.turnChar;
    }

    /**
     * Updates <b>current</b> character and color to next player's turn
     * accordingly
     */
    public void nextTurn() {

        if (head == playerChar.length - 1) {
            head = 0;
        } else {
            head++;
        }
        this.turnChar = playerChar[head];
        this.turnColor = playerColor[head];
    }

    /**
     * Main game algorithm used to validate the goal pattern of the Player
     *
     * @param i1 Integer index at X<sup>th</sup> position
     * @param i2 Integer index at Y<sup>th</sup> position
     */
    public void logic(int i1, int i2) {
        // my bad :(
        int result1 = 0, result2 = 0, result3 = 0, result4 = 0, result5 = 0,
                result6 = 0;

        for (int i = 0; i < this.boardSize; i++) {
            if (board[i][i2] == this.turnChar) {
                result1++;
                if (result1 == this.pattern) {
                    this.doNextMove = false;
                    result();
                    break;
                }
            } else {
                result1 = 0;
            }

            if (board[i1][i] == this.turnChar) {
                result2++;
                if (result2 == this.pattern) {
                    this.doNextMove = false;
                    result();
                    break;
                }
            } else {
                result2 = 0;
            }

            if (board[i][i] == this.turnChar) {
                result3++;
                if (result3 == this.pattern) {
                    this.doNextMove = false;
                    result();
                    break;
                }
            } else {
                result3 = 0;
            }

            for (int j = 0; j < this.boardSize; j++) {
                if (i + j == this.boardSize - 1) {
                    if (board[i][j] == this.turnChar) {
                        result4++;
                        if (result4 == this.pattern) {
                            this.doNextMove = false;
                            result();
                            break;
                        }
                    } else {
                        result4 = 0;
                    }
                }
            }

            if (!this.doNextMove) {
                break;
            }

            for (int j = 0; j < this.boardSize; j++) {
                if (i + j == i1 + i2) {
                    if (board[i][j] == this.turnChar) {
                        result5++;
                        if (result5 == this.pattern) {
                            this.doNextMove = false;
                            result();
                            break;
                        }
                    } else {
                        result5 = 0;
                    }
                }
            }

            if (!this.doNextMove) {
                break;
            }

            for (int j = 0; j < this.boardSize; j++) {
                if (i + j == Math.abs(i1 - i2)) {
                    if (board[i][j] == this.turnChar) {
                        result6++;
                        if (result6 == this.pattern) {
                            this.doNextMove = false;
                            result();
                            break;
                        }
                    } else {
                        result6 = 0;
                    }
                }
            }
            if (!this.doNextMove) {
                break;
            }
        }
    }

    /**
     * Prints the congratulations message on win
     */
    public void result() {
        renderStructure();
        System.out.printf("%s%sPlayer %s won the game.\n", RED_BG, WHITE, turnChar);
        System.out.println(RED_BG + WHITE + "Thanks For Playing." + RESET);
    }

    /**
     * Pushes the variables to JSON object and return JSON format in a String
     * and Write to <b>output.json</b>
     *
     * @throws IOException
     */
    public void jsonWrite() throws IOException {
        JSON games = new JSON(this.boardSize, this.board, this.playerChar, this.playerColor, this.input1, this.input2);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String out = gson.toJson(games);
        FileUtils.writeStringToFile(new File("output.json"), out);
    }

    /**
     * Iterates every move till winning
     *
     * @throws IOException
     */
    public void start() throws IOException {
        init();
        validateStructure();
        int move = 0;
        while (this.doNextMove) {
            renderStructure();
            askInput();

            if (!importJson) {
                input1.add(scan.nextInt() - 1);
                input2.add(scan.nextInt() - 1);
            }

            // foreground fix :( to not start indexes from 0 by adding -1 ^
            validateInput(input1.get(move), input2.get(move));
            if (!restart) {
                move++;
                continue;
            }
            setValue(input1.get(move), input2.get(move));
            logic(input1.get(move), input2.get(move));

            nextTurn();
            move++;
        }
        if (exportJson) {
            jsonWrite();
        }
    }

    /**
     * Creates TicTacToe's object and runs <b>start</b> method
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        TicTacToe game = new TicTacToe();
        game.start();
    }

}
