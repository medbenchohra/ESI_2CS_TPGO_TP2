package sample;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Graph {
    private int adjMat[][];
    public Graph(int nbNodes){
        adjMat = new int[nbNodes][nbNodes];
    }

    public int getAdjMat(int a, int b) {
        return adjMat[a][b];
    }

    public void setAdjMat(int a, int b, int val) {
        adjMat[a][b] = val;
    }
    public void logicLinkNodes(int a, int b){
        adjMat[(Math.max(a,b))][Math.min(a,b)]=1;
    }


    private ArrayList<Integer> dfs(int i){
        adjMat[0][i]=1;
        return null;
    }


}
