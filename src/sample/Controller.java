package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    private static boolean isLineDrawable = false;
    private static int nodeInfoIndex =-1;
    private Graph graph;
    @FXML
    private AnchorPane backbackground;
    @FXML
    private void validateGraph(){
        graph = new Graph(nbNodes);
        validated = true;

    };
    @FXML
    private void GraphiclinkTwoNodes(Circle newCircle, boolean linkeable){
        if (linkeable){arrangeable=false;
            if (src) {
                lineSrc=graphicToLogicNode(background.getChildren().indexOf(newCircle));
                Line newLine = new Line();
                newLine.setStartX(newCircle.getCenterX());
                newLine.setStartY(newCircle.getCenterY());
                newLine.setManaged(true);
                newLine.setDisable(false);
                newLine.setVisible(true);
                newLine.setFill(new Color(0,0,0,1));
                background.getChildren().add(newLine);
                background.setOnMouseMoved(event2 -> {
                    newLine.setEndX(event2.getScreenX());
                    newLine.setEndY(event2.getScreenY());
                    if (background.getChildren().indexOf(newLine)!= -1)
                    background.getChildren().set(background.getChildren().indexOf(newLine),newLine);

                });
            } else {
                lineDes=graphicToLogicNode(background.getChildren().indexOf(newCircle));

                if ((graph.getAdjMat(Math.max(lineSrc,lineDes),Math.min(lineSrc,lineDes))==0)&&(lineSrc!= lineDes))
                {
                    background.setOnMouseMoved(event -> {});

                    graph.logicLinkNodes(lineSrc,lineDes);
                Line existingLine ;
                int a= background.getChildren().size();
                existingLine= (Line)background.getChildren().get(a-1);
                existingLine.setEndX(newCircle.getCenterX());
                existingLine.setEndY(newCircle.getCenterY());
                existingLine.setManaged(false);
                background.getChildren().set(a-1,existingLine);
                    graph.setAdjMat(lineSrc,lineDes,1);
                    graph.setAdjMat(lineDes,lineSrc,1);
                    System.out.println("** done "+lineSrc+""+lineDes+""+graph.getAdjMat(lineSrc,lineDes)+""+graph.getAdjMat(lineDes,lineSrc));

                }
                else {
                    int a= background.getChildren().size();
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
    private int nodeInfo(int circleLogicId, boolean readOnly, int precedInfoId){
        int degree = 0;
        if (readOnly){
            if (circleLogicId==0){
                for (int o=0;o<graph.getSize();o++) if (graph.getAdjMat(o,circleLogicId)==1) degree++;
            }
            else for (int o=0;o<graph.getSize();o++) if (graph.getAdjMat(circleLogicId,o)==1) degree++;
            AnchorPane nodeInfoPane = new AnchorPane();
            TextArea infoNode = new TextArea();
            infoNode.setText(" *** Node : "+circleLogicId +" *** \nDegree :"+ degree +"\n ");
            infoNode.setFont(new Font(15));
            infoNode.setCenterShape(true);
            infoNode.setPrefSize(210,66);
            nodeInfoPane.setPrefSize(210,66);
            nodeInfoPane.getChildren().add(infoNode);
            nodeInfoPane.setLayoutX(backbackground.getParent().getScene().getWindow().getWidth()-260);
            nodeInfoPane.setLayoutY(285);
            if (precedInfoId!=-1) {backbackground.getChildren().set(precedInfoId,nodeInfoPane);return precedInfoId;}
            else {backbackground.getChildren().add(nodeInfoPane); return backbackground.getChildren().indexOf(nodeInfoPane);}
        }
        else {
            AnchorPane nodeInfoPane = new AnchorPane();
            TextArea infoNode = new TextArea();
            infoNode.setText(" *Nothing to show * ");
            infoNode.setFont(new Font(15));
            infoNode.setCenterShape(true);
            infoNode.setPrefSize(210,66);
            nodeInfoPane.setPrefSize(210,66);
            nodeInfoPane.getChildren().add(infoNode);
            nodeInfoPane.setLayoutX(backbackground.getParent().getScene().getWindow().getWidth()-260);
            nodeInfoPane.setLayoutY(285);
            if (precedInfoId!=-1) {
                backbackground.getChildren().set(precedInfoId,nodeInfoPane);return precedInfoId;

            }
            else backbackground.getChildren().add(nodeInfoPane); return backbackground.getChildren().indexOf(nodeInfoPane);

        }
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
                    GraphiclinkTwoNodes(newCircle,validated&&isLineDrawable);
                    nodeInfoIndex =nodeInfo(graphicToLogicNode(background.getChildren().indexOf(newCircle)),!isLineDrawable,nodeInfoIndex);


        }

            );
        Text nodeName =new Text();
        background.getChildren().add(newCircle);
        nodeName.setText(String.valueOf(graphicToLogicNode(background.getChildren().size()-1)));
        nodeName.setX(newCircle.getCenterX()-4);
        nodeName.setY(newCircle.getCenterY()+4);
        nodeName.setFont(new Font(15));
        nodeName.setFill(new Color(0,0,0,1));
        nodeName.toBack();
        nodeName.setMouseTransparent(true);
        background.getChildren().add(nodeName);
        ++nbNodes;

    }
    @FXML
    private void arrangeNode(){
        if (arrangeable) {
            int k = 0;
            int l = 0;
            Circle tempCircle;
            Text tempText;
            if (nbNodes > 11) {
                background.setMinHeight(60 * nbNodes);
                background.setMinWidth(60 * nbNodes);
                background.setPrefWidth(60 * nbNodes);
                background.resize(60 * nbNodes, 60 * nbNodes);

            }
            scroll.setContent(backbackground);
            background.setPrefSize(500,500);
            background.setLayoutX(500);
            background.setLayoutY(150);
            background.setPadding(new Insets(100,150,100,150));
            backbackground.getChildren().set(0,background);
            scroll.setPannable(true);
            while (l < background.getChildren().size()) {
                if (background.getChildren().get(l) instanceof Circle) {
                    tempCircle = ((Circle) background.getChildren().get(l));
                    tempCircle.setCenterX(Math.max(background.getScene().getWindow().getWidth() / 2 - 21 * nbNodes, 0) + 21 * nbNodes + 13 + 21 * nbNodes * Math.cos(k * 2 * Math.PI / nbNodes));
                    tempCircle.setCenterY(21 * nbNodes + 13 - 21 * nbNodes * Math.sin(k * 2 * Math.PI / nbNodes));
                    background.getChildren().set(l, tempCircle);
                    tempText= (Text)background.getChildren().get(l+1);
                    tempText.setX(tempCircle.getCenterX()-4);
                    tempText.setY(tempCircle.getCenterY()+4);
                    background.getChildren().set(l+1, tempText);


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


    }
    @FXML
    private void testDfs(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        JFXButton edit = new JFXButton("DRAWING ARCS");
        edit.setLayoutY(90);
        background.getChildren().add(edit);
        edit.setOnAction(event -> {
            isLineDrawable = !isLineDrawable;
            if (isLineDrawable) edit.setText("STOP DRAWING ARCS");
            else edit.setText("DRAWING ARCS");
            background.getChildren().set(background.getChildren().indexOf(edit),edit);

        });


        JFXButton articule = new JFXButton("GET ARTICULATED POINT");
        articule.setLayoutY(630);
        articule.setLayoutX(250);
        articule.setRipplerFill(Color.WHITE);
        articule.setFont(Font.font(15));
        articule.setStyle("-fx-background-color: WHITE;");
        backbackground.getChildren().add(articule);
        articule.setOnAction(event ->
        {
            if (!validated) validateGraph();
            for (int b=0; b<nbNodes;b++){
            if (graph.articulatePoint(b)){
                Circle editedCircle = ((Circle)background.getChildren().get(logicToGraphicNode(b)));
                editedCircle.setFill(new Color(1,0,0,1));
                background.getChildren().set(logicToGraphicNode(b),editedCircle);
            }
        }
        graph.setNbVisitedNodes(0);

        });
        JFXButton reset = new JFXButton("RESET");
        reset.setLayoutY(60);
        background.getChildren().add(reset);
        reset.setOnAction(event -> {
                background.getChildren().remove(3,background.getChildren().size());
                validated =false;
                validate.setDisable(false);
                arrangeable = true;
                nodeInfoIndex =-1;

        });
        validate.setVisible(false);
        validate.setOnAction(event -> validateGraph());
        background.setOnContextMenuRequested(event3 -> {
                showMenu(event3.getX(), event3.getY(),event3.getScreenX(), event3.getScreenY(),validated);
             });


    }
}
