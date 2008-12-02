//package org.openCage.stroy.dir.doubles2;
//
//import com.google.inject.Inject;
//import org.openCage.stroy.dir.FileContent;
//import org.openCage.stroy.graph.matching.TreeMatchingTask;
//import org.openCage.stroy.graph.matching.strategy.*;
//import org.openCage.util.logging.Log;
//
///***** BEGIN LICENSE BLOCK *****
//* Version: MPL 1.1/GPL 2.0
//*
//* The contents of this file are subject to the Mozilla Public License Version
//* 1.1 (the "License"); you may not use this file except in compliance with
//* the License. You may obtain a copy of the License at
//* http://www.mozilla.org/MPL/
//*
//* Software distributed under the License is distributed on an "AS IS" basis,
//* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
//* for the specific language governing rights and limitations under the
//* License.
//*
//* The Original Code is stroy code.
//*
//* The Initial Developer of the Original Code is Stephan Pfab <openCage@gmail.com>.
//* Portions created by Stephan Pfab are Copyright (C) 2006, 2007, 2008.
//* All Rights Reserved.
//*
//* Contributor(s):
//*
//* Alternatively, the contents of this file may be used under the terms of
//* either the GNU General Public License Version 2 or later (the "GPL"),
//* in which case the provisions of the GPL are applicable instead
//* of those above. If you wish to allow use of your version of this file only
//* under the terms of either the GPL, and not to allow others to
//* use your version of this file under the terms of the MPL, indicate your
//* decision by deleting the provisions above and replace them with the notice
//* and other provisions required by the GPL. If you do not delete
//* the provisions above, a recipient may use your version of this file under
//* the terms of any one of the MPL, the GPL.
//*
//***** END LICENSE BLOCK *****/
//
//public class EasyFirstStrategyFC implements TreeMatchingTaskStrategy<FileContent> {
//
//    private final MatchStrategy<FileContent> identicalLeafMatcher;
//    private final MatchStrategy<FileContent> hirDirMatcher;
//    private final MatchStrategy<FileContent> dupMatcher;
//    private final MatchStrategy<FileContent> historyMatcher;
//    private final MatchStrategy<FileContent> simpleDirMatcher;
//
//    @Inject
//    public EasyFirstStrategyFC(
//            @ForIdenticalLeaves   final MatchStrategy<FileContent>  identicalLeafMatcher,
//            @ForSimpleDirMatching final MatchStrategy<FileContent>  simpleDirMatcher,
//            @ForDirHierarchies final MatchStrategy<FileContent>  hirDirMatcher,
//            @ForDuplicates final MatchStrategy<FileContent>  dupMatcher,
//            @ForHistoryMatches final MatchStrategy<FileContent>  historyMatcher ) {
//        this.identicalLeafMatcher = identicalLeafMatcher;
//        this.hirDirMatcher        = hirDirMatcher;
//        this.dupMatcher           = dupMatcher;
//        this.historyMatcher       = historyMatcher;
//        this.simpleDirMatcher     = simpleDirMatcher;
//    }
//
//    public void applyStrategy( final TreeMatchingTask<FileContent> task) {
//
//        task.shortStatus();
//
//        Log.info( "matching identical leafs" );
//        identicalLeafMatcher.match(task);
//        task.shortStatus();
//
//        Log.info(  "Simple dir matching" );
//        simpleDirMatcher.match(task);
//        task.shortStatus();
//
//        Log.info(  "dupplicates" );
//        dupMatcher.match(task);
//        task.shortStatus();
//
//        Log.info(  "hierarchy dir" );
//        hirDirMatcher.match(task);
//        task.shortStatus();
//
//        Log.info(  "history" );
//        historyMatcher.match(task);
//        task.shortStatus();
//
//        Log.info(  "hierarchy dir" );
//        hirDirMatcher.match(task);
//        task.shortStatus();
//
////        Log.info(   "" );
//        //To change body of implemented methods use File | Settings | File Templates.
//
//        // TODO
//        //match radically changed files with same name and parent
//
//        //treeMatchingTask.state();
//
//    }
//}
