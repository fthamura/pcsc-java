# Microsoft Developer Studio Generated NMAKE File, Based on PKCS11Wrapper.dsp
!IF "$(CFG)" == ""
CFG=PKCS11Wrapper - Win32 Sha1HMacDemo
!MESSAGE Keine Konfiguration angegeben. PKCS11Wrapper - Win32 Sha1HMacDemo wird als Standard verwendet.
!ENDIF 

!IF "$(CFG)" != "PKCS11Wrapper - Win32 Release" && "$(CFG)" != "PKCS11Wrapper - Win32 Debug" && "$(CFG)" != "PKCS11Wrapper - Win32 GetInfo Debug" && "$(CFG)" != "PKCS11Wrapper - Win32 SSLMechanisms Debug" && "$(CFG)" != "PKCS11Wrapper - Win32 RSACipherTest Debug" && "$(CFG)" != "PKCS11Wrapper - Win32 ParametersTest Debug" && "$(CFG)" != "PKCS11Wrapper - Win32 Digest Debug" && "$(CFG)" != "PKCS11Wrapper - Win32 Sha1HMacDemo" && "$(CFG)" != "PKCS11Wrapper - Win32 MS MV Release"
!MESSAGE UngÅltige Konfiguration "$(CFG)" angegeben.
!MESSAGE Sie kînnen beim AusfÅhren von NMAKE eine Konfiguration angeben
!MESSAGE durch Definieren des Makros CFG in der Befehlszeile. Zum Beispiel:
!MESSAGE 
!MESSAGE NMAKE /f "PKCS11Wrapper.mak" CFG="PKCS11Wrapper - Win32 Sha1HMacDemo"
!MESSAGE 
!MESSAGE FÅr die Konfiguration stehen zur Auswahl:
!MESSAGE 
!MESSAGE "PKCS11Wrapper - Win32 Release" (basierend auf  "Win32 (x86) Dynamic-Link Library")
!MESSAGE "PKCS11Wrapper - Win32 Debug" (basierend auf  "Win32 (x86) Dynamic-Link Library")
!MESSAGE "PKCS11Wrapper - Win32 GetInfo Debug" (basierend auf  "Win32 (x86) Dynamic-Link Library")
!MESSAGE "PKCS11Wrapper - Win32 SSLMechanisms Debug" (basierend auf  "Win32 (x86) Dynamic-Link Library")
!MESSAGE "PKCS11Wrapper - Win32 RSACipherTest Debug" (basierend auf  "Win32 (x86) Dynamic-Link Library")
!MESSAGE "PKCS11Wrapper - Win32 ParametersTest Debug" (basierend auf  "Win32 (x86) Dynamic-Link Library")
!MESSAGE "PKCS11Wrapper - Win32 Digest Debug" (basierend auf  "Win32 (x86) Dynamic-Link Library")
!MESSAGE "PKCS11Wrapper - Win32 Sha1HMacDemo" (basierend auf  "Win32 (x86) Dynamic-Link Library")
!MESSAGE "PKCS11Wrapper - Win32 MS MV Release" (basierend auf  "Win32 (x86) Dynamic-Link Library")
!MESSAGE 
!ERROR Eine ungÅltige Konfiguration wurde angegeben.
!ENDIF 

!IF "$(OS)" == "Windows_NT"
NULL=
!ELSE 
NULL=nul
!ENDIF 

!IF  "$(CFG)" == "PKCS11Wrapper - Win32 Release"

OUTDIR=.\release
INTDIR=.\release
# Begin Custom Macros
OutDir=.\release
# End Custom Macros

ALL : "$(OUTDIR)\PKCS11Wrapper.dll" "$(OUTDIR)\PKCS11Wrapper.bsc"


CLEAN :
	-@erase "$(INTDIR)\pkcs11wrapper.obj"
	-@erase "$(INTDIR)\PKCS11wrapper.res"
	-@erase "$(INTDIR)\pkcs11wrapper.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(OUTDIR)\PKCS11Wrapper.bsc"
	-@erase "$(OUTDIR)\PKCS11Wrapper.dll"
	-@erase "$(OUTDIR)\PKCS11Wrapper.exp"
	-@erase "$(OUTDIR)\PKCS11Wrapper.lib"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /MT /W3 /GX /O2 /I "./include" /I "../../include" /I "./src" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "PKCS11WRAPPER_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\PKCS11Wrapper.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL=midl.exe
MTL_PROJ=/nologo /D "NDEBUG" /mktyplib203 /win32 
RSC=rc.exe
RSC_PROJ=/l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /d "NDEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\PKCS11Wrapper.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\pkcs11wrapper.sbr"

"$(OUTDIR)\PKCS11Wrapper.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib jvm.lib /nologo /dll /incremental:no /pdb:"$(OUTDIR)\PKCS11Wrapper.pdb" /machine:I386 /out:"$(OUTDIR)\PKCS11Wrapper.dll" /implib:"$(OUTDIR)\PKCS11Wrapper.lib" /libpath:"./lib" 
LINK32_OBJS= \
	"$(INTDIR)\pkcs11wrapper.obj" \
	"$(INTDIR)\PKCS11wrapper.res"

"$(OUTDIR)\PKCS11Wrapper.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 Debug"

OUTDIR=.\debug
INTDIR=.\debug
# Begin Custom Macros
OutDir=.\debug
# End Custom Macros

ALL : "$(OUTDIR)\PKCS11Wrapper.dll" "$(OUTDIR)\PKCS11Wrapper.bsc"


CLEAN :
	-@erase "$(INTDIR)\pkcs11wrapper.obj"
	-@erase "$(INTDIR)\PKCS11wrapper.res"
	-@erase "$(INTDIR)\pkcs11wrapper.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\PKCS11Wrapper.bsc"
	-@erase "$(OUTDIR)\PKCS11Wrapper.dll"
	-@erase "$(OUTDIR)\PKCS11Wrapper.exp"
	-@erase "$(OUTDIR)\PKCS11Wrapper.ilk"
	-@erase "$(OUTDIR)\PKCS11Wrapper.lib"
	-@erase "$(OUTDIR)\PKCS11Wrapper.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /MTd /W3 /Gm /GX /ZI /Od /I "./src" /I "./include" /I "../../include" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "PKCS11WRAPPER_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\PKCS11Wrapper.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL=midl.exe
MTL_PROJ=/nologo /D "_DEBUG" /mktyplib203 /win32 
RSC=rc.exe
RSC_PROJ=/l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /d "_DEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\PKCS11Wrapper.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\pkcs11wrapper.sbr"

"$(OUTDIR)\PKCS11Wrapper.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=jvm.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\PKCS11Wrapper.pdb" /debug /machine:I386 /out:"$(OUTDIR)\PKCS11Wrapper.dll" /implib:"$(OUTDIR)\PKCS11Wrapper.lib" /pdbtype:sept /libpath:"./lib" 
LINK32_OBJS= \
	"$(INTDIR)\pkcs11wrapper.obj" \
	"$(INTDIR)\PKCS11wrapper.res"

"$(OUTDIR)\PKCS11Wrapper.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 GetInfo Debug"

OUTDIR=.\GetInfo_debug
INTDIR=.\GetInfo_debug
# Begin Custom Macros
OutDir=.\GetInfo_debug
# End Custom Macros

ALL : "$(OUTDIR)\PKCS11Wrapper.dll" "$(OUTDIR)\PKCS11Wrapper.bsc"


CLEAN :
	-@erase "$(INTDIR)\pkcs11wrapper.obj"
	-@erase "$(INTDIR)\PKCS11wrapper.res"
	-@erase "$(INTDIR)\pkcs11wrapper.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\PKCS11Wrapper.bsc"
	-@erase "$(OUTDIR)\PKCS11Wrapper.dll"
	-@erase "$(OUTDIR)\PKCS11Wrapper.exp"
	-@erase "$(OUTDIR)\PKCS11Wrapper.ilk"
	-@erase "$(OUTDIR)\PKCS11Wrapper.lib"
	-@erase "$(OUTDIR)\PKCS11Wrapper.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /MTd /W3 /Gm /GX /ZI /Od /I "./include" /I "../../include" /I "./src" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "PKCS11WRAPPER_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\PKCS11Wrapper.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL=midl.exe
MTL_PROJ=/nologo /D "_DEBUG" /mktyplib203 /win32 
RSC=rc.exe
RSC_PROJ=/l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /d "_DEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\PKCS11Wrapper.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\pkcs11wrapper.sbr"

"$(OUTDIR)\PKCS11Wrapper.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=jvm.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\PKCS11Wrapper.pdb" /debug /machine:I386 /out:"$(OUTDIR)\PKCS11Wrapper.dll" /implib:"$(OUTDIR)\PKCS11Wrapper.lib" /pdbtype:sept /libpath:"./lib" 
LINK32_OBJS= \
	"$(INTDIR)\pkcs11wrapper.obj" \
	"$(INTDIR)\PKCS11wrapper.res"

"$(OUTDIR)\PKCS11Wrapper.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 SSLMechanisms Debug"

OUTDIR=.\SSLMechanisms_debug
INTDIR=.\SSLMechanisms_debug
# Begin Custom Macros
OutDir=.\SSLMechanisms_debug
# End Custom Macros

ALL : "$(OUTDIR)\PKCS11Wrapper.dll" "$(OUTDIR)\PKCS11Wrapper.bsc"


CLEAN :
	-@erase "$(INTDIR)\pkcs11wrapper.obj"
	-@erase "$(INTDIR)\PKCS11wrapper.res"
	-@erase "$(INTDIR)\pkcs11wrapper.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\PKCS11Wrapper.bsc"
	-@erase "$(OUTDIR)\PKCS11Wrapper.dll"
	-@erase "$(OUTDIR)\PKCS11Wrapper.exp"
	-@erase "$(OUTDIR)\PKCS11Wrapper.ilk"
	-@erase "$(OUTDIR)\PKCS11Wrapper.lib"
	-@erase "$(OUTDIR)\PKCS11Wrapper.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /MTd /W3 /Gm /GX /ZI /Od /I "./include" /I "../../include" /I "./src" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "PKCS11WRAPPER_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\PKCS11Wrapper.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL=midl.exe
MTL_PROJ=/nologo /D "_DEBUG" /mktyplib203 /win32 
RSC=rc.exe
RSC_PROJ=/l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /d "_DEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\PKCS11Wrapper.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\pkcs11wrapper.sbr"

"$(OUTDIR)\PKCS11Wrapper.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=jvm.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\PKCS11Wrapper.pdb" /debug /machine:I386 /out:"$(OUTDIR)\PKCS11Wrapper.dll" /implib:"$(OUTDIR)\PKCS11Wrapper.lib" /pdbtype:sept /libpath:"./lib" 
LINK32_OBJS= \
	"$(INTDIR)\pkcs11wrapper.obj" \
	"$(INTDIR)\PKCS11wrapper.res"

"$(OUTDIR)\PKCS11Wrapper.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 RSACipherTest Debug"

OUTDIR=.\RSACipherTest_debug
INTDIR=.\RSACipherTest_debug
# Begin Custom Macros
OutDir=.\RSACipherTest_debug
# End Custom Macros

ALL : "$(OUTDIR)\PKCS11Wrapper.dll" "$(OUTDIR)\PKCS11Wrapper.bsc"


CLEAN :
	-@erase "$(INTDIR)\pkcs11wrapper.obj"
	-@erase "$(INTDIR)\PKCS11wrapper.res"
	-@erase "$(INTDIR)\pkcs11wrapper.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\PKCS11Wrapper.bsc"
	-@erase "$(OUTDIR)\PKCS11Wrapper.dll"
	-@erase "$(OUTDIR)\PKCS11Wrapper.exp"
	-@erase "$(OUTDIR)\PKCS11Wrapper.ilk"
	-@erase "$(OUTDIR)\PKCS11Wrapper.lib"
	-@erase "$(OUTDIR)\PKCS11Wrapper.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /MTd /W3 /Gm /GX /ZI /Od /I "./include" /I "../../include" /I "./src" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "PKCS11WRAPPER_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\PKCS11Wrapper.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL=midl.exe
MTL_PROJ=/nologo /D "_DEBUG" /mktyplib203 /win32 
RSC=rc.exe
RSC_PROJ=/l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /d "_DEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\PKCS11Wrapper.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\pkcs11wrapper.sbr"

"$(OUTDIR)\PKCS11Wrapper.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=jvm.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\PKCS11Wrapper.pdb" /debug /machine:I386 /out:"$(OUTDIR)\PKCS11Wrapper.dll" /implib:"$(OUTDIR)\PKCS11Wrapper.lib" /pdbtype:sept /libpath:"./lib" 
LINK32_OBJS= \
	"$(INTDIR)\pkcs11wrapper.obj" \
	"$(INTDIR)\PKCS11wrapper.res"

"$(OUTDIR)\PKCS11Wrapper.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 ParametersTest Debug"

OUTDIR=.\ParametersTest_debug
INTDIR=.\ParametersTest_debug
# Begin Custom Macros
OutDir=.\ParametersTest_debug
# End Custom Macros

ALL : "$(OUTDIR)\PKCS11Wrapper.dll" "$(OUTDIR)\PKCS11Wrapper.bsc"


CLEAN :
	-@erase "$(INTDIR)\pkcs11wrapper.obj"
	-@erase "$(INTDIR)\PKCS11wrapper.res"
	-@erase "$(INTDIR)\pkcs11wrapper.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\PKCS11Wrapper.bsc"
	-@erase "$(OUTDIR)\PKCS11Wrapper.dll"
	-@erase "$(OUTDIR)\PKCS11Wrapper.exp"
	-@erase "$(OUTDIR)\PKCS11Wrapper.ilk"
	-@erase "$(OUTDIR)\PKCS11Wrapper.lib"
	-@erase "$(OUTDIR)\PKCS11Wrapper.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /MTd /W3 /Gm /GX /ZI /Od /I "./include" /I "../../include" /I "./src" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "PKCS11WRAPPER_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\PKCS11Wrapper.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL=midl.exe
MTL_PROJ=/nologo /D "_DEBUG" /mktyplib203 /win32 
RSC=rc.exe
RSC_PROJ=/l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /d "_DEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\PKCS11Wrapper.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\pkcs11wrapper.sbr"

"$(OUTDIR)\PKCS11Wrapper.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=jvm.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\PKCS11Wrapper.pdb" /debug /machine:I386 /out:"$(OUTDIR)\PKCS11Wrapper.dll" /implib:"$(OUTDIR)\PKCS11Wrapper.lib" /pdbtype:sept /libpath:"./lib" 
LINK32_OBJS= \
	"$(INTDIR)\pkcs11wrapper.obj" \
	"$(INTDIR)\PKCS11wrapper.res"

"$(OUTDIR)\PKCS11Wrapper.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 Digest Debug"

OUTDIR=.\Digest_debug
INTDIR=.\Digest_debug
# Begin Custom Macros
OutDir=.\Digest_debug
# End Custom Macros

ALL : "$(OUTDIR)\PKCS11Wrapper.dll" "$(OUTDIR)\PKCS11Wrapper.bsc"


CLEAN :
	-@erase "$(INTDIR)\pkcs11wrapper.obj"
	-@erase "$(INTDIR)\PKCS11wrapper.res"
	-@erase "$(INTDIR)\pkcs11wrapper.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\PKCS11Wrapper.bsc"
	-@erase "$(OUTDIR)\PKCS11Wrapper.dll"
	-@erase "$(OUTDIR)\PKCS11Wrapper.exp"
	-@erase "$(OUTDIR)\PKCS11Wrapper.ilk"
	-@erase "$(OUTDIR)\PKCS11Wrapper.lib"
	-@erase "$(OUTDIR)\PKCS11Wrapper.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /MTd /W3 /Gm /GX /ZI /Od /I "./include" /I "../../include" /I "./src" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "PKCS11WRAPPER_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\PKCS11Wrapper.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL=midl.exe
MTL_PROJ=/nologo /D "_DEBUG" /mktyplib203 /win32 
RSC=rc.exe
RSC_PROJ=/l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /d "_DEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\PKCS11Wrapper.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\pkcs11wrapper.sbr"

"$(OUTDIR)\PKCS11Wrapper.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=jvm.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\PKCS11Wrapper.pdb" /debug /machine:I386 /out:"$(OUTDIR)\PKCS11Wrapper.dll" /implib:"$(OUTDIR)\PKCS11Wrapper.lib" /pdbtype:sept /libpath:"./lib" 
LINK32_OBJS= \
	"$(INTDIR)\pkcs11wrapper.obj" \
	"$(INTDIR)\PKCS11wrapper.res"

"$(OUTDIR)\PKCS11Wrapper.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 Sha1HMacDemo"

OUTDIR=.\Sha1HMacDemo_debug
INTDIR=.\Sha1HMacDemo_debug
# Begin Custom Macros
OutDir=.\Sha1HMacDemo_debug
# End Custom Macros

ALL : "$(OUTDIR)\PKCS11Wrapper.dll" "$(OUTDIR)\PKCS11Wrapper.bsc"


CLEAN :
	-@erase "$(INTDIR)\pkcs11wrapper.obj"
	-@erase "$(INTDIR)\PKCS11wrapper.res"
	-@erase "$(INTDIR)\pkcs11wrapper.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(INTDIR)\vc60.pdb"
	-@erase "$(OUTDIR)\PKCS11Wrapper.bsc"
	-@erase "$(OUTDIR)\PKCS11Wrapper.dll"
	-@erase "$(OUTDIR)\PKCS11Wrapper.exp"
	-@erase "$(OUTDIR)\PKCS11Wrapper.ilk"
	-@erase "$(OUTDIR)\PKCS11Wrapper.lib"
	-@erase "$(OUTDIR)\PKCS11Wrapper.pdb"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /MTd /W3 /Gm /GX /ZI /Od /I "./include" /I "../../include" /I "./src" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "PKCS11WRAPPER_EXPORTS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\PKCS11Wrapper.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /GZ /c 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL=midl.exe
MTL_PROJ=/nologo /D "_DEBUG" /mktyplib203 /win32 
RSC=rc.exe
RSC_PROJ=/l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /d "_DEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\PKCS11Wrapper.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\pkcs11wrapper.sbr"

"$(OUTDIR)\PKCS11Wrapper.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=jvm.lib kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:yes /pdb:"$(OUTDIR)\PKCS11Wrapper.pdb" /debug /machine:I386 /out:"$(OUTDIR)\PKCS11Wrapper.dll" /implib:"$(OUTDIR)\PKCS11Wrapper.lib" /pdbtype:sept /libpath:"./lib" 
LINK32_OBJS= \
	"$(INTDIR)\pkcs11wrapper.obj" \
	"$(INTDIR)\PKCS11wrapper.res"

"$(OUTDIR)\PKCS11Wrapper.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 MS MV Release"

OUTDIR=.\MS_MV_release
INTDIR=.\MS_MV_release
# Begin Custom Macros
OutDir=.\MS_MV_release
# End Custom Macros

ALL : "$(OUTDIR)\PKCS11Wrapper.dll" "$(OUTDIR)\PKCS11Wrapper.bsc"


CLEAN :
	-@erase "$(INTDIR)\pkcs11wrapper.obj"
	-@erase "$(INTDIR)\PKCS11wrapper.res"
	-@erase "$(INTDIR)\pkcs11wrapper.sbr"
	-@erase "$(INTDIR)\vc60.idb"
	-@erase "$(OUTDIR)\PKCS11Wrapper.bsc"
	-@erase "$(OUTDIR)\PKCS11Wrapper.dll"
	-@erase "$(OUTDIR)\PKCS11Wrapper.exp"
	-@erase "$(OUTDIR)\PKCS11Wrapper.lib"

"$(OUTDIR)" :
    if not exist "$(OUTDIR)/$(NULL)" mkdir "$(OUTDIR)"

CPP=cl.exe
CPP_PROJ=/nologo /MT /W3 /GX /O2 /I "./include" /I "../../include" /I "./src" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_MBCS" /D "_USRDLL" /D "PKCS11WRAPPER_EXPORTS" /D "NO_CALLBACKS" /FR"$(INTDIR)\\" /Fp"$(INTDIR)\PKCS11Wrapper.pch" /YX /Fo"$(INTDIR)\\" /Fd"$(INTDIR)\\" /FD /c 

.c{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.obj::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.c{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cpp{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

.cxx{$(INTDIR)}.sbr::
   $(CPP) @<<
   $(CPP_PROJ) $< 
<<

MTL=midl.exe
MTL_PROJ=/nologo /D "NDEBUG" /mktyplib203 /win32 
RSC=rc.exe
RSC_PROJ=/l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /d "NDEBUG" 
BSC32=bscmake.exe
BSC32_FLAGS=/nologo /o"$(OUTDIR)\PKCS11Wrapper.bsc" 
BSC32_SBRS= \
	"$(INTDIR)\pkcs11wrapper.sbr"

"$(OUTDIR)\PKCS11Wrapper.bsc" : "$(OUTDIR)" $(BSC32_SBRS)
    $(BSC32) @<<
  $(BSC32_FLAGS) $(BSC32_SBRS)
<<

LINK32=link.exe
LINK32_FLAGS=kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib odbccp32.lib /nologo /dll /incremental:no /pdb:"$(OUTDIR)\PKCS11Wrapper.pdb" /machine:I386 /out:"$(OUTDIR)\PKCS11Wrapper.dll" /implib:"$(OUTDIR)\PKCS11Wrapper.lib" /libpath:"./lib" 
LINK32_OBJS= \
	"$(INTDIR)\pkcs11wrapper.obj" \
	"$(INTDIR)\PKCS11wrapper.res"

"$(OUTDIR)\PKCS11Wrapper.dll" : "$(OUTDIR)" $(DEF_FILE) $(LINK32_OBJS)
    $(LINK32) @<<
  $(LINK32_FLAGS) $(LINK32_OBJS)
<<

!ENDIF 


!IF "$(NO_EXTERNAL_DEPS)" != "1"
!IF EXISTS("PKCS11Wrapper.dep")
!INCLUDE "PKCS11Wrapper.dep"
!ELSE 
!MESSAGE Warning: cannot find "PKCS11Wrapper.dep"
!ENDIF 
!ENDIF 


!IF "$(CFG)" == "PKCS11Wrapper - Win32 Release" || "$(CFG)" == "PKCS11Wrapper - Win32 Debug" || "$(CFG)" == "PKCS11Wrapper - Win32 GetInfo Debug" || "$(CFG)" == "PKCS11Wrapper - Win32 SSLMechanisms Debug" || "$(CFG)" == "PKCS11Wrapper - Win32 RSACipherTest Debug" || "$(CFG)" == "PKCS11Wrapper - Win32 ParametersTest Debug" || "$(CFG)" == "PKCS11Wrapper - Win32 Digest Debug" || "$(CFG)" == "PKCS11Wrapper - Win32 Sha1HMacDemo" || "$(CFG)" == "PKCS11Wrapper - Win32 MS MV Release"
SOURCE=..\..\src\pkcs11wrapper.c

"$(INTDIR)\pkcs11wrapper.obj"	"$(INTDIR)\pkcs11wrapper.sbr" : $(SOURCE) "$(INTDIR)"
	$(CPP) $(CPP_PROJ) $(SOURCE)


SOURCE=.\src\PKCS11wrapper.rc

!IF  "$(CFG)" == "PKCS11Wrapper - Win32 Release"


"$(INTDIR)\PKCS11wrapper.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) /l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /i "src" /d "NDEBUG" $(SOURCE)


!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 Debug"


"$(INTDIR)\PKCS11wrapper.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) /l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /i "src" /d "_DEBUG" $(SOURCE)


!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 GetInfo Debug"


"$(INTDIR)\PKCS11wrapper.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) /l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /i "src" /d "_DEBUG" $(SOURCE)


!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 SSLMechanisms Debug"


"$(INTDIR)\PKCS11wrapper.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) /l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /i "src" /d "_DEBUG" $(SOURCE)


!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 RSACipherTest Debug"


"$(INTDIR)\PKCS11wrapper.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) /l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /i "src" /d "_DEBUG" $(SOURCE)


!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 ParametersTest Debug"


"$(INTDIR)\PKCS11wrapper.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) /l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /i "src" /d "_DEBUG" $(SOURCE)


!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 Digest Debug"


"$(INTDIR)\PKCS11wrapper.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) /l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /i "src" /d "_DEBUG" $(SOURCE)


!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 Sha1HMacDemo"


"$(INTDIR)\PKCS11wrapper.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) /l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /i "src" /d "_DEBUG" $(SOURCE)


!ELSEIF  "$(CFG)" == "PKCS11Wrapper - Win32 MS MV Release"


"$(INTDIR)\PKCS11wrapper.res" : $(SOURCE) "$(INTDIR)"
	$(RSC) /l 0xc07 /fo"$(INTDIR)\PKCS11wrapper.res" /i "src" /d "NDEBUG" $(SOURCE)


!ENDIF 

SOURCE=.\src\platform.c

!ENDIF 

