// Main program for COMP1721 Coursework 1
// DO NOT CHANGE THIS!

package comp1721.cwk1;

import java.io.IOException;


public class Wordle {
  public static void main(String[] args) throws IOException {
    Game game;
    boolean accessible = false;
    //if there are no arguments run game with colour mode+today's word
    if(args.length == 0){
      game = new Game("data/words.txt");
    }
    //check if first argument is -a for accessible mode
    else if(args.length == 1){
      if(args[0].equals("-a")){
        accessible=true;
        game = new Game("data/words.txt");
      }
      else{
        game = new Game(Integer.parseInt(args[0]), "data/words.txt");
      }
    }
    //for running preset and accessible mode
    else{
      game = new Game(Integer.parseInt(args[1]), "data/words.txt");
      if(args[0].equals("-a")){
        accessible=true;
      }
    }
    game.play(accessible);
    //write the played game to build/history.txt for storage
    game.writeHistory();
    //display stats from previous games
    game.displayStats("build/history.txt");
    //save game in build/lastgame.txt
    game.save("build/lastgame.txt");
  }
}
