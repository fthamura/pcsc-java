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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.MechanismInfo;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenInfo;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.objects.PrivateKey;
import iaik.pkcs.pkcs11.objects.X509PublicKeyCertificate;
import iaik.pkcs.pkcs7.EncryptedContentInfoStream;
import iaik.pkcs.pkcs7.EnvelopedDataStream;
import iaik.pkcs.pkcs7.IssuerAndSerialNumber;
import iaik.pkcs.pkcs7.RecipientInfo;
import iaik.security.provider.IAIK;
import iaik.x509.X509Certificate;



/**
 * This demo shows how to use a PKCS#11 token to decrypt a PKCS#7 encrypted
 * object. It only supports RSA decryption. This sample just decrypts the
 * included symmetric key on the token and uses the symmetric key to decrypt
 * the content on the host, i.e. in software.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class DecryptPKCS7 {

  static PrintStream output_;

  static BufferedReader input_;

  static {
    try {
      //output_ = new PrintWriter(new FileWriter("GetInfo_output.txt"), true);
      output_ = new PrintStream(System.out, true);
      input_ = new BufferedReader(new InputStreamReader(System.in));
    } catch (Throwable thr) {
      thr.printStackTrace();
      output_ = new PrintStream(System.out, true);
      input_ = new BufferedReader(new InputStreamReader(System.in));
    }
  }

  public static void main(String[] args) {
    if ((args.length != 2) && (args.length != 3)) {
      printUsage();
      System.exit(1);
    }

    try {
      Security.addProvider(new IAIK());

      Module pkcs11Module = Module.getInstance(args[0]);
      pkcs11Module.initialize(null);

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

      // check, if this token can do RSA decryption
      List supportedMechanisms = Arrays.asList(token.getMechanismList());
      if (!supportedMechanisms.contains(Mechanism.RSA_PKCS)) {
        output_.print("This token does not support RSA!");
        output_.flush();
        System.exit(0);
      } else {
        MechanismInfo rsaMechanismInfo = token.getMechanismInfo(Mechanism.RSA_PKCS);
        if (!rsaMechanismInfo.isDecrypt()) {
          output_.print("This token does not support RSA decryption according to PKCS!");
          output_.flush();
          System.exit(0);
        }
      }

      Session session =
          token.openSession(Token.SessionType.SERIAL_SESSION, Token.SessionReadWriteBehavior.RO_SESSION, null, null);

      TokenInfo tokenInfo = token.getTokenInfo();
      if (tokenInfo.isLoginRequired()) {
        if (tokenInfo.isProtectedAuthenticationPath()) {
          output_.println("Please enter the user PIN at the PIN-pad of your reader.");
          session.login(Session.UserType.USER, null); // the token prompts the PIN by other means; e.g. PIN-pad
        } else {
          output_.print("Enter user-PIN and press [return key]: ");
          output_.flush();
          String userPINString = input_.readLine();
          session.login(Session.UserType.USER, userPINString.toCharArray());
        }
      }

      // read all certificates that are on the token
      List tokenCertificates = new Vector();
      X509PublicKeyCertificate certificateTemplate = new X509PublicKeyCertificate();
      session.findObjectsInit(certificateTemplate);
      Object[] tokenCertificateObjects;

      while ((tokenCertificateObjects = session.findObjects(1)).length > 0) {
        tokenCertificates.add(tokenCertificateObjects[0]);
      }
      session.findObjectsFinal();

      output_.println("################################################################################");

      output_.println("################################################################################");
      output_.println("reading encrypted data from file: " + args[1]);

      FileInputStream encryptedInputStream = new FileInputStream(args[1]);

      EnvelopedDataStream envelopedData = new EnvelopedDataStream(encryptedInputStream);

      RecipientInfo[] recipientInfos = envelopedData.getRecipientInfos();

      // search through the recipients and look, if we have one of the recipients' certificates on the token
      boolean haveDecryptionKey = false;
      InputStream decryptedDataInputStream = null;
      for (int i = 0; i < recipientInfos.length; i++) {
        IssuerAndSerialNumber issuerAndSerialNumber = recipientInfos[i].getIssuerAndSerialNumber();

        // look if there is a certificate on our token with the given issuer and serial number
        X509PublicKeyCertificate matchingTokenCertificate = null;
        Iterator tokenCertificatesIterator = tokenCertificates.iterator();
        while (tokenCertificatesIterator.hasNext()) {
          X509PublicKeyCertificate tokenCertificate = (X509PublicKeyCertificate) tokenCertificatesIterator.next();
          X509Certificate parsedTokenCertificate = new X509Certificate(tokenCertificate.getValue().getByteArrayValue());
          if (issuerAndSerialNumber.isIssuerOf(parsedTokenCertificate)) {
            output_.println("________________________________________________________________________________");
            output_.println("Found matching certificate on the token:");
            output_.println(parsedTokenCertificate.toString(true));
            output_.println("________________________________________________________________________________");
            matchingTokenCertificate = tokenCertificate;
            break;
          }
        }

        if (matchingTokenCertificate != null) {
          // find the corresponding private key for the certificate
          PrivateKey privateKeyTemplate = new PrivateKey();
          privateKeyTemplate.getId().setByteArrayValue(matchingTokenCertificate.getId().getByteArrayValue());

          session.findObjectsInit(privateKeyTemplate);
          Object[] correspondingPrivateKeyObjects;
          PrivateKey correspondingPrivateKey = null;

          if ((correspondingPrivateKeyObjects = session.findObjects(1)).length > 0) {
            correspondingPrivateKey = (PrivateKey) correspondingPrivateKeyObjects[0];
            output_.println("________________________________________________________________________________");
            output_.println("Found corresponding private key:");
            output_.println(correspondingPrivateKey);
            output_.println("________________________________________________________________________________");
          } else {
            output_.println("Found no private key with the same ID as the matching certificate.");
          }
          session.findObjectsFinal();

          // check, if the private key is a decrpytion key
          PrivateKey decryptionKey =
              ((correspondingPrivateKey != null) && (correspondingPrivateKey.getDecrypt().getBooleanValue().booleanValue()))
              ? correspondingPrivateKey
              : null;

          if (decryptionKey != null) {
            haveDecryptionKey = true;
            output_.print("decrypting symmetric key... ");
            byte[] encryptedSymmetricKey = recipientInfos[i].getEncryptedKey();
            // decrypt the encrypted symmetric key using the e.g. RSA on the smart-card
            session.decryptInit(Mechanism.RSA_PKCS, decryptionKey);
            byte[] decryptedSymmetricKey = session.decrypt(encryptedSymmetricKey);
            output_.println("finished");

            // construct the symmetric key
            output_.print("constructing symmetric key for software decryption... ");
            EncryptedContentInfoStream encryptedContentInfo =
                (EncryptedContentInfoStream) envelopedData.getEncryptedContentInfo();
            AlgorithmID contentEncryptionAlgorithm = encryptedContentInfo.getContentEncryptionAlgorithm();
            SecretKeySpec secretKeySpec =
                new SecretKeySpec(decryptedSymmetricKey, contentEncryptionAlgorithm.getRawImplementationName());
            SecretKeyFactory secretKeyFactory =
                SecretKeyFactory.getInstance(contentEncryptionAlgorithm.getRawImplementationName());
            javax.crypto.SecretKey secretKey = secretKeyFactory.generateSecret(secretKeySpec);
            output_.println("finished");

            // decrypt the data (in software)
            encryptedContentInfo.setupCipher(secretKey);
            decryptedDataInputStream = encryptedContentInfo.getInputStream();

            // read decrypted data from decryptedDataInputStream
          }
        }
      }

      if (!haveDecryptionKey) {
        output_.print("Found no decryption key that matches any recipient info in the encrypted PKCS#7 object.");
        output_.flush();
        System.exit(0);
      }

      if (decryptedDataInputStream == null) {
        output_.print("Could not decrypt the PKCS#7 object.");
        output_.flush();
        System.exit(0);
      }
      output_.println("################################################################################");


      output_.println("################################################################################");
      OutputStream decryptedContentStream = (args.length == 3) ? new FileOutputStream(args[2]) : null;
      byte[] buffer = new byte[1024];
      int bytesRead;
      output_.println("The decrypted content data is: ");
      output_.println("________________________________________________________________________________");
      while ((bytesRead = decryptedDataInputStream.read(buffer)) > 0) {
        output_.write(buffer, 0, bytesRead);
        if (decryptedContentStream != null) {
          decryptedContentStream.write(buffer, 0, bytesRead);
        }
      }
      output_.println();
      output_.println("________________________________________________________________________________");
      if (decryptedContentStream != null) {
        output_.println("Decrypted content written to: " + args[2]);
        decryptedContentStream.flush();
        decryptedContentStream.close();
      }
      output_.println("################################################################################");

      session.closeSession();
      pkcs11Module.finalize(null);

    } catch (Throwable th) {
      th.printStackTrace();
    }
  }

  public static void printUsage() {
    output_.println("Usage: DecryptPKCS7 <PKCS#11 module> <PKCS#7 encrypted data file> [<decrypted content data>]");
    output_.println(" e.g.: DecryptPKCS7 slbck.dll encryptedData.p7 decryptedContent.dat");
    output_.println("The given DLL must be in the search path of the system.");
  }

}