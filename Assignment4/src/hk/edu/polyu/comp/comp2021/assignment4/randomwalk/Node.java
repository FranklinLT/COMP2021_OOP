package hk.edu.polyu.comp.comp2021.assignment4.randomwalk;

import java.util.ArrayList;
import java.util.HashSet;

class Node{

    // degree of a node is the number of adjacency nodes, i.e., the number of nodes that are connected to this node by an edge.
    private int degree;
    //The graph this node belongs to
    private Graph graph;

    public Graph getGraph(){
        return this.graph;
    }

    public void setGraph(Graph graph){
        this.graph = graph;
    }

    // Task 1: Obtain the degree of this by referring to all the random walk sequences.
    public void setDegree(){
        HashSet<RandomWalkSequence> WalkList = graph.getAllRandomWalkSequences();
        HashSet<Node> adjacency_node = new HashSet<Node>();

        for(RandomWalkSequence element : WalkList)
        {
            ArrayList<Node> way = element.getSequence();
            if(way.contains(this))
            {
                int location = way.indexOf(this);
                if(location == 0) adjacency_node.add(way.get(1));
                else if(location == way.size() - 1) adjacency_node.add(way.get(location - 1));
                else{
                    adjacency_node.add(way.get(location - 1));
                    adjacency_node.add(way.get(location + 1));
                }
            }
        }
        this.degree =  adjacency_node.size();
    }

    public int getDegree(){return this.degree;}

    // Task 2: Given another node o, obtain the transition probability from this node to the given node.
    // transition probability is calculated by f(this, o) / f(this, all).
    // f(this, o) is the frequency of o as the next node of this within all random walk sequences.
    // f(this, all) is the frequency of this having a next node within all random walk sequences.
    // When f(this, all) = 0, the transition probability is 0.
    public double transitionProbability(Node o){
        if(this.graph != o.graph) return 0.0;

        double nestiso = 0.0, nestone = 0.0;
        HashSet<RandomWalkSequence> WalkList = graph.getAllRandomWalkSequences();

        for(RandomWalkSequence element : WalkList)
        {
            ArrayList<Node> way = element.getSequence();
            if(way.contains(this) && way.indexOf(this) != way.size() - 1)
            {
                nestone++;
                if(way.contains(o) && way.indexOf(this) + 1 == way.indexOf(o))
                {
                    nestiso++;
                }
            }
        }

        if (nestone == 0.0) return 0.0;
        return (double) (nestiso / nestone);
    }
}