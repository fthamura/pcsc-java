The include and src subdirectories contain the portable code; i.e. the code that
is the same for all platforms. The platforms directory contains a subdirectory
for each supported platform. Each of this platform directory contains an own
include and src directory, which contain the platform dependent files platform.h
and platform.c. These two files contain the platform dependent part of the 
header and of the implementation. If you want to port this project to another
platform, I suggest to do it like this. Chose one of the existing platforms
that is most similar to the new platform. Make a copy of the complete platform
directory and give it an appropriate name; e.g. make a copy of the 
platforms/linux directory and call it platforms/aix for instance. Then 
adapt the platform.h and platform.c files of the copy to fit to your new 
platform.

If you use the included GNU makefiles or MS VC++ projects, you should be able to
compile the shared library or DLL directly. But if you want to use other means,
compiler or make utility, you must define some variables for the preprocessor.
These are:

DEBUG, if you want to compile with debug information and some debug output. 

NO_CALLBACKS, if you want to compile for a VM which does not support the 
	            invocation API or you just do not need callbacks (they are 
	            rarely used by PKCS#11 modules).
	              
If you compile with callbacks and for JDK 1.1.x, you need to replace the jvm.lib
file with the appropriate file of your VM; e.g. javai.lib for SUN JDK 1.1.8.
