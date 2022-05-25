/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.BitSet;

/**
 * This class is very similar to the java.net.URLEncoder class.
 * <p/>
 * Unfortunately, with java.net.URLEncoder there is no way to specify to the
 * java.net.URLEncoder which characters should NOT be encoded.
 * <p/>
 * This code was moved from DefaultServlet.java
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 */
public class URLEncoder {
  protected static final char[] hexadecimal =
      {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
       'A', 'B', 'C', 'D', 'E', 'F'};

  //Array containing the safe characters set.
  protected BitSet safeCharacters = new BitSet(256);

  public URLEncoder() {
    for (char i = 'a'; i <= 'z'; i++) {
      addSafeCharacter(i);
    }
    for (char i = 'A'; i <= 'Z'; i++) {
      addSafeCharacter(i);
    }
    for (char i = '0'; i <= '9'; i++) {
      addSafeCharacter(i);
    }
  }

  public void addSafeCharacter(char c) {
    safeCharacters.set(c);
  }

  public String encode(String path) {
    int maxBytesPerChar = 10;
    int caseDiff = ('a' - 'A');
    StringBuffer rewrittenPath = new StringBuffer(path.length());
    ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);
    OutputStreamWriter writer = null;
    try {
      writer = new OutputStreamWriter(buf, "UTF8");
    } catch (Exception e) {
      e.printStackTrace();
      writer = new OutputStreamWriter(buf);
    }

    for (int i = 0; i < path.length(); i++) {
      int c = (int) path.charAt(i);
      if (safeCharacters.get(c)) {
        rewrittenPath.append((char) c);
      } else {
        // convert to external encoding before hex conversion
        try {
          writer.write(c);
          writer.flush();
        } catch (IOException e) {
          buf.reset();
          continue;
        }
        byte[] ba = buf.toByteArray();
        for (int j = 0; j < ba.length; j++) {
          // Converting each byte in the buffer
          byte toEncode = ba[j];
          rewrittenPath.append('%');
          int low = (int) (toEncode & 0x0f);
          int high = (int) ((toEncode & 0xf0) >> 4);
          rewrittenPath.append(hexadecimal[high]);
          rewrittenPath.append(hexadecimal[low]);
        }
        buf.reset();
      }
    }
    return rewrittenPath.toString();
  }
}
