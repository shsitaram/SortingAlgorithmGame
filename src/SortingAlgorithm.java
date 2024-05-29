import java.awt.event.ActionListener;

/**
 * The SortingAlgorithm class is the superclass for all sorting algorithms to be performed on the graph.
 * It functions as an ActionListener.
 *
 * @author Shiv Sitaram
 * @version 1.0.0
 */
public abstract class SortingAlgorithm implements ActionListener {
    /**
     * The graph that the sorting algorithm will be run on.
     * Holds the array to be sorted.
     */
    protected final Graph graph;

    /**
     * Instantiates a sorting algorithm with a graph.
     *
     * @param graph the graph that the sorting algorithm will be run on
     */
    public SortingAlgorithm(Graph graph) {
        this.graph = graph;
    }
}
