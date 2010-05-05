package org.openCage.lang.structure;

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
* Portions created by Stephan Pfab are Copyright (C) 2006 - 2010.
* All Rights Reserved.
*
* Contributor(s):
***** END LICENSE BLOCK *****/
public class T2<A,B> {

    public final A i0;
    public final B i1;

    public T2( final A a, final B b ) {
        this.i0 = a;
        this.i1 = b;
    }

    public static <A,B> T2<A,B> c( A a, B b ) {
        return new T2<A,B>(a,b);
    }

    public static <A,B> T2<A,B> l( A a, Class<B> clazz ) {
        return new T2<A,B>(a,null);
    }

    public static <A,B> T2<A,B> r( Class<A> clazz, B b ) {
        return new T2<A,B>( null, b);
    }
}