<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<style>
	.round{border-radius:4px;border:1px solid #AAAAAA;}
    th, td {
        padding: 15px;
    }
</style>
 
 <div style="background:#dc002e; font-size:22px; text-align:left; color:#FFF; font-weight:bold;">
</div>
<header>	
 <title>REPORTISTICA AVANZATA - REPORT</title>
 
<!--<script>
   window.onbeforeunload = confirmExit;
    function confirmExit() {
        return "You have attempted to leave this page. Are you sure?";
    }
</script>-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://d3js.org/d3.v4.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>

inizioEnabled = false;
fineEnebled = false;
aslEnabled = false;


$.ajax({ 
    type: 'GET', 
    url: 'get_estrazioni.php', 
    dataType: 'json',
    success: function (data) { 
        console.log(data.json);
        for(let i=0; i<data.json.length; i++){
            d3.select("select#estrazioni").append('option')
                .text(data.json[i].descr)
                .attr("value", data.json[i].id)
                .attr("data_filter", data.json[i].data_filter)
                .attr("data_end_filter", data.json[i].data_end_filter)
                .attr("asl_filter", data.json[i].asl_filter)
                .attr("note", data.json[i].note);

        }
       // $("#asl").combobox("option", "disabled", true);
        $("#inizio").datepicker('disable');
        $("#fine").datepicker('disable');
    }
});

$( function() {
    $( "#inizio" ).datepicker({
      changeMonth: true,
      changeYear: true
    });
  } );


  $( function() {
    $( "#fine" ).datepicker({
      changeMonth: true,
      changeYear: true
    });
  } );


function estrai(){
    var id = document.getElementById("estrazioni").value; 
    console.log(id);
    var inizio = '';
    if( $('#inizio').datepicker('getDate') != null && inizioEnabled)
         inizio = $('#inizio').datepicker('getDate').toISOString().substring(0, 10);
    else if ($('#inizio').datepicker('getDate') != null)
        alert('Selezionare data inizio');
    var fine = '';
    if( $('#fine').datepicker('getDate') != null && fineEnebled)
         fine = $('#fine').datepicker('getDate').toISOString().substring(0, 10);
    else if ($('#fine').datepicker('getDate') != null)
        alert('Selezionare data fine');
    console.log(inizio);
    console.log(fine);
    if(aslEnabled){
        var arr = $('#asl').val();
        if(arr == null){
            alert('Selezionare almeno un asl');
            return;
        }
        var arr2 = '';
        for(var i=0; i<arr.length; i++){
            arr2 = arr2 + arr[i] + ',';
        }
        arr2 = arr2.slice(0, -1)
        console.log(arr2);
    }
    window.open('sheet.php?id='+id+'&inizio='+inizio+'&fine='+fine+'&asl='+arr2);

}

function change(selectObj) {
   var selectIndex=selectObj.selectedIndex;
   console.log(selectIndex);
   if(selectIndex != 0){
        var note=selectObj.options[selectIndex].attributes.note.value;
        var filter=selectObj.options[selectIndex].attributes.data_filter.value;
        var filterFine=selectObj.options[selectIndex].attributes.data_end_filter.value;
        var filterAsl=selectObj.options[selectIndex].attributes.asl_filter.value;

        $('#note').text(note);
        if(filter == 'f'){
            $("#inizio").datepicker('disable');
            inizioEnabled = false;
        }else{
            $("#inizio").datepicker('enable');
            inizioEnabled = true;
        }
        if(filterFine == 'f'){
            $("#fine").datepicker('disable');
            fineEnebled = false;
        }else{
            $("#fine").datepicker('enable');
            fineEnebled = true;
        }

      /*  if(filterAsl == 't'){
            $("#asl").combobox("option", "disabled", false);
            aslEnabled = true;
        }
        else{
            $("#asl").combobox("option", "disabled", true);
            aslEnabled = false;
        } */
    }
    else {
        $("#inizio").datepicker('disable');
        $("#fine").datepicker('disable');
      //  $("#asl").combobox("option", "disabled", true);
        $('#note').text("");
    }
 }

</script>

<table>
    <tr>
        <td><label>Estrazione: </label></td>
        <td><select name="estrazioni" id="estrazioni" onchange="change(this)">
            <option selected> </option>
        </select> </td>
        <br>
    </tr>
    <tr>
        <td>Data inizio: </td> 
        <td> <input type="text" id="inizio"> </td>
    </tr>
    <tr>
        <td> Data fine: </td>
        <td><input type="text" id="fine"> </td>
    </tr>
   <!-- <tr>
        <td>Asl: </td>
        <td> <select size="7" multiple="multiple" id="asl" disabled>
            <option value="201">Avellino</option>
            <option value="202">Benevento</option>
            <option value="203">Caserta</option>
            <option value="204">Napoli 1 Centro</option>
            <option value="205">Napoli 2 Nord </option>
            <option value="206">Napoli 3 Sud</option>
            <option value="207">Salerno</option>
        </select></td>
    </tr> -->
    <tr>
        <td></td>
        <td><button type="button" onclick="estrai()">Estrai</button></td>
    </tr>
</table>
<hr>
<div id="note" style='color:red; font-weight:bold;'></div>