/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc.rpm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ApplicationProperties {
	
	private static Properties applicationProperties = null;
	
	private ApplicationProperties(){ }
	
	static{
		InputStream is = ApplicationProperties.class.getResourceAsStream( "application.properties" );
		applicationProperties = new Properties();
		try {
			applicationProperties.load( is );
		} catch (IOException e) {
			applicationProperties = null;
		}
	}
	
	
	public static Properties getApplicationProperties() {
		return applicationProperties;
	}


	public static String getProperty( String property ){
		return (applicationProperties != null) ? applicationProperties.getProperty( property ) : "";
	}
}