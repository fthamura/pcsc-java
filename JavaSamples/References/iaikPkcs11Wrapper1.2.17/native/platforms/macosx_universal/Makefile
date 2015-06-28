CC = gcc
SOURCE_DIR = ../../src/
INCLUDE_DIR = ../../include/
PLATFORM_INCLUDE = include/
PLATFORM_SRC_INCLUDE = src/
PLATFORM_LIBRARIES = lib/
VM_INCLUDE = /System/Library/Frameworks/JavaVM.framework/Headers
DEBUG_OUTPUT_DIR = debug/
RELEASE_OUTPUT_DIR = release/
DEBUG11_OUTPUT_DIR = debug_jdk11/
RELEASE11_OUTPUT_DIR = release_jdk11/
TARGETS = debug release debug11 release11
ARCH = -arch ppc64 -arch ppc -arch i386 -arch x86_64

all : $(TARGETS)

VPATH = $(SOURCE_DIR) $(INCLUDE_DIR)

# we do not need '-fpack-struct' as option to get byte aligned structure members as required by PKCS#11,
# all PKCS#11 modules seem to be compiled without this option

.PHONY	: debug
debug : pkcs11wrapper.c pkcs11wrapper.h
	$(CC) -fPIC -I $(VM_INCLUDE) -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX $(ARCH) -DDEBUG -g -dynamiclib -o $(DEBUG_OUTPUT_DIR)libpkcs11wrapper.jnilib -framework JavaVM $(SOURCE_DIR)pkcs11wrapper.c

.PHONY	: release
release : pkcs11wrapper.c pkcs11wrapper.h
	$(CC) -fPIC -I $(VM_INCLUDE) -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX $(ARCH) -dynamiclib -o $(RELEASE_OUTPUT_DIR)libpkcs11wrapper.jnilib -framework JavaVM $(SOURCE_DIR)pkcs11wrapper.c

.PHONY	: debug11
debug11 : pkcs11wrapper.c pkcs11wrapper.h
	$(CC) -fPIC -I $(VM_INCLUDE) -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -arch ppc -DDEBUG -DNO_CALLBACKS -g -dynamiclib -o $(DEBUG11_OUTPUT_DIR)libpkcs11wrapper-ppc.jnilib -framework JavaVM -$(SOURCE_DIR)pkcs11wrapper.c
	$(CC) -fPIC -I $(VM_INCLUDE) -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -arch ppc64 -DDEBUG -DNO_CALLBACKS -g -dynamiclib -o $(DEBUG11_OUTPUT_DIR)libpkcs11wrapper-ppc64.jnilib -framework JavaVM -$(SOURCE_DIR)pkcs11wrapper.c
	$(CC) -fPIC -I $(VM_INCLUDE) -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -arch i386 -DDEBUG -DNO_CALLBACKS -g -dynamiclib -o $(DEBUG11_OUTPUT_DIR)libpkcs11wrapper-i386.jnilib -framework JavaVM -$(SOURCE_DIR)pkcs11wrapper.c
	$(CC) -fPIC -I $(VM_INCLUDE) -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -arch x86_64 -DDEBUG -DNO_CALLBACKS -g -dynamiclib -o $(DEBUG11_OUTPUT_DIR)libpkcs11wrapper-x86-64.jnilib -framework JavaVM -$(SOURCE_DIR)pkcs11wrapper.c

.PHONY	: release11
release11 : pkcs11wrapper.c pkcs11wrapper.h
	$(CC) -fPIC -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX $(ARCH) -DNO_CALLBACKS -dynamiclib -o $(RELEASE11_OUTPUT_DIR)libpkcs11wrapper.jnilib -framework JavaVM $(SOURCE_DIR)pkcs11wrapper.c

clean :
	rm -f $(DEBUG_OUTPUT_DIR)* $(RELEASE_OUTPUT_DIR)*
