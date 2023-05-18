package com.example.rpsudokusolver;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    //udelam si textove pole 9x9
    TextField[][] textField = new TextField[9][9];
    Label result = new Label();
    @Override
    public void start(Stage stage) throws IOException {
        //vytvorim si Gridpane
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
                grid.add(t, i, j);
            }
        }

        HBox hbox = new HBox(10);
        hbox.getChildren().add(result);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        grid.add(hbox,0, 9, 9, 1);

        //tlacitko na mazani cisel
        Button clearButton = new Button("Clear");
        VBox vbox1 = new VBox(10);
        vbox1.getChildren().add(clearButton);
        vbox1.setAlignment(Pos.TOP_RIGHT);
        grid.add(vbox1,9, 0, 6, 1);
        clearButton.setOnAction(e -> clearGrid());

        //tlacitko na vyreseni sudoku
        Button solveButton = new Button("Solve");
        VBox vbox2 = new VBox(10);
        vbox2.getChildren().add(solveButton);
        vbox2.setAlignment(Pos.CENTER_RIGHT);
        grid.add(vbox2,9, 4, 6, 1);
        solveButton.setOnAction(e -> solveBoard());


        Scene scene = new Scene(grid,425, 400);

        stage.setResizable(false);
        stage.setTitle("Sudoku Solver");
        stage.setScene(scene);
        stage.show();
    }
    //Tato metoda resi sudoku po zmacnuti tlacitka Solve
    public void solveBoard() {
        int matrix[][] = new int[9][9];
        int poleKUlozeni[][] = new int[9][9];
        int chuan;
        //Nactu stringy z mrizky a nactu je jako inty do intoveho pole
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String oss = textField[i][j].getText();
                    if (oss.equals("")) {
                        chuan = 0;
                    } else {
                        chuan = Integer.parseInt(oss);
                    }
                    matrix[i][j] = chuan;
                    poleKUlozeni[i][j] = chuan;
                }
            }
            //Pomoci intoveho pole a tridy SudokuSolver vygeneruji reseni sudoku
            //Pole pak prevedu na Stringy a nactu do textovych poli
            SudokuSolver ss = new SudokuSolver(matrix);

            if(ss.generate() == false) {
                System.out.println("Can not be solved");
                result.setText("Can not be solved");
                for(int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        if(poleKUlozeni[i][j] == 0) {
                            textField[i][j].setStyle("-fx-background-color: rgb(255,0,0)");
                        }
                        else {
                            textField[i][j].setStyle("-fx-background-color: rgb(255,255,255)");
                        }
                    }
                }
            }
            else {
                System.out.println("Can be solved");
                result.setText("Solved");
                for(int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        int x = matrix[i][j];
                        String str = Integer.toString(x);
                        textField[i][j].setStyle("-fx-background-color: rgb(0,255,0)");
                        textField[i][j].setText(str);
                        if(poleKUlozeni[i][j] > 0) {
                            textField[i][j].setStyle("-fx-background-color: rgb(255,255,255)");
                        }
                    }
                }
            }
        }
        catch (NumberFormatException e) {
            System.out.println("only numbers");
        }
    }
    //Tato metoda vymaze vsechny cisla z mrizky
    public void clearGrid() {
        result.setText("OSS");
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