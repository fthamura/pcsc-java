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
import java.security.Signature;
import java.util.Arrays;
import java.util.List;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.MechanismInfo;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.objects.PrivateKey;
import iaik.pkcs.pkcs11.objects.RSAPrivateKey;
import iaik.x509.X509Certificate;



/**
 * Signs an X.509 certificate using a token. The actual certificate specific
 * operations are in the last section of this demo.
 * The hash is calculated outside the token. This implementation just uses raw
 * RSA.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class SignCertificate {

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

      Module pkcs11Module = Module.getInstance(args[0]);
      pkcs11Module.initialize(null);

      Token token = Util.selectToken(pkcs11Module, output_, input_);
      if (token == null) {
        output_.println("We have no token to proceed. Finished.");
        output_.flush();
        System.exit(0);
      }

      List supportedMechanisms = Arrays.asList(token.getMechanismList());
      if (!supportedMechanisms.contains(Mechanism.RSA_PKCS)) {
        output_.print("This token does not support raw RSA signing!");
        output_.flush();
        System.exit(0);
      } else {
        MechanismInfo rsaMechanismInfo = token.getMechanismInfo(Mechanism.RSA_PKCS);
        if (!rsaMechanismInfo.isSign()) {
          output_.print("This token does not support RSA signing according to PKCS!");
          output_.flush();
          System.exit(0);
        }
      }

      Session session = Util.openAuthorizedSession(token, Token.SessionReadWriteBehavior.RO_SESSION, output_, input_);

      // first we search for private RSA keys that we can use for signing
      RSAPrivateKey privateSignatureKeyTemplate = new RSAPrivateKey();
      privateSignatureKeyTemplate.getSign().setBooleanValue(Boolean.TRUE);

      KeyAndCertificate selectedSignatureKeyAndCertificate =
          Util.selectKeyAndCertificate(session, privateSignatureKeyTemplate, output_, input_);
      if (selectedSignatureKeyAndCertificate == null) {
        output_.println("We have no signature key to proceed. Finished.");
        output_.flush();
        System.exit(0);
      }

      PrivateKey selectedSignatureKey = (PrivateKey) selectedSignatureKeyAndCertificate.getKey();

      // here the interesting code starts

      output_.println("################################################################################");
      output_.println("signing demo certificate");

      Signature tokenSignatureEngine =
          new PKCS11SignatureEngine("SHA1withRSA", session, Mechanism.RSA_PKCS, AlgorithmID.sha1);
      AlgorithmIDAdapter pkcs11Sha1RSASignatureAlgorithmID = new AlgorithmIDAdapter(AlgorithmID.sha1WithRSAEncryption);
      pkcs11Sha1RSASignatureAlgorithmID.setSignatureInstance(tokenSignatureEngine);

      InputStream templateCertificateStream = new FileInputStream(args[1]);
      X509Certificate certificate = new X509Certificate(templateCertificateStream);

      java.security.PrivateKey tokenSignatureKey = new TokenPrivateKey(selectedSignatureKey);

      output_.print("signing certificate... ");
      certificate.sign(pkcs11Sha1RSASignatureAlgorithmID , tokenSignatureKey);
      output_.println("finished");

      output_.print("writing certificate to file \"");
      output_.print(args[2]);
      output_.print("\"... ");
      OutputStream certificateStream = new FileOutputStream(args[2]);
      certificate.writeTo(certificateStream);
      output_.println("finished");

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
    output_.println("Usage: SignCertificate <PKCS#11 module> <DER-encoded X.509 template certificate> <DER-encoded certificate output file>");
    output_.println(" e.g.: SignCertificate pk2priv.dll templateCert.der signedCert.der");
    output_.println("The given DLL must be in the search path of the system.");
  }

}