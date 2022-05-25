: '
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.'
#!/bin/bash

FILE=$(basename $PWD)



if [ -f "./bin/$FILE" ]; then
   echo mv -f --backup=numbered "./bin/$FILE" "./old/$FILE"
   #mv -f --backup=numbered "./bin/$FILE" "./old/$FILE"
   rm -f  "./bin/$FILE" 
fi
echo $(date)
cd app
go build  -o "../bin/$FILE" *
echo $(date)

cd ..
##./stop.sh

