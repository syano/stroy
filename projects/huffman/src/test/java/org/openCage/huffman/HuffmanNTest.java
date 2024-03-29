package org.openCage.huffman;

import org.junit.Test;
import org.openCage.kleinod.collection.T2;
import org.openCage.kleinod.io.Resource;
import org.openCage.kleinod.lambda.VF1E;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

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

public class HuffmanNTest {


    @Test
    public void testA() {
        String src = "aaaaabcabbbbeeeaf";
        BitField dba = BitList.valueOf(src.getBytes(Charset.forName("utf8")));

        System.out.println(dba.toString8());

        HuffmanN hn = new HuffmanN(dba);

        HuffmanN.printCodes(hn.getCode(8));

        System.out.println("-------");

        HuffmanN.printCodes(new Canonical().canonisize(hn.getCode(8)).i0);
    }


    @Test
    public void testWriteCanonical() {
        String src = "aaaaabcabbbbeeeafgghijklmnopqrastuvw1234567789";
        BitField dba = BitList.valueOf(src.getBytes(Charset.forName("utf8")));

        Canonical can = new Canonical();

        for ( int len = 2; len < 17; len++ ) {

            System.out.println(" --- " + len + " ----" );

            T2<Map<BitField, BitField>, Integer> code = can.canonisize(new HuffmanN(dba).getCode(len));
            BitField ww = can.writeCode(code.i0, (byte) code.i1.intValue());

            System.out.println(code.i1);
            System.out.println(ww.size() / 8);

            System.out.println(ww.toString8());


            Map<BitField, BitField> out = can.readCode(ww).i0;
            HuffmanN.printCodes(can.readCode(ww).i0);

            assertEquals(code.i0, out);
        }

    }

    @Test
    public void testCanonical() {
        String src = "aaaaabcabbbbeeeaf";
        BitField dba = BitList.valueOf(src.getBytes(Charset.forName("utf8")));

        for (int len = 2; len < 17; len++) {

            System.out.println(" ---------------------------- " + len + " ---------------------------- ");

            T2<Map<BitField, BitField>, Integer> code = Canonical.canonisize(new HuffmanN(dba).getCode(len));
            BitField ww = Canonical.writeCode(code.i0, (byte) code.i1.intValue());

            Map<BitField, BitField> out = Canonical.readCode(ww).i0;

            assertEquals(code.i0, out);
        }
    }

    @Test
    public void testEncodeDecode() {
        String src = "aaaaabcabbbbeeeaf";
        BitField bsrc = BitList.valueOf(src.getBytes(Charset.forName("utf8")));

        HuffmanN hn = new HuffmanN(bsrc);

        System.out.println(bsrc.toString8());

        System.out.println(hn.encode(hn.getCode(4)).toString8());
        System.out.println(hn.encode(hn.getCode(6)).toString8());
        System.out.println(hn.encode(hn.getCode(7)).toString8());
        System.out.println(hn.encode(hn.getCode(8)).toString8());
        System.out.println(hn.encode(hn.getCode(12)).toString8());

        Map<BitField, BitField> code = hn.getCode(8);

        System.out.println(hn.decode(code, hn.encode(code)).toString8());

        code = hn.getCode(7);

        System.out.println(hn.decode(code, hn.encode(code)).toString8());

        for (int len = 2; len < 16; len++) {
            Map<BitField, BitField> cod = hn.getCode(len);

            BitField res = hn.decode(code, hn.encode(code));
            res.trimTo(bsrc.size());
            System.out.println(bsrc);
            System.out.println(res.toString());

            assertEquals("len is " + len, bsrc, res);

        }

    }

    @Test
    public void testEncodeDecodeWithCanonical() {
        String src = "aaaaabcabbbbeeeaf";
        BitField bsrc = BitList.valueOf(src.getBytes(Charset.forName("utf8")));

        HuffmanN hn = new HuffmanN(bsrc);

        for (int len = 2; len < 16; len++) {
            if (len == 9) {
                continue;
            }
            Map<BitField, BitField> code1 = hn.getCode(len);
            Map<BitField, BitField> codeC = Canonical.canonisize(code1).i0;

            BitField encoded = hn.encode(codeC);
            System.out.println("encoded to " + encoded.size());

            BitField res = hn.decode(codeC, hn.encode(codeC));
            res.trimTo(bsrc.size());
            System.out.println(bsrc);
            System.out.println(res.toString());

            assertEquals("len is " + len, bsrc, res);

        }

    }

    @Test
    public void testCompressPic() {


        Resource.tryWith(new VF1E<Resource>() {
            @Override
            public void callWithException(Resource resource) throws Exception {

                BitField bf = BitList.valueOf(resource.add(getClass().getResourceAsStream("content.jpg")));

                for (int len = 2; len < 17; len++) {

                    System.out.println("--- " + len + " --");

                    System.out.println("before: " + bf.size());

                    HuffmanN hn = new HuffmanN(bf);
                    BitField after = hn.encode(len);

                    System.out.println("after " + after.size());

                    BitField back = hn.decode(after);

                    assertEquals(bf, back);

                    System.out.println("reduced by (bytes) " + (bf.size() - after.size()) / 8);
                }
            }
        });
    }

    @Test
    public void testCompressTwice() {


        Resource.tryWith(new VF1E<Resource>() {
            public void callWithException(Resource resource) throws Exception {

                InputStream is = resource.add(getClass().getResourceAsStream("content.jpg"));

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
                byte[] bytes = new byte[512];

                // Read bytes from the input stream in bytes.length-sized chunks and write
                // them into the output stream
                int readBytes;
                while ((readBytes = is.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, readBytes);
                }

                // Convert the contents of the output stream into a byte array
                byte[] byteData = outputStream.toByteArray();


                BitField bf = BitList.valueOf(byteData);

                BitField first = new HuffmanN(bf).encode(8);


                for ( int len = 2; len < 17; len++ ) {

                    System.out.println( "--- " + len + " --" );

                    System.out.println( "before: " + first.size());

                    HuffmanN hn = new HuffmanN(first );
                    BitField after = hn.encode(len);

                    System.out.println( "after " + after.size() );

                    BitField back = hn.decode( after );

                    assertEquals( first, back );
                    assertEquals( bf, HuffmanN.decode( back ) );

                    System.out.println( "reduced by (bytes) " + (first.size() - after.size()) / 8 );
                }
            }
        });

    }

    @Test
    public void testEncodeDecode2() {
        String src = "aaaaabcabbbbeeeaf";
        BitField bsrc = BitList.valueOf(src.getBytes(Charset.forName("utf8")));

        System.out.println("orig: " + bsrc);

        HuffmanN hn = new HuffmanN(bsrc);

        for (int len = 2; len < 13; len++) {

            System.out.println(" ---------------------- " + len + " ---------------------- ");

            BitField bf = hn.encode(len);
            System.out.println("coded " + bf);
            System.out.println("coded " + bf.toString8());
            BitField back = hn.decode(bf);

            System.out.println(bsrc);
            System.out.println(bsrc.toString8());
            System.out.println(back);
            System.out.println(back.toString8());


            assertEquals("len is " + len, bsrc, back);

        }
    }


    public static byte[] toBytes(int by) {
        byte[] data = new byte[4];

// int -> byte[]
        for (int i = 0; i < 4; ++i) {
            int shift = i << 3; // i * 8
            data[i] = (byte) ((by & (0xff << shift)) >>> shift);
        }

        return data;
    }


    @Test
    public void testTT() {
        byte[] bytes = toBytes(1);

        assertEquals(1, bytes[0]);
        assertEquals(0, bytes[1]);
        assertEquals(0, bytes[2]);
        assertEquals(0, bytes[3]);
    }


}
