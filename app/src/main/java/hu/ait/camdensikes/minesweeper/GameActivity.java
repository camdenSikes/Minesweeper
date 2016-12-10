package hu.ait.camdensikes.minesweeper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import hu.ait.camdensikes.minesweeper.MinesweeperModel.MinesweeperModel;
import hu.ait.camdensikes.minesweeper.MinesweeperView.MinesweeperView;

public class GameActivity extends AppCompatActivity {

    private ScrollView layoutContent;
    private MinesweeperModel model;
    private MinesweeperView gameView;
    private short fieldSize;
    private short bombNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        layoutContent = (ScrollView) findViewById(R.id.activity_main);

        gameView = (MinesweeperView) findViewById(R.id.gameView);


        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.restartGame();
            }
        });

        Button btnSelect = (Button) findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameView.getMode() != MinesweeperView.GAMEOVER) {
                    gameView.setMode(MinesweeperView.SELECT);
                    showSimpleSnackbarMessage(getString(R.string.select_mode_set));
                }
            }
        });

        Button btnFlag = (Button) findViewById(R.id.btnFlag);
        btnFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameView.getMode() != MinesweeperView.GAMEOVER) {
                    gameView.setMode(MinesweeperView.FLAG);
                    showSimpleSnackbarMessage(getString(R.string.flag_mode_set));
                }
            }
        });
    }

    public void showSnackbarMessage(String message) {
        Snackbar.make(layoutContent,message,Snackbar.LENGTH_LONG).setAction(R.string.Restart, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.restartGame();
            }
        }).show();
    }

    public void showSimpleSnackbarMessage(String message) {
        Snackbar.make(layoutContent,message,Snackbar.LENGTH_LONG).show();
    }

    public short getFieldSize() {
        return fieldSize;
    }

    public short getBombNum() {
        return bombNum;
    }
}
