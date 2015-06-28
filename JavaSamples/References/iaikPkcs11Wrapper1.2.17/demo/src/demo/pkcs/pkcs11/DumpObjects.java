/* Copyright  (c) 2002 Graz University of Technology. All rights reserved.
 *
 * Redistribution and use in  source and binary forms, with or without 
 * modification, are permitted  provided that the following conditions are met:
 *
 * 1. Redistributions of  source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in  binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  
 * 3. The end-user documentation included with the redistribution, if any, must
 *    include the following acknowledgment:
 * 
 *    "This product includes software developed by IAIK of Graz University of
 *     Technology."
 * 
 *    Alternately, this acknowledgment may appear in the software itself, if 
 *    and wherever such third-party acknowledgments normally appear.
 *  
 * 4. The names "Graz University of Technology" and "IAIK of Graz University of
 *    Technology" must not be used to endorse or promote products derived from 
 *    this software without prior written permission.
 *  
 * 5. Products derived from this software may not be called 
 *    "IAIK PKCS Wrapper", nor may "IAIK" appear in their name, without prior 
 *    written permission of Graz University of Technology.
 *  
 *  THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE LICENSOR BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 *  OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 *  OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 *  OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY  OF SUCH DAMAGE.
 */

package demo.pkcs.pkcs11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Hashtable;

import iaik.pkcs.pkcs11.DefaultInitializeArgs;
import iaik.pkcs.pkcs11.Info;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.SessionInfo;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenInfo;
import iaik.pkcs.pkcs11.objects.Attribute;
import iaik.pkcs.pkcs11.objects.ByteArrayAttribute;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.objects.X509AttributeCertificate;
import iaik.pkcs.pkcs11.objects.X509PublicKeyCertificate;



/**
 * This demo program lists information about a library, the available slots,
 * the available tokens and the objects on them. It takes the name of the module
 * and prompts the user PIN. If the user PIN is not available,
 * the program will list only public objects but no private objects; i.e. as
 * defined in PKCS#11 for public read-only sessions.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class DumpObjects {

  static PrintWriter output_;

  static BufferedReader input_;

  static {
    try {
      //output_ = new PrintWriter(new FileWriter("GetInfo_output.txt"), true);
      output_ = new PrintWriter(System.out, true);
      input_ = new BufferedReader(new InputStreamReader(System.in));
    } catch (Throwable thr) {
      thr.printStackTrace();
      output_ = new PrintWriter(System.out, true);
      input_ = new BufferedReader(new InputStreamReader(System.in));
    }
  }

  public static void main(String[] args) {
    if (args.length >= 2) {
      String outputDirectroyName = args[1];
      File outputDirectory = new File(outputDirectroyName);
      if (outputDirectory.exists() && !outputDirectory.isDirectory()) {
        output_.println(outputDirectroyName + " is not a directory!");
        System.exit(1);
      }

      try {
        // create directory if not present
        if (!outputDirectory.exists()) {
          outputDirectory.mkdirs();
        }

        String moduleName = args[0];
        output_.println("################################################################################");
        output_.println("load and initialize module: " + moduleName);
        output_.flush();
        Module pkcs11Module = Module.getInstance(moduleName);
        
        if (args.length == 2) {
          pkcs11Module.initialize(null);
        } else {
          DefaultInitializeArgs arguments = new DefaultInitializeArgs();
          byte[] stringBytes = args[2].getBytes();
          byte[] reservedBytes = new byte[stringBytes.length + 5];
          System.arraycopy(stringBytes, 0, reservedBytes, 0, stringBytes.length);
          arguments.setReserved(reservedBytes);
          pkcs11Module.initialize(arguments);
        }

        Info info = pkcs11Module.getInfo();
        output_.println(info);
        output_.println("################################################################################");

        Token token = Util.selectToken(pkcs11Module, output_, input_);
        if (token == null) {
          output_.println("We have no token to proceed. Finished.");
          output_.flush();
          System.exit(0);
        }

        output_.println("################################################################################");
        output_.println("dumping objects for token: ");
        TokenInfo tokenInfo = token.getTokenInfo();
        output_.println(tokenInfo);
        Session session = token
            .openSession(Token.SessionType.SERIAL_SESSION, Token.SessionReadWriteBehavior.RO_SESSION, null, null);

        if (tokenInfo.isLoginRequired()) {
          if (tokenInfo.isProtectedAuthenticationPath()) {
            session.login(Session.UserType.USER, null); // the token prompts the PIN by other means; e.g. PIN-pad
          } else {
            output_.print("Enter user-PIN or press [return] to list just public objects: ");
            output_.flush();
            String userPINString = input_.readLine();
            output_.println();
            output_.print("listing all" + ((userPINString.length() > 0) ? "" : " public") + " objects on token");
            if (userPINString.length() > 0) {
              // login user
              session.login(Session.UserType.USER, userPINString.toCharArray());
            }
          }
        }
        SessionInfo sessionInfo = session.getSessionInfo();
        output_.println(" using session:");
        output_.println(sessionInfo);

        String pathSepatator = System.getProperty("file.separator");

        session.findObjectsInit(null);
        Object[] objects = session.findObjects(1);

        while ((objects.length > 0) && (objects[0] != null)) {
          Object object = objects[0];
          output_.println("--------------------------------------------------------------------------------");
          long handle = object.getObjectHandle();
          output_.println("Dumping object with handle " + handle);
          String textDumpFilename = outputDirectroyName + pathSepatator + handle + ".txt";
          output_.println("Dumping text output to file " + textDumpFilename);
          FileOutputStream textDumpStream = new FileOutputStream(textDumpFilename);
          textDumpStream.write(object.toString().getBytes("UTF-8"));
          textDumpStream.flush();
          textDumpStream.close();

          Hashtable attributes = object.getAttributeTable();
          if (attributes.containsKey(Attribute.VALUE)) {
            ByteArrayAttribute valueAttribute = (ByteArrayAttribute) attributes.get(Attribute.VALUE);
            byte[] value = valueAttribute.getByteArrayValue();
            if (value != null) {
              String valueDumpFilename = outputDirectroyName + pathSepatator + handle + ".value.bin";
              output_.println("Dumping value attribut to file " + valueDumpFilename);
              FileOutputStream valueDumpStream = new FileOutputStream(valueDumpFilename);
              valueDumpStream.write(value);
              valueDumpStream.flush();
              valueDumpStream.close();
              if ((object instanceof X509PublicKeyCertificate) || (object instanceof X509AttributeCertificate)) {
                String certificateDumpFilename = outputDirectroyName + pathSepatator + handle + ".der.cer";
                output_.println("Dumping DER encoding of certificate to file " + certificateDumpFilename);
                FileOutputStream certificateDumpStream = new FileOutputStream(certificateDumpFilename);
                certificateDumpStream.write(value);
                certificateDumpStream.flush();
                certificateDumpStream.close();
              }
            }
          }

          output_.println("--------------------------------------------------------------------------------");
          objects = session.findObjects(1);
        }
        session.findObjectsFinal();

        session.closeSession();
        output_.println("################################################################################");

        pkcs11Module.finalize(null);
      } catch (Throwable ex) {
        ex.printStackTrace();
      }
    } else {
      printUsage();
    }
    System.gc(); // to finalize and disconnect the pkcs11Module
  }

  protected static void printUsage() {
    output_.println("DumpObjects <PKCS#11 module name> <output directory> [<initialization parameters>]");
    output_.println("e.g.: DumpObjects slbck.dll dumpdir");
  }

}