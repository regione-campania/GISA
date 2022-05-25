/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.utils;
public class GoogleGeoCodeResponse {
	public String status;
	public results[] results = new results[10] ;

	public GoogleGeoCodeResponse() {
	}

	public class results {
	    public String formatted_address;
	    public geometry geometry;
	    public String[] types;
	    public address_component[] address_components;
	}

	public class geometry {
	    public bounds bounds;
	    public String location_type;
	    public location location;
	    public bounds viewport;
	}

	public class bounds {

	    public location northeast;
	    public location southwest;
	}

	public class location {
	    public String lat;
	    public String lng;
	}

	public class address_component {
	    public String long_name;
	    public String short_name;
	    public String[] types;
	}}