/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.gestioneDocumenti.base;

import java.sql.Timestamp;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class DocumentaleDocumento  extends GenericBean {
	
	private static final long serialVersionUID = 4320567602597719160L;
	
	private String tipo = null; //tipo certificato
	private int orgId = -1; 
	private int idCU = -1; 
	private int ticketId = -1; 
	private String url = null; 
	private boolean glifo =  false;
	private String actionName = "PrintModulesHTML.do"; //nome action da chiamare
	private String file = null;
	private String urlOriginale = null;
	private int userId = -1;
	private String userIp = null;
	private String idHeader = null;
	private int idDocumento = -1;
	private String dataCreazione = null;
	private String nomeDocumento = null;
	private String md5 = null;
	private String extra = null;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	public void setOrgId(String orgId) {
		if (orgId!=null && !orgId.equals("null") && !orgId.equals(""))
			this.orgId = Integer.parseInt(orgId);
	}
	public int getIdCU() {
		return idCU;
	}
	public void setIdCU(int idCU) {
		this.idCU = idCU;
	}
	public void setIdCU(String idCU) {
		if (idCU!=null && !idCU.equals("null") && !idCU.equals(""))
			this.idCU = Integer.parseInt(idCU);
	}
	public int getTicketId() {
		return ticketId;
	}
	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}
	public void setTicketId(String ticketId) {
		if (ticketId!=null && !ticketId.equals("null") && !ticketId.equals(""))
			this.ticketId = Integer.parseInt(ticketId);
	}

	public boolean isGlifo() {
		return glifo;
	}
	public void setGlifo(boolean glifo) {
		this.glifo = glifo;
	}
	public void setGlifo(String glifo) {
		if (glifo!=null && !glifo.equals(""))
		this.glifo = true;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getUrlOriginale() {
		return urlOriginale;
	}
	public void setUrlOriginale(String urlOriginale) {
		this.urlOriginale = urlOriginale;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUserId(String userId) {
		if (userId!=null && !userId.equals("null") && !userId.equals(""))
			this.userId = Integer.parseInt(userId);
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getIdHeader() {
		return idHeader;
	}
	public void setIdHeader(String idHeader) {
		this.idHeader = idHeader;
	}
	public int getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(int idDocumento) {
		this.idDocumento = idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		if (idDocumento!=null && !idDocumento.equals("null") && !idDocumento.equals(""))
			this.idDocumento = Integer.parseInt(idDocumento);
	}
	public String getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public String getNomeDocumento() {
		return nomeDocumento;
	}
	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		
	public DocumentaleDocumento(ActionContext context) {
	this.setTipo(context.getRequest().getParameter("tipo")); 
	this.setOrgId(context.getRequest().getParameter("orgId")); 
	this.setIdCU(context.getRequest().getParameter("idCU")); 
	this.setTicketId(context.getRequest().getParameter("ticketId")); 
	this.setGlifo(context.getRequest().getParameter("glifo"));
	this.setFile(context.getRequest().getParameter("file"));
	this.setUrl(context.getRequest().getParameter("url"));
	
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public DocumentaleDocumento(String riga) {
		
		String[] split;
		split = riga.split(";;");
				
		this.setTipo(split[7]); 
		this.setDataCreazione(split[0]); 
		this.setUserId(split[2]);
		this.setIdHeader(split[5]);
		this.setIdDocumento(split[6]); 
		this.setExtra(split[9]); 
		
		}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}

}
