/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
//rotta per ispezioni
var express = require('express');
var router = express.Router();
var conn = require('../db/connection');
const conf = require('../config/config.js');

router.get("/getMacchine", function (req, res) {

    try {

        conn.client.query(`select * from gds_srv.vw_macchine`, (err, result) => {
            if (err) {
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                res.json(result.rows).end();
            }
        })


    } catch (e) {
        console.log(e.stack);
        res.writeHead(500).end();
    }

})



router.get("/getCantieriAttivi", function (req, res) {

    try {

        conn.client.query(`select * from gds_srv.vw_cantieri_attivi`, (err, result) => {
            if (err) {
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                res.json(result.rows).end();
            }
        })

    } catch (e) {
        console.log(e.stack);
        res.writeHead(500).end();
    }

})


router.get("/getCantiereImpreseSedi", function (req, res) {

    try {

        const idCantiere = req.query.idCantiere;
        const query = `select * from gds_srv.vw_cantIere_impresa_sedi where id_cantiere = ${idCantiere}`;
        console.log(query);

        conn.client.query(query, (err, result) => {
            if (err) {
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                res.json(result.rows).end();
            }
        })

    } catch (e) {
        console.log(e.stack);
        res.writeHead(500).end();
    }

})


router.get("/getIspezioni", function (req, res) {

    try {
        console.log("getIspezioni");
        if(!conf.ispezioniEnabled){
            res.writeHead(500).end();
        } else {

            conn.client.query(`select descr_isp, codice_ispezione, id_ispezione, descr_ente_isp,descr_uo_isp,descr_motivo_isp,cantiere_o_impresa,data_ispezione,descr_ente,descr_stato_ispezione
                                from gds_srv.vw_nucleo_ispettori order by data_modifica desc`, (err, result) => {
                if (err) {
                    console.log(err.stack)
                    res.writeHead(500).end();
                } else {
                    res.json(result.rows).end();
                }
            })
        }


    } catch (e) {
        console.log(e.stack);
        res.writeHead(500).end();
    }

})

router.get("/insertIspezione", function (req, res) {
    // const id_utente = JSON.parse(req.body.id_soggetto_notificante).valore;
    const id_utente = 1
    // async/await
    try {

        var url = `call gds_srv.upd_dati('ins_ispezione', null, ${id_utente}, null)`;
        console.log(url);
        conn.client.query(url, (err, result) => {
            if (err) {
                console.log("error in query");
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                console.log(result.rows);
                res.send(result.rows[0].joutput).end();
            }
        })


    } catch (err) {
        console.log(err.stack)
        res.writeHead(500).end();
    }

})


router.get("/getIspezioneInfo", async function (req, res) {

    try {

        const idIspezione = req.query.idIspezione;
        var response = {};
        console.log("getIspezioneInfo " + idIspezione);
        var url = `call gds_srv.get_dati('get_ispezione', '{"id": "${idIspezione}"}', 1, null)`;
        console.log(url);
        var result = await conn.client.query(url);
        response = JSON.parse(result.rows[0].joutput.info);

        res.json(response).end();


    } catch (err) {
        console.log(err.stack)
        res.writeHead(500).end();
    }

})

router.get("/getIspezioneFasi", async function (req, res) {

    try {
        response = {};
        const idIspezioneFase = req.query.idIspezioneFase;
        console.log("getIspezioneFasi " + idIspezioneFase);
            var url = `call gds_srv.get_dati('get_ispezione_fase', '{"id": "${idIspezioneFase}"}', 1, null)`;
            console.log(url);
            var result = await conn.client.query(url);
            response = JSON.parse(result.rows[0].joutput.info);

            res.json(response).end();

    } catch (e) {
        console.log(e.stack);
        res.writeHead(500).end();
    }

})

router.get("/getMotiviIspezione", function (req, res) {

    try {
        console.log("getMotiviIspezione");

        conn.client.query(`select *
                               from gds_types.vw_motivi_isp`, (err, result) => {
            if (err) {
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                res.json(result.rows).end();
            }
        })


    } catch (e) {
        console.log(e.stack);
        res.writeHead(500).end();
    }

})

router.get("/getEsitiFaseTree", function (req, res) {

    try {
        const idIspezione = req.query.idIspezione;
        console.log(`getEsitiFaseTree`);

        const query = `select *
        from gds.vw_fasi_esiti_per_ispezione where id_ispezione = ${idIspezione} order by id_fase, id_esito_per_fase`;
        console.log(query);

        conn.client.query(query, (err, result) => {
            if (err) {
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                var response = [];
                var currFase = {};

                result.rows.forEach(function(row , i) {
                    var fase = {};
                    if (i == 0) { //primo
                        fase.id_fase = row.id_fase;
                        fase.descr = row.descr_fase;
                        fase.children = [];
                        var child = {}
                        child.id_fase_esito = row.id_fase_esito_possibile;
                        child.id_esito_per_fase = row.id_esito_per_fase;
                        child.descr_esito_per_fase = row.descr_esito_per_fase;
                        child.riferimento_fase_esito = row.riferimento_fase_esito;
                    } else {
                        if (row.id_fase == currFase.id_fase) {
                            var child = {}
                            child.id_fase_esito = row.id_fase_esito_possibile;
                            child.id_esito_per_fase = row.id_esito_per_fase;
                            child.descr_esito_per_fase = row.descr_esito_per_fase;
                            child.riferimento_fase_esito = row.riferimento_fase_esito;
                            fase = currFase;
                            fase.children.push(child);
                        } else {
                            response.push(currFase);
                            currFase = {};
                            fase.id_fase = row.id_fase;
                            fase.descr = row.descr_fase;
                            fase.children = [];
                            var child = {}
                            child.id_fase_esito = row.id_fase_esito_possibile;
                            child.id_esito_per_fase = row.id_esito_per_fase;
                            child.descr_esito_per_fase = row.descr_esito_per_fase;
                            child.riferimento_fase_esito = row.riferimento_fase_esito;
                            fase.children.push(child);
                        }
                    }
                    currFase = fase;
                    if (i == result.rowCount - 1) { //ultimo che altrimenti rimarrebbe fuori
                        response.push(currFase);
                    }
                })

                res.json(response).end();
            
            }
        })


    } catch (e) {
        console.log(e.stack);
        res.writeHead(500).end();
    }

})


router.get("/getEsitiFase", function (req, res) {

    try {
        console.log("getEsitiFase");

        conn.client.query(`select *
                               from gds_types.vw_fasi_esiti order by id_fase, id_esito_per_fase`, (err, result) => {
            if (err) {
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                res.json(result.rows).end();
            }
        })


    } catch (e) {
        console.log(e.stack);
        res.writeHead(500).end();
    }

})


router.post("/updateIspezioneInfo", async function (req, res) {

    req.session.idNotificante = 1; //dovrà essere id_ispettore
    console.log(JSON.stringify(req.body));
    console.log(req.session.idNotificante);

    try {

        var url = `call gds.upd_dati('upd_ispezione','${JSON.stringify(req.body).replace(/'/g, "''")}', '${req.session.idNotificante}', null)`;
        console.log(url);
        result = await conn.client.query(url);
        console.log(result.rows[0].joutput);
        res.json(result.rows[0].joutput)



    } catch (err) {
        console.log(err.stack)
        await conn.client.query('ROLLBACK');
        res.writeHead(500).end();
    }

})



router.post("/updateFaseInfo", async function (req, res) {

    req.session.idNotificante = 1; //dovrà essere id_ispettore
    console.log(JSON.stringify(req.body));
    console.log(req.session.idNotificante);

    try {

        var url = `call gds.upd_dati('upd_ispezione_fase','${JSON.stringify(req.body).replace(/'/g, "''")}', '${req.session.idNotificante}', null)`;
        console.log(url);
        result = await conn.client.query(url);
        console.log(result.rows[0].joutput);
        res.json(result.rows[0].joutput)



    } catch (err) {
        console.log(err.stack)
        await conn.client.query('ROLLBACK');
        res.writeHead(500).end();
    }

})


router.get("/insertIspezioneFase", function (req, res) {

    const id_utente = 1
    const idIspezione = req.query.idIspezione;
    // async/await
    try {

        var url = `call gds_srv.upd_dati('ins_ispezione_fase', '{"id_ispezione" : "${idIspezione}"}', ${id_utente}, null)`;
        console.log(url);
        conn.client.query(url, (err, result) => {
            if (err) {
                console.log("error in query");
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                console.log(result.rows);
                res.send(result.rows[0].joutput).end();
            }
        })

    } catch (err) {
        console.log(err.stack)
        res.writeHead(500).end();
    }

})


router.get("/getModuli", function (req, res) {

    try {

        conn.client.query(`select * from gds_srv.vw_moduli`, (err, result) => {
            if (err) {
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                res.json(result.rows).end();
            }
        })

    } catch (e) {
        console.log(e.stack);
        res.writeHead(500).end();
    }

})


router.get("/insertVerbaleFase", async function (req, res) {

    try {

        const idModulo = req.query.idModulo;
        const idIspezioneFase = req.query.idIspezioneFase
        const dataVerbale = req.query.dataVerbale

        var query = `select * from gds.ins_verbale('{"id_modulo":${idModulo},"data_verbale":"'||to_char(current_date, 'YYYY-MM-DD')||'"}', 1)`;
        console.log(query);
        await conn.client.query('BEGIN');

        conn.client.query(query, (err, result) => {
            if (err) {
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                if(result.rows[0].esito){
                    const idVerbale = result.rows[0].valore;
                    query = `select * from gds.ins_fase_verbale('{"id_verbale":"${idVerbale}","id_ispezione_fase":"${idIspezioneFase}"}', 1)`;
                    console.log(query);
                    conn.client.query(query, (err, result2) => {
                        if(!result2.rows[0].esito){
                            conn.client.query('ROLLBACK');
                        }
                        conn.client.query('COMMIT');
                        res.json(result.rows[0]).end();
                    })
                }else{
                    res.json(result.rows[0]).end();
                }
            }
        })

    } catch (e) {
        console.log(e.stack);
        await conn.client.query('ROLLBACK');
        res.writeHead(500).end();
    }

})





module.exports = router;
