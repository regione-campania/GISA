/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
//rotta per anagrafiche
var express = require('express');
var router = express.Router();
var conn = require('../db/connection');
var um = require('./um');


router.get("/getComuni", function (req, res) {

    try {

        conn.client.query(`select * from gds_srv.vw_comuni`, (err, result) => {
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


router.get("/getComuniCantiere", function (req, res) {

    try {

        conn.client.query(`select * from gds_srv.vw_comuni_cantiere`, (err, result) => {
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

router.get("/getImprese", function (req, res) {

    try {

        conn.client.query(`select * from gds_srv.vw_imprese`, (err, result) => {
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

router.get("/getImpreseSedi", function (req, res) {

    try {

        conn.client.query(`select * from gds_srv.vw_impresa_sedi`, (err, result) => {
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

router.get("/getSoggettiFisici", function (req, res) {

    try {

        conn.client.query(`select * from gds_srv.vw_soggetti_fisici`, (err, result) => {
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

router.get("/geocode", async function (req, res) {
    try {
        const mapUtilts = require("../utils/mapUtils");
        res.send(await mapUtilts.geocode(req.query.indirizzo));
    } catch (e) {
        console.log(e.stack);
        res.send(null);
    } finally {
        res.end();
    }
})

router.get("/getEntiUnitaOperative", function (req, res) {

    try {

        conn.client.query(`select * from gds_types.vw_ente_uo order by id_ente, id_uo`, (err, result) => {
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


router.get("/getEntiUnitaOperativeTree", function (req, res) {

    try {
        console.log("getEntiUo");

        var where = '';
        if(req.query.isAsl == 'true')
            where = ' where id_asl is not null ';
        else if(req.query.isAsl == 'false')
            where = ' where id_asl is null ';

        conn.client.query(`select * from gds_types.vw_ente_uo ${where} order by id_ente, id_uo`, (err, result) => {
            if (err) {
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                try {
                    var response = [];
                    var enteCurr = {};
                    //console.log(result.rows);
                    result.rows.forEach(function (struttura, i) {
                        var ente = {};
                        if (i == 0) { //primo
                            ente.id_ente = struttura.id_ente;
                            ente.descr = struttura.descr_ente;
                            ente.id_asl = struttura.id_asl;
                            ente.children = [];
                            var child = {}
                            child.id_ente_uo = struttura.id_ente_uo;
                            child.id_ente = struttura.id_ente;
                            child.id_uo = struttura.id_uo;
                            child.descr = struttura.descr_uo;
                            child.descr_ente_uo = struttura.descr_ente_uo;
                            ente.children.push(child);
                            //enteCurr = ente
                        } else {
                            if (struttura.id_ente == enteCurr.id_ente) {
                                var child = {}
                                child.id_ente_uo = struttura.id_ente_uo;
                                child.id_ente = struttura.id_ente;
                                child.id_uo = struttura.id_uo;
                                child.descr = struttura.descr_uo;
                                child.descr_ente_uo = struttura.descr_ente_uo;
                                ente = enteCurr;
                                ente.children.push(child);
                                //enteCurr = ente;
                            } else {
                                response.push(enteCurr);
                                enteCurr = {};
                                ente.id_ente = struttura.id_ente;
                                ente.descr = struttura.descr_ente;
                                ente.id_asl = struttura.id_asl;
                                ente.children = [];
                                var child = {}
                                child.id_ente_uo = struttura.id_ente_uo;
                                child.id_ente = struttura.id_ente;
                                child.id_uo = struttura.id_uo;
                                child.descr = struttura.descr_uo;
                                child.descr_ente_uo = struttura.descr_ente_uo;
                                ente.children.push(child);
                            }
                        }
                        enteCurr = ente;
                        if (i == result.rowCount - 1) { //ultimo che altrimenti rimarrebbe fuori
                            response.push(enteCurr);
                        }
                    })

                    res.json(response).end();

                } catch (err) {
                    console.log(err.stack)
                    res.writeHead(500).end();
                }
            }
        })


    } catch (e) {
        console.log(e.stack);
        res.writeHead(500).end();
    }

})

module.exports = router;
