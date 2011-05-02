package org.openCage.huffman;

import org.junit.Test;

import java.nio.charset.Charset;

/**
 * Created by IntelliJ IDEA.
 * User: SPF
 * Date: 08.04.11
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class HuffmanNTest {


    @Test
    public void testA() {
        String src = "abcabea";
        BitField dba = BitFieldImpl.valueOf( src.getBytes(Charset.forName("utf8")));

        System.out.println( dba.toString8() );

        HuffmanN hn = new HuffmanN( dba );

        HuffmanN.printCodes( hn.getCode( 8 ));

        System.out.println("-------");

        HuffmanN.printCodes( new Canonical().canonisize( hn.getCode( 8 )));
    }
}