/*
 * Copyright 2016 Victor Albertos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.reactivecache2;

import java.io.File;
import java.security.InvalidParameterException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Created by daemontus on 02/09/16.
 */
public class ReactiveCacheBuilderValidationTest {

  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Test(expected = InvalidParameterException.class)
  public void Cache_Directory_Null() {
    new ReactiveCache.Builder()
        .using(null, Jolyglot$.newInstance());

  }

  @Test(expected = InvalidParameterException.class)
  public void Jolyglot_Null() {
    new ReactiveCache.Builder()
        .using(temporaryFolder.getRoot(), null);
  }

  @Test(expected = InvalidParameterException.class)
  public void Cache_Directory_Not_Exist() {
    File cacheDir = new File(temporaryFolder.getRoot(), "non_existent_folder");
    new ReactiveCache.Builder()
        .using(cacheDir, Jolyglot$.newInstance());
  }

  @Test(expected = InvalidParameterException.class)
  public void Cache_Directory_Not_Writable() {
    File cacheDir = new File(temporaryFolder.getRoot(), "non_existent_folder");
    if (!cacheDir.mkdirs()) {
      throw new IllegalStateException("Cannot create temporary directory");
    }
    if (!cacheDir.setWritable(false, false)) {
      throw new IllegalStateException("Cannot modify permissions");
    }
    new ReactiveCache.Builder()
        .using(cacheDir, Jolyglot$.newInstance());
  }
}
