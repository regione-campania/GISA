/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.tool;

import it.us.web.bean.guc.Canile;
import it.us.web.bean.guc.Clinica;
import it.us.web.bean.guc.Importatori;
import it.us.web.bean.guc.Ruolo;
import it.us.web.bean.guc.Struttura;
import it.us.web.bean.guc.Utente;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
public class Utility {
	
	public static Boolean confrontaDatiComplessi (Object a, Object b) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Boolean flag = false;
		int size1 = (int) a.getClass().getMethod("size", null).invoke(a, null);
		int size2 = (int) b.getClass().getMethod("size", null).invoke(b, null);

		Class[] cArg = new Class[1]; 
		cArg[0]= int.class;

		if (size1>0 && size2>0){
			int cont  = 0;
			for (int i=0;i<size1;i++){
				Method m1 = a.getClass().getMethod("get",cArg);
				Object o1 = m1.invoke(a, i);
				String cc1 = (String)o1.getClass().getMethod("toString", null).invoke(o1, null);
				for (int j=0;j<size2;j++){
					Method m2 = b.getClass().getMethod("get",cArg);
					Object o2 = m2.invoke(b, j);
					String cc2 = (String)o2.getClass().getMethod("toString", null).invoke(o2, null);

					if (cc1.equals(cc2)){
						cont++;			
					}
				}
			}
			if (cont!=size1){
				flag=true;
			}
		}		
		return flag;
	}
	
	public static int confrontaBean(Utente u_new, Utente u_old,String listaModificheStruttura) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
			Boolean flag = false;	
			Boolean flagRuoli = false;
			String endpointModStrutt = listaModificheStruttura ;
	
			BeanMap map = new BeanMap(u_old);
			PropertyUtilsBean propUtils = new PropertyUtilsBean();
			for (Object propNameObject : map.keySet()) {
				String propertyName = (String) propNameObject;
				Object property1 = propUtils.getProperty(u_old, propertyName);
				Object property2 = propUtils.getProperty(u_new, propertyName);
	
				if (property1!=null && property2!=null){
					Object p1 = property1.toString();
					Object p2 = property2.toString();
	
					if (propertyName.equals("ruoli")){    //RUOLI
						Set<Ruolo> r1 = (Set<Ruolo>) property1;
						Set<Ruolo> r2 = (Set<Ruolo>) property2;   	
						ArrayList<Object> a = new ArrayList<Object>(r1);
						ArrayList<Object> b = new ArrayList<Object>(r2);
						if (r1.size()==r2.size()){
							if (confrontaDatiComplessi(a, b)==true)
								flagRuoli = true;
						}
						else {
							flagRuoli=true;
						}
					} else if (propertyName.equals("extOption")){
						TreeMap treeMap_old = new TreeMap();
						TreeMap treeMap_new = new TreeMap();
						treeMap_old.putAll(u_old.getExtOption());
						treeMap_new.putAll(u_new.getExtOption());
						if (u_old.getHashRuoli().toString().equals(u_new.getHashRuoli().toString())){
							if (treeMap_new.toString().equals(treeMap_old.toString())){
								flagRuoli = false;
							} else {
								flagRuoli = true;
							}	
						} else {
							flagRuoli = true;
						}
					} else if (propertyName.equals("clinicheVam")){ //CLINICHE VAM
						ArrayList<Clinica> c1 = (ArrayList<Clinica>)property1;
						ArrayList<Clinica> c2 = (ArrayList<Clinica>)property2;
						if (c1.size()==c2.size()){
							if (confrontaDatiComplessi(c1, c2)==true){
								endpointModStrutt = endpointModStrutt+"endpointDBVam;";
								flagRuoli = true;
							}
						} 
						else {
							endpointModStrutt = endpointModStrutt+"endpointDBVam;";
							flagRuoli=true;
						}
					} else if (propertyName.equals("caniliBdu")){ //CANILI BDU
						ArrayList<Canile> c1 = (ArrayList<Canile>)property1;
						ArrayList<Canile> c2 = (ArrayList<Canile>)property2;
						if (c1.size()==c2.size()){
							if (confrontaDatiComplessi(c1, c2)==true){
								endpointModStrutt = endpointModStrutt+"endpointDBBdu;";
								flagRuoli=true;
							}
						}
						else {
							endpointModStrutt = endpointModStrutt+"endpointDBBdu;";
							flagRuoli=true;
						}
					} else if (propertyName.equals("struttureGisa")){ //STRUTTURE GISA
						ArrayList<Struttura> c1 = (ArrayList<Struttura>)property1;
						ArrayList<Struttura> c2 = (ArrayList<Struttura>)property2;
						if (c1.size()==c2.size()){
							if (confrontaDatiComplessi(c1, c2)==true){
								endpointModStrutt = endpointModStrutt+"endpointDBGisa;";
								flagRuoli=true;
							}
						}
						else {
							endpointModStrutt = endpointModStrutt+"endpointDBGisa;";
							flagRuoli=true;
						}		        
					} else if (propertyName.equals("importatori")){ //IMPORTATORI
						ArrayList<Importatori> c1 = (ArrayList<Importatori>)property1;
						ArrayList<Importatori> c2 = (ArrayList<Importatori>)property2;	        	
						if (c1.size()==c2.size()){
							if (confrontaDatiComplessi(c1, c2)==true){
								endpointModStrutt = endpointModStrutt+"endpointDBImportatori;";
								flagRuoli=true;
							}
						}
						else {
							endpointModStrutt = endpointModStrutt+"endpointDBImportatori;";
							flagRuoli=true;
						}	
					} else{ //CONTROLLA TUTTI I CAMPI IN COMUNE ECCETTO LE STRUTTURE ED I RUOLI 
						if (!p1.equals(p2) && !propertyName.equalsIgnoreCase("hashRuoli") && !propertyName.equalsIgnoreCase("extOption") 
								&& !propertyName.contains("clinica") && !propertyName.contains("canile") && !propertyName.contains("importator") 
								&& !propertyName.contains("strutturagisa")){
							flag=true;
						}
					}
				}
				else if((property1!=null && property2==null) || (property1==null && property1!=null)){
					flag=true;
				}
			}
			int ret = 0;
			if (flag==true && flagRuoli==false){ //CAMBIATI SOLO GLI ATTRIBUTI
				ret=1;
			}else if (flag==false && flagRuoli==true){ //CAMBIATI SOLO I RUOLI
				ret=2;
			}else if (flag==true && flagRuoli==true){ //CAMBIATI SIA RUOLI CHE ATTRIBUTI
				ret=3;
			}
	
//			setListaModificheStrutt(endpointModStrutt);
	
			return ret;
		}
}

