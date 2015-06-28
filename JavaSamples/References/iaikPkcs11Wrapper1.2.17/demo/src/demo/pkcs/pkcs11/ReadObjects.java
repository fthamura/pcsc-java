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
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenInfo;
import iaik.pkcs.pkcs11.objects.Data;
import iaik.pkcs.pkcs11.objects.HardwareFeature;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.objects.PrivateKey;
import iaik.pkcs.pkcs11.objects.PublicKey;
import iaik.pkcs.pkcs11.objects.SecretKey;
import iaik.pkcs.pkcs11.objects.X509PublicKeyCertificate;



/**
 * This demo program is similar to GetInfo. It takes the first token and dumps
 * all objects on this token.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class ReadObjects {

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
    try {
      if ((args.length == 1) || (args.length == 2)) {
        Module pkcs11Module = Module.getInstance(args[0]);
        pkcs11Module.initialize(null);

        Slot[] slots = pkcs11Module.getSlotList(Module.SlotRequirement.TOKEN_PRESENT);

        if (slots.length == 0) {
          System.out.println("No slot with present token found!");
          System.exit(0);
        }

        Slot selectedSlot = slots[0];
        Token token = selectedSlot.getToken();

        Session session =
            token.openSession(Token.SessionType.SERIAL_SESSION, Token.SessionReadWriteBehavior.RO_SESSION, null, null);

        TokenInfo tokenInfo = token.getTokenInfo();
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

        output_.println("################################################################################");
        output_.println("listing all private keys");
        PrivateKey privateKeyTemplate = new PrivateKey();

        session.findObjectsInit(privateKeyTemplate);

        Object[] foundPrivateKeyObjects = session.findObjects(1); // find first

        while (foundPrivateKeyObjects.length > 0) {
          output_.println("________________________________________________________________________________");
          output_.println(foundPrivateKeyObjects[0]);
          output_.println("________________________________________________________________________________");
          foundPrivateKeyObjects = session.findObjects(1); //find next
        }
        session.findObjectsFinal();

        output_.println("################################################################################");

        output_.println("################################################################################");
        output_.println("listing all public keys");
        PublicKey publicKeyTemplate = new PublicKey();

        session.findObjectsInit(publicKeyTemplate);

        Object[] foundPublicKeyObjects = session.findObjects(1); // find first

        while (foundPublicKeyObjects.length > 0) {
          output_.println("________________________________________________________________________________");
          output_.println(foundPublicKeyObjects[0]);
          output_.println("________________________________________________________________________________");
          foundPublicKeyObjects = session.findObjects(1); //find next
        }
        session.findObjectsFinal();

        output_.println("################################################################################");

        output_.println("################################################################################");
        output_.println("listing all X.509 public key certificates");
        X509PublicKeyCertificate x509PublicKeyCertificateTemplate = new X509PublicKeyCertificate();

        session.findObjectsInit(x509PublicKeyCertificateTemplate);

        Object[] foundTokenObjects = session.findObjects(1); // find first

        while (foundTokenObjects.length > 0) {
          output_.println("________________________________________________________________________________");
          output_.println(foundTokenObjects[0]);
          output_.println("--------------------------------------------------------------------------------");
          X509PublicKeyCertificate x509PublicKeyCertificate = (X509PublicKeyCertificate) foundTokenObjects[0];
          CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
          byte[] derEncodedCertificate = x509PublicKeyCertificate.getValue().getByteArrayValue();
          X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(
              new ByteArrayInputStream(derEncodedCertificate));
          output_.println(x509Certificate);
          output_.println("________________________________________________________________________________");
          foundTokenObjects = session.findObjects(1); //find next
        }
        session.findObjectsFinal();

        output_.println("################################################################################");

        output_.println("################################################################################");
        output_.println("listing all secret keys");
        SecretKey secretKeyTemplate = new SecretKey();

        session.findObjectsInit(secretKeyTemplate);

        Object[] foundSecretKeyObjects = session.findObjects(1); // find first

        while (foundSecretKeyObjects.length > 0) {
          output_.println("________________________________________________________________________________");
          output_.println(foundSecretKeyObjects[0]);
          output_.println("________________________________________________________________________________");
          foundSecretKeyObjects = session.findObjects(1); //find next
        }
        session.findObjectsFinal();

        output_.println("################################################################################");

        output_.println("################################################################################");
        output_.println("listing all data objects");
        Data dataTemplate = new Data();

        session.findObjectsInit(dataTemplate);

        Object[] foundDataObjects = session.findObjects(1); // find first

        while (foundDataObjects.length > 0) {
          output_.println("________________________________________________________________________________");
          output_.println(foundDataObjects[0]);
          output_.println("________________________________________________________________________________");
          foundDataObjects = session.findObjects(1); //find next
        }
        session.findObjectsFinal();

        output_.println("################################################################################");

        output_.println("################################################################################");
        output_.println("listing all hardware feature objects");
        HardwareFeature hardwareFeatureTemplate = new HardwareFeature();

        session.findObjectsInit(hardwareFeatureTemplate);

        Object[] foundHardwareFeatureObjects = session.findObjects(1); // find first

        while (foundHardwareFeatureObjects.length > 0) {
          output_.println("________________________________________________________________________________");
          output_.println(foundHardwareFeatureObjects[0]);
          output_.println("________________________________________________________________________________");
          foundHardwareFeatureObjects = session.findObjects(1); //find next
        }
        session.findObjectsFinal();

        output_.println("################################################################################");

        session.closeSession();

        pkcs11Module.finalize(null);

      } else {
        printUsage();
      }
    } catch (Throwable ex) {
      ex.printStackTrace();
    }
  }

  protected static void printUsage() {
    output_.println("ReadObjects <PKCS#11 module name>");
    output_.println("e.g.: ReadObjects pk2priv.dll");
  }

}