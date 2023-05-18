package com.example.rpsudokusolver;

/*
 * Vytvari reseni sudoku
 * Parametry: matrix - 2D pole
 * Konstruktory: trida ma jeden bezparametricky konstruktor,
 *               ktery nastavi velikost 2d pole
 * Metody:
 *        checkRow - zkontroluje, jestli muze dosadit do radku cislo
 *        checkColumn - zkontroluje, jestli muze dosadit do sloupce cislo
 *        checkSquare - zkontroluje, jestli muze dosadit do ctverce 3x3 cislo
 *        fillCells - vyplni vsechna neobsazena policka spravnymi cisly dle pravidel sudoku
 *        generate - vygeneruje cele sudoku
 *        print - vytiske hotove sudoku
 */
public class SudokuSolver {
    public int matrix [][];


    // Konstruktor
    SudokuSolver(int matrix [][]) {
        this.matrix = matrix;

    }

    public boolean checkValidRow (int Row) {
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = i + 1; j < this.matrix.length; j++) {
                if (this.matrix[Row][i] != 0) {
                    if (this.matrix[Row][i] == this.matrix[Row][j]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public boolean checkAllRows () {
        for (int i = 0; i < this.matrix.length; i++) {
            if(!checkValidRow(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkValidColumn(int Column) {
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = i + 1; j < this.matrix.length; j++) {
                if (this.matrix[i][Column] != 0) {
                    if (this.matrix[i][Column] == this.matrix[j][Column]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkAllColumns() {
        for (int i = 0; i < this.matrix.length; i++) {
            if(!checkValidColumn(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkValidSquare(int Row, int Column) {
        boolean[] checkNum = new boolean[10];
        for (int i = Row; i < Row + 3; i++) {
            for (int j = Column; j < Column + 3; j++) {
                if (this.matrix[i][j]!=0) {
                    int x = this.matrix[i][j];
                    if (checkNum[x] == true) {
                        return false;
                    }
                    else {
                        checkNum[x] = true;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkAllSquares() {
        for (int i = 0; i < this.matrix.length; i+=3) {
            for (int j = 0; j < this.matrix.length; j+=3) {
                if(!checkValidSquare(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkSudoku() {
        if(checkAllRows() && checkAllColumns() && checkAllSquares()) {
            return true;
        }
        return false;
    }
    /*
     * Funkce na vstupu dostane cislo radku a cislo,
     * ktere chceme do nej dosadit
     * Funkce zkontroluje, jestli muze dosadit do radku cislo,
     * tak ze porovnam cislo na vstupu s cisly, ktere jiz v radku  jsou
     * Pokud najdu nejake, kteremu se cislo rovna, vratim false,
     * jinak vratim true
     */
    private boolean checkRow(int Number, int Row) {
        // Prochazim cely radek
        for (int i = 0; i < this.matrix.length; i++) {
            if (Number == this.matrix[Row][i])
                return false;
        }
        return true;
    }

    /*
     * Funkce na vstupu dostane cislo sloupce a cislo,
     * ktere chceme do nej dosadit
     * Funkce zkontroluje, jestli muze dosadit do sloupce cislo,
     * tak ze porovnam cislo na vstupu s cisly, ktere jiz ve sloupci jsou
     * Pokud najdu nejake, kteremu se cislo rovna, vratim false,
     * jinak vratim true
     */
    private boolean checkColumn(int Number,int Column) {
        // Prochazim cely sloupec
        for (int i = 0; i < this.matrix.length; i++){
            if (Number == this.matrix[i][Column])
                return false;
        }
        return true;
    }

    /*
     * Funkce na vstupu dostane souradnice policka a cislo, ktere
     * chceme do nej dosadit
     * Funkce zkontroluje, jestli muze dosadit do policka cislo,
     * tak ze porovnam cislo na vstupu s cisly, ktere jiz ve ctverci jsou
     * Pokud najdu nejake, kteremu se cislo rovna, vratim false,
     * jinak vratim true
     */
    private boolean checkSquare(int Number, int X, int Y) {
        // Vytvorim instanci (=rezervuju si misto v pameti) policka se souradnicemi X, Y
        CellCoordinates cell = new CellCoordinates(X, Y);
        // Ziskam souradnice leveho horniho rohu ctverce 3x3
        CellCoordinates squareLeftCorner = cell.mySquare();

        // Od levehho horniho rohu prochazim ctverec 3x3 po radcich
        for (int i = squareLeftCorner.Row; i < squareLeftCorner.Row + 3; i++) {
            for (int j = squareLeftCorner.Column; j < squareLeftCorner.Column + 3; j++) {
                if (Number == this.matrix[i][j])
                    return false;
            }
        }
        return true;
    }

    /*
     * Funkce vyplni vsechna neobsazena policka spravnymi cisly dle pravidel sudoku
     * Funkce na vstupu dostane souradnice prazdneho policka
     * Funkce vyplni prazdne policko vhodnym cislem dle pravidel
     * sudoku: provedeme kontrolu radku, sloupce, ctverce 3x3
     * Pokud funkce najde cislo, ktere odpovida vsem parametrum sudoku, zapise
     * ho do pole a rekurzivne se zavola
     * Takovym zpusobem postupuje az do te doby nez nevyresi cele sudoku nebo nenarazi
     * na policko kam nejde doplnit ani jedno cislo
     * Potom funkce vrati false na soucasne policko nastavi 0 a vrati se na posledni policko, ktere bylo prazdne
     * (v zasobniku na dalsi otevrenou funkci v poradi) a tam zkusi jine cislo
     * To dela do te doby dokud kompletne nevyresi sudoku nebo nezkusi vsechna
     * prazdna policka a zjisti ze sudoku nema reseni
     */

    public boolean fillCells() {
        // Porchzim cele pole 9x9 od leveho horniho rohu
        for(int cellRow = 0; cellRow < this.matrix.length; cellRow++) {
            for(int cellColumn = 0; cellColumn < this.matrix[cellRow].length; cellColumn++) {
                if (this.matrix[cellRow][cellColumn] == 0) {
                    for(int newNumber = 1; newNumber <= this.matrix.length; newNumber++) {
                        if((checkRow(newNumber, cellRow)) && (checkColumn(newNumber, cellColumn)) && (checkSquare(newNumber, cellRow, cellColumn))) {
                            this.matrix[cellRow][cellColumn] = newNumber;
                            //rekurzivne volam funkci
                            if(fillCells()) {
                                return true;
                            }
                            else {
                                this.matrix[cellRow][cellColumn] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * Funkce vygeneruje cele sudoku
     */
    public boolean generate() {
        if(checkSudoku()) {
                if(fillCells()) {
                    return true;
                }
                return false;
        }
        return false;
    }

    /*
     * Funkce vytiske reseni sudoku (cele pole 9x9 - matrix)
     */
    public void print() {
        generate();
        // Prochazim cele pole od leveho horniho rohu
        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(this.matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}