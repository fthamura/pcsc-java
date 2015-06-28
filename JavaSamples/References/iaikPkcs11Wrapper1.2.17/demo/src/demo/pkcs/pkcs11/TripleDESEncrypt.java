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
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import iaik.pkcs.pkcs11.Info;
import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.MechanismInfo;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenInfo;
import iaik.pkcs.pkcs11.objects.DES3SecretKey;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.parameters.InitializationVectorParameters;



/**
 * This demo program uses a PKCS#11 module to encrypt a given file using
 * Triple DES.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class TripleDESEncrypt {

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
    if (args.length != 3) {
      printUsage();
      System.exit(1);
    }

    try {
        // Security.addProvider(new IAIK());

        output_.println("################################################################################");
        output_.println("load and initialize module: " + args[0]);
        Module pkcs11Module = Module.getInstance(args[0]);
        pkcs11Module.initialize(null);

        Info info = pkcs11Module.getInfo();
        output_.println(info);
        output_.println("################################################################################");


        output_.println("################################################################################");
        output_.println("getting list of all tokens");
        Slot[] slotsWithToken = pkcs11Module.getSlotList(Module.SlotRequirement.TOKEN_PRESENT);
        Token[] tokens = new Token[slotsWithToken.length];
        Map tokenIDtoToken = new HashMap(tokens.length);

        for (int i = 0; i < slotsWithToken.length; i++) {
          output_.println("________________________________________________________________________________");
          tokens[i] = slotsWithToken[i].getToken();
          TokenInfo tokenInfo = tokens[i].getTokenInfo();
          long tokenID = tokens[i].getTokenID();
          tokenIDtoToken.put(new Long(tokenID), tokens[i]);
          output_.println("Token ID: " + tokenID);
          output_.println(tokenInfo);
          output_.println("________________________________________________________________________________");
        }
        output_.println("################################################################################");

        output_.println("################################################################################");
        Token token = null;
        Long selectedTokenID = null;
        if (tokens.length == 0) {
          output_.println("There is no slot with a present token.");
          output_.flush();
          System.exit(0);
        } else if (tokens.length == 1) {
          output_.println("Taking token with ID: " + tokens[0].getTokenID());
          selectedTokenID = new Long(tokens[0].getTokenID());
          token = tokens[0];
        } else {
          boolean gotTokenID = false;
          while (!gotTokenID) {
            output_.print("Enter the ID of the token to use or 'x' to exit: ");
            output_.flush();
            String tokenIDstring = input_.readLine();
            if (tokenIDstring.equalsIgnoreCase("x")) {
              output_.flush();
              System.exit(0);
            }
            try {
              selectedTokenID = new Long(tokenIDstring);
              token = (Token) tokenIDtoToken.get(selectedTokenID);
              if (token != null) {
                gotTokenID = true;
              } else {
                output_.println("A token with the entered ID \"" + tokenIDstring + "\" does not exist. Try again.");
              }
            } catch (NumberFormatException ex) {
              output_.println("The entered ID \"" + tokenIDstring + "\" is invalid. Try again.");
            }
          }
        }

        Session session =
            token.openSession(Token.SessionType.SERIAL_SESSION, Token.SessionReadWriteBehavior.RO_SESSION, null, null);

        TokenInfo tokenInfo = token.getTokenInfo();
        if (tokenInfo.isLoginRequired()) {
          if (tokenInfo.isProtectedAuthenticationPath()) {
            output_.print("Please enter the user PIN at the PIN-pad of your reader.");
            session.login(Session.UserType.USER, null); // the token prompts the PIN by other means; e.g. PIN-pad
          } else {
            output_.print("Enter user-PIN and press [return key]: ");
            output_.flush();
            String userPINString = input_.readLine();
            session.login(Session.UserType.USER, userPINString.toCharArray());
          }
        }
        output_.println("################################################################################");

        List supportedMechanisms = Arrays.asList(token.getMechanismList());
        MechanismInfo des3CbcMechanismInfo = null;
        if (!supportedMechanisms.contains(Mechanism.DES3_CBC_PAD)) {
          output_.print("This token does not support Tripple DES!");
          output_.flush();
          System.exit(0);
        } else {
          des3CbcMechanismInfo = token.getMechanismInfo(Mechanism.DES3_CBC_PAD);
          if (!des3CbcMechanismInfo.isEncrypt()) {
            output_.print("This token does not support Tripple DES for encryption!");
            output_.flush();
            System.exit(0);
          }
        }

        output_.println("################################################################################");
        output_.println("searching for Tripple DES encryption keys");

        List encryptionKeyList = new Vector(4);

        // first we search for secret keys that we can use for encryption
        DES3SecretKey secretEncryptionKeyTemplate = new DES3SecretKey();
        secretEncryptionKeyTemplate.getEncrypt().setBooleanValue(Boolean.TRUE);

        session.findObjectsInit(secretEncryptionKeyTemplate);
        Object[] secretEncryptionKeys = session.findObjects(1);

        while (secretEncryptionKeys.length > 0) {
          encryptionKeyList.add(secretEncryptionKeys[0]);
          secretEncryptionKeys = session.findObjects(1);
        }
        session.findObjectsFinal();

        DES3SecretKey selectedEncryptionKey = null;
        if (encryptionKeyList.size() == 0) {
          if (supportedMechanisms.contains(Mechanism.DES3_KEY_GEN)) {
            output_.println("Found NO Tripple DES key that can be used for encryption.");
            output_.print("Do you want to generate a temporal session key? (y/n) ");
            output_.flush();

            String mechanismNameString = input_.readLine();
            if (mechanismNameString.equalsIgnoreCase("y")) {
              Mechanism keyGenerationMechanism = (Mechanism) Mechanism.DES3_KEY_GEN.clone();

              DES3SecretKey secretKeyTemplate = new DES3SecretKey();
              secretKeyTemplate.getEncrypt().setBooleanValue(Boolean.TRUE);
              secretKeyTemplate.getDecrypt().setBooleanValue(Boolean.TRUE);
              // we only have a read-only session, thus we only create a session object
              secretKeyTemplate.getToken().setBooleanValue(Boolean.FALSE);

              selectedEncryptionKey = (DES3SecretKey) session.generateKey(keyGenerationMechanism, secretKeyTemplate);
            } else {
              output_.flush();
              System.exit(0);
            }
          } else {
            output_.println("Found NO Tripple DES key that can be used for encryption.");
            output_.println("This token does not support generation of Tripple DES keys.");
            output_.flush();
            System.exit(0);
          }
        } else {
          output_.println("found these Tripple DES encryption keys:");
          Map objectHandleToObjectMap = new HashMap(encryptionKeyList.size());
          Iterator encryptionKeyListIterator = encryptionKeyList.iterator();
          while (encryptionKeyListIterator.hasNext()) {
            Object encryptionKey = (Object) encryptionKeyListIterator.next();
            long objectHandle = encryptionKey.getObjectHandle();
            objectHandleToObjectMap.put(new Long(objectHandle), encryptionKey);
            output_.println("________________________________________________________________________________");
            output_.println("Object with handle: " + objectHandle);
            output_.println(encryptionKey);
            output_.println("________________________________________________________________________________");
          }

          boolean gotObjectHandle = false;
          Long selectedObjectHandle;
          while (!gotObjectHandle) {
            output_.print("Enter the handle of the key to use for encryption or 'x' to exit: ");
            output_.flush();
            String objectHandleString = input_.readLine();
            if (objectHandleString.equalsIgnoreCase("x")) {
              System.exit(0);
            }
            try {
              selectedObjectHandle = new Long(objectHandleString);
              selectedEncryptionKey = (DES3SecretKey) objectHandleToObjectMap.get(selectedObjectHandle);
              if (selectedEncryptionKey != null) {
                gotObjectHandle = true;
              } else {
                output_.println("An object with the handle \"" + objectHandleString + "\" does not exist. Try again.");
              }
            } catch (NumberFormatException ex) {
              output_.println("The entered handle \"" + objectHandleString + "\" is invalid. Try again.");
            }
          }
        }

        output_.println("################################################################################");

        output_.println("################################################################################");
        output_.println("encrypting data from file: " + args[1]);

        InputStream dataInputStream = new FileInputStream(args[1]);

        /* we buffer all data in memory that we can use encrypt(byte[]) instad of several
         * subsequent encryptUpdate(byte[]) calls, because many tokens do not support this
         */

        byte[] dataBuffer = new byte[1024];
        int bytesRead;
        ByteArrayOutputStream streamBuffer = new ByteArrayOutputStream();

        // feed in all data from the input stream
        while ((bytesRead = dataInputStream.read(dataBuffer)) >= 0) {
          streamBuffer.write(dataBuffer, 0, bytesRead);
        }
        Arrays.fill(dataBuffer, (byte) 0); // ensure that no data is left in the memory
        streamBuffer.flush();
        streamBuffer.close();
        dataInputStream.close();
        byte[] rawData = streamBuffer.toByteArray();

        Mechanism selectedMechanism = (Mechanism) Mechanism.DES3_CBC_PAD.clone();

        byte[] encryptInitializationVector = { 0, 0, 0, 0, 0, 0, 0, 0}; // use random value
        InitializationVectorParameters encryptInitializationVectorParameters =
            new InitializationVectorParameters(encryptInitializationVector);
        selectedMechanism.setParameters(encryptInitializationVectorParameters);

        output_.print("encrypting the data... ");

        // initialize for encryption
        session.encryptInit(selectedMechanism, selectedEncryptionKey);

        byte[] encryptedData = session.encrypt(rawData);

        output_.println("finished");

        output_.print("writing encrypted data to file \"" + args[2] + "\"...");

        FileOutputStream outputStream = new FileOutputStream(args[2]);
        outputStream.write(encryptedData);
        outputStream.flush();
        outputStream.close();

        output_.println("finished");

        output_.println("################################################################################");

        output_.println("################################################################################");
        
        if (!des3CbcMechanismInfo.isDecrypt()) {
            output_.print("This token does not support Tripple DES for decryption!");
        } else {       
          if (!selectedEncryptionKey.getDecrypt().getBooleanValue().booleanValue()) {
            output_.print("The selected key cannot be used for decryption!");
          } else {           
            output_.println("decrypting data from file: " + args[2]);
            
            // we alread have the data in the encryptedData array
    
            // use same mechanism and IV as before
            selectedMechanism = (Mechanism) Mechanism.DES3_CBC_PAD.clone();
            selectedMechanism.setParameters(encryptInitializationVectorParameters);
    
            output_.print("decrypting the data... ");
    
            // initialize for encryption
            session.decryptInit(selectedMechanism, selectedEncryptionKey);
    
            byte[] decryptedData = session.decrypt(encryptedData);
    
            output_.println("finished");
    
            // compare initial data and decrypted data
            boolean equal = false;
            if (rawData.length != decryptedData.length) {
              equal = false;
            } else {
              equal = true;
              for (int i=0; i < rawData.length; i++) {
                if (rawData[i] != decryptedData[i]) {
                  equal = false;
                  break;
                }
              }
            }

            output_.println("decryption " + ((equal) ? "successful" : "FAILED"));
    
            output_.println("finished");
          }
        }

        output_.println("################################################################################");

        session.closeSession();
        pkcs11Module.finalize(null);

    } catch (Throwable thr) {
      thr.printStackTrace();
    }
  }

  public static void printUsage() {
    output_.println("Usage: Encrypt <PKCS#11 module> <file to be encrypted> <encrypted file>");
    output_.println(" e.g.: Encrypt pk2priv.dll data.dat data.enc");
    output_.println("The given DLL must be in the search path of the system.");
  }

}