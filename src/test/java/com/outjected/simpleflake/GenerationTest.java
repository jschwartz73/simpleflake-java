package com.outjected.simpleflake;


import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class GenerationTest {

  @Test
  public void test() {
    FlakeGenerator gen = new FlakeGenerator();
    Assert.assertTrue(gen.generateBytes().length == 8);
    Assert.assertNotEquals(gen.generate(), gen.generate());
  }

  @Test
  public void generateAMillion() {
    final int ITERATIONS = 1_000_000;
    FlakeGenerator gen = new FlakeGenerator();
    HashSet<Long> results = new HashSet<>(ITERATIONS, 100);
    for (int i = 0; i < ITERATIONS; i++) {
      results.add(gen.generate());
    }

    // Since a Set can't have duplicates there must be exactly 1,000,000
    Assert.assertEquals(ITERATIONS, results.size());
  }
}
