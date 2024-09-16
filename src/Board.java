/**
 * BD 9-2024
 * CS220 Project 1
 * ---
 * Board object for use as the main board in the game of life
 */

import java.util.Random;

public class Board {
    // Holds the current state of the NxM board
    //  Using booleans instead of ints since we're only using two different states.
    /**
     * Note:
     * The use of booleans here may not actually cause an increase in computational efficiency, due to several factors including
     * but not limited to how you can't directly add two boolean values together, and how each boolean has an address
     * which refers to its location in memory and generally takes up about 1 byte a piece (for a few reasons);
     * I've still used booleans here to demonstrate how you can use them in this scenario due to how we only need to represent
     * two different states for each cell, so as to illustrate another manner of looking at the problem.
     * Another way one might wish to go about doing something similar with more space efficiency is by using BitSets,
     * which are essentially using the individual bits of a long-type primitive to hold a bunch of booleans at one address.
     * I have omitted the use of BitSets here for sake of clarity and readability of the code, however their use in this project
     * should be fairly similar to how I've implemented things here.
     */
    private boolean[][] mainBoard;
    Random rng = new Random();      // Used for randomizing board state

    // Default constructor to initialize board state to the default 10x10 cells
    public Board() {
        // Call the parameterized constructor with height and width of 10
        this(10, 10);
    }

    // Parameterized constructor for the initialization of a board as an NxM matrix, i.e. (width)x(height)
    public Board(int height, int width) {
        // Instantiate the board to have given width and height
        this.mainBoard = new boolean[height][width];

        // Initialize the new board
        this.resetBoard();
    }

    // Constructor to allow copying Boards
    public Board(Board brd) {
        // Instantiate board to width and height of old board
        this.mainBoard = new boolean[brd.height()][brd.width()];

        // Set the new board state to the state of the given board
        this.setBoard(brd.board());
    }

    // Get the height of the board
    public int height() {
        return this.mainBoard.length;
    }

    // Get the width of the board
    public int width() {
        return this.mainBoard[0].length;
    }

    // Get the board state
    public boolean[][] board() {
        return this.mainBoard;
    }

    // Get the cell at the given indices
    public int getCell(int i, int j) {
        // Check if the given indices are in bounds
        if (i < this.mainBoard.length && j < this.mainBoard[0].length) {
            // Return a 1 if the cell at the given index is true, a 0 if it's false
            return this.mainBoard[i][j] ? 1 : 0;
        }
        // If given indices are out of bounds
        else {
            // Tell user if the input height is larger than the board height
            if (i >= this.mainBoard.length)
                System.out.println(String.format("Board.java: getCell(%d, %d): Input height %d larger than board height %d. Please try again.", i, j, i, this.mainBoard.length));
            // Tell user if the input width is larger than the board height
            if (j >= this.mainBoard[0].length)
                System.out.println(String.format("Board.java: getCell(%d, %d): Input width %d larger than board height %d. Please try again.", i, j, i, this.mainBoard[0].length));

            // Return -1, denoting an error
            return -1;
        }
    }

    // Overload function for setCell; if no state given, flip the cell at the given index
    public int setCell(int i, int j) {
        // Flip the state of the boolean representing the given cell, if it is within the bounds of the board
        if (i < this.mainBoard.length && j < this.mainBoard[0].length) {
            this.mainBoard[i][j] = !this.mainBoard[i][j];

            // Denote successful execution
            return 0;
        }
        // Otherwise, ask the user to try again within the bounds of the board
        else {
            // Tell user if the input height is larger than the board height
            if (i >= this.mainBoard.length)
                System.out.println(String.format("Board.java: setCell(%d, %d): Input height %d larger than board height %d. Please try again.",
                        i, j, i, this.mainBoard.length));
            // Tell user if the input width is larger than the board height
            if (j >= this.mainBoard[0].length)
                System.out.println(String.format("Board.java: setCell(%d, %d): Input width %d larger than board height %d. Please try again.",
                        i, j, i, this.mainBoard[0].length));

            // Denote an issue has occurred
            return -1;
        }
    }

    // Set the state (i.e. boolean/bit) of the cell at the given index
    public int setCell(int i, int j, boolean state) {
        // Flip the state of the boolean representing the given cell, if it is within the bounds of the board
        if (i < this.mainBoard.length && j < this.mainBoard[0].length) {
            this.mainBoard[i][j] = state;

            // Denote successful execution
            return 0;
        }
        // Otherwise, ask the user to try again within the bounds of the board
        else {
            // Tell user if the input height is larger than the board height
            if (i >= this.mainBoard.length)
                System.out.println(String.format("Board.java: setCell(%d, %d, %b): Input height %d larger than board height %d. Please try again.",
                        i, j, state, i, this.mainBoard.length));
            // Tell user if the input width is larger than the board height
            if (j >= this.mainBoard[0].length)
                System.out.println(String.format("Board.java: setCell(%d, %d, %b): Input width %d larger than board height %d. Please try again.",
                        i, j, state, i, this.mainBoard[0].length));

            // Denote an issue has occurred
            return -1;
        }
    }

    // Set the state of the main board to the state of the given board
    public int setBoard(boolean[][] newBoard) {
        // Check to make sure the boards are of the same size
        if (newBoard.length == this.mainBoard.length && newBoard[0].length == this.mainBoard[0].length) {
            // Copy the values of the new board to the main board one row at a time
            for (int i = 0; i < newBoard.length; i++) {
                // Copy the current row from the new board to the old board
                System.arraycopy(newBoard[i], 0, this.mainBoard[i], 0, newBoard[0].length);
            }

            // Denote successful board set
            return 0;
        }
        // Otherwise, do nothing and print into console what happened
        else {
            // Tell user if the input board height is larger than the existing board height
            if (newBoard.length != this.mainBoard.length)
                System.out.println(String.format("Board.java: setBoard(): Input board height %d larger than existing board height %d. Please try again.",
                        newBoard.length, this.mainBoard.length));
            // Tell user if the input board width is larger than the existing board width
            if (newBoard[0].length != this.mainBoard[0].length)
                System.out.println(String.format("Board.java: setBoard(): Input board width %d larger than existing board width %d. Please try again.",
                        newBoard[0].length, this.mainBoard[0].length));

            // Denote unsuccessful board set
            return -1;
        }
    }

    // Reset the state of the board to all dead
    public void resetBoard()
    {
        // Populate the board with zeros (a.k.a. false)
        for (int i = 0; i < this.mainBoard.length; i++)        // Columns
        {
            for (int j = 0; j < this.mainBoard[0].length; j++)     // Rows
            {
                // Set current cell to false (denoting a 0)
                this.mainBoard[i][j] = false;
            }
        }
    }

    // Check if any of a cell's neighbors are edges of the board
    private int getCellState(int i, int j) {
        // Check the top, left, bottom, and right neighbors of the cell
        boolean checkTop = (i - 1) < 0;
        boolean checkLeft = (j - 1) < 0;
        boolean checkBottom = (i + 1) >= this.mainBoard.length;
        boolean checkRight = (j + 1) >= this.mainBoard[0].length;

        // Return the cell's state in the form of a base2 derived int (unique for each possible state)
        return ((checkTop ? 1 : 0) + (checkLeft ? 1 : 0) * 2 + (checkBottom ? 1 : 0) * 4 + (checkRight ? 1 : 0) * 8);
    }

    // Get the number of neighboring cells given a cell's indices
    private int getCellNeighbors(int i, int j) {
        // Check to make sure the given indices are within bounds of the board
        if (i < this.mainBoard.length && j < this.mainBoard[0].length) {
            // Get the number of neighbors while taking account of potential board borders neighboring the given cell
            return switch (this.getCellState(i, j)) {
                //  The syntax used here essentially just gets the sum of the output of if-else statements;
                //  if the boolean representing the cell is true, it returns 1; otherwise, 0. The sum of these is the number of neighbors in this case

                // No cells are out of bounds. This is treated the same as default, and is done as it is here
                //  so that the other case checks don't always have to be made when no cell out of bounds
                case 0 ->
                        (this.mainBoard[i - 1][j] ? 1 : 0) + (this.mainBoard[i][j - 1] ? 1 : 0) + (this.mainBoard[i][j + 1] ? 1 : 0)
                                + (this.mainBoard[i + 1][j] ? 1 : 0) + (this.mainBoard[i + 1][j - 1] ? 1 : 0) + (this.mainBoard[i + 1][j + 1] ? 1 : 0)
                                + (this.mainBoard[i - 1][j - 1] ? 1 : 0) + (this.mainBoard[i - 1][j + 1] ? 1 : 0);

                // Only the top cells are out of bounds, so we sum up the rest of the cells
                case 1 ->
                        (this.mainBoard[i][j - 1] ? 1 : 0) + (this.mainBoard[i][j + 1] ? 1 : 0) + (this.mainBoard[i + 1][j] ? 1 : 0)
                                + (this.mainBoard[i + 1][j - 1] ? 1 : 0) + (this.mainBoard[i + 1][j + 1] ? 1 : 0);

                // Only the left cells are out of bounds
                case 2 ->
                        (this.mainBoard[i - 1][j] ? 1 : 0) + (this.mainBoard[i][j + 1] ? 1 : 0) + (this.mainBoard[i + 1][j] ? 1 : 0)
                                + (this.mainBoard[i + 1][j + 1] ? 1 : 0) + (this.mainBoard[i - 1][j + 1] ? 1 : 0);

                // Only the bottom cells are out of bounds
                case 4 ->
                        (this.mainBoard[i - 1][j] ? 1 : 0) + (this.mainBoard[i][j - 1] ? 1 : 0) + (this.mainBoard[i][j + 1] ? 1 : 0)
                                + (this.mainBoard[i - 1][j + 1] ? 1 : 0) + (this.mainBoard[i - 1][j - 1] ? 1 : 0);

                // Only the right cells are out of bounds
                case 8 ->
                        (this.mainBoard[i - 1][j] ? 1 : 0) + (this.mainBoard[i][j - 1] ? 1 : 0) + (this.mainBoard[i + 1][j] ? 1 : 0)
                                + (this.mainBoard[i + 1][j - 1] ? 1 : 0) + (this.mainBoard[i - 1][j - 1] ? 1 : 0);

                // The top and left cells are out of bounds
                case 3 ->
                        (this.mainBoard[i][j + 1] ? 1 : 0) + (this.mainBoard[i + 1][j] ? 1 : 0) + (this.mainBoard[i + 1][j + 1] ? 1 : 0);

                // The left and bottom cells are out of bounds
                case 6 ->
                        (this.mainBoard[i - 1][j] ? 1 : 0) + (this.mainBoard[i][j + 1] ? 1 : 0) + (this.mainBoard[i - 1][j + 1] ? 1 : 0);

                // The bottom and right cells are out of bounds
                case 12 ->
                        (this.mainBoard[i - 1][j] ? 1 : 0) + (this.mainBoard[i][j - 1] ? 1 : 0) + (this.mainBoard[i - 1][j - 1] ? 1 : 0);

                // The top and right cells are out of bounds
                case 9 ->
                        (this.mainBoard[i][j - 1] ? 1 : 0) + (this.mainBoard[i + 1][j] ? 1 : 0) + (this.mainBoard[i + 1][j - 1] ? 1 : 0);

                // The top and bottom cells are out of bounds
                case 5 ->
                        (this.mainBoard[i][j - 1] ? 1 : 0) + (this.mainBoard[i][j + 1] ? 1 : 0);

                // The top, bottom, and left cells are out of bounds
                case 7 ->
                        (this.mainBoard[i][j + 1] ? 1 : 0);

                // The left and right cells are out of bounds
                case 10 ->
                        (this.mainBoard[i - 1][j] ? 1 : 0) + (this.mainBoard[i + 1][j] ? 1 : 0);

                // The top, left, and right cells are out of bounds
                case 11 ->
                        (this.mainBoard[i + 1][j] ? 1 : 0);

                // The top, bottom, and right cells are out of bounds
                case 13 ->
                        (this.mainBoard[i][j - 1] ? 1 : 0);

                // The bottom, left, and right cells are out of bounds
                case 14 ->
                        (this.mainBoard[i - 1][j] ? 1 : 0);

                // All surrounding cells out of bounds
                case 15 ->
                        0;  // Return 0, denoting no neighbors

                // Default base case (i.e. no cells are out of bounds)
                default ->
                        (this.mainBoard[i - 1][j] ? 1 : 0) + (this.mainBoard[i][j - 1] ? 1 : 0) + (this.mainBoard[i][j + 1] ? 1 : 0)
                                + (this.mainBoard[i + 1][j] ? 1 : 0) + (this.mainBoard[i + 1][j - 1] ? 1 : 0) + (this.mainBoard[i + 1][j + 1] ? 1 : 0)
                                + (this.mainBoard[i - 1][j - 1] ? 1 : 0) + (this.mainBoard[i - 1][j + 1] ? 1 : 0);
            };
        }
        // If given indices are out of bounds
        else {
            // Tell user if the input height is larger than the board height
            if (i >= this.mainBoard.length)
                System.out.println(String.format("Board.java: getCellNeighbors(%d, %d): Input height %d larger than board height %d. Please try again.", i, j, i, this.mainBoard.length));
            // Tell user if the input width is larger than the board height
            if (j >= this.mainBoard[0].length)
                System.out.println(String.format("Board.java: getCellNeighbors(%d, %d): Input width %d larger than board height %d. Please try again.", i, j, i, this.mainBoard[0].length));

            // Return -1, denoting an error
            return -1;
        }
    }

    // Check if the cell at given indices needs to be changed based on the conditions of the game of life
    private int checkCell(int i, int j) {
        // Check to make sure the given indices are within the bounds of the existing board
        if (i < this.mainBoard.length && j < this.mainBoard[0].length) {
            // Get the number of neighbors to this cell
            int numNeighbors = this.getCellNeighbors(i, j);

            // A cell is born (i.e. set to true) if there are three neighbors
            if (numNeighbors == 3)
                return 1;    // Cell needs to be changed/flipped
            //this.setCell(i, j, true);

            // A cell dies (i.e. set to false) if there are four or more neighbors or one or zero
            if (numNeighbors > 3 || numNeighbors < 2)
                return 0;
            //this.setCell(i, j, false);

            // If none of the above conditionals were triggered, return its current value
            return this.getCell(i, j);
        }
        // If given indices are out of bounds
        else {
            // Tell user if the input height is larger than the board height
            if (i >= this.mainBoard.length)
                System.out.println(String.format("Board.java: checkCell(%d, %d): Input height %d larger than board height %d. Please try again.", i, j, i, this.mainBoard.length));
            // Tell user if the input width is larger than the board height
            if (j >= this.mainBoard[0].length)
                System.out.println(String.format("Board.java: checkCell(%d, %d): Input width %d larger than board height %d. Please try again.", i, j, i, this.mainBoard[0].length));
        }

        // Return -1, denoting an error
        return -1;
    }

    // Update the state of the board to the next generation as per the guidelines of the game of life
    public void nextGeneration() {
        /**
         * N.B.:
         *  Creating a new board here in order to actually copy all of the values of each of the booleans into a new 2D array, rather than simply passing
         *  a reference to them. Otherwise, doing this here would effectively do nothing in the cause of preventing inter-generational interference.
         *  Another way to do this might be doing it row by row, since the only possible interference would be from one row to the next, however in order to do this
         *  you'd essentially have to do the same thing as this is doing, i.e. copying several rows to a new place in memory in order to store them temporarily during
         *  board update time.
         */
        // Create a new board to store the existing board along with any cell state changes.
        //  Doing this separately from the existing board to prevent interference between cell rows during the shift to the next generation
        Board oldBoard = new Board(this);

        // Go through each cell and update its state accordingly
        for (int i = 0; i < this.mainBoard.length; i++)         // Cols
        {
            for (int j = 0; j < this.mainBoard[0].length; j++)  // Rows
            {
                // Update the cell at the given index in the main board by checking the old board
                this.setCell(i, j, oldBoard.checkCell(i, j) != 0);
            }
        }
    }

    // Randomize the state of the board
    public void randomizeBoard()
    {
        // Go through each cell, and set it to a pseudo-random state
        for (int i = 0; i < height(); i++)      // Rows
        {
            for (int j = 0; j < width(); j++)   // Cols
            {
                // Set the current cell in the main board to be a randomly selected boolean
                this.mainBoard[i][j] = rng.nextInt(2) == 1;
            }
        }
    }

    // Display information pertaining to the Board instance
    public String toString()
    {
        // String to hold the output
        StringBuffer output = new StringBuffer();

        // Board size
        output.append(String.format("Board size:\t%dx%d\t(Height x Width)", this.mainBoard.length, this.mainBoard[0].length));

        // Board state
        output.append("\nBoard state:");
        for (int i = 0; i < this.mainBoard.length; i++)         // Cols
        {
            // Add tabs at start of lines as well as new lines
            output.append("\n\t");
            for (int j = 0; j < this.mainBoard[0].length; j++)  // Rows
            {
                output.append(String.format("%d ", this.getCell(i, j)));
            }
        }

        return output.toString();
    }
}
