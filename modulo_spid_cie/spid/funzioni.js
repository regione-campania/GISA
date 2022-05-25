/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
window.history.replaceState({}, document.title, window.location.href.split('?')[0]);
RUOLI_GLOB = null;
SISTEMA_RUOLO_GLOB = null;

function getConfigs() {
    const cfInput = document.getElementById("cf").value;
    d3.json("db.php?operation=get_ruoli&cf=" + cfInput, function (error, data) {
        if (error)
            throw "Errore recupero ruoli";
        console.log(data);
        RUOLI_GLOB = data;
        SISTEMA_RUOLO_GLOB = data.registrazione

        data.tipologia_utente.forEach(function (tipologia, i) {
            d3.select("#tdUtenti")
                .append("input")
                .attr("type", "radio")
                .style("width", "50px")
                .attr("name", "enteRichiedente")
                .attr("value", tipologia.id)
                .attr("id", i == 0 ? "firstChecked" : null)
                .property("checked", i == 0 ? true : false)

            d3.select("#tdUtenti")
                .append("label")
                .text(tipologia.descr)

            d3.select("#tdUtenti")
                .append("br");
        })

        data.tipo_richiesta.forEach(function (tipo, i) {
            if (SISTEMA_RUOLO_GLOB != undefined || i == 0) {
                d3.select("#tdRichieste")
                    .append("input")
                    .attr("type", "radio")
                    .style("width", "50px")
                    .attr("name", "tipoRichiesta")
                    .attr("value", tipo.id)
                    .attr("id", i == 0 ? "firstChecked2" : null)
                    .attr("onchange", "showSistemaRuolo()")
                    // .attr("onchange", i == 1 ? "showSistemaRuolo()" : null)
                    // .attr("onchange", i == 2 ? "showSistemaRuolo()" : null)
                    .property("checked", i == 0 ? true : false)
                    .attr("title", tipo.title)

                d3.select("#tdRichieste")
                    .append("label")
                    .text(tipo.descr)
                    .attr("title", tipo.title)

                d3.select("#tdRichieste")
                    .append("br");
            }
        })

        data.asl.forEach(function (a) {
            d3.select("select#aslEnte").append("option").html(a.descr).attr("value", a.id).attr("id", "optAsl");
        })

        data.ruoli_gisa.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa");
        })

        data.ruoli_vam.forEach(function (ruolo, i) {
            d3.select("select#ruoloVam").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optVam");
        })

        data.ruoli_asl_bdu.forEach(function (ruolo) {
            d3.select("select#ruoloBdu").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optBdu");
        })

        data.ruoli_digemon.forEach(function (ruolo) {
            d3.select("select#digemon").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optDigemon");
        })

        data.ruoli_giava.forEach(function (ruolo) {
            d3.select("select#giava").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGiava");
        })

        data.cliniche_vam.forEach(function (clinica) {
            d3.select("select#clinicheVam").append("option").html(clinica.descr).attr("value", clinica.id).attr("id", "optCliniche");
        })

        data.gestori_acque.forEach(function (gestore) {
            d3.select("select#gestoreAcqua").append("option").html(gestore.nome).attr("value", gestore.id).attr("id", "optGestori");
        })

        if ($('[id="optGisa"]').length == 1) {
            d3.select("#optGisa").property("selected", true);
            d3.select("#optGisa").text("SI'");
        }
        if ($('[id="optVam"]').length == 1) {
            d3.select("#optVam").property("selected", true);
            d3.select("#optVam").text("SI'");
        }
        if ($('[id="optBdu"]').length == 1) {
            d3.select("#optBdu").property("selected", true);
            d3.select("#optBdu").text("SI'");
        }
        if ($('[id="optCliniche"]').length == 1) {
            d3.select("#optCliniche").property("selected", true);
            d3.select("#optCliniche").text("SI'");
        }
        if ($('[id="optGestori"]').length == 1) {
            d3.select("#optGestori").property("selected", true);
            d3.select("#optGestori").text("SI'");
        }
        if ($('[id="optDigemon"]').length == 1) {
            d3.select("#optDigemon").property("selected", false);
            d3.select("#optDigemon").text("SI'");
        }
        if ($('[id="optGiava"]').length == 1) {
            d3.select("#optGiava").property("selected", false);
            d3.select("#optGiava").text("SI'");
        }

        var comuniList = [];
        data.comuni.forEach(function (comune) {
            comuniList.push({
                istat: comune.istat,
                nome: comune.nome
            });
        })
        console.log(comuniList);
        $(function () {
            $("input#comune").autocomplete({
                source: comuniList.map(c => c.nome)
            });
            $("input#comuneSezFour").autocomplete({
                source: comuniList.map(c => c.nome)
            });
        });

        lastSelected2 = null;
        $('input:radio[name="tipoRichiesta"]').click(function () {
            var sel = parseInt($(this).val());
            console.log(sel);
            lastSelected2 = sel;
        });

        lastSelected = null;
        $('input:radio[name="enteRichiedente"]').click(function () {
            cambioTipologiaUtente(parseInt($(this).val()));
        })

        document.getElementById('firstChecked').click();
        document.getElementById('firstChecked2').click();

    })


    /*const cfInput = document.getElementById("cf").value;
    d3.json("db.php?operation=getRichiesteByCf&cf=" + cfInput, function (error, data) {
        console.log(data);
        if (error)
            throw "Errore recupero sistema";
        SISTEMA_RUOLO_GLOB = data;
    });*/
}


function changeRuoloVam() {
    var role = document.getElementById("ruoloVam").value;
    console.log(role);
    if (role == -1) {
        document.getElementById("clinicheVam").value = -1;
        d3.select("#clinicheVamRow").style("display", "none");
    } else {
        console.log("mostro cliniche vam");
        d3.select("#clinicheVamRow").style("display", "");
        // $("#clinicheVam").mousedown(function(e){
        //     e.preventDefault();
        //
        //     var select = this;
        //     var scroll = select.scrollTop;
        //
        //      e.target.selected = !e.target.selected;
        //
        //     setTimeout(function(){select.scrollTop = scroll;}, 0);
        //
        //     $(select).focus();
        //
        //     if(e.target.selected == true) {
        //       // $(e.target).prependTo("#clinicheVam");
        //       e.target.clinicaSelected = true;
        //     }
        //     if(e.target.selected == false) {
        //       // $(e.target).appendTo("#clinicheVam");
        //       e.target.clinicaSelected = false;
        //     }
        //   }) .mousemove(function(e){e.preventDefault()});
    }
}

function changeClinicheVam(sel) {
    const id_asl_selected = sel.value;
    document.getElementById("clinicheVam").value = -1;
    d3.selectAll("#optCliniche").remove();

    RUOLI_GLOB.cliniche_vam.forEach(function (clinica) {
        if (clinica.id_asl == id_asl_selected || id_asl_selected == -1)
            d3.select("select#clinicheVam").append("option").html(clinica.descr).attr("value", clinica.id).attr("id", "optCliniche");
    })
}

function hideSez5(sel) {
    console.log("hideSez5: " + sel);
    if (sel == 3) {
        d3.select("#sez5Row").style("display", "none");
        d3.select("#ruoloGisaRow").style("display", "none");
        d3.select("#ruoloVamRow").style("display", "none");
        d3.select("#clinicheVamRow").style("display", "none");
        d3.select("#ruoloBduRow").style("display", "none");
        d3.select("#gestoriAcqueRow").style("display", "none");
        d3.select("#digemonRow").style("display", "none");
        d3.select("#giavaRow").style("display", "none");
    } else {
        d3.select("#sez5Row").style("display", "");
    }
}

function setSistemaRuolo(sel) {
    console.log(sel);
    d3.selectAll("#optRegistrazioneSistema").remove();
    if (sel == 3) {
        //d3.selectAll("#sez1Row").style("display", "none");
        document.getElementById("sistemSelect").style.background = "#ff2626";
        SISTEMA_RUOLO_GLOB.forEach(function (registrazione, i) {
            if (registrazione.tipologia_utente != null) {
                var splitted = registrazione.tipologia_utente.split(",");
                if (splitted.indexOf(lastSelected.toString()) > -1)
                    d3.select("select#sistemSelect").append("option").html(registrazione.descrizione).attr("value", registrazione.id_guc_ruoli).attr("id", "optRegistrazioneSistema").attr("id_asl", registrazione.id_asl);
            }
        })
    } /*else {
        d3.selectAll("#sez1Row").style("display", "");
    }*/
    if (sel == 2) {
        document.getElementById("sistemSelect").style.background = "yellow";
        document.getElementById('firstChecked').click();
        $('input[name="enteRichiedente"]').prop("disabled", true);
        SISTEMA_RUOLO_GLOB.forEach(function (registrazione, i) {
            if (registrazione.tipologia_utente != null) {
                var splitted = registrazione.tipologia_utente.split(",");
                if (registrazione.is_edit && splitted.indexOf("1") > -1)
                    d3.select("select#sistemSelect").append("option").html(registrazione.descrizione).attr("value", registrazione.id_guc_ruoli).attr("id", "optRegistrazioneSistema").attr("id_asl", registrazione.id_asl);
            }
        })
    } else {
        $('input[name="enteRichiedente"]').prop("disabled", false);
    }
}

function showSistemaRuolo() {
    //d3.selectAll("#optRegistrazioneSistema").remove();
    const sel = $('input[name="tipoRichiesta"]:checked').val();
    setSistemaRuolo(sel);
    cambioTipologiaUtente(lastSelected);
    d3.select("#sistemaRow").style("display", "none");
    if (sel == 2 || sel == 3) {
        d3.select("#sistemaRow").style("display", "");

        /*d3.json("db.php?operation=getRichiesteByCf&cf=" + cfInput, function (error, data) {
          console.log(data);
          if (error)
              throw "Errore recupero sistema";
          if(data !== null) {
            d3.select("#sistemaRow").style("display", "");
            data.registrazione.forEach((registrazione, i) => {
              // console.log(registrazione);
              d3.select("select#sistemSelect").append("option").html(registrazione.descrizione).attr("value", registrazione.id_guc_ruoli).attr("id", "optRegistrazioneSistema");
              //}
              });
          }
      });*/
    }
}

function modificaEnteSP(ente) {
    const idEnteSp = ente.value;
    const sel = $('input[name="enteRichiedente"]:checked').val()
    if (sel == 2 || sel == 19) {
        document.getElementById("ruoloGisa").value = idEnteSp;
    }
    if (sel == 3) {
        document.getElementById("gestoreAcqua").value = idEnteSp;
    }
}


function cambioTipologiaUtente(sel) {
    console.log(sel);
    lastSelected = sel;

    setSistemaRuolo($('input[name="tipoRichiesta"]:checked').val());

    //gestione ruoli da visualizzare
    document.getElementById("ruoloGisa").value = -1;
    document.getElementById("ruoloVam").value = -1;
    document.getElementById("ruoloBdu").value = -1;
    document.getElementById("clinicheVam").value = -1;
    document.getElementById("gestoreAcqua").value = -1;
    document.getElementById("digemon").value = "null";
    document.getElementById("giava").value = "false";

    //document.getElementById("altro").value = "";
    d3.select("#ruoloGisa").property("disabled", true);
    d3.select("#ruoloVam").property("disabled", true)
    d3.select("#ruoloBdu").property("disabled", true);
    d3.select("#ruoloGisaRow").style("display", "");
    d3.select("#ruoloVamRow").style("display", "")
    d3.select("#ruoloBduRow").style("display", "");
    d3.select("#clinicheVamRow").style("display", "none");
    d3.select("#gestoriAcqueRow").style("display", "none");
    d3.select("#digemonRow").style("display", "none");
    d3.select("#giavaRow").style("display", "none");
    d3.select("#identificativoEnteRowSP").style("display", "none");
    document.getElementById('aslEnte').disabled = false;
    document.getElementById("digemon").disabled = false;

    //d3.select("#giava").property("disabled", false);
    //d3.select("input#altro").property("disabled", true);
    //d3.select("input#altro").attr("placeholder", "")

    document.getElementById("pecLabel").innerHTML = "PEC struttura di appartenenza";

    if (sel == 1) {
        d3.select("#ruoloGisa").property("disabled", false);
        d3.select("#ruoloVam").property("disabled", false)
        d3.select("#ruoloBdu").property("disabled", false);
        d3.select("#digemonRow").style("display", "");
        d3.selectAll("#optGisa").remove();

        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_asl.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })

        d3.selectAll("#optVam").remove();
        RUOLI_GLOB.ruoli_vam.forEach(function (ruolo, i) {
            d3.select("select#ruoloVam").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optVam");
        })

        d3.selectAll("#optBdu").remove();
        RUOLI_GLOB.ruoli_asl_bdu.forEach(function (ruolo) {
            d3.select("select#ruoloBdu").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optBdu").attr("is_ext", ruolo.ext);
        })
    } else if (sel == 10) {
        d3.select("#ruoloGisa").property("disabled", false);
        d3.select("#ruoloBdu").property("disabled", false);
        d3.select("#digemonRow").style("display", "");

        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_regione.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })

        d3.selectAll("#optBdu").remove();
        RUOLI_GLOB.ruoli_regione_bdu.forEach(function (ruolo) {
            d3.select("select#ruoloBdu").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optBdu").attr("is_ext", ruolo.ext);
        })

    } else if (sel == 2) {
        d3.select("#identificativoEnteRowSP").style("display", "");
        d3.selectAll("#optSP").remove();
        RUOLI_GLOB.ruoli_forze.forEach(function (ruolo) {
            d3.select("select#identificativoEnteSP").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optSP")
        })
        d3.select("#ruoloGisa").property("disabled", true);
        //d3.select("#ruoloBdu").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_forze.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
        /*d3.selectAll("#optBdu").remove();
        RUOLI_GLOB.ruoli_bdu_forze.forEach(function (ruolo) {
            d3.select("select#ruoloBdu").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optBdu").attr("is_ext", ruolo.ext);
        })*/
    } else if (sel == 3) {
        d3.select("#identificativoEnteRowSP").style("display", "");
        d3.selectAll("#optSP").remove();
        RUOLI_GLOB.gestori_acque.forEach(function (gestore) {
            d3.select("select#identificativoEnteSP").append("option").html(gestore.nome).attr("value", gestore.id).attr("id", "optSP")
        })
        d3.select("#ruoloGisa").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_gestori_acque.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
        d3.select("#gestoreAcqua").property("disabled", true);
        d3.select("#gestoriAcqueRow").style("display", "");
    } else if (sel == 4) {
        d3.select("#ruoloGisa").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_apicoltore.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
        // d3.select("#giavaRow").style("display", "");
        document.getElementById("pecLabel").innerHTML = "PEC";
    } else if (sel == 5) {
        d3.select("#ruoloGisa").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_apicoltore.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
        // d3.select("#giavaRow").style("display", "");
    } else if (sel == 6) {
        d3.select("#ruoloGisa").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_delegato_apicoltore.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
        //d3.select("#giavaRow").style("display", "");
    } else if (sel == 7 || sel == 20) {
        d3.select("#ruoloGisa").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_trasporti.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
        //d3.select("#giavaRow").style("display", "");
        /* }else if(sel == 8){
           d3.select("input#altro").property("disabled", false);
           d3.select("input#altro").attr("placeholder", "Specificare");*/
    } else if (sel == 9) {
        // d3.select("#numeroAutorizzazioneRegionaleVetRow").style("display", "");
        d3.select("#ruoloBdu").property("disabled", false);
        d3.selectAll("#optBdu").remove();
        RUOLI_GLOB.ruoli_bdu_liberi_prof.forEach(function (ruolo) {
            d3.select("select#ruoloBdu").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optBdu").attr("is_ext", ruolo.ext);
        })
        document.getElementById("pecLabel").innerHTML = "PEC personale dell'Ordine";
    } else if (sel == 11 || sel == 18) {
        d3.select("#ruoloBdu").property("disabled", false);
        d3.select("#ruoloGisa").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        d3.selectAll("#optBdu").remove();

        RUOLI_GLOB.ruoli_zoofile_gisa.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
        d3.selectAll("#optBdu").remove();
        RUOLI_GLOB.ruoli_zoofile_bdu.forEach(function (ruolo) {
            d3.select("select#ruoloBdu").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optBdu").attr("is_ext", ruolo.ext);
        })
    } else if (sel == 13) {
        d3.select("#giavaRow").style("display", "");
        document.getElementById("giava").value = RUOLI_GLOB.ruoli_giava[0].id;
        d3.select("#giava").property("disabled", true);
    } else if (sel == 14) {
        d3.select("#ruoloBdu").property("disabled", false);
        d3.selectAll("#optBdu").remove();
        RUOLI_GLOB.ruoli_bdu.forEach(function (ruolo) {
            d3.select("select#ruoloBdu").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optBdu").attr("is_ext", ruolo.ext);
        })
        document.getElementById("pecLabel").innerHTML = "PEC personale dell'Ordine/Collegio professionale";
    } else if (sel == 16) {
        d3.select("#ruoloGisa").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_arpac.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
    } else if (sel == 17) {
        d3.select("#digemonRow").style("display", "");
        d3.select("#ruoloGisa").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_osservatori.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
    } else if (sel == 15) {
        //d3.select("#digemonRow").style("display", "");
        d3.select("#ruoloGisa").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_crr.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
    } else if (sel == 12) {
        d3.select("#digemonRow").style("display", "");
        d3.select("#ruoloVam").property("disabled", false);
        d3.selectAll("#optVam").remove();
        RUOLI_GLOB.ruoli_izsm_vam.forEach(function (ruolo) {
            d3.select("select#ruoloVam").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optVam").attr("is_ext", ruolo.ext);
        })

        d3.select("#ruoloGisa").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_gisa_izsm.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
    }
    // Gestione Tipologia Utente -> Esercito
    else if (sel == 19) {
        d3.select("#identificativoEnteRowSP").style("display", "");
        d3.selectAll("#optSP").remove();
        RUOLI_GLOB.ruoli_esercito.forEach(function (ruolo) {
            d3.select("select#identificativoEnteSP").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optSP")
        })
        d3.select("#ruoloGisa").property("disabled", true);
        //d3.select("#ruoloBdu").property("disabled", false);
        d3.selectAll("#optGisa").remove();
        RUOLI_GLOB.ruoli_esercito.forEach(function (ruolo) {
            d3.select("select#ruoloGisa").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optGisa").attr("is_ext", ruolo.ext);
        })
        d3.selectAll("#optBdu").remove();
        /*RUOLI_GLOB.ruoli_bdu_forze.forEach(function (ruolo) {
            d3.select("select#ruoloBdu").append("option").html(ruolo.descr).attr("value", ruolo.id).attr("id", "optBdu").attr("is_ext", ruolo.ext);
        })*/
    }

    if ($('[id="optGisa"]').length == 1 && !d3.select("select#ruoloGisa").property("disabled")) {
        d3.select("#optGisa").property("selected", true);
        //d3.select("select#ruoloGisa").property("disabled", true)
        d3.select("#optGisa").text("SI'");
    }
    if ($('[id="optVam"]').length == 1 && !d3.select("select#ruoloVam").property("disabled")) {
        d3.select("#optVam").property("selected", true);
        //d3.select("select#ruoloVam").property("disabled", true)
        d3.select("#optVam").text("SI'");
        d3.select("#clinicheVamRow").style("display", "");
    }
    if ($('[id="optBdu"]').length == 1 && !d3.select("select#ruoloBdu").property("disabled")) {
        d3.select("#optBdu").property("selected", true);
        //d3.select("select#ruoloBdu").property("disabled", true)
        d3.select("#optBdu").text("SI'");
    }
    if ($('[id="optCliniche"]').length == 1 && !d3.select("select#clinicheVam").property("disabled")) {
        d3.select("#optCliniche").property("selected", true);
        //d3.select("select#clinicheVam").property("disabled", true)
        d3.select("#optCliniche").text("SI'");
    }
    if ($('[id="optGestori"]').length == 1 && !d3.select("select#gestoreAcqua").property("disabled")) {
        d3.select("#optGestori").property("selected", true);
        //d3.select("select#gestoreAcqua").property("disabled", true)
        d3.select("#optGestori").text("SI'");
    }

    //gestione campi da visualizzare
    d3.select("#identificativoEnteRow").style("display", "none");
    document.getElementById("identificativoEnte").value = "";
    d3.select("#identificativoEnte").property("required", false);
    d3.select("#pIvaRow").style("display", "none");
    document.getElementById("pIva").value = "";
    d3.select("#pIva").property("required", false);
    d3.select("#comuneRow").style("display", "none");
    document.getElementById("comune").value = "";
    d3.select("#comune").property("required", false);
    d3.select("#referenteRow").style("display", "none");
    document.getElementById("referente").value = "";
    d3.select("#referente").property("required", false);
    d3.select("#ruoloReferenteRow").style("display", "none");
    document.getElementById("ruolo").value = "";
    d3.select("#ruolo").property("required", false);
    d3.select("#indirizzoEnteRow").style("display", "none");
    document.getElementById("indirizzoEnte").value = "";
    d3.select("#indirizzoEnte").property("required", false);
    d3.select("#telefonoEnteRow").style("display", "none");
    document.getElementById("telefonoEnte").value = "";
    d3.select("#telefonoEnte").property("required", false);
    d3.select("#codiceGisaRow").style("display", "none");
    document.getElementById("codiceGisa").value = "";
    d3.select("#codiceGisa").property("required", false);
    d3.select("#addressRow").style("display", "none");
    document.getElementById("address").value = "";
    d3.select("#address").property("required", false);
    d3.select("#capRow").style("display", "none");
    document.getElementById("cap").value = "";
    d3.select("#cap").property("required", false);

    d3.select("#decretoPrefettizioRow").style("display", "none");
    document.getElementById("decretoPrefettizio").value = "";
    d3.select("#decretoPrefettizio").property("required", false);
    d3.select("#decretoPrefettizioScadRow").style("display", "none");
    document.getElementById("decretoPrefettizioScad").value = "";
    d3.select("#decretoPrefettizioScad").property("required", false);

    d3.select("#numeroOrdineVetRow").style("display", "none");
    document.getElementById("numeroOrdineVet").value = "";
    d3.select("#numeroOrdineVet").property("required", false);
    d3.select("#provinciaOrdineVetRow").style("display", "none");
    document.getElementById("provinciaOrdineVet").value = "";
    d3.select("#provinciaOrdineVet").property("required", false);

    d3.select("#numeroAutorizzazioneRegionaleVetRow").style("display", "none");
    document.getElementById("numeroAutorizzazioneRegionaleVet").value = "";
    d3.select("#numeroAutorizzazioneRegionaleVet").property("required", false);

    d3.select("#comuneRowSezFour").style("display", "none");
    document.getElementById("comuneSezFour").value = "";
    d3.select("#comuneSezFour").property("required", false);
    d3.select("#addressRowSezFour").style("display", "none");
    document.getElementById("addressSezFour").value = "";
    d3.select("#addressSezFour").property("required", false);
    d3.select("#capRowSezFour").style("display", "none");
    document.getElementById("capSezFour").value = "";
    d3.select("#capSezFour").property("required", false);


    if ([1, 2, 3, 6, 8, 10, 11, 12, 13, 15, 16, 17, 18, 19].indexOf(sel) > -1) {
        d3.select("#identificativoEnteRow").style("display", "");
        d3.select("#identificativoEnte").property("required", true);
        d3.select("#referenteRow").style("display", "");
        d3.select("#referente").property("required", true);
        d3.select("#ruoloReferenteRow").style("display", "");
        d3.select("#ruolo").property("required", true);
        d3.select("#indirizzoEnteRow").style("display", "");
        d3.select("#indirizzoEnte").property("required", true);
        d3.select("#telefonoEnteRow").style("display", "");
        d3.select("#telefonoEnte").property("required", true);
    }
    if ([3, 5, 6, 7, 20, 8, 13].indexOf(sel) > -1) {
        d3.select("#pIvaRow").style("display", "");
        d3.select("#pIva").property("required", true);
    }
    if ([2, 3, 5, 7, 20, 8, 11, 13, 18, 19].indexOf(sel) > -1) {
        d3.select("#comuneRow").style("display", "");
        d3.select("#comune").property("required", true);
    }
    if ([3, 5, 7, 20, 13].indexOf(sel) > -1) {
        d3.select("#addressRow").style("display", "");
        d3.select("#address").property("required", true);
        d3.select("#capRow").style("display", "");
        d3.select("#cap").property("required", true);
    }
    if ([7, 20, 13, 14].indexOf(sel) > -1) {
        d3.select("#codiceGisaRow").style("display", "");
        d3.select("#codiceGisa").property("required", true);
    }
    if ([11].indexOf(sel) > -1) {
        d3.select("#decretoPrefettizioRow").style("display", "");
        d3.select("#decretoPrefettizio").property("required", true);
        d3.select("#decretoPrefettizioScadRow").style("display", "");
        d3.select("#decretoPrefettizioScad").property("required", true);
    }

    if ([9].indexOf(sel) > -1) {
        d3.select("#numeroAutorizzazioneRegionaleVetRow").style("display", "");
        d3.select("#numeroAutorizzazioneRegionaleVet").property("required", true);
        d3.select("#provinciaOrdineVetRow").style("display", "");
        d3.select("#provinciaOrdineVet").property("required", true);
        d3.select("#numeroOrdineVetRow").style("display", "");
        d3.select("#numeroOrdineVet").property("required", true);
    }

    if ([2, 3, 19].indexOf(sel) > -1) {
        d3.select("#identificativoEnteRow").style("display", "none");
        d3.select("#identificativoEnte").property("required", false);
    }

    if ([4].indexOf(sel) > -1) {
        d3.select("#comuneRowSezFour").style("display", "");
        d3.select("#addressRowSezFour").style("display", "");
        d3.select("#capRowSezFour").style("display", "");
    }


    //gestione ASL
    document.getElementById("aslEnte").value = -1;
    if (sel == 1) {
        d3.select("#identificativoEnteRow").style("display", "none");
        d3.select("#identificativoEnte").property("required", false);

        d3.select("#aslEnteRow").style("display", "");
    } else {
        d3.select("#aslEnteRow").style("display", "none");
    }

    //GESTIONE CLINICHE IF IZSM
    d3.selectAll("#optCliniche").remove();
    if (sel == 12) {
        RUOLI_GLOB.cliniche_vam_izsm.forEach(function (clinica) {
            d3.select("select#clinicheVam").append("option").html(clinica.descr).attr("value", clinica.id).attr("id", "optCliniche");
        })
    } else {
        RUOLI_GLOB.cliniche_vam.forEach(function (clinica) {
            d3.select("select#clinicheVam").append("option").html(clinica.descr).attr("value", clinica.id).attr("id", "optCliniche");
        })
    }

    //nascondere sez 5 su cancella
    hideSez5($('input[name="tipoRichiesta"]:checked').val());
};

var CONFIRM_VALUE = false;
function save(event, is_print) {
    event.preventDefault();
    if ($('#clinicheVam').val().length == 0 && document.getElementById("ruoloVam").value != -1) {
        alert("Selezionare clinica accessibile Vam!");
        return;
    }

    if (d3.select('input[name="enteRichiedente"]:checked').property("value") == 3 && $('#gestoreAcqua option:selected').val() == -1) {
        alert("Selezionare gestore!");
        return;
    }

    /*  if(document.getElementById('cognome').value == "" || document.getElementById('nome').value == "" || document.getElementById('cf').value == "" ||
          document.getElementById('indirizzo').value == "" || document.getElementById('telefono').value == ""){
            alert("Compilare completamente Sezione 3!");
            return;
      }*/
    /* console.log(document.getElementById("ruoloForze").value);
     console.log($('input[name="enteRichiedente"]:checked').val());
     if (document.getElementById("ruoloForze").value == -1 && $('input[name="enteRichiedente"]:checked').val() == 2) {
       alert("Selezionare ruolo Forze Armate!");
       return;
     }*/
    var data = {};

    $('input[name="tipoRichiesta"][value=' + lastSelected2 + ']').prop("checked", true).attr("checked", "checked");
    document.getElementsByName("tipoRichiesta").forEach(function (d) {
        console.log(d.checked)
        if (!d.checked) {
            d.checked = false;
            d.removeAttribute("checked");
        }
    })

    $('input[name="enteRichiedente"][value=' + lastSelected + ']').prop("checked", true).attr("checked", "checked");
    document.getElementsByName("enteRichiedente").forEach(function (d) {
        console.log(d.checked)
        if (!d.checked) {
            d.checked = false;
            d.removeAttribute("checked");
        }
    })

    // d3.select("input#altro").attr("value", document.getElementById('altro').value);
    data.tipologia_utente = $('input[name="enteRichiedente"]:checked').val(); // + document.getElementById('altro').value;
    data.tipo_richiesta = $('input[name="tipoRichiesta"]:checked').val();

    data.cognome = document.getElementById('cognome').value;
    d3.select("#cognome").attr("value", data.cognome);
    data.nome = document.getElementById('nome').value;
    d3.select("#nome").attr("value", data.nome);
    data.codice_fiscale = document.getElementById('cf').value;
    d3.select("#cf").attr("value", data.codice_fiscale);
    data.email = document.getElementById('indirizzo').value;
    d3.select("#indirizzo").attr("value", data.email);
    data.telefono = document.getElementById('telefono').value;
    d3.select("#telefono").attr("value", data.telefono);

    d3.selectAll('#optGisa').attr("selected", function () {
        d3.select(this).attr("value") == $('#ruoloGisa option:selected').val() ? "selected" : false
    });
    d3.selectAll('#optBdu').attr("selected", function () {
        d3.select(this).attr("value") == $('#ruoloBdu option:selected').val() ? "selected" : false
    });
    d3.selectAll('#optVam').classed("sel", function () {
        d3.select(this).attr("value") == $('#ruoloVam option:selected').val() ? true : false
    });
    d3.selectAll('#optCliniche').attr("selected", function () {
        d3.select(this).attr("value") == $('#clinicheVam option:selected').val() ? "selected" : false
    });
    d3.selectAll('#optGestori').attr("selected", function () {
        d3.select(this).attr("value") == $('#gestoreAcqua option:selected').val() ? "selected" : false
    });
    d3.selectAll('#optDigemon').attr("selected", function () {
        d3.select(this).attr("value") == $('#digemon option:selected').val() ? "selected" : false
    });
    d3.selectAll('#optGiava').attr("selected", function () {
        d3.select(this).attr("value") == $('#giava option:selected').val() ? "selected" : false
    });
    d3.selectAll('#optAsl').attr("selected", function () {
        d3.select(this).attr("value") == $('#aslEnte option:selected').val() ? "selected" : false
    });
    d3.selectAll('#optSP').attr("selected", function () {
        d3.select(this).attr("value") == $('#identificativoEnteSP option:selected').val() ? "selected" : false
    });
    d3.selectAll('#optRegistrazioneSistema').attr("selected", function () {
        d3.select(this).attr("value") == $('#sistemSelect option:selected').val() ? "selected" : false
    });

    data.id_ruolo_ext = 'null';
    data.id_ruolo_gisa = 'null';
    if (lastSelected == 13) {//osa autovalutazione
        data.id_ruolo_ext = $('#giava option:selected').val();
    }
    else if ($('#ruoloGisa option:selected').attr("is_ext")) {
        data.id_ruolo_ext = $('#ruoloGisa option:selected').val();
    } else {
        data.id_ruolo_gisa = $('#ruoloGisa option:selected').val();
    }
    $('#ruoloGisa option:selected').attr("selected", "selected");

    data.id_ruolo_bdu = $('#ruoloBdu option:selected').val();
    $('#ruoloBdu option:selected').attr("selected", "selected");
    data.id_ruolo_vam = $('#ruoloVam option:selected').val();
    $('#ruoloVam option:selected').attr("selected", "selected");
    data.id_clinica_vam = $('#clinicheVam').val().join(",");
    $('#clinicheVam option:selected').attr("selected", "selected");
    data.id_gestore_acqua = $('#gestoreAcqua option:selected').val();
    $('#gestoreAcqua option:selected').attr("selected", "selected");
    data.digemon = $('#digemon option:selected').val();
    $('#digemon option:selected').attr("selected", "selected");
    //data.giava = $('#giava option:selected').val();
    $('#giava option:selected').attr("selected", "selected");

    $('#identificativoEnteSP option:selected').attr("selected", "selected");

    $('#sistemSelect option:selected').attr("selected", "selected");
    data.id_guc_ruoli = $('#sistemSelect option:selected').val();
    if (data.id_guc_ruoli == undefined)
        data.id_guc_ruoli = 'null';

    if (data.id_guc_ruoli == 'null' && data.tipo_richiesta == 2) {
        alert("Selezionare ruolo da modificare!");
        return;
    } else if (data.id_guc_ruoli == 'null' && data.tipo_richiesta == 3) {
        alert("Selezionare ruolo da eliminare!");
        return;
    }

    if (data.tipo_richiesta != 3) {
        if ((data.id_ruolo_gisa == -1 || data.id_ruolo_gisa == "null") && data.id_ruolo_ext == "null" && data.id_ruolo_vam == -1 && data.id_ruolo_bdu == -1) {
            alert("SELEZIONARE ALMENO UN RUOLO!");
            return;
        }
    }


    data.identificativo_ente = document.getElementById('identificativoEnte').value;
    d3.select("#identificativoEnte").attr("value", data.identificativo_ente);
    if ([2, 3].indexOf(lastSelected) > -1) {
        data.identificativo_ente = $('#identificativoEnteSP option:selected').text();
        if (document.getElementById('identificativoEnteSP').value == -1) {
            alert("Selezionare Ente!");
            return;
        }
    }


    data.piva_numregistrazione = document.getElementById('pIva').value;
    d3.select("#pIva").attr("value", data.piva_numregistrazione);

    var elementComune = d3.select("#comuneRowSezFour").style("display") == "none" ? document.getElementById('comune') : document.getElementById('comuneSezFour');
    if (elementComune.value == '')
        data.comune = '';
    else if (RUOLI_GLOB.comuni.find(comune => comune.nome.toUpperCase() == elementComune.value.trim().toUpperCase()) == undefined) {
        alert("Selezionare comune da lista");
        return;
    } else
        data.comune = RUOLI_GLOB.comuni.find(comune => comune.nome.toUpperCase() == elementComune.value.trim().toUpperCase()).istat;
    d3.select("#comune").attr("value", elementComune.value);
    d3.select("#comuneSezFour").attr("value", elementComune.value);

    data.nominativo_referente = document.getElementById('referente').value;
    d3.select("#referente").attr("value", data.nominativo_referente);
    data.ruolo_referente = document.getElementById('ruolo').value;
    d3.select("#ruolo").attr("value", data.ruolo_referente);
    data.email_referente = document.getElementById('indirizzoEnte').value;
    d3.select("#indirizzoEnte").attr("value", data.email_referente);
    data.telefono_referente = document.getElementById('telefonoEnte').value;
    d3.select("#telefonoEnte").attr("value", data.telefono_referente);
    data.codice_gisa = document.getElementById('codiceGisa').value;
    d3.select("#codiceGisa").attr("value", data.codice_gisa);


    data.indirizzo = d3.select("#addressRowSezFour").style("display") == "none" ? document.getElementById('address').value : document.getElementById('addressSezFour').value;
    d3.select("#address").attr("value", data.indirizzo);
    d3.select("#addressSezFour").attr("value", data.indirizzo);

    data.cap = d3.select("#capRowSezFour").style("display") == "none" ? document.getElementById('cap').value : document.getElementById('capSezFour').value;
    d3.select("#cap").attr("value", data.cap);
    d3.select("#capSezFour").attr("value", data.cap);

    if (data.cap.trim() == '80100') {
        alert("CAP 80100 non valido!");
        return;
    }

    data.pec = document.getElementById('pec').value;
    d3.select("#pec").attr("value", data.pec);

    data.numero_decreto_prefettizio = document.getElementById('decretoPrefettizio').value;
    d3.select("#decretoPrefettizio").attr("value", data.numero_decreto_prefettizio);
    data.scadenza_decreto_prefettizio = document.getElementById('decretoPrefettizioScad').value;
    d3.select("#decretoPrefettizioScad").attr("value", data.scadenza_decreto_prefettizio);

    data.numero_ordine_vet = document.getElementById('numeroOrdineVet').value;
    d3.select("#numeroOrdineVet").attr("value", data.numero_ordine_vet);
    data.provincia_ordine_vet = document.getElementById('provinciaOrdineVet').value;
    d3.select("#provinciaOrdineVet").attr("value", data.provincia_ordine_vet);
    data.numero_autorizzazione_regionale_vet = document.getElementById('numeroAutorizzazioneRegionaleVet').value;
    d3.select("#numeroAutorizzazioneRegionaleVet").attr("value", data.numero_autorizzazione_regionale_vet);


    data.asl = 'null';
    if (lastSelected == 1) {
        data.asl = $('#aslEnte option:selected').val();
        $('#aslEnte option:selected').attr("selected", "selected");
        if (data.asl == -1) {
            alert("Selezionare ASL!");
            return;
        }
    }

    data.id_tipologia_trasp_dist = 'null';
    if (lastSelected == 7)
        data.id_tipologia_trasp_dist = 1;
    else if (lastSelected == 20)
        data.id_tipologia_trasp_dist = 2;

    data.html = document.getElementById('modulo').innerHTML;

    console.log(data);

    //alert(JSON.stringify(data));
    console.log(document.getElementById('form_registrazione').checkValidity())
    if (document.getElementById('form_registrazione').reportValidity()) {
        CONFIRM_VALUE = null;
        confirm("La richiesta verrà inoltrata al sistema. Sei sicuro?")
        var checkConfirm = setInterval(function () {
            if (CONFIRM_VALUE != null) {
                clearInterval(checkConfirm);
                if (CONFIRM_VALUE) {
                    document.body.style.pointerEvents = "none";
                    $.post('db.php?operation=save', data,
                        function (returnedData) {
                            console.log(returnedData);
                            var n_richiesta = returnedData.split('||')[0];
                            var avviso = returnedData.split('||')[1];
                            if (!returnedData.startsWith("KO")) {
                                if (avviso != '')
                                    alertBlock(avviso, 'esporta', event, n_richiesta, document.getElementById('modulo').innerHTML, is_print, data.cognome + "_" + n_richiesta);
                                else
                                    esporta(event, n_richiesta, document.getElementById('modulo').innerHTML, is_print, data.cognome + "_" + n_richiesta);
                            }
                            else if (returnedData.startsWith("KO: ESISTE GIA' LA RICHIESTA"))
                                alert(returnedData.replace("KO: ", ""));
                            else
                                alert("ERRORE NELL'INOLTRO DELLA RICHIESTA!");
                            document.body.style.pointerEvents = "auto";
                        });
                }
            }
        }, 100);




    }
    /*}else{
      alert("Assicurarsi di aver compilato le sezioni 3 e 5");
    }*/
    /*let selectClinicheVam = document.getElementById("#clinicheVam");
    let listClinicheVamSelected = $("#optCliniche[selected=selected]");
    console.log(listClinicheVamSelected);
    $("#optCliniche[selected=selected]").prependTo("#clinicheVam");
    $("#clinicheVam").attr("size", `${listClinicheVamSelected.length}`);*/

}


function esporta(event, nRIchiesta, html, is_print, filename) {
    event.preventDefault();
    document.body.style.pointerEvents = "none";

    var sTable = '<label>Da inviare tramite PEC della propria Struttura a casella PEC registrazione.gisa@pec.regione.campania.it </label>\
                <label style="float: right; margin-right: 10px"> ' + nRIchiesta + ' </label>' +
        html +
        "<table>\
          <tr>\
            <td> Luogo </td> <td> Firma del richiedente </td> <td> Firma del responsabile </td>\
           </tr>\
           <tr>\
            <td> &zwnj; <br> &zwnj; </td> <td>  </td> <td>  </td>\
          </tr>\
        </table>";


    // CREATE A WINDOW OBJECT.
    var win = window.open('', '_blank', 'height=' + ($(window).height() - 100) + ',width=' + ($(window).width() - 100));



    /*if(!is_print){
        var splitted = sTable.split('<th colspan="2" id="sez5Row">SEZIONE 5 - Profilazione</th>');
        if(splitted[1] != undefined) //per la cancellazione non esiste la sez 5
            sTable = splitted[0] + '</table><div class="html2pdf__page-break"></div><table>'+ '<th colspan="2" id="sez5Row">SEZIONE 5 - Profilazione</th>' + splitted[1];
    }*/


    win.document.write('<html> <link href=\"../css/bootstrap.min.css" rel="stylesheet" type="text/css\"> <link href=\"modulo.css?v3" rel="stylesheet" type="text/css\"> <head>');
    win.document.write('<title> ' + filename + ' </title>'); // <title> FOR PDF HEADER.
    //   win.document.write(style);          // ADD STYLE INSIDE THE HEAD TAG.
    win.document.write('</head><body style="pointer-events: none;">');
    //  win.document.write('<body>Da inviare tramite PEC corrispondente al proprio domicilio digitale a <casella pec da definire>');
    win.document.write(sTable); // THE TABLE CONTENTS INSIDE THE BODY TAG.
    win.document.write('</body></html>');
    win.document.getElementById("fixedTable").style.tableLayout = "auto";
    win.document.getElementById("sistemSelect").style.background = "white";
    win.document.getElementById("aslEnte").style.background = "white";

    win.document.getElementById("clinicheVamRow").style.display = "none";
    if ($('#clinicheVam option:selected').toArray().length > 0) {
        win.document.getElementById("clinicheVamTextRow").style.display = "";
        win.document.getElementById("clinicheVamText").textContent = $('#clinicheVam option:selected').toArray().map(item => item.text).join("; ");
    }

    if ($('#sistemSelect option:selected').val() != "null") {
        win.document.getElementById("sistemaTextRow").style.display = "";
        //alert($('#sistemSelect option:selected').text());
        win.document.getElementById("sistemText").textContent = $('#sistemSelect option:selected').text();
        win.document.getElementById("sistemaRow").style.display = "none";
    }

    /*d3.select("#clinicheVamRow").style("display", "none");
    d3.select("#clinicheVamTextRow").style("display", "");
    d3.select("#clinicheVamText").text($('#clinicheVam option:selected').toArray().map(item => item.text).join("; "));*/

    win.onbeforeunload = function () {
        document.body.style.pointerEvents = "auto";
        cambioTipologiaUtente(lastSelected);
        //$("#clinicheVam").attr("size", "4"); 
        // GisaSpid.logoutSpid("");
    }

    win.document.close(); // CLOSE THE CURRENT WINDOW.

    win.setInterval(function () {
        win.focus();
    }, 10)

    if (is_print) {
        win.addEventListener('load', function () {
            //GisaSpid.logoutSpid("");
            win.print(); // PRINT THE CONTENTS.
            win.close();
        }, false)
    } else {
        var options = {
            margin: [6, 2, 0, 5],
            filename: filename + '.pdf',
            image: { type: 'jpeg', quality: 1 },
            html2canvas: { scale: 5 },
            jsPDF: { unit: 'mm', format: [210, 298 + 80] },
            pagebreak: { mode: ['avoid-all', 'avoid-all', 'avoid-all'] }
        };
        sTable = win.document.documentElement.innerHTML; //per vedere le modifiche alle cliniche
        html2pdf().set(options).from(sTable).save().then(function () {
            win.close();
            alert("Pre-registrazione andata a buon fine. La richiesta, per essere lavorata, dovrà essere stampata ed inviata via PEC all’indirizzo registrazione.gisa@pec.regione.campania.it esclusivamente in formato PDF. Altre tipologie di formato saranno scartate automaticamente");
        });

        /*win.creaPdf = function(){
            if(!win.html2pdf){
                setTimeout(function(){
                    win.creaPdf()
                    console.log("wait");
                },1000);
            }else{
                console.log(options);
                win.options = options;
                win.sTable = sTable;
                win.html2pdf(win.sTable, win.options); 
                //win.close();
                GisaSpid.logoutSpid("");
                setInterval(function(){
                    win.focus();
                }, 1)
            }
        }
        win.creaPdf();*/
        //win.close(); // CLOSE THE CURRENT WINDOW.
    }
}


function changeSistemaRuolo(sel) {

    var testo = sel.options[sel.selectedIndex].text

    document.getElementById("ruoloGisa").disabled = true;
    document.getElementById("ruoloVam").disabled = true;
    document.getElementById("ruoloBdu").disabled = true;
    document.getElementById("digemon").disabled = true;

    if (testo.includes("GISA")) {
        document.getElementById("ruoloGisa").disabled = false;
    } else if (testo.includes("VAM")) {
        document.getElementById("ruoloVam").disabled = false;
    } else if (testo.includes("BDU")) {
        document.getElementById("ruoloBdu").disabled = false;
    } else if (testo.includes("DIGEMON")) {
        document.getElementById("digemon").disabled = false;
    }

    //sincronizzo asl
    var id_asl = sel.options[sel.selectedIndex].attributes.id_asl.value;
    document.getElementById('aslEnte').value = id_asl;
    document.getElementById('aslEnte').disabled = false;
    if (id_asl != -1)
        document.getElementById('aslEnte').disabled = true;


}

function alert(textD) {
    $(function () {
        $("#dialog").dialog({
            autoOpen: true,
            width: 500,
            text: textD
        })
    });
    document.getElementById("dialogText").innerText = textD;
}

function alertBlock(textD, callback, ev, n_richiesta, html, is_print, filename) {
    $(function () {
        document.getElementById("dialogBlockingText").innerText = textD;
        $("#dialog-blocking").dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true,
            buttons: {
                "OK": function () {
                    window[callback](ev, n_richiesta, html, is_print, filename)
                    $(this).dialog("close");
                }
            }
        });
    });
}



function confirm(text) {
    $(function () {
        document.getElementById("confirmText").innerText = text;
        $("#dialog-confirm").dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true,
            buttons: {
                "Continua": function () {
                    CONFIRM_VALUE = true;
                    $(this).dialog("close");
                },
                "Annulla": function () {
                    CONFIRM_VALUE = false;
                    $(this).dialog("close");
                }
            }
        });
    });
}

window.addEventListener('load', function () {
    console.log("click");
    getConfigs();
}, false)
