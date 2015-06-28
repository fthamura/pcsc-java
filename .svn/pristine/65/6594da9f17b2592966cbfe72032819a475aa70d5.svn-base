CC = gcc
SOURCE_DIR = ../../src/
INCLUDE_DIR = ../../include/
PLATFORM_INCLUDE = include/
PLATFORM_SRC_INCLUDE = src/
DEBUG_OUTPUT_DIR = debug/
RELEASE_OUTPUT_DIR = release/
DEBUG11_OUTPUT_DIR = debug_jdk11/
RELEASE11_OUTPUT_DIR = release_jdk11/
TARGETS = debug release debug11 release11

all : $(TARGETS)

VPATH = $(SOURCE_DIR) $(INCLUDE_DIR)

# we do not need '-fpack-struct' as option to get byte aligned structure members as required by PKCS#11,
# all PKCS#11 modules seem to be compiled without this option

.PHONY	: debug
debug : pkcs11wrapper.c pkcs11wrapper.h
	$(CC) -fPIC -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -DDEBUG -m32 -g -o $(DEBUG_OUTPUT_DIR)libpkcs11wrapper.so $(SOURCE_DIR)pkcs11wrapper.c -shared

.PHONY	: release
release : pkcs11wrapper.c pkcs11wrapper.h
	$(CC) -fPIC -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -m32 -o $(RELEASE_OUTPUT_DIR)libpkcs11wrapper.so $(SOURCE_DIR)pkcs11wrapper.c -shared

.PHONY	: debug11
debug11 : pkcs11wrapper.c pkcs11wrapper.h
	$(CC) -fPIC -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -DDEBUG -DNO_CALLBACKS -m32 -g -o $(DEBUG11_OUTPUT_DIR)libpkcs11wrapper.so $(SOURCE_DIR)pkcs11wrapper.c -shared

.PHONY	: release11
release11 : pkcs11wrapper.c pkcs11wrapper.h
	$(CC) -fPIC -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -DNO_CALLBACKS -m32 -o $(RELEASE11_OUTPUT_DIR)libpkcs11wrapper.so $(SOURCE_DIR)pkcs11wrapper.c -shared

clean :
	rm -f $(DEBUG_OUTPUT_DIR)* $(RELEASE_OUTPUT_DIR)*
