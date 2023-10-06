/*
 * Name: Safin Rashid
 * EID: srr3288
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Program2 {

    int size, maxNode = 0;
    ArrayList<Integer> MST_parents;
    ArrayList<ArrayList<Integer>> MST_adjList, weights;
    ArrayList<ArrayList<Region>> neighbors;

    /**
     * findMinimumLength()
     * @param problem  - contains the regions of the graph.
     *
     * @return The sum of all the edges of the MST.
     *
     * @function Should track the edges in the MST using region.mst_neighbors and region.mst_weights
     *  This function will not modify the mst_lists when run Gradescope if called in calculateDiameter()
     */
    public int findMinimumLength(Problem problem) {

        problem.getRegions().get(0).setMinDist(0);
        Queue<Region> heap = new PriorityQueue<>(problem.getRegions());

        int length = 0;
        size = problem.getRegions().size();
        MST_parents = new ArrayList<Integer>();
        neighbors = new ArrayList<ArrayList<Region>>();
        weights = new ArrayList<ArrayList<Integer>>();
        MST_adjList = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < size; i++) MST_adjList.add(new ArrayList<Integer>());
        ArrayList<Integer> keys = new ArrayList<Integer>();
        boolean[] visited = new boolean[size];

        //initialize maintains
        for(int i = 0; i < size; i++){
            keys.add(Integer.MAX_VALUE);
            MST_parents.add(null);
            neighbors.add(problem.getRegions().get(i).getNeighbors());
            weights.add(problem.getRegions().get(i).getWeights());
        }

        keys.set(0,0); //entry

        for(int i = 0; i < size-1; i++){

            //get min weights not visited (extract-min)
            int min = Integer.MAX_VALUE, min_edge = 0;
            for(int j = 0; j < size; j++){
                if(! visited[j] && keys.get(j) < min){
                    min = keys.get(j);
                    min_edge = j;
                }
            }

            visited[min_edge] = true;

            //choose MST edge
            for(int k = 0; k < neighbors.get(min_edge).size(); k++){
                int n = neighbors.get(min_edge).get(k).getName(), w = weights.get(min_edge).get(k); //neighbor, weight of neighbors
                if(! visited[n] && keys.get(n) > w){ //unvisited and least weight
                    keys.set(n, w);
                    MST_parents.set(n, min_edge);
                }
            }
        }

        //Calculate length and make adj list
        for(int i = 1; i < size; i++) {//O(n)
            length += keys.get(i);
            MST_adjList.get(MST_parents.get(i)).add(i);
            MST_adjList.get(i).add(MST_parents.get(i));
        }

        //configure Problem MST from adj list
        for(int i = 0; i < MST_adjList.size(); i++){
            for(int j = 0; j < MST_adjList.get(i).size(); j++){
                Region n = problem.getRegions().get(MST_adjList.get(i).get(j));
                problem.getRegions().get(i).setMST_NeighborAndWeight(n, 1);
            }
        }

        //print_MST(length);

        return length;
    }

    private void print_MST(int length){
        System.out.println("Node -- Parent");
        for(int i = 0; i < MST_adjList.size(); i++){ //O(m+n)
            if(MST_adjList.get(i).size() == 0) continue;
            System.out.print(i + " --> ");
            for(int j = 0; j < MST_adjList.get(i).size(); j++)
                System.out.print(MST_adjList.get(i).get(j) + "  ");
            System.out.println();
        }
        System.out.println("minLength: " + length);
    }

    /**
     * calculateDiameter(Problem problem)
     *
     * @param problem  - contains the regions of the problem. Each region has an adjacency list
     *
     * @return the length of the longest path between any pair of nodes
     *
     * defined by mst_neighbors and mst_weights, which defines the provided MST.
     *
     */
    public int calculateDiameter(Problem problem) {

        findMinimumLength(problem); //O(mn)
//        lengthBFS(problem, 0);
//        return lengthBFS(problem, maxNode);

        int maxHeight = Integer.MIN_VALUE;
        for(int i = 0; i < size; i++){
            int nodeBFS = lengthBFS(problem, i);
            if(nodeBFS > maxHeight){
                maxHeight = nodeBFS;
            }
        }
        return maxHeight;


    }

    private int lengthBFS(Problem problem, int start){

        int height = 0, curr;
        int[] level = new int[size];
        boolean[] visited = new boolean[size];
        Queue<Integer> Q = new PriorityQueue<>();

        //set start of BFS
        visited[start] = true;
        level[start] = 0;
        Q.add(start);

        while (! Q.isEmpty()) { //O(n)
            curr = Q.poll(); //next on queue is visited

            ArrayList<Region> neighborsCurr = problem.getRegions().get(curr).getMST_Neighbors();

            for (int i = 0; i < neighborsCurr.size(); i++) { //for curr MST neighbors O(m)
                int child = neighborsCurr.get(i).getName();
                if (! visited[child]) { //neighbor is not visited
                    visited[curr] = true;
                    level[child] = level[curr] + 1; //neighbor level is one more than parent
                    if (level[child] >= height){ //increment level counter
                        height = level[child];
                        maxNode = child;
                    }
                    Q.add(child);
                }
            }
        }

        return height;
    }


}
