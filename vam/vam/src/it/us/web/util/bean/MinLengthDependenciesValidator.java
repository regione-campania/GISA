/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.bean;

import org.apache.commons.beanutils.BeanUtils;

//import sun.org.mozilla.javascript.internal.ObjArray;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MinLengthDependenciesValidator implements ConstraintValidator<MinLengthDependencies, Object>
{
    private String dependence;
    private String attributo;
    private int minLength;

    
    @Override
    public void initialize(final MinLengthDependencies constraintAnnotation)
    {
    	dependence = constraintAnnotation.dependence();
    	attributo = constraintAnnotation.attributo();
    	minLength = constraintAnnotation.minLength();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
        try
        {
        	
        	Object objDependence = BeanUtils.getProperty(value, dependence);
        	
        	if(objDependence!=null && Boolean.parseBoolean(objDependence.toString())==true)
        	{
        		Object objAttributo = BeanUtils.getProperty(value, attributo);
        		if(objAttributo==null || objAttributo.toString().length()<minLength)
        			return false;
        	}
        	return true;
        	
        }
        catch (final Exception ignore)
        {
            return false;
        }
    }
}