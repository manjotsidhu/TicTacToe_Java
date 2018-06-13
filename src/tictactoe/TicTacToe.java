/**
 * Copyright (C) 2018, Manjot Sidhu <manjot.techie@gmail.com>
 */
package tictactoe;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static tictactoe.Colors.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * TicTacToe Object is the main and the only component in the functionality of
 * this TicTacToe game
 *
 * @version 0.1
 * @author ManjotSidhu
 */
public class TicTacToe {

    // Board Array and board size    
    private String[][] board;
    private int boardSize;

    // Events Array
    ArrayList events = new ArrayList();

    private String[] playerChar;
    private String[] playerColor;

    // in-game params
    private String turnChar;
    private String turnColor;
    private int head;
    private boolean doNextMove = true;
    private final int pattern = 3;
    private boolean restart = false;
    private boolean doJsonImport = true;
    private boolean doJsonExport = false;

    // Moves data
    ArrayList<Integer> input1 = new ArrayList<>();
    ArrayList<Integer> input2 = new ArrayList<>();

    // json.simple objects
    JSONObject PlayingData;
    JSONArray moves;
    JSONObject MainJSON;

    // Scanner
    Scanner scan = new Scanner(System.in);

    /**
     * Get player's input to import JSON file
     */
    public void askJsonImport() {
        System.out.print(GREEN + "Do you want to load game params from Json:[Y/n] " + RESET);
        char in = scan.next(".").charAt(0);
        switch (in) {
            case 'Y':
                this.doJsonImport = true;
                break;
            case 'n':
                this.doJsonImport = false;
                break;
            default:
                System.out.println(RED + "Enter correct response" + RESET);
                askJsonImport();
        }
    }

    /**
     * Get player's input to export JSON file
     */
    public void askJsonExport() {
        System.out.print(GREEN + "Do you want to export game params to Json:[Y/n] " + RESET);
        char out = scan.next(".").charAt(0);
        switch (out) {
            case 'Y':
                this.doJsonExport = true;
                break;
            case 'n':
                this.doJsonExport = false;
                break;
            default:
                System.out.println(RED + "Enter correct response" + RESET);
                askJsonExport();
                break;
        }
    }
    
    /**
     * initialize necessary JSON stuff
     */
    public void jsonInit() {

        PlayingData = new JSONObject();
        moves = new JSONArray();
        MainJSON = new JSONObject();

        JSONObject Board = new JSONObject();
        Board.put("Size X", this.boardSize);
        Board.put("Size Y", this.boardSize);

        JSONObject Players = new JSONObject();
        Players.put("Count", this.playerChar.length);

        JSONArray PlayersData = new JSONArray();
        for (int i = 0; i < this.playerChar.length; i++) {
            JSONObject n = new JSONObject();
            n.put("color", this.playerColor[i]);
            n.put("character", this.playerChar[i]);
            PlayersData.add(n);
        }

        Players.put("PlayersData", PlayersData);
        MainJSON.put("Board", Board);
        MainJSON.put("Players", Players);
    }

    /**
     * Reads Json input file, parses it to a String and sets
     * variables to their superclass variables
     *
     * @throws FileNotFoundException if <b>input.json</b> file is not found
     * @throws IOException if any I/O error occurs
     * @throws org.json.simple.parser.ParseException if json.simple fails to
     * parse to JSONObjects and JSONArrays
     */
    public void jsonRead() throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        System.out.println(GREEN + "Enter File Location: " + RESET);
        Object obj = parser.parse(new FileReader(scan.next()));

        JSONObject myjson = (JSONObject) obj;
        JSONObject board = (JSONObject) myjson.get("Board");

        JSONObject PlayingData = (JSONObject) myjson.get("PlayingData");
        JSONArray movess = (JSONArray) PlayingData.get("Moves");

        ArrayList<Integer> input1 = new ArrayList<>();
        ArrayList<Integer> input2 = new ArrayList<>();

        for (int i = 0; i < movess.size(); i++) {
            JSONObject insideMove = (JSONObject) movess.get(i);
            input1.add(Math.toIntExact((Long) insideMove.get("row")));
            input2.add(Math.toIntExact((Long) insideMove.get("column")));
        }

        JSONObject players = (JSONObject) myjson.get("Players");
        int count = Integer.parseInt(players.get("Count").toString());

        JSONArray data = (JSONArray) players.get("PlayersData");

        String[] playerChar = new String[count];
        String[] playerColor = new String[count];

        for (int j = 0; j < count; j++) {
            JSONObject insideData = (JSONObject) data.get(j);
            playerChar[j] = (String) insideData.get("character");
            playerColor[j] = (String) insideData.get("color");
        }

        this.boardSize = Integer.parseInt(board.get("Size X").toString());
        this.input1 = input1;
        this.input2 = input2;
        this.playerChar = playerChar;
        this.playerColor = playerColor;
    }

    /**
     * Inputs size/order of board array and initialize the array
     */
    public void setSize() {
        System.out.print(GREEN + "Enter Size/Order: " + RESET);

        this.boardSize = scan.nextInt();
        // boardSize predefined
        // this.boardSize = 6;

        board = new String[boardSize][boardSize];
    }

    /**
     * Inputs number of players playing this game
     */
    public void setUsers() {
        System.out.print(GREEN + "Number of players: " + RESET);
        int players = scan.nextInt();
        validateUsers(players);
        playerChar = new String[players];
        playerColor = new String[players];

        this.head = 0;
    }
    
    /**
     * Validate number of players inputted by player
     * 
     * @param i number of players from Scanner
     */
    public void validateUsers(int i) {
        // TODO: fix temp value to dynamic
        int temp = 6;
        if(i < 1 || i > temp) {
            System.out.println(RED + "Number of players should not be less than 1"
                + " and more than " + temp + RESET);
            System.out.println(RED + "Please try again" + RESET);
            setUsers();
        }
    }

    /**
     * Inputs player-wise characters and colors to choose from
     */
    public void setUserProp() {
        for (int i = 0; i < playerChar.length; i++) {
            int n = i + 1;
            System.out.print(GREEN + "Player " + n + ", enter your character: " + RESET);
            this.playerChar[i] = scan.next().substring(0, 1);

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
                default:
                    playerColor[i] = PURPLE;
                    int player = i+1; 
                    System.out.println(YELLOW + "Invalid color given by Player " + player + RESET);
                    System.out.println(YELLOW + "Setting color to PURPLE" + RESET);
                    break;
            }
        }
    }

    /**
     * Runs all the methods used to fetch all the needed params
     *
     * @throws IOException
     * @throws java.io.FileNotFoundException
     * @throws org.json.simple.parser.ParseException
     */
    // Init in-game parameters
    public void init() throws IOException, FileNotFoundException, ParseException {
        askJsonImport();
        if (this.doJsonImport) {
            jsonRead();
            this.board = new String[boardSize][boardSize];
        } else {
            setSize();
            setUsers();
            setUserProp();
            playerColors();
        }

        // Game starts with first player
        this.turnChar = playerChar[0];
        this.turnColor = playerColor[0];
        jsonInit();
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
                if (board[i][j] == null) {
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
            this.restart = true;
        } // foreground fix :( to not start indexes from 0  
        /*else if(i == 0 && j == 0) {
            System.out.println(RED + "Invalid index, indexes starts from 1(inclusive)" + RESET);
            this.restart = true;
        }*/ else if (board[i][j] != null) {
            // foreground fix :( to not start indexes from 0
            int fix1 = i + 1;
            int fix2 = i + 1;
            System.out.println(RED + fix1 + " " + fix2 + " index already has " + board[i][j] + " value." + RESET);
            this.restart = true;
        } else {
            this.restart = false;
        }
    }

    /**
     * Adds values after the Last Element of Events Array
     *
     * @param x X<sup>th</sup> index of Board (Starting from 0)
     * @param y Y<sup>th</sup> index of Board (Starting from 0)
     * @param z Player Character
     * @param q Event type, 0 - General Value, 1 - Value from Undo, 2 - Value
     * from Redo
     */
    public void newEvent(int x, int y, String z, int q) {
        events.add(new ArrayList());
        ((ArrayList) events.get(events.size() - 1)).add(x);
        ((ArrayList) events.get(events.size() - 1)).add(y);
        ((ArrayList) events.get(events.size() - 1)).add(z);
        ((ArrayList) events.get(events.size() - 1)).add(q);
    }

    /**
     * Undo last move by removing last element from the <b>Board</b> and <b>Moves</b>
     * Array and adding a new event in events array
     */
    public void doUndo() {
        newEvent((Integer)((ArrayList) events.get(events.size() - 1)).get(0),(Integer) ((ArrayList) events.get(events.size() - 1)).get(1),(String)((ArrayList) events.get(events.size() - 1)).get(2), 1);
        this.board[this.input1.get(this.input1.size()-1)-1][this.input2.get(this.input2.size()-1)-1] = null;
        // foreground fix :( to not start indexes from 0 by adding -1 ^
        this.input1.remove(this.input1.size()-1);
        this.input2.remove(this.input2.size()-1);
        
        this.restart = true;
        prevTurn();
    }
    
    /**
     * Redo last move by getting last value from events and putting to <b>Board</>
     * and <b>Moves</b> array
     */
    public void doRedo() {
        int x = (Integer) ((ArrayList) events.get(this.events.size()-1)).get(0);
        int y = (Integer) ((ArrayList) events.get(this.events.size()-1)).get(1);
        String turnChar = (String) ((ArrayList) events.get(this.events.size()-1)).get(2);
        this.board[x][y] = turnChar;
        input1.add(x+1);
        input2.add(y+1);
        // foreground fix :( to not start indexes from 0 by adding +1 ^
        
        newEvent(x, y, turnChar, 2);
        this.restart = true;
        nextTurn();
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
     * Updates <b>current</b> character and color to previous player's turn
     * accordingly
     */
    public void prevTurn() {

        if (head == 0) {
            head = playerChar.length - 1;
        } else {
            head--;
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
     * Initializes Json objects and updates params on every input
     *
     * @param x Integer index at X<sup>th</sup> position
     * @param y Integer index at Y<sup>th</sup> position
     */
    public void jsonUpdate(int x, int y) {
        x++;
        y++;
        // foreground fix :( to not start indexes from 0 by adding +1 ^

        JSONObject m = new JSONObject();
        m.put("row", x);
        m.put("column", y);
        moves.add(m);

        PlayingData.put("Moves", moves);
        MainJSON.put("PlayingData", PlayingData);
    }

    /**
     * Prints the congratulations message on win
     */
    public void result() {
        renderStructure();
        System.out.printf("%s%sPlayer %s won the game.\n", RED_BG, WHITE, turnChar);
        System.out.println(RED_BG + WHITE + "Thanks For Playing." + RESET);
        if(!this.doJsonImport) {
            askJsonExport();
        }
    }

    /**
     * Pushes the variables to JSON object and return JSON format in a String
     * and Write to Json File
     *
     * @throws IOException
     */
    public void jsonWrite() throws IOException {
        System.out.println(GREEN + "Enter File Name" + RESET);
        String filename = scan.next();
        if(!filename.contains("json")) {
            filename += ".json";
        }
        FileWriter file = new FileWriter(filename);

        file.write(MainJSON.toJSONString());
        file.flush();
    }

    /**
     * Iterates every move till winning
     *
     * @throws IOException
     * @throws java.io.FileNotFoundException
     * @throws org.json.simple.parser.ParseException
     */
    public void start() throws IOException, FileNotFoundException, ParseException {
        init();
        validateStructure();
        int move = 0;
        while (this.doNextMove) {
            renderStructure();
            askInput();
            
            if (!this.doJsonImport) {
                if(!scan.hasNextInt()) {
                    String t = scan.next();
                    if(t.equals("undo")) {
                        doUndo();
                        move--;
                        if (restart) {
                            continue;
                        }
                    } else if(t.equals("redo")) {
                        doRedo();
                        move++;
                        if(restart) {
                            continue;
                        }
                    } else if(t.equals("save")) {
                        jsonWrite();
                        this.doNextMove = false;
                        continue;
                    }
                }
                
                input1.add(scan.nextInt());
                input2.add(scan.nextInt());
            }

            int x = input1.get(move) - 1;
            int y = input2.get(move) - 1;
            // foreground fix :( to not start indexes from 0 by adding -1 ^
            
            validateInput(x, y);
            if (restart) {
                move++;
                continue;
            }
            jsonUpdate(x, y);
            newEvent(x, y, this.turnChar, 0);
            setValue(x, y);
            logic(x, y);
            
            nextTurn();
            move++;
            if(this.doJsonImport) {
                if(move == input1.size()) {
                    this.doJsonImport = false;
                }
            }
        }
        if (this.doJsonExport) {
            jsonWrite();
        }
        
    }

    /**
     * Creates TicTacToe's object and runs <b>start</b> method
     *
     * @throws IOException
     * @throws java.io.FileNotFoundException
     * @throws org.json.simple.parser.ParseException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        TicTacToe game = new TicTacToe();
        game.start();
    }

}
