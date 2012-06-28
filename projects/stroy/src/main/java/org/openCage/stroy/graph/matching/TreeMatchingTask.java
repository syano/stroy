package org.openCage.stroy.graph.matching;

import org.openCage.stroy.content.Content;
import org.openCage.stroy.graph.SameContent;
import org.openCage.stroy.graph.node.TreeDirNode;
import org.openCage.stroy.graph.node.TreeLeafNode;
import org.openCage.stroy.graph.node.TreeNode;
import org.openCage.stroy.task.MatchingTask;
import org.openCage.stroy.ui.ChangeVector;

import java.util.Collection;


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

public interface TreeMatchingTask<T extends Content> {

    public void addDup(final SameContent<T> sh);

    public MatchingTask<TreeLeafNode<T>>  getLeaves();
    public MatchingTask<TreeDirNode<T>>   getDirs();

    public boolean     isMatched(  final TreeNode<T> obj );
    public TreeNode<T> getMatch(   final TreeNode<T> obj );
    public double      getMatchQuality( final TreeNode<T> obj );

    public Collection<SameContent<T>> getDuplicates();

    public void shortStatus();

    public void status();

    public int                            getLeftLeaveCount();
    public int getLeftDirCount();
    public int getRightLeaveCount();
    public int getRightDirCount();

    public Collection<TreeLeafNode<T>>    getModifiedLeaves();
    public Collection<TreeLeafNode<T>>    getRenamedLeaves();
    public Collection<TreeLeafNode<T>>    getMovedLeaves();

    public Collection<TreeDirNode<T>>     getRenamedDirs();
    public Collection<TreeDirNode<T>>     getMovedDirs();

    public Collection<TreeLeafNode<T>>     getComplexModifiedLeaves();
    
    

    public Collection<TreeLeafNode<T>> getUnmatchedLeftFiles();
    public Collection<TreeDirNode<T>> getUnmatchedLeftDirs();
    public Collection<TreeLeafNode<T>> getUnmatchedRightFiles();
    public Collection<TreeDirNode<T>> getUnmatchedRightDirs();

    public TreeDirNode<T> getLeftRoot();
    public TreeDirNode<T> getRightRoot();

    public boolean isContentChanged( TreeNode<T> obj );

    public ChangeVector getChangeVector( TreeNode<T> obj );

    public void remove(TreeNode<T> node);

    void merge();

    public MatchingTask<TreeDirNode<T>> getDirTask();

    public MatchingTask<TreeLeafNode<T>> getFileTask();

}
