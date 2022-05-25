/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils.web;

import org.aspcf.modules.controlliufficiali.base.Piano;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.accounts.base.Comuni;
import org.aspcfs.modules.accounts.base.ComuniAnagrafica;
import org.aspcfs.modules.base.Constants;
import org.aspcfs.modules.base.SyncableList;
import org.aspcfs.modules.buffer.base.Comune;
import org.aspcfs.modules.macellazioninew.utils.ConfigTipo;
import org.aspcfs.utils.DatabaseUtils;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import java.awt.Font;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.text.Style;

/**
 *  A generic class that contains a list of LookupElement objects.
 *
 * @author     mrajkowski
 * @created    September 7, 2001
 * @version    $Id: LookupList.java,v 1.36.12.1 2004/11/29 20:53:42 mrajkowski
 *      Exp $
 */
public class LookupList extends HtmlSelect implements SyncableList, Serializable {
	
	private static final long serialVersionUID = 1L;
	
  public static String uniqueField = "code";
  public String tableName = null;
  protected String jsEvent = null;
  protected int selectSize = 1;
  protected String selectStyle = null;
  protected boolean multiple = false;
  protected java.sql.Timestamp lastAnchor = null;
  protected java.sql.Timestamp nextAnchor = null;
  protected int syncType = Constants.NO_SYNC;
  protected boolean showDisabledFlag = true;
  protected PagedListInfo pagedListInfo = null;
  protected HashMap selectedItems = null;
  protected boolean excludeDisabledIfUnselected = false;
  private String filtro = "" ;
  private int idAsl = -1 ;
  private boolean required = false;
  private boolean searchable = false;
  //private boolean required = false;
  private String label = "";
  
  
  
  
//  private String identificativoAcque ="";
//  
//  private void setIdentificativoAcque(String identificativoAcque){
//	this.identificativoAcque = identificativoAcque;  
//  }
//  private String getIdentificativoAcque(){
//	  return identificativoAcque;
//  }

  
  
  public boolean isRequired() {
	return required;
}


public boolean isSearchable() {
	return searchable;
}


public void setSearchable(boolean searchable) {
	this.searchable = searchable;
}


public void setRequired(boolean required) {
	this.required = required;
}




public String getLabel() {
	return label;
}


public void setLabel(String label) {
	this.label = label;
}


/**
   *  Constructor for the LookupList object. Generates an empty list, which is
   *  not very useful.
   *
   * @since    1.1
   */	
  public LookupList() { }


  /**
   *  Builds a list of elements based on the database connection and the table
   *  name specified for the lookup. Only retrieves "enabled" items at this
   *  time.
   *
   * @param  db                Description of Parameter
   * @param  thisTable         Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public LookupList(Connection db, String thisTable,ConfigTipo configTipo) throws SQLException {
    tableName = thisTable;
    
    buildList(db,configTipo);
  }
  
  public LookupList(Connection db, String thisTable,org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo configTipo) throws SQLException {
	    tableName = thisTable;
	    final int tipo = configTipo.getIdTipo();
		ConfigTipo configTipo2 = new ConfigTipo(tipo);
		buildList(db,configTipo2);	
	  }
  
  public LookupList(Connection db, String thisTable,org.aspcfs.modules.macellazioninewsintesis.utils.ConfigTipo configTipo) throws SQLException {
	    tableName = thisTable;
	    final int tipo = configTipo.getIdTipo();
		ConfigTipo configTipo2 = new ConfigTipo(tipo);
		buildList(db,configTipo2);	
	  }
  
  public LookupList(Connection db, String thisTable) throws SQLException {
	    tableName = thisTable;
	    
	    buildList(db);
	  }
  
  public LookupList(Connection db, String thisTable, String ordinamento)  throws SQLException {
	  tableName = thisTable;
	  buildList(db, ordinamento);
	  
  }

  
  public LookupList(String thisTable, Connection db, boolean withoutEnabled) throws SQLException 
  {
	    tableName = thisTable;
	    if(withoutEnabled)
	    	buildListWithoutEnabled(db);
	    else
	    	buildList(db);
	  }
  
  public LookupList(Connection db, String thisTable,boolean enabled) throws SQLException {
	    tableName = thisTable;
	    buildListWithEnabled(db);
	  }
  
  public void buildListComuni(Connection db, String thisTable,String filtro,ConfigTipo configTipo) throws SQLException {
	    tableName = thisTable;
	    this.filtro = filtro;
	    buildList(db,configTipo);
	  }
  
  public void buildListComuni(Connection db, String thisTable,String filtro) throws SQLException {
	    tableName = thisTable;
	    this.filtro = filtro;
	    buildList(db);
	  }

  
  public void removeItemfromLookup(Connection db,String table, String filtroElementidaCancellare) throws SQLException{
	  
	  PreparedStatement pst = db.prepareStatement(
		        "SELECT level " +
		        "FROM " +table+
		        " WHERE "+filtroElementidaCancellare);
	  
	  
	  ResultSet rs=pst.executeQuery();
	  int code=0;
	  while(rs.next()) {
		 
		  code=rs.getInt("level");
		  this.removeElementByLevel(code);
		 
	  }
	  
  }
  
public void removeItemfromLookupByCode(Connection db,String table, String filtroElementidaCancellare) throws SQLException{
	  
	  PreparedStatement pst = db.prepareStatement(
		        "SELECT code " +
		        "FROM " +table+
		        " WHERE "+filtroElementidaCancellare);
	  
	  
	  ResultSet rs=pst.executeQuery();
	  int code=0;
	  while(rs.next()) {
		 
		  code=rs.getInt("code");
		  this.removeElementByCode(code);
		 
	  }
	  
  }
  
  public void addItemPianiLocaliinMonitoraggio(Connection db,int site_id) throws SQLException{
	  
	  
	  PreparedStatement pst = db.prepareStatement(
		        "SELECT code,description " +
		        "FROM lookup_piano_monitoraggio"+
		        " WHERE site_id not in ("+site_id+") and enabled=true");
	 
	  
	  ResultSet rs=pst.executeQuery();
	  
	  while(rs.next()){
		  LookupElement thisElement = new LookupElement();
		  int code=rs.getInt("code");
		  String description=rs.getString("description");
		  thisElement.setDescription(description);
		  thisElement.setCode(code);
		
		  this.remove(  this.get(description));
		 // this.addItem(code, description);
		  
	  }
	  
  }
  

  /**
   *  Constructor for the LookupList object
   *
   * @param  db                Description of the Parameter
   * @param  moduleId          Description of the Parameter
   * @param  lookupId          Description of the Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of the Exception
   */
  public LookupList(Connection db, int moduleId, int lookupId) throws SQLException {
    PreparedStatement pst = db.prepareStatement(
        "SELECT lll.table_name AS tableName " +
        "FROM lookup_lists_lookup lll " +
        "WHERE lll.category_id = ? " +
        "AND lll.lookup_id = ? ");
    pst.setInt(1, moduleId);
    pst.setInt(2, lookupId);
    ResultSet rs = null;
    rs = pst.executeQuery();
    if (rs.next()) {
      this.setTableName(rs.getString("tableName"));
    } else {
      rs.close();
      pst.close();
      throw new SQLException("No lookup table found");
    }
    rs.close();
    pst.close();
  }


  /**
   *  Constructor for the LookupList object
   *
   * @param  vals              Description of Parameter
   * @param  names             Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public LookupList(String[] vals, String[] names) throws SQLException {

    for (int i = 0; i < vals.length; i++) {
      LookupElement thisElement = new LookupElement();
      thisElement.setDescription(names[i]);

      //as long as it is not a new entry

      if (!(vals[i].startsWith("*"))) {
        thisElement.setCode(Integer.parseInt(vals[i]));
      }

      thisElement.setLevel(i);
      this.add(thisElement);
    }
  }
  
  public LookupList(String[] vals, String[] names,boolean string) throws SQLException {

	    for (int i = 0; i < vals.length; i++) {
	      LookupElement thisElement = new LookupElement();
	      thisElement.setDescription(names[i]);

	      //as long as it is not a new entry

	      if (!(vals[i].startsWith("*"))) {
	        thisElement.setCode(vals[i]);
	      }

	      thisElement.setLevel(i);
	      this.add(thisElement);
	    }
	  }


  /**
   *  Gets the pagedListInfo attribute of the LookupList object
   *
   * @return    The pagedListInfo value
   */
  public PagedListInfo getPagedListInfo() {
    return pagedListInfo;
  }


  /**
   *  Sets the pagedListInfo attribute of the LookupList object
   *
   * @param  pagedListInfo  The new pagedListInfo value
   */
  public void setPagedListInfo(PagedListInfo pagedListInfo) {
    this.pagedListInfo = pagedListInfo;
  }


  /**
   *  Gets the selectedItems attribute of the LookupList object
   *
   * @return    The selectedItems value
   */
  public HashMap getSelectedItems() {
    return selectedItems;
  }


  /**
   *  Sets the selectedItems attribute of the LookupList object
   *
   * @param  tmp  The new selectedItems value
   */
  public void setSelectedItems(HashMap tmp) {
    this.selectedItems = tmp;
  }


  /**
   *  Gets the excludeDisabledIfUnselected attribute of the LookupList object
   *
   * @return    The excludeDisabledIfUnselected value
   */
  public boolean getExcludeDisabledIfUnselected() {
    return excludeDisabledIfUnselected;
  }


  /**
   *  Sets the excludeDisabledIfUnselected attribute of the LookupList object
   *
   * @param  tmp  The new excludeDisabledIfUnselected value
   */
  public void setExcludeDisabledIfUnselected(boolean tmp) {
    this.excludeDisabledIfUnselected = tmp;
  }


  /**
   *  Sets the excludeDisabledIfUnselected attribute of the LookupList object
   *
   * @param  tmp  The new excludeDisabledIfUnselected value
   */
  public void setExcludeDisabledIfUnselected(String tmp) {
    this.excludeDisabledIfUnselected = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Constructor for the LookupList object
   *
   * @param  db                Description of Parameter
   * @param  table             Description of Parameter
   * @param  fieldId           Description of Parameter
   * @exception  SQLException  Description of the Exception
   * @throws  SQLException     Description of Exception
   */
  public LookupList(Connection db, String table, int fieldId) throws SQLException {
    if (System.getProperty("DEBUG") != null) {
    }
    Statement st = null;
    ResultSet rs = null;

    StringBuffer sql = new StringBuffer();
    sql.append(
        "SELECT * " +
        "FROM " + table + " " +
        "WHERE field_id = " + fieldId + " " +
        "AND CURRENT_TIMESTAMP > start_date " +
        "AND (CURRENT_TIMESTAMP < end_date OR end_date IS NULL) " +
        "ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ", description ");

    st = db.createStatement();
    rs = st.executeQuery(sql.toString());
    while (rs.next()) {
      LookupElement thisElement = new LookupElement(rs);
      this.add(thisElement);
    }
    rs.close();
    st.close();
  }


  /**
   *  Sets the showDisabledFlag attribute of the LookupList object
   *
   * @param  showDisabledFlag  The new showDisabledFlag value
   */
  public void setShowDisabledFlag(boolean showDisabledFlag) {
    this.showDisabledFlag = showDisabledFlag;
  }


  /**
   *  Gets the showDisabledFlag attribute of the LookupList object
   *
   * @return    The showDisabledFlag value
   */
  public boolean getShowDisabledFlag() {
    return showDisabledFlag;
  }


  /**
   *  Sets the table attribute of the LookupList object
   *
   * @param  tmp  The new table value
   */
  public void setTable(String tmp) {
    this.tableName = tmp;
  }


  /**
   *  Sets the tableName attribute of the LookupList object
   *
   * @param  tmp  The new tableName value
   */
  public void setTableName(String tmp) {
    this.tableName = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the LookupList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(java.sql.Timestamp tmp) {
    this.lastAnchor = tmp;
  }


  /**
   *  Sets the lastAnchor attribute of the LookupList object
   *
   * @param  tmp  The new lastAnchor value
   */
  public void setLastAnchor(String tmp) {
    this.lastAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the nextAnchor attribute of the LookupList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(java.sql.Timestamp tmp) {
    this.nextAnchor = tmp;
  }


  /**
   *  Sets the nextAnchor attribute of the LookupList object
   *
   * @param  tmp  The new nextAnchor value
   */
  public void setNextAnchor(String tmp) {
    this.nextAnchor = java.sql.Timestamp.valueOf(tmp);
  }


  /**
   *  Sets the syncType attribute of the LookupList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(int tmp) {
    this.syncType = tmp;
  }


  /**
   *  Sets the syncType attribute of the LookupList object
   *
   * @param  tmp  The new syncType value
   */
  public void setSyncType(String tmp) {
    this.syncType = Integer.parseInt(tmp);
  }


  /**
   *  Sets the Multiple attribute of the LookupList object
   *
   * @param  multiple  The new Multiple value
   */
  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }


  /**
   *  Sets the JsEvent attribute of the LookupList object
   *
   * @param  tmp  The new JsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }
  
  public String getJsEvent() {
	    return jsEvent ;
	  }


  /**
   *  Sets the SelectSize attribute of the LookupList object
   *
   * @param  tmp  The new SelectSize value
   */
  public void setSelectSize(int tmp) {
    this.selectSize = tmp;
  }


  /**
   *  Sets the selectStyle attribute of the LookupList object
   *
   * @param  tmp  The new selectStyle value
   */
  public void setSelectStyle(String tmp) {
    this.selectStyle = tmp;
  }


  /**
   *  Gets the tableName attribute of the LookupList object
   *
   * @return    The tableName value
   */
  public String getTableName() {
    return tableName;
  }


  /**
   *  Gets the uniqueField attribute of the LookupList object
   *
   * @return    The uniqueField value
   */
  public String getUniqueField() {
    return uniqueField;
  }


  /**
   *  Gets the table attribute of the LookupList object
   *
   * @return    The table value
   */
  public String getTable() {
    return tableName;
  }


  /**
   *  Gets the Multiple attribute of the LookupList object
   *
   * @return    The Multiple value
   */
  public boolean getMultiple() {
    return multiple;
  }


  /**
   *  Gets the htmlSelectDefaultNone attribute of the LookupList object
   *
   * @param  selectName  Description of the Parameter
   * @param  thisSystem  Description of the Parameter
   * @return             The htmlSelectDefaultNone value
   */
  public String getHtmlSelectDefaultNone(SystemStatus thisSystem, String selectName) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.isGroup()) {
        // Create an option group
        thisSelect.addGroup(thisElement.getDescription());
      } else {
        // Add the item
        thisSelect.addItem(
            thisElement.getCode(),
            thisElement.getDescription());
      }
    }

    return thisSelect.getHtml(selectName);
  }


  /**
   *  Gets the enabledElementCount attribute of the LookupList object
   *
   * @return    The enabledElementCount value
   */
  public int getEnabledElementCount() {
    int count = 0;

    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.getEnabled() && !thisElement.getGroup()) {
        count++;
      }
    }
    return count;
  }
  /**
   *  Gets the firstEnabledElement attribute of the LookupList object
   *
   * @return    The firstEnabledElement value
   */
  public int getFirstEnabledElement() {
	int code = -1;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.getEnabled() && !thisElement.getGroup()) {
        code = thisElement.getCode();
        break;
      }
    }
    return code;
  }


  /**
   *  Gets the htmlSelect attribute of the LookupList object
   *
   * @param  selectName  Description of the Parameter
   * @param  defaultKey  Description of the Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, int defaultKey) {
    return getHtmlSelect(selectName, defaultKey, false);
  }
  
  
  public String getHtmlSelectDb(String selectName, int defaultKey,boolean obbligatorio,String label) {
	    return getHtmlSelectDb(selectName, defaultKey, false,obbligatorio,label);
	  }

  
  
  
 
  

  /**
   *  Gets the HtmlSelect attribute of the ContactEmailTypeList object
   *
   * @param  selectName  Description of Parameter
   * @param  defaultKey  Description of Parameter
   * @param  disabled    Description of the Parameter
   * @return             The HtmlSelect value
   * @since              1.1
   */
  public String getHtmlSelect(String selectName, int defaultKey, boolean disabled) {
    HtmlSelect thisSelect = new HtmlSelect();
   
    thisSelect.setIdName(selectName); 
    thisSelect.setRequired(required);
    thisSelect.setSelectSize(selectSize);
    thisSelect.setSelectStyle(selectStyle);
    thisSelect.setMultiple(multiple);
    thisSelect.setDisabled(disabled);
    thisSelect.setSearchable(searchable);
    thisSelect.setJsEvent(jsEvent);
    thisSelect.setTableName(tableName);
    thisSelect.setLabel(label);
    Iterator i = this.iterator();
    boolean keyFound = false;
    int lookupDefault = defaultKey;
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      
      
      HashMap<String, String> permessoAttr = null;
      if (thisElement.getPermission()!=null && !"".equals(thisElement.getPermission()))
      {
    	  permessoAttr = new HashMap<String, String>();
    	  permessoAttr.put("permesso", thisElement.getPermission());
      }
      
      if (thisElement.isGroup()) {
        // Create an option group
        thisSelect.addGroup(thisElement.getDescription());
      } else {
        // Add the item to the list
        if (thisElement.getEnabled() || !showDisabledFlag) {
          thisSelect.addItem(
              thisElement.getCode(), thisElement.getDescription(),permessoAttr,true);
          if (thisElement.getDefaultItem()) {
            lookupDefault = thisElement.getCode();
          }
        } else if (thisElement.getCode() == defaultKey) {
          thisSelect.addItem(
              thisElement.getCode(), thisElement.getDescription(),permessoAttr,true);
        }
        //Handle --None- case
        if (thisElement.getCode() == defaultKey && defaultKey > -1) {
          keyFound = true;
        }
      }
    }
      if (keyFound) {
      return thisSelect.getHtml(selectName, defaultKey);
    } else {
    	thisSelect.setMultipleSelects(multipleSelects);
      return thisSelect.getHtml(selectName, lookupDefault);
    }
  }
  
  
  public String getHtmlSelectDb(String selectName, int defaultKey, boolean disabled,boolean obbligatorio,String label) {
	    HtmlSelect thisSelect = new HtmlSelect();
	   
	    thisSelect.setIdName(selectName); 
	    thisSelect.setSelectSize(selectSize);
	    thisSelect.setSelectStyle(selectStyle);
	    thisSelect.setMultiple(multiple);
	    thisSelect.setDisabled(disabled);
	    thisSelect.setJsEvent(jsEvent);
	    Iterator i = this.iterator();
	    boolean keyFound = false;
	    int lookupDefault = defaultKey;
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      if (thisElement.isGroup()) {
	        // Create an option group
	        thisSelect.addGroup(thisElement.getDescription());
	      } else {
	        // Add the item to the list
	        if (thisElement.getEnabled() || !showDisabledFlag) {
	          thisSelect.addItem(
	              thisElement.getCode(), thisElement.getDescription());
	          if (thisElement.getDefaultItem()) {
	            lookupDefault = thisElement.getCode();
	          }
	        } else if (thisElement.getCode() == defaultKey) {
	          thisSelect.addItem(
	              thisElement.getCode(), thisElement.getDescription());
	        }
	        //Handle --None- case
	        if (thisElement.getCode() == defaultKey && defaultKey > -1) {
	          keyFound = true;
	        }
	      }
	    }
	      if (keyFound) {
	      return thisSelect.getHtmlDb(selectName, defaultKey,obbligatorio,label);
	    } else {
	    	thisSelect.setMultipleSelects(multipleSelects);
	      return thisSelect.getHtmlDb(selectName, lookupDefault,obbligatorio,label);
	    }
	  }

//  public String getHtmlSelectDbDefaultString(String selectName, String defaultKey, boolean disabled,boolean obbligatorio,String label) {
//	    HtmlSelect thisSelect = new HtmlSelect();
//	   
//	    thisSelect.setIdName(selectName); 
//	    thisSelect.setSelectSize(selectSize);
//	    thisSelect.setSelectStyle(selectStyle);
//	    thisSelect.setMultiple(multiple);
//	    thisSelect.setDisabled(disabled);
//	    thisSelect.setJsEvent(jsEvent);
//	    Iterator i = this.iterator();
//	    boolean keyFound = false;
//	    int lookupDefault = defaultKey;
//	    while (i.hasNext()) {
//	      LookupElement thisElement = (LookupElement) i.next();
//	      if (thisElement.isGroup()) {
//	        // Create an option group
//	        thisSelect.addGroup(thisElement.getDescription());
//	      } else {
//	        // Add the item to the list
//	        if (thisElement.getEnabled() || !showDisabledFlag) {
//	          thisSelect.addItem(
//	              thisElement.getCode(), thisElement.getDescription());
//	          if (thisElement.getDefaultItem()) {
//	            lookupDefault = thisElement.getCode();
//	          }
//	        } else if (thisElement.getCode() == defaultKey) {
//	          thisSelect.addItem(
//	              thisElement.getCode(), thisElement.getDescription());
//	        }
//	        //Handle --None- case
//	        if (thisElement.getCode() == defaultKey && defaultKey > -1) {
//	          keyFound = true;
//	        }
//	      }
//	    }
//	      if (keyFound) {
//	      return thisSelect.getHtmlDb(selectName, defaultKey,obbligatorio,label);
//	    } else {
//	    	thisSelect.setMultipleSelects(multipleSelects);
//	      return thisSelect.getHtmlDb(selectName, lookupDefault,obbligatorio,label);
//	    }
//	  }

  /**
   *  Gets the htmlSelectObj attribute of the LookupList object
   *
   * @param  defaultKey  Description of the Parameter
   * @return             The htmlSelectObj value
   */
  public HtmlSelect getHtmlSelectObj(int defaultKey) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setSelectSize(selectSize);
    thisSelect.setMultiple(multiple);
    thisSelect.setJsEvent(jsEvent);

    Iterator i = this.iterator();
    boolean keyFound = false;
    int lookupDefault = defaultKey;

    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.isGroup()) {
        // Create an option group
        thisSelect.addGroup(thisElement.getDescription());
      } else {
        //Add the item
        if (thisElement.getEnabled() == true || !showDisabledFlag) {
          thisSelect.addItem(
              thisElement.getCode(), thisElement.getDescription());
          if (thisElement.getDefaultItem()) {
            lookupDefault = thisElement.getCode();
          }
        } else if (thisElement.getCode() == defaultKey) {
          thisSelect.addItem(
              thisElement.getCode(), thisElement.getDescription());
        }
        if (thisElement.getCode() == defaultKey) {
          keyFound = true;
        }
      }
    }
    if (keyFound) {
      thisSelect.setDefaultKey(defaultKey);
    } else {
      thisSelect.setDefaultKey(lookupDefault);
    }
    return thisSelect;
  }


  /**
   *  Gets the htmlSelect attribute of the LookupList object
   *
   * @param  selectName    Description of the Parameter
   * @param  defaultValue  Description of the Parameter
   * @return               The htmlSelect value
   */
  public String getHtmlSelect(String selectName, String defaultValue) {
    return getHtmlSelect(selectName, defaultValue, false);
  }
  
  public String getHtmlSelectText(String selectName, String defaultValue) {
	    return getHtmlSelecText(selectName, defaultValue, false);
	  }

  public String getHtmlSelectWithdisabled(String selectName, String defaultValue) {
	    return getHtmlSelectWithDiabled(selectName, defaultValue, excludeDisabledIfUnselected);
	  }

  /**
   *  Gets the HtmlSelect attribute of the ContactEmailTypeList object
   *
   * @param  selectName    Description of Parameter
   * @param  defaultValue  Description of Parameter
   * @param  disabled      Description of the Parameter
   * @return               The HtmlSelect value
   * @since                1.1
   */
  public String getHtmlSelect(String selectName, String defaultValue, boolean disabled) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setIdName(selectName);
    thisSelect.setSelectSize(selectSize);
    thisSelect.setSelectStyle(selectStyle);
    thisSelect.setJsEvent(jsEvent);
    thisSelect.setDisabled(disabled);
    Iterator i = this.iterator();
    boolean keyFound = false;
    String lookupDefault = null;
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.isGroup()) {
        // Create an option group
        thisSelect.addGroup(thisElement.getDescription());
      } else {
        // Add the item
        if (thisElement.getEnabled() == true) {
          thisSelect.addItem(
              thisElement.getCode(), thisElement.getDescription());
        } else if (thisElement.getDescription().equals(defaultValue)) {
          keyFound = true;
          thisSelect.addItem(
              thisElement.getCode(), thisElement.getDescription());
        }
        if (thisElement.getDescription().equals(defaultValue)) {
          keyFound = true;
        }
        if (thisElement.getDefaultItem()) {
          lookupDefault = thisElement.getDescription();
        }
      }
    }
    return thisSelect.getHtml(selectName, defaultValue);
  }
  
  
  public String getHtmlSelectWithDiabled(String selectName, String defaultValue, boolean disabled) {
	    HtmlSelect thisSelect = new HtmlSelect();
	    thisSelect.setIdName(selectName);
	    thisSelect.setSelectSize(selectSize);
	    thisSelect.setSelectStyle(selectStyle);
	    thisSelect.setJsEvent(jsEvent);
	    thisSelect.setDisabled(disabled);
	    Iterator i = this.iterator();
	    boolean keyFound = false;
	    String lookupDefault = null;
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      if (thisElement.isGroup()) {
	        // Create an option group
	        thisSelect.addGroup(thisElement.getDescription());
	      } else {
	        // Add the item
	        if (thisElement.getEnabled() == true || thisElement.getEnabled() == false ) {
	          thisSelect.addItem(
	              thisElement.getCode(), thisElement.getDescription());
	        } else if (thisElement.getDescription().equals(defaultValue)) {
	          keyFound = true;
	          thisSelect.addItem(
	              thisElement.getCode(), thisElement.getDescription());
	        }
	        if (thisElement.getDescription().equals(defaultValue)) {
	          keyFound = true;
	        }
	        if (thisElement.getDefaultItem()) {
	          lookupDefault = thisElement.getDescription();
	        }
	      }
	    }
	    return thisSelect.getHtml(selectName, defaultValue);
	  }
  
  
  public String getHtmlSelecText(String selectName, String defaultValue, boolean disabled) {
	    HtmlSelect thisSelect = new HtmlSelect();
	    thisSelect.setIdName(selectName);
	    thisSelect.setSelectSize(selectSize);
	    thisSelect.setRequired(required);
	    thisSelect.setSelectStyle(selectStyle);
	    thisSelect.setJsEvent(jsEvent);
	    thisSelect.setDisabled(disabled);
	    Iterator i = this.iterator();
	    boolean keyFound = false;
	    String lookupDefault = null;
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      if (thisElement.isGroup()) {
	        // Create an option group
	        thisSelect.addGroup(thisElement.getDescription());
	      } else {
	        // Add the item
	        if (thisElement.getEnabled() == true) {
	          thisSelect.addItem(
	              thisElement.getCode(), thisElement.getDescription());
	        } else if (thisElement.getDescription().equals(defaultValue)) {
	          keyFound = true;
	          thisSelect.addItem(
	              thisElement.getCode(), thisElement.getDescription());
	        }
	        if (thisElement.getDescription().equals(defaultValue)) {
	          keyFound = true;
	        }
	        if (thisElement.getDefaultItem()) {
	          lookupDefault = thisElement.getDescription();
	        }
	      }
	    }
	    return thisSelect.getHtmlText(selectName, defaultValue);
	  }

  
  

  /**
   *  Gets the htmlSelect attribute of the LookupList object
   *
   * @param  selectName  Description of Parameter
   * @param  ms          Description of Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, LookupList ms) {
    HtmlSelect thisSelect = new HtmlSelect();
    thisSelect.setIdName(selectName);
    thisSelect.setSelectSize(selectSize);
    thisSelect.setSelectStyle(selectStyle);
    thisSelect.setJsEvent(jsEvent);
    thisSelect.setMultiple(multiple);
    thisSelect.setMultipleSelects(ms);
    thisSelect.setRequired(required);
    thisSelect.setLabel(label);
    Iterator i = this.iterator();
    boolean keyFound = false;
    String lookupDefault = null;
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.isGroup()) {
        // Create an option group
        thisSelect.addGroup(thisElement.getDescription());
      } else {
        // Add the item
        if (thisElement.getEnabled() == true) {
          thisSelect.addItem(
              thisElement.getCode(), thisElement.getDescription());
        }
        if (thisElement.getDefaultItem()) {
          lookupDefault = thisElement.getDescription();
        }
      }
    }
    return thisSelect.getHtml(selectName);
  }


  /**
   *  Gets the selectedKey attribute of the LookupList object
   *
   * @return    The selectedKey value
   */
  public int getSelectedKey() {
    Iterator i = this.iterator();
    LookupElement keyFound = null;
    int x = 0;
    while (i.hasNext()) {
      ++x;
      LookupElement thisElement = (LookupElement) i.next();
      if (x == 1) {
        keyFound = thisElement;
      }
      try {
        if (thisElement.getCode() == Integer.parseInt(defaultKey)) {
          return thisElement.getCode();
        }
      } catch (Exception e) {
      }
      if (thisElement.getDefaultItem()) {
        keyFound = thisElement;
      }
    }
    if (keyFound != null) {
      return keyFound.getCode();
    } else {
      return -1;
    }
  }


  /**
   *  Gets the SelectedValue attribute of the LookupList object
   *
   * @param  selectedId  Description of Parameter
   * @return             The SelectedValue value
   */
  public String getSelectedValue(int selectedId) {
    Iterator i = this.iterator();
    
    LookupElement keyFound = null;	 
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.getCode() == selectedId) {
        return thisElement.getDescription();
      }
      if (thisElement.getDefaultItem()) {
        keyFound = thisElement;
      }
    }
    if (keyFound != null) {
      return keyFound.getDescription();
    } else {
      return "";
    }
  }
  
  public int getValueFromCodiceInterno(String codiceInterno) {
	    Iterator i = this.iterator();
	    
	    LookupElement keyFound = null;	 
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      if (codiceInterno.equalsIgnoreCase(thisElement.getCodiceInterno())) {
	        return thisElement.getCode();
	      }
	      if (thisElement.getDefaultItem()) {
	        keyFound = thisElement;
	      }
	    }
	    if (keyFound != null) {
	      return keyFound.getCode();
	    } else {
	      return -1;
	    }
	  }
  
  public ArrayList<Integer> getCodes() {
	  
	    ArrayList<Integer> codes = new ArrayList<Integer>();
	    Iterator i = this.iterator();
	    
	    LookupElement elem = null;
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      codes.add(thisElement.getCode());
	      
	    }
	    return codes;
	  }

  public String getSelectedValueShort(String selectedId, Connection db) throws SQLException {
	    Iterator i = this.iterator();
	    PreparedStatement pst = null;
	    LookupElement keyFound = null;
	    String shortDescription = null;
	    ResultSet rs = null;
	    
	    StringBuffer sqlCount = new StringBuffer();
	    	   
	    	    sqlCount.append(
	    	        "SELECT short_description " +
	    	        "FROM lookup_codistat " +
	    	        "WHERE description = '"+selectedId+"'");
	        
	    	   pst = db.prepareStatement(sqlCount.toString());
	    	   rs = pst.executeQuery();
	    	   if (rs.next()) {
	    	          shortDescription = rs.getString("short_description");
	    	          
	    	        }
	    	        rs.close();
	    	        pst.close();
	        
	    return shortDescription;
	  }


  /**
   *  Gets the selectedValue attribute of the LookupList object
   *
   * @param  selectedId  Description of Parameter
   * @return             The selectedValue value
   */
  public String getSelectedValue(String selectedId) {
    try {
      return getSelectedValue(Integer.parseInt(selectedId));
    } catch (Exception e) {
      return "";
    }
  }


  /**
   *  Gets the SelectedValue attribute of the LookupList object
   *
   * @param  selectedId  Description of Parameter
   * @return             The SelectedValue value
   */
  public String getValueFromId(int selectedId) {
    return getSelectedValue(selectedId);
  }


  /**
   *  Gets the selectedValue attribute of the LookupList object
   *
   * @param  selectedId  Description of Parameter
   * @return             The selectedValue value
   */
  public String getValueFromId(String selectedId) {
    return getSelectedValue(selectedId);
  }


  /**
   *  Gets the object attribute of the LookupList object
   *
   * @param  rs             Description of Parameter
   * @return                The object value
   * @throws  SQLException  Description of Exception
   */
  public LookupElement getObject(ResultSet rs) throws SQLException {
    LookupElement thisElement = new LookupElement(rs);
    return thisElement;
  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void select(Connection db,ConfigTipo configTipo) throws SQLException {
    buildList(db,configTipo);
  }
  
  public void select(Connection db) throws SQLException {
	    buildList(db);
	  }


  /**
   *  Description of the Method
   *
   * @param  db             Description of Parameter
   * @throws  SQLException  Description of Exception
   */
  public void buildList(Connection db,ConfigTipo configTipo) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = queryList(db, pst,configTipo);
    if (pagedListInfo != null) {
      pagedListInfo.doManualOffset(db, rs);
    }
    while (rs.next()) {
      LookupElement thisElement = this.getObject(rs);
      if (thisElement.getEnabled() == true || !showDisabledFlag || hasItem(
          thisElement.getCode())) {
        this.add(thisElement);
      }
    }
    rs.close();
    if (pst != null) {
      pst.close();
    }
  }
  
  public void buildList(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryList(db, pst);
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      LookupElement thisElement = this.getObject(rs);
	      if (thisElement.getEnabled() == true || !showDisabledFlag || hasItem(
	          thisElement.getCode())) {
	        this.add(thisElement);
	      }
	    }
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	  }
  
  public void buildList(Connection db, String ordinamento) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryList(db, pst, ordinamento);
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      LookupElement thisElement = this.getObject(rs);
	      if (thisElement.getEnabled() == true || !showDisabledFlag || hasItem(
	          thisElement.getCode())) {
	        this.add(thisElement);
	      }
	    }
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	  }

  
  public void buildListWithoutEnabled(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryListWithoutEnabled(db, pst);
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      LookupElement thisElement = this.getObject(rs);
	      if (thisElement.getEnabled() == true || !showDisabledFlag || hasItem(
	          thisElement.getCode())) {
	        this.add(thisElement);
	      }
	    }
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	  }
  
  public void buildListWithEnabled(Connection db,ConfigTipo configTipo) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryListWithEnabled(db, pst,configTipo);
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      LookupElement thisElement = this.getObject(rs);
	      if (thisElement.getEnabled() == true || !showDisabledFlag || hasItem(
	          thisElement.getCode())) {
	        this.add(thisElement);
	      }
	    }
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	  }
  
  public void buildListWithEnabled(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryListWithEnabled(db, pst);
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      LookupElement thisElement = this.getObject(rs);
	      if (thisElement.getEnabled() == true || !showDisabledFlag || hasItem(
	          thisElement.getCode())) {
	        this.add(thisElement);
	      }
	    }
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	  }
  
  
  
  public void buildListNucleoIspettivo(Connection db) throws SQLException {
	    PreparedStatement pst = null;
	    ResultSet rs = queryListWithEnabledInNucleo(db, pst);
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    while (rs.next()) {
	      LookupElement thisElement = this.getObject(rs);
	      if (thisElement.getEnabled() == true || !showDisabledFlag || hasItem(
	          thisElement.getCode())) {
	        this.add(thisElement);
	      }
	    }
	    rs.close();
	    if (pst != null) {
	      pst.close();
	    }
	  }

 
  
  public LookupList(ArrayList<Comuni> lista) 
  {
	 for(Comuni c : lista)
	 {
	      LookupElement thisElement = new LookupElement();
	      
	      thisElement.setDescription(c.getComune());
	      //thisElement.setCode(c.getCodice()); //Aggiunto Veronica
	      thisElement.setEnabled(true);
	      if (thisElement.getEnabled() == true || !showDisabledFlag || hasItem(
	          thisElement.getCode())) {
	        this.add(thisElement);
	      }
	    }
  }
  

  public LookupList(ArrayList<Comune> lista,boolean flag) 
  {
	 for(Comune c : lista)
	 {
	      LookupElement thisElement = new LookupElement();
	      
	      thisElement.setDescription(c.getDescrizione());
	      thisElement.setCode(c.getId());
	      //thisElement.setCode(c.getCodice()); //Aggiunto Veronica
	      thisElement.setEnabled(true);
	      if (thisElement.getEnabled() == true || !showDisabledFlag || hasItem(
	          thisElement.getCode())) {
	        this.add(thisElement);
	      }
	    }
  }
  
  
  public LookupList(ArrayList<ComuniAnagrafica> lista, int id) 
  {
	 for(ComuniAnagrafica c : lista)
	 {
	      LookupElement thisElement = new LookupElement();
	      
	      thisElement.setDescription(c.getDescrizione());
	      thisElement.setCode(Integer.parseInt(c.getCodice())); //Aggiunto Veronica
	      thisElement.setEnabled(true);
	      if (thisElement.getEnabled() == true || !showDisabledFlag || hasItem(
	          thisElement.getCode())) {
	        this.add(thisElement);
	      }
	    }
  }
  


public void queryListComuni(ArrayList<org.aspcfs.modules.opu.base.ComuniAnagrafica> lista, int id) 
  {
	 for(org.aspcfs.modules.opu.base.ComuniAnagrafica c : lista)
	 {
	      LookupElement thisElement = new LookupElement();
	      
	      thisElement.setDescription(c.getDescrizione());
	      thisElement.setCode(Integer.parseInt(c.getCodice())); //Aggiunto Veronica
	      thisElement.setEnabled(true);
	      if (thisElement.getEnabled() == true || !showDisabledFlag || hasItem(
	          thisElement.getCode())) {
	        this.add(thisElement);
	      }
	    }
  }
  
 

  /**
   *  This method is required for synchronization, it allows for the resultset
   *  to be streamed with lower overhead
   *
   * @param  db             Description of the Parameter
   * @param  pst            Description of the Parameter
   * @return                Description of the Return Value
   * @throws  SQLException  Description of the Exception
   */
  public ResultSet queryList(Connection db, PreparedStatement pst,ConfigTipo configTipo) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlSelect = new StringBuffer();
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM " + DatabaseUtils.getTableName(db, tableName) + " a " +
	        "WHERE code > -1 and enabled = true ");
	    if(tableName.equals("m_lookup_piani_risanamento") || 
	       tableName.equals("m_lookup_motivi_comunicazioni_asl") || 
	       tableName.equals("m_lookup_specie") ||
	       tableName.equals("m_lookup_campioni_motivi") ||
	       tableName.equals("m_lookup_patologie") ||
		 	  tableName.equals("m_lookup_matrici"))
	    	createFilter(sqlFilter,configTipo);
	    else
	    	createFilter(sqlFilter);
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
	      items = prepareFilter(pst);
	      pagedListInfo.doManualOffset(db, pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	        pst = db.prepareStatement(
	            sqlCount.toString() + sqlFilter.toString() +
	            "AND description < ? ");
	        items = prepareFilter(pst);
	        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
	        if (pagedListInfo != null) {
	          pagedListInfo.doManualOffset(db, pst);
	        }
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }

	      //Determine column to sort by
	      if (pagedListInfo.getColumnToSortBy() == null || "".equals(pagedListInfo.getColumnToSortBy())) {
	        pagedListInfo.setDefaultSort(
	            "enabled DESC," + DatabaseUtils.addQuotes(db, "level") + ",description", null);
	      }
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	    } else {
	      sqlOrder.append(" ORDER BY enabled DESC," + DatabaseUtils.addQuotes(db, "level") + ",description ");
	    }
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(
	        "lt.* " +
	        "FROM " + tableName + " lt " +
	        "WHERE code > -1 and enabled = true ");
	    
	    if( ! filtro.equals("") && idAsl!=-1 )
	    {
	    	 sqlSelect.append(filtro);
	    }
	    
	    
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    items = prepareFilter(pst);
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }
  
  public ResultSet queryList(Connection db, PreparedStatement pst, String ordinamento) throws SQLException {
	  ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlSelect = new StringBuffer();
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM " + DatabaseUtils.getTableName(db, tableName) + " a " +
	        "WHERE code > -1 and enabled = true ");
	    createFilter(sqlFilter);
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
	      items = prepareFilter(pst);
	      pagedListInfo.doManualOffset(db, pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	        pst = db.prepareStatement(
	            sqlCount.toString() + sqlFilter.toString() +
	            "AND description < ? ");
	        items = prepareFilter(pst);
	        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
	        if (pagedListInfo != null) {
	          pagedListInfo.doManualOffset(db, pst);
	        }
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }

	      //Determine column to sort by
	      if (pagedListInfo.getColumnToSortBy() == null || "".equals(pagedListInfo.getColumnToSortBy())) {
	        pagedListInfo.setDefaultSort(
	            "enabled DESC," + DatabaseUtils.addQuotes(db, "level") + ",description", null);
	      }
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	    } else {
	      sqlOrder.append("ORDER BY "+ordinamento);
	    }
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(
	        "lt.* " +
	        "FROM " + tableName + " lt " +
	        "WHERE code > -1 and enabled = true ");
	    
	    if( ! filtro.equals("") && idAsl!=-1 )
	    {
	    	 sqlSelect.append(filtro);
	    }
	    
	    
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    items = prepareFilter(pst);
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }
  
  
  public ResultSet queryList(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlSelect = new StringBuffer();
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM " + DatabaseUtils.getTableName(db, tableName) + " a " +
	        "WHERE code > -1 and enabled = true ");
	    createFilter(sqlFilter);
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
	      items = prepareFilter(pst);
	      pagedListInfo.doManualOffset(db, pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	        pst = db.prepareStatement(
	            sqlCount.toString() + sqlFilter.toString() +
	            "AND description < ? ");
	        items = prepareFilter(pst);
	        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
	        if (pagedListInfo != null) {
	          pagedListInfo.doManualOffset(db, pst);
	        }
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }

	      //Determine column to sort by
	      if (pagedListInfo.getColumnToSortBy() == null || "".equals(pagedListInfo.getColumnToSortBy())) {
	        pagedListInfo.setDefaultSort(
	            "enabled DESC," + DatabaseUtils.addQuotes(db, "level") + ",description", null);
	      }
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	    } else {
	      sqlOrder.append("ORDER BY enabled DESC," + DatabaseUtils.addQuotes(db, "level") + ",description ");
	    }
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(
	        "lt.* " +
	        "FROM " + tableName + " lt " +
	        "WHERE code > -1 and enabled = true ");
	    
	    if( ! filtro.equals("") && idAsl!=-1 )
	    {
	    	 sqlSelect.append(filtro);
	    }
	    
	    
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    items = prepareFilter(pst);
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }
  
  
  public ResultSet queryListWithoutEnabled(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlSelect = new StringBuffer();
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM " + DatabaseUtils.getTableName(db, tableName) + " a " +
	        "WHERE code > -1 ");
	    createFilter(sqlFilter);
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
	      items = prepareFilter(pst);
	      pagedListInfo.doManualOffset(db, pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	        pst = db.prepareStatement(
	            sqlCount.toString() + sqlFilter.toString() +
	            "AND description < ? ");
	        items = prepareFilter(pst);
	        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
	        if (pagedListInfo != null) {
	          pagedListInfo.doManualOffset(db, pst);
	        }
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }

	      //Determine column to sort by
	      if (pagedListInfo.getColumnToSortBy() == null || "".equals(pagedListInfo.getColumnToSortBy())) {
	        pagedListInfo.setDefaultSort(
	            "enabled DESC," + DatabaseUtils.addQuotes(db, "level") + ",description", null);
	      }
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	    } else {
	      sqlOrder.append("ORDER BY enabled DESC," + DatabaseUtils.addQuotes(db, "level") + ",description ");
	    }
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(
	        "lt.* " +
	        "FROM " + tableName + " lt " +
	        "WHERE code > -1  ");
	    
	    if( ! filtro.equals("") && idAsl!=-1 )
	    {
	    	 sqlSelect.append(filtro);
	    }
	    
	    
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    items = prepareFilter(pst);
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }
  
  public ResultSet queryListWithEnabled(Connection db, PreparedStatement pst, ConfigTipo configTipo) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlSelect = new StringBuffer();
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM " + DatabaseUtils.getTableName(db, tableName) + " p " +
	        "WHERE code > -1  ");
	    if(tableName.equals("m_lookup_piani_risanamento") || 
	       tableName.equals("m_lookup_motivi_comunicazioni_asl") ||
	       tableName.equals("m_lookup_specie") ||
	 	   tableName.equals("m_lookup_campioni_motivi") ||
	 	  tableName.equals("m_lookup_patologie") ||
	 	   tableName.equals("m_lookup_matrici"))
	    	createFilter(sqlFilter,configTipo);
	    else
	    	createFilter(sqlFilter);
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
	      items = prepareFilter(pst);
	      pagedListInfo.doManualOffset(db, pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	        pst = db.prepareStatement(
	            sqlCount.toString() + sqlFilter.toString() +
	            "AND description < ? ");
	        items = prepareFilter(pst);
	        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
	        if (pagedListInfo != null) {	
	          pagedListInfo.doManualOffset(db, pst);
	        }
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }

	      //Determine column to sort by
	      if (pagedListInfo.getColumnToSortBy() == null || "".equals(pagedListInfo.getColumnToSortBy())) {
	        pagedListInfo.setDefaultSort(
	            "enabled DESC," + DatabaseUtils.addQuotes(db, "level") + ",description", null);
	      }
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	    } else {
	      sqlOrder.append("ORDER BY enabled DESC," + DatabaseUtils.addQuotes(db, "level") + ",description ");
	    }
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(
	        "lt.* " +
	        "FROM " + DatabaseUtils.getTableName(db, tableName) + " lt " +
	        "WHERE code > -1 ");
	    
	    if( ! filtro.equals("") && idAsl!=-1 )
	    {
	    	 sqlSelect.append(filtro);
	    }
	    
	    
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    items = prepareFilter(pst);
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }
  
  public ResultSet queryListWithEnabled(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlSelect = new StringBuffer();
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM " + DatabaseUtils.getTableName(db, tableName) + " p " +
	        "WHERE code > -1  ");
	    createFilter(sqlFilter);
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
	      items = prepareFilter(pst);
	      pagedListInfo.doManualOffset(db, pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	        pst = db.prepareStatement(
	            sqlCount.toString() + sqlFilter.toString() +
	            "AND description < ? ");
	        items = prepareFilter(pst);
	        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
	        if (pagedListInfo != null) {	
	          pagedListInfo.doManualOffset(db, pst);
	        }
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }

	      //Determine column to sort by
	      if (pagedListInfo.getColumnToSortBy() == null || "".equals(pagedListInfo.getColumnToSortBy())) {
	        pagedListInfo.setDefaultSort(
	            "enabled DESC," + DatabaseUtils.addQuotes(db, "level") + ",description", null);
	      }
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	    } else {
	      sqlOrder.append("ORDER BY enabled DESC," + DatabaseUtils.addQuotes(db, "level") + ",description ");
	    }
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(
	        "lt.* " +
	        "FROM " + DatabaseUtils.getTableName(db, tableName) + " lt " +
	        "WHERE code > -1 ");
	    
	    if( ! filtro.equals("") && idAsl!=-1 )
	    {
	    	 sqlSelect.append(filtro);
	    }
	    
	    
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    items = prepareFilter(pst);
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }
  
  
  
  public ResultSet queryListWithEnabledInNucleo(Connection db, PreparedStatement pst) throws SQLException {
	    ResultSet rs = null;
	    int items = -1;
	    StringBuffer sqlCount = new StringBuffer();
	    StringBuffer sqlOrder = new StringBuffer();
	    StringBuffer sqlFilter = new StringBuffer();
	    StringBuffer sqlSelect = new StringBuffer();
	    sqlCount.append(
	        "SELECT COUNT(*) AS recordcount " +
	        "FROM " + DatabaseUtils.getTableName(db, tableName) + " p " +
	        "WHERE code > -1 and in_nucleo_ispettivo=true  ");
	    createFilter(sqlFilter);
	    if (pagedListInfo != null) {
	      //Get the total number of records matching filter
	      pst = db.prepareStatement(sqlCount.toString() + sqlFilter.toString());
	      items = prepareFilter(pst);
	      pagedListInfo.doManualOffset(db, pst);
	      rs = pst.executeQuery();
	      if (rs.next()) {
	        int maxRecords = rs.getInt("recordcount");
	        pagedListInfo.setMaxRecords(maxRecords);
	      }
	      rs.close();
	      pst.close();
	      //Determine the offset, based on the filter, for the first record to show
	      if (!pagedListInfo.getCurrentLetter().equals("")) {
	        pst = db.prepareStatement(
	            sqlCount.toString() + sqlFilter.toString() +
	            "AND description < ? ");
	        items = prepareFilter(pst);
	        pst.setString(++items, pagedListInfo.getCurrentLetter().toLowerCase());
	        if (pagedListInfo != null) {	
	          pagedListInfo.doManualOffset(db, pst);
	        }
	        rs = pst.executeQuery();
	        if (rs.next()) {
	          int offsetCount = rs.getInt("recordcount");
	          pagedListInfo.setCurrentOffset(offsetCount);
	        }
	        rs.close();
	        pst.close();
	      }

	      //Determine column to sort by
	      if (pagedListInfo.getColumnToSortBy() == null || "".equals(pagedListInfo.getColumnToSortBy())) {
	        pagedListInfo.setDefaultSort(
	            DatabaseUtils.addQuotes(db, "level") + ",description,enabled DESC", null);
	      }
	      pagedListInfo.appendSqlTail(db, sqlOrder);
	    } else {
	      sqlOrder.append("ORDER BY " + DatabaseUtils.addQuotes(db, "level") + ",description, enabled DESC ");
	    }
	    if (pagedListInfo != null) {
	      pagedListInfo.appendSqlSelectHead(db, sqlSelect);
	    } else {
	      sqlSelect.append("SELECT ");
	    }
	    sqlSelect.append(
	        "lt.* " +
	        "FROM " + DatabaseUtils.getTableName(db, tableName) + " lt " +
	        "WHERE code > -1 and in_nucleo_ispettivo=true  ");
	    
	    if( ! filtro.equals("") && idAsl!=-1 )
	    {
	    	 sqlSelect.append(filtro);
	    }
	    
	    
	    pst = db.prepareStatement(
	        sqlSelect.toString() + sqlFilter.toString() + sqlOrder.toString());
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, pst);
	    }
	    items = prepareFilter(pst);
	    rs = pst.executeQuery();
	    if (pagedListInfo != null) {
	      pagedListInfo.doManualOffset(db, rs);
	    }
	    return rs;
	  }



  /**
   *  Description of the Method
   *
   * @param  key  Description of Parameter
   * @return      Description of the Returned Value
   */
  public boolean containsKey(int key) {
    Iterator i = this.iterator();
    boolean keyFound = false;

    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();

      if (thisElement.getEnabled() == true && thisElement.getCode() == key) {
        keyFound = true;
      }
    }

    return keyFound;
  }


  /**
   *  Description of the Method
   *
   * @return    Description of the Returned Value
   */
  public String valuesAsString() {
    Iterator i = this.iterator();
    String result = "";
    int count = 0;
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (count > 0) {
        result += ", " + thisElement.getDescription();
      } else {
        result += thisElement.getDescription();
      }
      count++;
    }
    return result;
  }


  /**
   *  Gets the idFromLevel attribute of the LookupList object
   *
   * @param  level  Description of the Parameter
   * @return        The idFromLevel value
   */
  public int getIdFromLevel(int level) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.getLevel() == level) {
        return thisElement.getId();
      }
    }
    return -1;
  }


  /**
   *  Gets the idFromValue attribute of the LookupList object
   *
   * @param  value  Description of the Parameter
   * @return        The idFromValue value
   */
  public int getIdFromValue(String value) {
	  
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (value.equalsIgnoreCase(thisElement.getDescription()) || thisElement.getDescription().contains(value)) {
        return thisElement.getId();
      }else if (thisElement.getDescription().length()>6 && value.equalsIgnoreCase(thisElement.getDescription().substring(0,thisElement.getDescription().length() - 5))){
    	  return thisElement.getId();
      }
      
    }
    return -1;
  }
  
  
  public int getIdFromDescription(String value) {
	  
	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      if (value.equals(thisElement.getDescription())) {
	        return thisElement.getId();
	      }else if (value.equals(thisElement.getDescription())){
	    	  return thisElement.getId();
	      }
	      
	    }
	    return -1;
	  }


  /**
   *  Gets the levelFromId attribute of the LookupList object
   *
   * @param  id  Description of the Parameter
   * @return     The levelFromId value
   */
  public int getLevelFromId(int id) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.getCode() == id) {
        return thisElement.getLevel();
      }
    }
    return -1;
  }


  /**
   *  Description of the Method
   *
   * @param  value  Description of the Parameter
   * @return        Description of the Return Value
   */
  public LookupElement get(String value) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      
      if (value.equals(thisElement.getDescription())) {
        return thisElement;
      }
    }
    return null;
  }
 
  public LookupElement getElementfromValue(int value) {
	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      
	      if (value == thisElement.getCode()) {
	        return thisElement;
	      }
	    }
	    return null;
	  }
  
  
  public LookupElement getElementfromCodiceInterno(String codiceInterno ) {
	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      
	      if (codiceInterno.equalsIgnoreCase(thisElement.getCodiceInterno())) {
	        return thisElement;
	      }
	    }
	    return null;
	  }
  
  public LookupElement getElement(String value) {
	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      
	      if (thisElement.getDescription().startsWith(value)) {
	        return thisElement;
	      }
	    }
	    return null;
	  }


  /**
   *  Description of the Method
   */
  public void printVals() {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
     
    }
  }


  /**
   *  Adds a feature to the Item attribute of the LookupList object
   *
   * @param  tmp1  The feature to be added to the Item attribute
   * @param  tmp2  The feature to be added to the Item attribute
   */
  public void addItem(int tmp1, String tmp2) {
    if (!exists(tmp1)) {
      LookupElement thisElement = new LookupElement();
      thisElement.setCode(tmp1);
     
      thisElement.setDescription(tmp2);
      if (this.size() > 0) {
        this.add(0, thisElement);
      } else {
        this.add(thisElement);
      }
    }
  }


  /**
   *  Checks to see if the entry is already in the list
   *
   * @param  tmp1  Description of the Parameter
   * @return       Description of the Return Value
   */
  public boolean exists(int tmp1) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.getCode() == tmp1) {
        return true;
      }
    }
    return false;
  }


  /**
   *  Description of the Method
   *
   * @param  tmp1  Description of Parameter
   * @param  tmp2  Description of Parameter
   */
  public void appendItem(int tmp1, String tmp2) {
    LookupElement thisElement = new LookupElement();
    thisElement.setCode(tmp1);
    thisElement.setDescription(tmp2);
    if (this.size() <= 0) {
      this.add(0, thisElement);
    } else {
      this.add(this.size(), thisElement);
    }
  }


  /**
   *  A group is for visual presentation only, the following items will be in
   *  this group.
   *
   * @param  category  The feature to be added to the Group attribute
   */
  public void addGroup(String category) {
    LookupElement thisElement = new LookupElement();
    thisElement.setDescription(category);
    thisElement.setGroup(true);
    this.add(thisElement);
  }


  /**
   *  Description of the Method
   *
   * @param  sqlFilter  Description of Parameter
   */
  private void createFilter(StringBuffer sqlFilter, ConfigTipo configTipo) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        sqlFilter.append("AND entered > ? ");
      }
      sqlFilter.append("AND entered < ? ");
    }
    if (syncType == Constants.SYNC_UPDATES) {
      sqlFilter.append("AND modified > ? ");
      sqlFilter.append("AND entered < ? ");
      sqlFilter.append("AND modified < ? ");
    }
    if (selectedItems != null) {
      if (selectedItems.size() > 0) {
        sqlFilter.append(
            "AND (enabled = ? OR code IN (" + getItemsAsList() + ")) ");
      } else {
        sqlFilter.append("AND enabled = ? ");
      }
    }
    //Gestione tipi di macellazione
    if(configTipo!=null)
    	sqlFilter.append("AND " + configTipo.getFiltroLookup());
    else
    	sqlFilter.append("AND bovini " );
  }
  
  private void createFilter(StringBuffer sqlFilter) {
	    if (sqlFilter == null) {
	      sqlFilter = new StringBuffer();
	    }
	    if (syncType == Constants.SYNC_INSERTS) {
	      if (lastAnchor != null) {
	        sqlFilter.append("AND entered > ? ");
	      }
	      sqlFilter.append("AND entered < ? ");
	    }
	    if (syncType == Constants.SYNC_UPDATES) {
	      sqlFilter.append("AND modified > ? ");
	      sqlFilter.append("AND entered < ? ");
	      sqlFilter.append("AND modified < ? ");
	    }
	    if (selectedItems != null) {
	      if (selectedItems.size() > 0) {
	        sqlFilter.append(
	            "AND (enabled = ? OR code IN (" + getItemsAsList() + ")) ");
	      } else {
	        sqlFilter.append("AND enabled = ? ");
	      }
	    }
	  }


  /**
   *  Description of the Method
   *
   * @param  pst            Description of Parameter
   * @return                Description of the Returned Value
   * @throws  SQLException  Description of Exception
   */
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    if (syncType == Constants.SYNC_INSERTS) {
      if (lastAnchor != null) {
        pst.setTimestamp(++i, lastAnchor);
      }
      pst.setTimestamp(++i, nextAnchor);
    }
    if (syncType == Constants.SYNC_UPDATES) {
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, lastAnchor);
      pst.setTimestamp(++i, nextAnchor);
    }
    if (selectedItems != null) {
      pst.setBoolean(++i, true);
    }
    return i;
  }


  /**
   *  If a list of codes is provided, then hasItem will return whether the list
   *  contains the specified code
   *
   * @param  code  Description of the Parameter
   * @return       Description of the Return Value
   */
  public boolean hasItem(int code) {
    if (selectedItems != null) {
      if (!selectedItems.containsKey(new Integer(code))) {
        return false;
      }
    }
    return true;
  }


  /**
   *  Gets the itemsAsList attribute of the LookupList object
   *
   * @return    The itemsAsList value
   */
  private String getItemsAsList() {
    StringBuffer sb = new StringBuffer();
    if (selectedItems != null) {
      Iterator i = selectedItems.keySet().iterator();
      while (i.hasNext()) {
        sb.append(String.valueOf((Integer) i.next()));
        if (i.hasNext()) {
          sb.append(",");
        }
      }
    }
    return sb.toString();
  }


  /**
   *  Gets the defaultElementCode attribute of the LookupList object
   *
   * @return    The defaultElementCode value
   */
  public int getDefaultElementCode() {
    int result = -1;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.getDefaultItem()) {
        return thisElement.getCode();
      } else if (result == -1) {
        result = thisElement.getCode();
      }
    }
    return result;
  }


  /**
   *  Description of the Method
   *
   * @param  level  Description of the Parameter
   * @return        Description of the Return Value
   */
  public int removeElementByLevel(int level) {
    int result = 0;
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.getLevel() == level) {
        i.remove();
        result++;
      }
    }
    return result;
  }
  
  
  
  public int filtraPerCodiceCategoria(String codiceCategoria) 
  {
	    int result = 0;
	    Iterator i = this.iterator();
	    while (i.hasNext()) 
	    {
	      LookupElement thisElement = (LookupElement) i.next();
	      if (!thisElement.getCodice_Categoria().equalsIgnoreCase(codiceCategoria)) 
	      {
	        i.remove();
	        result++;
	      }
	    }
	    return result;
	  }
  
  /**
   *    Checks if the code exists
   *    
   * @param  code  Description of the Parameter
   * @return       Description of the Return Value
   * @throws SQLException 
   */

  public boolean hasCode(int code) {
    Iterator i = this.iterator();
    while (i.hasNext()) {
      LookupElement thisElement = (LookupElement) i.next();
      if (thisElement.getCode() == code) {
        return true;
      }
    }
    return false;
  }

   /**
   *  Description of the Method
   *
   *@param  db                Description of the Parameter
   *@param  tmpCategoryId         Description of the Parameter
   *@return                   Description of the Return Value
   *@exception  SQLException  Description of the Exception
   */
  public static int retrieveMaxLevel(Connection db, int tmpCategoryId) throws SQLException {
    int maxLevel = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT MAX(" + DatabaseUtils.addQuotes(db, "level") + ") AS max_level " +
        "FROM lookup_lists_lookup " +
				"WHERE module_id = ? ");
    pst.setInt(1, tmpCategoryId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      maxLevel = rs.getInt("max_level");
    }
    rs.close();
    pst.close();
    return maxLevel;
  }
  
  public static String stampaCombo(ArrayList<String> listaDate , String name)
  {
	  if (listaDate.isEmpty())
	  {
		  return "<font color = 'red'>Non Presenti Animali</font>";
	  }
	  String combo = "<select name = '"+name+"' id = '"+name+"'>";
	  
	  for (String d: listaDate)
	  {
		  combo += "<option value = '"+d+"'>"+d+"</option>";
	  }
	  
	  combo += "</select>";
	  
	  return combo ;
  }
  
  public int removeElementByValue(String value) {
	    int result = 0;
	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      if (thisElement.getDescription().equalsIgnoreCase(value)) {
	        i.remove();
	        result++;
	      }
	    }
	    return result;
	  }
  
  public int removeElementByCode(int code) {
	    int result = 0;
	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      if (thisElement.getCode() == code) {
	        i.remove();
	        result++;
	      }
	    }
	    return result;
	  }
  
  
  public int removeAllElementsButLevel(int level) {
	    int result = 0;
	    Iterator i = this.iterator();
	    while (i.hasNext()) {
	      LookupElement thisElement = (LookupElement) i.next();
	      if (thisElement.getLevel() != level) {
	        i.remove();
	        result++;
	      }
	    }
	    return result;
	  }
}

