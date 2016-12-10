package hu.ait.camdensikes.minesweeper.MinesweeperView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import hu.ait.camdensikes.minesweeper.GameActivity;
import hu.ait.camdensikes.minesweeper.MinesweeperModel.MinesweeperModel;
import hu.ait.camdensikes.minesweeper.R;

/**
 * Minesweeper
 * By Camden Sikes
 */

public class MinesweeperView extends View {

    private Paint paintBg;
    private Paint paintBorder;
    private Paint paintLine;
    private Paint paintSolid;
    private Paint paintText;
    private Bitmap flag;
    private Bitmap bomb;
    private short fieldSize;
    private short bombNum;
    private short bombsRemaining;
    private short mode;
    public static final short SELECT = 0;
    public static final short FLAG = 1;
    public static final short GAMEOVER = 2;
    
    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        paintBg = new Paint();
        paintBg.setColor(Color.LTGRAY);
        paintBg.setStyle(Paint.Style.FILL);

        paintSolid = new Paint();
        paintSolid.setColor(Color.WHITE);
        paintSolid.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintBorder = new Paint();
        paintBorder.setColor(Color.WHITE);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setStrokeWidth(5);

        paintText = new Paint();
        paintText.setTextSize(100);
        paintText.setColor(Color.BLACK);

        flag = BitmapFactory.decodeResource(getResources(), R.drawable.flag);

        bomb = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);

        fieldSize = MinesweeperModel.getInstance().getFieldSize();

        bombNum = MinesweeperModel.getInstance().getBombNum();

        bombsRemaining = bombNum;

        mode = SELECT;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawGameField(canvas);

        drawSquares(canvas);

        if(mode == GAMEOVER){
            revealBombs(canvas);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {

            int tX = ((int) event.getX()) / (getWidth() / fieldSize);
            int tY = ((int) event.getY()) / (getHeight() / fieldSize);

            if(tX<fieldSize && tY<fieldSize) {

                if (MinesweeperModel.getInstance().getFieldContent(tX, tY) == MinesweeperModel.EMPTY) {
                    if (mode == FLAG) {
                        lose();
                    }
                    else if (mode == SELECT) {
                        MinesweeperModel.getInstance().mark(tX, tY);
                    }
                }
                else if (MinesweeperModel.getInstance().getFieldContent(tX, tY) == MinesweeperModel.BOMB){
                    if (mode == FLAG){
                        MinesweeperModel.getInstance().setFieldContent(tX,tY,MinesweeperModel.FLAG);
                        bombsRemaining--;
                        if(bombsRemaining==0){
                            win();
                        }
                    }
                    else if (mode == SELECT){
                        lose();
                    }
                }
                invalidate();
            }
        }
        return true;
    }

    private void drawGameField(Canvas canvas) {
        //background
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBorder);
        // horizontal lines
        for(int i = 1; i < fieldSize + 1 ; i++){
            canvas.drawLine(0, i * getHeight() / fieldSize, getWidth(), i * getHeight() / fieldSize,
                    paintLine);
            canvas.drawLine(i * getWidth() / fieldSize, 0, i * getWidth() / fieldSize, getHeight(),
                    paintLine);
        }
    }

    private void drawSquares(Canvas canvas) {
        short value;
        for(int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                value = MinesweeperModel.getInstance().getFieldContent(i,j);
                if(value < 9){
                    canvas.drawRect(i*getWidth()/fieldSize,j*getHeight()/fieldSize,
                            (i+1)*getWidth()/fieldSize,(j+1)*getHeight()/fieldSize,paintSolid);
                }
                if(value < 9 && value > 0){
                    canvas.drawText(String.valueOf(value), i*getWidth()/fieldSize + getWidth()/fieldSize/4,
                            (j+1)*getWidth()/fieldSize - getHeight()/fieldSize/8, paintText);
                }
                else if(value == MinesweeperModel.FLAG){
                    canvas.drawBitmap(flag, i*getWidth()/fieldSize, j*getWidth()/fieldSize,null);
                }
                //else if(value == MinesweeperModel.EMPTY || value == MinesweeperModel.BOMB){
                //    canvas.drawRect(i*getWidth()/fieldSize,j*getHeight()/fieldSize,
                //            (i+1)*getWidth()/fieldSize,(j+1)*getHeight()/fieldSize,paintSolid);
                //}
            }
        }
    }

    private void revealBombs(Canvas canvas){
        for(int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if(MinesweeperModel.getInstance().getFieldContent(i,j) == MinesweeperModel.BOMB){
                    canvas.drawBitmap(bomb, i*getWidth()/fieldSize, j*getWidth()/fieldSize,null);
                }
            }
        }
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        flag = Bitmap.createScaledBitmap(flag, getWidth()/fieldSize, getHeight()/fieldSize, false);
        bomb = Bitmap.createScaledBitmap(bomb, getWidth()/fieldSize, getHeight()/fieldSize, false);

        paintText.setTextSize(getWidth()/(fieldSize));
    }

    public short getMode() {
        return mode;
    }

    public void setMode(short mode) {
        this.mode = mode;
    }

    public void restartGame() {
        MinesweeperModel.getInstance().resetModel();
        bombsRemaining = bombNum;
        setMode(SELECT);
        invalidate();
    }

    private void lose() {
        setMode(GAMEOVER);
        ((GameActivity)getContext()).showSnackbarMessage(getContext().getString(R.string.lose_message));
    }

    private void win() {
        setMode(GAMEOVER);
        ((GameActivity)getContext()).showSnackbarMessage(getContext().getString(R.string.win_message));
    }
}
