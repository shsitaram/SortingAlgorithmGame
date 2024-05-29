import java.awt.event.ActionEvent;

/**
 * The Selection Sort class implements the <a href="https://en.wikipedia.org/wiki/Selection_sort">Selection Sort</a> algorithm.
 * It is a subclass of the SortingAlgorithm class.
 *
 * @author Shiv Sitaram
 * @version 1.0.0
 */
public final class SelectionSort extends SortingAlgorithm {
    private int i;
    private int j;
    private int minIdx;

    /**
     * Instantiates the selection sort class.
     *
     * @param graph the graph that contains the array
     */
    public SelectionSort(Graph graph) {
        super(graph);
        i = 0;
        j = 1;
        minIdx = 0;
        graph.select(1);
    }

    /**
     * A method to perform a "step" of the selection sort process.
     *
     * @param e the event to be processed; not used
     */
    public void actionPerformed(ActionEvent e) {
        if (i == graph.size()) return;
        if (j < graph.size()) {
            if (graph.at(j) < graph.at(minIdx)) {
                minIdx = j;
            }
            graph.unselect(j);
            graph.select(++j);
        }
        if (j == graph.size()) {
            if (i != minIdx) {
                graph.swap(i, minIdx);
            }
            i++;
            j = i + 1;
            minIdx = i;
            graph.select(j);
        }
        graph.resize();
    }
}
