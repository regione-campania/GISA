/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.util.vam;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public final class QueryKey implements Serializable {

   private final String queryText;
   private final List queryParameters;

   public QueryKey(final String queryText, final List queryParameters) {
      this.queryText = queryText;
      this.queryParameters = queryParameters;
   }

   public String getQueryText() {
      return queryText;
   }

   public List getQueryParameters() {
      return Collections.unmodifiableList(queryParameters);
   }

   public boolean equals(final Object value) {
      if (this == value) return true;
      if (value == null || getClass() != value.getClass()) return false;
      final QueryKey query = (QueryKey)value;
      if (!queryParameters.equals(query.queryParameters)) return false;
      if (!queryText.equals(query.queryText)) return false;
      return true;
   }


   public int hashCode() {
      int result;
      result = queryText.hashCode();
      result = 29 * result + queryParameters.hashCode();
      return result;
   }
}