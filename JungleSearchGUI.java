// Andi Muhammmad Imam Akbar
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
// Constructing the backbone of the program
public class JungleSearchGUI extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel, controlPanel, mapPanel;
    private JButton searchButton;
    private JTextArea searchResults;
    private int numVertices, numEdges, numPeople, startVertex;
    private int[][] adjacencyMatrix;
    private boolean[] visited;
    private ArrayList<Integer> peopleLocations;

    // Sets the GUI Layout
  
    public JungleSearchGUI() {
        setTitle("Jungle Search Simulator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        searchButton = new JButton("Search for People");
        searchButton.addActionListener(this);
        controlPanel.add(searchButton);

        searchResults = new JTextArea(10, 50);
        searchResults.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(searchResults);

        controlPanel.add(scrollPane);

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        mapPanel = new JPanel();
        mainPanel.add(mapPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }
    // Creating the graph and its corresponding calculations
    public void generateGraph() {
        numVertices = (int) (Math.random() * 10) + 5; // Generate random number of vertices between 5 and 14
        numEdges = (int) (Math.random() * (numVertices * (numVertices - 1) / 2)); // Generate random number of edges between 0 and n(n-1)/2
        adjacencyMatrix = new int[numVertices][numVertices];
        visited = new boolean[numVertices];

        for (int i = 0; i < numEdges; i++) {
            int u = (int) (Math.random() * numVertices);
            int v = (int) (Math.random() * numVertices);
            if (u != v && adjacencyMatrix[u][v] == 0 && adjacencyMatrix[v][u] == 0) {
                adjacencyMatrix[u][v] = (int) (Math.random() * 10) + 1; // Generate random weight between 1 and 10
                adjacencyMatrix[v][u] = adjacencyMatrix[u][v];
            }
        }

        numPeople = (int) (Math.random() * (numVertices / 2)) + 1; // Generate random number of people between 1 and n/2
        peopleLocations = new ArrayList<>();
        for (int i = 0; i < numPeople; i++) {
            int location = (int) (Math.random() * numVertices);
            peopleLocations.add(location);
        }

        startVertex = (int) (Math.random() * numVertices);
    }

    public void drawGraph() {
        mapPanel.removeAll();

        int radius = 150;
        int centerX = mapPanel.getWidth() / 2;
        int centerY = mapPanel.getHeight() / 2;

        // Draw vertices
        for (int i = 0; i < numVertices; i++) {
            int x = (int) (centerX + radius * Math.cos(i * 2 * Math.PI / numVertices));
            int y = (int) (centerY + radius * Math.sin(i * 2 * Math.PI / numVertices));
            JLabel vertexLabel = new JLabel(Integer.toString(i));
            vertexLabel.setBounds(x - 10, y - 10, 20, 20);
            vertexLabel.setHorizontalAlignment(SwingConstants.CENTER);
            vertexLabel.setVerticalAlignment(SwingConstants.CENTER);
            mapPanel.add(vertexLabel);
        }
    
        // Draw edges
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (adjacencyMatrix[i][j] > 0) {
                    int x1 = (int) (centerX + radius * Math.cos(i * 2 * Math.PI / numVertices));
                    int y1 = (int) (centerY + radius * Math.sin(i * 2 * Math.PI / numVertices));
                    int x2 = (int) (centerX + radius * Math.cos(j * 2 * Math.PI / numVertices));
                    int y2 = (int) (centerY + radius * Math.sin(j * 2 * Math.PI / numVertices));
                    Graphics g = mapPanel.getGraphics();
                    g.drawLine(x1, y1, x2, y2);
                    JLabel edgeLabel = new JLabel(Integer.toString(adjacencyMatrix[i][j]));
                    edgeLabel.setBounds((x1 + x2) / 2 - 10, (y1 + y2) / 2 - 10, 20, 20);
                    edgeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    edgeLabel.setVerticalAlignment(SwingConstants.CENTER);
                    mapPanel.add(edgeLabel);
                }
            }
        }
    
        // Highlight start vertex
        JLabel startVertexLabel = (JLabel) mapPanel.getComponent(startVertex);
        startVertexLabel.setBackground(Color.GREEN);
        startVertexLabel.setOpaque(true);
    
        mapPanel.revalidate();
        mapPanel.repaint();
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchResults.setText("");
            generateGraph();
            drawGraph();
            searchDFS(startVertex);
        }
    }
    
    public void searchDFS(int vertex) {
        visited[vertex] = true;
    
        if (peopleLocations.contains(vertex)) {
            searchResults.append("Person found at vertex " + vertex + "\n");
        }
    
        for (int i = 0; i < numVertices; i++) {
            if (adjacencyMatrix[vertex][i] > 0 && !visited[i]) {
                searchDFS(i);
            }
        }
    }
    // the "simple" part of the program.
    public static void main(String[] args) {
        JungleSearchGUI gui = new JungleSearchGUI();
        gui.setVisible(true);
    }
}