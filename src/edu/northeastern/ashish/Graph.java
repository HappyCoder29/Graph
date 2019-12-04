package edu.northeastern.ashish;

import java.util.*;

public class Graph {
    public HashMap<String, Node> nodes;

    public  Graph(){
        nodes = new HashMap<String, Node>();
        Initialize();
    }

    private void Initialize(){

        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node E = new Node("E");
        Node F = new Node("F");
        Node G = new Node("G");


        A.addEdge("B", 1);

        B.addEdge("C", 1);
        B.addEdge("E", 1);
        B.addEdge("D", 1);

        C.addEdge("E", 1);
        C.addEdge("A", 1);

        D.addEdge("E", 1);

        E.addEdge("F", 1);

        G.addEdge("E", 1);


        nodes.put("A", A);
        nodes.put("B", B);
        nodes.put("C", C);
        nodes.put("D", D);
        nodes.put("E", E);
        nodes.put("F", F);
        nodes.put("G", G);


    }

    public void AddNode(Node node){
        if( nodes.containsKey(node.name))
            return;
        nodes.put(node.name, node);

    }

    public void DeleteNode(Node node){
        if( ! nodes.containsKey(node.name))
            return;
        nodes.remove(node);

    }

    public void ResetVisited(){
        nodes.forEach((k,v) ->{
            v.visited = false;
        });
    }



    public void BreathFirstSearch(String startNode){

        if(!nodes.containsKey(startNode))
            return;

        ResetVisited();

        Queue<Node> queueNodes = new LinkedList<Node>();
        queueNodes.add(nodes.get(startNode));
        queueNodes.add(null);


        while(queueNodes.size() != 0){
            Node node = queueNodes.remove();
            if( node != null){
                if(!node.visited ){
                    System.out.printf(node.name + "->");
                    node.visited = true;
                }else{
                    continue;
                }
                List<String> neighbours = node.getNeighbours();

                for(int i = 0 ; i < neighbours.size(); i ++){
                    if(nodes.get(neighbours.get(i)).visited == false){
                        queueNodes.add(nodes.get(neighbours.get(i)));
                    }
                }
            }else{
                // node was null
                if(queueNodes.size() == 0 )
                    break;
                System.out.println("");
                queueNodes.add(null);
            }
        }
    }

    public void DepthFirstSearch(String startNode){
        if(!nodes.containsKey(startNode))
            return;

        ResetVisited();

        Stack<Node> stackNodes = new Stack<Node>();
        stackNodes.add(nodes.get(startNode));
        //stackNodes.add(null);


        while(stackNodes.size() != 0){
            Node node = stackNodes.pop();

            if(!node.visited ){
                System.out.printf(node.name + "->");
                node.visited = true;
            }else{
                continue;
            }
            List<String> neighbours = node.getNeighbours();

            for(int i = 0 ; i < neighbours.size(); i ++){
                if(nodes.get(neighbours.get(i)).visited == false){
                    stackNodes.push(nodes.get(neighbours.get(i)));
                }
            }
        }
    }


    public Boolean ContainsCycle(){

        Iterator iterator = nodes.entrySet().iterator();

        while(iterator.hasNext()){
            Map.Entry kvPair = (Map.Entry) iterator.next();
            Node value = (Node) kvPair.getValue();

            ResetVisited();
            Stack<Node> stackNodes = new Stack<Node>();
            stackNodes.push(value);
            System.out.println("Starting from " + value.name);
            while(stackNodes.size() != 0){

                Node node = stackNodes.pop();
                if(!node.visited ){
                    //System.out.printf(node.name + "->");
                    node.visited = true;
                }
                else if(node.visited && stackNodes.contains(node)){
                    return  true;
                }
                else{
                    continue;
                }
                List<String> neighbours = node.getNeighbours();

                for(int i = 0 ; i < neighbours.size(); i ++){
                    if(nodes.get(neighbours.get(i)).visited == false){
                        stackNodes.push(nodes.get(neighbours.get(i)));
                    }
                }
            }
        }// end of while
        return  false;

    }// end of function


    public  boolean isReachable(String startNode, String endNode){

        if(!nodes.containsKey(startNode) || !nodes.containsKey(endNode))
            return false;

        ResetVisited();

        Queue<Node> queueNodes = new LinkedList<Node>();
        queueNodes.add(nodes.get(startNode));



        while(queueNodes.size() != 0){
            Node node = queueNodes.remove();

            if(!node.visited ){
                System.out.printf(node.name + "->");
                node.visited = true;
            }


            List<String> neighbours = node.getNeighbours();
            for(int i = 0 ; i < neighbours.size(); i ++){

                if(nodes.get(neighbours.get(i)).name == endNode){
                    return  true;
                }

                if(nodes.get(neighbours.get(i)).visited == false){
                    queueNodes.add(nodes.get(neighbours.get(i)));
                }

            }

        }
        return  false;

    }


    public void printAllPaths(String src, String dest){
        if(!nodes.containsKey(src) || !nodes.containsKey(dest))
            return;
        HashSet<String> visited = new HashSet<String>();
        printAllPaths(visited, src, dest);


    }

    private void printAllPaths( HashSet<String> visited, String current, String dest){

            if(visited.contains(dest))
                return;
            if(dest == current){
                // print everything in the set
                for (String str : visited) {
                    System.out.printf(str + " -> ");
                }
                System.out.println(dest );
            }

            visited.add(current);

            Node node = nodes.get(current);

            List<Edge> edges = node.listEdges;

            for(int i = 0 ; i < edges.size(); i ++){
                if(! visited.contains(edges.get(i).endNode))
                    printAllPaths(visited, edges.get(i).endNode, dest);
            }
            visited.remove((current));
    }






}
