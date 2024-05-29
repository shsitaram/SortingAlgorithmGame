import java.awt.event.ActionEvent;

import java.util.Stack;

/**
 * The Quicksort class implements the <a href="https://en.wikipedia.org/wiki/Quicksort">Quicksort</a> algorithm.
 * It is a subclass of the SortingAlgorithm class.
 *
 * @author Shiv Sitaram
 * @version 1.0.0
 */
public final class Quicksort extends SortingAlgorithm {
    private int low;
    private int high;
    private int piv;
    private int i;
    private Stack<Integer> nextLow;
    private Stack<Integer> nextHigh;

    /**
     * Instantiates the quicksort class.
     *
     * @param graph the graph that contains the array
     */
    public Quicksort(Graph graph) {
        super(graph);
        low = 0;
        high = graph.size() - 1;
        piv = low;
        i = low + 1;
        nextLow = new Stack<>();
        nextHigh = new Stack<>();
        graph.select(piv);
    }

    /**
     * A method to perform a "step" of the quicksort process.
     *
     * @param e the event to be processed; not used
     */
    public void actionPerformed(ActionEvent e) {
        if (i > high) {
            if (piv + 1 < high) {
                nextLow.push(piv + 1);
                nextHigh.push(high);
            }
            if (low < piv - 1) {
                nextLow.push(low);
                nextHigh.push(piv - 1);
            }
            graph.unselect(piv);
            if (!nextLow.isEmpty()) {
                low = nextLow.pop();
                high = nextHigh.pop();
                piv = low;
                i = low + 1;
                graph.select(piv);
            }
            return;
        }
        if (graph.at(i) < graph.at(piv)) {
            graph.unselect(piv);
            graph.swap(piv, piv + 1);
            if (i != piv + 1) {
                graph.swap(i, piv);
            }
            graph.select(++piv);
        }
        i++;
        graph.resize();
    }
}
