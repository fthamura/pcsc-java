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

import iaik.pkcs.pkcs11.Info;
import iaik.pkcs.pkcs11.Module;



/**
 * This demo program lists information about a module.
 *
 * @author <a href="mailto:Karl.Scheibelhofer@iaik.at"> Karl Scheibelhofer </a>
 * @version 0.1
 * @invariants
 */
public class ModuleInfo {

  public static void main(String[] args) {
    if (args.length == 1){
      try {
        Module pkcs11Module = Module.getInstance(args[0]);

        System.out.print("initializing module...");
        pkcs11Module.initialize(null);
        System.out.println("finished");

        System.out.print("getting module info...");
        Info info = pkcs11Module.getInfo();
        System.out.println("finished");
        System.out.println("module info is:");
        System.out.println(info);

        pkcs11Module.finalize(null);
      } catch (Throwable ex) {
        ex.printStackTrace();
      }
    } else {
      printUsage();
      System.exit(1);
    }
  }

  protected static void printUsage() {
    System.out.println("ModuleInfo <PKCS#11 module name>");
    System.out.println("e.g.: ModuleInfo pk2priv.dll");
  }

}