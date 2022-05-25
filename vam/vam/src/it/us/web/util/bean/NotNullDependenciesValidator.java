/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.bean;

import org.apache.commons.beanutils.BeanUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullDependenciesValidator implements ConstraintValidator<NotNullDependencies, Object>
{
    private String[] listField;
    private boolean  allNull;

    @Override
    public void initialize(final NotNullDependencies constraintAnnotation)
    {
    	listField = constraintAnnotation.listField().split(";");
    	allNull = constraintAnnotation.allNull();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
        try
        {
        	int countNull = 0;
        	
        	//Conta quanti null si sono nella lista
        	for(int i=0;i<listField.length;i++)
        	{
        		Object obj = BeanUtils.getProperty(value, listField[i]);
        		
        		if(obj==null)
        			countNull++;
        	}
        	
        	//Se non sono tutti null o tutti not-null allora la validazione di sicuro fallisce
        	if(countNull>0 && countNull<listField.length)
        		return false;
        	
        	//Se sono tutti null ma tutti null non sono ammessi allora ritorna false
        	if(countNull==listField.length && !allNull)
        		return false;
        	
        	//Se nessun test fallisce allora la lista di campi è valida
        	return true;
        		
        	
        }
        catch (final Exception ignore)
        {
            return false;
        }
    }
}