<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="AllevamentoBDN" class="org.aspcfs.modules.allevamenti.base.AllevamentoAjax" scope="request"/>
<jsp:useBean id="DiffenzeBDN" class="java.util.ArrayList" scope="request"/>

<SCRIPT src="javascript/masterList.js"></SCRIPT>

  <script src="javascript/jquery.js"></script>
	<script src="javascript/jquery-ui-1.10.4.min.js"></script>
    <script src="javascript/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="v/jquery-ui-1.9.2.custom.min.js"></script>
    <!-- bootstrap -->
<script src="javascript/bootstrap.min.js"></script>
<script src="javascript/angular/angular.js"></script>


<%@ include file="../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" type="text/javascript" src="javascript/popLookupSelect.js"></script>
		
<html>
<head>
<title>INTERROGAZIONE BDN</title>

</head>

<body>
		
		
		
		<div ng-controller="AttCtrl"  >
  
  <select ng-model="selectedMacroarea"  style="width: 30%"
  		
  		ng-change="changedValueAggregazione(selectedMacroarea)" 
  		ng-options="item.macroarea for item in itemsMacroarea track by item.id">
  <option value="-1">Seleziona Macroarea</option>
</select>  

<br>
<br>
  
  <select ng-model="selectedAggregazione"  style="width: 30%"
    		ng-change="changedValueAttivita(selectedAggregazione)" 
  			ng-options="itemAgg.aggregazione for itemAgg in itemsAggregazione track by itemAgg.id">
  
  <option value="-1">Seleziona Aggregazione</option>
</select>  
<br>
<br>

<select ng-model="selectedAttivita" style="width: 30%" name = "idLineaProduttiva" required="required"
  
  ng-options="itemAtt.lineaAttivita for itemAtt in itemsAttivita track by itemAtt.id">
  
  <option value="">Seleziona Linea Attivita</option>
</select>  
<br>
<br>
<b>{{selectedMacroarea.macroarea}}  - {{selectedAggregazione.aggregazione}} - {{selectedAttivita.lineaAttivita}}</b>

</div>
		
		
	</body>
	
	<script>



angular.module('attivita', []).controller('AggCtrl', function ($scope, $http) {
    $scope.selectedTestAccount = null;
    $scope.testAccounts = [];
    var data = {};
    data.tipologia="macroarea";
    data.flussoOrig="3";
   
    $http({
        method: 'POST',
        datatype:"json",
        header: {
            "contentType": 'application/json'
        },
        data: JSON.stringify(data),
        url: 'GestioneMasterList.do?command=CostruisciMasterList&flussoOrig=3&tipologia=aggregazione&idMacroarea='+item.id,
    }).success(function(data, status, headers, config) {
        $scope.itemsAggregazione= data;
    }).error(function(data, status, headers, config) {
        alert( "failure");
    });
    
     
});


angular.module('attivita', []).controller('AttCtrl', function ($scope, $http) {
    $scope.selectedTestAccount = null;
    $scope.testAccounts = [];
    var data = {};
    data.tipologia="macroarea";
    data.flussoOrig="3";
   
    $http({
        method: 'POST',
        datatype:"json",
        header: {
            "contentType": 'application/json'
        },
        data: JSON.stringify(data),
        url: 'GestioneMasterList.do?command=CostruisciMasterList&flussoOrig=3&tipologia=macroarea',
    }).success(function(data, status, headers, config) {
        $scope.itemsMacroarea = data;
    }).error(function(data, status, headers, config) {
        alert( "failure");
    });
    
    
    
    $scope.changedValueAggregazione = function(item) {
       
    	 $http({
    	        method: 'POST',
    	        datatype:"json",
    	        header: {
    	            "contentType": 'application/json'
    	        },
    	        data: JSON.stringify(data),
    	        url: 'GestioneMasterList.do?command=CostruisciMasterList&flussoOrig=3&tipologia=aggregazione&idMacroarea='+item.id,
    	    }).success(function(data, status, headers, config) {
    	        $scope.itemsAggregazione= data;
    	    }).error(function(data, status, headers, config) {
    	        alert( "failure");
    	    });
    	
      }   
    
    $scope.changedValueAttivita = function(item) {
        
     	 $http({
     	        method: 'POST',
     	        datatype:"json",
     	        header: {
     	            "contentType": 'application/json'
     	        },
     	        data: JSON.stringify(data),
     	        url: 'GestioneMasterList.do?command=CostruisciMasterList&flussoOrig=3&tipologia=attivita&idAggregazione='+item.id,
     	    }).success(function(data, status, headers, config) {
     	        $scope.itemsAttivita= data;
     	    }).error(function(data, status, headers, config) {
     	        alert( "failure");
     	    });
     	
       }   
});
</script>
	
</html>
		