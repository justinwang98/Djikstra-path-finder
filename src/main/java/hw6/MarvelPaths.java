package hw6;

import hw3.Graph;
import hw3.Graph.GraphEdge;
import hw3.Graph.GraphNode;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * This is not an abstract data type
 */
public class MarvelPaths {

  /**
   * Runs the command line
   * @param args arguments passed in
   * @throws MarvelParser.MalformedDataException if the file is not well-formed: each line contains
   *     exactly two * tokens separated by a tab, or else starting with a # symbol to indicate a
   *     comment line
   */
  public static void main(String[] args) throws MarvelParser.MalformedDataException {
      Scanner reader = new Scanner(System.in, UTF_8.name());
        String char1 = reader.nextLine();
        String char2 = reader.nextLine();
        MarvelPaths(char1, char2, loadGraph("marvel.tsv"));
    }

  /**
   * Creates a graph based upon the tsv file passed in, mapping characters to each over in every
   * book they have been in
   *
   * @param fileName file to be processed
   * @return a graph based upon the the file
   * @throws MarvelParser.MalformedDataException if the file is not well-formed: each line contains
   *     exactly two * tokens separated by a tab, or else starting with a # symbol to indicate a
   *     comment line
   */
  public static Graph<String> loadGraph(String fileName) throws MarvelParser.MalformedDataException {
        Graph<String> graph = new Graph<String>();
        String file = "./src/main/java/hw6/data/" + fileName;
        Set<String> chars = new HashSet<String>();
        Map<String, List<String>> book = new HashMap<String, List<String>>();
        MarvelParser.parseData(file, chars, book);
        for (String character : chars) {
            graph.add(new GraphNode(character));
        }
        Iterator<String> iter2 = book.keySet().iterator();
        for (String bookName : book.keySet()) { // loop over every book
            List<String> charsInBook = book.get(bookName);
            for (String character : charsInBook) { // loop over every character in book
                for  (String otherChar : charsInBook) { // loop over every other char in book
                    if (!character.equals(otherChar)) {
                        GraphNode temp = graph.get(otherChar);
                        if (temp != null) {
                            GraphNode temp2 = graph.get(character);
                            if (temp2 != null) {
                                temp2.add(new GraphEdge(temp, bookName)); // add edge between src and dest node
                            }
                        }
                    }
                }
            }
        }
        return graph;
    }

    /**
     * Returns the shortest path between the node at char1 and the node char2
     * @param char1 string for src node
     * @param char2 string for dest node
     * @param graph graph to do operation
     * @return A list of Graphedges that represents the shortest path between two nodes,
     * if no path is found returns null
     */
    public static @Nullable List<GraphEdge> MarvelPaths(String char1, String char2, Graph<String> graph) {
        Graph.GraphNode start = graph.get(char1);
        Graph.GraphNode dest = graph.get(char2);
        List<Graph.GraphNode> queue = new ArrayList<Graph.GraphNode>(); // queue
        Map<Graph.GraphNode, ArrayList<Graph.GraphEdge>> map = new HashMap<Graph.GraphNode, ArrayList<Graph.GraphEdge>>();
        if (start != null) {
            queue.add(start);
            map.put(start, new ArrayList<>()); // start mapped to empty list
        }
        while (!queue.isEmpty()) {
            Graph.GraphNode curr = queue.remove(0);
            if (curr.equals(dest)) {
                return map.get(curr);
            }

            Map<String, Graph.GraphEdge> sorted = new TreeMap<String, Graph.GraphEdge>(curr.getEdges()); // sorts the hashset alphabetically
//            Set<GraphEdge> sorted = new TreeSet<GraphEdge>(new Comparator<GraphEdge>() {
//                @Override
//                public int compare(GraphEdge o1, GraphEdge o2) {
//                    if(o1.getDestination().equals(o2.getDestination())) { // test dest equality
//                        if (o1.getLabel().equals(o2.getLabel())) {
//                            return 0;
//                        }
//                        return comparison between labels
//                    }
//                    return comparion between dest
//                }
//
//                int temp = o1.getDestination().compareTo(o2.getDestination());
//
//                if (temp != 0) {
//                    return temp;
//                } else {
//                    return o1.getLabel().compareTo(o2.getLabel());
//                }
//
//            });
                for (String e : sorted.keySet()) {
                    Graph.GraphNode destination = sorted.get(e).getDestination();
                    if (!map.containsKey(destination)) {
                        ArrayList<Graph.GraphEdge> temp = map.get(curr);
                        if (temp != null) {
                            ArrayList<Graph.GraphEdge> path = new ArrayList<>(temp);
                            path.add(sorted.get(e));
                            map.put(destination, path);
                            queue.add(destination);
                        }
                    }
                }
            }
            return null;
        }
}
