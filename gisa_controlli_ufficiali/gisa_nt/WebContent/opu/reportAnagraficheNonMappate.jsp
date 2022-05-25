<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="//jqueryui.com/jquery-wp-content/themes/jquery/css/base.css?v=1">
<link rel="stylesheet" href="//jqueryui.com/jquery-wp-content/themes/jqueryui.com/style.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

<style>
	td
	{
		font-size : 10px;
		padding: 20px;
		
	}
	
	tr
	{
		border  : 1px solid rgba(0,0,0,0.1);
	}
	
	th
	{
		border  : 1px solid rgba(0,0,0,0.7);
	}
	
	.selected_row
	{
		background : rgba(20,255,20,.4);
		 
	}
	
	 

</style>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 
 <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" />

<title>Insert title here</title>
</head>
<body>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
	 <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	
	  
 
	
	<div ng-app="app1" ng-controller="ctr1" >
	<center>
	<br>
	<table   >
		 
		<tr>
			<td>
				<div class="dropdown">
				<select ng-model="tipologiaScelta" id="sel_tipologia" >
					<option ng-repeat="tipologia in tipologie" value="{{tipologia.tipo}}">{{tipologia.descrizione}}</option>					
				</select>
				</div>
			</td>
			<td>
				<select ng-model="statoScelto">
					<option value="mappate">mappate</option>
					<option value="non_mappate">non mappate</option>
				</select>
			</td>
			<td>
				<input type="button" value="Mostra linee" ng-click="mostraLinee()"/>
			</td>
		</tr>
	
	</table>
	<br>
	<span ng-if="linee.length > 0">Linee ritornate: <b ng-bind="linee.length"></b></span>
	<hr/>
	<table border="1" ng-if="linee.length > 0">
		<tr>
			<th>LINEA VECCHIA<br>(LISTA_LINEE_ATTIVITA_VECCHIA_ANAGRAFICA)</th>
			<th ng-if="linee[0].ranking > 0">LINEA ML MAPPATA</th>
			<th ng-if="linee[0].ranking > 0">RANKING</th>
		</tr>
		<tr ng-repeat="linea in linee | orderBy: '-ranking'" ng-mouseenter="entertr(linea.indice)" 
			ng-mouseleave="leavetr()"	ng-class="{selected_row : trSelId === linea.indice }"
			ng-click="linee[0].ranking > 0 && clickedtr(linea)"
			id="tr{{linea.indice}}"
			>
			<td title="{{linea.idLineaV}}">{{linea.macroV}} -> {{linea.aggrV}} -> {{linea.attV}}</td>
			<td ng-if="linee[0].ranking > 0" title="{{linea.idLineaN}}">{{linea.macroN}} -> {{linea.aggrN}} -> {{linea.attN}}</td>
			<!-- <td title="{{linea.idLineaN}}"></td>-->
			<td ng-if="linee[0].ranking > 0">{{linea.ranking}}</td>
		</tr>
	</table>
	
	</center>
	<div id="dialog1" title="STABILIMENTI IMPORTATI CON QUESTO MAPPING (primi 20...)">
		<center>
		<table width="100%" ng-if="stabsDetails.length > 0">
			<tr><th>ORG_ID</th><th>STAB_ID</th><th>PARTITA IVA</th><th>RAGIONE SOCIALE</th></tr>
			<tr ng-repeat="stab0 in stabsDetails">
				<td>{{stab0.campi.riferimento_org_id}}</td>
				<td>{{stab0.campi.id_stabilimento}}</td>
				<td>{{stab0.campi.partita_iva }}</td>
				<td>{{stab0.campi.ragione_sociale}}</td>
			</tr>
		</table>		
		
		<span ng-if="stabsDetails.length == 0"><b> NON CI SONO STABILIMENTI NEL DETTAGLIO </b></span>
		</center>
	</div>
	
	</div>
	
	
	
	<script>
		var app = angular.module("app1",[]);
		var ctr = app.controller("ctr1",function($scope,$http)
		{
			
			
			$http(
					{
						method : 'POST'
						,url : 'ReportAnagraficheNonMappate.do?command=MappabilitaLinee'
						,params : {op : 'get_tipologie'}
					}
					).success(function(data ){
						 console.log("ARRIVATO JSON "+data);
						 
						 $scope.tipologie = [];
						 for(var key in data)
						 {
							 if(data[key].trim().length == 0)
								 continue;
							 
							 
							 $scope.tipologie.push(
									 { tipo : key 
									  , descrizione : data[key] }
									 
							 )
						 }
						 
						 $scope.tipologie.push({tipo : -1, descrizione : 'TUTTE'});
						
						 $scope.tipologiaScelta = $scope.tipologie[0].tipo;
						 $("#sel_tipologia").prop('selectedIndex',0);
						 
						 $scope.statoScelto = 'mappate';
						 
						 
						 
			});
			
			
			
			
			
			$scope.trSelId = -1;
			$scope.entertr = function(indlineav)
			{

				$scope.trSelId = indlineav;
			}
			
			$scope.leavetr= function()
			{
				$scope.trSelId = -1;
			}
			
			
			$scope.clickedtr = function(linea)
			{
				
				$scope.stabsDetails = [];
				$http(
						{
							url : 'ReportAnagraficheNonMappate.do?command=MappabilitaLinee'
							, method : 'get'
							,params : {op : 'get_stabilimenti', idLineaV : linea.idLineaV, idLineaN : linea.idLineaN}
						}).
				success(function(risp){
						
					console.log("ARRIVATIIII");
					for(var k = 0;k<risp.length;k++)
					{
						$scope.stabsDetails.push(
							risp[k]	
						);
						console.log(risp[k]);
					}
					
					$("#dialog1").dialog();
					$("#dialog1").dialog("option",{minWidth : 700, minHeight : 400});
					
					
					
					
				});
			}
			
			
			
			
			
			
			$scope.mostraLinee = function()
			{
				$scope.linee = []; 
				$http(
					{
						url : 'ReportAnagraficheNonMappate.do?command=MappabilitaLinee'
						,params : {op : 'get_linee', tipologia : $scope.tipologiaScelta, stato : $scope.statoScelto}
						,method : 'post'
						
					})
					.success
					(
							function(data)
							{
								console.log(data);
								
								$scope.linee = [];
								
								for(var i = 0; i< data.length; i++)
								{
									var h = data[i];
									h.indice = i;
									$scope.linee.push(h);
								}
								
								 
							}
					);
				 
			}
			 
			 
		});
	</script>
	
</body>
</html>