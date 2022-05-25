<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<?php
    $row = $_GET['row'];
    $id = $_GET['nominativo'];
?>
<style>
table {
  border-collapse: collapse;
  margin: 10px;
}
table, td, th {
  border: 1px solid black;
}
th {
    background-color: rgb(204, 192, 218);
    text-align: center;
}

</style>

<script src="https://d3js.org/d3.v4.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<table id="table">
<tr>
    <th></th>
    <th style="max-width: 300px; text-align: center">Causale</th>
    <th style="text-align: center; width:80px">Percentuale</th>
    <th style="text-align: center; width:120px">*</th>
</tr>
</table>
<div style="margin-left: 10px">Note:</div>
<textarea style="margin: 10px" rows="5" cols="50" id="note" enabled></textarea>
<br>
<button onclick="save()">Salva</button>
<br>
<script>
    var id_nominativo = <?php echo $id ?>;
    var row = <?php echo $row ?>;
    var fattori = [];

    var httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', 'fattori_json.php?id=' + id_nominativo);
    httpRequest.send();
    httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === 4 && httpRequest.status === 200) {
        var httpResult = JSON.parse(httpRequest.responseText);
        createTable(httpResult.data);
      }
    }

    function createTable(data){
        console.log(data)
        d3.select("#note")._groups[0][0].value = data[0].fatt_text;

        data.forEach(function(d,i){
            fattori.push(d.id_fattore);
            var checked = "";
            var disabled = "disabled";
            if(d.valore != ""){
                checked = "checked";
                disabled = "";
            }
            d3.select("#table").append("tr").html(" \
                <td><input type=\"checkbox\" "+checked+" id=\"check"+d.id_fattore+"\" onchange='change("+d.id_fattore+")'></td> \
                <td id=\"descr"+d.id_fattore+"\">"+d.descr+" </td> \
                <td> <input type=\"text\" style=\"width: 30px;\" max="+d.max_perc+" id=\"perc"+d.id_fattore+"\" "+disabled+" value="+d.valore+"> <div style=\"font-size: 10px\">(max "+d.max_perc+"%)<div></td> \
                <td style=\"font-size: 10px\">"+d.note+"</td> \
                ")
        })
        console.log(fattori);
    }

    function change(id){
        if(d3.select("#check"+id)._groups[0][0].checked)
            d3.select("#perc"+id).property("disabled", "")
        else
            d3.select("#perc"+id).property("disabled", "disabled")
    }

    function save(){
        var note = d3.select("#note")._groups[0][0].value;
        console.log(note);

        fattori.forEach(function(id){ //primo ciclo per controllare validità
            if(d3.select("#check"+id)._groups[0][0].checked){
                console.log( d3.select("#descr"+id)._groups[0][0]);
                var val = d3.select("#perc"+id)._groups[0][0].value.trim();
                if(!/^\d+$/.test(val)){
                    alert("Puoi inserire solo numeri nel campo percentuale")
                    throw "errore inserimento";
                }
                var max = d3.select("#perc"+id)._groups[0][0].attributes.max.value;
                if(parseFloat(val) > parseFloat(max)){
                    var descr = d3.select("#descr"+id)._groups[0][0].innerText;
                    alert("Il valore massimo per '"+descr+"' è "+max+ ". Inserito "+ val);
                    throw "errore inserimento";
                }
            }
        })

        var precSottr = 100;
        var valuesFattori = [[null, id_nominativo, null, note]]; //entro almeno una volta per eliminare i valori precedenti
        fattori.forEach(function(id){ //secondo ciclo per calcoli
            if(d3.select("#check"+id)._groups[0][0].checked){
                var val = parseFloat(d3.select("#perc"+id)._groups[0][0].value.trim());
                console.log("sottraggo "+ val);
                precSottr = precSottr - (precSottr*val/100);
                console.log("nuovo " + precSottr);
                valuesFattori.push([id, id_nominativo, val, note]);
            }
        })
        console.log(valuesFattori);
        $.ajax({
            type: "POST",
            data: {values: valuesFattori},
            url: "update_fattori.php",
            dataType: "text",
            async: false,
            success: function(risposta) {
            console.log(risposta);

            if(risposta.startsWith("KO"))
                alert(risposta);
            },
            error: function(risposta){
            console.log("Errore ajax");
            }
        })
        var sottr = 100 - precSottr;
        console.log("da sottrarre " + sottr);
        window.opener.$("[id=sottr1][row="+row+"]").val(sottr);
        window.opener.$("[id=sottr1uba][row="+row+"]").val(sottr);
        window.opener.CHANGED_FATTORI = true;
        window.opener.calculate(row);
        self.close();
    }

</script>