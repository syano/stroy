package org.openCage.artig.stjx;

import java.util.List;

/**
 * ** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 * <p/>
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * <p/>
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * <p/>
 * The Original Code is stroy code
 * <p/>
 * The Initial Developer of the Original Code is Stephan Pfab <openCage@gmail.com>.
 * Portions created by Stephan Pfab are Copyright (C) 2006 - 2010.
 * All Rights Reserved.
 * <p/>
 * Contributor(s):
 * **** END LICENSE BLOCK ****
*/
public class ArtifactDescriptionToXML {
   public static  String toStringCLT( String prefix, CLT cLT ){
      String ret = prefix;
      ret += "<CLT ";
      if( cLT.getMainClass() != null ){
         ret += "mainClass=\"" + cLT.getMainClass() + "\" ";
      }
      ret += "/>\n";
      return ret;
   }
   public static  String toStringLib( String prefix, Lib lib ){
      String ret = prefix;
      ret += "<Lib ";
      if( lib.getMainClass() != null ){
         ret += "mainClass=\"" + lib.getMainClass() + "\" ";
      }
      ret += "/>\n";
      return ret;
   }
   public static  String toStringLicences( String prefix, List<Licence> licences ){
      if( licences.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<licences>\n";
      for ( Licence vr : licences ) {
         ret += toStringLicence( prefix + "   ", vr );
      }
      ret += prefix + "</licences>\n";
      return ret;
   }
   public static  String toStringDropIns( String prefix, List<ArtifactRef> dropIns ){
      if( dropIns.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<dropIns>\n";
      for ( ArtifactRef vr : dropIns ) {
         ret += toStringArtifactRef( prefix + "   ", vr );
      }
      ret += prefix + "</dropIns>\n";
      return ret;
   }
   public static  String toStringAuthor( String prefix, Author author ){
      String ret = prefix;
      ret += "<Author ";
      if( author.getName() != null ){
         ret += "name=\"" + author.getName() + "\" ";
      }
      if( author.getEmail() != null ){
         ret += "email=\"" + author.getEmail() + "\" ";
      }
      ret += "/>\n";
      return ret;
   }
   public static  String toStringLicence( String prefix, Licence licence ){
      String ret = prefix;
      ret += "<Licence ";
      if( licence.getName() != null ){
         ret += "name=\"" + licence.getName() + "\" ";
      }
      if( licence.getVersion() != null ){
         ret += "version=\"" + licence.getVersion() + "\" ";
      }
      ret += ">\n";
      if( licence.getAddress() != null ){
         ret += toStringAddress( prefix + "   ", licence.getAddress() );
      }
      if( licence.getPositives() != null ){
         ret += toStringPositives( prefix + "   ", licence.getPositives() );
      }
      if( licence.getNegatives() != null ){
         ret += toStringNegatives( prefix + "   ", licence.getNegatives() );
      }
      ret += prefix + "</Licence>\n";
      return ret;
   }
   public static  String toStringNegatives( String prefix, List<LicenceRef> negatives ){
      if( negatives.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<negatives>\n";
      for ( LicenceRef vr : negatives ) {
         ret += toStringLicenceRef( prefix + "   ", vr );
      }
      ret += prefix + "</negatives>\n";
      return ret;
   }
   public static  String toStringExternals( String prefix, List<Artifact> externals ){
      if( externals.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<externals>\n";
      for ( Artifact vr : externals ) {
         ret += toStringArtifact( prefix + "   ", vr );
      }
      ret += prefix + "</externals>\n";
      return ret;
   }
   public static  String toStringModule( String prefix, Module module ){
      String ret = prefix;
      ret += "<Module ";
      ret += ">\n";
      if( module.getArtifact() != null ){
         ret += toStringArtifact( prefix + "   ", module.getArtifact() );
      }
      if( module.getModuleKind() != null ){
         ret += toStringModuleKind( prefix + "   ", module.getModuleKind() );
      }
      ret += prefix + "</Module>\n";
      return ret;
   }
   public static  String toStringJava( String prefix, Java java ){
      String ret = prefix;
      ret += "<Java ";
      if( java.getMin() != null ){
         ret += "min=\"" + java.getMin() + "\" ";
      }
      if( java.getMax() != null ){
         ret += "max=\"" + java.getMax() + "\" ";
      }
      ret += "/>\n";
      return ret;
   }
   public static  String toStringKind( String prefix, Kind kind ){
      if( kind instanceof Module ){
         return toStringModule( prefix, ((Module)kind) );
      }
      if( kind instanceof Project ){
         return toStringProject( prefix, ((Project)kind) );
      }
      if( kind instanceof Deployed ){
         return toStringDeployed( prefix, ((Deployed)kind) );
      }
      throw new IllegalStateException( "no a valid suptype of Kind" );
   }
   public static  String toStringPositives( String prefix, List<LicenceRef> positives ){
      if( positives.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<positives>\n";
      for ( LicenceRef vr : positives ) {
         ret += toStringLicenceRef( prefix + "   ", vr );
      }
      ret += prefix + "</positives>\n";
      return ret;
   }
   public static  String toStringLanguages( String prefix, List<Language> languages ){
      if( languages.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<languages>\n";
      for ( Language vr : languages ) {
         ret += toStringLanguage( prefix + "   ", vr );
      }
      ret += prefix + "</languages>\n";
      return ret;
   }
   public static  String toStringAuthors( String prefix, List<Author> authors ){
      if( authors.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<authors>\n";
      for ( Author vr : authors ) {
         ret += toStringAuthor( prefix + "   ", vr );
      }
      ret += prefix + "</authors>\n";
      return ret;
   }
   public static  String toStringModuleRef( String prefix, ModuleRef moduleRef ){
      String ret = prefix;
      ret += "<ModuleRef ";
      if( moduleRef.getName() != null ){
         ret += "name=\"" + moduleRef.getName() + "\" ";
      }
      ret += "/>\n";
      return ret;
   }
   public static  String toStringDropInFor( String prefix, DropInFor dropInFor ){
      String ret = prefix;
      ret += "<DropInFor ";
      ret += ">\n";
      if( dropInFor.getArtifactRef() != null ){
         ret += toStringArtifactRef( prefix + "   ", dropInFor.getArtifactRef() );
      }
      ret += prefix + "</DropInFor>\n";
      return ret;
   }
   public static  String toStringLicenceRef( String prefix, LicenceRef licenceRef ){
      String ret = prefix;
      ret += "<LicenceRef ";
      if( licenceRef.getName() != null ){
         ret += "name=\"" + licenceRef.getName() + "\" ";
      }
      ret += "/>\n";
      return ret;
   }
   public static  String toStringDependencies( String prefix, List<Artifact> dependencies ){
      if( dependencies.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<dependencies>\n";
      for ( Artifact vr : dependencies ) {
         ret += toStringArtifact( prefix + "   ", vr );
      }
      ret += prefix + "</dependencies>\n";
      return ret;
   }
   public static  String toStringArtifactDescription( String prefix, ArtifactDescription artifactDescription ){
      String ret = prefix;
      ret += "<ArtifactDescription ";
      if( artifactDescription.getVersion() != null ){
         ret += "version=\"" + artifactDescription.getVersion() + "\" ";
      }
      ret += ">\n";
      if( artifactDescription.getKind() != null ){
         ret += toStringKind( prefix + "   ", artifactDescription.getKind() );
      }
      ret += prefix + "</ArtifactDescription>\n";
      return ret;
   }
   public static  String toStringProject( String prefix, Project project ){
      String ret = prefix;
      ret += "<Project ";
      if( project.getName() != null ){
         ret += "name=\"" + project.getName() + "\" ";
      }
      if( project.getGroupId() != null ){
         ret += "groupId=\"" + project.getGroupId() + "\" ";
      }
      ret += ">\n";
      if( project.getModules() != null ){
         ret += toStringModules( prefix + "   ", project.getModules() );
      }
      if( project.getExternals() != null ){
         ret += toStringExternals( prefix + "   ", project.getExternals() );
      }
      if( project.getLicences() != null ){
         ret += toStringLicences( prefix + "   ", project.getLicences() );
      }
      if( project.getDropIns() != null ){
         ret += toStringDropIns( prefix + "   ", project.getDropIns() );
      }
      ret += prefix + "</Project>\n";
      return ret;
   }
   public static  String toStringDepends( String prefix, List<ArtifactRef> depends ){
      if( depends.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<depends>\n";
      for ( ArtifactRef vr : depends ) {
         ret += toStringArtifactRef( prefix + "   ", vr );
      }
      ret += prefix + "</depends>\n";
      return ret;
   }
   public static  String toStringDownload( String prefix, Download download ){
      String ret = prefix;
      ret += "<Download ";
      if( download.getScreenshot() != null ){
         ret += "screenshot=\"" + download.getScreenshot() + "\" ";
      }
      if( download.getIcon() != null ){
         ret += "icon=\"" + download.getIcon() + "\" ";
      }
      if( download.getDownload() != null ){
         ret += "download=\"" + download.getDownload() + "\" ";
      }
      ret += "/>\n";
      return ret;
   }
   public static  String toStringContributors( String prefix, List<Author> contributors ){
      if( contributors.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<contributors>\n";
      for ( Author vr : contributors ) {
         ret += toStringAuthor( prefix + "   ", vr );
      }
      ret += prefix + "</contributors>\n";
      return ret;
   }
   public static  String toStringDeployed( String prefix, Deployed deployed ){
      String ret = prefix;
      ret += "<Deployed ";
      if( deployed.getIcon() != null ){
         ret += "icon=\"" + deployed.getIcon() + "\" ";
      }
      ret += ">\n";
      if( deployed.getArtifact() != null ){
         ret += toStringArtifact( prefix + "   ", deployed.getArtifact() );
      }
      if( deployed.getDependencies() != null ){
         ret += toStringDependencies( prefix + "   ", deployed.getDependencies() );
      }
      if( deployed.getLicences() != null ){
         ret += toStringLicences( prefix + "   ", deployed.getLicences() );
      }
      ret += prefix + "</Deployed>\n";
      return ret;
   }
   public static  String toStringArtifactRef( String prefix, ArtifactRef artifactRef ){
      String ret = prefix;
      ret += "<ArtifactRef ";
      if( artifactRef.getGroupId() != null ){
         ret += "groupId=\"" + artifactRef.getGroupId() + "\" ";
      }
      if( artifactRef.getName() != null ){
         ret += "name=\"" + artifactRef.getName() + "\" ";
      }
      if( artifactRef.getVersion() != null ){
         ret += "version=\"" + artifactRef.getVersion() + "\" ";
      }
      if( artifactRef.getScope() != null ){
         ret += "scope=\"" + artifactRef.getScope() + "\" ";
      }
      ret += "/>\n";
      return ret;
   }
   public static  String toStringArtifact( String prefix, Artifact artifact ){
      String ret = prefix;
      ret += "<Artifact ";
      if( artifact.getGroupId() != null ){
         ret += "groupId=\"" + artifact.getGroupId() + "\" ";
      }
      if( artifact.getName() != null ){
         ret += "name=\"" + artifact.getName() + "\" ";
      }
      if( artifact.getVersion() != null ){
         ret += "version=\"" + artifact.getVersion() + "\" ";
      }
      if( artifact.getLicence() != null ){
         ret += "licence=\"" + artifact.getLicence() + "\" ";
      }
      if( artifact.getDescription() != null ){
         ret += "description=\"" + artifact.getDescription() + "\" ";
      }
      if( artifact.getSupport() != null ){
         ret += "support=\"" + artifact.getSupport() + "\" ";
      }
      ret += ">\n";
      if( artifact.getDepends() != null ){
         ret += toStringDepends( prefix + "   ", artifact.getDepends() );
      }
      if( artifact.getAuthors() != null ){
         ret += toStringAuthors( prefix + "   ", artifact.getAuthors() );
      }
      if( artifact.getContributors() != null ){
         ret += toStringContributors( prefix + "   ", artifact.getContributors() );
      }
      if( artifact.getAddress() != null ){
         ret += toStringAddress( prefix + "   ", artifact.getAddress() );
      }
      if( artifact.getLanguages() != null ){
         ret += toStringLanguages( prefix + "   ", artifact.getLanguages() );
      }
      if( artifact.getJava() != null ){
         ret += toStringJava( prefix + "   ", artifact.getJava() );
      }
      if( artifact.getRefs() != null ){
         ret += toStringRefs( prefix + "   ", artifact.getRefs() );
      }
      if( artifact.getDropInFor() != null ){
         ret += toStringDropInFor( prefix + "   ", artifact.getDropInFor() );
      }
      if( artifact.getFullDescription() != null ){
         ret += toStringFullDescription( prefix + "   ", artifact.getFullDescription() );
      }
      ret += prefix + "</Artifact>\n";
      return ret;
   }
   public static  String toStringApp( String prefix, App app ){
      String ret = prefix;
      ret += "<App ";
      if( app.getMainClass() != null ){
         ret += "mainClass=\"" + app.getMainClass() + "\" ";
      }
      if( app.getIcon() != null ){
         ret += "icon=\"" + app.getIcon() + "\" ";
      }
      ret += ">\n";
      if( app.getDownload() != null ){
         ret += toStringDownload( prefix + "   ", app.getDownload() );
      }
      ret += prefix + "</App>\n";
      return ret;
   }
   public static  String toStringRefs( String prefix, List<ArtifactRef> refs ){
      if( refs.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<refs>\n";
      for ( ArtifactRef vr : refs ) {
         ret += toStringArtifactRef( prefix + "   ", vr );
      }
      ret += prefix + "</refs>\n";
      return ret;
   }
   public static  String toStringModuleKind( String prefix, ModuleKind moduleKind ){
      if( moduleKind instanceof App ){
         return toStringApp( prefix, ((App)moduleKind) );
      }
      if( moduleKind instanceof CLT ){
         return toStringCLT( prefix, ((CLT)moduleKind) );
      }
      if( moduleKind instanceof Lib ){
         return toStringLib( prefix, ((Lib)moduleKind) );
      }
      throw new IllegalStateException( "no a valid suptype of ModuleKind" );
   }
   public static  String toStringAddress( String prefix, Address address ){
      String ret = prefix;
      ret += "<Address ";
      if( address.getPage() != null ){
         ret += "page=\"" + address.getPage() + "\" ";
      }
      if( address.getShrt() != null ){
         ret += "shrt=\"" + address.getShrt() + "\" ";
      }
      ret += "/>\n";
      return ret;
   }
   public static  String toStringFullDescription( String prefix, String fullDescription ){
      return prefix + "<FullDescription>" + fullDescription + "</FullDescription>\n";
   }
   public static  String toStringLanguage( String prefix, Language language ){
      String ret = prefix;
      ret += "<Language ";
      if( language.getName() != null ){
         ret += "name=\"" + language.getName() + "\" ";
      }
      ret += "/>\n";
      return ret;
   }
   public static  String toStringModules( String prefix, List<ModuleRef> modules ){
      if( modules.isEmpty() ){
         return "";
      }
      String ret = prefix;
      ret += "<modules>\n";
      for ( ModuleRef vr : modules ) {
         ret += toStringModuleRef( prefix + "   ", vr );
      }
      ret += prefix + "</modules>\n";
      return ret;
   }

}
