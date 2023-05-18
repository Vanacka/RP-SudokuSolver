package com.example.rpsudokusolver;

/*
 * Reprezentuje policko v sudoku
 * Parametry:
 *          Row - souradnice radku
 *          Column - souradnice sloupce
 * Konstruktory:
 *          trida ma jeden parametricky konstruktor,
 *          ktery jako parametry dostava souradnice policka
 * Metody:
 *          mySquare - zjisteni leveho horniho rohu
 *                      ctverce 3x3, ve kterem se nachazi policko
 */
public class CellCoordinates {
    public int Row;
    public int Column;

    // Konstruktor
    public CellCoordinates(int Row, int Column) {
        this.Row = Row;
        this.Column = Column;
    }

    /*
     * Funkce vraci souradnice leveho horniho rohu
     * ctverce 3x3, ve kterem se nachazi nase policko
     * Pokud si od bodu [0][0] pole 9x9 oznacime kazdy treti radek a sloupec
     * vcetne radku 0 a sloupce 0, tak pruseciky techto radku a sloupcu
     * budou horni levy rohy ctverce 3x3
     * Ctverec 3x3 jsou 3 radky pod sebou o rozmeru 3 policek
     * a prvni radek zacina na souradnici leveho horniho rohu ctverce
     * Funkce vydeli souradnice policka aby zjistila,
     * ve ktere ze 3 casti pole 9x9 se policko nachazi
     * Pokud se podil bude rovnat 0 policko se nachazi v 1 casti atd.
     */
    public CellCoordinates mySquare() {
        int cornerColumn = 0;
        int cornerRow = 0;

        // Najdeme sloupec, ve kterem se nachazi
        // levy horni roh ctverce
        if (this.Column / 3 == 0) {
            cornerColumn = 0;
        }
        else if (this.Column / 3 == 1) {
            cornerColumn = 3;
        }
        else if (this.Column / 3 == 2) {
            cornerColumn = 6;
        }

        //Najdeme radek, ve kterem se nachazi
        //levy horni roh ctverce
        if (this.Row / 3 == 0) {
            cornerRow = 0;
        }
        else if (this.Row / 3 == 1) {
            cornerRow = 3;
        }
        else if (this.Row / 3 == 2) {
            cornerRow = 6;
        }

        return new CellCoordinates(cornerRow, cornerColumn);
    }
}