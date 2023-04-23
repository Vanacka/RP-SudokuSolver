package com.example.rpsudokusolver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    //udelam si textove pole 9x9
    TextField[][] textField = new TextField[9][9];
    @Override
    public void start(Stage stage) throws IOException {
        /*String kokot = "123";
        String sulin = "56";
        int curak = Integer.parseInt(kokot) + Integer.parseInt(sulin);
        System.out.println(curak);*/
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //dam textova pole do gridu, ktery mam
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                TextField t = new TextField();
                t.setAlignment(Pos.CENTER);
                textField[i][j] = t;
                //GridPane.setConstraints(t, i, j);
                grid.add(t, i, j);
            }
        }

        Button clearButton = new Button("Clear");
        VBox vbox1 = new VBox(10);
        vbox1.getChildren().add(clearButton);
        vbox1.setAlignment(Pos.TOP_RIGHT);
        grid.add(vbox1,9, 0, 6, 1);
        clearButton.setOnAction(e -> clearGrid());

        Button solveButton = new Button("Solve");
        VBox vbox2 = new VBox(10);
        vbox2.getChildren().add(solveButton);
        vbox2.setAlignment(Pos.CENTER_RIGHT);
        grid.add(vbox2,9, 4, 6, 1);
        solveButton.setOnAction(e -> solveBoard());

        Label result = new Label("oss");
        HBox hbox = new HBox(10);
        hbox.getChildren().add(result);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        grid.add(hbox,0, 9, 9, 1);


        Scene scene = new Scene(grid,425, 400);

        stage.setTitle("Sudoku Solver");
        stage.setScene(scene);
        stage.show();
    }
    public void solveBoard() {
        int matrix[][] = new int[9][9];
        int chuan;
        try {
            for(int i = 0; i < 9; i++) {
                for(int j = 0; j < 9; j++) {
                    String oss = textField[i][j].getText();
                    if(oss.equals("")) {
                       chuan = 0;
                    }
                    else {
                        textField[i][j].setStyle("-fx-font-weight: bold");
                        chuan = Integer.parseInt(oss);
                    }
                    matrix[i][j] = chuan;
                }
            }
            SudokuSolver ss = new SudokuSolver(matrix);
            ss.generate();
            int overeni = SudokuSolver.cislo;
            System.out.println(overeni);
            if(overeni == 0) {
                System.out.println("neni reseni");
            }
            else if(overeni == 1) {
                System.out.println("existuje reseni");
                for(int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        int x = matrix[i][j];
                        String str = Integer.toString(x);
                        textField[i][j].setStyle("-fx-background-color: rgb(0,255,0)");
                        textField[i][j].setText(str);

                    }
                }
            }
        }
        catch (NumberFormatException e) {
            System.out.println("only numbers");
        }
    }
    public void clearGrid() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                textField[i][j].setStyle("-fx-background-color: rgb(255,255,255)");
                textField[i][j].setText("");
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}