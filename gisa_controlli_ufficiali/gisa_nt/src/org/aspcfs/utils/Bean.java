/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dom4j.Entity;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;

public class Bean  {
	
	public Bean() {
		
	}
	
	public static String getModifiche(Object obj) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException 
	{
		String modifiche  = "";
		String separatore = "";
		Class classe = obj.getClass();
		Method[] metodi = classe.getMethods();
		
		for(int i=0;i<metodi.length;i++)
		{
			Method metodo = metodi[i];
			String nome = metodo.getName();
			Class returnType = metodo.getReturnType();
			if(!nome.equals("getHibernateLazyInitializer") &&
			   !nome.equals("equals") &&
			   !nome.equals("hashCode") &&
			   !nome.equals("wait") &&
			   !nome.equals("getClass") &&
			   !nome.equals("notify") &&
			   !nome.equals("getNomeEsame") &&
			   !nome.equals("getHtml") &&
			   !nome.equals("getOperatoriId") &&
			   !nome.equals("getDettaglioEsamiForJspEdit") &&
			   !nome.equals("getDettaglioEsamiForJsp") &&
			   !nome.equals("getDettaglioEsamiForJspDetail") &&
			   !nome.equals("getDettaglioEsami") &&
			   !nome.equals("getDettaglioEsamiReferto") &&
			   !nome.equals("getAops") &&
			   !nome.equals("getValoriDettaglioEsamiForJspEdit") &&
			   !nome.equals("getValoriDettaglioEsamiForJspDetail") &&
			   !nome.equals("getFenomeniCadavericiReferto") &&
			   !nome.equals("getEsamiObiettivoApparato") &&
			   (nome.startsWith("get") || nome.startsWith("is")) )
			{
				if(!modifiche.equals(""))
					separatore="&&&&&&";
				
				if(nome.startsWith("get"))
					nome=nome.replace("get", "");
				if(nome.startsWith("is"))
					nome=nome.replace("is", "");
				nome=nome.substring(0, 1).toLowerCase()+nome.substring(1, nome.length());
				Object valueReturn = metodo.invoke(obj, null);
				
				//Se � un primitivo o classe semplice
				if(returnType.isPrimitive() || returnType.getName().equals("java.util.Date") || returnType.getName().equals("java.lang.Integer") || returnType.getName().equals("java.lang.Boolean") || returnType.getName().equals("java.lang.Float") || returnType.getName().equals("java.lang.Double"))
				{
					if(returnType.getName().equals("java.util.Date"))
					{
						if(valueReturn==null)
							modifiche+=separatore+nome+"||||||";
						else
						{
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							String v = sdf.format(valueReturn);
							modifiche+=separatore+nome+"||||||"+v;
						}
					}
					else
					{
						modifiche+=separatore+nome+"||||||"+((valueReturn==null)?("null"):(valueReturn.toString()));
					}
					
				}
				//Se � un Bean
				else if(returnType.getAnnotation(Entity.class)!=null)
				{
					Object value = null;
					if(valueReturn!=null)
					{
						Method metodo2 = valueReturn.getClass().getMethod("toString");
						value = metodo2.invoke(valueReturn, null);
						try
						{
							metodo2 = valueReturn.getClass().getMethod("getNomeEsame");
							nome = (String)metodo2.invoke(valueReturn, null);
						}
						catch(NoSuchMethodException e)
						{
							
						}
							
					}
					else
					{
						Constructor<?> constructor = Class.forName(returnType.getName()).getConstructor();
						Object myObj = constructor.newInstance();
						Method myObjMethod = myObj.getClass().getMethod("getNomeEsame", null);
						nome = (String)myObjMethod.invoke(myObj, null); 
					}
					modifiche+=separatore+nome+"||||||"+((value==null)?("null"):(value.toString()));
				}
				//Altrimenti Collection
				else
				{
					Object value = null;
					if(valueReturn!=null)
					{
						Method metodo2 = valueReturn.getClass().getMethod("toString");
						value = metodo2.invoke(valueReturn, null);
						try
						{
							metodo2 = valueReturn.getClass().getMethod("getNomeEsame");
							nome = (String)metodo2.invoke(valueReturn, null);
						}
						catch(NoSuchMethodException e)
						{
							
						}
					}
					modifiche+=separatore+nome+"||||||"+((value==null)?("null"):(value.toString()));
				}
			}
		}
		return modifiche;
	}
	
	
	public static Object populate(Object bean,Map<String,String[]> properties, boolean autoGrowNestedPaths) 
	{
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
		wrapper.setAutoGrowNestedPaths(autoGrowNestedPaths);
		wrapper.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
		//Nuova gestione
		MutablePropertyValues pvs = new MutablePropertyValues(properties);
		wrapper.setPropertyValues(pvs, true,true);
		//Vecchio
		//wrapper.setPropertyValues(properties);
		return bean;
		
		
		
	}
	
	public static Object populate(Object bean,Map<String,String[]> properties) 
	{
		return populate(bean,properties,true);
	}
	
	public static Object populate(Object bean,ResultSet rs) throws SQLException 
	{
		Map<String,Object> properties = new HashMap<String,Object>();
		ResultSetMetaData metaData = rs.getMetaData();
		int cols = metaData.getColumnCount();
		for (int i=1; i<=cols ; i++) 
		{
				properties.put(metaData.getColumnName(i),rs.getObject(i));
		}
		
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
		wrapper.setAutoGrowNestedPaths(true);
		wrapper.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		//Nuova gestione
		MutablePropertyValues pvs = new MutablePropertyValues(properties);
		wrapper.setPropertyValues(pvs, true,true);
		//Vecchio
		//wrapper.setPropertyValues(properties);
		return bean;
	}
	
	public static Object populate(Object bean,Map<String,String[]> properties, String prefix,boolean isPrefix, boolean autoGrowNestedPaths ) throws SQLException, IllegalArgumentException, IllegalAccessException, ParseException 
	{
		Map<String,String[]> parameterFiltered = filterParameter(properties, prefix, isPrefix);
		return populate(bean, parameterFiltered, autoGrowNestedPaths);
	}
	
	public static Object populate(Object bean,Map<String,String[]> properties, String prefix,boolean isPrefix ) throws SQLException, IllegalArgumentException, IllegalAccessException, ParseException 
	{
		Map<String,String[]> parameterFiltered = filterParameter(properties, prefix, isPrefix);
		return populate(bean, parameterFiltered);
	}
	
	private static Map<String,String[]> filterParameter(Map<String,String[]> properties, String prefix, boolean isPrefix )
	{
		Map<String,String[]> propertiesFiltered = new HashMap<String,String[]>();
		Set<String> keys = properties.keySet();
		for(String key: keys)
		{
			if(key.startsWith(prefix) && isPrefix || key.endsWith(prefix) && !isPrefix)
			{
				String keyToPut = key.replaceAll(prefix, "");
				propertiesFiltered.put(keyToPut, properties.get(key));
			}
		}
		return propertiesFiltered;
	}
	
	
	
	
}
