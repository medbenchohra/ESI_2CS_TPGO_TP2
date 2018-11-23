package sample;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Node;
import javafx.scene.Cursor;
import javafx.scene.shape.Line;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane background;
    public static int i = 0;
    private static boolean src;
    @FXML
    public void createNode(double x, double y){
        final int[] lineId = {0,0};
        Circle newCircle = new Circle();
        newCircle.setCenterX(x);
        newCircle.setCenterY(y);
        newCircle.setRadius(15);
        newCircle.setFill(new Color(0.1,1,0.1,1));
        newCircle.setVisible(true);
        newCircle.setDisable(false);
            newCircle.setOnMouseClicked(event1 -> {
                if (src) src=false;
                else src =true;
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
                    int a= (int)background.getChildren().size();
                    existingLine= (Line)background.getChildren().get(a-1);
                    existingLine.setEndX(thisCircle.getCenterX());
                    existingLine.setEndY(thisCircle.getCenterY());
                    existingLine.setManaged(false);
                    background.getChildren().set(a-1,existingLine);

                }
            });
        background.getChildren().add(newCircle);

        System.out.println(background.getChildren());

    }
    @FXML
    public void arrangeNode(){
        int k=0;
        Circle classType = new Circle();
        while (background.getChildren().get(k).getClass()==classType.getClass()){
            k++;
        }
       // background.getChildren().get(0).setLayoutX(background.getChildren().get(0).getLayoutX()+);
        while (background.getChildren().get(k).getClass()==classType.getClass()){
         //   background.getChildren().get(k).setLayoutX(background.getChildren().get(k).getLayoutX()+);
        }
    }
    @FXML
    public void showMenu(double x, double y){
        MenuBar newMenuBar = new MenuBar();
        ContextMenu newMenu = new ContextMenu();
        MenuItem newMenuItem = new MenuItem();
        newMenuItem.setText("Create Node");
        newMenuItem.setOnAction(event -> {createNode(x,y);});
        newMenu.getItems().add(newMenuItem);
        newMenuBar.setLayoutX(x);
        newMenuBar.setLayoutY(y);
        newMenuBar.setContextMenu(newMenu);
        newMenuBar.setVisible(false);
        newMenuBar.setDisable(false);
        background.getChildren().add(newMenuBar);
            newMenu.show(background,x,y);
        System.out.println(background.onMouseClickedProperty().toString());

        System.out.println(newMenu);

    }
    @FXML
   /* public void linkTwoNodes(){
        if (src){
            background.setOnMouseMoved(event -> {
                Line newLine= new Line();
                newLine.setStartX(background.getChildren().get());

            });
        }
        else {}
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        background.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event3) {
                System.out.println(event3.getX()+"and "+ event3.getY());
                showMenu(event3.getX(), event3.getY());
            }
        });


    }
}
