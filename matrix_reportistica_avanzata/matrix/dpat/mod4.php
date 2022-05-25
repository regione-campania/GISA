<!--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.-->
<!DOCTYPE html>
<meta charset="utf-8">
<style>
  table {
    margin: 10px
  }

  th {
    border: 2px solid black;
    background-color: rgb(204, 192, 218);
    font-size: 12px;
    text-align: center;
  }

  .thuos {
    border: 2px solid black;
    background-color: rgb(149, 179, 215);
    font-size: 12px;
    text-align: center;
  }

  .thStep1 {
    border: 2px solid black;
    background-color: rgb(253, 233, 217);
    font-size: 12px;
    text-align: center;
  }

  .thStep2 {
    border: 2px solid black;
    background-color: rgb(252, 213, 180);
    font-size: 12px;
    text-align: center;
  }

  .thStep3 {
    border: 2px solid black;
    background-color: rgb(250, 191, 143);
    font-size: 12px;
    text-align: center;
  }


  td {
    border: 1px solid black;
    background-color: rgb(242, 242, 242);
    font-size: 12px;
    text-align: center;
  }

  .bold {
    font-weight: bold;
  }


  input[type=text] {
    width: 40px;
    text-align: center;
  }


  input[type=checkbox] {
    width: 10px;
    text-align: right;
  }

  select {
    width: 70px;
    text-align: right;
  }

  p {
    display: inline
  }
</style>

<head>
  <title>Calcolo UPS-UBA</title>
</head>

<body>
  <script src="https://d3js.org/d3.v4.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>

  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


  <text>UPS: </text>
  <table style="width: 500px;" id="table">


    <tr>
      <th colspan="6" class="thStep1">Step 1</th>
      <th colspan="2" class="thStep2">Step 2</th>
      <th colspan="3" class="thStep3">Step 3</th>
    </tr>

    <tr>
      <!-- <th>UOS</th> -->
      <th>Nominativi</th>
      <th>Qualifica</th>
      <th>CF</th>
      <th style="width: 100px;">Livello formativo</th>
      <th>Carico annuale</th>
      <th>Fattori personali</th>
      <th style="display:none">% da sottrarre</th>
      <th>Carico annuale teorico (step 2)</th>
      <th style="width: 90px;">Fattori che incidono negativamente</th>
      <th>% da sottrarre (max 5%)</th>
      <th id="perc_dest_uba">% di UIP da destinare alle UBA</th>
      <th>Carico annuale minimo (step 3)</th>
      <th class="thuos">Carico annuale teorico UOS</th>
      <th class="thuos">Fattori che incidono negativamente UOS</th>
      <th class="thuos">% da sottrarre (max 4%)</th>
      <th class="thuos">Carico annuale effettivo UOS</th>

    </tr>

  </table>

  <script>
    function getUrlVars() {
      var vars = {};
      var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m, key, value) {
        vars[key] = value;
      });
      return vars;
    }

    function getUrlParam(parameter, defaultvalue) {
      var urlparameter = defaultvalue;
      if (window.location.href.indexOf(parameter) > -1) {
        urlparameter = getUrlVars()[parameter];
      }
      return urlparameter;
    }

    id_s = getUrlParam("id");

    id_s = <?php echo $_REQUEST["id"] ?>;

    console.log("ID_STRUTTURA: " + id_s);


    dataGlob = null;

    var httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', 'uos_json.php?id=' + id_s);
    httpRequest.send();
    httpRequest.onreadystatechange = function() {
      if (httpRequest.readyState === 4 && httpRequest.status === 200) {
        var httpResult = JSON.parse(httpRequest.responseText);
        data = httpResult.data;
        dataGlob = data;
        createTable(data);
      }
    }

    j = 0;

    function createTable(data) {
      data.forEach(function(d, i) {
        // console.log(d);
        j++;
        if (i == 0) {
          var check1 = "";
          check2 = "";
          check3 = "";
          if (d.fattore1 == 't')
            check1 = "checked";
          if (d.fattore2 == 't')
            check2 = "checked";
          if (d.fattore3 == 't')
            check3 = "checked";

          var selected = ["", "", "", ""];
          selected[d.livello] = "selected";

          d3.select("#table").append("tr")
            .html( //'<td rowspan="'+data.length+'">' + d.uos + '</td> \
              '<td id="nominativo" row="' + j + '" idN="' + d.id_nominativo + '">' + d.nominativo + '</td>  \
            <td>' + d.qualifica + '</td> \
            <td id="cf" row="' + j + '">' + d.cf + '</td> \
            <td><select id="livello" row="' + j + '">  \
                  <option value="0" ' + selected[0] + '>0-Nessuno</option>  \
                  <option value="1" ' + selected[1] + '>1-Ispezioni negli stabilimenti</option>  \
                  <option value="2" ' + selected[2] + '>2-Audit sugli stabilimenti</option>  \
                  <option value="3" ' + selected[3] + '>3-Audit interni</option>  \
                </select></td>  \
            <td><input value="' + d.carico_annuale + '" type="text" id="hour1" row="' + j + '"></td> \
            <td><button onclick="window.open(\'fattori_table.php?nominativo=' + d.id_nominativo + '&row=' + j + '\' , \'_blank\', \'toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400\')">Modifica fattori personali</button></td> \
            <td style="display:none"><input value="' + d.sottr + '" type="text" id="sottr1" row="' + j + '" disabled></td> \
            <td id="step1" row="' + j + '" class="bold">4</td>  \
            <td>  <p>\
              1<input type="checkbox" id="fatt1" title="1. Eventuale insufficienza del numero di amministrativi afferenti alla struttura"  row="' + j + '" ' + check1 + '>  \
              2<input type="checkbox" id="fatt2" title="2. Eventuale insufficienza delle risorse strumentali in rapporto alle risorse umane presenti "  row="' + j + '" ' + check2 + '>  \
              3<input type="checkbox" id="fatt3" title="3. Caratteristiche geo-morfologiche del territorio"  row="' + j + '" ' + check3 + '>  \
            </p></td> \
            <td><input value="' + d.sottr2 + '" type="text" id="sottr2" row="' + j + '"></td>  \
            <td  id="perc_dest_uba"><input value="' + d.perc_dest_uba + '" type="text" row="' + j + '" id="perc_dest_uba_input"></td>  \
            <td id="step2" row="' + j + '" class="bold">3</td> \
            \
            <td rowspan="' + data.length + '" id="hour3" row="' + j + '" class="bold">6</td>  \
            <td rowspan="' + data.length + '">  \
              1<input id="fatt4" type="checkbox" title="1. Condizioni socio-economiche del territorio">  \
              2<input id="fatt5" type="checkbox" title="2. Presenza di particolari problematiche  di natura sanitaria e/o ambientali ">  \
            </td> \
            <td rowspan="' + data.length + '"><input value="0" type="text" id="sottr3" row="' + j + '"></td>  \
            <td rowspan="' + data.length + '" id="step3" row="' + j + '" class="bold">6</td>  ');
        } else {
          var check1 = "";
          check2 = "";
          check3 = "";
          console.log(d.fattore1)
          if (d.fattore1 == 't')
            check1 = "checked";
          if (d.fattore2 == 't')
            check2 = "checked";
          if (d.fattore3 == 't')
            check3 = "checked";

          var selected = ["", "", "", ""];
          selected[d.livello] = "selected";

          d3.select("#table")
            .style("width", "calc(100% - 30px)")
            .append("tr")
            .html('<td id="nominativo" row="' + j + '" idN="' + d.id_nominativo + '">' + d.nominativo + '</td>  \
            <td>' + d.qualifica + '</td> \
            <td id="cf" row="' + j + '">' + d.cf + '</td> \
            <td><select id="livello" row="' + j + '">  \
                  <option value="0" ' + selected[0] + '>0-Nessuno</option>  \
                  <option value="1" ' + selected[1] + '>1-Ispezioni negli stabilimenti</option>  \
                  <option value="2" ' + selected[2] + '>2-Audit sugli stabilimenti</option>  \
                  <option value="3" ' + selected[3] + '>3-Audit interni</option>  \
                </select></td>  \
            <td><input value="' + d.carico_annuale + '" type="text" id="hour1" row="' + j + '"></td> \
            <td><button onclick="window.open(\'fattori_table.php?nominativo=' + d.id_nominativo + '&row=' + j + '\' , \'_blank\', \'toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400\')">Modifica fattori personali</button></td> \
            <td style="display:none"><input value="' + d.sottr + '" type="text" id="sottr1" row="' + j + '" disabled></td> \
            <td id="step1" row="' + j + '" class="bold">4</td>  \
            <td>  <p>\
              1<input type="checkbox" id="fatt1" title="1. Eventuale insufficienza del numero di amministrativi afferenti alla struttura" row="' + j + '" ' + check1 + '>  \
              2<input type="checkbox" id="fatt2" title="2. Eventuale insufficienza delle risorse strumentali in rapporto alle risorse umane presenti " row="' + j + '" ' + check2 + '>  \
              3<input type="checkbox" id="fatt3" title="3. Caratteristiche geo-morfologiche del territorio" row="' + j + '" ' + check3 + '>  \
            </p></td> \
            <td><input value="' + d.sottr2 + '" type="text" id="sottr2" row="' + j + '"></td>  \
            <td id="perc_dest_uba"><input value="' + d.perc_dest_uba + '" type="text" row="' + j + '"  id="perc_dest_uba_input"></td>  \
            <td id="step2" row="' + j + '" class="bold">3</td> \
            </td>');
        }
      })
      // if(SHOW_UBA){
      //  if(true){

      d3.select("#mod4").append("hr")
      d3.select("#mod4").append("text").attr("id", "ubatext").text("UBA: ");
      d3.select("#mod4").append("table").attr("id", "table2").html(' <tr> \
      <th colspan="5" class="thStep1">Step 1</th> \
      <th colspan="1" class="thStep2">Step 2</th> \
      <th colspan="1" class="thStep3">Step 3</th> \
    </tr> \
      <tr><th>Nominativi</th> \
      <th>Qualifica</th>  \
      <th>CF</th> \
      <th>Carico annuale</th> \
      <th>Uba/Ora (da 2,5 a 8,5)</th> \
      <th style="display:none">Carico annuale teorico UBA</th> \
      <th style="display:none">% da sottrarre</th> \
      <th style="display:none">Carico annuale toerico UBA (step 2)</th>  \
      <th>% da sottrarre (max 5%)</th> \
      <th class="thuos">Carico annuale minimo UBA (step 3)</th> \
      <th class="thuos">Carico annuale teorico UOS</th>\
      <th class="thuos">% da sottrarre (max 4%)</th> \
      <th class="thuos">Carico annuale effettivo UOS UBA</th></tr>');

      j = 0;

      if (!SHOW_UBA) {
        d3.select("#table2").style("display", "none");
        d3.select("#ubatext").style("display", "none");
        d3.selectAll("#perc_dest_uba").style("display", "none");
      }

      data.forEach(function(d, i) {
        // console.log(d);
        j++;
        if (i == 0) {

          d3.select("#table2").style("width", "calc(100% - 30px)").append("tr")
            .html( //'<td rowspan="'+data.length+'">' + d.uos + '</td> \
              '<td id="nominativo" row="' + j + '" idN="' + d.id_nominativo + '">' + d.nominativo + '</td>  \
            <td>' + d.qualifica + '</td> \
            <td id="cf" row="' + j + '">' + d.cf + '</td> \
            <td><input value="' + d.carico_annuale + '" type="text" id="hourUba" row="' + j + '" disabled></td> \
            <td><input id="ubaora" value="' + d.ubaora + '" type="text" row="' + j + '"></td> \
            <td id="step1uba" row="' + j + '" class="bold" style="display:none">3</td> \
            <td style="display:none"><input value="' + d.sottr + '" type="text" id="sottr1uba" row="' + j + '" disabled></td> \
            <td id="step2uba" row="' + j + '" class="bold" style="display:none">3</td> \
            <td><input value="' + d.sottr2 + '" type="text" id="sottr2uba" row="' + j + '" disabled></td> \
            <td id="step3uba" row="' + j + '" class="bold">3</td> \
            <td rowspan="' + data.length + '" id="step4uba" row="' + j + '" class="bold">3</td> \
            <td rowspan="' + data.length + '"><input value="0" type="text" id="sottr3uba" row="' + j + '" disabled></td>  \
            <td rowspan="' + data.length + '" id="step5uba" row="' + j + '" class="bold">3</td> ')
        } else {

          d3.select("#table2").append("tr")
            .html('<td id="nominativo" row="' + j + '" idN="' + d.id_nominativo + '">' + d.nominativo + '</td>  \
            <td>' + d.qualifica + '</td> \
            <td id="cf" row="' + j + '">' + d.cf + '</td> \
            <td><input value="' + d.carico_annuale + '" type="text" id="hourUba" row="' + j + '" disabled></td> \
            <td><input id="ubaora" value="' + d.ubaora + '" type="text" row="' + j + '"></td> \
            <td id="step1uba" row="' + j + '" class="bold" style="display:none">3</td> \
            <td style="display:none"><input value="' + d.sottr + '" type="text" id="sottr1uba" row="' + j + '" disabled></td> \
            <td id="step2uba" row="' + j + '" class="bold" style="display:none">3</td> \
            <td><input value="' + d.sottr2 + '" type="text" id="sottr2uba" row="' + j + '" disabled></td> \
            <td id="step3uba" row="' + j + '" class="bold">3</td> ')
        }
      })
      // }

      d3.select("#mod4").append("button").attr("id", "save").text("Salva").on("click", save);

      windowHeight = parseInt($(window).height()) - 50;
      windowWidth = parseInt($(window).width()) - 20 - 540;

      console.log("altezza div:" + windowHeight);
      d3.select("#mod4").style("height", windowHeight + "px");
      d3.select("#mod4").style("width", windowWidth + "px");

      setUosValues();

      addListener();
      j = 0;
      data.forEach(function(d, i) {
        j++;
        calculate(j);
      })


      d3.json("lock.php", function(error, data_l) {
        if (error)
          throw error;
        console.log(data_l)
        for (i = 0; i < data_l.data.length; i++) {
          if (data_l.data[i].asl == ID_ASL && data_l.data[i].is_locked == 't') {
            d3.selectAll('input').property('disabled', true);
            d3.selectAll('select').property('disabled', true);
            d3.selectAll('textarea').property('disabled', true);
          }
        }

      })
    }

    window.onresize = function(event) {
      windowHeight = parseInt($(window).height()) - 50;
      windowWidth = parseInt($(window).width()) - 20 - 540;

      d3.select("#mod4").style("height", windowHeight + "px");
      d3.select("#mod4").style("width", windowWidth + "px");

    };


    function setUosValues() {
      var httpRequest = new XMLHttpRequest();
      httpRequest.open('GET', 'uos_values_json.php?id=' + id_s);
      httpRequest.send();
      httpRequest.onreadystatechange = function() {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
          var httpResult = JSON.parse(httpRequest.responseText);
          data = httpResult.data;
          console.log(data);
          //  d3.select("#fatt1").value(data['fattore1']);
          //  d3.select("#fatt2").value(data['fattore2']);
          d3.select("#sottr3")._groups[0][0].value = data[0]['sottr'];
          d3.select("#sottr3uba")._groups[0][0].value = data[0]['sottr'];

          d3.select("#step3")._groups[0][0].textContent = data[0]['ups'];

          if (data[0]['fattore1'] == 't')
            d3.select("#fatt4")._groups[0][0].checked = true;
          if (data[0]['fattore2'] == 't')
            d3.select("#fatt5")._groups[0][0].checked = true;
        }
      }
    }

    row = null;
    CHANGED = false;
    CHANGED_FATTORI = false;

    function addListener() {
      d3.selectAll("input").on("change", function(e) {
        console.log(d3.select(this));
        CHANGED = true;
        var value = d3.select(this)._groups[0][0].value;
        value = value.replace(",", ".");
        d3.select(this)._groups[0][0].value = value;
        console.log(d3.select(this).attr("row"));
        row = d3.select(this).attr("row");
        setTimeout(function(d) {
          calculate(row)
        }, 10)
      })
    }


    function calculate(row) {

      _h1 = d3.selectAll("#hour1").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });
      h1 = _h1._groups[0][0].value;

      _huba = d3.selectAll("#hourUba").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });
      _huba._groups[0][0].value = h1;

      _h3 = d3.selectAll("#hour3")
      /*.filter(function (d) {
              if (d3.select(this)._groups[0][0].attributes['row'].value == row)
                return true;
                return false;
            });*/
      h3 = _h3._groups[0][0].value;

      _sottr1 = d3.selectAll("#sottr1").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });
      sottr1 = _sottr1._groups[0][0].value;


      _sottr1uba = d3.selectAll("#sottr1uba").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });
      _sottr1uba._groups[0][0].value = sottr1;

      if (sottr1 > 100) {
        alert("Valore max consentito 100");
        _sottr1._groups[0][0].value = 100;
        calculate(row);
        return;
      }

      _sottr2 = d3.selectAll("#sottr2").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });
      sottr2 = _sottr2._groups[0][0].value;


      _sottr2uba = d3.selectAll("#sottr2uba").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });
      _sottr2uba._groups[0][0].value = sottr2;

      if (sottr2 > 5) {
        alert("Valore max consentito 5");
        _sottr2._groups[0][0].value = 5;
        calculate(row);
        return;
      }

      _sottr3 = d3.selectAll("#sottr3")
      /*.filter(function (d) {
              if (d3.select(this)._groups[0][0].attributes['row'].value == row)
                return true;
                return false;
            });*/
      sottr3 = _sottr3._groups[0][0].value;


      if (sottr3 > 4) {
        alert("Valore max consentito 4");
        _sottr3._groups[0][0].value = 4;
        calculate(row);
        return;
      }

      _sottr3uba = d3.selectAll("#sottr3uba") //.filter(function(d) {
      /* if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });*/
      _sottr3uba._groups[0][0].value = sottr3;



      if (sottr2 > 5) {
        alert("Valore max consentito 5");
        _sottr2._groups[0][0].value = 5
      }

      _step1 = d3.selectAll("#step1").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });

      _step2 = d3.selectAll("#step2").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
      });

      _perc_dest_uba = d3.selectAll("#perc_dest_uba_input").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row){
          return true;
        }
      });
      perc_dest_uba = _perc_dest_uba._groups[0][0].value;

      if(perc_dest_uba < 0 || perc_dest_uba > 100){
        alert("Valore permesso tra 0 e 100");
          _perc_dest_uba._groups[0][0].value  = 0;
          calculate(row);
          return;
      }

      _step3 = d3.selectAll("#step3")
      /*.filter(function (d) {
              if (d3.select(this)._groups[0][0].attributes['row'].value == row)
                return true;
                return false;
            });*/

      step1 = h1 - (h1 / 100 * sottr1);
      _step1._groups[0][0].textContent = Math.round(step1);

      step2Intero = step1 - (step1 / 100 * sottr2); //a questa devo togliere la % dest uba
      step2 = step2Intero -(step2Intero / 100 * perc_dest_uba)

      _step2._groups[0][0].textContent = Math.round(step2);

      step2Sum = 0;

      steps2 = d3.selectAll("#step2");
      steps2._groups[0].forEach(function(el) {
        step2Sum = step2Sum + parseInt(el.textContent);
      })
      h3 = Math.round(step2Sum / 2);
      _h3._groups[0][0].textContent = h3;

      step3 = h3 - (h3 / 100 * sottr3);
      _step3._groups[0][0].textContent = Math.round(step3);


      _ubaora = d3.selectAll("#ubaora").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });
      ubaora = _ubaora._groups[0][0].value;


      if (ubaora > 8.5) {
        alert("Valori consentiti: 0 o tra 2.5 e 8.5!");
        _ubaora._groups[0][0].value = 8.5;
        calculate(row);
        return;
      } else if (ubaora < 2.5 && ubaora != 0) {
        alert("Valori consentiti: 0 o tra 2.5 e 8.5!");
        _ubaora._groups[0][0].value = 2.5;
        calculate(row);
        return;
      }


      step1uba = d3.selectAll("#step1uba").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });

      step2uba = d3.selectAll("#step2uba").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });


      step3uba = d3.selectAll("#step3uba").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row)
          return true;
        return false;
      });


      /*step1uba._groups[0][0].textContent = Math.round(h1 * 4 * ubaora);
      var step1uba = h1 * 4 * ubaora;
      step2uba._groups[0][0].textContent = Math.round(step1uba - (step1uba / 100 * sottr1))
      var step2uba = step1uba - (step1uba / 100 * sottr1);
      step3uba._groups[0][0].textContent = Math.round(step2uba - (step2uba / 100 * sottr2))

      NUOVO CALCOLO FLUSSO 268
      */
      val_dest_uba = (step2Intero / 100 * perc_dest_uba); //quantità di uba
      console.log(val_dest_uba);
      step3uba._groups[0][0].textContent = Math.round(val_dest_uba * 4 * ubaora);


      step4sum = 0
      steps3 = d3.selectAll("#step3uba");
      steps3._groups[0].forEach(function(el) {
        step4sum = step4sum + parseInt(el.textContent);
      })
      step4uba = Math.round(step4sum);

      _step4uba = d3.selectAll("#step4uba")
      /*.filter(function (d) {
              if (d3.select(this)._groups[0][0].attributes['row'].value == row)
                return true;
                return false;
            });*/
      _step4uba._groups[0][0].textContent = step4uba;

      step5uba = step4uba - (step4uba / 100 * sottr3);
      _step5uba = d3.select("#step5uba")

      _step5uba._groups[0][0].textContent = Math.round(step5uba);

    }

    function save() {
      console.log("save");
      var values = [];
      dataGlob.forEach(function(el, i) {
        var row = i + 1;
        console.log(row);

        _id = d3.selectAll("#nominativo").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        id = _id._groups[0][0].attributes['idN'].value;

        _h1 = d3.selectAll("#hour1").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        h1 = _h1._groups[0][0].value;


        _liv = d3.selectAll("#livello").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        liv = _liv._groups[0][0].value;

        /*_text = d3.selectAll("#text").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        text = _text._groups[0][0].value;*/

        _sottr1 = d3.selectAll("#sottr1").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        sottr1 = _sottr1._groups[0][0].value;

        _sottr2 = d3.selectAll("#sottr2").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        sottr2 = _sottr2._groups[0][0].value;

        _ups = d3.selectAll("#step2").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        ups = _ups._groups[0][0].textContent;

        _fatt1 = d3.selectAll("#fatt1").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        fatt1 = _fatt1._groups[0][0].checked;
        if (fatt1 == true)
          fatt1 = 'true';
        else
          fatt1 = 'false';

        _fatt2 = d3.selectAll("#fatt2").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        fatt2 = _fatt2._groups[0][0].checked
        if (fatt2 == true)
          fatt2 = 'true';
        else
          fatt2 = 'false';

        _fatt3 = d3.selectAll("#fatt3").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        fatt3 = _fatt3._groups[0][0].checked
        if (fatt3 == true)
          fatt3 = 'true';
        else
          fatt3 = 'false';


        _ubaora = d3.selectAll("#ubaora").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        ubaora = _ubaora._groups[0][0].value;

        _uba = d3.selectAll("#step3uba").filter(function(d) {
          if (d3.select(this)._groups[0][0].attributes['row'].value == row)
            return true;
          return false;
        });
        uba = _uba._groups[0][0].textContent;

        _perc_dest_uba = d3.selectAll("#perc_dest_uba_input").filter(function(d) {
        if (d3.select(this)._groups[0][0].attributes['row'].value == row){
          return true;
        }
        });
        perc_dest_uba = _perc_dest_uba._groups[0][0].value;

        if (!SHOW_UBA) {
          uba = 0;
          ubaora = 0;
          perc_dest_uba = 0;
        }

        //values.push([id, liv, h1, sottr1, sottr2, fatt1, fatt2, fatt3, ubaora, text, ups, uba]);
        values.push([id, liv, h1, sottr1, sottr2, fatt1, fatt2, fatt3, ubaora, ups, uba, perc_dest_uba]);

      })
      console.log(values);

      $.ajax({
        type: "POST",
        data: {
          values: values
        },
        url: "update_values.php",
        dataType: "text",
        async: false,
        success: function(risposta) {
          console.log(risposta);

          if (risposta.startsWith("KO"))
            alert("errore insetimento nel db (NOMINATIVI)");
        },
        error: function(risposta) {
          console.log("Errore ajax");
        }
      })

      fatt5 = d3.selectAll("#fatt5")._groups[0][0].checked;
      fatt4 = d3.selectAll("#fatt4")._groups[0][0].checked;
      sottr3 = d3.selectAll("#sottr3")._groups[0][0].value;
      ups = d3.selectAll("#step3")._groups[0][0].textContent;
      uba = d3.selectAll("#step5uba")._groups[0][0].textContent;


      if (!SHOW_UBA) {
        uba = 0;
      }

      console.log([id_s, fatt4, fatt5, sottr3, ups, uba]);
      var valuesUos = [id_s, fatt4, fatt5, sottr3, ups, uba];
      $.ajax({
        type: "POST",
        data: {
          values: valuesUos
        },
        url: "update_values_uos.php",
        dataType: "text",
        async: false,
        success: function(risposta) {
          console.log(risposta);

          if (risposta.startsWith("KO"))
            alert("errore insetimento nel db (UOS)");
        },
        error: function(risposta) {
          console.log("Errore ajax");
        }
      })
      alert("Salvataggio riuscito!");
      getNewValues();
      CHANGED = false;
      CHANGED_FATTORI = false;
    }
  </script>

</body>