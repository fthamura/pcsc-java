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
import java.util.Arrays;
import java.util.List;

import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenInfo;
import iaik.pkcs.pkcs11.objects.GenericSecretKey;


/**
 * This demo program shows how to generate secret keys.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class GenerateKey {

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
    if ((args.length != 1) && (args.length != 2)) {
      printUsage();
      System.exit(1);
    }

    try {

      //Security.addProvider(new IAIK());

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

      List supportedMechanisms = Arrays.asList(token.getMechanismList());

      Session session =
          token.openSession(Token.SessionType.SERIAL_SESSION, Token.SessionReadWriteBehavior.RW_SESSION, null, null);

      // if we have to user PIN login user
      if (args.length == 2) {
        session.login(Session.UserType.USER, args[1].toCharArray());
      }

      if (supportedMechanisms.contains(Mechanism.GENERIC_SECRET_KEY_GEN)) {
        output_.println("################################################################################");
        output_.println("Generating generic secret key");

        Mechanism keyGenerationMechanism = (Mechanism) Mechanism.GENERIC_SECRET_KEY_GEN.clone();

        GenericSecretKey secretKeyTemplate = new GenericSecretKey();
        secretKeyTemplate.getValueLen().setLongValue(new Long(16));

        GenericSecretKey secretKey =
            (GenericSecretKey) session.generateKey(keyGenerationMechanism, secretKeyTemplate);

        output_.println("the secret key is");
        output_.println(secretKey.toString());

        output_.println("################################################################################");
      }
      session.closeSession();
      pkcs11Module.finalize(null);

    } catch (Throwable thr) {
      thr.printStackTrace();
    }
  }

  public static void printUsage() {
    output_.println("Usage: GenerateKey <PKCS#11 module> [<user-PIN>]");
    output_.println(" e.g.: GenerateKey cryptoki.dll");
    output_.println("The given DLL must be in the search path of the system.");
  }

}