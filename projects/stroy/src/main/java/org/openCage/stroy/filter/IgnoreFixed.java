//package org.openCage.stroy.filter;
//
//import java.util.List;
//import java.util.regex.Pattern;
//
///***** BEGIN LICENSE BLOCK *****
// * BSD License (2 clause)
// * Copyright (c) 2006 - 2012, Stephan Pfab
// * All rights reserved.
// *
// * Redistribution and use in source and binary forms, with or without
// * modification, are permitted provided that the following conditions are met:
// *     * Redistributions of source code must retain the above copyright
// *       notice, this list of conditions and the following disclaimer.
// *     * Redistributions in binary form must reproduce the above copyright
// *       notice, this list of conditions and the following disclaimer in the
// *       documentation and/or other materials provided with the distribution.
// *
// * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// * DISCLAIMED. IN NO EVENT SHALL Stephan Pfab BE LIABLE FOR ANY
// * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//***** END LICENSE BLOCK *****/
//
//public class IgnoreFixed implements Ignore {
//
//    private final Pattern pattern;
//
//    public IgnoreFixed( IgnoreStore store ) {
//        String str = "";
//
//         if ( store.extensions != null ) {
//             for ( String ext : store.extensions ) {
//                 if ( str.length() > 0 ) {
//                     str += "|";
//                 }
//
//                 str += "(.*\\." + ext + ")";
//             }
//         }
//
////        pattern = Pattern.compile( str );
//
//         if ( store.paths != null ) {
//             for ( String path : store.paths ) {
//                 if ( str.length() > 0 ) {
//                     str += "|";
//                 }
//
//                 str += "(" + path + ")";
//             }
//         }
//
//         if ( store.patterns != null ) {
//             for ( String pat : store.patterns) {
//                 if ( str.length() > 0 ) {
//                     str += "|";
//                 }
//
//                 str += "(" + pat + ")";
//             }
//         }
//
//          pattern = Pattern.compile( str );
//    }
//
//    public boolean match(String path) {
//
//        return pattern.matcher( path ).matches();
//    }
//
//    public void setExtensions(List<String> extensions) {}
//
//    public void setPatterns(List<String> patterns) {}
//
//    public void setPaths(List<String> paths) {}
//}
