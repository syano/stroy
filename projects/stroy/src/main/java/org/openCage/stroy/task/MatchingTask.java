package org.openCage.stroy.task;

import org.openCage.stroy.diff.ContentDiff;

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

public interface MatchingTask<T> {

    public void addLeft( T obj );
    public void addRight( T obj );
    public void remove(T node);

    public Collection<T> getUnmatchedLeft();
    public Collection<T> getUnmatchedRight();
    public Collection<T> getMatchedLeft();
    public Collection<T> getMatchedRight();

    public boolean isMatched( T obj );
    public T       getMatch( T obj );

    public void match( T src, T tgt, double quality  );
    public void breakMatch( T src );

    public T getLeftRoot();
    public T getRightRoot();

    public void setRoots( T src, T tgt );


    public double getMatchQuality(T obj);

    public void addNodeChangeListener( NodeChangeListener listener );
    public void removeNodeChangeListener( NodeChangeListener listener );

    public ContentDiff getDifference( T obj );
    public void        setDifference( T obj, ContentDiff payload );

    void setRoot( T oneRoot, boolean left);
}
