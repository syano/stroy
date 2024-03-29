package org.openCage.stroy.cli;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.cli.*;
import org.openCage.util.lang.Once;
import org.openCage.stroy.RuntimeModule;
import org.openCage.stroy.graph.matching.TreeMatchingTask;
import org.openCage.stroy.dir.FileContent;
import org.openCage.stroy.filter.Ignore;
import org.openCage.stroy.dir.doubles2.CompareDirs2;
import org.openCage.util.logging.Log;

import java.io.File;
import java.util.logging.Level;


/***** BEGIN LICENSE BLOCK *****
* Version: MPL 1.1/GPL 2.0
*
* The contents of this file are subject to the Mozilla Public License Version
* 1.1 (the "License"); you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
* http://www.mozilla.org/MPL/
*
* Software distributed under the License is distributed on an "AS IS" basis,
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
* for the specific language governing rights and limitations under the
* License.
*
* The Original Code is stroy code.
*
* The Initial Developer of the Original Code is Stephan Pfab <openCage@gmail.com>.
* Portions created by Stephan Pfab are Copyright (C) 2006 - 2009.
* All Rights Reserved.
*
* Contributor(s):
*
* Alternatively, the contents of this file may be used under the terms of
* either the GNU General Public License Version 2 or later (the "GPL"),
* in which case the provisions of the GPL are applicable instead
* of those above. If you wish to allow use of your version of this file only
* under the terms of either the GPL, and not to allow others to
* use your version of this file under the terms of the MPL, indicate your
* decision by deleting the provisions above and replace them with the notice
* and other provisions required by the GPL. If you do not delete
* the provisions above, a recipient may use your version of this file under
* the terms of any one of the MPL, the GPL.
*
***** END LICENSE BLOCK *****/

/**
 * a cli main
 */
public class Main {

    private final Options           optionsPublic;
    private final Options           options;
    private final Once<CommandLine> line;


    public static void main(String[] args) {

        Main main = new Main();

        main.createOptions();
        main.parseArgs( args );
        main.eval();

    }

    public Main() {

        options       = new Options();
        optionsPublic = new Options();
        line          = new Once<CommandLine>();
    }

    private void eval() {
        if ( line.get().hasOption( "dh" ) ) {
            usageReal();
            return;
        }

        if ( line.get().hasOption( "h" ) || line.get().getArgs().length != 2 ) {
            usage();
            return;
        }


        if ( line.get().hasOption( "loglevel")) {
            setLogLevel( line.get().getOptionValue( "loglevel" ));
        }

        if ( line.get().hasOption( "s")) {
            setLogLevel( "0" );            
        }

        String pattern = null;
        if ( line.get().hasOption( "ignore" )) {
            pattern = line.get().getOptionValue( "ignore" );
        }


        File from = new File( line.get().getArgs()[0] );
        File to   = new File( line.get().getArgs()[1] );

        if ( ! from.exists() ) {
            System.err.println("does not exits: " + from.getPath() );
            usage();
            return;
        }

        if ( ! to.exists() ) {
            System.err.println("does not exits: " + to.getPath() );
            usage();
            return;
        }

        if ( from.isDirectory() ) {
            if ( to.isDirectory() ) {
                Injector injector = Guice.createInjector( new RuntimeModule() );

                // FIRST GOOD
//                CompareDirs cd = injector.getInstance( CompareDirsImpl.class );
//
//                System.out.println(pattern);
//                if ( pattern != null ) {
//                    cd.addIgnore( pattern );
//                }
//                cd.go( from.getAbsolutePath(), to.getAbsolutePath());


                CompareDirs2 cd     = injector.getInstance(CompareDirs2.class );
                Ignore       ignore = injector.getInstance( Ignore.class );

//                IgnoreHelper.addStd( ignore );
//                if ( pattern != null ) {
//                    ignore.add( pattern );
//                }

                TreeMatchingTask<FileContent> matching = cd.compare( ignore, from, to );
                matching.status();


                return;
            } else {
                throw new Error( "not impl" );
            }
        } else if ( to.isDirectory() ) {
            throw new Error( "not impl" );
        } else {
//            new CompareFiles( from.getAbsolutePath(), to.getAbsolutePath()).go();
            return;
        }

    }

    private void usage() {
        // automatically generate the help statement
        HelpFormatter formatter = new HelpFormatter();
        //formatter.printUsage();
        formatter.printHelp( "stroy [] (file|dir) (file|dir)\nstroy compares 2 files/dirs.\nIt finds files which print changed, renamed and moved",
                optionsPublic );
    }

    private void usageReal() {
        // automatically generate the help statement
        HelpFormatter formatter = new HelpFormatter();
        //formatter.printUsage();
        formatter.printHelp( "stroy [] (file|dir) (file|dir)\nstroy compares 2 files/dirs.\nIt finds files which print changed, renamed and moved",
                options );
    }

    private void parseArgs(String[] args) {

        CommandLineParser parser = new GnuParser();
        try {
            line.set( parser.parse( options, args ));
        }
        catch( ParseException exp ) {
            // oops, something went wrong
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
        }

    }

    private void createOptions() {

        options.addOption( "h", "org.openCage.localization.dict.help", false, "show this message" );
        optionsPublic.addOption( "h", "org.openCage.localization.dict.help", false, "show this message" );

        Option loglevel   = OptionBuilder.withArgName( "loglevel" )
                .hasArg()
                .withDescription(  "set the loglevel" )
                .create( "loglevel" );
//
//        OptionBuilder.withLongOpt( "block-size" )
//                                .withDescription( "use SIZE-byte blocks" )
//                                .withValueSeparator( '=' )
//                                .hasArg()
//                                .create() );

        options.addOption( loglevel );

        options.addOption( "dbg", "debug", false, "print debugging info" );
        options.addOption( "dh", "debugHelp", false, "show this message" );

        options.addOption( "s", "siltent", false, "no messages" );
        optionsPublic.addOption( "s", "siltent", false, "no messages" );

        Option filter   = OptionBuilder.withArgName( "ignore" )
                .hasArg()
                .withDescription(  "files matching this pattern are ignored" )
                .create( "ignore" );

        options.addOption( filter );
        optionsPublic.addOption( filter );

    }


    private void setLogLevel( String levelStr ) {

        Log.setLevel( Level.parse( levelStr ));
    }



}
