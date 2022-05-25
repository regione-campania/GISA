/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.jmesa;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.NullComparator;
import org.jmesa.core.sort.ColumnSort;
import org.jmesa.limit.Limit;
import org.jmesa.limit.Order;
import org.jmesa.limit.Sort;
import org.jmesa.limit.SortSet;

public class CustomColumnSort implements ColumnSort 
{
    @SuppressWarnings("unchecked")
    public Collection<?> sortItems(Collection<?> items, Limit limit) 
    {
        ComparatorChain chain = new ComparatorChain();
        
//        Transformer transformer = new Transformer() 
//        {
//            public Object transform(Object input) 
//            {
//            	if(input==null)
//            		return input;
//            	if(input.getClass().toString().indexOf("Date")>=1)
//            		return ((Date)input);
//            	else
//            		return ((String)input).toLowerCase();
//            }
//        };
        
        //Comparator comparator = new TransformingComparator(transformer);
        
        
        SortSet sortSet = limit.getSortSet();
        for (Sort sort : sortSet.getSorts()) 
        {
            if (sort.getOrder() == Order.ASC) 
            	chain.addComparator(new BeanComparator(sort.getProperty() , new NullComparator(false) ));
            else if (sort.getOrder() == Order.DESC)
            	chain.addComparator(new BeanComparator(sort.getProperty() , new NullComparator(false) ),true);
        }

        if (chain.size() > 0) 
        {
            Collections.sort((List<?>) items, chain);
        }
        
        return items;
    }
}
