package org.openCage.gpad;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.openCage.huffman.DynamicBitArray;
import org.openCage.huffman.Huffman;
import org.openCage.lang.protocol.FE1;
import org.openCage.withResource.impl.WithImpl;
import org.openCage.withResource.protocol.FileLineIterable;
import org.openCage.withResource.protocol.Iterators;

import java.io.*;
import java.net.URI;
import java.util.*;
import java.util.zip.Deflater;


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
* Portions created by Stephan Pfab are Copyright (C) 2006 - 2010.
* All Rights Reserved.
*
* Contributor(s):
***** END LICENSE BLOCK *****/
public class FaustByteNum implements TextEncoderIdx<Byte> {

    List<String>[] num2line;
    Set<String> knownLines = new HashSet();
    Map<String,Integer> line2num = new HashMap<String,Integer>();
    private DynamicBitArray pad;



    public void setPad( @NotNull URI path ) {
        final byte[] uncompressedPad = new byte[10000];
        new WithImpl().withInputStream( path, new FE1<Integer, InputStream>() {
            public Integer call(InputStream inputStream) throws Exception {
                return inputStream.read( uncompressedPad );
            }
        });

        pad = new Huffman().encode( uncompressedPad );// compress( uncompressedPad );
        int i = 0;
    }

    public boolean isSet() {
        return pad != null;
    }

    public FaustByteNum() {
        num2line = new List[256];
        for ( int i = 0; i < 256; ++i ) {
            num2line[i] = new ArrayList<String>();
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader( new InputStreamReader( getClass().getResource( "faust.txt" ).openStream()) );

            int idx = 0;
            for ( String str : Iterators.lines( reader ) ) {
                if ( str.contains(":") || str.contains("(") || str.trim().equals( "" )) {
                    continue;
                }

                if ( knownLines.contains(str )) {
                    continue;
                }

                ++idx;
                knownLines.add( str );
                num2line[idx % 256].add(str);
                line2num.put( str, idx % 256 );
            }


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            IOUtils.closeQuietly( reader );
        }
    }

    public String encode( Byte ch, int idx ) {
        if ( pad == null ) {
            throw new IllegalStateException("no pad yet");
        }

        List<String> posi =  num2line[xor(ch, getByte(idx)) - Byte.MIN_VALUE];
        return posi.get(((int)(Math.random() * posi.size())));
    }

    public Byte decode( String line, int idx ) {
        if ( pad == null ) {
            throw new IllegalStateException("no pad yet");
        }

        return xor((byte)((line2num.get( line ).intValue()) + Byte.MIN_VALUE), getByte(idx));
    }

    public static byte xor( byte a, byte b ) {
        return (byte)((int)a ^ (int)b);
    }



//    private byte[] compress( byte[] input ) {
//
//        // Create the compressor with highest level of compression
//        Deflater compressor = new Deflater();
//        compressor.setLevel(Deflater.BEST_COMPRESSION);
//
//        // Give the compressor the data to compress
//        compressor.setInput(input);
//        compressor.finish();
//
//        // Create an expandable byte array to hold the compressed data.
//        // You cannot use an array that's the same size as the orginal because
//        // there is no guarantee that the compressed data will be smaller than
//        // the uncompressed data.
//        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
//
//        // Compress the data
//        byte[] buf = new byte[1024];
//        while (!compressor.finished()) {
//            int count = compressor.deflate(buf);
//            bos.write(buf, 0, count);
//        }
//        try {
//            bos.close();
//        } catch (IOException e) {
//        }
//
//        // Get the compressed data
//        return bos.toByteArray();
//    }


    private byte getByte( int idx) {
        return pad.getByteModulo( idx );
    }




}
