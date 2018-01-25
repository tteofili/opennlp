/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opennlp.tools.util.wordvector;

import java.util.Arrays;
import java.util.Map;

class MapWordVectorTable implements WordVectorTable {

  private final Map<String, WordVector> vectors;

  MapWordVectorTable(Map<String, WordVector> vectors) {
    this.vectors = vectors;
  }

  @Override
  public WordVector get(String token) {
    return vectors.get(token);
  }

  @Override
  public int size() {
    return vectors.size();
  }

  @Override
  public int dimension() {
    if (vectors.size() > 0) {
      return vectors.values().iterator().next().dimension();
    }
    else {
      return -1;
    }
  }

  @Override
  public DocumentVector get(String... words) {
    DocumentVector documentVector = null;
    int size = dimension();
    if (size > 0) {
      double noOfWords = words.length;
      double[] dva = new double[size];
      Arrays.fill(dva, 0d);
      for (String word : words) {
        WordVector wordVector = vectors.get(word);
        double[] wva = wordVector.toDoubleBuffer().array();
        for (int i = 0; i < wva.length; i++) {
          dva[i] += wva[i] / noOfWords;
        }
      }
      documentVector = new DoubleArrayVector(dva);
    }
    return documentVector;
  }
}
