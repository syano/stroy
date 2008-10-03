package org.openCage.stroy.graph.matching.strategy;

import org.openCage.stroy.content.Content;
import org.openCage.stroy.graph.node.TreeDirNode;
import org.openCage.stroy.graph.node.TreeNode;
import org.openCage.stroy.graph.matching.TreeMatchingTask;
import org.openCage.stroy.graph.matching.strategy.MatchStrategy;
import org.openCage.stroy.locale.Message;
import org.openCage.util.logging.Log;

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

/**
 * match dirs based on name and same path
 */
public class SimpleDirMatching<T extends Content> implements MatchStrategy<T> {

    public void match(TreeMatchingTask<T> treeMatchingTask, Reporter reporter) {
        Log.fine( "match Simple dirs" );

        if ( !treeMatchingTask.isMatched( treeMatchingTask.getLeftRoot())) {
            treeMatchingTask.getDirs().match( treeMatchingTask.getLeftRoot(),
                                              treeMatchingTask.getRightRoot(), 1.0 );
        }

        matchInChildList(treeMatchingTask, reporter, treeMatchingTask.getDirs().getLeftRoot(), treeMatchingTask.getDirs().getRightRoot());
    }


    public void matchInChildList( TreeMatchingTask<T> treeMatchingTask, Reporter reporter, TreeDirNode<T> fromDir, TreeDirNode<T> toParent ) {

        if ( ! treeMatchingTask.isMatched( fromDir )  ) {

            String name = fromDir.getContent().getName();

            if ( toParent == null ) {
                throw new IllegalStateException( "prog error" );
            }

            reporter.detail( Message.get( "testing"), fromDir.toString() );

            for ( TreeNode<T> tgtKid : toParent.getChildren() ) {

                if ( !tgtKid.isLeaf() &&
                     !treeMatchingTask.isMatched( tgtKid ) &&
                     tgtKid.getContent().getName().equals( name )) {

                    treeMatchingTask.getDirs().match( fromDir, (TreeDirNode<T>)tgtKid, 1.0 );
                    break;
                }
            }
            
        }

        TreeDirNode<T> newParent = treeMatchingTask.getDirs().getMatch(fromDir);

        if ( newParent == null ) {
            return;
        }

        for ( TreeNode<T> fm : fromDir.getChildren() ) {

            if ( !fm.isLeaf() ) {
                matchInChildList( treeMatchingTask, reporter, (TreeDirNode<T>)fm, newParent );
            }
        }

    }

}
