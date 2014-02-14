package com.outjected.simpleflake;


import org.junit.Assert;
import org.junit.Test;

public class GenerationTest {

  @Test
  public void test() {
    SimpleFlake sf = FlakeGenerator.generate();
    Assert.assertTrue(sf.toByteArray().length == 8);
    Assert.assertNotEquals(FlakeGenerator.generate().toLong(), FlakeGenerator.generate().toLong());
  }
}
