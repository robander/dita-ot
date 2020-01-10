rem call %dcsclient% -install

rem HTML5 TESTS WITH DCS
rem call %dcsclient% -i jira226.ditamap -f ibmhtml5 -filter \dcs\test\jira226\simple\ditavaldir\revs.ditaval -temp temp -Dclean.temp=no -v -o out
rem call %dcsclient% -i jira226.ditamap -f ibmhtml5 -filter "..\ditavaldir\revs.ditaval;..\ditavaldir\defaults.ditaval" -temp temp -Dclean.temp=no -o relpaths -l logrelpaths.log -v

rem PDF WITH DCS
rem call %dcsclient% -i jira226.ditamap -f pdf -filter \dcs\test\jira226\simple\ditavaldir\revs.ditaval -temp temppdf -Dclean.temp=no -v -l logpdf.txt


rem DITA-OT GITHUB COPY
call \github\robander\dita-ot\src\main\bin\dita -i jira226.ditamap -f html5 -filter \dcs\test\jira226\simple\ditavaldir\revs.ditaval -temp tempOOB -Dclean.temp=no -o outOOB -v -l loghtml.txt
call \github\robander\dita-ot\src\main\bin\dita -i jira226.ditamap -f pdf -filter \dcs\test\jira226\simple\ditavaldir\revs.ditaval -temp tempOOBpdf -Dclean.temp=no -o outOOB -v -l logpdf.txt
call \github\robander\dita-ot\src\main\bin\dita -i jira226.ditamap -f html5 -filter \dcs\test\jira226\simple\ditavaldir\revs.ditaval -temp tempOOB-checkin -Dclean.temp=no -o outOOB-checkin -v -l loghtml.txt

rem ET CETERA
rem call \ditatools\dcs-2.5\bin\dita -i jira226.ditamap -f ibmhtml5 -filter \dcs\test\jira226\simple\ditavaldir\revs.ditaval -temp temp25 -Dclean.temp=no -o out25
REM call \dita-ot\dita-ot-3.3.2\bin\dita -i jira226.ditamap -f html5 -filter \dcs\test\jira226\simple\ditavaldir\revs.ditaval -temp tempOOB332 -Dclean.temp=no -o outOOB332 -v
rem call \dita-ot\dita-ot-3.3.2\bin\dita -i jira226.ditamap -f pdf -filter \dcs\test\jira226\simple\ditavaldir\revs.ditaval -temp tempOOB332pdf -Dclean.temp=no -o outOOB332 -v -l logpdf332.txt