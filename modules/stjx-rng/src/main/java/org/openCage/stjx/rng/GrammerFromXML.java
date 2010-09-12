package org.openCage.stjx.rng;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.events.EntityDeclaration;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Locale;

public class GrammerFromXML extends DefaultHandler {

   public class ListHelper<T> {
      private List<T> list;
      public  ListHelper( List<T> list ){
         this.list = list;
      }
      public void add( T elem ){
         list.add( elem );
      }

   }
   private Stack stack = new Stack();
   private Object goal;
   private boolean getCharacters;
   @Override public void startDocument(  ) throws SAXException {
      stack.clear();
   }
   @Override public void endDocument(  ) throws SAXException {
   }
   public Grammer getGoal(  ){
      return ((Grammer)goal);
   }
   @Override public void startElement( String uri, String localName, String qName, Attributes attributes ) throws SAXException {
      if( qName.equals( "define" ) ){
         Define elem = new Define();
         if( attributes.getValue( "name" ) != null ){
            elem.setName( attributes.getValue( "name" ) );
         } else {
            throw new IllegalArgumentException( "Define: attribute name is required" );
         }
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof Grammer ){
               ((Grammer)peek).getDefineList().add( elem );
            }
         }
         stack.push( elem );
         return;
      }
      if( qName.equals( "element" ) ){
         Element elem = new Element();
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof Define ){
               ((Define)peek).setElement( elem );
            }
         }
         stack.push( elem );
         return;
      }
      if( qName.equals( "text" ) ){
         Text elem = new Text();
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof Attribute ){
               ((Attribute)peek).setText( elem );
            }
         }
         stack.push( elem );
         return;
      }
      if( qName.equals( "choice" ) ){
         Choice elem = new Choice();
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof Attribute ){
               ((Attribute)peek).setChoice( elem );
            }
         }
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof Element ){
               ((Element)peek).getChoiceList().add( elem );
            }
         }
         stack.push( elem );
         return;
      }
      if( qName.equals( "attribute" ) ){
         Attribute elem = new Attribute();
         if( attributes.getValue( "name" ) != null ){
            elem.setName( attributes.getValue( "name" ) );
         } else {
            throw new IllegalArgumentException( "Attribute: attribute name is required" );
         }
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof Element ){
               ((Element)peek).getAttributeList().add( elem );
            }
         }
         stack.push( elem );
         return;
      }
      if( qName.equals( "grammer" ) ){
         Grammer elem = new Grammer();
         stack.push( elem );
         return;
      }
      if( qName.equals( "zeroOrMore" ) ){
         ZeroOrMore elem = new ZeroOrMore();
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof Element ){
               ((Element)peek).getZeroOrMoreList().add( elem );
            }
         }
         stack.push( elem );
         return;
      }
      if( qName.equals( "ref" ) ){
         Ref elem = new Ref();
         if( attributes.getValue( "name" ) != null ){
            elem.setName( attributes.getValue( "name" ) );
         } else {
            throw new IllegalArgumentException( "Ref: attribute name is required" );
         }
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof ZeroOrMore ){
               ((ZeroOrMore)peek).setRef( elem );
            }
            if( peek instanceof Start ){
               ((Start)peek).setRef( elem );
            }
            if( peek instanceof Optional ){
               ((Optional)peek).setRef( elem );
            }
         }
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof Choice ){
               ((Choice)peek).getRefList().add( elem );
            }
         }
         stack.push( elem );
         return;
      }
      if( qName.equals( "start" ) ){
         Start elem = new Start();
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof Grammer ){
               ((Grammer)peek).setStart( elem );
            }
         }
         stack.push( elem );
         return;
      }
      if( qName.equals( "optional" ) ){
         Optional elem = new Optional();
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof Element ){
               ((Element)peek).getOptionalList().add( elem );
            }
         }
         stack.push( elem );
         return;
      }
      if( qName.equals( "value" ) ){
         getCharacters = true;
         return;
      }
      throw new IllegalArgumentException( "unknown tag " + qName );
   }
   @Override public void endElement( String uri, String localName, String qName ) throws SAXException {
      if( qName.equals( "define" ) ){
         goal = stack.pop();
         if( ((Define)goal).getElement() == null ){
            throw new IllegalArgumentException( "Define: required member element not set" );
         }
      }
      if( qName.equals( "element" ) ){
         goal = stack.pop();
      }
      if( qName.equals( "text" ) ){
         goal = stack.pop();
      }
      if( qName.equals( "choice" ) ){
         goal = stack.pop();
      }
      if( qName.equals( "attribute" ) ){
         goal = stack.pop();
      }
      if( qName.equals( "grammer" ) ){
         goal = stack.pop();
         if( ((Grammer)goal).getStart() == null ){
            throw new IllegalArgumentException( "Grammer: required member start not set" );
         }
      }
      if( qName.equals( "zeroOrMore" ) ){
         goal = stack.pop();
         if( ((ZeroOrMore)goal).getRef() == null ){
            throw new IllegalArgumentException( "ZeroOrMore: required member ref not set" );
         }
      }
      if( qName.equals( "ref" ) ){
         goal = stack.pop();
      }
      if( qName.equals( "start" ) ){
         goal = stack.pop();
         if( ((Start)goal).getRef() == null ){
            throw new IllegalArgumentException( "Start: required member ref not set" );
         }
      }
      if( qName.equals( "optional" ) ){
         goal = stack.pop();
         if( ((Optional)goal).getRef() == null ){
            throw new IllegalArgumentException( "Optional: required member ref not set" );
         }
      }
      if( qName.equals( "value" ) ){
         String str = "";
         while( stack.peek() instanceof String ){
            str = ((String)stack.pop()) + str;
         }
         if( ! stack.empty() ){
            Object peek = stack.peek();
            if( peek instanceof Choice ){
               ((Choice)peek).getValueList().add( str );
            }
         }
         getCharacters = false;
      }
   }
   public void characters( char[] ch, int start, int length ){
      if( getCharacters ){
         StringBuffer sb = new StringBuffer();
         for ( int idx = start; (idx < ch.length) && (idx < (start + length)); ++ idx){
            sb.append( ch[idx] );
         }
         stack.push( sb.toString() );
      }
   }

}
