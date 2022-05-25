<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="org.aspcfs.modules.opu.base.Stabilimento"%>
<%@page import="java.sql.*,java.util.HashMap,java.util.Map"%>
<%@page import="org.aspcfs.utils.Canile"%>
<jsp:useBean id="canile" class="org.aspcfs.utils.Canile" scope="request" />
<link rel="stylesheet" type="text/css" href="css/template1.css">
<link rel="stylesheet" type="text/css" href="css/template1-8pt.css">
<link rel="stylesheet" type="text/css" href="css/capitalize.css">

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>

<style>
/* The Modal (background) */
.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 20px;
    border: 1px solid #888;
    width: 20%;
}

/* The Close Button */
.close {
    color: #aaaaaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}
</style>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th colspan="2">
			Informazioni Canile
		</th>
	</tr>

<%
	if(canile.getDataFine()==null || canile.getDataFine().equals(""))
	{ 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String data_corrente_st = sdf.format(new Date());
		Date data_corrente = sdf.parse(data_corrente_st);

		if(canile.getDataOperazioneBlocco()==null)
		{ 
%>
			 <tr class="containerBody">
				 <td nowrap class="formLabel">
				 	Stato
				 </td>
				 <td>
				 	<font color="#008800"><b>ATTIVO</b></font>
				 </td>
			 </tr>
<% 
		}
		else
		{
		
			if(canile.isBloccato())
			{ 
				String data_operazione_st = sdf.format(new Date(canile.getDataSospensioneBlocco().getTime()));
				Date data_operazione = sdf.parse(data_operazione_st);
				if(data_operazione.compareTo(data_corrente)>0)
				{
%>
					<tr class="containerBody">
						<td nowrap class="formLabel">
							Stato
						</td>
						<td>
							<font color="#FFA500"><b>ATTIVO</b></font><br>
							<font color="#FFA500">SOSPENSIONE INGRESSI</font> A PARTIRE DAL <%=data_operazione_st%>
						</td>
					</tr>
<% 
				}
				else
				{ 
					data_operazione_st = sdf.format(new Date(canile.getDataRiattivazioneBlocco().getTime()));
					data_operazione = sdf.parse(data_operazione_st);
					if(data_operazione.compareTo(data_corrente)>0)
					{
%>
						<tr class="containerBody">
							<td nowrap class="formLabel">
								Stato
							</td>
							<td>
								<font color="#FF0000"><b>BLOCCATO</b></font><br>
								<font color="#FFA500">RIATTIVAZIONE INGRESSI</font> A PARTIRE DAL <%=data_operazione_st%>
							</td>
						</tr>
<% 
					}
					else
					{ 
%>
						<tr class="containerBody">
							<td nowrap class="formLabel">
								Stato
							</td>
							<td>
								<b><font color="#008800">RIATTIVAZIONE INGRESSI</font> A PARTIRE DAL <%=data_operazione_st%></b>
							</td>
						</tr>
<% 
					}
		 		} 
	 		}
		}
	}
	else
	{ 
%>
		<tr class="containerBody">
			<td nowrap class="formLabel">
				Stato
			</td>
			<td>
				CHIUSO/BLOCCATO
			</td>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel">
				Data chiusura
			</td>
			<td>
				<%=canile.getDataFine().toString().substring(0,10)%>
			</td>
		</tr>
<% 
	} 
%>


	<tr class="containerBody">
		<td nowrap class="formLabel" name="orgname1" id="orgname1">
			Superficie destinata al ricovero animali
		</td>
		<td>
<%
			String mq = "";
			if(canile.getMqDisponibili()>0)
				mq=String.valueOf((canile.getMqDisponibili()));
			else
				mq="N.D.";
%>
			
			<div id="divMqOld" style="display:block">
				<%=mq %>  mq.
			</div>
			
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel" name="orgname1" id="orgname1">
				Indice di capienza
		</td>
<%
		double occupazioneAttuale = canile.getOccupazioneAttuale();
		double occupazioneTotale  = canile.getMqDisponibili();
		double percentuale = 0;
		String colorFont = "green";
		
		if(occupazioneTotale>0)
			percentuale = (occupazioneAttuale/occupazioneTotale)*100;
		percentuale = new BigDecimal(percentuale).setScale(2 , BigDecimal .ROUND_UP).doubleValue();
		
		if(percentuale>=80 && percentuale<100)
			colorFont="orange";
		else if(percentuale>=100)
		{
			colorFont="red";
		}
%>
			<td>
				<b>
					<font color="<%=colorFont%>"><%=occupazioneAttuale%> mq 
<%
						if(occupazioneTotale>0)
							out.println(" (" + percentuale + " %)");
%>
					</font>
				</b>
			</td>
		</tr>
		
		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1">
				Numero di cani vivi
			</td>
<%
			int limiteNumeroCaniVivi = 350;
			int numeroCaniVivi = canile.getNumeroCaniVivi();
			percentuale = 0;
			colorFont = "green";
		
			if(numeroCaniVivi>0)
				percentuale = (Double.parseDouble(numeroCaniVivi+"")/limiteNumeroCaniVivi)*100;
			percentuale = new BigDecimal(percentuale).setScale(2 , BigDecimal .ROUND_UP).doubleValue();
		
			if(percentuale>=80 && percentuale<100)
				colorFont="orange";
			else if(percentuale>=100)
				colorFont="red";
%>
			<td>
				<b>
					<font color="<%=colorFont%>"><%=numeroCaniVivi%> 
<%
						if(limiteNumeroCaniVivi>0)
							out.println(" (" + percentuale + " %)");
%>
					</font>
				</b>
			</td>
		</tr>
</table>

