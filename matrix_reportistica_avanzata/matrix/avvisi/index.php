<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->

<h3 style="text-align: center" >Avvisi sistema '<?php echo $_REQUEST["sistema"]?>'</h3>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<script src="https://d3js.org/d3.v4.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>

<body style="margin: 5px">
<table id="table" style="border-collapse: collapse; width: 100%;">

</table>
<button onclick="newAvviso()">+</button>
<button onclick="saveAvvisi()">Salva</button>
</body>

<script>

    var sistema = '<?php echo $_REQUEST["sistema"]?>';


    d3.json("/avvisi/db.php?sistema=" + sistema, function(d){
        d.forEach(function(av){
            var tr = d3.select("#table").append("tr");
            tr.append("input").attr("type", "text").style("width", "90%").attr("id", "avviso").attr("value", av);
            tr.append("button").html("-").attr("onclick", "removeAvviso(this)");
        })
    })


    function newAvviso(){
        var tr = d3.select("#table").append("tr");
        tr.append("input").attr("type", "text").style("width", "90%").attr("id", "avviso");
        tr.append("button").html("-").attr("onclick", "removeAvviso(this)");
    }

    function removeAvviso(el){
        el.parentElement.remove()
    }

    function saveAvvisi(){
        var avvisi = [];
        d3.selectAll("#avviso").each(function(){
            avvisi.push(d3.select(this)._groups[0][0].value);
        })

        $.post("db.php?operation=save&sistema="+sistema, {"avvisi": avvisi}, function( data ) {
            alert(data);
        });

    }


</script>