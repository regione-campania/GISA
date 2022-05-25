<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
  <%! public static String fixEncoding(String parola)
  {
	
	  if (parola==null)
	  return "" ;
		parola = parola.replaceAll("à", "a'");
		parola = parola.replaceAll("è", "e'");
		parola = parola.replaceAll("ì", "i'");
		parola = parola.replaceAll("ò", "o'");
		parola = parola.replaceAll("ù", "u'");
		
		parola=parola.replaceAll("À", "A'");
		parola=parola.replaceAll("È", "E'");
		parola=parola.replaceAll("Ì", "I'");
		parola=parola.replaceAll("Ò", "O'");
		parola=parola.replaceAll("Ù", "U'");
		
		parola=parola.replaceAll("Á", "A'");
		parola=parola.replaceAll("É", "E'");
		parola=parola.replaceAll("í", "I'");
		parola=parola.replaceAll("Ó", "O'");
		parola=parola.replaceAll("Ú", "U'");
			
		
		return parola;
	  
  }%>