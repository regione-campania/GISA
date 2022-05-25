/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package main

import (
	"io/ioutil"
	"os"
	"github.com/tidwall/gjson"
)

var ConfigJson =  []byte{}

func setConfigFile(f string) {
	configFile, _ := os.Open(f)
    cj, _ := ioutil.ReadAll(configFile)
	ConfigJson = cj;
}

func getProperty(p string) string {
    return gjson.GetBytes(ConfigJson, p).String()
}

func getConfig() string {
	return string(ConfigJson)
}
