package rsc.output;

import java.nio.file.Path;

/**
 * This class exists because scala singleton `object`s aren't "singleton"s to java code which
 * accesses them via reflection, as the private constructor is called each time (see
 * https://blog.softwaremill.com/is-your-scala-object-always-a-singleton-cb3fd24a2fd9). I believe
 * the reflection boundary is occurring in AnalyzingCompiler.scala in bloop, where this expression
 * is used:
 *
 * `call("xsbt.CompilerInterface", "newCompiler", log)`.
 */
public class StaticCache {
  private static SingletonOutputCache cache = null;

  public static synchronized SingletonOutputCache getCache() {
    if (cache == null) {
      cache = new SingletonOutputCache();
    }
    return cache;
  }
}
