<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script>
function performAction(risposta)
{
	var cueseguiti = document.getElementById('cueseguiti').value ;
	var idAllerta =	document.getElementById("idAllerta").value ;
	var chiusuraUfficio	= document.getElementById("chiusuraUfficio").value ;
	var idAslUtente	= document.getElementById("idAslUtente").value ;
	var numero_cu_seguiti =	document.getElementById("numero_cu_seguiti").value ;
	var cu_pianificati = document.getElementById("cu_pianificati").value ;
	var tipo_alimenti = document.getElementById("tipo_alimenti").value ;
	var specie_alimenti	= document.getElementById("specie_alimenti").value ;

	
	if (idAslUtente!=-1)
	{
	if (cueseguiti>=cu_pianificati)
	{
	if(risposta=='si')
	{
		
		if (chiusuraUfficio == '1')
		{
			window.opener.document.details.action='TroubleTicketsDocumentsAllerte.do?command=Add&tipoAlimenti='+tipo_alimenti+'&specie_alimenti='+specie_alimenti+'&id='+idAllerta+'&parentId=-1&folderId=-1&documentiAllerta=1&chiusuraUfficio=1';
		}
		else
		{
			window.opener.document.details.action='TroubleTicketsDocumentsAllerte.do?command=Add&tipoAlimenti='+tipo_alimenti+'&specie_alimenti='+specie_alimenti+'&id='+idAllerta+'&parentId=-1&folderId=-1&documentiAllerta=1';
			
		}
		window.opener.document.details.submit();
	}
	else
	{
		if (chiusuraUfficio == '1')
		{
			window.opener.document.details.action='TroubleTicketsAllerte.do?command=ChiudiAllerta&tipoAlimenti='+tipo_alimenti+'&specie_alimenti='+specie_alimenti+'&chiusuraUfficio=1&id='+idAllerta;
		}
		else
		{
			window.opener.document.details.action='TroubleTicketsAllerte.do?command=ChiudiAllerta&tipoAlimenti='+tipo_alimenti+'&specie_alimenti='+specie_alimenti+'&id='+idAllerta;
		}
		window.opener.document.details.submit();
		
	}
	}
	else
	{

		if (numero_cu_seguiti>=0 )
		{
			if(risposta=='si' )
			{
				
				if (chiusuraUfficio == 1)
				{
					window.opener.document.details.action='TroubleTicketsDocumentsAllerte.do?command=Add&motivazioni=true&tipoAlimenti='+tipo_alimenti+'&specie_alimenti='+specie_alimenti+'&tId='+idAllerta+'&parentId=-1&folderId=-1&documentiAllerta=1&chiusuraUfficio=1';
				}
				else
				{
					window.opener.document.details.action='TroubleTicketsDocumentsAllerte.do?command=Add&motivazioni=true&tipoAlimenti='+tipo_alimenti+'&specie_alimenti='+specie_alimenti+'&tId='+idAllerta+'&parentId=-1&folderId=-1&documentiAllerta=1';
					
				}
				window.opener.document.details.submit();
			}	
			else
				{
				if (chiusuraUfficio == '1')
				{
					window.opener.document.details.action='TroubleTicketsAllerte.do?command=ChiudiAllerta&tipoAlimenti='+tipo_alimenti+'&specie_alimenti='+specie_alimenti+'&chiusuraUfficio=1&id='+idAllerta;
				}
				else
				{
					window.opener.document.details.action='TroubleTicketsAllerte.do?command=ChiudiAllerta&tipoAlimenti='+tipo_alimenti+'&specie_alimenti='+specie_alimenti+'&id='+idAllerta;
				}
				window.opener.document.details.submit();
				}
		}


	}
	}
	
	window.opener.document.getElementById('risposta').value=risposta;
	onUnloadAction() ;
	window.close();
}


function onUnloadAction()
{

	submit = false ;
	var cueseguiti			=	document.getElementById("cueseguiti").value ;
	var idAllerta			= 	document.getElementById("idAllerta").value ;
	var chiusuraUfficio		=	document.getElementById("chiusuraUfficio").value ;
	var idAslUtente			=	document.getElementById("idAslUtente").value ;
	var numero_cu_seguiti	=	document.getElementById("numero_cu_seguiti").value ;
	var cu_pianificati		=	document.getElementById("cu_pianificati").value ;
	var tipo_alimenti		=	document.getElementById("tipo_alimenti").value ;
	var specie_alimenti		=	document.getElementById("specie_alimenti").value ;
	
	var risposta = window.opener.document.getElementById('risposta').value ;
	
	if (idAslUtente!=-1)
	{
		
	if (cueseguiti>=cu_pianificati)
	{
		
	if(risposta=='si')
	{
		
		if (chiusuraUfficio == 1)
		{
			window.opener.document.details.submit() ;
			window.close();
			
					}
		else
		{
			window.opener.document.details.submit() ;	
			window.close();		
			return ;
		}
		//window.opener.document.details.submit();
	}
	else
	{
		
		if (chiusuraUfficio == 1)
		{
			window.opener.document.details.submit() ;		
			window.close();
			return ;
			}
		else
		{
			
			window.opener.document.details.submit() ;
			window.close();
			return ;
					}
		//window.opener.document.details.submit();
		
	}
	}
	else
	{

		if (numero_cu_seguiti>=0 )
		{

			if(risposta=='si' )
			{
				
				if (chiusuraUfficio == 1)
				{
					window.opener.document.details.submit() ;
					window.close();	
					return ;
								}
				else
				{
					window.opener.document.details.submit() ;	
					window.close();		
					return ;		
				}
				//window.opener.document.details.submit();
			}
			else
			{
				
				if (chiusuraUfficio == 1)
				{
					
					 window.opener.open('TroubleTicketsAllerte.do?command=GoChiudiAllerta&tipoAlimenti='+tipo_alimenti+'&specie_alimenti='+specie_alimenti+'&id='+idAllerta+'&parentId=-1&folderId=-1&documentiAllerta=1&chiusuraUfficio=1',null,
					'height=350px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
					 
				}
				else
				{
					
					window.opener.open('TroubleTicketsAllerte.do?command=GoChiudiAllerta&tipoAlimenti='+tipo_alimenti+'&specie_alimenti='+specie_alimenti+'&id='+idAllerta+'&parentId=-1&folderId=-1&documentiAllerta=1&chiusuraUfficio=1',null,
					'height=350px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
					
						
				}
				
			}
			
		}
		
	}
	}

}

</script>

<body bgcolor="">
<table class="trails" cellspacing="0">
<tr>
<td>
Chiusura Allerta
</td></tr>
</table>
<input type = "hidden" id = "idAllerta" value = "<%=request.getAttribute("idAllerta") %>" >
<input type = "hidden" id = "chiusuraUfficio" value = "<%=request.getAttribute("chiusuraUfficio") %>" >
<input type = "hidden" id = "idAslUtente" value = "<%=request.getAttribute("idAslUtente") %>" >
<input type = "hidden" id = "numero_cu_seguiti" value = "<%=request.getAttribute("numero_cu_seguiti") %>" >
<input type = "hidden" id = "cu_pianificati" value = "<%=request.getAttribute("cu_pianificati") %>" >
<input type = "hidden" id = "tipo_alimenti" value = "<%=request.getAttribute("tipo_alimenti") %>" >
<input type = "hidden" id = "specie_alimenti" value = "<%=request.getAttribute("specie_alimenti") %>" >
<input type = "hidden" id = "cueseguiti" value = "<%=request.getAttribute("cueseguiti") %>" >

<table align="center">
<tr>
<td colspan="2"><%=request.getAttribute("msg")%></td>
</tr>
<tr>
<td><input type ="button" size="15" width="15" name = "  si  " value = "si" onclick="performAction('si')"></td>
<td><input type ="button" size="15" width="15"  name = "  no  " value = "no" onclick="performAction('no')"></td>
</tr>

</table>

</body>
