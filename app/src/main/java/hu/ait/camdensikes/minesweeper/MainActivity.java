package hu.ait.camdensikes.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hu.ait.camdensikes.minesweeper.MinesweeperModel.MinesweeperModel;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_SIZE = "KEY_SIZE";
    public static final String KEY_BOMB = "KEY_BOMB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEasy = (Button) findViewById(R.id.btnEasy);
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameWithSize((short)3,(short)1);
            }
        });

        Button btnMedium = (Button) findViewById(R.id.btnMedium);
        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameWithSize((short)5,(short)3);
            }
        });

        Button btnHard = (Button) findViewById(R.id.btnHard);
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameWithSize((short)9, (short) 10);
            }
        });

        Button btnExtreme = (Button) findViewById(R.id.btnExtreme);
        btnExtreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameWithSize((short)16, (short) 40);
            }
        });
    }

    private void startGameWithSize(short fieldSize, short bombNum) {
        Intent intentStartGame = new Intent();
        intentStartGame.setClass(this, GameActivity.class);

        //intentStartGame.putExtra(KEY_SIZE, fieldSize);
        //intentStartGame.putExtra(KEY_BOMB, bombNum);

        if(MinesweeperModel.getInstance().getFieldSize() != fieldSize ||
                MinesweeperModel.getInstance().getBombNum() != bombNum) {
            MinesweeperModel.getInstance().newModel(fieldSize, bombNum);
        }
        startActivity(intentStartGame);
    }
}
