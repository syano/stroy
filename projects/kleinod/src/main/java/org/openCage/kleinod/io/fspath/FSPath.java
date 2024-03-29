package org.openCage.kleinod.io.fspath;

import net.jcip.annotations.Immutable;

import java.io.File;
import java.net.URI;
import java.util.Iterator;
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
 * A file path util, platform independent
 */
@Immutable
public interface FSPath extends Iterable<FSPath> {

    String toString();

    File toFile();

    /**
     * create a new fspath relative to this one
     * @param elements path elements.
     * @return A new FSPath
     */
    FSPath add( String ... elements );

    /**
     * create a new fspath relative to this one
     * @param elements path elements.
     * @return A new FSPath
     */
    FSPath add( List<String> elements );

    /**
     * create a new fspath realtive to this one
     * @param rel A relative path
     * @return returns a new path with the relative path added to it
     */
    FSPath add( FSRelPath rel );

    /**
     * Translate a package (e.g. org.openCage.foo) in a platform specfic directroy path
     * @param packageDescr a package, e.g. org.openCage
     * @return a new FSPath
     */
    FSPath addPackage( String packageDescr );

    /**
     * Iterate over the elements of this path
     * @return PathIterator
     */
    Iterator<FSPath> iterator();

    /**
     * the number of elements
     * @return the number of elements
     */
    int size();

    /**
     * the path as uri
     * @return a uri of that path
     */
    URI toURI();

    /**
     * A new FSPath i steps up
     * @param i number of parents to move up
     * @return a new dspath
     */
    FSPath parent(int i);

    /**
     * The parent path
     * @return The parent path
     */
    FSPath parent();

    /**
     * The file name, i.e. the last path element
     * @return Thr filename
     */
    String getFileName();

    /**
     * i.e. no parent
     * @return
     */
    boolean isRoot();
}
