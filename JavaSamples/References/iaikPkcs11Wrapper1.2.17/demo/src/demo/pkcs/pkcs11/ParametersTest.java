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
import java.io.InputStreamReader;
import java.io.PrintWriter;

import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.objects.Key;
import iaik.pkcs.pkcs11.parameters.DHKeyDerivationParameters;
import iaik.pkcs.pkcs11.parameters.Parameters;
import iaik.pkcs.pkcs11.parameters.X942DH2KeyDerivationParameters;



/**
 * Just to test the parameters classes.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class ParametersTest {

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
    if (args.length != 1) {
      printUsage();
      System.exit(1);
    }

    try {

        Module pkcs11Module = Module.getInstance(args[0]);
        pkcs11Module.initialize(null);

        Slot[] slots = pkcs11Module.getSlotList(Module.SlotRequirement.TOKEN_PRESENT);

        if (slots.length == 0) {
          output_.println("No slot with present token found!");
          output_.flush();
          System.exit(0);
        }

        Slot selectedSlot = slots[0];
        Token token = selectedSlot.getToken();

        Session session =
            token.openSession(Token.SessionType.SERIAL_SESSION, Token.SessionReadWriteBehavior.RO_SESSION, null, null);

        output_.println("################################################################################");

        Mechanism mechanism;
        Parameters parameters;
        Key key = new Key(); // create a dummy key
        Key anotherKey = new Key(); // create a dummy key
        key.setObjectHandle(1);
        anotherKey.setObjectHandle(2);

/*
        output_.print("testing DHPkcsDeriveParameters... ");
        mechanism = (Mechanism) Mechanism.DH_PKCS_DERIVE.clone();
        byte[] publicValue = { 1, 2, 3, 4, 5, 6, 7, 8 };
        parameters = new DHPkcsDeriveParameters(publicValue);
        mechanism.setParameters(parameters);
        session.deriveKey(mechanism, key, null);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing ExtractParameters... ");
        mechanism = (Mechanism) Mechanism.EXTRACT_KEY_FROM_KEY.clone();
        parameters = new ExtractParameters(4);
        mechanism.setParameters(parameters);
        session.deriveKey(mechanism, key, null);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing InitializationVectorParameters... ");
        mechanism = (Mechanism) Mechanism.DES3_CBC_PAD.clone();
        byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };
        parameters = new InitializationVectorParameters(iv);
        mechanism.setParameters(parameters);
        session.encryptInit(mechanism, key);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing KEADeriveParameters... ");
        mechanism = (Mechanism) Mechanism.EXTRACT_KEY_FROM_KEY.clone();
        byte[] randomA = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        byte[] randomB = { 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F };
        byte[] publicData = { 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        parameters = new KEADeriveParameters(true, randomA, randomB, publicData);
        mechanism.setParameters(parameters);
        session.deriveKey(mechanism, key, null);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing KeyDerivationStringDataParameters... ");
        byte[] data = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        parameters = new KeyDerivationStringDataParameters(data);
        mechanism = (Mechanism) Mechanism.CONCATENATE_BASE_AND_DATA.clone();
        mechanism.setParameters(parameters);
        session.deriveKey(mechanism, key, null);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing KeyWrapSetOaepParameters... ");
        byte[] x = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        parameters = new KeyWrapSetOaepParameters((byte) 0xEF, x);
        mechanism = (Mechanism) Mechanism.KEY_WRAP_SET_OAEP.clone();
        mechanism.setParameters(parameters);
        session.wrapKey(mechanism, key, anotherKey);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing MacGeneralParameters... ");
        byte[] x = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        parameters = new MacGeneralParameters(16);
        mechanism = (Mechanism) Mechanism.DES3_MAC_GENERAL.clone();
        mechanism.setParameters(parameters);
        session.signInit(mechanism, key);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing ObjectHandleParameters... ");
        Key parameterKey = new Key(); // create a dummy key
        parameterKey.setObjectHandle(3);
        parameters = new ObjectHandleParameters(parameterKey);
        mechanism = (Mechanism) Mechanism.CONCATENATE_BASE_AND_KEY.clone();
        mechanism.setParameters(parameters);
        session.deriveKey(mechanism, key, anotherKey);
        output_.println("finished");
        output_.flush();
*/

/*
        output_.print("testing PBEParameters... ");
        mechanism = (Mechanism) Mechanism.PBE_SHA1_DES3_EDE_CBC.clone();
        char[] iv = "initiali".toCharArray();
        char[] password = "password".toCharArray();
        char[] salt = "salt".toCharArray();
        long iterations = 1000;
        parameters = new PBEParameters(iv, password, salt, iterations);
        mechanism.setParameters(parameters);
        session.encryptInit(mechanism, key);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing PKCS5PBKD2Parameters... ");
        mechanism = (Mechanism) Mechanism.PBE_SHA1_DES3_EDE_CBC.clone();
        long saltSource = PKCS5PBKD2Parameters.SaltSourceType.SALT_SPECIFIED;
        byte[] saltSourceData = "salt".getBytes("ASCII");
        long iterations = 1000;
        long pseudoRandomFunction = PKCS5PBKD2Parameters.PseudoRandomFunctionType.HMAC_SHA1;
        byte[] pseudoRandomFunctionData = "datadata".getBytes("ASCII");
        parameters = new PKCS5PBKD2Parameters(saltSource, saltSourceData, iterations,
                                              pseudoRandomFunction, pseudoRandomFunctionData);
        mechanism.setParameters(parameters);
        SecretKey secretKeyTemplate = new SecretKey();
        secretKeyTemplate.setObjectHandle(4);
        session.generateKey(mechanism, secretKeyTemplate);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing RC2CbcParameters... ");
        byte[] iv = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        parameters = new RC2CbcParameters(42, iv);
        mechanism = (Mechanism) Mechanism.RC2_CBC_PAD.clone();
        mechanism.setParameters(parameters);
        session.encryptInit(mechanism, key);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing RC2MacGeneralParameters... ");
        parameters = new RC2MacGeneralParameters(128, 16);
        mechanism = (Mechanism) Mechanism.RC2_MAC_GENERAL.clone();
        mechanism.setParameters(parameters);
        session.signInit(mechanism, key);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing RC2Parameters... ");
        parameters = new RC2Parameters(128);
        mechanism = (Mechanism) Mechanism.RC2_MAC.clone();
        mechanism.setParameters(parameters);
        session.signInit(mechanism, key);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing RC5CbcParameters... ");
        byte[] iv = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        parameters = new RC5CbcParameters(4, 16, iv);
        mechanism = (Mechanism) Mechanism.RC5_CBC_PAD.clone();
        mechanism.setParameters(parameters);
        session.encryptInit(mechanism, key);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing RC5MacGeneralParameters... ");
        parameters = new RC5MacGeneralParameters(4, 16, 12);
        mechanism = (Mechanism) Mechanism.RC5_MAC_GENERAL.clone();
        mechanism.setParameters(parameters);
        session.signInit(mechanism, key);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing RC5Parameters... ");
        parameters = new RC5Parameters(4, 16);
        mechanism = (Mechanism) Mechanism.RC5_MAC.clone();
        mechanism.setParameters(parameters);
        session.signInit(mechanism, key);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing RSAPkcsOaepParameters... ");
        Mechanism hashAlgorithm = Mechanism.SHA_1;
        long maskGenerationFunction = RSAPkcsParameters.MessageGenerationFunctionType.SHA1;
        long source = RSAPkcsOaepParameters.SourceType.DATA_SPECIFIED;
        byte[] sourceData = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        parameters = new RSAPkcsOaepParameters(hashAlgorithm, maskGenerationFunction, source, sourceData);
        mechanism = (Mechanism) Mechanism.RC5_MAC.clone();
        mechanism.setParameters(parameters);
        session.encryptInit(mechanism, key);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing SkipJackPrivateWrapParameters... ");
        byte[] password = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        byte[] publicData = { 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F };
        byte[] randomA = { 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        byte[] primeP = { 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F };
        byte[] baseG = { 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27 };
        byte[] subprimeQ = { 0x28, 0x29, 0x2A, 0x2B, 0x2C, 0x2D, 0x2E, 0x2F };
        parameters = new SkipJackPrivateWrapParameters(password, publicData, randomA, primeP, baseG, subprimeQ);
        mechanism = (Mechanism) Mechanism.SKIPJACK_PRIVATE_WRAP.clone();
        mechanism.setParameters(parameters);
        session.wrapKey(mechanism, key, anotherKey);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing SkipJackRelayXParameters... ");
        byte[] oldWrappedX = { 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37 };
        byte[] oldPassword = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        byte[] oldPublicData = { 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F };
        byte[] oldRandomA = { 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        byte[] newPassword = { 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F };
        byte[] newPublicData = { 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27 };
        byte[] newRandomA = { 0x28, 0x29, 0x2A, 0x2B, 0x2C, 0x2D, 0x2E, 0x2F };
        parameters = new SkipJackRelayXParameters(oldWrappedX, oldPassword, oldPublicData, oldRandomA,
                                                  newPassword, newPublicData, newRandomA);
        mechanism = (Mechanism) Mechanism.SKIPJACK_RELAYX.clone();
        mechanism.setParameters(parameters);
        session.wrapKey(mechanism, key, anotherKey);
        output_.println("finished");
        output_.flush();

 */

        // SSL* and Version parameters tested in SSLMechanismsDemo

/*
        output_.print("testing RSAPkcsPssParameters... ");
        Mechanism hashAlgorithm = Mechanism.SHA_1;
        long maskGenerationFunction = RSAPkcsParameters.MessageGenerationFunctionType.SHA1;
        long saltLength = 16;
        parameters = new RSAPkcsPssParameters(hashAlgorithm, maskGenerationFunction, saltLength);
        mechanism = (Mechanism) Mechanism.RSA_PKCS_PSS.clone();
        mechanism.setParameters(parameters);
        session.signInit(mechanism, key);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing EcDH1KeyDerivationParameters... ");
        long keyDerivationFunction = DHKeyDerivationParameters.KeyDerivationFunctionType.SHA1_KDF;
        byte[] sharedData = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        byte[] publicData = { 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F };
        parameters = new EcDH1KeyDerivationParameters(keyDerivationFunction, sharedData, publicData);
        mechanism = (Mechanism) Mechanism.ECDH1_DERIVE.clone();
        mechanism.setParameters(parameters);
        session.deriveKey(mechanism, key, anotherKey);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing EcDH2KeyDerivationParameters... ");
        long keyDerivationFunction = DHKeyDerivationParameters.KeyDerivationFunctionType.SHA1_KDF;
        byte[] sharedData = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        byte[] publicData = { 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F };
        byte[] publicData2 = { 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        long privateDataLength = 16;
        Key privateData = new Key();
        privateData.setObjectHandle(3);
        parameters = new EcDH2KeyDerivationParameters(keyDerivationFunction, sharedData, publicData,
                                                      privateDataLength, privateData, publicData2);
        mechanism = (Mechanism) Mechanism.ECMQV_DERIVE.clone();
        mechanism.setParameters(parameters);
        session.deriveKey(mechanism, key, anotherKey);
        output_.println("finished");
        output_.flush();
 */

/*
        output_.print("testing X942DH1KeyDerivationParameters... ");
        long keyDerivationFunction = DHKeyDerivationParameters.KeyDerivationFunctionType.SHA1_KDF_ASN1;
        byte[] otherInfo = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        byte[] publicData = { 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F };
        parameters = new X942DH1KeyDerivationParameters(keyDerivationFunction, otherInfo, publicData);
        mechanism = (Mechanism) Mechanism.X9_42_DH_DERIVE.clone();
        mechanism.setParameters(parameters);
        session.deriveKey(mechanism, key, anotherKey);
        output_.println("finished");
        output_.flush();
 */

        output_.print("testing X942DH2KeyDerivationParameters... ");
        long keyDerivationFunction = DHKeyDerivationParameters.KeyDerivationFunctionType.SHA1_KDF_ASN1;
        byte[] otherInfo = { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
        byte[] publicData = { 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F };
        byte[] publicData2 = { 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        long privateDataLength = 16;
        Key privateData = new Key();
        privateData.setObjectHandle(3);
        parameters = new X942DH2KeyDerivationParameters(keyDerivationFunction, otherInfo, publicData,
                                                        privateDataLength, privateData, publicData2);
        mechanism = (Mechanism) Mechanism.X9_42_DH_HYBRID_DERIVE.clone();
        mechanism.setParameters(parameters);
        session.deriveKey(mechanism, key, anotherKey);
        output_.println("finished");
        output_.flush();

        output_.println("################################################################################");
        session.closeSession();
        pkcs11Module.finalize(null);

    } catch (Throwable thr) {
      thr.printStackTrace();
    }
    output_.println("press any key to continue...");
    try {
      input_.read();
    } catch (Throwable thr) {
      thr.printStackTrace();
    }
  }

  public static void printUsage() {
    output_.println("Usage: ParametersTest <PKCS#11 module>");
    output_.println(" e.g.: ParametersTest pk2priv.dll");
    output_.println("The given DLL must be in the search path of the system.");
  }

}