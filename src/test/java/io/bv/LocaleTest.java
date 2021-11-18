package io.bv;

import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;

public class LocaleTest {

  @Test
  public void localeList() {
    Arrays
      .asList(Locale.getAvailableLocales())
      .forEach(System.out::println);
  }

}
