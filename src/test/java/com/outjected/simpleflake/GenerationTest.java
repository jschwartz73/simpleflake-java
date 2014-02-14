package com.outjected.simpleflake;


import org.junit.Assert;
import org.junit.Test;

import java.util.TreeSet;

public class GenerationTest {

  @Test
  public void test() {
    FlakeGenerator gen = new FlakeGenerator();
    SimpleFlake sf = gen.generateSimpleFlake();
    Assert.assertTrue(sf.toByteArray().length == 8);
    Assert.assertNotEquals(gen.generateLong(), gen.generateLong());
    System.out.println(gen.generateLong());
  }

  @Test
  public void generateMillions(){
      long start = System.currentTimeMillis();
      FlakeGenerator gen = new FlakeGenerator();
      TreeSet<Long> results = new TreeSet<>();
      for(int i =0; i < 250_000; i++) {
          results.add(gen.generateLong());
      }

      //Since a tree set can't have duplicates there must be exactly 250,000
      Assert.assertEquals(250_000L, results.size());
      long end = System.currentTimeMillis();
      System.out.println("Completed in " + (end - start) + " ms");
  }
}
