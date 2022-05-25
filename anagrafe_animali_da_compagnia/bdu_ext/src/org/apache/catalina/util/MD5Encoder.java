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


/**
 * Encode an MD5 digest into a String.
 * <p/>
 * The 128 bit MD5 hash is converted into a 32 character long String.
 * Each character of the String is the hexadecimal representation of 4 bits
 * of the digest.
 *
 * @author Remy Maucherat
 * @version $Revision: 12404 $ $Date: 2005-08-05 19:37:07 +0200 (Fri, 05 Aug 2005) $
 */

public final class MD5Encoder {


  // ----------------------------------------------------- Instance Variables


  private static final char[] hexadecimal =
      {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
       'a', 'b', 'c', 'd', 'e', 'f'};


  // --------------------------------------------------------- Public Methods


  /**
   * Encodes the 128 bit (16 bytes) MD5 into a 32 character String.
   *
   * @param binaryData Array containing the digest
   * @return Encoded MD5, or null if encoding failed
   */
  public String encode(byte[] binaryData) {

    if (binaryData.length != 16) {
      return null;
    }

    char[] buffer = new char[32];

    for (int i = 0; i < 16; i++) {
      int low = (int) (binaryData[i] & 0x0f);
      int high = (int) ((binaryData[i] & 0xf0) >> 4);
      buffer[i * 2] = hexadecimal[high];
      buffer[i * 2 + 1] = hexadecimal[low];
    }

    return new String(buffer);

  }


}

