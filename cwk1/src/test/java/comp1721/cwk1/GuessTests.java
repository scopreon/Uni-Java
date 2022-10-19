// Correctness testing for COMP1721 Coursework 1 (Guess class)
// DO NOT CHANGE ANYTHING IN THIS FILE!

package comp1721.cwk1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class GuessTests {
  private Guess g1, g2;

  @BeforeEach
  public void perTestSetup() {
    g1 = new Guess(1, "agile");
    g2 = new Guess(2, "sport");
  }

  @Test
  @DisplayName("guessNumber field set up correctly")
  public void guessNumberField() {
    assertAll(
      () -> assertThat(g1.getGuessNumber(), is(1)),
      () -> assertThat(g2.getGuessNumber(), is(2))
    );
  }

  @Test
  @DisplayName("word field set up correctly")
  public void wordField() {
    assertAll(
      () -> assertThat(g1.getChosenWord(), is("AGILE")),
      () -> assertThat(g2.getChosenWord(), is("SPORT"))
    );
  }

  @Test
  @DisplayName("GameException thrown for invalid guess number")
  public void invalidGuessNumber() {
    assertAll(
      () -> assertThrows(GameException.class, () -> new Guess(0)),
      () -> assertThrows(GameException.class, () -> new Guess(7))
    );
  }

  @Test
  @DisplayName("GameException thrown for invalid word")
  public void invalidChosenWord() {
    assertAll(
      () -> assertThrows(GameException.class, () -> new Guess(1, "")),
      () -> assertThrows(GameException.class, () -> new Guess(1, "xxxx")),
      () -> assertThrows(GameException.class, () -> new Guess(1, "xxxxxx")),
      () -> assertThrows(GameException.class, () -> new Guess(1, "12345"))
    );
  }

  @Test
  @DisplayName("matches() behaves correctly")
  public void matches() {
    assertAll(
      () -> assertThat(g2.matches("SPORT"), is(true)),
      () -> assertThat(g2.matches("SPURT"), is(false))
    );
  }

  @Test
  @DisplayName("compareWith() correct when no letters match")
  public void compareNoMatches() {
    assertThat(
      "AGILE compared with SPORT",
      g1.compareWith("SPORT"),
      is("\033[30;107m A \033[0m\033[30;107m G \033[0m\033[30;107m I \033[0m\033[30;107m L \033[0m\033[30;107m E \033[0m")
    );
  }

  @Test
  @DisplayName("compareWith() correct when all letters match")
  public void compareAllMatching() {
    assertThat(
      "AGILE compared with AGILE",
      g1.compareWith("AGILE"),
      is("\033[30;102m A \033[0m\033[30;102m G \033[0m\033[30;102m I \033[0m\033[30;102m L \033[0m\033[30;102m E \033[0m")
    );
  }

  @Test
  @DisplayName("compareWith() correct when all letters in wrong place")
  public void compareAllInWrongPlace() {
    assertThat(
      "SPORT compared with PORTS",
      g2.compareWith("PORTS"),
      is("\033[30;103m S \033[0m\033[30;103m P \033[0m\033[30;103m O \033[0m\033[30;103m R \033[0m\033[30;103m T \033[0m")
    );
  }

  @Test
  @DisplayName("compareWith() correct when some letters match")
  public void compareSomeMatching() {
    Guess g = new Guess(1, "soups");
    assertThat(
      "SOUPS compared with PAUSE",
      g.compareWith("PAUSE"),
      is("\033[30;103m S \033[0m\033[30;107m O \033[0m\033[30;102m U \033[0m\033[30;103m P \033[0m\033[30;107m S \033[0m")
    );
  }
}
