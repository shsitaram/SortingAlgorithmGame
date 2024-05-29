import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Graph controls the graph section of the GUI.
 * It is also responsible for handling the array and the Correct / Incorrect messages.
 *
 * @author Shiv Sitaram
 * @version 1.0.0
 */
public final class Graph {
    private final Color NORMAL_BAR_COLOR = Color.gray;
    private final Color SELECTED_BAR_COLOR = Color.red;
    private final JPanel parent;
    private int[] array;
    private JPanel[] barContainersSecondary;
    private JPanel[] bars;
    private boolean paused;

    /**
     * Instantiates the Graph class. It sets the array to the length of 0.
     *
     * @param parent a JPanel which contains the central graph
     */
    public Graph(JPanel parent) {
        array = new int[]{};
        this.parent = parent;
    }

    /**
     * Sets up the mechanism to handle resizing.
     */
    public void handleResizing() {
        parent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resize();
            }
        });
    }

    /**
     * Returns the element of the array at a certain index.
     *
     * @param idx the index of the element requested
     * @return the element
     */
    public int at(int idx) {
        if (paused) return -1;
        return array[idx];
    }

    /**
     * Returns the size of the array.
     *
     * @return the size of the array
     */
    public int size() {
        if (paused) return -1;
        return array.length;
    }

    /**
     * Reassigns the graph to a different array (the parameter).
     *
     * @param array the array that the Graph should be set to
     */
    public void setToArray(int[] array) {
        paused = false;

        this.array = array;

        parent.removeAll();

        parent.setLayout(new GridLayout(1, array.length));

        final JPanel[] barContainers = new JPanel[array.length];
        barContainersSecondary = new JPanel[array.length];
        bars = new JPanel[array.length];

        for (int i = 0; i < array.length; ++i) {
            barContainers[i] = new JPanel();

            barContainersSecondary[i] = new JPanel();
            barContainersSecondary[i].setLayout(new BorderLayout());

            bars[i] = new JPanel();
            bars[i].setBackground(NORMAL_BAR_COLOR);

            barContainersSecondary[i].add(bars[i], BorderLayout.SOUTH);
            barContainers[i].add(barContainersSecondary[i]);
            parent.add(barContainers[i]);
        }

        resize();
    }

    /**
     * Returns the maximum element of the array.
     *
     * @return the maximum element of the array
     */
    private int maxElement() {
        if (paused) return -1;

        int max = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    /**
     * Resizes the entire graph; recalculates all widths and heights of the bars.
     */
    public void resize() {
        if (paused) return;

        int max = maxElement();

        for (int i = 0; i < array.length; ++i) {
            barContainersSecondary[i].setPreferredSize(new Dimension(parent.getWidth()/array.length, parent.getHeight()));

            if (max == 0) {
                bars[i].setPreferredSize(new Dimension(parent.getWidth()/array.length, parent.getHeight()));
            } else {
                final double height = parent.getHeight() * ((double) (array[i]))/max;
                bars[i].setPreferredSize(new Dimension(parent.getWidth()/array.length, (int) Math.round(height)));
            }
            bars[i].revalidate();
            bars[i].repaint();
        }

        parent.revalidate();
        parent.repaint();
    }

    /**
     * Swaps the elements at two indices. The changes are not reflected on the graph itself.
     *
     * @param i1 the first index
     * @param i2 the second index
     */
    public void swap(int i1, int i2) {
        if (paused) return;

        int i1val = array[i1];
        array[i1] = array[i2];
        array[i2] = i1val;
    }

    /**
     * Selects a certain index of the array (highlights it in red).
     *
     * @param i the index to be selected
     */
    public void select(int i) {
        if (paused) return;

        if (i < bars.length && i >= 0) {
            bars[i].setBackground(SELECTED_BAR_COLOR);
        }
    }

    /**
     * Unselects a certain index of the array (turns its color to the normal gray).
     *
     * @param i the index to be unselected
     */
    public void unselect(int i) {
        if (paused) return;

        if (i < bars.length && i >= 0) {
            bars[i].setBackground(NORMAL_BAR_COLOR);
        }
    }

    /**
     * If the graph is paused, it is not displaying the array (it is displaying a message).
     *
     * @return whether the graph is paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Clears the graph to display a message. The color of the message can be specified.
     *
     * @param message the message to be displayed
     * @param fg the color of the message to be displayed
     */
    public void clearAndDisplay(String message, Color fg) {
        paused = true;

        parent.removeAll();

        parent.setLayout(new GridBagLayout());

        JLabel label = new JLabel("<html>" + message + "</html>");
        label.setFont(new Font("Monospace", Font.PLAIN, 50));
        label.setForeground(fg);

        parent.add(label);

        parent.revalidate();
        parent.repaint();
    }
}
