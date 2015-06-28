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
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;

import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.MechanismInfo;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenException;
import iaik.pkcs.pkcs11.objects.DSAPrivateKey;
import iaik.pkcs.pkcs11.objects.DSAPublicKey;
import iaik.pkcs.pkcs11.objects.Key;
import iaik.pkcs.pkcs11.objects.Object;



/**
 * Creates and verifies a signature on a token. The hash is calculated outside
 * the token. Notice that many tokens do not support verification. In this case
 * you will get an exception when the progrom tries to verify the signature
 * on the token.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class DSASignAndVerify {

  static BufferedReader input_;

  static PrintWriter output_;

  static {
    try {
      //output_ = new PrintWriter(new FileWriter("SignAndVerify_output.txt"), true);
      output_ = new PrintWriter(System.out, true);
      input_ = new BufferedReader(new InputStreamReader(System.in));
    } catch (Throwable thr) {
      thr.printStackTrace();
      output_ = new PrintWriter(System.out, true);
      input_ = new BufferedReader(new InputStreamReader(System.in));
   }
  }

  public static void main(String[] args) {
    if ((args.length != 2) && (args.length != 3)) {
      printUsage();
      System.exit(1);
    }

    try {

        Module pkcs11Module = Module.getInstance(args[0]);
        pkcs11Module.initialize(null);

        Token token = Util.selectToken(pkcs11Module, output_, input_);
        if (token == null) {
          output_.println("We have no token to proceed. Finished.");
          output_.flush();
          System.exit(0);
        }

        // first check out what attributes of the keys we may set
        HashSet supportedMechanisms = new HashSet(Arrays.asList(token.getMechanismList()));

        MechanismInfo signatureMechanismInfo;
        if (supportedMechanisms.contains(Mechanism.DSA_SHA1)) {
          signatureMechanismInfo = token.getMechanismInfo(Mechanism.DSA_SHA1);
        } else {
          signatureMechanismInfo = null;
          output_.println("The token does not support mechanism DSA_SHA1. Going to exit.");
          System.exit(0);
        }

        if ((signatureMechanismInfo == null) || !signatureMechanismInfo.isSign()) {
          output_.println("The token does not support signing with mechanism DSA_SHA1. Going to exit.");
          System.exit(0);
        }

        Session session = Util.openAuthorizedSession(token, Token.SessionReadWriteBehavior.RO_SESSION, output_, input_);

        output_.println("################################################################################");
        output_.println("find private signature key");
        DSAPrivateKey templateSignatureKey = new DSAPrivateKey();
        templateSignatureKey.getSign().setBooleanValue(Boolean.TRUE);

        KeyAndCertificate selectedSignatureKeyAndCertificate =
            Util.selectKeyAndCertificate(session, templateSignatureKey, output_, input_);
        if (selectedSignatureKeyAndCertificate == null) {
          output_.println("We have no signature key to proceed. Finished.");
          output_.flush();
          System.exit(0);
        }
        Key signatureKey = selectedSignatureKeyAndCertificate.getKey();
        output_.println("################################################################################");


        output_.println("################################################################################");
        output_.println("signing data from file: " + args[1]);

        InputStream dataInputStream = new FileInputStream(args[1]);

        //be sure that your token can process the specified mechanism
        Mechanism signatureMechanism = Mechanism.DSA_SHA1;
        // initialize for signing
        session.signInit(signatureMechanism, signatureKey);

        byte[] dataBuffer = new byte[1024];
        byte[] helpBuffer;
        int bytesRead;

        // feed all data from the input stream to the message digest
        while ((bytesRead = dataInputStream.read(dataBuffer)) >= 0) {
          helpBuffer = new byte[bytesRead]; // we need a buffer that only holds what to send for signing
          System.arraycopy(dataBuffer, 0, helpBuffer, 0, bytesRead);
          session.signUpdate(helpBuffer);
          Arrays.fill(helpBuffer, (byte) 0); // ensure that no data is left in the memory
        }

        byte[] signatureValue = session.signFinal();

        Arrays.fill(dataBuffer, (byte) 0); // ensure that no data is left in the memory

        output_.println("The siganture value is: " + new BigInteger(1, signatureValue).toString(16));

        if (args.length == 3) {
          output_.println("Writing signature to file: " + args[2]);

          OutputStream signatureOutput = new FileOutputStream(args[2]);
          signatureOutput.write(signatureValue);
          signatureOutput.flush();
          signatureOutput.close();
        }

        output_.println("################################################################################");


        if ((signatureMechanismInfo == null) || !signatureMechanismInfo.isVerify()) {
          output_.println("The token does not support verification with mechanism RSA_PKCS. Going to exit.");
          System.exit(0);
        }

        output_.println("################################################################################");
        output_.println("find public verification key");
        DSAPublicKey templateVerificationKey = new DSAPublicKey();
        templateVerificationKey.getVerify().setBooleanValue(Boolean.TRUE);
        // we search for a public key with the same ID
        templateVerificationKey.getId().setByteArrayValue(signatureKey.getId().getByteArrayValue());

        session.findObjectsInit(templateVerificationKey);

        Object[] foundVerificationKeyObjects = session.findObjects(1); // find first

        DSAPublicKey verificationKey = null;
        if (foundVerificationKeyObjects.length > 0) {
          verificationKey = (DSAPublicKey) foundVerificationKeyObjects[0];
          output_.println("________________________________________________________________________________");
          output_.println(verificationKey);
          output_.println("________________________________________________________________________________");
       } else {
          output_.println("No matching public key found!");
          System.exit(0);
        }
        session.findObjectsFinal();

        output_.println("################################################################################");


        output_.println("################################################################################");
        output_.println("verifying signature");

        dataInputStream = new FileInputStream(args[1]);

        // feed all data from the input stream to the message digest
        while ((bytesRead = dataInputStream.read(dataBuffer)) >= 0) {
          helpBuffer = new byte[bytesRead]; // we need a buffer that only holds what to send for signing
          System.arraycopy(dataBuffer, 0, helpBuffer, 0, bytesRead);
          session.signUpdate(helpBuffer);
          Arrays.fill(helpBuffer, (byte) 0); // ensure that no data is left in the memory
        }

        //be sure that your token can process the specified mechanism
        Mechanism verificationMechanism = Mechanism.DSA_SHA1;
        // initialize for signing
        session.verifyInit(verificationMechanism, verificationKey);

        try {
          session.verifyFinal(signatureValue); // throws an exception upon unsuccessful verification
          output_.println("Verified the signature successfully");
        } catch (TokenException ex) {
          output_.println("Verification FAILED: " + ex.getMessage());
        }

        output_.println("################################################################################");


        session.closeSession();
        pkcs11Module.finalize(null);

    } catch (Throwable thr) {
      thr.printStackTrace();
    } finally {
      output_.close();
    }
  }

  public static void printUsage() {
    output_.println("Usage: DSASignAndVerify <PKCS#11 module> <file to be signed> [<signature value file>]");
    output_.println(" e.g.: DSASignAndVerify pk2priv.dll data.dat signature.bin");
    output_.println("The given DLL must be in the search path of the system.");
  }

}