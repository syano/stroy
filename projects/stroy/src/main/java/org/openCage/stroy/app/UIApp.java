package org.openCage.stroy.app;

import org.openCage.stroy.ui.difftree.NWayDiffPane;
import org.openCage.stroy.graph.matching.TreeMatchingTask;
import org.openCage.lindwurm.LindenDirNode;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

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

/**
 * The class to hold the data in the ui case
 * No logic here
 */
public class UIApp {

    private NWayDiffPane                        diffPane;
    private List<DefaultMutableTreeNode>        uiRoots;
    private Tasks                            tasks;
//    private JFrame                              

    public UIApp( NWayDiffPane diffPane, List<DefaultMutableTreeNode> uiRoots, Tasks tasks ) {
        this.diffPane = diffPane;
        this.uiRoots = uiRoots;
        this.tasks = tasks;
    }

    public NWayDiffPane getDiffPane() {
        return diffPane;
    }

    public List<DefaultMutableTreeNode> getUIRoots() {
        return uiRoots;
    }

    public List<TreeMatchingTask> getTasks() {
        return tasks.getTasks();
    }

    public List<LindenDirNode> getTreeRoots() {
        return tasks.getRoots();
    }
}
