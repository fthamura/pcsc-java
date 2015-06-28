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
import java.util.Hashtable;

import iaik.pkcs.pkcs11.Info;
import iaik.pkcs.pkcs11.Module;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.SessionInfo;
import iaik.pkcs.pkcs11.Slot;
import iaik.pkcs.pkcs11.State;
import iaik.pkcs.pkcs11.Token;
import iaik.pkcs.pkcs11.TokenInfo;
import iaik.pkcs.pkcs11.objects.Object;



/**
 * This demo program allows to delete certain objects on a certain token. It
 * allows the user to select a token. Thereafter, it displays the objects on
 * that token and lets the user select one of them to delete it.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class DeleteObject {

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
    if (args.length == 1) {
      try {
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
        Hashtable tokenIDtoToken = new Hashtable(tokens.length);

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

        Token selectedToken = null;
        Long selectedTokenID = null;
        if (tokens.length == 0) {
          output_.println("There is no slot with a present token.");
          System.exit(0);
        } else if (tokens.length == 1) {
          output_.println("Taking token with ID: " + tokens[0].getTokenID());
          selectedTokenID = new Long(tokens[0].getTokenID());
          selectedToken = tokens[0];
        } else {
          boolean gotTokenID = false;
          while (!gotTokenID) {
            output_.print("Enter the ID of the token to use or 'x' to exit: ");
            output_.flush();
            String tokenIDstring = input_.readLine();
            if (tokenIDstring.equalsIgnoreCase("x")) {
              System.exit(0);
            }
            try {
              selectedTokenID = new Long(tokenIDstring);
              selectedToken = (Token) tokenIDtoToken.get(selectedTokenID);
              if (selectedToken != null) {
                gotTokenID = true;
              } else {
                output_.println("A token with the entered ID \"" + tokenIDstring + "\" does not exist. Try again.");
              }
            } catch (NumberFormatException ex) {
              output_.println("The entered ID \"" + tokenIDstring + "\" is invalid. Try again.");
            }
          }
        }

        output_.println("################################################################################");
        Session session = selectedToken
            .openSession(Token.SessionType.SERIAL_SESSION, Token.SessionReadWriteBehavior.RW_SESSION, null, null);

        TokenInfo tokenInfo = selectedToken.getTokenInfo();
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
        output_.println("using session:");
        output_.println(sessionInfo);

        output_.println("listing all" +
                          (((sessionInfo.getState() == State.RO_USER_FUNCTIONS) || (sessionInfo.getState() == State.RW_SO_FUNCTIONS))
                           ? ""
                           : " public")
                        + " objects on token with ID " + selectedTokenID);
        output_.println("________________________________________________________________________________");

        deleteLoop:
        while (true) {
          session.findObjectsInit(null);
          Object[] objects = session.findObjects(1);
          Hashtable objectHandleToObject = new Hashtable(10);

          while (objects.length > 0) {
            output_.println("--------------------------------------------------------------------------------");
            long objectHandle = objects[0].getObjectHandle();
            objectHandleToObject.put(new Long(objectHandle), objects[0]);
            output_.println("Object with handle: " + objectHandle);
            output_.println(objects[0]);
            output_.println("--------------------------------------------------------------------------------");
            objects = session.findObjects(1);
          }
          session.findObjectsFinal();

          output_.println("________________________________________________________________________________");
          output_.println("################################################################################");

          Object selectedObject = null;
          Long selectedObjectHandle;
          if (objectHandleToObject.isEmpty()) {
            output_.println("There are no objects on the token.");
            break deleteLoop;
          } else {
            boolean gotObjectHandle = false;
            while (!gotObjectHandle) {
              output_.print("Enter the handle of the object to delete or 'x' to exit: ");
              output_.flush();
              String objectHandleString = input_.readLine();
              if (objectHandleString.equalsIgnoreCase("x")) {
                break deleteLoop;
              }
              try {
                selectedObjectHandle = new Long(objectHandleString);
                selectedObject = (Object) objectHandleToObject.get(selectedObjectHandle);
                if (selectedObject != null) {
                  gotObjectHandle = true;
                } else {
                  output_.println("An object with the handle \"" + objectHandleString + "\" does not exist. Try again.");
                }
              } catch (NumberFormatException ex) {
                output_.println("The entered handle \"" + objectHandleString + "\" is invalid. Try again.");
              }
            }
          }

          output_.println("Going to delete this object: ");
          output_.println(selectedObject);
          output_.println();
          output_.print("Are you sure that you want to DELETE this object permanently? [yes/no] ");
          output_.flush();
          String answer = input_.readLine();
          if (answer.equalsIgnoreCase("yes")) {
            session.destroyObject(selectedObject);
            output_.println("Object deleted.");
          }
        }

        session.closeSession();
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
    output_.println("DeleteObject <PKCS#11 module name>");
    output_.println("e.g.: DeleteObject pk2priv.dll");
  }

}