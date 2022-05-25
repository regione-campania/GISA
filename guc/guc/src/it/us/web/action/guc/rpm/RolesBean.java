/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.guc.rpm;

import java.util.ArrayList;
import java.util.Collections;

/***************************************
 * BEAN PER I RUOLI/PERMESSI TRA 2 DB  *
****************************************/

public class RolesBean {

	private ArrayList<String> RoleDB1 = new ArrayList<String>();
	private ArrayList<String> RoleDB2 = new ArrayList<String>();
	private ArrayList<String> PermDB1 = new ArrayList<String>();
	private ArrayList<String> PermDB2 = new ArrayList<String>();
	private ArrayList<String> CommonRole = new ArrayList<String>();
	private ArrayList<String> CommonPermission = new ArrayList<String>();
	
//-----------------	
	public ArrayList<String> getRoleDB1() {
		return RoleDB1;
	}
	public void setRoleDB1(ArrayList<String> roleDB1) {
		RoleDB1 = roleDB1;
	}
//-----------------	
	public ArrayList<String> getRoleDB2() {
		return RoleDB2;
	}
	public void setRoleDB2(ArrayList<String> roleDB2) {
		RoleDB2 = roleDB2;
	}
//-----------------	
	public void setPermDB1(ArrayList<String> permDB1) {
		PermDB1 = permDB1;
	}
	public ArrayList<String> getPermDB1() {
		return PermDB1;
	}
//-----------------
	public void setPermDB2(ArrayList<String> permDB2) {
		PermDB2 = permDB2;
	}
	public ArrayList<String> getPermDB2() {
		return PermDB2;
	}
//-----------------
	public void setCommonRole(ArrayList<String> commonRole) {
		CommonRole = commonRole;
	}
	public ArrayList<String> getCommonRole() {
		return CommonRole;
	}
//-----------------
	public void setCommonPermission(ArrayList<String> commonPermission) {
		CommonPermission = commonPermission;
	}
	public ArrayList<String> getCommonPermission() {
		return CommonPermission;
	}
	
	public ArrayList<String> sortList (ArrayList<String> list){
		Collections.sort(list);
		return list;
	} 
}
