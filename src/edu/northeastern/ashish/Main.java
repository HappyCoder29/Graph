package edu.northeastern.ashish;

public class Main {

    public static void main(String[] args) {

        Graph graph = new Graph();
        //graph.topologicalSort();
        System.out.println(graph.IsHamiltonian());



        int INF = 100000;
//        Graph graph = new Graph();
//        graph.printAllPaths("A", "F");
        //System.out.println(graph.isReachable("A", "G"));

        //graph.DepthFirstSearch("A");


//        int graph[][] = new int[][]{    {INF,5,INF,INF},
//                                        {INF,INF,3,2},
//                                        {1,INF,INF,1},
//                                        {5,INF,2,INF}
//                                    };
//
//        int graphTranstive[][] = new int[][] {      {0,1,0,0},
//                                                    {0,0,1,0},
//                                                    {1,0,0,0},
//                                                    {0,0,0,0}
//                                            };
//        boolean[][] reach = transtive(graphTranstive);
//        printTranstive(reach);
//
//        int[][] dist = floydWarshall(graph);
//        printDist(dist);
    }

    private static void printDist(int[][] dist){
        int length = dist.length;
        for(int i = 0 ; i < length; i ++){
            for(int j = 0 ; j < length; j ++){
                System.out.printf(dist[i][j] + "\t");
            }
            System.out.println("");
        }

    }

    private static void printTranstive(boolean[][] reach){
        int length = reach.length;
        for(int i = 0 ; i < length; i ++){
            for(int j = 0 ; j < length; j ++){
                if(reach[i][j])
                    System.out.printf("T\t");
                else
                    System.out.printf("F\t");
            }
            System.out.println("");
        }

    }

    private static  int[][] floydWarshall(int[][] graph){

        int length = graph.length;
        int[][] dist = new int[length][length];

        for(int i = 0 ; i < length; i ++){
            for(int j = 0 ; j < length; j ++){
                dist[i][j] = graph[i][j];
            }
        }

        for(int k = 0 ; k < length; k ++) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    // if there is a K for which (i,k) + (k,j) < (i,j)
                    if (i == j)
                        continue;
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        return dist;
    }


    private static boolean[][] transtive(int[][] graph){
        int length = graph.length;
        boolean[][] reach = new boolean[length][length];

        // initialize the reach matrix
        for(int i = 0 ; i < length; i ++){
            for(int j = 0 ; j < length; j ++){
                if( graph[i][j] == 1)
                    reach[i][j] = true;
                else
                    reach[i][j] = false;
            }
        }


        for(int k = 0 ; k < length; k ++) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {

                    reach[i][j] = reach[i][j] || (reach[i][k] && reach[k][j]);

                }
            }
        }

        return  reach;
    }



}
