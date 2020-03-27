package edu.northeastern.ashish;

import java.util.LinkedList;
import java.util.List;

public class Node {

    private Node(){}
    public String name;
    public boolean visited;
    public int rank;
    public Node parent;

    public List<Edge>  listEdges = null;

    public Node(String name){
        this.name = name;
        this.listEdges = new LinkedList<Edge>();
        this.visited = false;
        this.rank = 0;
        this.parent = this;
    }

    public void addEdge(String endNode, int weight ){
        // check if the edge already exist
        for(int i = 0; i < listEdges.size(); i ++){
            if(listEdges.get(i).endNode == endNode)
                return;
        }
        Edge edge = new Edge(this.name, endNode, weight);

        listEdges.add(edge);
    }

    public void deleteEdge(String endNode ){
        // check if the edge already exist
        for(int i = 0; i < listEdges.size(); i ++){
            if(listEdges.get(i).endNode == endNode){
                listEdges.remove(i);
            }
        }
    }

    public List<String> getNeighbours(){
        List<String> neighbours = new LinkedList<String>();

        for(int i = 0 ; i < listEdges.size(); i ++){
            neighbours.add(listEdges.get(i).endNode);
        }
        return  neighbours;
    }

    public List<Edge> getListEdges(){
        List<Edge> neighbours = new LinkedList<Edge>();

        for(int i = 0 ; i < listEdges.size(); i ++){
            neighbours.add(listEdges.get(i));
        }
        return  neighbours;
    }

    public void setVisited(Boolean visited){
        this.visited = visited;
    }

}
