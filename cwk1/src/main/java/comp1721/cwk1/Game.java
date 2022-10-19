package comp1721.cwk1;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

public class Game {
    //initialising class variables
    private int gameNumber;
    private String target;
    private Guess[] guess = new Guess[6];
    //number of guesses for that game
    private int guesses=0;
    //true is word is correctly guessed
    private boolean wordGuessed=false;


    public Game(String wordListPath) throws IOException {
      WordList list = new WordList(wordListPath);
      LocalDate date = LocalDate.now();
      //day0, the first day of wordle
      LocalDate date0 = LocalDate.of(2021, 6, 19);

      //difference between 2 dates, normally returns long, typecast to int
      int daysDiff = (int) DAYS.between(date0, date);

      //add 2 because there was a 2-day offset
      gameNumber=daysDiff+2;
      target=(list.getWord(gameNumber)).toUpperCase();

    }

    public Game(int gameNum, String wordListPath) throws IOException {
      WordList list = new WordList(wordListPath);
      //fetching day for today given the game number
      target = list.getWord(gameNum);
      gameNumber=gameNum;
    }

    public void writeHistory() throws IOException {
      BufferedWriter bw = new BufferedWriter(new FileWriter("build/history.txt", true));
      //format for the history file: game number, word guessed right (t/f), number of guesses
      bw.write(String.format("%s,%b,%d\n",gameNumber,wordGuessed,guesses));
      bw.flush();
      //flush and close file
      bw.close();
    }

    public void displayStats(String historyPath) throws IOException{
      //initialising variables to calculate stats
      float numberOfGames = 0;
      float numberOfWins = 0;
      int currentWinStreak = 0;
      int longestWinStreak = 0;
      String[] item;
      int[] guessItems = {0,0,0,0,0,0};
      FileInputStream fis = new FileInputStream(historyPath);
      Scanner l=new Scanner(fis);
      String line="";
      while(l.hasNextLine()) {
        line=l.nextLine();
        item = line.split(",");
        numberOfGames+=1;
        //if day was correct
        if(item[1].equals("true")){
          numberOfWins++;
          currentWinStreak++;
          guessItems[Integer.parseInt(item[2])-1]++;
        }
        else{
          currentWinStreak=0;
        }
        if(currentWinStreak > longestWinStreak){
          longestWinStreak = currentWinStreak;
        }
      }
      //printing format in a box
      System.out.print("/-------------------------------\\\n");
      System.out.printf("| Games played\t\t%d\t|\n",(int)numberOfGames);
      System.out.printf("| Win rate\t\t%d%%\t|\n",(int)(numberOfWins/numberOfGames*100));
      System.out.printf("| Current win streak\t%d\t|\n",currentWinStreak);
      System.out.printf("| Longest win streak\t%d\t|\n",longestWinStreak);

      System.out.print("|                               |\n");
      System.out.print("| Data Histogram                |\n");
      //histogram, bar graph 1->6 on y-axis. - represents 1 game
      for(int i = 0;i<6;i++){
        System.out.printf("| \033[30;107m%d\033[0m",i+1);
        for(int x=0;x<guessItems[i];x++){
          System.out.print("-");
        }
        //format for each bar in histogram, calculates how many blank spaces after bar
        //assumes all characters have even width
        for(int x=0;x<29-(guessItems[i]);x++){
          System.out.print(" ");
        }
        System.out.print("|\n");
      }
      //closing table
      System.out.print("\\-------------------------------/\n");
    }

    public void play(boolean accessible){
      System.out.printf("WORDLE %d\n\n",gameNumber);
      //looping through maximum of 6 attempts
      for(int i=0;i<=5;i++){
        guesses++;
        System.out.printf("Enter guess (%d/6): ",i+1);
        guess[i] = new Guess(i+1);
        //creates new guess object and initialises
        //applies compareWith method to get output formatting
        if(accessible){
          System.out.printf("%s\n",guess[i].compareWithAccessible(target));
        }
        else{
          System.out.printf("%s\n",guess[i].compareWith(target));
        }
        if(guess[i].matches(target)){
          wordGuessed = true;
          break;
        }

      }
      //printing message according to the score of the game
      if(guesses == 1){
        System.out.println("Superb - Got it in one!");
      }
      else if(guesses<=5){
        System.out.println("Well done!");
      }
      else if(guesses==6 && wordGuessed){
        System.out.println("That was a close call!");
      }
      if(!wordGuessed){
        System.out.println("Nope - Better luck next time");
        System.out.printf("The correct word was %s\n",target);
      }

    }

    public void save(String lastgamePath) throws IOException {
      //writing game to file build/lastgame.txt with PrintWriter
      //ensures no formatting is lost in the process
      PrintWriter pw = new PrintWriter(lastgamePath);
      for(int i = 0; i < guesses; i++){
        pw.print(String.format("%s\n",guess[i].compareWith(target)));
      }
      pw.close();
    }
}
