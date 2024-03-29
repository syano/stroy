package org.openCage.utils.lang;

import org.junit.Test;
import org.openCage.lang.protocol.F1;
import static junit.framework.Assert.assertEquals;

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
* Portions created by Stephan Pfab are Copyright (C) 2006 - 2009.
* All Rights Reserved.
*
* Contributor(s):
***** END LICENSE BLOCK *****/

public class RefTest {


    @Test
    public void testChangable() {
        Ref<Integer> i = Ref.c( 5 );

        new F1<Void, Ref<Integer>>() {
            public Void call( Ref<Integer> integerRef ) {
                integerRef.o = 7;
                return null;
            }

        }.call( i );

        assertEquals( 7, i.o.intValue());
    }
}
