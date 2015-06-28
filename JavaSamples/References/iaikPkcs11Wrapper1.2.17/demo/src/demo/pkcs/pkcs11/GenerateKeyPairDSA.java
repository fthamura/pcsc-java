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
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.Security;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.MechanismInfo;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenInfo;
import iaik.pkcs.pkcs11.objects.DSAPrivateKey;
import iaik.pkcs.pkcs11.objects.DSAPublicKey;
import iaik.pkcs.pkcs11.objects.KeyPair;
import iaik.pkcs.pkcs11.objects.Object;
import iaik.pkcs.pkcs11.wrapper.Functions;
import iaik.security.provider.IAIK;



/**
 * This demo program generates a 1024 bit DSA key-pair on the token and writes
 * the public key to a file.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class GenerateKeyPairDSA {

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
    if (args.length != 2) {
      printUsage();
      System.exit(1);
    }

    try {

      Security.addProvider(new IAIK());

      Module pkcs11Module = Module.getInstance(args[0]);
      pkcs11Module.initialize(null);

      Slot[] slots = pkcs11Module.getSlotList(Module.SlotRequirement.TOKEN_PRESENT);

      if (slots.length == 0) {
        output_.println("No slot with present token found!");
        System.exit(0);
      }

      Slot selectedSlot = slots[0];
      Token token = selectedSlot.getToken();
      TokenInfo tokenInfo = token.getTokenInfo();

      output_.println("################################################################################");
      output_.println("Information of Token:");
      output_.println(tokenInfo);
      output_.println("################################################################################");

      Session session = Util.openAuthorizedSession(token, Token.SessionReadWriteBehavior.RW_SESSION, output_, input_);
          // token.openSession(Token.SessionType.SERIAL_SESSION, Token.SessionReadWriteBehavior.RW_SESSION, null, null);

      // generate DSA domain parameters in software
      output_.println("################################################################################");
      output_.print("Generating new 1024 bit DSA parameters (in software)... ");
      output_.flush();
      AlgorithmParameterGenerator parameterGenerator = AlgorithmParameterGenerator.getInstance("DSA");
      parameterGenerator.init(1024);
      AlgorithmParameters parameters = parameterGenerator.generateParameters();
      DSAParameterSpec parameterSpec = (DSAParameterSpec) parameters.getParameterSpec(DSAParameterSpec.class);
      output_.println("Success");
      output_.println("P: " + Functions.toHexString(parameterSpec.getP().toByteArray()));
      output_.println("Q: " + Functions.toHexString(parameterSpec.getQ().toByteArray()));
      output_.println("G: " + Functions.toHexString(parameterSpec.getG().toByteArray()));
      output_.println("################################################################################");

      output_.println("################################################################################");
      output_.print("Generating new 1024 bit DSA key-pair... ");
      output_.flush();

      // first check out what attributes of the keys we may set
      HashSet supportedMechanisms = new HashSet(Arrays.asList(token.getMechanismList()));

      MechanismInfo signatureMechanismInfo;
      if (supportedMechanisms.contains(Mechanism.DSA)) {
        signatureMechanismInfo = token.getMechanismInfo(Mechanism.DSA);
      } else {
        signatureMechanismInfo = null;
      }

      Mechanism keyPairGenerationMechanism = Mechanism.DSA_KEY_PAIR_GEN;
      DSAPublicKey dsaPublicKeyTemplate = new DSAPublicKey();
      DSAPrivateKey dsaPrivateKeyTemplate = new DSAPrivateKey();

      // set the general attributes for the public key
      dsaPublicKeyTemplate.getPrime().setByteArrayValue(
          iaik.pkcs.pkcs11.Util.unsignedBigIntergerToByteArray(parameterSpec.getP()));
      dsaPublicKeyTemplate.getSubprime().setByteArrayValue(
          iaik.pkcs.pkcs11.Util.unsignedBigIntergerToByteArray(parameterSpec.getQ()));
      dsaPublicKeyTemplate.getBase().setByteArrayValue(
          iaik.pkcs.pkcs11.Util.unsignedBigIntergerToByteArray(parameterSpec.getG()));

      dsaPublicKeyTemplate.getToken().setBooleanValue(Boolean.TRUE);
      byte[] id = new byte[20];
      new Random().nextBytes(id);
      dsaPublicKeyTemplate.getId().setByteArrayValue(id);
      //dsaPublicKeyTemplate.getLabel().setCharArrayValue(args[2].toCharArray());

      dsaPrivateKeyTemplate.getSensitive().setBooleanValue(Boolean.TRUE);
      dsaPrivateKeyTemplate.getToken().setBooleanValue(Boolean.TRUE);
      dsaPrivateKeyTemplate.getPrivate().setBooleanValue(Boolean.TRUE);
      dsaPrivateKeyTemplate.getId().setByteArrayValue(id);
      //byte[] subject = args[1].getBytes();
      //dsaPrivateKeyTemplate.getSubject().setByteArrayValue(subject);
      //dsaPrivateKeyTemplate.getLabel().setCharArrayValue(args[2].toCharArray());

      // set the attributes in a way netscape does, this should work with most tokens
      if (signatureMechanismInfo != null) {
        dsaPublicKeyTemplate.getVerify().setBooleanValue(new Boolean(signatureMechanismInfo.isVerify()));
        dsaPublicKeyTemplate.getVerifyRecover().setBooleanValue(new Boolean(signatureMechanismInfo.isVerifyRecover()));
        dsaPublicKeyTemplate.getEncrypt().setBooleanValue(new Boolean(signatureMechanismInfo.isEncrypt()));
        dsaPublicKeyTemplate.getDerive().setBooleanValue(new Boolean(signatureMechanismInfo.isDerive()));
        dsaPublicKeyTemplate.getWrap().setBooleanValue(new Boolean(signatureMechanismInfo.isWrap()));

        dsaPrivateKeyTemplate.getSign().setBooleanValue(new Boolean(signatureMechanismInfo.isSign()));
        dsaPrivateKeyTemplate.getSignRecover().setBooleanValue(new Boolean(signatureMechanismInfo.isSignRecover()));
        dsaPrivateKeyTemplate.getDecrypt().setBooleanValue(new Boolean(signatureMechanismInfo.isDecrypt()));
        dsaPrivateKeyTemplate.getDerive().setBooleanValue(new Boolean(signatureMechanismInfo.isDerive()));
        dsaPrivateKeyTemplate.getUnwrap().setBooleanValue(new Boolean(signatureMechanismInfo.isUnwrap()));
      } else {
        // if we have no information we assume these attributes
        dsaPrivateKeyTemplate.getSign().setBooleanValue(Boolean.TRUE);

        dsaPublicKeyTemplate.getVerify().setBooleanValue(Boolean.TRUE);
      }

      // netscape does not set these attribute, so we do no either
      dsaPublicKeyTemplate.getKeyType().setPresent(false);
      dsaPublicKeyTemplate.getObjectClass().setPresent(false);

      dsaPrivateKeyTemplate.getKeyType().setPresent(false);
      dsaPrivateKeyTemplate.getObjectClass().setPresent(false);

      KeyPair generatedKeyPair = session.generateKeyPair(keyPairGenerationMechanism, dsaPublicKeyTemplate, dsaPrivateKeyTemplate);
      DSAPublicKey generatedDSAPublicKey = (DSAPublicKey) generatedKeyPair.getPublicKey();
      DSAPrivateKey generatedDSAPrivateKey = (DSAPrivateKey) generatedKeyPair.getPrivateKey();
      // no we may work with the keys...

      output_.println("Success");
      output_.println("The public key is");
      output_.println("_______________________________________________________________________________");
      output_.println(generatedDSAPublicKey);
      output_.println("_______________________________________________________________________________");
      output_.println("The private key is");
      output_.println("_______________________________________________________________________________");
      output_.println(generatedDSAPrivateKey);
      output_.println("_______________________________________________________________________________");

      // write the public key to file
      output_.println("################################################################################");
      output_.println("Writing the public key of the generated key-pair to file: " + args[1]);
      DSAPublicKey exportableDsaPublicKey = generatedDSAPublicKey;
      BigInteger p = new BigInteger(1, exportableDsaPublicKey.getPrime().getByteArrayValue());
      BigInteger q = new BigInteger(1, exportableDsaPublicKey.getSubprime().getByteArrayValue());
      BigInteger g = new BigInteger(1, exportableDsaPublicKey.getBase().getByteArrayValue());
      BigInteger y = new BigInteger(1, exportableDsaPublicKey.getValue().getByteArrayValue());
      DSAPublicKeySpec rsaPublicKeySpec = new DSAPublicKeySpec(y, p, q, g);
      KeyFactory keyFactory = KeyFactory.getInstance("DSA");
      java.security.interfaces.DSAPublicKey javaDsaPublicKey = (java.security.interfaces.DSAPublicKey)
          keyFactory.generatePublic(rsaPublicKeySpec);
      X509EncodedKeySpec x509EncodedPublicKey = (X509EncodedKeySpec)
          keyFactory.getKeySpec(javaDsaPublicKey, X509EncodedKeySpec.class);

      FileOutputStream publicKeyFileStream = new FileOutputStream(args[1]);
      publicKeyFileStream.write(x509EncodedPublicKey.getEncoded());
      publicKeyFileStream.flush();
      publicKeyFileStream.close();

      output_.println("################################################################################");

      // now we try to search for the generated keys
      output_.println("################################################################################");
      output_.println("Trying to search for the public key of the generated key-pair by ID: " +
                      Functions.toHexString(id));
      // set the search template for the public key
      DSAPublicKey exportDsaPublicKeyTemplate = new DSAPublicKey();
      exportDsaPublicKeyTemplate.getId().setByteArrayValue(id);

      session.findObjectsInit(exportDsaPublicKeyTemplate);
      Object[] foundPublicKeys = session.findObjects(1);
      session.findObjectsFinal();

      if (foundPublicKeys.length != 1) {
        output_.println("Error: Cannot find the public key under the given ID!");
      } else {
        output_.println("Found public key!");
        output_.println("_______________________________________________________________________________");
        output_.println(foundPublicKeys[0]);
        output_.println("_______________________________________________________________________________");
      }

      output_.println("################################################################################");

      session.closeSession();
      pkcs11Module.finalize(null);

    } catch (Throwable thr) {
      thr.printStackTrace();
    }
  }

  public static void printUsage() {
    output_.println("Usage: GenerateKeyPair <PKCS#11 module> <X.509 encoded public key output file>");
    output_.println(" e.g.: GenerateKeyPair pk2priv.dll publicKey.xpk");
    output_.println("The given DLL must be in the search path of the system.");
  }

}