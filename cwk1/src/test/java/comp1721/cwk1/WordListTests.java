// Correctness testing for COMP1721 Coursework 1 (WordList class)
// DO NOT CHANGE ANYTHING IN THIS FILE!

package comp1721.cwk1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class WordListTests {
  private static final String WORDS_FILE = "data/test.txt";

  @Test
  @DisplayName("size() returns correct value for a WordList")
  public void correctSize() throws IOException {
    WordList words = new WordList(WORDS_FILE);
    assertThat(words.size(), is(5));
  }

  @Test
  @DisplayName("Suitable exception if file of words cannot be found")
  public void missingFile() {
    Throwable t = assertThrows(
      IOException.class, () -> new WordList("non-existent-file.txt"));
    assertThat(t.getClass(), anyOf(is(FileNotFoundException.class), is(NoSuchFileException.class)));
  }

  @Test
  @DisplayName("Words can be retrieved from a WordList")
  public void retrieveWords() throws IOException {
    WordList words = new WordList(WORDS_FILE);
    assertAll(
      () -> assertThat(words.getWord(0), is("TEST0")),
      () -> assertThat(words.getWord(1), is("TEST1"))
    );
  }

  @Test
  @DisplayName("GameException if game number is out of range")
  public void invalidGameNumber() throws IOException {
    WordList words = new WordList(WORDS_FILE);
    assertAll(
      () -> assertThrows(GameException.class, () -> words.getWord(-1)),
      () -> assertThrows(GameException.class, () -> words.getWord(5))
    );
  }
}
