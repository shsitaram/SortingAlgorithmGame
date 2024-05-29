import java.awt.Color;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The SortSelector class manages the bar on top of the graph.
 * It also manages the general interaction with the game.
 *
 * @author Shiv Sitaram
 * @version 1.0.0
 */
public final class SortSelector {
    private static final Color CORRECT_COLOR = Color.green;
    private static final Color INCORRECT_COLOR = Color.red;
    private static final Color NORMAL_COLOR = Color.black;
    private static final Class<?>[] ALGORITHMS = new Class<?>[]{
            SelectionSort.class,
            InsertionSort.class,
            Quicksort.class,
    };
    private static final String[] ALGORITHM_NAMES = new String[]{
            "Selection Sort",
            "Insertion Sort",
            "Quicksort",
    };
    private final Graph graph;
    private Timer timer;
    private int answer;
    private int score;
    private final int rounds;
    private final int element_count;
    private int rounds_completed;
    private long sum_times;
    private long sum_all_times;
    private final JButton[] buttons;
    private long startTime;

    /**
     * Instantiates a SortSelector class.
     * It sets up the buttons for the sorting algorithms to be clicked when the user guesses an answer.
     *
     * @param parent the JPanel that contains the buttons
     * @param graph the graph that contains the Graph object.
     * @param rounds the number of rounds to be played.
     * @param element_count the number of elements in the array
     */
    public SortSelector(JPanel parent, Graph graph, int rounds, int element_count) {
        this.graph = graph;
        this.rounds = rounds;
        this.element_count = element_count;
        rounds_completed = 0;
        sum_times = 0;

        buttons = new JButton[ALGORITHMS.length];

        for (int i = 0; i < ALGORITHMS.length; ++i) {
            buttons[i] = new JButton(ALGORITHM_NAMES[i]);
            final int finalI = i;
            buttons[i].addActionListener(e -> respondToClick(finalI));
            parent.add(buttons[i]);
        }
    }

    /**
     * Starts a new round of the game. Creates a new array and randomly chooses a sorting algorithm.
     */
    public void startRound() {
        for (JButton button : buttons) {
            button.setForeground(NORMAL_COLOR);
        }

        final int[] array = new int[element_count];
        for (int i = 0; i < element_count; ++i) {
            array[i] = (int) (Math.random() * 1000000000) + 1;
        }
        graph.setToArray(array);

        answer = (int) (Math.random() * ALGORITHMS.length);
        try {
            timer = new Timer(200, (SortingAlgorithm) ALGORITHMS[answer].getConstructor(Graph.class).newInstance(graph));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        startTime = System.currentTimeMillis();
        timer.start();
    }

    /**
     * Responds to a click on a button. Updates the score, round number, and time taken.
     * It displays a message to the user (if they got it correct or incorrect).
     * If the game is over, an option pane opens to display the results. (If not, a new round is started.)
     *
     * @param algorithmNumber the algorithm number that was selected by the user
     */
    private void respondToClick(int algorithmNumber) {
        if (graph.isPaused()) return;

        final long endTime = System.currentTimeMillis();

        ++rounds_completed;

        buttons[algorithmNumber].setForeground(INCORRECT_COLOR);
        buttons[answer].setForeground(CORRECT_COLOR);

        timer.stop();
        if (algorithmNumber == answer) {
            ++score;
            graph.clearAndDisplay("Correct!", CORRECT_COLOR);
            sum_times += endTime - startTime;
        } else {
            graph.clearAndDisplay("Incorrect!", INCORRECT_COLOR);
        }

        sum_all_times += endTime - startTime;

        if (rounds_completed == rounds) {
            JOptionPane.showMessageDialog(null, "You got the answer correct on " + score + " out of " + rounds + " rounds. On average, it took you " + ((double) sum_times / rounds) + " milliseconds per round correct and " + ((double) sum_all_times / rounds) + " milliseconds per round overall. The number of elements in each array was " + element_count + ".");
            return;
        }

        Timer nrTimer = new Timer(2000, e -> startRound());
        nrTimer.setRepeats(false);
        nrTimer.start();
    }
}
