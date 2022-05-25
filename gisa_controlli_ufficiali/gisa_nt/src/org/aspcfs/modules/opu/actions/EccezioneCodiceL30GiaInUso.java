/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.opu.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EccezioneCodiceL30GiaInUso extends Exception {

	public EccezioneCodiceL30GiaInUso()
	{
		
	}
	public EccezioneCodiceL30GiaInUso(String msg)
	{
		super(msg);
	}
	
	public static void main(String[] args)
	{
		String t = "cc00234asd";
		Pattern p = Pattern.compile("[0-9]{4}");
		 
		int i = 0; 
		int parsed = -1;
		t = t.replaceAll("[^0-9]","");
		parsed = Integer.parseInt(t);
	}
}
