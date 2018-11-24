package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public ScrollPane getScroll() {
        return scroll;
    }


    public void setScroll(ScrollPane scroll) {
        this.scroll = scroll;
    }

    @FXML
    private ScrollPane scroll;


    public AnchorPane getBackground() {
        return background;
    }

    public void setBackground(AnchorPane background) {
        this.background = background;
    }

    @FXML
    public AnchorPane background ;
    @FXML
    private JFXButton validate;
    private static int nbNodes = 0;
    private static boolean src=true;
    private static boolean arrangeable=true;
    private static int lineSrc =0;
    private static int lineDes =0;
    private static boolean validated = false;
    private Graph graph;
    @FXML
    private void validateGraph(){
        graph = new Graph(nbNodes);
        System.out.println("surprise \n "+graph);
        validated = true;

    };
    @FXML
    private void GraphiclinkTwoNodes(Circle newCircle, boolean linkeable){
        if (linkeable){arrangeable=false;
            if (src) {
                System.out.println("proceding to creat Link");
                lineSrc=graphicToLogicNode(background.getChildren().indexOf(newCircle));
                System.out.println("step1"+lineSrc+"\n");
                Line newLine = new Line();
                newLine.setStartX(newCircle.getCenterX());
                newLine.setStartY(newCircle.getCenterY());
                newLine.setManaged(true);
                newLine.setDisable(false);
                newLine.setVisible(true);
                newLine.setFill(new Color(0,0,0,1));
                background.getChildren().add(newLine);
                System.out.println("new line created");
                background.setOnMouseMoved(event2 -> {
                    newLine.setEndX(event2.getScreenX());
                    newLine.setEndY(event2.getScreenY());
                    if (background.getChildren().indexOf(newLine)!= -1)
                    background.getChildren().set(background.getChildren().indexOf(newLine),newLine);

                });
            } else {
                lineDes=graphicToLogicNode(background.getChildren().indexOf(newCircle));
                System.out.println(graphicToLogicNode(background.getChildren().indexOf(newCircle))+"//////"+ graphicToLogicNode(background.getChildren().indexOf(newCircle))+ "\n \n \n ***");
                System.out.println(((graph.getAdjMat(Math.max(lineSrc,lineDes),Math.min(lineSrc,lineDes)))==0) +" /* "+Math.max(lineSrc,lineDes)+" /*"+Math.min(lineSrc,lineDes));

                if ((graph.getAdjMat(Math.max(lineSrc,lineDes),Math.min(lineSrc,lineDes))==0)&&(lineSrc!= lineDes))
                {
                    background.setOnMouseMoved(event -> {});

                System.out.println("step1"+lineSrc+"\n step2"+lineDes+"\n");
                    graph.logicLinkNodes(lineSrc,lineDes);
                    System.out.println("done !!!");
                Line existingLine ;
                int a= background.getChildren().size();
                    System.out.println(background.getChildren().get(a-1));
                existingLine= (Line)background.getChildren().get(a-1);
                existingLine.setEndX(newCircle.getCenterX());
                existingLine.setEndY(newCircle.getCenterY());
                existingLine.setManaged(false);
                    System.out.println(existingLine.getStartX()+existingLine.getEndX()+"what does it mean ? ");
                background.getChildren().set(a-1,existingLine);
                    graph.setAdjMat(lineSrc,lineDes,1);
                    graph.setAdjMat(lineDes,lineSrc,1);
                    System.out.println(graph.getAdjMat(Math.max(lineSrc,lineDes),Math.min(lineSrc,lineDes)));

                }
                else {
                    int a= background.getChildren().size();
                    System.out.println(background.getChildren().get(a-1));
                    background.getChildren().remove(a-1);

                }


            }
        src = (!src) ;

    }
    }
    @FXML
    public  int logicToGraphicNode(int i){
        int l =0, k=0;
        while (l < background.getChildren().size()) {
            if (background.getChildren().get(l) instanceof Circle) {
                if (k==i) break;
                k++;
            }
            l++;
        }
        return l;
    }
    @FXML
    public int graphicToLogicNode(int i){
        int l =0, k=0;
        while (l < background.getChildren().size()) {
            if (background.getChildren().get(l) instanceof Circle) {
                if (l==i) break;
                k++;
            }
            l++;
        }
        return k;
    }
    @FXML
    private void createNode(double x, double y){
        Circle newCircle = new Circle();
        newCircle.setCenterX(x);
        newCircle.setCenterY(y);
        newCircle.setRadius(15);
        newCircle.setFill(new Color(0.1,1,0.1,1));
        newCircle.setVisible(true);
        newCircle.setDisable(false);

        newCircle.setOnMouseClicked(event1 -> {
                    validate.fire();
                    validate.setDisable(true);
                    GraphiclinkTwoNodes(newCircle,validated);}

            );
        background.getChildren().add(newCircle);
++nbNodes;
        System.out.println(background.getChildren());

    }
    @FXML
    private void arrangeNode(){
        if (arrangeable) {
            int k = 0;
            int l = 0;
            Circle tempCircle;
            System.out.println("*** here is the number of nodes" + nbNodes + " *** \n");
            if (nbNodes > 11) {
                System.out.println("resizing");
                background.setMinHeight(60 * nbNodes);
                background.setMinWidth(60 * nbNodes);
                background.setPrefWidth(60 * nbNodes);
                background.resize(60 * nbNodes, 60 * nbNodes);
                System.out.println(background.getWidth() + "the size of window" + background.getHeight() + "\n");

            }
            scroll.setContent(background);
            scroll.setPannable(true);
            while (l < background.getChildren().size()) {
                if (background.getChildren().get(l) instanceof Circle) {
                    tempCircle = ((Circle) background.getChildren().get(l));
                    tempCircle.setCenterX(Math.max(background.getScene().getWindow().getWidth() / 2 - 21 * nbNodes, 0) + 21 * nbNodes + 13 + 21 * nbNodes * Math.cos(k * 2 * Math.PI / nbNodes));
                    tempCircle.setCenterY(21 * nbNodes + 13 - 21 * nbNodes * Math.sin(k * 2 * Math.PI / nbNodes));
                    //System.out.println(tempCircle.getCenterX()+"this for x, and for y :"+tempCircle.getCenterY()+"\n \n");
                    background.getChildren().set(l, tempCircle);
                    k++;

                }
                l++;
            }


        }
    }
    @FXML
    private void showMenu(double x, double y, double xx, double yy, boolean validated){
        MenuBar newMenuBar = new MenuBar();
        ContextMenu newMenu = new ContextMenu();
        MenuItem newMenuItem = new MenuItem();
        MenuItem newMenuItem2 = new MenuItem();
        newMenuItem.setText("Create Node");
        newMenuItem.setOnAction(event -> createNode(x,y));
        newMenu.getItems().add(0,newMenuItem);
        newMenuItem2.setText("reorganize node");
        newMenuItem2.setOnAction(event -> {
            arrangeNode();
            arrangeNode();
        });
        if (!arrangeable)
            newMenuItem2.setDisable(true);
        if (validated)
            newMenuItem.setDisable(true);
        newMenu.getItems().add(1,newMenuItem2);
        newMenuBar.setLayoutX(x);
        newMenuBar.setLayoutY(y);
        newMenuBar.setContextMenu(newMenu);
        newMenuBar.setVisible(false);
        newMenuBar.setDisable(false);
        background.getChildren().add(newMenuBar);
        newMenu.show(background,xx,yy);
        System.out.println(background.onMouseClickedProperty().toString());

        System.out.println(newMenu.getX()+"*******"+newMenu.getY());

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        validate.setOnAction(event -> validateGraph());
        background.setOnContextMenuRequested(event3 -> {
                System.out.println(event3.getSceneX()+"and "+ event3.getSceneY()+"versus "+event3.getX()+"and "+ event3.getY());
                showMenu(event3.getX(), event3.getY(),event3.getScreenX(), event3.getScreenY(),validated);
             });


    }
}
