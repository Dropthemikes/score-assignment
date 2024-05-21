## Purpose of Test
The test will be run using the Gauge behaviour Driven Testing framework to go through a user-journey scenario of Logging in, navigating to the leagues page, selecting a league and verifying that the list of teams in the standings tab belong to the correct league.
The scenarios can be found in the specs directory.

Scenario example:
```
Leagues
=====================
tags: leagues, android

* Navigate to "Leagues" Page

User views NHL league standings
----------------
tags:nhl

* Select "NHL" from leagues tab
* Open "Standings" tab
* Verify standings
* Navigate to previous page
```

## Pre-requisites
1. Install Java, set JAVA_HOME in environment variables and add bin folder to path variables.
2. Install Maven and set MAVEN_HOME & M2_HOME in environment variables and add bin folder to path variables.
3. Install Android studio and set ANDROID_HOME in environment and add bin, platform-tools, build-tools and emulator to path variables.
4. Install Intellij Community Edition.

## Install Gauge
1. Install gauge with windows installer by following step 1 on the [Gauge Install Page](https://docs.gauge.org/getting_started/installing-gauge?os=windows&language=java&ide=vscode)
   (Alternatively, you can install gauge using chocolatey by opening  cmd and typing `choco install gauge`. if chocolatey is not installed, open cmd from administrator and enter `@"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"`)
2. Verify installation by opening command prompt and typing  `gauge -v`
3. If Java plugin does not appear in list of plugins, open cmd and  enter `gauge install java`
4. If html-report plugin does not appear in list of plugins, open cmd and enter `gauge install html-report`

## Intellij Plugins
1. Open File>Settings>Plugins
2. Install `Gauge` plugin

## Build/Device setup
* create folder called `build` in project and add .apk file, rename to `com.fivemobile.thescore.apk` (Version used for testing: com.fivemobile.thescore_24.8.0-33339.apk from APKMIRROR)
* If necessary, Go to src/test/java/gaugeSteps/BaseClass to change appium device capabilities in the setUp method (device name, platform version, UDID, etc) Currently set to run on Pixel 6 emulator. (To find UDID, open cmd and enter `adb devices` while device is plugged in or emulator running.)
* If necessary, change the email & password  for loginUser(); (currently set to my personal account)
* 

## Running Test/Specification class
* Make sure Appium is running and device or emulator is connected.

All tests can be run directly from the .spec class in the `specs` directory. Either by right clicking on the .spec class and select Run from the menu or by using the 

Run commands in terminal (Add  `test-compile` after mvn to get latest changes when necessary)

*  `mvn gauge:execute -DspecsDir=specs` Runs all specification classes (.spec) that are in the specs directory.
*  `mvn gauge:execute -DspecsDir=specs -Dtags="***tagName***"` Use -Dtags to only run tests that are tagged with the assigned keyword. Multiple tags can be accepted (-Dtags="leagues & Android".) Tags can also be excluded as well using ! operator(D-tags="!leagues")
*  `mvn gauge:execute -DspecsDir=specs -Denv="***environmentDirectory***"` Use -Denv to use property files other than default.
*  `mvn gauge:execute -DspecsDir=specs -Dscenario="***scenarioName***"` use -Dscenario to run specific scenario using name of scenario, example -Dscenario="User views CFL league standings"
*  `mvn gauge:execute -Dflags="--failed"` ReRuns any failed scenario from the previous run.

## Viewing Gauge report
* Double click `scoreAssignment\reports\html-report\index.html` to view report.  

## Troubleshooting if there are compile issues
* Build Project (Ctrl + F9)
* run mvn clean, install.
