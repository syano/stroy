package org.openCage.lang;

import org.openCage.lang.functions.F1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/***** BEGIN LICENSE BLOCK *****
 * New BSD License
 * Copyright (c) 2006 - 2010, Stephan Pfab
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Stephan Pfab nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
***** END LICENSE BLOCK *****/

public class Strings {

    private Strings() {}

    /**
     * Make the first character lower case
     * @param name Any String
     * @return A String
     */
    public static String toFirstLower( String name ) {
        return "" + name.toLowerCase().charAt(0) + name.substring(1);
    }

    /**
     * Make the first character upper case
     * @param name Any String
     * @return A String
     */
    public static String toFirstUpper(String name) {
        return "" + name.toUpperCase().charAt(0) + name.substring(1);
    }


    /**
     * Joins the elements of an iterable to a string
     * @param list A iterable, e.g. List, Set ...
     * @param <T> The element type
     * @return A StringJoiner with various options
     */
    public static <T> StringJoiner<T> join( Iterable<T> list ) {
        return new StringJoiner<T>( list );
    }

    /**
     * Joins the elements of an array to a string
     * @param list An array
     * @param <T> The element type
     * @return A StringJoiner with various options
     */
    public static <T> StringJoiner<T> join( T[] list ) {
        return join( Arrays.asList( list ));
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static class StringJoiner<T> {

        private final Iterable<T> list;
        private String sep = ", ";
        private String prefix = "";
        private F1<String,T> trans;
        private boolean comma = false;
        private F1<Boolean,T> filter;
        private String postfix = "";
        private boolean order = false;

        public StringJoiner(Iterable<T> list) {
            this.list = list;
        }

        /**
         * builds the String
         * @return The result String
         */
        public String toString() {
            if ( list == null || !list.iterator().hasNext() ) {
                return "";
            }

            if ( order ) {
                return toOrderedString();

            } else {

                return toUnorderedString();
            }
        }

        private String toUnorderedString() {
            StringBuffer buf = new StringBuffer( prefix );

            boolean one = false;

            for ( T str : list ) {

                if ( filter != null && filter.call( str )) {
                    continue;
                }

                one = true;

                if ( comma ) {
                    buf.append( sep );
                }
                comma = true;

                if ( trans == null ) {
                    buf.append( str );
                } else {
                    buf.append( trans.call( str ));
                }

            }

            if ( !one ) {
                return "";
            }

            return buf.toString() + postfix;
        }

        private String toOrderedString() {
            List<String> intermediate = new ArrayList<String>();

            for ( T str : list ) {

                if ( filter != null && filter.call( str )) {
                    continue;
                }

                if ( trans == null ) {
                    intermediate.add( str.toString() );
                } else {
                    intermediate.add( trans.call( str ));
                }
            }

            Collections.sort( intermediate );

            StringJoiner<String> sj = Strings.join( intermediate );
            sj.sep = sep;
            sj.prefix = prefix;
            sj.postfix = postfix;

            return sj.toString() ;
        }

        /**
         * Add a prefix to the result
         * bur only if there is a non null result
         * @param prefix The prefix String
         * @return
         */
        public StringJoiner prefix( String prefix ) {
            this.prefix = prefix;
            return this;
        }

        /**
         * Appends a postfix to the result
         * bur only if there is a non null result
         * @param postfix The postfix String
         * @return Itself (fluent style)
         */
        public StringJoiner postfix( String postfix ) {
            this.postfix = postfix;
            return this;
        }

        /**
         * overrides the default separator ", "
         * @param sep The new separator
         * @return Itself (fluent style)
         */
        public StringJoiner separator( String sep ) {
            this.sep = sep;
            return this;
        }

        /**
         * Translate each element
         * @param trans A function to translate an Collection Element to a String
         * @return Itself (fluent style)
         */
        public StringJoiner trans( F1<String,T> trans ) {
            this.trans = trans;
            return this;
        }

        /**
         * Wether the join starts with the separator or not (not is default)
         * @param start
         * @return Itself (fluent style)
         */
        public StringJoiner startWithSeparator( boolean start ) {
            comma = start;
            return this;
        }

        /**
         * A predicate to define which elements to skip
         * @param filter a predicate function
         * @return Itself (fluent style)
         */
        public StringJoiner skip( F1<Boolean, T> filter ) {
            this.filter = filter;
            return this;
        }

        /**
         * Requires an alphabetic ordering of the elements (after trans)
         * @return Itself (fluent style)
         */
        public StringJoiner order() {
            this.order = true;
            return this;
        }

    }

}
