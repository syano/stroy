package org.openCage.stroy.array;

import org.openCage.stroy.InformedDistance;
import org.openCage.stroy.task.MatchingTask;
import org.openCage.util.compare.TrippleProj1Comparator;
import org.openCage.util.iterator.T3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/***** BEGIN LICENSE BLOCK *****
* Version: MPL 1.1/GPL 2.0
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
*
* Alternatively, the contents of this file may be used under the terms of
* either the GNU General Public License Version 2 or later (the "GPL"),
* in which case the provisions of the GPL are applicable instead
* of those above. If you wish to allow use of your version of this file only
* under the terms of either the GPL, and not to allow others to
* use your version of this file under the terms of the MPL, indicate your
* decision by deleting the provisions above and replace them with the notice
* and other provisions required by the GPL. If you do not delete
* the provisions above, a recipient may use your version of this file under
* the terms of any one of the MPL, the GPL.
*
***** END LICENSE BLOCK *****/

public class MatchBestConnections2<S,T> { // <S,T extends Id> {

    private final InformedDistance<S,T> dist;
    private final boolean               perfect;

    private final List<T3<Double,T,T>> edges = new ArrayList<T3<Double,T,T>>();


    public MatchBestConnections2( InformedDistance<S,T> dist, boolean perfect  ) {
        this.dist = dist;
        this.perfect = perfect;
    }

    public void match( S info, MatchingTask<T> task, Collection<T> lefts, Collection<T> rights ) {

        for ( T elemFrom : lefts ) {
            for ( T elemTo : rights ) {
                if ( !task.isMatched( elemTo )) {
                    double distval = dist.distance( info, elemFrom, elemTo );

                    if ( distval < 0.31  ) {
                        edges.add( new T3<Double,T,T>( distval, elemFrom, elemTo ));
                    }
                }
            }
        }

        Collections.sort( edges, new TrippleProj1Comparator());

        for ( T3<Double,T,T> edge : edges ) {
            if ( !task.isMatched( edge.i1) && !task.isMatched( edge.i2 )) {
                if ( perfect ) {
                    task.match( edge.i1,  edge.i2, 1.0 );
                } else {
                    task.match( edge.i1,  edge.i2, 1 - edge.i0 );
                }
            }
        }

    }
}

