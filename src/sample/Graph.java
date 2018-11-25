package sample;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Graph {
    private int adjMat[][];

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int size;
    private int nbVisitedNodes=0;
    public Graph(int nbNodes){
        adjMat = new int[nbNodes][nbNodes];
        size=nbNodes;
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


    public int dfs(int i){
        int k=0;
        int temp =-1;
        adjMat[0][i]=2;
        System.out.println("proceeding for dfs for node"+i);
        if (i==0){
        for (k=0; k<size; k++){
                System.out.println("CI : adjmath["+k+"]["+i+"] and adjmath[0]["+k+"]"+adjMat[k][i]+";"+adjMat[0][k]);
                if ((adjMat[k][i]==1)&&(k!=i)&&adjMat[0][k]!=2){
                    System.out.println("first node encountred"+k);
                    temp = k;
                    break;
                    }
            }
                for (k=0; k<size; k++){
                    System.out.println("CI : adjmath["+k+"]["+i+"] and adjmath[0]["+k+"]"+adjMat[k][i]+";"+adjMat[0][k]);
                    if ((adjMat[k][i]==1)&&(k!=i)&&adjMat[0][k]!=2){
                        System.out.println("reaching node"+k+"from"+i);
                        nbVisitedNodes++;
                        dfs(k);
                    }
        }}
        else {for (k=0; k<size; k++){
            System.out.println("CI : adjmath["+i+"]["+k+"] and adjmath[0]["+k+"]"+adjMat[k][i]+";"+adjMat[0][k]);
            if ((adjMat[i][k]==1)&&(k!=i)&&adjMat[0][k]!=2){
                System.out.println("first node encountred"+k);
                temp = k;
                break;
            }
        }
            for (k=0; k<size; k++){
                System.out.println("CI : adjmath["+i+"]["+k+"] and adjmath[0]["+k+"]"+adjMat[k][i]+";"+adjMat[0][k]);
                if ((adjMat[i][k]==1)&&(k!=i)&&adjMat[0][k]!=2){
                    System.out.println("reaching node"+k+"from"+i);
                    nbVisitedNodes++;
                    dfs(k);
                }
            }}
        System.out.println(temp+" is the first neighbor for "+i);
        return temp;
    }
    public void dfsWithout(int i,int m){
        int k=0;
        adjMat[0][i]=2;
        System.out.println("proceeding for dfs for node"+i+"without"+m);
        if (i==0)
        {for (k=0; k<size; k++){
                System.out.println("CI : adjmath["+k+"]["+i+"] and adjmath[0][k]"+adjMat[k][i]+";"+adjMat[0][k]);
                if ((adjMat[k][i]==1)&&(k!=i)&&(k!=m)&&adjMat[0][k]!=2){
                    System.out.println("reaching node"+k+"from"+i);
                    nbVisitedNodes--;
                    System.out.println("number decremented reaching"+nbVisitedNodes);
                    dfsWithout(k,m);
        }}}
        else {
            for (k=0; k<size; k++){
                System.out.println("CI : adjmath["+i+"]["+k+"] and adjmath[0][k]"+adjMat[i][k]+";"+adjMat[0][k]);
                if ((adjMat[i][k]==1)&&(k!=i)&&(k!=m)&&adjMat[0][k]!=2){
                    System.out.println("reaching node"+k+"from"+i);
                    nbVisitedNodes--;
                    System.out.println("number decremented reaching"+nbVisitedNodes);
                    dfsWithout(k,m);
                }
        }

            }
        }
    public void printDfsResult(){
        System.out.println("number of visited nodes is"+nbVisitedNodes+" and they are");
        for (int i=0; i<size; i++){
            if (adjMat[0][i]==2) System.out.print(i+" ;");
            else System.out.print("no one ; ");
        }
    }

    public int getNbVisitedNodes() {
        return nbVisitedNodes;
    }

    public void setNbVisitedNodes(int nbVisitedNodes) {
        this.nbVisitedNodes = nbVisitedNodes;
    }

    public boolean articulatePoint(int i){
        nbVisitedNodes = 0;
        for (int c=0; c<size; c++)adjMat[0][c]=0;
        int temp = dfs(i);
        printDfsResult();
        for (int c=0; c<size; c++)adjMat[0][c]=0;
        if (temp !=-1) {
            dfsWithout(temp, i);
            printDfsResult();
            return nbVisitedNodes != 1;
        }
        else return false;
    }


}
