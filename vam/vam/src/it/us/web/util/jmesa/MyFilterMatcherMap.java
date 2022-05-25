/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.jmesa;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.FilterMatcherMap;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.filter.StringFilterMatcher;


public class MyFilterMatcherMap implements FilterMatcherMap
{ 
	public Map<MatcherKey, FilterMatcher> getFilterMatchers() 
	{ 
		Map<MatcherKey, FilterMatcher> filterMatcherMap = new HashMap<MatcherKey, FilterMatcher>(); 
		filterMatcherMap.put(new MatcherKey(Date.class),    new MyDateFilterMatcher()); 
		filterMatcherMap.put(new MatcherKey(boolean.class), new StringFilterMatcher());
		return filterMatcherMap;
	}

} 
