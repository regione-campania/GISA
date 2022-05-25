/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package it.us.web.action.vam.test;

import it.us.web.action.GenericAction;
import it.us.web.bean.remoteBean.RegistrazioniCanina;
import it.us.web.bean.remoteBean.RegistrazioniFelina;
import it.us.web.exceptions.AuthorizationException;

public class TestRegistrazioni extends GenericAction
{
	
	private String system;
	
	public TestRegistrazioni() {		
	}
	
	public TestRegistrazioni (String system) {
		this.system = system;
	}
	@Override
	public void can() throws AuthorizationException
	{

	}

	
	@Override
	public void setSegnalibroDocumentazione()
	{
	}

	@Override
	public void execute() throws Exception
	{
		RegistrazioniFelina a = (RegistrazioniFelina) persistence.getNamedQuery("GetRegistrazioniFelina").setString("identificativo", "380098100734788").list().get(0);
		System.out.println("Adozione: " + a.getAdozione());
		System.out.println("Decesso: " + a.getDecesso());
		System.out.println("Furto: " + a.getFurto());
		System.out.println("Passaporto: " + a.getPassaporto());
		System.out.println("Ritrovamento: " + a.getRitrovamento());
		System.out.println("Smarrimento: " + a.getSmarrimento());
		System.out.println("Sterilizzazione: " + a.getSterilizzazione());
		System.out.println("Trasferimento: " + a.getTrasferimento());
		
		RegistrazioniCanina b = (RegistrazioniCanina) persistence.getNamedQuery("GetRegistrazioniCanina").setString("identificativo", "50SA1111").list().get(0);
		System.out.println("Adozione: " + b.getAdozione());
		System.out.println("Decesso: " + b.getDecesso());
		System.out.println("Furto: " + b.getFurto());
		System.out.println("Passaporto: " + b.getPassaporto());
		System.out.println("Ritrovamento: " + b.getRitrovamento());
		System.out.println("Smarrimento: " + b.getSmarrimento());
		System.out.println("Trasferimento: " + b.getTrasferimento());
		
	}

}
