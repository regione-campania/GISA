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