/*
 * Copyright (C) 2018, Manjot Sidhu <manjot.techie@gmail.com>
 */
package tictactoe;

import java.util.ArrayList;

public class mArrayList {

    public static void main(String args[]) {
        ArrayList matrix = new ArrayList();
        matrix.add(new ArrayList());
        ((ArrayList) matrix.get(0)).add("row 0 col 0");
        ((ArrayList) matrix.get(0)).add("row 0 col 1");
        matrix.add(new ArrayList());
        ((ArrayList) matrix.get(1)).add("row 1 col 0");
        ((ArrayList) matrix.get(1)).add("row 1 col 1");
        // display contents of matrix
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < ((ArrayList) matrix.get(i)).size(); j++) {
                System.out.print((String) ((ArrayList) matrix.get(i)).get(j) + "  ");
            }
            System.out.println();
        }
    }
}
