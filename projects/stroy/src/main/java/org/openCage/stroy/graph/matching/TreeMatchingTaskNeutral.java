package org.openCage.stroy.graph.matching;

import org.openCage.stroy.content.Content;
import org.openCage.stroy.graph.SameContent;
import org.openCage.stroy.graph.node.TreeLeafNode;
import org.openCage.stroy.graph.node.TreeDirNode;
import org.openCage.stroy.graph.node.TreeNode;
import org.openCage.stroy.graph.iterator.DepthFirstIterable;
import org.openCage.stroy.task.MatchingTask;
import org.openCage.stroy.task.MatchingTaskNeutral;
import org.openCage.stroy.ui.ChangeVector;
import org.openCage.stroy.diff.ContentDiff;
import org.openCage.util.logging.Log;
import org.openCage.util.generics.Generics;

import java.util.*;
import java.util.logging.Level;

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

// TODO simplify
public class TreeMatchingTaskNeutral<T extends Content> implements TreeMatchingTask<T>{

    private List<SameContent<T>>           dups;
    private MatchingTask<TreeDirNode<T>>   dirTask;
    private MatchingTask<TreeLeafNode<T>>  fileTask;

    public TreeMatchingTaskNeutral() {
        dups         = new ArrayList<SameContent<T>>();
        dirTask      = new MatchingTaskNeutral<TreeDirNode<T>>();
        fileTask     = new MatchingTaskNeutral<TreeLeafNode<T>>();
    }


    public void addDup(final SameContent<T> sh) {
        dups.add( sh );
    }

    public MatchingTask<TreeLeafNode<T>> getLeaves() {
        return fileTask;
    }

    public MatchingTask<TreeDirNode<T>> getDirs() {
        return dirTask;
    }

    public boolean isMatched( final TreeNode<T> obj ) {
        if ( obj.isLeaf() ) {
            return fileTask.isMatched( (TreeLeafNode<T>)obj );
        }

        return dirTask.isMatched( (TreeDirNode<T>)obj );
    }

    public TreeNode<T> getMatch( final TreeNode<T> obj ) {

        if ( obj == null ) {
//            Log.warning( "getMatch called for null" );
            return null;
        }

        if ( obj.isLeaf() ) {
            return fileTask.getMatch( (TreeLeafNode<T>)obj );
        }

        return dirTask.getMatch( (TreeDirNode<T>)obj );
    }

    public double getMatchQuality(TreeNode<T> obj) {
        if ( obj.isLeaf() ) {
            return fileTask.getMatchQuality( (TreeLeafNode<T>)obj );
        }

        return dirTask.getMatchQuality( (TreeDirNode<T>)obj );
    }


    public Collection<SameContent<T>> getDuplicates() {
        return dups;
    }


    // TODO
    public void shortStatus() {

//        if ( Log.isAtLeast( Level.FINE )) {
//            System.out.println("unmatched source dirs  " + dirTask.getUnmatchedLeft().size() );
//            System.out.println("unmatched source files " + fileTask.getUnmatchedLeft().size() );
//            System.out.println("unmatched target dirs  " + dirTask.getUnmatchedRight().size() );
//            System.out.println("unmatched target files " + fileTask.getUnmatchedRight().size() );
//        }
    }

    // TODO
    public void status() {
        System.out.println("files deleted");

        for ( final TreeLeafNode<T> lfm : fileTask.getUnmatchedLeft() ) {
            System.out.println("  " + lfm );
        }

        System.out.println("dirs deleted");
        for ( final TreeDirNode<T> fm : dirTask.getUnmatchedLeft() ) {
            System.out.println("  " + fm );
        }

        System.out.println("files moved");
        for ( final TreeLeafNode<T> lfm : getMovedLeaves() ) {
            System.out.println("  " + lfm.toString() + "->" + fileTask.getMatch( lfm ).getParent() );
        }

        System.out.println("files renamed");
        for ( TreeLeafNode<T> lfm : getRenamedLeaves() ) {
            System.out.println("  " + lfm.toString() + "->" + fileTask.getMatch( lfm ).getContent().getName() );
        }

        System.out.println("dirs moved");
        for ( final TreeDirNode<T> lfm : getMovedDirs() ) {
            System.out.println("  " + lfm.toString() + "->" + getMatch( lfm ).getParent() );
        }

        System.out.println("dirs renamed");
        for ( TreeDirNode<T> lfm : getRenamedDirs() ) {
            System.out.println("  " + lfm.toString() + "->" + getMatch( lfm ).getContent().getName() );
        }

        System.out.println("files changed");
        for ( TreeLeafNode<T> lfm : getModifiedLeaves() ) {
            System.out.println("  " + lfm );
        }

        System.out.println("new dirs");
        for ( TreeDirNode<T> lfm : dirTask.getUnmatchedRight() ) {
            System.out.println("  " + lfm );
        }

        System.out.println("new files");
        for ( TreeLeafNode<T> lfm : fileTask.getUnmatchedRight() ) {
            System.out.println("  " + lfm );
        }

        System.out.println("dirs changed");
        for ( TreeDirNode<T> lfm : getModifiedDirs() ) {
            System.out.println("  " + lfm );
        }

    }

    public int getLeftLeaveCount() {
        int count = 0;
        for ( TreeNode<T> node : new DepthFirstIterable<T>( getLeftRoot() )) {
            if ( node.isLeaf() ) {
                count++;
            }
        }
        return count;
    }

    public int getLeftDirCount() {
        int count = 0;
        for ( TreeNode<T> node : new DepthFirstIterable<T>( getLeftRoot() )) {
            if ( !node.isLeaf() ) {
                count++;
            }
        }
        return count;
    }

    public int getRightLeaveCount() {
        int count = 0;
        for ( TreeNode<T> node : new DepthFirstIterable<T>( getRightRoot() )) {
            if ( node.isLeaf() ) {
                count++;
            }
        }
        return count;
    }

    public int getRightDirCount() {
        int count = 0;
        for ( TreeNode<T> node : new DepthFirstIterable<T>( getRightRoot() )) {
            if ( !node.isLeaf() ) {
                count++;
            }
        }
        return count;
    }

    public Collection<TreeLeafNode<T>> getModifiedLeaves() {
        final List<TreeLeafNode<T>> modified = new ArrayList<TreeLeafNode<T>>();

        for ( TreeLeafNode<T> lfm : fileTask.getMatchedLeft() ) {

            if ( !fileTask.getDifference( lfm).equals( ContentDiff.same )) {

                if ( !isContentChanged( lfm )) {
                    int i = 0;
                }

                modified.add( lfm );
            }
        }

        return modified;
    }

    public Collection<TreeLeafNode<T>> getRenamedLeaves() {
        //noinspection unchecked
        return filterRenamed( (Collection) fileTask.getMatchedLeft() );
    }

    public Collection<TreeDirNode<T>> getRenamedDirs() {
        //noinspection unchecked
        return filterRenamed( (Collection) dirTask.getMatchedLeft() );
    }

    public TreeDirNode<T> getLeftRoot() {
        return dirTask.getLeftRoot();
    }

    public TreeDirNode<T> getRightRoot() {
        return dirTask.getRightRoot();
    }

    public boolean isContentChanged(TreeNode<T> obj) {

        if ( !obj.isLeaf() || !isMatched( obj ) ) {
            return false;
        }

        ContentDiff diff = fileTask.getDifference( (TreeLeafNode)obj );

        return !diff.equals( ContentDiff.same );

        //return fileTask.getMatchQuality( (TreeLeafNode)obj ) < 1.0;
    }



    public Collection filterRenamed( Collection<TreeNode<T>> matched ) {
        List renamed = new ArrayList();

        for ( TreeNode<T> lfm : matched ) {
            if ( ! lfm.getContent().getName().equals( getMatch( lfm ).getContent().getName() )){
                //noinspection unchecked
                renamed.add( lfm );
            }
        }

        return renamed;
    }

    public Collection<TreeLeafNode<T>> getMovedLeaves() {

        //noinspection unchecked
        return getMoved( new Generics<TreeNode<T>,TreeLeafNode<T>>().cast( fileTask.getMatchedLeft() ));
    }

    public Collection<TreeDirNode<T>> getMovedDirs() {

        //noinspection unchecked
        return getMoved( new Generics<TreeNode<T>,TreeDirNode<T>>().cast( dirTask.getMatchedLeft()));
    }


    public Collection<TreeLeafNode<T>> getUnmatchedLeftFiles() {
        return fileTask.getUnmatchedLeft();
    }

    public Collection<TreeDirNode<T>> getUnmatchedLeftDirs() {
        return dirTask.getUnmatchedLeft();
    }

    public Collection<TreeLeafNode<T>> getUnmatchedRightFiles() {
        return fileTask.getUnmatchedRight();
    }

    public Collection<TreeDirNode<T>> getUnmatchedRightDirs() {
        return dirTask.getUnmatchedRight();
    }

    private Collection getMoved( Collection< TreeNode<T>> matched ) {
        List moved = new ArrayList();

        for ( final TreeNode<T> node : matched ) {
            TreeDirNode<T> parent     = node.getParent();
            TreeNode<T>    match      = getMatch( node );

            if ( parent != null ) {
                if ( !dirTask.isMatched( parent )) {
                    moved.add( node );
                } else if ( dirTask.getMatch( parent ) != match.getParent() ) {
                    moved.add( node );
                }
            }
        }

        return moved;
    }

    public Collection<TreeDirNode<T>> getModifiedDirs() {
        Set<TreeDirNode<T>> modified = new HashSet<TreeDirNode<T>>();

        for ( TreeDirNode<T> node : getRenamedDirs() ) {
            if ( node.getParent() != null ) {
                modified.add( node.getParent() );
            }
        }

        for ( TreeLeafNode<T> node : getRenamedLeaves() ) {
            if ( node.getParent() != null ) {
                modified.add( node.getParent() );
            }
        }

        for ( TreeDirNode<T> node : getMovedDirs() ) {
            modified.add( node.getParent() );

            TreeDirNode<T> newParentSrc = dirTask.getMatch( dirTask.getMatch( node ).getParent());

            if ( newParentSrc != null ) {
                modified.add( newParentSrc );
            }
        }

        for ( TreeLeafNode<T> node : getMovedLeaves() ) {
            modified.add( node.getParent() );

            TreeDirNode<T> newParentSrc = dirTask.getMatch( fileTask.getMatch( node ).getParent());

            if ( newParentSrc != null ) {
                modified.add( newParentSrc );
            }
        }

        for (TreeDirNode node : dirTask.getUnmatchedLeft() ) {
            modified.add( node.getParent() );
        }

        for (TreeLeafNode node : fileTask.getUnmatchedLeft() ) {
            modified.add( node.getParent() );
        }

        for (TreeDirNode node : dirTask.getUnmatchedRight() ) {
            TreeDirNode<T> parentSrc = dirTask.getMatch( node.getParent());

            if ( parentSrc != null ) {
                modified.add( parentSrc );
            }
        }

        for (TreeLeafNode node : fileTask.getUnmatchedRight() ) {
            TreeDirNode<T> parentSrc = dirTask.getMatch( node.getParent());

            if ( parentSrc != null ) {
                modified.add( parentSrc );
            }
        }

        return modified;
    }

    public ChangeVector getChangeVector(TreeNode<T> obj) {

        ChangeVector cv = new ChangeVector();

        TreeNode<T> match = getMatch( obj );

        if ( match == null ) {
            cv.only = true;

            return cv;
        }

        if ( obj.isLeaf() ) {
            cv.content =  fileTask.getDifference( (TreeLeafNode)obj );
        }

        cv.name = ! match.getContent().getName().equals( obj.getContent().getName());

        TreeDirNode<T> parent     = obj.getParent();

        if ( parent != null ) {
            if ( !dirTask.isMatched( parent )) {
                cv.parent = true;
            } else if ( dirTask.getMatch( parent ) != match.getParent() ) {
                cv.parent = true;
            } else if ( !parent.getContent().getName().equals( match.getParent().getContent().getName() ) ) {
                cv.parentRenamed = true;
            }
        }


        return cv;
    }

    public void remove(TreeNode<T> treeNode) {
        if ( treeNode.isLeaf() ) {
            fileTask.remove( (TreeLeafNode<T>)treeNode );
        } else {
            dirTask.remove( (TreeDirNode<T>)treeNode );
        }
    }

    public void merge() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<TreeLeafNode<T>> getComplexModifiedLeaves() {
        Collection<TreeLeafNode<T>> struct = getMovedLeaves();

        struct.addAll( getRenamedLeaves() );

        List<TreeLeafNode<T>> ret = new ArrayList<TreeLeafNode<T>>();

        for ( TreeLeafNode<T> lfm : struct ) {
            if ( fileTask.getMatchQuality( lfm ) < 1.0 ) {
                ret.add( lfm );
            }
        }

        return ret;
    }


    public MatchingTask<TreeDirNode<T>> getDirTask() {
        return dirTask;
    }

    public MatchingTask<TreeLeafNode<T>> getFileTask() {
        return fileTask;
    }

}