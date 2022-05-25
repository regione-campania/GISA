/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5
{
	public static String encrypt(String password)
	{
		MessageDigest messageDigest;
		try
		{
			messageDigest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e)
		{
			return password;
		}
		byte[] bytes = messageDigest.digest( password.getBytes() );
		StringBuffer sb = new StringBuffer();
		for( int i = 0; i < bytes.length; i++ )
		{
			sb.append(Integer.toHexString((bytes[i] & 0xFF) | 0x100).toLowerCase().substring(1,3));
		}
		return sb.toString();
	}
	
}
