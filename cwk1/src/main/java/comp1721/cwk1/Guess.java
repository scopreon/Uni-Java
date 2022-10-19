package comp1721.cwk1;


import java.util.Scanner;

public class Guess{
  private int guessNumber;
  private String chosenWord;
  // Use this to get player input in readFromPlayer()
  private static final Scanner INPUT = new Scanner(System.in);
  //guess constructor for preset string
  public Guess(int guessNum){
    //guess number should always be between 1 and 6
    if(guessNum<1 || guessNum >6){
      throw new GameException("Guess number must be between 1 and 6");
    }
    guessNumber=guessNum;
    readFromPlayer();
  }
  //guess constructor for input string
  public Guess(int guessNum, String guessWord){
    guessNumber=guessNum;
    chosenWord=guessWord.toUpperCase();
    if (checkWordValid()){
      throw new GameException("Guess must have length 5, only alphabet characters");
    }
  }
  private boolean checkWordValid(){
    if(chosenWord.length()!=5){
      return true;
    }
    //make sure no numbers in word, assumes no symbols
    String alphabet="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for(int i = 0; i <chosenWord.length();i++){
      if(!alphabet.contains(Character.toString(chosenWord.charAt(i)))){
        return true;
      }
    }
    return false;
  }

  public int getGuessNumber(){
    return guessNumber;
  }

  public String getChosenWord(){
    return chosenWord;
  }

  public void readFromPlayer(){
    chosenWord=(INPUT.next()).toUpperCase();
    if (checkWordValid()){
      throw new GameException("Guess must have length 5, only alphabet characters");
    }
  }

  //returns the string format for -a option or not
  public String returnStringFormat(String guessWord){
    char[] chosenWordArray=chosenWord.toCharArray();
    char[] sParamArray=guessWord.toCharArray();
    StringBuilder output = new StringBuilder();
    for(int i = 0; i < guessWord.length(); i++){
      String tempArray = new String(sParamArray);
      //creates a char array
      //if it finds a match with the target and chosen word it removes the character from target
      //this prevents repetition letters in chosen word all showing up
      if(tempArray.contains(Character.toString(chosenWordArray[i]))){
        if(chosenWordArray[i] == sParamArray[i]){
          //if in correct place and correct letter
          output.append("2");
        }
        else{
          //incorrect place but correct letter
          output.append("1");
        }
        for(int a = 0; a < 5; a++){
          if(sParamArray[a]==chosenWordArray[i]){
            sParamArray[a]=' ';
            break;
          }
        }
      }
      else{
        //incorrect place and incorrect letter
        output.append("0");
      }

    }
    return output.toString();
  }
  //compareWith function for accessible
  public String compareWithAccessible(String guessWord){
    char[] chosenWordArray=chosenWord.toCharArray();
    char[] returnStringFormatArray=returnStringFormat(guessWord).toCharArray();
    StringBuilder output = new StringBuilder();
    String verb;
    int counter;
    output.append(" | ");
    for(int i = 0; i < 3; i++) {
      counter=0;
      for (int x = 0; x < 5; x++) {
        int i1 = Integer.parseInt(Character.toString(returnStringFormatArray[x]));
        if (i1 == i) {
          counter++;
          output.append(String.format("%c,", chosenWordArray[x]).toUpperCase());
        }
      }
      if(counter>0) {
        if(counter>1){
          verb="are";
        }
        else{
          verb="is";
        }
        output.setLength(output.length() - 1);
        if (i == 2) {
          output.append(String.format(" %s correct | ",verb));
        } else if (i == 1) {
          output.append(String.format(" %s correct but in wrong place | ",verb));
        } else {
          output.append(String.format(" %s incorrect | ",verb));
        }
      }
    }
    return output.toString();
  }
  //compareWith function for colour
  public String compareWith(String guessWord){
    char[] chosenWordArray=chosenWord.toCharArray();
    char[] returnStringFormatArray=returnStringFormat(guessWord).toCharArray();
    StringBuilder output = new StringBuilder();
    for(int i = 0; i < 5; i++){
      int i1 = Integer.parseInt(Character.toString(returnStringFormatArray[i]));
      if(i1 ==2){
        output.append(String.format("\033[30;102m %c \033[0m",chosenWordArray[i]));
      }
      else if(i1 ==1){
        output.append(String.format("\033[30;103m %c \033[0m",chosenWordArray[i]));
      }
      else{
        output.append(String.format("\033[30;107m %c \033[0m",chosenWordArray[i]));
      }
    }
    return output.toString();
  }
  //returns if current word matches chosen word
  public boolean matches(String guessWord) {
    return guessWord.equals(chosenWord);
  }
}
