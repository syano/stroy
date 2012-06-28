package org.openCage.stroy.graph.matching.strategy;

import org.openCage.stroy.content.ReducedContent;

/***** BEGIN LICENSE BLOCK *****
 * BSD License (2 clause)
 * Copyright (c) 2006 - 2012, Stephan Pfab
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Stephan Pfab BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
***** END LICENSE BLOCK *****/

public class IdenticalLeavesMatchStrategyTest extends StrategyTestsWorker implements StrategyTests {

    public void testSimpleIdentical() {
        strategyTestSimpleIdentical(
                new IdenticalLeafMatchStrategy<ReducedContent>(),
                3,0,3,0, 1,1,1,1);
    }

    public void testRootMatch() {
        strategyTestRootMatch(
                new IdenticalLeafMatchStrategy<ReducedContent>());
    }


    public void testMoved() {
        strategyTestMoved(
                new IdenticalLeafMatchStrategy<ReducedContent>(),
                3,0,3,0, 1,1,1,2);
    }

    public void testIgnoreChecksum() {
        // the strategy uses the fingerprint so it can't match here
        strategyTestIgnoreChecksum(
                new IdenticalLeafMatchStrategy<ReducedContent>(),
                0,3,0,3, 1,1,1,1);
    }

//        assertEquals( 1.0, task.getLeaves().getMatchQuality( (TreeLeafNode<ReducedContent>)node ));
//    }
}

