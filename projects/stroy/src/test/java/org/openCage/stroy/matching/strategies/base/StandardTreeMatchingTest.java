//package org.openCage.stroy.matching.strategies.base;
//
//import junit.framework.TestCase;
//import org.openCage.stroy.algo.tree.str.StringNoedBuilder;
//import org.openCage.stroy.algo.tree.Noed;
//import org.openCage.stroy.algo.matching.TreeTask;
//import org.openCage.stroy.algo.matching.Tasks;
//import org.openCage.stroy.algo.matching.strategies.base.StandardTreeMatching;
//import org.openCage.stroy.matching.TreeTaskImpl;
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
//public class StandardTreeMatchingTest extends TestCase {
//
//    public void testSame() {
//        StringNoedBuilder b = new StringNoedBuilder();
//
//        Noed left = b.d( "foo", b.l("a", "t1", "aaa"),
//                                b.l("b", "t1", "bbb"));
//
//        Noed right = b.d( "foo", b.l("a", "t1", "aaa"),
//                                b.l("b", "t1", "bbb"));
//
//
//        final TreeTask tt = new TreeTaskImpl( left, right );
//
//        new StandardTreeMatching().match( tt );
//
//        assertEquals( 0, tt.getLeft( Tasks.isUnmatched ).size());
//
//        assertEquals( 0, tt.getRight( Tasks.isUnmatched ).size());
//
//    }
//
//    public void testOneMore() {
//        StringNoedBuilder b = new StringNoedBuilder();
//
//        Noed left = b.d( "foo", b.l("a", "t1", "aaa"),
//                                b.l("b", "t1", "bbb"));
//
//        Noed right = b.d( "foo", b.l("a", "t1", "aaa"),
//                                b.l("b", "t1", "bbb"),
//                                b.l( "c", "t1", "ccc"));
//
//
//        final TreeTask tt = new TreeTaskImpl( left, right );
//
//        new StandardTreeMatching().match( tt );
//
//        assertEquals( 0, tt.getLeft( Tasks.isUnmatched ).size());
//
//        assertEquals( 1, tt.getRight( Tasks.isUnmatched ).size());
//    }
//
//    public void testMove() {
//        StringNoedBuilder b = new StringNoedBuilder();
//
//        Noed left = b.d( "foo", b.l("a", "t1", "aaa"),
//                                b.l("b", "t1", "bbb"));
//
//        Noed right = b.d( "foo", b.l("a", "t1", "aaa"),
//                                 b.d( "extra",
//                                    b.l("b", "t1", "bbb")));
//
//
//
//        final TreeTask tt = new TreeTaskImpl( left, right );
//
//        new StandardTreeMatching().match( tt );
//
//        assertEquals( 1, tt.getLeft( Tasks.isUnmatched ).size());
//        assertEquals( 2, tt.getRight( Tasks.isUnmatched ).size());
//    }
//}
