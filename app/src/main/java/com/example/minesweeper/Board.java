package com.example.minesweeper;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Board {
    private List<List<Block>> blocks = new ArrayList<List<Block>>();

    public Board(){
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            List<Block> bl = new ArrayList<Block>();
            for(int j = 0; j < 8; j++) {
                Block b = new Block();
                b.setValue(0);
                bl.add(b);
            }
            blocks.add(bl);
        }

        for(int i = 0; i < 4; i++){
            int x = random.nextInt(9);
            int y = random.nextInt(7);
            if(blocks.get(x).get(y).getValue() == 0){
                blocks.get(x).get(y).setValue(-1);
            }
            else{
                i--;
            }
        }
        int bombs = 0;

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 8; j++){
                if(!blocks.get(i).get(j).isBomb()){
                    List<Block> neighbors = getNeighbors(i,j);
                    for(Block b: neighbors){
                        if(b.isBomb()){
                            bombs++;
                        }
                    }
                    blocks.get(i).get(j).setValue(bombs);
                    bombs = 0;
                }
            }
        }
    }

    public Block getBlock(int x, int y){
        if (x < 0 || x >= 10 || y < 0 || y >= 8) {
            return null;
        }
        return blocks.get(x).get(y);
    }

    public List<Block> getNeighbors(int x, int y){
        List<Block> neighbors = new ArrayList<Block>();
        List<Block> n = new ArrayList<Block>();

        neighbors.add(getBlock(x-1, y+1));
        neighbors.add(getBlock(x, y-1));
        neighbors.add(getBlock(x+1, y+1));
        neighbors.add(getBlock(x-1, y));
        neighbors.add(getBlock(x+1, y));
        neighbors.add(getBlock(x-1, y-1));
        neighbors.add(getBlock(x, y+1));
        neighbors.add(getBlock(x+1, y-1));

        for(Block b: neighbors){
            if(b != null){
                n.add(b);
            }
        }
        return n;
    }

    public List<List<Block>> getBlocks() {
        return blocks;
    }
}
