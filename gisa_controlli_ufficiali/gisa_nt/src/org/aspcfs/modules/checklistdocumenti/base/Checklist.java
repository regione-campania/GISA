/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.checklistdocumenti.base;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.aspcfs.checklist.base.Audit;
import org.aspcfs.checklist.base.AuditChecklist;
import org.aspcfs.checklist.base.AuditChecklistType;
import org.aspcfs.checklist.base.Organization;
import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.web.CustomLookupElement;
import org.aspcfs.utils.web.CustomLookupList;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Checklist extends GenericBean {
	
	private int orgId = -1;
	private int idAsl = -1;
	private int tipoChecklist = -1;

	private HashMap <Integer, Integer[]> questionario = new HashMap<Integer, Integer[]>();
	
	

}
