package org.openCage.stroy.graph.matching;

import junit.framework.TestCase;
import org.openCage.stroy.graph.node.SimpleContentTreeBuilder;
import org.openCage.stroy.graph.node.TreeNode;
import org.openCage.stroy.graph.node.TreeNodeUtils;
import org.openCage.stroy.graph.matching.strategy.StandardMatching;
import org.openCage.stroy.content.ReducedContent;

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
* Portions created by Stephan Pfab are Copyright (C) 2006 - 2009.
* All Rights Reserved.
*
* Contributor(s):
***** END LICENSE BLOCK *****/

public class TreeLeaveNodeFuzzyDistanceTest extends TestCase {

    public void testFuzzyIdenticalIsNotDistance1() {

        SimpleContentTreeBuilder b = new SimpleContentTreeBuilder();

        TreeNode treeLeft = b.d( "f", b.l( "a"),
                                                      b.d( "g", b.l("b"),
                                                                b.l("c")));

        TreeNode treeRight = b.d( "f", b.l( "a", "a12"),
                                                       b.d( "g", b.l("b", "b23"),
                                                                 b.l("c", "c7")));


        TreeMatchingTask task =
                TreeMatchingTaskNeutralBuilder.build( treeLeft, treeRight );



    }
}


