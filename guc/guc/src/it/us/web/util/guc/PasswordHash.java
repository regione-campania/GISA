/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/**
 *  This file is part of the Centric CRM Tools library package
 *  Copyright (C) 2001-2007 Centric CRM, Inc.
 *  http://www.centriccrm.com
 *  This library is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as published by the
 *  Free Software Foundation; version 2.1 of the License.
 *  This library is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 *  for more details. You should have received a copy of the GNU Lesser General
 *  Public License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA
 */
package it.us.web.util.guc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Random;

/**
 * Utility to hash a String. Code from news group.
 *
 * @author matt rajkowski
 * @version $Id: PasswordHash.java,v 1.1.1.1 2002/01/14 19:49:27 mrajkowski
 *          Exp $
 * @created August 16, 2001
 */
public class PasswordHash {
  private static Random random = new Random();


  /**
   * Default constructor.
   */
  public PasswordHash() {
  }


  /**
   * Takes a string and turns it into a one-way string hash
   *
   * @param inString Description of Parameter
   * @return Description of the Returned Value
   */
  public static String encrypt(String inString) {
    try {
      MessageDigest md;
      //MD5, SHA, SHA-1
      md = MessageDigest.getInstance("MD5");
      byte[] output = md.digest(inString.getBytes());
      StringBuffer sb = new StringBuffer(2 * output.length);
   
      for (int i = 0; i < output.length; ++i) {
        int k = output[i] & 0xFF;
        if (k < 0x10) {
          sb.append('0');
        }
        sb.append(Integer.toHexString(k));
      }
      return sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) {
    }
    return null;
  }


  /**
   * Command line utility. When run as an application, this member uses the
   * encrypt method to display the encrypted version of a line of input.
   *
   * @param args Not used.
   */
  public static void main(String args[]) {
    PasswordHash hasher = new PasswordHash();
    try {
      String text;
      if (args.length == 0) {
        System.out.print("Password: ");
        BufferedReader in =
            new BufferedReader(new InputStreamReader(System.in));
        text = in.readLine();
        System.out.println("Hash: " + hasher.encrypt(text));
      } else {
        text = args[0];
        System.out.println(hasher.encrypt(text));
      }
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }


  /**
   * Gets the randomString attribute of the PasswordHash class
   *
   * @param lo Description of the Parameter
   * @param hi Description of the Parameter
   * @return The randomString value
   */
  public static String getRandomString(int lo, int hi) {
    int n = rand(lo, hi);
    byte b[] = new byte[n];
    for (int i = 0; i < n; i++) {
      b[i] = (byte) rand('a', 'z');
    }
    return new String(b);
  }


  /**
   * Description of the Method
   *
   * @param lo Description of the Parameter
   * @param hi Description of the Parameter
   * @return Description of the Return Value
   */
  public static int rand(int lo, int hi) {
    int n = hi - lo + 1;
    int i = random.nextInt() % n;
    if (i < 0) {
      i = -i;
    }
    return lo + i;
  }
}


