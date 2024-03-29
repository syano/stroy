package org.openCage.ruleofthree.threetoxml;

import org.junit.Test;
import org.openCage.ruleofthree.Three;
import org.openCage.ruleofthree.ThreeKey;

import java.io.File;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.openCage.ruleofthree.Threes.THREE;

public class XmlToThreeTest {

    @Test
    public void testMinimal() {
        XmlToThree xmlToThree = new XmlToThree();

        URL url = getClass().getResource("/org/openCage/ruleofthree/threetoxml/three-one.xml");
        assertTrue(new File( url.getFile()).exists());

        Three readable =  xmlToThree.read( "root", url.getFile());

        assertNotNull(readable);
        assertTrue(readable.isMap());
    }

    @Test
    public void simpleMap() {
        XmlToThree xmlToThree = new XmlToThree();

        URL url = getClass().getResource("/org/openCage/ruleofthree/threetoxml/simple-map.xml");
        assertTrue(new File( url.getFile()).exists());

        Three readable =  xmlToThree.read( "root", url.getFile());

        assertNotNull(readable);
        assertTrue(readable.isMap());

        assertEquals(THREE("val"), readable.getMap().get(ThreeKey.valueOf("key")));
    }


    @Test
    public void testPropertyMap() {
        XmlToThree xmlToThree = new XmlToThree();

        URL url = getClass().getResource("/org/openCage/ruleofthree/threetoxml/three-key-map.xml");
        assertTrue(new File( url.getFile()).exists());

        Three readable =  xmlToThree.read( "root", url.getFile());

        assertNotNull(readable);
        assertTrue(readable.isMap());

        assertNotNull(readable.getMap().get(ThreeKey.valueOf("amap")));
        assertEquals( THREE("val"), readable.getMap().get(ThreeKey.valueOf("amap")).getMap().get(ThreeKey.valueOf("key")));
    }

    @Test
    public void testString() {
        XmlToThree xmlToThree = new XmlToThree();

        URL url = getClass().getResource("/org/openCage/ruleofthree/threetoxml/three-string.xml");
        assertTrue(new File( url.getFile()).exists());

        Three readable =  xmlToThree.read( "root", url.getFile());

        assertNotNull(readable);
        assertTrue(readable.isString());
    }


//    @Test
//    public void testOneString() {
//        XMLtoReadable xmlReader = new XMLtoReadable();
//
//        URL url = getClass().getResource("/org/openCage/comphy/three-one.xml");
//        assertTrue(new File( url.getFile()).exists());
//
//        ThreeText readable =  xmlReader.read(url.getFile());
//
//        assertNotNull(readable);
//        assertTrue(readable.isMap() );
//
//        GMap<Str,ThreeText> map = readable.getMap();
//
//        assertTrue(map.containsKeyG(S("key")));
//        assertEquals( "txt", map.getG(S("key")).toString());
//    }
//
//    @Test
//    public void testOneMap() {
//        XMLtoReadable xmlReader = new XMLtoReadable();
//
//        URL url = getClass().getResource("/org/openCage/comphy/comphy-map.xml");
//        assertTrue(new File( url.getFile()).exists());
//
//        ThreeText readable =  xmlReader.read(url.getFile());
//
//        assertNotNull(readable);
//        assertTrue(readable instanceof RMap );
//
//        RMap map = (RMap)readable;
//
//        assertTrue(map.containsKey( XMLtoReadable.Key.valueOf("things")));
//        ThreeText rr = map.get(XMLtoReadable.Key.valueOf("things"));
//        assertTrue( rr instanceof RMap );
//
//        RMap things = (RMap)rr;
//
//        assertEquals("txt", things.get(XMLtoReadable.Key.valueOf("one")).toString());
//        assertEquals("2", things.get(XMLtoReadable.Key.valueOf("two")).toString());
//    }
//
//    @Test
//    public void testEmptyList() {
//        XMLtoReadable xmlReader = new XMLtoReadable();
//
//        URL url = getClass().getResource("/org/openCage/comphy/comphy-empty-list.xml");
//        assertTrue(new File( url.getFile()).exists());
//
//        ThreeText readable =  xmlReader.read(url.getFile());
//
//        assertNotNull(readable);
//        assertTrue(readable instanceof RMap );
//
//        RMap map = (RMap)readable;
//
//        assertTrue(map.containsKey( XMLtoReadable.Key.valueOf("foo")));
//        ThreeText rr = map.get(XMLtoReadable.Key.valueOf("foo"));
//        assertTrue( rr instanceof RString);
//
//    }
//
    @Test
    public void testList() {
        XmlToThree xmlToThree = new XmlToThree();

        URL url = getClass().getResource("/org/openCage/ruleofthree/threetoxml/list.xml");

        Three readable =  xmlToThree.read( "root", url.getFile());

        assertNotNull(readable);
        assertTrue(readable.isList());
        assertEquals(3,readable.getList().size());


//
//        ThreeText readable =  xmlReader.read(url.getFile());
//        RMap map = (RMap)readable;
//
//        assertTrue(map.containsKey( XMLtoReadable.Key.valueOf("list")));
//        RList list = (RList)map.get(XMLtoReadable.Key.valueOf("list"));
//
//        assertEquals( "texta", list.get(0).toString());
//        assertEquals( "textb", list.get(1).toString());
//        assertEquals( "textc", list.get(2).toString());
    }
//
//    @Test
//    public void testMore() {
//        XMLtoReadable xmlReader = new XMLtoReadable();
//
//        URL url = getClass().getResource("/org/openCage/comphy/comphy-more.xml");
//        assertTrue(new File( url.getFile()).exists());
//
//        ThreeText readable =  xmlReader.read(url.getFile());
//
//        assertNotNull(readable);
//        assertTrue(readable instanceof RMap );
//        RMap map = (RMap)readable;
//
//        assertTrue(map.containsKey( XMLtoReadable.Key.valueOf("one")));
//        ThreeText rr = map.get(XMLtoReadable.Key.valueOf("one"));
//        assertTrue( rr instanceof RMap );
//
//        assertTrue(map.containsKey( XMLtoReadable.Key.valueOf("two")));
//        rr = map.get(XMLtoReadable.Key.valueOf("two"));
//        assertTrue( rr instanceof RList );
//
//        assertTrue(map.containsKey( XMLtoReadable.Key.valueOf("three")));
//        rr = map.get(XMLtoReadable.Key.valueOf("three"));
//        assertTrue( rr instanceof RString);
//
//    }
//
//    @Test
//    public void testComplex() {
//        XMLtoReadable xmlReader = new XMLtoReadable();
//
//        ThreeText readable =  xmlReader.read( getClass().getResource("/org/openCage/comphy/comphy-complex.xml").getFile());
//
//        RMap map = (RMap)readable;
//
//        RMap fileTypes = (RMap)map.get(XMLtoReadable.Key.valueOf("fileTypes"));
//        RMap jpeg      = (RMap)fileTypes.get(XMLtoReadable.Key.valueOf("jpg"));
//
//        assertEquals( "lossy picture codec", jpeg.get(XMLtoReadable.Key.valueOf("description")).toString() );
//    }
//
//    @Test
//    public void testSpecialChars() {
//        XMLtoReadable xmlReader = new XMLtoReadable();
//
//        ThreeText readable =  xmlReader.read( getClass().getResource("/org/openCage/comphy/comphy-complex1.xml").getFile());
//
//        RMap map = (RMap)readable;
//
//        RMap jpg = (RMap)map.get(XMLtoReadable.Key.valueOf("jpg"));
//
//        assertEquals( "<standard>", jpg.get(XMLtoReadable.Key.valueOf("open")).toString() );
//    }
//
//    @Test
//    public void testEmptyString() {
//        XMLtoReadable xmlReader = new XMLtoReadable();
//
//        ThreeText readable =  xmlReader.read( getClass().getResource("/org/openCage/comphy/comphy-empty-string.xml").getFile());
//
//        RMap map = (RMap)readable;
//
//        RString str = (RString)map.get(XMLtoReadable.Key.valueOf("empty"));
//
//        assertEquals( "", str.get() );
//    }

}
