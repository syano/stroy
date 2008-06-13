package org.openCage.stroy.filter;

import java.util.ArrayList;
import java.util.List;

/***** BEGIN LICENSE BLOCK *****
* Version: MPL 1.1
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
* Portions created by Stephan Pfab are Copyright (C) 2006, 2007, 2008.
* All Rights Reserved.
*
* Contributor(s):
***** END LICENSE BLOCK *****/
public class IgnoreHelper {

//    public static void addStd( Ignore ignore ) {
//        ignore.add( "\\.svn" );
//        ignore.add( "\\.DS_Store" );
//        ignore.add( "\\.copyarea.db" );
//        ignore.add( "\\.copyarea.dat" );
//        ignore.add( ".*\\.class" );
//        ignore.add( ".*\\.o" );
//        ignore.add( ".*\\.obj" );
//        ignore.add( ".*\\.get_date\\.dat" );
//    }

    public static ArrayList<String> getStdExtensions() {
        ArrayList<String> exts = new ArrayList<String>();

        exts.add( "svn" );
        exts.add( "DS_Store" );
        exts.add( "class" );
        exts.add( "o" );
        exts.add( "obj" );


//        CVS;SCCS;RCS;rcs;.DS_Store;.svn;vssver.scc;vssver2.scc;.sbas;.IJI.*;
        
        return exts;
    }

    public static List<String> getStdPatterns() {
        ArrayList<String> pat = new ArrayList<String>();
        pat.add( ".*/get_date\\.dat" );
        pat.add( ".*/copyarea\\.db" );
        pat.add( ".*/copyarea\\.dat" );
        pat.add( ".*/vssver\\.scc" );
        pat.add( ".*/vssver2\\.scc" );
        pat.add( ".*/CVS" );
        pat.add( ".*/SCCS" );
        pat.add( ".*/RCS" );
        pat.add( ".*/rcs" );

//        CVS;SCCS;RCS;rcs;.DS_Store;.svn;vssver.scc;vssver2.scc;.sbas;.IJI.*;

        return pat;
    }
}
