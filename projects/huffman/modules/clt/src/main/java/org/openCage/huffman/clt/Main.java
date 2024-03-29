package org.openCage.huffman.clt;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.openCage.huffman.BitField;
import org.openCage.huffman.BitList;
import org.openCage.huffman.HuffmanN;
import org.openCage.io.Resource;
import org.openCage.io.fspath.FSPath;
import org.openCage.io.fspath.FSPathBuilder;
import org.openCage.lang.functions.FE1;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

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

public class Main {

    @Argument(required = true)
    private String source;
    private URI uriSource;

    @Option(name = "-b", aliases = "--wordLength")
    private int wordLength;

    @Option( name = "-d", aliases = {"--decode"}, required = false )
    private boolean decode = false;

    @Option( name = "-o", aliases = {"--output"},required = true )
    private String target;
    private FSPath targetPath;


    public static void main(String[] args) {
        // parse the command line arguments and options
        Main values = new Main();
        CmdLineParser parser = new CmdLineParser(values);
        parser.setUsageWidth(80); // width of the error display area

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("huffman [options...] arguments...");
            parser.printUsage( System.err );
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
            System.exit(1);
        }

        System.out.println( values.source );

        values.init();


        if ( values.decode ) {
            values.decode();
        } else {
            values.encode();
        }
    }

    public void init() {
        getURI();
        getTarget();
    }

    public void getURI() {
        try {
            uriSource = new URI( source );
            if ( uriSource.getScheme() != null ) {
                return;
            }
        } catch (URISyntaxException e) {
        }

        FSPath path = FSPathBuilder.getPath( new File(source) );
        if ( !path.toFile().exists() ){
            throw new IllegalArgumentException("no such file" + source );
        }

        uriSource = path.toURI();
    }

    public void getTarget() {
        targetPath = FSPathBuilder.getPath( new File(target) );
    }

    public void encode()  {

        Resource.tryWith( new FE1<Object, Resource>() {
            @Override public Object call(Resource resource) throws Exception {

                BitField encoded = new HuffmanN(BitList.valueOf(resource.add(new BufferedInputStream(uriSource.toURL().openStream())))).encode(8);

                OutputStream os = resource.add( new FileOutputStream( targetPath.toFile()));

                os.write( encoded.toByteArray() );

                return null;
            }
        });
    }

    public void decode() {
        Resource.tryWith( new FE1<Object, Resource>() {
            @Override public Object call(Resource resource) throws Exception {
                BitField decoded = HuffmanN.decode( BitList.valueOf(resource.add(new BufferedInputStream(uriSource.toURL().openStream()))));
                OutputStream os = resource.add( new FileOutputStream( targetPath.toFile()));

                os.write( decoded.toByteArray() );
                return null;
            }
        });
    }

}
