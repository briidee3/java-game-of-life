import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// Set up an inner class to represent and handle cells.
//  This class was created as an inner class in the GUI class because the Cell class needs access to the mainBoard object
//  used and referenced within the GUI class.
public class Cell extends JPanel
{
    // Store the indices of the cell for use referencing the actual cell in mainBoard
    private int i;
    private int j;
    private boolean state;
    private JPanel circle;
    private Board mainBoard;

    // Constructor for a cell, given the indices of that cell
    public Cell(int i, int j, Board mainBoard, int cellSize)
    {
        this.i = i;
        this.j = j;
        this.mainBoard = mainBoard;

        // Set the size and location of top left corner of cell relative to its container
        this.setBounds(0, 0, cellSize, cellSize);
        // Set the layout of the Cell
        //this.setLayout(new BorderLayout(10, 10));
        this.setLayout(new GridLayout());

        // Set the background of the cell, alternating first by row then by column;
        //  i.e. the first cell of each row should be alternating in color, then the next cells
        //  in the row should be alternating in color, so as to create a checkerboard pattern
        switch ( (this.i % 2) + ((this.j % 2) * 2) )
        {
            // Both i and j are even
            case 0:
            {
                this.setBackground(Color.lightGray);
                break;
            }
            // i is odd, j is even
            case 1:
            {
                this.setBackground(Color.gray);
                break;
            }
            // i is even, j is odd
            case 2:
            {
                this.setBackground(Color.gray);
                break;
            }
            // Both i and j are odd
            case 3:
            {
                this.setBackground(Color.lightGray);
                break;
            }
            default:
            {
                this.setBackground(Color.black);
                break;
            }
        }

        // Set up the inner panel holding the circle
        circle = new JPanel() {
            // Draw a circle within the new JPanel
            public void paintComponent(Graphics g) {
                // Paint a black circle within the circle JPanel
                g.fillOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
            }
        };
        circle.setOpaque(false);    // Make background of circle transparent
        circle.setVisible(false);   // Make circle invisible
        this.add(circle);           // Add the circle to the Cell JPanel

        // Add listener for mouse events
        this.addMouseListener(new MouseListener() {
            // Handle a mouse click on the given cell
            public void mousePressed(MouseEvent event)
            {
                // Flip the state of the cell
                setState(!state);
            }

            // The next several methods are required for MouseListeners,
            //  however since we only plan to use mousePressed, they have been left empty
            public void mouseClicked(MouseEvent event) { }

            public void mouseReleased(MouseEvent event) { }

            public void mouseEntered(MouseEvent event) { }

            public void mouseExited(MouseEvent event) { }
        });
    }

    // Set the state of the cell manually
    public void setState(boolean state)
    {
        // Set the state of the cell in the Board
        mainBoard.setCell(i, j, state);
        // Update the state of the cell in this Cell
        updateCell();

        //System.out.println(String.format("mainBoard[%d][%d]: %d", i, j, state ? 1 : 0));
    }

    // Update the state of the cell to the state of the corresponding boolean in mainBoard
    public void updateCell()
    {
        // If getCell returns something other than 0, then this cell's state is true; otherwise, it's false
        state = mainBoard.getCell(i, j) != 0;

        // Change the visibility of the circle to match the state of the cell
        circle.setVisible(state);
    }
}
