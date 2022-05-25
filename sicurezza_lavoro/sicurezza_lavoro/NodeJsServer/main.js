/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
const express = require('./app/app');
//const expressStatic = require('./app/appStatic');
var conf = require('./config/config');
var https = require('https');
var fs = require('fs');

process.env.TZ = 'Europe/Amsterdam'
console.log(conf);

const serverHttp = express.app.listen(conf.httpPort, function () {
  var host = serverHttp.address().address
  var port = serverHttp.address().port
  console.log("Server listening at http://%s:%s", host, port)
})

//se sul server sono presenti le chiavi ssl per l https starto il server anche sulla porta https
if (fs.existsSync(conf.sslPrivateKeyLocation) && fs.existsSync(conf.sslPublicCertLocation)) {
  const httpsOptions = {
    key: fs.readFileSync(conf.sslPrivateKeyLocation),
    cert: fs.readFileSync(conf.sslPublicCertLocation),
  }
  const serverHttps = https.createServer(httpsOptions, express.app).listen(conf.httpsPort, function () {
    var host = serverHttps.address().address
    var port = serverHttps.address().port
    console.log("Server listening at https://%s:%s", host, port)
  })
  
} //else { //il server static sale solo su un protocollo http o https

  

//}