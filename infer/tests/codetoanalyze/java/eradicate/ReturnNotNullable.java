/*
* Copyright (c) 2013- Facebook.
* All rights reserved.
*/

package codetoanalyze.java.eradicate;

import com.google.common.base.Optional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ReturnNotNullable {

  void returnvoid() {
    // No warning here.
  }

  Void returnVoid() {
    // This is OK too.
    return null;
  }

  String returnNull() {
    return null;
  }

  String returnNullable(@Nullable String s) {
    return s;
  }

  @Nonnull
  String returnNonnull() {
    return "abc";
  }

  @Nullable
  String returnNullOK() {
    return null;
  }

  @Nullable
  String returnNullableOK(@Nullable String s) {
    return s;
  }

  String throwException(@Nullable Exception e, boolean bad) throws Exception {
    if (bad) {
      throw (e); // no ERADICATE_RETURN_NOT_NULLABLE should be reported
    }
    return "OK";
  }

  @Nullable
  String redundantEq() {
    String s = returnNonnull();
    int n = s == null ? 0 : s.length();
    return s;
  }

  @Nullable
  String redundantNeq() {
    String s = returnNonnull();
    int n = s != null ? 0 : s.length();
    return s;
  }

  @Nonnull
  BufferedReader nn(BufferedReader br) {
    return br;
  }

  void tryWithResources(String path) {
    try (BufferedReader br = nn(new BufferedReader(new FileReader(path)))) {
    } // no condition redundant should be reported on this line
    catch (IOException e) {
    }
  }

  /*
  Check that orNull is modelled and RETURN_OVER_ANNOTATED is not returned.
   */
  @Nullable
  String testOptional(Optional<String> os) {
    return os.orNull();
  }
}
