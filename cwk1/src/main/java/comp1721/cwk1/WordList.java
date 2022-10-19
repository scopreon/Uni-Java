package comp1721.cwk1;


import java.io.IOException;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordList{

    private List<String> list=new ArrayList<>();
    public WordList(String wordListPath) throws IOException {
        Scanner input = new Scanner(Paths.get(wordListPath));
        //reading in entire file and saving it in list
        //each line is a new element in the list
        while (input.hasNextLine()) {
          String line = input.nextLine();
          list.add(line);
        }

    }

    public int size(){
        return list.size();
    }

    public String getWord(int gameNum){
        String test;
        //throw error if gameNum too large or < 0
        try{
          test =  list.get(gameNum);
        }
        catch(IndexOutOfBoundsException e) {
          throw new GameException("Word does not exist");
        }
        return test;
    }

}
