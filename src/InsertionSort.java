import java.awt.event.ActionEvent;

/**
 * The InsertionSort class implements the <a href="https://en.wikipedia.org/wiki/Insertion_sort">Insertion Sort</a> algorithm.
 * It is a subclass of the SortingAlgorithm class.
 *
 * @author Shiv Sitaram
 * @version 1.0.0
 */
public final class InsertionSort extends SortingAlgorithm {
    private int i;
    private int next;

    /**
     * Instantiates the insertion sort class.
     *
     * @param graph the graph that contains the array
     */
    public InsertionSort(Graph graph) {
        super(graph);
        i = 1;
        next = 2;
        graph.select(i);
    }

    /**
     * A method to perform a "step" of the insertion sort process.
     *
     * @param e the event to be processed; not used
     */
    public void actionPerformed(ActionEvent e) {
        if (i == graph.size()) return;
        graph.unselect(i);
        if (i != 0 && graph.at(i) < graph.at(i - 1)) {
            graph.swap(i, i - 1);
            --i;
        } else {
            i = next++;
        }
        graph.select(i);
        graph.resize();
    }
}
