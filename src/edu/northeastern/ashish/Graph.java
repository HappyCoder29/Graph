package edu.northeastern.ashish;

import java.sql.Ref;
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

        A.addEdge("B",1);
        B.addEdge("C",1);
        C.addEdge("D",1);
        D.addEdge("A",1);

//        Node E = new Node("E");
//        Node F = new Node("F");


//        A.addEdge("B", 1);
//        A.addEdge("C", 1);
//
//        B.addEdge("D", 1);
//        C.addEdge("E",1);
//        C.addEdge("F", 1);
//
//        E.addEdge("F",1 );
////
//        A.addEdge("B", 1);
//        B.addEdge("C", 1);
//        C.addEdge("D",1);
//        D.addEdge("A", 1);
//
//        Node E = new Node("E");
//        Node F = new Node("F");
//        Node G = new Node("G");
//
//
//        A.addEdge("B", 1);
//        A.addEdge("C",1);
//
//
////        B.addEdge("C", 1);
////        B.addEdge("E", 1);
//        B.addEdge("D", 1);
//
//        C.addEdge("D", 1);
//        C.addEdge("E", 1);
//
//        D.addEdge("F", 1);
//
//        E.addEdge("F", 1);
//        F.addEdge("G", 1);
//
//       // G.addEdge("G", 1);
//

        nodes.put("A", A);
        nodes.put("B", B);
        nodes.put("C", C);
        nodes.put("D", D);
//        nodes.put("E", E);
//        nodes.put("F", F);
//        nodes.put("G", G);


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

    public boolean   IsHamiltonian()
    {
        RefStore<List<String>>  result = new RefStore<List<String>>();
        result.value = new LinkedList<>();
        boolean bHamiltonian = HamiltonianCycle(result);

        if (bHamiltonian)
        {
            for (int i = 0; i < result.value.size(); i++)
            {
                System.out.print(result.value.get(i) + " ");
            }
            System.out.println();

        }
        return bHamiltonian;
    }

    private boolean HamiltonianCycle(RefStore<List<String>> result)
    {
        Map.Entry<String,Node> entry = nodes.entrySet().iterator().next();

        String startNode = entry.getKey();
        HashSet<String> visited = new HashSet<String>();
        return HamiltonianUtil(startNode, startNode, result, visited);

    }

    private boolean HamiltonianUtil(String startNode, String currentNode, RefStore<List<String> >result,
                                    HashSet<String> visited){

        visited.add(currentNode);
        result.value.add(currentNode);

        List<Edge> edges = nodes.get(currentNode).getListEdges();
        for (int i = 0; i < edges.size(); i++)
        {
            if (startNode == edges.get(i).endNode && nodes.size() == result.value.size())
            {
                result.value.add(startNode);
                return true;
            }
            if (!visited.contains(edges.get(i).endNode))
            {
                boolean isHamil = HamiltonianUtil(startNode, edges.get(i).endNode, result, visited);
                if (isHamil)
                {
                    return true;
                }
            }
        }
        result.value.remove(result.value.size() - 1);
        visited.remove(currentNode);
        return false;
    }


    public void topologicalSort(){
        Stack  <String> stack = new Stack<>();
        HashSet<String> visited = new HashSet<>();

        for(Map.Entry<String,Node> entry: nodes.entrySet()){

            if(!visited.contains(entry.getKey())){
                topologicalSort( entry.getKey(), visited, stack);
            }

        }

        while(stack.size()!= 0){
            System.out.print(stack.pop() + "->");
        }
        System.out.println();

    }

    private  void topologicalSort(String nodeName, HashSet<String> visited, Stack<String> stack){

        visited.add(nodeName);

        List<Edge> edges = nodes.get(nodeName).getListEdges();

        for(int i = 0 ; i < edges.size(); i ++){
            if(!visited.contains(edges.get(i).endNode)){
                topologicalSort(edges.get(i).endNode, visited, stack);
                stack.push(nodeName);
            }

        }
    }

    public Node findParent(Node node){
        Node parent = node.parent;

        if(parent == node){
            return node;
        }
        node.parent = findParent(node.parent);
        return  node.parent;
    }

    public void union(Node node1, Node node2){
        if(! nodes.containsKey(node1.name) || ! nodes.containsKey(node2.name) ){
            return;
        }
        Node parent1 = findParent(node1);
        Node parent2 = findParent(node2);

        if(parent1 == parent2)
            return;

        if(parent1.rank >= parent2.rank){
            if(parent1.rank == parent2.rank)
                parent1.rank++;
            parent2.parent = parent1;
        }else{
            parent1.parent = parent2;
        }

    }

    // Just for testingDisjoint sets does not always work in Directed graph
    public  boolean isCyclic(){

        List<Edge> allEdges = new LinkedList<>();
        for (Map.Entry<String, Node> entry: nodes.entrySet() ){

            Node node = entry.getValue();

            for (Edge edge: node.getListEdges()) {
                allEdges.add(edge);
            }

        }

        for (Edge edge : allEdges) {
            Node start = nodes.get(edge.startNode);
            Node end = nodes.get(edge.endNode);
            if( findParent(start) == findParent(end) ){
                return   true;
            }
            union(start, end);
        }

        return false;
    }
}
