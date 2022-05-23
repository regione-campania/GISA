/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.properties;

public class PropertiesTest
{

	public static void main(String[] args)
	{
		System.out.println( Message.get( "ERRORE" ) );
		System.out.println( Label.get( "PAGE_TITLE" ) );
		System.out.println( Message.getSmart( "PIPPO" ) );
		System.out.println( Label.getSmart( "page_title" ) );
		System.out.println( Message.get( "pippo" ) );
		System.out.println( Label.get( "page_aafasd" ) );
		System.out.println( Message.getSmart( "ERRORE" ) );
		System.out.println( Label.getSmart( "page_asdf" ) );
	}

}
