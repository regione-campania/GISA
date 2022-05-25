/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import it.us.web.bean.vam.lookup.LookupCMF;
import it.us.web.bean.vam.lookup.LookupDiagnosi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

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
			   (nome.startsWith("get") || nome.startsWith("is")) &&
			   metodo.getAnnotation(Transient.class)==null)
			{
				if(!modifiche.equals(""))
					separatore="&&&&&&";
				
				if(nome.startsWith("get"))
					nome=nome.replace("get", "");
				if(nome.startsWith("is"))
					nome=nome.replace("is", "");
				nome=nome.substring(0, 1).toLowerCase()+nome.substring(1, nome.length());
				Object valueReturn = metodo.invoke(obj, null);
				
				//Se è un primitivo o classe semplice
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
				//Se è un Bean
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
	
	
	public static Object populate(Object bean,ResultSet resultSet) throws SQLException 
	{
		HashMap<String,Object> properties = new HashMap<String,Object>();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols = metaData.getColumnCount();
		for (int i=1; i<=cols ; i++) 
		{
			properties.put(metaData.getColumnName(i),resultSet.getString(i));
		}
		try 
		{
		     DateTimeConverter dtConverter = new DateConverter();
		     dtConverter.setPattern("dd/MM/yyyy HH:mm:ss");

		     ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
		     convertUtilsBean.deregister(Date.class);
		     convertUtilsBean.register(dtConverter, Date.class);

		     BeanUtilsBean beanUtilsBean = new BeanUtilsBean(convertUtilsBean, new PropertyUtilsBean());
		     beanUtilsBean.getConvertUtils().register(false, false, 0);
		     beanUtilsBean.populate(bean,properties);
		     
		        
			 //BeanUtils.populate(bean,properties);
		} 
		catch (Exception e)
		{
			throw new SQLException("BeanUtils.populate threw " + e.toString());
		}
		return bean;
	}
	
	
	
	
	

}
