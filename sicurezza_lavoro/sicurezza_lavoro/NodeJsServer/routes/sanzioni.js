//rotta per notifiche
var express = require('express');
var router = express.Router();
var conn = require('../db/connection');
var auth = require('../utils/auth');

router.get("/getPagatori", function (req, res) {
    
    try {

        conn.client.query(`select * from pagopa.vw_anagrafica_pagatori`, (err, result) => {
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

router.get("/getSanzioneInfo", async function (req, res) {

    const idSanzione = req.query.idSanzione;
    //const idNotificante = req.authData.idUtente;
    const idUtente = 1;

    var response = {};
    // async/await
    try {

        var url = `call gds.get_dati('get_sanzione','{"id":"${idSanzione}"}', '${idUtente}', null)`;
        console.log(url);
        result = await conn.client.query(url);
        response = JSON.parse(result.rows[0].joutput.info);

        res.json(response).end();


    } catch (err) {
        console.log(err.stack)
        res.writeHead(500).end();
    }

})

router.get("/insertSanzione", function (req, res) {

    //const idNotificante = req.authData.idUtente;
    const idIspezione = req.query.idIspezione;
    const idUtente = 1;

    // async/await
    try {

        var url = `call gds_srv.upd_dati('ins_sanzione', '{"id_sanzione" : "${idIspezione}"}', ${idUtente}, null)`;
        console.log(url);
        conn.client.query(url, (err, result) => {
            if (err) {
                console.log("error in query");
                console.log(err.stack)
                res.writeHead(500).end();
            } else {
                console.log(result.rows);
                res.json(result.rows[0].joutput).end();
            }
        })


    } catch (err) {
        console.log(err.stack)
        res.writeHead(500).end();
    }

})


module.exports = router;