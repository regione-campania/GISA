/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
const jwt = require('jsonwebtoken');
const conf = require('../config/config.js');



function generateAccessToken(data) {
    return jwt.sign(data, conf.tokenSecret, { expiresIn: '12h' });
}

function authenticateToken(req, res, next) {
    const authHeader = req.headers['authorization']
    const token = authHeader && authHeader.split(' ')[1]

    if (token == null){
        console.log("AuthToken null");
        return res.sendStatus(401);
    } 

    jwt.verify(token, conf.tokenSecret, (err, decryptedData) => {

        if (err){
            console.log("AuthToken non valido");
            console.log(err);
            return res.sendStatus(403);
        } 
        req.authData = decryptedData;
        console.log("Utente autorizzato " + JSON.stringify(req.authData))
        next()
    })
}


exports.generateAccessToken = generateAccessToken;
exports.authenticateToken = authenticateToken;