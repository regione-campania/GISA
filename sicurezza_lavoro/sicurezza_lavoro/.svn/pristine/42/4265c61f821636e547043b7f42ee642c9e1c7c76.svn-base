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