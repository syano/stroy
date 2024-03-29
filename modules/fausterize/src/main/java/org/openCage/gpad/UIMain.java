package org.openCage.gpad;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * ** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 * <p/>
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * <p/>
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * <p/>
 * The Original Code is stroy code.
 * <p/>
 * The Initial Developer of the Original Code is Stephan Pfab <openCage@gmail.com>.
 * Portions created by Stephan Pfab are Copyright (C) 2006 - 2010.
 * All Rights Reserved.
 * <p/>
 * Contributor(s):
 * **** END LICENSE BLOCK ****
 */

/**
 * Main class for fausterize
 * TODO finish cli
 */
public class UIMain {


    public static void main(String[] args) {
        //Stage.
        Injector injector = Guice.createInjector( Stage.DEVELOPMENT, new FausterizeWiring());

        CliOptions bean = new CliOptions();
        CmdLineParser parser = new CmdLineParser(bean);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar myprogram.jar [options...] arguments...");
            parser.printUsage(System.err);
            return;
        }

        if ( bean.getFilepath() == null ) {

            // ui case
            FaustUI faust = injector.getInstance(FaustUI.class);
            faust.setVisible(true);
        } else {

            // cli case
            System.out.println("++++ " + bean.getFilepath());
        }
        

    }
}
