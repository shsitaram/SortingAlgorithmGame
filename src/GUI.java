import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The GUI class is responsible for creating the GUI and instantiating the Graph and SortSelector classes.
 * It should be run when starting the program.
 *
 * @author Shiv Sitaram
 * @version 1.0.0
 */
public final class GUI {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    /**
     * Constructor for the GUI. Sets up all the elements in the GUI.
     *
     * @param r the number of rounds to be played
     * @param elc the number of elements in the array
     */
    public GUI(int r, int elc) {
        if (elc < 2) {
            throw new IllegalArgumentException("The number of elements must be at least 2.");
        }
        if (r < 0) {
            throw new IllegalArgumentException("The number of rounds must be at least 0.");
        }

        final JFrame frame = new JFrame("Sorting Algorithm Visualizer");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel titleHolder = new JPanel();

        final JLabel title = new JLabel("Sorting Algorithm Visualizer");
        title.setFont(new Font("Monospace", Font.PLAIN, 20));

        titleHolder.add(title);

        final JPanel selectorAndGraph = new JPanel();
        selectorAndGraph.setLayout(new BorderLayout());

        final JPanel selectorPanel = new JPanel();
        selectorPanel.setBorder(new javax.swing.border.LineBorder(java.awt.Color.black));

        final JPanel graphPanel = new JPanel();

        selectorAndGraph.add(selectorPanel, BorderLayout.NORTH);
        selectorAndGraph.add(graphPanel, BorderLayout.CENTER);

        final Graph graph = new Graph(graphPanel);
        final SortSelector selector = new SortSelector(selectorPanel, graph, r, elc);

        frame.add(titleHolder, BorderLayout.NORTH);
        frame.add(selectorAndGraph, BorderLayout.CENTER);

        frame.setVisible(true);

        graph.handleResizing();
        selector.startRound();
    }

    /**
     * Main method to run the program.
     *
     * @param args command line arguments; the first element should be the number of rounds; the second element should be the number of values in the array every round
     */
    public static void main(String[] args) {
        new GUI(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}
