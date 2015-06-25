package org.inferred.freebuilder.processor.util;

import javax.lang.model.SourceVersion;

import com.google.common.base.Optional;

/**
 * Compliance levels which are idiomatically supported by this processor.
 *
 * <p>{@link SourceVersion} is problematic to use, as the constants themselves will be missing
 * on compilers that do not support them (e.g. "RELEASE_8" is not available on javac v6 or v7).
 * Additionally, "sourceLevel.supportsDiamondOperator()" is far more readable than
 * "sourceVersion.compareTo(SourceLevel.RELEASE_7) >= 0".
 */
public enum SourceLevel {
  JAVA_6("1.6"), JAVA_7("1.7");

  String sourceFlagValue;

  private SourceLevel(String sourceFlagValue) {
    this.sourceFlagValue = sourceFlagValue;
  }

  public static SourceLevel from(SourceVersion sourceVersion) {
    // RELEASE_6 is always available, as previous releases did not support annotation processing.
    if (sourceVersion.compareTo(SourceVersion.RELEASE_6) <= 0) {
      return JAVA_6;
    } else {
      return JAVA_7;
    }
  }

  public Optional<TypeReference> javaUtilObjects() {
    switch (this) {
      case JAVA_6:
        return Optional.absent();

      default:
        return Optional.of(TypeReference.to("java.util", "Objects"));
    }
  }

  public boolean supportsDiamondOperator() {
    return this.compareTo(JAVA_7) >= 0;
  }

  /** Returns the value that should be passed to javac -source to produce this source level. */
  public String sourceFlagValue() {
    return sourceFlagValue;
  }
}
