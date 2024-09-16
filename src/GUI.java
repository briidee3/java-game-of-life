/**
 * BD 9-2024
 * CS220 Project 1
 * ---
 * GUI for use with the game of life
 */

// Java Swing imports (for GUI)
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    // GUI elements
    private JFrame mainFrame;
    private JLabel gameName;
    private JLabel widthLabel;
    private JLabel heightLabel;
    private JPanel boardPanel;
    private JPanel buttonPanel;
    private JPanel changeSizePanel;
    private JButton advanceGeneration;
    private JButton resetBoard;
    private JButton randomize;
    private JButton setSize;
    private JSpinner[] boardSizeSpinner;
    private SpinnerNumberModel[] boardSize;
    private Cell[][] cellPanels;    // Create cells (defined by inner class at bottom)

    // Main board for the game of life
    private Board mainBoard;

    private int cellSpacing = 3;        // Visual spacing between cells in the board panel (in pixels)
    private int cellSize = 30;          // Visual size of each cell (in pixels)

    // Height and width of the board in pixels
    private int pxHeight;
    private int pxWidth;
    // Height and width of display in pixels
    private int displayHeight;
    private int displayWidth;

    // Default constructor for the GUI
    public GUI() {
        // Create a new board using default width and height, use parameterized constructor to instantiate GUI
        this(new Board());
    }

    // Parameterized constructor for GUI
    public GUI(Board mainBoard)
    {
        // Set local class instance of mainBoard to reference the Board passed to the constructor
        this.mainBoard = mainBoard;

        // Set up cell panels for each of the cells in mainBoard
        cellPanels = new Cell[this.mainBoard.height()][this.mainBoard.width()];

        // Initialize the spinners to the size of the given board
        boardSize = new SpinnerNumberModel[2];
        boardSizeSpinner = new JSpinner[2];
        // Height
        boardSize[0] = new SpinnerNumberModel(this.mainBoard.height(), 1, 100, 1);  // Height
        boardSizeSpinner[0] = new JSpinner(boardSize[0]);
        // Width
        boardSize[1] = new SpinnerNumberModel(this.mainBoard.width(), 1, 100, 1);   // Width
        boardSizeSpinner[1] = new JSpinner(boardSize[1]);
    }

    // Update the state of all of the cells on the board to that of their corresponding mainBoard cells
    private void updateBoard()
    {
        // Go through each of the cells in cellPanels and update their states
        for (int i = 0; i < cellPanels.length; i++)
        {
            for (int j = 0; j < cellPanels[0].length; j++)
            {
                cellPanels[i][j].updateCell();
            }
        }
    }

    // Set up the rendering of the Board in the GUI
    public void setupBoardGUI()
    {
        // Pixel width and height of main board
        pxWidth = (mainBoard.width() * cellSize) + cellSpacing;
        pxHeight = (mainBoard.height() * cellSize) + cellSpacing;

        // Adjust board for screen size; loops until smaller than screen size
        while (pxHeight > displayHeight || pxWidth > displayWidth)
        {
            // Subtract 1 pixel from each of the cells
            cellSize--;

            // Update pxWidth and pxHeight
            pxWidth = (mainBoard.width() * cellSize) + cellSpacing;
            pxHeight = (mainBoard.height() * cellSize) + cellSpacing;
        }

        // Set up the panel representing the main board
        boardPanel = new JPanel();
        // Set the boardPanel to have a grid layout sized based on the number of rows and cols in the mainBoard
        boardPanel.setLayout(new GridLayout(mainBoard.height(), mainBoard.width(), cellSpacing, cellSpacing));
        boardPanel.setBackground(Color.black);      // Set the background color of the board
        // Set the width, height, and location of top-left corner of boardPanel. The width and height are set to factors
        //  of the number of cells and their sizes in pixels.
        boardPanel.setPreferredSize(new Dimension(pxWidth, pxHeight));

        // Create cells on the board
        for (int i = 0; i < mainBoard.height(); i++)
        {
            for (int j = 0; j < mainBoard.width(); j++)
            {
                // Create new Cell
                cellPanels[i][j] = new Cell(i, j, mainBoard, cellSize);
                // Update new cell
                cellPanels[i][j].updateCell();
                // Add Cell to boardPanel
                boardPanel.add(cellPanels[i][j]);
            }
        }
    }

    // Begin the game via the GUI
    public void startGUI()
    {
        // Pixel width and height of main board
        pxWidth = (mainBoard.width() * cellSize) + cellSpacing;
        pxHeight = (mainBoard.height() * cellSize) + cellSpacing;

        // Pixel width and height of display
        GraphicsDevice display = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        displayWidth = display.getDisplayMode().getWidth();
        displayHeight = display.getDisplayMode().getHeight();

        // Set up main frame
        mainFrame = new JFrame("Game of Life");
        // Set size of the main window, accounting for screen size and board size
        if ((pxWidth + 300) > displayWidth || (pxHeight + 100) > displayHeight)
            mainFrame.setSize(displayWidth - 100, displayHeight - 100);
        else
            mainFrame.setSize(pxWidth + 300, pxHeight + 100);
        mainFrame.setLayout(new GridBagLayout());

        // Set up listener for exiting the main window
        mainFrame.addWindowListener(new WindowAdapter() {
            public void closeWindow(WindowEvent event) {
                System.exit(0);
            }
        });

        // Set up the board
        setupBoardGUI();

        // Set up button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        // Constraints for button panel
        GridBagConstraints bpConstraints = new GridBagConstraints();
        bpConstraints.gridx = 1;
        bpConstraints.gridy = 0;

        // Set up constraints for boardPanel
        GridBagConstraints boardConstraints = new GridBagConstraints();
        boardConstraints.gridx = 0;
        boardConstraints.gridy = 0;

        // Set up the button to advance to the next generation
        advanceGeneration = new JButton("Advance");
        advanceGeneration.setActionCommand("advance");
        advanceGeneration.addActionListener(new ActionListener() {
            // Listen and react to button press
            public void actionPerformed(ActionEvent event)
            {
                // Move on to the next generation of the game
                mainBoard.nextGeneration();
                // Update the board in the GUI accordingly
                updateBoard();
            }
        });
        // Set up constraints for advanceGeneration
        GridBagConstraints agConstraints = new GridBagConstraints();
        agConstraints.gridx = 0;
        agConstraints.gridy = 0;

        // Set up the button to reset the bord when pressed
        resetBoard = new JButton("Reset");
        resetBoard.setActionCommand("reset");
        resetBoard.addActionListener(new ActionListener() {
            // Set up listener to listen for and react to button press
            public void actionPerformed(ActionEvent event)
            {
                // Reset the board
                mainBoard.resetBoard();
                updateBoard();
            }
        });
        // Set up constraints for advanceGeneration
        GridBagConstraints rstConstraints = new GridBagConstraints();
        rstConstraints.gridx = 3;
        rstConstraints.gridy = 0;

        // Set up the randomize button
        randomize = new JButton("Randomize!");
        randomize.setActionCommand("randomize");
        randomize.addActionListener(new ActionListener() {
            // Listen and react to button press
            public void actionPerformed(ActionEvent event)
            {
                // Randomize the board
                mainBoard.randomizeBoard();
                updateBoard();
            }
        });
        // Set up constraints for advanceGeneration
        GridBagConstraints rngConstraints = new GridBagConstraints();
        rngConstraints.gridx = 1;
        rngConstraints.gridy = 1;

        //header = new JLabel("", JLabel.CENTER);
        gameName = new JLabel("Game of Life", JLabel.CENTER);
        gameName.setSize(250, 100);
        gameName.setFont(new Font("Verdana", Font.BOLD, 28));
        // Set up constraints for advanceGeneration
        GridBagConstraints gnConstraints = new GridBagConstraints();
        gnConstraints.gridx = 1;
        gnConstraints.gridy = 0;
        gnConstraints.fill = GridBagConstraints.HORIZONTAL;

        // Set up board size changer panel
        changeSizePanel = new JPanel();
        changeSizePanel.setLayout(new GridBagLayout());
        // Set up constraints for changeSizePanel
        GridBagConstraints cspConstraints = new GridBagConstraints();
        cspConstraints.gridx = 1;
        cspConstraints.gridy = 2;
        cspConstraints.fill = GridBagConstraints.BOTH;

        // Set up height label and spinner
        heightLabel = new JLabel("Height: ");
        GridBagConstraints hlConstraints = new GridBagConstraints();
        hlConstraints.gridx = 0;
        hlConstraints.gridy = 0;
        // Height spinner constraints
        GridBagConstraints bshConstraints = new GridBagConstraints();
        bshConstraints.gridx = 1;
        bshConstraints.gridy = 0;

        // Set up width label and spinner
        widthLabel = new JLabel("Width: ");
        GridBagConstraints wlConstraints = new GridBagConstraints();
        wlConstraints.gridx = 0;
        wlConstraints.gridy = 1;
        // Width spinner constraints
        GridBagConstraints bswConstraints = new GridBagConstraints();
        bswConstraints.gridx = 1;
        bswConstraints.gridy = 1;

        // Set up button to change size
        setSize = new JButton("Set size");
        setSize.setActionCommand("set size");
        setSize.addActionListener(new ActionListener() {
            // Listen and react to button press
            public void actionPerformed(ActionEvent event)
            {
                // Check if the triggering event is from the Set size button
                if (event.getActionCommand().equals("set size"))
                {
                    // Create a new board of the size given by the SpinnerModels
                    mainBoard = new Board(boardSize[0].getNumber().intValue(), boardSize[1].getNumber().intValue());
                    // Set up cell panels for each of the cells in mainBoard
                    cellPanels = new Cell[mainBoard.height()][mainBoard.width()];

                    // Set up the visual board
                    mainFrame.remove(boardPanel);                   // Remove board panel from main frame
                    setupBoardGUI();                                // Set up new boardPanel
                    mainFrame.add(boardPanel, boardConstraints);    // Re-add board panel to main frame
                    mainFrame.repaint();                            // Clear and repaint the canvas
                    mainFrame.setVisible(true);                     // Update mainFrame
                    updateBoard();
                }
            }
        });
        GridBagConstraints ssConstraints = new GridBagConstraints();
        ssConstraints.gridx = 0;
        ssConstraints.gridy = 2;
        ssConstraints.fill = GridBagConstraints.BOTH;

        // Add height and width labels and spinners as well as the set size button to the change size panel
        changeSizePanel.add(heightLabel, hlConstraints);
        changeSizePanel.add(boardSizeSpinner[0], bshConstraints);
        changeSizePanel.add(widthLabel, wlConstraints);
        changeSizePanel.add(boardSizeSpinner[1], bswConstraints);
        changeSizePanel.add(setSize, ssConstraints);

        // Add components to button panel
        buttonPanel.add(advanceGeneration, agConstraints);
        buttonPanel.add(gameName, gnConstraints);//, BorderLayout.NORTH);
        buttonPanel.add(resetBoard, rstConstraints);
        buttonPanel.add(randomize, rngConstraints);
        buttonPanel.add(changeSizePanel, cspConstraints);

        // Add components to main frame
        mainFrame.add(boardPanel, boardConstraints);//, BorderLayout.CENTER);
        mainFrame.add(buttonPanel, bpConstraints);
        mainFrame.setVisible(true);

        //System.out.println("Initial board state: \n" + mainBoard.toString());
    }

    public static void main(String[] args)
    {
        // Create an instance of the GUI class
        GUI guiStandalone = new GUI();
        guiStandalone.startGUI();
    }
}
