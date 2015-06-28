// Copyright (C) 2002 IAIK
// http://jce.iaik.at
//
// Copyright (C) 2003 Stiftung Secure Information and 
//                    Communication Technologies SIC
// http://www.sic.st
//
// All rights reserved.
//
// This source is provided for inspection purposes and recompilation only,
// unless specified differently in a contract with IAIK. This source has to
// be kept in strict confidence and must not be disclosed to any third party
// under any circumstances. Redistribution in source and binary forms, with
// or without modification, are <not> permitted in any case!
//
// THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
// OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
// HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
// LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
// OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE.
//
// $Header: /SmartCardIntegration/PKCS11wrapper/JavaToPKCS11/applet-demo/src/IAIK/pkcs/pkcs11/wrapper/installer/WindowsInstaller.java 1     23.11.04 18:19 Kscheibelhofer $
// $Revision: 1 $
//

package iaik.pkcs.pkcs11.wrapper.installer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;


/**
 * @author Karl Scheibelhofer
 * @invariants 
 */
public class WindowsInstaller {

  private static final boolean JAR = false; 
  private static final boolean NATIVE = true; 

  public static void main(String[] args) throws IOException {
    installExtensionFile("iaikPkcs11Wrapper.jar", JAR);
    installExtensionFile("pkcs11wrapper.dll", NATIVE);
  }

  static private void installExtensionFile(String fileName, boolean jarOrNative) throws IOException {
    // determine destination directory for extension file
    String destinationDirectory;
    if (jarOrNative == JAR) {
      String extensionDirs = System.getProperty("java.ext.dirs");
      String pathSeparator = System.getProperty("path.separator");
      StringTokenizer dirsTokenizer = new StringTokenizer(extensionDirs, pathSeparator);
      if (dirsTokenizer.hasMoreTokens()) {
        destinationDirectory = dirsTokenizer.nextToken();
      } else {
        throw new RuntimeException("No extensions directory found in \"java.ext.dirs\" property.");
      }
    } else {
      String binaryDirectory = System.getProperty("java.home");
      String fileSeparator = System.getProperty("file.separator");
      destinationDirectory = binaryDirectory + fileSeparator + "bin";
    }
    
    // copy extension file from our JAR file (or somewhere else in the CLASSPATH) into the destination directory 
    InputStream fileInputStream = WindowsInstaller.class.getClassLoader().getResourceAsStream(fileName);
    try {
      BufferedInputStream bufferdFileInputStream = new BufferedInputStream(fileInputStream);
      File destinationFile = new File(destinationDirectory, fileName);
      BufferedOutputStream bufferedFileOutputStream = new BufferedOutputStream(new FileOutputStream(destinationFile));
      byte[] buffer = new byte[4096];
      int bytesRead;
      while ((bytesRead = bufferdFileInputStream.read(buffer, 0, buffer.length)) != -1) {
        bufferedFileOutputStream.write(buffer, 0, bytesRead);
      }
      bufferdFileInputStream.close();
      bufferedFileOutputStream.flush();
      bufferedFileOutputStream.close();
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      try { if (fileInputStream != null) { fileInputStream.close(); } } catch (Throwable ex) { }
    }
  }
  
}
