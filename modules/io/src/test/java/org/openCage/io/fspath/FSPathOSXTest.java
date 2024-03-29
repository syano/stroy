package org.openCage.io.fspath;

import org.junit.Before;
import org.junit.Test;
import org.openCage.lang.OS;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotSame;

public class FSPathOSXTest {

    @Before
    public void beforeMethod() {
        org.junit.Assume.assumeTrue( !OS.isWindows() );
    }

    @Test
    public void testPreferences() {
        assertTrue( FSPathBuilder.getPreferences().toString().endsWith("/Library/Preferences"));
    }

    @Test
    public void testDocuments() {
        assertTrue( FSPathBuilder.getDocuments().toString().endsWith("/Documents"));
    }


    @Test
    public void testTmp() {
        assertNotSame( FSPathBuilder.getTmpFile("xml"), FSPathBuilder.getTmpFile("xml"));

        assertFalse( FSPathBuilder.getTmpFile("xml").toFile().exists() );
    }
}
