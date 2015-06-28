CC = cc
SOURCE_DIR = ../../src/
INCLUDE_DIR = ../../include/
PLATFORM_INCLUDE = include/
PLATFORM_SRC_INCLUDE = src/
PLATFORM_LIBRARIES = lib/
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
	$(CC) -G -KPIC -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -DDEBUG -g -xarch=v9 -o $(DEBUG_OUTPUT_DIR)libpkcs11wrapper.so $(SOURCE_DIR)pkcs11wrapper.c -L $(PLATFORM_LIBRARIES)

.PHONY	: release
release : pkcs11wrapper.c pkcs11wrapper.h
	$(CC) -G -KPIC -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -xarch=v9 -o $(RELEASE_OUTPUT_DIR)libpkcs11wrapper.so $(SOURCE_DIR)pkcs11wrapper.c -L $(PLATFORM_LIBRARIES)

.PHONY	: debug11
debug11 : pkcs11wrapper.c pkcs11wrapper.h
	$(CC) -G -KPIC -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -DDEBUG -DNO_CALLBACKS -g -xarch=v9 -o $(DEBUG11_OUTPUT_DIR)libpkcs11wrapper.so $(SOURCE_DIR)pkcs11wrapper.c -L $(PLATFORM_LIBRARIES)

.PHONY	: release11
release11 : pkcs11wrapper.c pkcs11wrapper.h
	$(CC) -G -KPIC -I $(PLATFORM_SRC_INCLUDE) -I $(PLATFORM_INCLUDE) -I $(INCLUDE_DIR) -DUNIX -DNO_CALLBACKS -xarch=v9 -o $(RELEASE11_OUTPUT_DIR)libpkcs11wrapper.so $(SOURCE_DIR)pkcs11wrapper.c -L $(PLATFORM_LIBRARIES)

clean :
	rm -f $(DEBUG_OUTPUT_DIR)* $(RELEASE_OUTPUT_DIR)*
