package sample;

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
    @FXML
    private AnchorPane background;
    @FXML
    private ScrollPane scroll;
    private static int nbNodes = 0;
    private static boolean src;
    private static boolean arrangeable=true;
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
                   arrangeable=false;
                     src = (!src) ;
                if (src) {
                    System.out.println("proceding to creat Link");
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
                        background.getChildren().set(background.getChildren().lastIndexOf(newLine),newLine);

                    });
                } else {
                    background.setOnMouseMoved(event -> {});
                    Circle thisCircle = (Circle) event1.getSource();
                    Line existingLine ;
                    int a= background.getChildren().size();
                    existingLine= (Line)background.getChildren().get(a-1);
                    existingLine.setEndX(thisCircle.getCenterX());
                    existingLine.setEndY(thisCircle.getCenterY());
                    existingLine.setManaged(false);
                    background.getChildren().set(a-1,existingLine);

                }

            }

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
    private void showMenu(double x, double y, double xx, double yy){
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

        background.setOnContextMenuRequested(event3 -> {
                System.out.println(event3.getSceneX()+"and "+ event3.getSceneY()+"versus "+event3.getX()+"and "+ event3.getY());
                showMenu(event3.getX(), event3.getY(),event3.getScreenX(), event3.getScreenY());
             });


    }
}
