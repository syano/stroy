package org.openCage.lispaffair;
import org.openCage.lishp.Function;
import org.openCage.lishp.Special;
import org.openCage.lishp.Symbol;

import java.text.*;
import java.util.*;
import java.io.*;

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

public class LispFormat extends Format{
    
    /** Creates a new instance of LispFormat */
    public LispFormat() {
    }
    
    
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {

        if (toAppendTo == null || pos == null) {
            throw new NullPointerException();
        }
        
        if (obj == null) {
            toAppendTo.append( "#n" );
            return toAppendTo;
        }
        
        if ( obj instanceof List ) {
            List lst = (List)obj;
            
            if ( lst.isEmpty() ) {
                toAppendTo.append( "#n" );
                return toAppendTo;                
            }
            
            toAppendTo.append( "(");
                        
            ListIterator it = lst.listIterator();
            while ( it.hasNext() ) {
                toAppendTo = format( it.next(), toAppendTo, pos );
                if ( it.hasNext() ) {
                    toAppendTo.append( " ");
                }
            }
            
            toAppendTo.append( ")");                        
            
            return toAppendTo;
        }        
        
        if ( obj instanceof String ) {
            toAppendTo.append( '"' );
            toAppendTo.append( obj );
            toAppendTo.append( '"' );
            return toAppendTo;
        }

        if ( obj instanceof Boolean ) {
            
            if ( ((Boolean)obj).booleanValue() ) {            
                toAppendTo.append( "#t" );
            } else {
                toAppendTo.append( "#f" );
            }

            return toAppendTo;            
        }
        
        if ( obj instanceof Function ) {
            ((Function)obj).format( toAppendTo );
            return toAppendTo;
        }
        
        if ( obj instanceof Special ) {
            ((Special)obj).format( toAppendTo );
            return toAppendTo;            
        }

        if ( obj instanceof Macro ) {
            ((Macro)obj).format( toAppendTo );
            return toAppendTo;            
        }
        
        toAppendTo.append( "" + obj );
        return toAppendTo;
        
//        throw new NullPointerException("not impl");            
        
    }
    
    public Object parseObject(String source, ParsePosition pos) {

        InputStream is = new ByteArrayInputStream(source.getBytes());

        BufferedReader rd = new BufferedReader( 
                            new InputStreamReader(is));
        

        StreamTokenizer tokenizer = new StreamTokenizer( rd );
        tokenizer.parseNumbers();
        tokenizer.commentChar( ';' );
        tokenizer.quoteChar( '"' );
        tokenizer.wordChars( ':',':' ); 
        tokenizer.wordChars( '#','#' ); 
        tokenizer.wordChars( '<','<' ); 
        tokenizer.wordChars( '=','=' ); 
        tokenizer.wordChars( '>','>' ); 
        tokenizer.wordChars( '+','+' ); 
        tokenizer.wordChars( '-','-' ); 
        tokenizer.wordChars( '/','/' ); 
        tokenizer.wordChars( '*','*' ); 
        tokenizer.wordChars( '%','%' ); 
        tokenizer.ordinaryChar( ',' ); 
        tokenizer.ordinaryChar( '\'' ); 
        tokenizer.ordinaryChar( '`'); 
        tokenizer.ordinaryChar( '.');

        try {
            pos.setIndex(1);
            tokenizer.nextToken();
            return parseObject( tokenizer );
        } catch ( java.io.IOException e ) {
        }
        return null;
    }

    private Object parseObject( StreamTokenizer tokenizer ) throws IOException {

        int type = tokenizer.ttype;

        switch (type) {
            case StreamTokenizer.TT_NUMBER:
                return new Integer( (int)tokenizer.nval );
            case StreamTokenizer.TT_WORD:
                if ( tokenizer.sval.startsWith( "#" )) {                                    
                    return parseReaderMacro( tokenizer );
                }
                return Symbol.get( tokenizer.sval);
            case '"':
                return tokenizer.sval;
            case '(':
                return parseList( tokenizer );
            case '\'':
                return parseQuote( tokenizer );
            case '`':
                return parseBackQuote( tokenizer );
            case ',':
                return parseComma( tokenizer );
            case '-':
                // return Symbol.get( tokenizer.sval ); is null probably because of number parsing
                return Symbol.get( "-" );
            case '.':
                return Symbol.get(".");
            default:
              System.out.println("else: type " + type + " " + tokenizer.sval);
              break;
//!= ) {
        }
        
        return null;
        
    }

    private Object parseQuote( StreamTokenizer tokenizer ) throws IOException{

        LinkedList lst = new LinkedList(); 
        lst.add( BuildinSpecials.specialQuote );
        tokenizer.nextToken();
        lst.add( parseObject( tokenizer ));        
        return lst;        
    }

    private Object parseBackQuote( StreamTokenizer tokenizer ) throws IOException{

        LinkedList lst = new LinkedList(); 
        lst.add( BuildinSpecials.specialBackQuote );
        tokenizer.nextToken();
        lst.add( parseObject( tokenizer ));        
        return lst;        
    }
    private Object parseComma( StreamTokenizer tokenizer ) throws IOException{

        LinkedList lst = new LinkedList(); 
        lst.add( BuildinSpecials.specialComma );
        tokenizer.nextToken();
        lst.add( parseObject( tokenizer ));        
        return lst;        
    }
    
    private Object parseList( StreamTokenizer tokenizer ) throws IOException{

        LinkedList lst = new LinkedList();
        
        while ( true ) {
            
            int type = tokenizer.nextToken();
            
            switch (type) {
                case StreamTokenizer.TT_EOF:
                    return null;
                case ')':
                    return lst;
                default:
                    lst.addLast( parseObject( tokenizer ));
                    break;            
            }
        }                        
    }

    private Object parseReaderMacro( StreamTokenizer tokenizer ) throws IOException{

//        int type = tokenizer.ttype;
//
//        if ( type != StreamTokenizer.TT_WORD ) {
//            System.out.println("reader macro: ow");
//            return null;
//        }
//        
//        if ( !readerMacros.containsKey( tokenizer.sval )) {
//            System.out.println("reader macro: ow 2");
//            return null;            
//        }
        
        return readerMacros.get( tokenizer.sval );
    }
    

    public static boolean addReaderMacro( String key, Object obj ) {
        if ( readerMacros.containsKey( key )) {
            return false;
        }
        
        readerMacros.put( key, obj );
        return true;
        
    }
    
    private static Hashtable readerMacros = new Hashtable();
       
}
