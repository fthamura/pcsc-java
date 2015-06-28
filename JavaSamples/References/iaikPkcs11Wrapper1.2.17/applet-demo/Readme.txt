This directory contains build scripts written for Apache ANT.

This demos assumes that you have installed a Java plug-in version 1.3 or higher.
There are different approaches to install the PKCS#11 Wrapper. 

First, you can simply copy the iaikPkcs11Wrapper.jar into the JRE/lib/ext 
directory and the pkcs11wrapper.dll in your JRE/bin directory (Windows/System32 
should also work). On Unix platforms, you have to use the libpkcs11wrapper.so 
instead of the DLL.

Second, you can use the automatic extension installation mechanism of the
Java plug-in (version 1.3 or later). The JAR file of this demo uses this
feature. It works through entries in the manifest file which point to an
installer for the extension. This installer is loaded and executed unless the
extension has already been installed. For details please read

http://java.sun.com/j2se/1.5.0/docs/guide/plugin/developer_guide/extensions.html

Per default, this demo JAR points the installer file 

file:///temp/$(os-name)$/PKCS11WrapperWin32Installer.jar

The Java plug-in does not seem to accept relative URLs at this place. The 
plug-in evaluates the $(os-name)$ part according to its underlying platform.
This is depends on the Java system property "os.name", where spaces are replaced
by a minus '-'; e.g. Windows-98, Windows-ME, Windows-2000 or Windows-XP.
Thus, you have to place this file there or modify the manifest file; e.g. edit 
the URL in lib/demo.MF and rebuild the demo JAR using the buidl.xml ANT script 
(type "ant jar"). Please take special care with whitespace characters when
modifying manifest entries. Trailing whitespaces may count as part of the name 
value.

You may also build a different installer for a different platform. You may use
the build_installer.xml ANT script and the lib/installer.MF manifest for this 
purpose.

When you open the ModuleInfo.html the first time, it will automatically install
the PKCS#11 Wrapper into the Java Runtime Environment of your Java plug-in 
unless it has been installed before. If it has been installed before, it does 
not matter if the installer did it or if some other means have been employed to
place the files at their positions.
The installer copies the iaikPkcs11Wrapper.jar into the JRE/lib/ext directory 
and the DLL in your JRE/bin directory. For other platforms like Linux, you have 
to build an installer which uses the shared library suitable for this platform 
instead of the DLL.

The ModuleInfo.html starts the applet. You can should modify the "ModuleName"
parameter in this HTML file to the name of your PKCS#11 module.
You must open the Java console of your VM to see the output. 
This applet has only been tested with SUN's Java plug-in.
