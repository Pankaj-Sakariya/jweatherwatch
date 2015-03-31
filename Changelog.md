## jWeatherWatch v1.3.5 ##
  * Removed direct Libnotify support using gnome-java cause its to unstable
    * If you still interested in good locking notifications on Linux try [SnoreNotify](http://code.google.com/p/snorenotify/)
  * fixed word time clock

## jWeatherWatch v1.3.4 ##
  * fixed GThread issue when switching to LibNotify
  * fixed X Errors when using LibNotify
  * cleaned up some mess
  * used new restart feature to switch between dev/nonDev channel
  * added auto start support for Linux
  * jWeatherWatch adds itself to ~/bin/ if ~/bin/ exists
  * removed unavailable notifier from combobox in settings dialog
  * added install uninstall command and functions
  * added start menu entry on windows


## jWeatherWatch v1.3.3 ##
  * fixed LibNotify
  * added LibNotify support on x64 systems
  * removed java-gnome from package you have now to install it first see [LibNotify](LibNotify.md)

## jWeatherWatch v1.3.2 ##
  * jWeatherWatch saves settings on System Shutdown
  * jWeatherWatch should be more stable and use less memory
  * fixed a bug which prevented switching dev/release channel in Advanced-Settingstab

## jWeatherWatch v1.3.1 ##
  * Fixed a bug with " " in installation path
  * added portable option, now its possible to start jWeatherWatch with the argument -portable, then the settings will be saved in the same path as the jar
## jWeatherWatch v1.3 ##
  * Added library BrwoserLauncher2 now its possible to select a browser(Settings->Advanced)
    * to use this with Chromium on linux you have to set Chromium to your default browser and select Konquerer(kde) to your browser in jWeatherWatch
  * Added library junique now jWeatherWatch comes to front if you try to start a second instance
  * modified lib-growl(NetGrowl) its now possible to detect if growl is running
  * With the new version it is possible to receive "nightly builds"
  * Wrote a small installer using java webstart [Try It](http://jweatherwatch.googlecode.com/svn/installer/dist/installer.jnlp)
    1. The installer downloads and extract the files to a directory you choose
    1. on Windows it creates a desktop shortcut
    1. it starts jWeatherWatch after completing installation

  * Implemented auto update
  * mainframe now extends JDialog --> no minimize maximize button anymore
  * added actions to supporting  Notificationsystems
    1. [NetSnarl](http://code.google.com/p/jweatherwatch/wiki/Snarl) Lefclick bring to front, Rightclick visit forecast
    1. LibNotify added buttons for these actions
    1. Tray icon click brings the application to front

  * support for lib notify
    1. On Gnome ![http://jweatherwatch.googlecode.com/svn/images/libnotify%20gnome.png](http://jweatherwatch.googlecode.com/svn/images/libnotify%20gnome.png)
    1. On KDE 4 ![http://jweatherwatch.googlecode.com/svn/images/libnotify%20kde.png](http://jweatherwatch.googlecode.com/svn/images/libnotify%20kde.png)
## jWeatherWatch v1.2.2 ##
  * fixed auto start setting in windows
## jWeatherWatch v1.2.1 ##
  * fixed NetGrowl notifier thanks to danmancuso and wamcgruder for leaving a comment and reported this bug, if you find a bug leave a [Comment](http://code.google.com/p/jweatherwatch/wiki/Comments)
## jWeatherWatch v1.2.8 ##
  * fixed a bug who prevented weather display update after removing of a location
  * jWeatherWatch now hides on window closing
  * TraIcon is now the default notifier because there was a problem with notifier recognition, it always ended up with Snarl, just set the notifier in the settings dialog.

### jWeatherWatch v1.2.8 RC5 ###
  * implemented new version of SnarlNetworkBridge it now has icon support

### jWeatherWatch v1.2.8 RC4 ###
  * Hopefully fixed bug with iconpath....

### jWeatherWatch v1.2.8 RC3 ###
  * fixed clock in default view
  * changed running check

### jWeatherWatch v1.2.8 RC2 ###
  * fixed the ability to set a different icon path
  * single application instance control

### jWeatherWatch v1.2.8 RC1 ###
  * minimal View
  * better exception handling
  * advanced settings
  * new Settings Dialog
  * faster data fetching
  * added support for SNP 1.1(in development)
  * fixed auto start
<br>
<img src='http://jweatherwatch.googlecode.com/svn/images/minimal%20view.png' /></li></ul>

<h2>jWeatherWatch v1.2.7.1 ##
  * Added workaround for currupted settingsxml,profile.xml

## jWeatherWatch v1.2.7 ##
  * Created launcher exe and changed release to zip, to make it essayer for windows user to start jWeatherWatch

## jWeatherWatch v1.2.5.6.1 ##
  * Fixed remove from auto start

## jWeatherWatch v1.2.5.6 ##
  * Fixed bug With Remove button
  * Changed display of current time
  * Unregister Notifier on host change
  * Changed application Icon
  * Icon in Settings Dialog


## jWeatherWatch v1.2.5.5 Beta ##
  * Fixed bug which prevented jWeatherWatch to start on winxp Systems.

Thanks to Brian from the Growl for Windows team for reporting this bug

## jWeatherWatch v1.2.5.4 Beta ##
  * Remote Icon Support
  * Fixed some bugs in Settings Dialog
  * Reorganized Settings Dialog
  * Fixed critical bug on initialize
  * Added Start wit System option on windows
  * added start arguments
    1. workingdirectory, used to start jWeather watch from outside  the icon location
    1. minimized to start jWeatherWatch minimized

## jWeatherWatch v1.2.5.3 Beta ##
  * Added Icons to Growl suppoert

## jWeatherWatch v1.2.5.2 Beta ##
  * Fixed Notification timer, incorrect conversion from ms to min

## jWeatherWatch v1.2.5.1 Beta ##
  * New in this version is the automatic loading and saving of the settings.
  * The possibility to Change the host in Notification systems which support remote hosts.
  * Changing the Notification system while the programme is running.
  * Support for Growl using GNTP, only tested on windows with [Growl for Windows](http://growl.info/index.php)
> > ![http://jweatherwatch.googlecode.com/svn/images/win_gntp.png](http://jweatherwatch.googlecode.com/svn/images/win_gntp.png)
  * Support for KNotify on KDE4
> > ![http://jweatherwatch.googlecode.com/svn/images/knotify.png](http://jweatherwatch.googlecode.com/svn/images/knotify.png)