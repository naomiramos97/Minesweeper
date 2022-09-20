package com.example.minesweeper;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int clock = 0;
    private boolean running = false;
    private static final int COLUMN_COUNT = 8;
    private static final int FLAG_COUNT = 4;
    private boolean flagMode = false;
    private boolean digMode = true;
    int flagsPlaced = 0;
    private boolean lost = false;

    // save the TextViews of all cells in an array, so later on,
    // when a TextView is clicked, we know which cell it is
    private Board board = new Board();
    private ArrayList<TextView> cell_tvs;
    private List<List<Block>> blocks = board.getBlocks();

    private int dpToPixel(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.textView12);
        btn.setOnClickListener(this::onClickButton);

        final TextView flagCount = (TextView) findViewById(R.id.textView10);
        flagCount.setText(Integer.toString(FLAG_COUNT));

        cell_tvs = new ArrayList<TextView>();
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout01);
        LayoutInflater li = LayoutInflater.from(this);

        Random random = new Random();
        for (int i=0; i<=9; i++) {
            for (int j=0; j<=7; j++) {
                TextView tv = (TextView) li.inflate(R.layout.custom_cell_layout, grid, false);
                GridLayout.LayoutParams lp = (GridLayout.LayoutParams) tv.getLayoutParams();
                tv.setBackgroundColor(Color.GREEN);
                tv.setOnClickListener(this::onClickTV);

                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);

                grid.addView(tv, lp);

                cell_tvs.add(tv);
            }
        }

        if (savedInstanceState != null) {
            clock = savedInstanceState.getInt("clock");
            running = savedInstanceState.getBoolean("running");
        }

        runTimer();

    }

    private int findIndexOfCellTextView(TextView tv) {
        for (int n=0; n<cell_tvs.size(); n++) {
            if (cell_tvs.get(n) == tv)
                return n;
        }
        return -1;
    }

    public void onClickTV(View view) {
        TextView tv = (TextView) view;
        final TextView flagCounts = (TextView) findViewById(R.id.textView10);
        int n = findIndexOfCellTextView(tv);
        int i = n / COLUMN_COUNT;
        int j = n % COLUMN_COUNT;
        Block b = board.getBlock(i, j);

        if(flagMode && !b.isFlagged()){
            tv.setText("\uD83D\uDEA9");
            b.setFlagged(true);
            flagsPlaced++;
            flagCounts.setText(Integer.toString(FLAG_COUNT - flagsPlaced));
        }
        else if(flagMode && b.isFlagged()){
            tv.setText(null);
            b.setFlagged(false);
            flagsPlaced--;
            flagCounts.setText(Integer.toString(FLAG_COUNT - flagsPlaced));
        }
        else if(digMode){
            if (b.isBomb()) {
                tv.setText("\uD83D\uDCA3");
                tv.setBackgroundColor(Color.RED);
                lost = true;
            } else if(!b.isBomb() && b.getValue() != 0) {
                tv.setText(Integer.toString(b.getValue()));
                tv.setTextColor(Color.GRAY);
                tv.setBackgroundColor(Color.LTGRAY);
            }
            else if(!b.isBomb() && b.getValue() == 0){
                tv.setBackgroundColor(Color.LTGRAY);
            }
        }

        onClickStart(view);
    }

    public void onClickButton(View view){
        TextView tv = (TextView) view;
        if(flagMode){
            tv.setText("\u26CF");
            flagMode = false;
            digMode = true;
        }
        else if(digMode){
            tv.setText("\uD83D\uDEA9");
            flagMode = true;
            digMode = false;
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("clock", clock);
        savedInstanceState.putBoolean("running", running);
    }

    public void onClickStart(View view) {
        running = true;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.textView11);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int seconds = clock;
                String time = String.format("%d", seconds);
                timeView.setText(time);

                if (running) {
                    clock++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}