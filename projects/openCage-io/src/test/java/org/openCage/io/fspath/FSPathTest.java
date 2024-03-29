package org.openCage.io.fspath;

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
import org.junit.Test;
import org.openCage.lang.iterators.Count;

import static junit.framework.Assert.assertEquals;

public class FSPathTest {

    // simple -----

    @Test
    public void testSimpleUnixRoot() {
        FSPath path = new FSPathUnix( "/");

        assertEquals( 1, path.size());
        assertEquals( "/", path.toString());
    }

    @Test
    public void testSimpleWin() {

        FSPath path = new FSPathWindows( "C:");

        assertEquals( 1, path.size());
        assertEquals( "C:\\", path.toString());
    }

    @Test
    public void testSimplUNC() {

        FSPath path = new FSPathUNC( "\\\\huh");

        assertEquals( 1, path.size());
        assertEquals( "\\\\huh", path.toString());
    }


    // empty ----

    @Test( expected = IllegalArgumentException.class )
    public void testEmptyPathUnix() {
        new FSPathUnix( "" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testEmptyPathWindows() {
        new FSPathWindows( "" );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testEmptyUNC() {

        new FSPathUNC( "");
    }

    @Test( expected = IllegalArgumentException.class )
    public void testNoRootUNC() {

        new FSPathUNC( "\\\\");
    }
    // add -----

    @Test
    public void testAddUnix() {
        FSPath path = new FSPathUnix( "/").add("foo", "bar");

        assertEquals( 3, path.size());
        assertEquals( "/foo/bar", path.toString());
    }

    @Test( expected = IllegalArgumentException.class )
    public void testAddUnixIllegal() {
        new FSPathUnix( "/").add("foo", "/bar");
    }

    @Test
    public void testAddUNC() {
        FSPath path = new FSPathUNC( "\\\\aaa").add("foo", "bar");

        assertEquals( 3, path.size());
        assertEquals( "\\\\aaa\\foo\\bar", path.toString());
    }

    @Test( expected = IllegalArgumentException.class )
    public void testAddUNCIllegal() {
        new FSPathUNC( "\\\\aaa").add("foo", "\\bar");
    }

    @Test
    public void testAddWindows() {
        FSPath path = new FSPathWindows( "X:").add("foo", "bar");

        assertEquals( 3, path.size());
        assertEquals( "X:\\foo\\bar", path.toString());
    }

    @Test( expected = IllegalArgumentException.class )
    public void testAddWindowsIllegal() {
        new FSPathWindows( "X:").add("foo", "\\bar");
    }



    // ---

    @Test
    public void testAbsoluteUnix() {
        FSPath path = new FSPathUnix( "/foo" );
        assertEquals( 2, path.size());

        assertEquals( "/foo", path.toString() );
    }




    @Test
    public void testXDG(){

        // TODO

        System.out.println( System.getenv("$XDG_CONFIG"));
        System.out.println( System.getenv("XDG_CONFIG"));
    }


    @Test
    public void testRootUnix() {
        assertEquals( 1, new FSPathUnix("/").size() );
    }


    @Test
    public void testIteratorUnix() {
        int tested = 0;
        for ( Count<FSPath> elem : Count.count(new FSPathUnix("/a/b/c"))) {
            if ( elem.idx() == 0 ) {
                assertEquals( new FSPathUnix("/"), elem.obj());
                tested++;
            }
            if ( elem.idx() == 1 ) {
                assertEquals( new FSPathUnix("/a"), elem.obj());
                tested++;
            }
            if ( elem.idx() == 2 ) {
                assertEquals( new FSPathUnix("/a/b"), elem.obj());
                tested++;
            }
            if ( elem.idx() == 3 ) {
                assertEquals( new FSPathUnix("/a/b/c"), elem.obj());
                tested++;
            }
        }

        assertEquals(4, tested );
    }

    @Test
    public void testIteratorWindows() {
        int tested = 0;
        for ( Count<FSPath> elem : Count.count(new FSPathWindows("C:\\a\\b\\c"))) {
            if ( elem.idx() == 0 ) {
                assertEquals( new FSPathWindows("C:"), elem.obj());
                tested++;
            }
            if ( elem.idx() == 1 ) {
                assertEquals( new FSPathWindows("C:\\a"), elem.obj());
                tested++;
            }
            if ( elem.idx() == 2 ) {
                assertEquals( new FSPathWindows("C:\\a\\b"), elem.obj());
                tested++;
            }
            if ( elem.idx() == 3 ) {
                assertEquals( new FSPathWindows("C:\\a\\b\\c"), elem.obj());
                tested++;
            }
        }

        assertEquals(4, tested );
    }

    @Test
    public void testIteratorUNC() {
        int tested = 0;
        for ( Count<FSPath> elem : Count.count(new FSPathUNC("\\\\a\\b\\c"))) {
            if ( elem.idx() == 0 ) {
                assertEquals( new FSPathUNC("\\\\a"), elem.obj());
                tested++;
            }
            if ( elem.idx() == 1 ) {
                assertEquals( new FSPathUNC("\\\\a\\b"), elem.obj());
                tested++;
            }
            if ( elem.idx() == 2 ) {
                assertEquals( new FSPathUNC("\\\\a\\b\\c"), elem.obj());
                tested++;
            }
        }

        assertEquals(3, tested );
    }




    @Test
    public void testAbsoluteWindows() {
        FSPath path = new FSPathWindows( "D:\\foo" );
        assertEquals( 2, path.size());

        assertEquals( "D:\\foo", path.toString() );
    }

    @Test
    public void testPackage() {
        FSPath base = new FSPathUnix("/");

        assertEquals( "/a/b/c", base.addPackage( "a.b.c" ).toString());
    }

    @Test
    public void testPackageUNC() {
        FSPath base = new FSPathUNC( "\\\\duda");

        assertEquals( "\\\\duda\\a\\b\\c", base.addPackage( "a.b.c" ).toString());
    }

    // ------------ dots --------------------

    @Test
    public void testDotsUnix() {
        assertEquals( "/", new FSPathUnix("/.").toString() );
        assertEquals( "/", new FSPathUnix("/duh/..").toString() );
    }

    @Test
    public void testDotsWindows() {
        assertEquals( "C:\\foo\\duh", new FSPathWindows("C:\\foo\\.\\duh").toString() );
        assertEquals( "C:\\duh", new FSPathWindows("C:\\foo\\..\\duh").toString() );
    }

    @Test
    public void testDotsUNC() {
        assertEquals( "\\\\foo\\duh", new FSPathUNC("\\\\foo\\.\\duh").toString() );
        assertEquals( "\\\\foo\\duh", new FSPathUNC("\\\\foo\\bar\\..\\duh").toString() );
    }

    @Test( expected = IllegalArgumentException.class )
    public void testDotsUNCIllegal() {
        new FSPathUNC("\\\\foo\\..\\duh");
    }

}
