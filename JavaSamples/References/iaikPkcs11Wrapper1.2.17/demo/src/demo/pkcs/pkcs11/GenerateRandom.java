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

import java.io.FileOutputStream;

import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.Token;



/**
 * This demo program uses a PKCS#11 module to produce random data.
 * Optionally the random data can be written to file.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class GenerateRandom {

  public static void main(String[] args) {
    if ((args.length != 2) && (args.length != 3)) {
      printUsage();
      System.exit(1);
    }
    int numberOfBytes = -1;
    try {
      numberOfBytes = Integer.parseInt(args[1]);
    } catch (Exception ex) {
      printUsage();
      System.exit(1);
    }

    try {

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

        System.out.println("################################################################################");
        System.out.print("generating " + numberOfBytes + " bytes of random data... ");

        byte[] dataBuffer = session.generateRandom(numberOfBytes);

        System.out.println("finished");
        System.out.println("################################################################################");


        if (args.length == 3) {
          System.out.println("################################################################################");
          System.out.println("writing random data to file : " + args[2]);

          FileOutputStream dataOutput = new FileOutputStream(args[2]);
          dataOutput.write(dataBuffer);
          dataOutput.flush();
          dataOutput.close();
          System.out.println("################################################################################");
        }

        session.closeSession();
        pkcs11Module.finalize(null);

    } catch (Throwable thr) {
      thr.printStackTrace();
    }
  }

  public static void printUsage() {
    System.out.println("Usage: GenerateRandom <PKCS#11 module> <number of bytes> [<output file>]");
    System.out.println(" e.g.: GenerateRandom pk2priv.dll 128 random.dat");
    System.out.println("The given DLL must be in the search path of the system.");
  }

}