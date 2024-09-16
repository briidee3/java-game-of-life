/**
 * BD 9-2024
 * CS220 Project 1
 * ---
 * Main class for the game of life
 */

// Import the Scanner class for getting user input
import java.util.Scanner;

public class Main
{
    // Helper function to handle taking yes or no input from the user via the console.
    private static char askYesNo(Scanner userInput)
    {

        // Hold the user's response data as a char
        char input = (char) 0;

        // Until the user inputs a 'y' or 'n', keep prompting them
        do
        {
            // Prompt user for input
            System.out.println("Please enter 'y' or 'n':");
            try
            {
                // Check if there's a new line to check
                if (userInput.hasNextLine())
                {
                    // Set input to the input from the scanner
                    input = userInput.nextLine().charAt(0);
                }
            }
            // If there was an exception, let the user know via the console
            catch (Exception e)
            {
                System.out.println("Main.java: askYesNo(): An Exception has occurred. Please try again.\n\t" + e.toString());
            }

        } while (input != 'y' && input != 'n');

        // Return the user's input
        return input;
    }

    // Prompt user and create new Board given user input width and height
    public static Board createBoard()
    {
        // Create scanner to get user input from the console
        Scanner userInput = new Scanner(System.in);

        // Initialize variables representing board height and width
        int height = 0;
        int width = 0;

        // Print out the current game state
        System.out.println("\n\nBOARD CREATION:\n");

        //  Repeat this process until a valid height has been acquired and set
        while (height == 0)
        {
            // Prompt user for board height, get user input
            System.out.println("Please enter a valid board height (0-99):");
            try
            {
                // Check if the user input an integer (or equivalent), as well as that it's a reasonable size
                if (userInput.hasNextInt())
                {
                    height = userInput.nextInt();   // Set height to next integer

                    // Check if height is less than 100 and greater than 0 to ensure validity. Otherwise, reset height
                    if (!((height < 100) && (height > 0)))
                    {
                        // Reset width, to prevent breaking out of the loop
                        height = 0;
                        // Let the user know what the issue was
                        System.out.println("Only integers less than 100 and greater than 0 may be used.");
                    }
                }
                else
                    System.out.println("Only integers less than 100 and greater than 0 may be used.");
            }
            catch (Exception e)
            {
                System.out.println("Main.java: createBoard(): An Exception has occurred. Please try again.");
            }

            userInput.nextLine();           // Go to next line of System.in
        }

        //  Repeat this process until a valid height has been acquired and set
        while (width == 0)
        {
            // Prompt user for board width, get user input
            System.out.println("Please enter a valid board width (0-99):");
            try
            {
                // Check if the user input an integer (or equivalent), as well as that it's a reasonable size
                if (userInput.hasNextInt())
                {
                    width = userInput.nextInt();   // Set width to next integer

                    // Check if width is less than 100 and greater than 0 to ensure validity. Otherwise, reset width
                    if (!((width < 100) && (width > 0)))
                    {
                        // Reset width, to prevent breaking out of the loop
                        width = 0;
                        // Let the user know what the issue was
                        System.out.println("Only integers less than 100 and greater than 0 may be used.");
                    }
                }
                else
                    System.out.println("Only integers less than 100 and greater than 0 may be used.");
            }
            catch (Exception e)
            {
                System.out.println("Main.createBoard(): An Exception has occurred. Please try again.");
            }

            userInput.nextLine();           // Go to next line of System.in
        }

        // Create and return a new Board of specified width and height
        return new Board(height, width);
    }

    // Allow user to interactively set cells on the board
    public static void setBoard(Board mainBoard)
    {
        // Create scanner to get user input from the console
        Scanner userInput = new Scanner(System.in);

        // User input handlers
        char input;
        String[] curResponse;
        int[] intResponse = new int[3];

        // Print out current game state
        System.out.println("\nSETTING BOARD:\n");

        // Ask user if they'd like to set a cell, repeating until getting a response equivalent to 'y' or 'n'
        System.out.println("Would you like to set one of the cells of the board? (y/n):");
        input = askYesNo(userInput);
        // If the user would not like to set a cell, exit the method
        if (input == 'n')
            return;

        // Reset input variable for use in logic below
        input = 0;

        // Ask user for comma separated indices within the bounds of the board, repeating until given a valid response,
        //  repeating until given a valid response
        System.out.println("Please enter the coordinates of the cell you'd like to set, as well as what you'd like to set it to (1/0). " +
                "\n\tFormat your response as \"<height>,<width>,<value>\", for example, \"5,4,1\" (without quotes):");
        while (input == 0)
        {
            // Try to set the cell, assuming input was valid
            try
            {
                // Acquire user input, split at the comma into separate numbers
                curResponse = userInput.nextLine().split(",");
                intResponse[0] = Integer.parseInt(curResponse[0]);
                intResponse[1] = Integer.parseInt(curResponse[1]);
                intResponse[2] = Integer.parseInt(curResponse[2]);

                // Check if the third input is a 1 or a 0. If not, ask for new input.
                if (intResponse[2] > 1 || intResponse[2] < 0)
                    throw new ArithmeticException("Invalid input for cell state");

                // Try to set the cell; if Board.setCell() exits cleanly (i.e. returns 0), exit the while loop
                //  (i.e. set input to something other than 0). Otherwise, try again.
                else if (mainBoard.setCell(intResponse[0], intResponse[1], intResponse[2] == 1) == 0)
                    input = 1;
                else
                {
                    System.out.println("Acquired invalid input. Please try again." +
                            "\n\tFormat your response as \"<height>,<width>,<value>\", for example, \"5,4,1\" (without quotes):");
                }
            }
            catch (Exception e)
            {
                System.out.println("Acquired invalid input. Please try again." +
                        "\n\tFormat your response as \"<height>,<width>,<value>\", for example, \"5,4,1\" (without quotes):");
            }
        }

        // Call setBoard again; if the user doesn't want to set another cell, they can exit from there; this allows the user to set as many cells
        //  as they wish at the start of the game.
        setBoard(mainBoard);
    }

    // Allow the user to step through the game of life until they no longer wish to continue
    //  This is the console version of the game
    public static void playGameConsole(Board mainBoard)
    {
        // Create scanner to get user input from the console
        Scanner userInput = new Scanner(System.in);

        // Print out current game state
        System.out.println("\nGAMEPLAY:\n");

        // Let user know how to play
        System.out.println("While the game is running, press \". + <Enter>\" to take a step to the next generation. " +
                "If you'd like to quit the game, press any other character prior to pressing \"<Enter>\".");

        // Main gameplay loop
        do
        {
            // Check if the input is "."
            //if (userInput.next().charAt(0) == '.')
            //{
                // Look at the board
            System.out.println(mainBoard.toString());

                // Take a step into the next generation
            mainBoard.nextGeneration();
                //userInput.nextLine();
  //          }
    //        // If not, exit game loop
      //      else
        //        break;

            // Check if the first character of the next line is not '.'. If so, exit the game.
            if (!userInput.nextLine().contains("."))
                break;
        } while (true);

        System.out.println("\nExiting the game of life.");
    }

    // Allow user to play the GUI version of the game
    public static void playGameGUI(Board mainBoard)
    {
        // Create a new GUI object
        GUI gameGUI = new GUI(mainBoard);
        gameGUI.startGUI();
    }

    public static void main(String[] args)
    {
        // Create the main board, given user input
        Board gameBoard = createBoard();
        // Hold user input for decision as to what version of the game to play
        char input;

        // Create temp scanner to ask player if they'd like to play the GUI version of the game
        Scanner userInput = new Scanner(System.in);
        // Ask the user if they'd like to play the GUI version of the game
        System.out.println("Would you like to play the GUI version of the game of life? (y/n):");
        input = askYesNo(userInput);

        // If the user would like to play the GUI version of the game, then start the GUI.
        //  Otherwise, start the console version of the game.
        if (input == 'y')
            playGameGUI(gameBoard);
        else
        {
            // Ask user if they'd like to randomize the board
            System.out.println("Would you like to randomize the state of the board? (y/n):");
            input = askYesNo(userInput);

            // If the user would like to randomize, randomize the board.
            //  Otherwise, prompt to manually set board.
            if (input == 'y')
            {
                // Randomize the state of the board
                gameBoard.randomizeBoard();
            }
            else
            {
                // Set the board, given user input
                setBoard(gameBoard);
            }

            // Play the game of life (console edition)
            playGameConsole(gameBoard);
        }
    }
}
