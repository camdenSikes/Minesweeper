package hu.ait.camdensikes.minesweeper.MinesweeperModel;


import java.util.Random;

public class MinesweeperModel {

    public static final short EMPTY = 9;
    public static final short BOMB = 10;
    public static final short FLAG = 11;

    private short fieldSize;

    private short bombNum;

    private short [][] model;

    private static MinesweeperModel instance = null;

    private Random random;

    private MinesweeperModel() {
        fieldSize = 9;
        bombNum = 10;
        model = new short[fieldSize][fieldSize];
        random = new Random();
        createField();
    }

    public static MinesweeperModel getInstance(){
        if(instance == null){
            instance = new MinesweeperModel();
        }
        return instance;
    }

    public void newModel(short fieldSize, short bombNum){
        this.fieldSize = fieldSize;
        this.bombNum = bombNum;
        model = new short[fieldSize][fieldSize];
        createField();
    }

    private void createField() {
        for(int i = 0; i < fieldSize; i++){
            for(int j = 0; j < fieldSize; j++){
                model[i][j] = EMPTY;
            }
        }
        generateBombs();
    }

    private void generateBombs() {
        for(int i = 0; i < bombNum; i++){
            int x = random.nextInt(fieldSize);
            int y = random.nextInt(fieldSize);
            if(model[x][y] == EMPTY){
                model[x][y] = BOMB;
            }
            else{
                i--;
            }
        }
    }

    public short getFieldContent(int x, int y) {
        return model[x][y];
    }

    public void setFieldContent(int x, int y, short player){
        model[x][y] = player;
    }


    public void resetModel() {
        createField();
    }

    public void mark(int tX, int tY) {
        short numNeighborBombs = 0;
        for(int i = tX-1; i < tX+2; i++){
            for(int j = tY-1; j < tY+2; j++){
                if(i>=0 && j>=0 && i<fieldSize && j<fieldSize && (model[i][j] == BOMB || model[i][j] == FLAG)){
                    numNeighborBombs++;
                }
            }
        }

        model[tX][tY] = numNeighborBombs;

        if(numNeighborBombs == 0){
            for(int i = tX-1; i < tX+2; i++){
                for(int j = tY-1; j < tY+2; j++){
                    if((i != tX || j != tY) &&
                            i>=0 && j>=0 && i<fieldSize && j<fieldSize &&
                            model[i][j] == EMPTY){
                        mark(i,j);
                    }
                }
            }
        }

    }

    public short getFieldSize() {
        return fieldSize;
    }

    public short getBombNum() {
        return bombNum;
    }

    public void setFieldSize(short fieldSize) {
        this.fieldSize = fieldSize;
    }

    public void setBombNum(short bombNum) {
        this.bombNum = bombNum;
    }
}
