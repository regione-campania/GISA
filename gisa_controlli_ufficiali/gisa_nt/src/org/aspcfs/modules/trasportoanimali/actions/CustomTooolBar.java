/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.trasportoanimali.actions;

import java.util.List;

import org.jmesa.core.CoreContext;
import org.jmesa.view.ViewUtils;
import org.jmesa.view.component.Row;
import org.jmesa.view.html.HtmlUtils;
import org.jmesa.view.html.toolbar.AbstractItemRenderer;
import org.jmesa.view.html.toolbar.AbstractToolbar;
import org.jmesa.view.html.toolbar.ImageItem;
import org.jmesa.view.html.toolbar.ImageItemImpl;
import org.jmesa.view.html.toolbar.MaxRowsItem;
import org.jmesa.view.html.toolbar.ToolbarItem;
import org.jmesa.view.html.toolbar.ToolbarItemRenderer;
import org.jmesa.view.html.toolbar.ToolbarItemType;
import org.jmesa.web.WebContext;

class CustomToolbar extends AbstractToolbar { 
	
	@Override     public String render() {  
		
		addToolbarItem(ToolbarItemType.FIRST_PAGE_ITEM);    
		addToolbarItem(ToolbarItemType.PREV_PAGE_ITEM);        
		addToolbarItem(ToolbarItemType.NEXT_PAGE_ITEM);        
		addToolbarItem(ToolbarItemType.LAST_PAGE_ITEM);            
		addToolbarItem(ToolbarItemType.SEPARATOR);   
		MaxRowsItem maxRowsItem = (MaxRowsItem) addToolbarItem(ToolbarItemType.MAX_ROWS_ITEM);        
		if (getMaxRowsIncrements() != null) {          
			maxRowsItem.setIncrements(getMaxRowsIncrements());    
			}                 
		boolean exportable = ViewUtils.isExportable(getExportTypes());        
		if (exportable) {           
			addToolbarItem(ToolbarItemType.SEPARATOR);             
			addExportToolbarItems(getExportTypes());       
			}              
		Row row = getTable().getRow();     
		List columns = row.getColumns();                
		boolean filterable = ViewUtils.isFilterable(columns);     
		if (filterable) {         
			addToolbarItem(ToolbarItemType.SEPARATOR);          
  addToolbarItem(ToolbarItemType.FILTER_ITEM);       
  addToolbarItem(ToolbarItemType.CLEAR_ITEM);      
  }              
		boolean editable = ViewUtils.isEditable(getCoreContext().getWorksheet());    
		if (editable) {            
			
			addToolbarItem(ToolbarItemType.SEPARATOR);       
			addToolbarItem(ToolbarItemType.SAVE_WORKSHEET_ITEM);         
			addToolbarItem(ToolbarItemType.FILTER_WORKSHEET_ITEM);         
			}                  addToolbarItem(ToolbarItemType.SEPARATOR);          
			addToolbarItem(createCustomItem());     
			return super.render();   
			}        
	
	private ImageItem createCustomItem() {  
		
		ImageItemImpl item = new ImageItemImpl();    
		item.setCode("custom-item");       
		item.setTooltip("Hello World");    
    item.setImage(getImage("custom.png", getWebContext(), getCoreContext()));   
    item.setAlt("custom");       
    ToolbarItemRenderer renderer = new CustomItemRenderer(item, getCoreContext());      
    renderer.setOnInvokeAction("onInvokeAction");      
    item.setToolbarItemRenderer(renderer);         
    return item;   
    
	}    
	private static String getImage(String image, WebContext webContext, CoreContext coreContext) { 
		String imagesPath = HtmlUtils.imagesPath(webContext, coreContext);     
		return imagesPath + image; 
		}    
	private static class CustomItemRenderer extends AbstractItemRenderer {    
		public CustomItemRenderer(ToolbarItem item, CoreContext coreContext) {       
			setToolbarItem(item);        
			setCoreContext(coreContext);      
			}          public String render() {      
				ToolbarItem item = getToolbarItem();      
      StringBuilder action = new StringBuilder("javascript:");      
      action.append("alert('Hello World')");        
      item.setAction(action.toString());      
      return item.enabled();       
      }   
			}
	} 








