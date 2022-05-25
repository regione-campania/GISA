/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewopu.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.LoggerFactory;

public class ReflectionUtil
{
	
	public static Object nuovaIstanza(String nomeClasse) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
        Class myClass = Class.forName(nomeClasse);
        Object whatInstance = myClass.newInstance();
        return whatInstance;
	}
	
	public static Method getMetodo(Class myClass,String nomeMetodo,Class<?>... parametri) throws NoSuchMethodException, SecurityException
	{
       return myClass.getMethod(nomeMetodo,parametri);
	}
	
	public static Object invocaMetodo(String nomeClasse, String nomeMetodo,Class[] parametriTipi, Object... parametri) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException
	{
	   Object instanzaClasse = nuovaIstanza(nomeClasse);
	   Method metodo = getMetodo(instanzaClasse.getClass(), nomeMetodo, parametriTipi);
       return (Object)metodo.invoke(instanzaClasse,parametri);
	}
	
	public static Object invocaMetodo(Object instanzaClasse, String nomeMetodo,Class[] parametriTipi, Object... parametri ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
	   Method metodo = getMetodo(instanzaClasse.getClass(), nomeMetodo, parametriTipi);
       return (Object)metodo.invoke(instanzaClasse,parametri);
	}
	
	public static Object invocaMetodoByVariabile(Object instanzaClasseVariabile,String nomeVariabile, String nomeMetodo,Object... parametri) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, NoSuchMethodException, InvocationTargetException
	{
		Method m = getMetodo(instanzaClasseVariabile.getClass(), nomeMetodo, null);
		return (Object)m.invoke(instanzaClasseVariabile,parametri);
	}
	
	public static void assegnaValore(Object instanzaClasseVariabile,String nomeVariabile, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Class classeVariabile = instanzaClasseVariabile.getClass();
		Field field = classeVariabile.getDeclaredField(nomeVariabile);
	    field.set(instanzaClasseVariabile, value);
	}
	
	public static Object getVariabile(Object instanzaClasseVariabile,String nomeVariabile) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
	{
		Class classeVariabile = instanzaClasseVariabile.getClass();
		return classeVariabile.getDeclaredField(nomeVariabile).get(instanzaClasseVariabile);
	}
	
	
}