/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.anagrafe_animali.base;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

import org.aspcfs.utils.DbUtil;
import org.aspcfs.utils.GestoreConnessioni;

public class test1 {

	public static void main(String[] args) {
		
	Connection db;
	try {
		db = DbUtil.getConnection("bdu_indici", "postgres", "postgres", "172.16.3.250");
		Animale thisAnimale = new Animale(db, 6204262);
		stampaValoreField(thisAnimale);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
		
		// TODO Auto-generated method stub

	}
	
	
	public static void stampaValoreField(Object target) {
	       
	       

        String PACKAGE = "org.aspcfs.modules.opu.base";
               
                Field[] classFields = target.getClass().getDeclaredFields();
                try {
                for (Field field : classFields) {
                    //imposto il campo accessibile altrimenti JAva solleva una eccezione
                    field.setAccessible(true);
                    field.getGenericType();
                    field.getDeclaringClass();
                    field.getType();
                    field.getClass();
                    String canonicalName = field.getGenericType().toString();
                    String fieldName = field.getName();
                     //controllo se è un campo di un tipo primitivo Java
                    if (field.get(target) != null && (canonicalName.contains("javax.xml.ws.Holder") || canonicalName.equals("T"))) {
                         stampaValoreField(field.get(target));
                    } else
                    if(field.get(target) != null && !canonicalName.contains(PACKAGE)){
                        System.out.println(field.getName() + ": ");
                        try {
                            System.out.println(field.get(target));
                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else {
                        /**
                         * Eseguo il parsing di un campo complesso:
                         *
                         * Classi del mio package
                         **/
                        Object complexObj = field.get(target);
                        
                        if(complexObj!=null && field.get(target) != null){
                         //GESTIONE OGGETTO COMPLESSO DEL MIO PACKAGE
                          System.out.println("Inizio: " + fieldName);
                          //uso la ricorsione per eseguire gli stessi calcoli in profondita
                          stampaValoreField(complexObj);
                          System.out.println("END: "+fieldName);
                        
                        }
                       }
               
                }
              
                }catch (Exception ecc) { }
}

}
