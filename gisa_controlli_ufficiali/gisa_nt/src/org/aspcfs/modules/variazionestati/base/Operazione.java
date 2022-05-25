/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.variazionestati.base;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Operazione {

private int code;
private String description;


public Operazione(ResultSet rs) throws SQLException {
setCode(rs.getInt("code"));
description = rs.getString("description");

}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public int getCode() {
	return code;
}
public void setCode(int code) {
	this.code = code;
}


	
	
	
	
	
	
	
	
}
