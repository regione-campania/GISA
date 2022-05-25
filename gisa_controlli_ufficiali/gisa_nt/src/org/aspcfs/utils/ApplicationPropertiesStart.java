/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

public class ApplicationPropertiesStart {
        
        private static Properties applicationProperties = null;
        private transient static Logger logger = Logger.getLogger("MainLogger");
        //costruttore
        public ApplicationPropertiesStart() { }
        

        public static String getProperty ( String property ){
                return ( applicationProperties != null) ? applicationProperties.getProperty( property ) : null;
        }
        public static Iterator<Object> getKeySet (){
            if (applicationProperties != null)
				return applicationProperties.keySet().iterator();
			else
				return null;
    }
        
public ApplicationPropertiesStart(String nomeFile) {
                
                InputStream is = ApplicationPropertiesStart.class.getResourceAsStream( nomeFile);
                applicationProperties = new Properties();
                try{
                        applicationProperties.load( is );
                }catch(IOException e) {
                          logger.severe("[] - EXCEPTION nella classe ApplicationProperties");
                        applicationProperties = null;
                }
                
                
        }
        
        
}