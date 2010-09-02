package org.openCage.generj;

import org.openCage.lang.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * The Original Code is stroy code
 * <p/>
 * The Initial Developer of the Original Code is Stephan Pfab <openCage@gmail.com>.
 * Portions created by Stephan Pfab are Copyright (C) 2006 - 2010.
 * All Rights Reserved.
 * <p/>
 * Contributor(s):
 * **** END LICENSE BLOCK ****
*/
public class Call implements Expr {
    private Callble name;
    private List<Expr> args = new ArrayList<Expr>();

    public Call( Callble name, Expr ... args ) {
        this.name = name;
        this.args.addAll( Arrays.asList( args ));
    }

    public String toString() {
        if ( args.isEmpty() ) {
            return name + "()";
        }
        
        return name + "( " + Strings.join( args ) + " )";
    }

    public static Call CALL( Callble name, Expr ... args ) {
        return new Call( name, args );
    }

    @Override
    public String toString(String prefix) {
        return prefix + toString();
    }
}
