package org.openCage.stroy.graph.matching.strategy;

import org.openCage.stroy.content.Content;
import org.openCage.stroy.graph.SameContent;
import org.openCage.stroy.graph.node.TreeLeafNode;
import org.openCage.stroy.graph.matching.TreeMatchingTask;
import org.openCage.stroy.graph.matching.strategy.MatchStrategy;
import org.openCage.stroy.locale.Message;
import org.openCage.util.logging.Log;

import java.util.HashMap;
import java.util.Map;

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

public class IdenticalLeafMatchStrategy<T extends Content> implements MatchStrategy<T> {

    public void match(TreeMatchingTask<T> treeMatchingTask, Reporter reporter) {

        Log.fine( "match Identical" );
        reporter.title( Message.get( "Strategy.IdenticalLeaf" ));

        if ( !treeMatchingTask.isMatched( treeMatchingTask.getLeftRoot())) {
            treeMatchingTask.getDirs().match( treeMatchingTask.getLeftRoot(),
                                              treeMatchingTask.getRightRoot(), 1.0 );
        }
        

        Map<String, SameContent<T>> sames = computeHashes(treeMatchingTask, reporter);
        matchSames(treeMatchingTask, sames );

    }

    private void matchSames( TreeMatchingTask<T> treeMatchingTask, Map<String, SameContent<T>> sames) {

        for ( SameContent<T> sh : sames.values() ) {

            if ( !sh.isSingle() ) {
               treeMatchingTask.addDup( sh );
            } else if ( sh.isOk() ) {
                treeMatchingTask.getLeaves().match( sh.getSingleSrc(), sh.getSingleTgt(), 1.0 );
            } else if ( sh.isSingleNew() ) {
            } else if ( sh.isSingleOld() )  {
            } else {
                throw new Error( "huh" );
            }
        }
    }


    private Map<String, SameContent<T>> computeHashes( TreeMatchingTask<T> matchingTask, Reporter reporter) {
        Map<String, SameContent<T>> sames = new HashMap<String, SameContent<T>>();

        for ( TreeLeafNode<T> lfm : matchingTask.getLeaves().getUnmatchedLeft() ) {

            reporter.detail( Message.get( "Progress.checking" ), lfm.toString() );
            
            String checksum = lfm.getContent().getChecksum();

            SameContent<T> sh;

            if ( sames.containsKey( checksum )) {
                sh = sames.get( checksum );
            } else {
                sh = new SameContent<T>();
                sames.put( checksum, sh );
            }

            sh.add( lfm, true );
        }

        for ( TreeLeafNode<T> lfm : matchingTask.getLeaves().getUnmatchedRight() ) {

            reporter.detail( Message.get( "Progress.checking" ), lfm.toString() );

            String checksum = lfm.getContent().getChecksum();

            SameContent<T> sh;

            if ( sames.containsKey( checksum )) {
                sh = sames.get( checksum );
            } else {
                sh = new SameContent<T>();
                sames.put( checksum, sh );
            }

            sh.add( lfm, false );
        }

        return sames;
    }


}
