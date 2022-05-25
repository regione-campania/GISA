const { Pool } = require('pg')
var types = require('pg').types
var moment = require('moment')

var parseTs = function(val) {
   return val === null ? null : moment(val).format("YYYY-MM-DD HH:mm:ss");
}
var parseDt = function(val) {
  return val === null ? null : moment(val).format("YYYY-MM-DD");
}
types.setTypeParser(types.builtins.TIMESTAMPTZ, parseTs)
types.setTypeParser(types.builtins.TIMESTAMP, parseTs)
types.setTypeParser(types.builtins.DATE, parseDt)

var conf = require('../config/config.js');

const client = new Pool({
  user: conf.dbUser,
  host: conf.dbHost,
  database: conf.dbName,
  password: conf.dbPassword,
  port: conf.dbPort,
})
client.connect();
/*console.log("Server connected to db");
console.log(client.options);*/

exports.client = client;