/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/

package it.izs.bdn.anagrafica.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Apistopro", propOrder = {
    "propIdFiscale",
    "dtInizioAttivita",
    "apiattAziendaCodice"
})
public class Apistopro
    extends Entity
{

    protected String propIdFiscale;
    protected XMLGregorianCalendar dtInizioAttivita;
    protected String apiattAziendaCodice;

    public String getPropIdFiscale() {
        return propIdFiscale;
    }

    public void setPropIdFiscale(String value) {
        this.propIdFiscale = value;
    }

    public String getApiattAziendaCodice() {
        return apiattAziendaCodice;
    }

    public void setApiattAziendaCodice(String value) {
        this.apiattAziendaCodice = value;
    }
    
    public XMLGregorianCalendar getDtInizioAttivita() {
        return dtInizioAttivita;
    }

    public void setDtInizioAttivita(XMLGregorianCalendar value) {
        this.dtInizioAttivita = value;
    }

   

}
