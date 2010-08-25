package org.openCage.artig;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.openCage.artig.stjx.*;
import org.openCage.io.FileUtils;
import org.openCage.io.fspath.FSPath;
import org.openCage.lang.Strings;
import org.openCage.lang.errors.Unchecked;
import org.openCage.lang.functions.F1;
import org.openCage.lang.iterators.Count;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElephantsGen {

    private final Project project;

    private Artig artig;


    public ElephantsGen( @NotNull Artig artig) { //}, FSPath baseDir  ) {
        this.project = artig.getProject();
        this.artig = artig;
    }

    public void generate() {

        FSPath rootPom = artig.getRootPath().add( "dependencies.xml" );
        FileUtils.ensurePath( rootPom );

        FileWriter writer = null;
        try {
            writer = new FileWriter( rootPom.toFile() );

            writer.write( deps());

        } catch (IOException e) {
            throw Unchecked.wrap( e );
        } finally {
            IOUtils.closeQuietly( writer );
        }

          for ( ModuleRef mod : artig.getProject().getModules() ) {
            FSPath modPom = artig.getRootPath().add( "modules", mod.getName(), "build.xml" );
            FileUtils.ensurePath( modPom );

            try {
                writer = new FileWriter( modPom.toFile() );


                writer.write( buildxml( artig.get( mod )));

            } catch (IOException e) {
                throw Unchecked.wrap( e );
            } finally {
                IOUtils.closeQuietly( writer );
            }

        }

    }



    public String buildxml( Module mod ) {

        Artifact arti = mod.getArtifact();

        String ret = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project name=\"" + mod.getArtifact().getName() + "\" default=\"default\" basedir=\".\">\n\n" +
                "   <!-- generated by artig -->\n\n";



        ret += antProperty( "el.version", arti.getVersion()) + "\n";
//        ret += antProperty( "el.buildnumber", "" + arti.getVersion().getBuildNumber()) + "\n";
        ret += antProperty( "el.groupId", arti.getGroupId()) + "\n";
        ret += antProperty( "el.src.java", "src/main/java") + "\n";
        ret += antProperty( "el.src.resources", "src/main/resources") + "\n";

        if ( mod.getApp() != null ) {
            ret += antProperty( "el.isApp", "true") + "\n";
            ret += antProperty( "el.main.class", mod.getApp().getMainClass() ) + "\n";
        }

        ret += antProperty( "el.description.short", "TODO descr") + "\n";
        ret += antProperty( "el.licence", arti.getLicence()) + "\n";
        ret += antProperty( "el.localization", Strings.join( arti.getLanguages()).trans( new F1<String, Language>() {
            public String call(Language language) {
                return language.getName();
            }
        }).toString()) + "\n";
        ret += antProperty( "el.webpage", arti.getAddress().getPage()) + "\n";
        ret += antProperty( "el.email", arti.getSupport()) + "\n";
        ret += antProperty( "el.java.version", arti.getJava().getMin()) + "\n";

        List<String> names = new ArrayList<String>();
        for ( Author author : arti.getAuthors() ) {
            names.add( author.getName());
        }
        ret += antProperty( "el.author", Strings.join( names ).toString()) + "\n";
        if ( names.size() > 0 ) {
            String firstAuthor = names.get(0);

            ret += antProperty( "el.author.first", firstAuthor.substring(0, firstAuthor.lastIndexOf(' '))) + "\n";
            ret += antProperty( "el.author.last", firstAuthor.substring( firstAuthor.lastIndexOf(' ') + 1)) + "\n";
        }

        if ( mod.getApp() != null ) {
            //if ( mod.getApp().getDownload().getScreenshot().isSet() ) {
                ret += antProperty( "el.url.screenshot", mod.getApp().getDownload().getScreenshot() ) + "\n";
            //}
            //if ( arti.getIconUrl().isSet() ) {
                ret += antProperty( "el.url.icon", mod.getApp().getDownload().getIcon() ) + "\n";
            //}
            //if ( arti.getDownloadUrl().isSet() ) {
                ret += antProperty( "el.url.download", mod.getApp().getDownload().getDownload() ) + "\n";
            //}
        }


        ret += "\n   <import file=\"../../build-resources/build-common.xml\"/>\n" +
                "</project>";
        return ret;
    }

    private String antProperty( String key, String val ) {
        return "   <property name = \"" + key + "\"       value=\"" + val + "\" />";
    }

    public String deps() {
        String ret =
                "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                "<project name=\"dependencies\">\n\n" +
                        "  <!-- ===== generated by artig ======== -->\n\n" +
                "   <dirname property=\"dependencies.basedir\" file=\"${ant.file.dependencies}\"/>\n\n";


        for ( ModuleRef modu : artig.getProject().getModules() ) {
            ret += writeModule( modu );
        }

        ret += "\n" +
                "   <!-- ================================================================== -->\n" +
                "   <!--     external library dependencies                                  --> \n" +
                "   <!-- ================================================================== -->    \n" +
                "\n";

        for ( Artifact arti  : artig.getProject().getExternals() ) {
            ret += writeExternal( arti );
        }

//        for ( Artifact arti : project.getAll() ) {
//            Artifact ch = arti; //.getChoice();
//            if ( !project.isModule( ch )) {
//                ret += writeExternal(ch);
//            }
//        }

        ret += "\n\n</project>\n";



        return ret;
    }

    //    <!-- ================================================================== -->
    //    <target name= "launch4j"
    //            depends="xstream">
    //        <copy todir="${dependencies.basedir}/build/libs" file="${dependencies.basedir}/external/production/launch4j.jar"/>
    //    </target>
    private String writeExternal(Artifact arti) {
        String ret = "   <!-- ================================================================== -->\n";
        ret += "   <target name=\""+ arti.getName() +"\"";

        ret +=
            Strings.join( arti.getDepends() ).
                    prefix( "\n           depends=\"" ).
                    postfix( "\"").
                    skip( new F1<Boolean,ArtifactRef>( ) {
                        @Override public Boolean call(ArtifactRef ref) {
                            return ref.getScope() != null && !ref.getScope().equals( "compile" );
                        }
                    }).
                    trans( new F1<String,ArtifactRef>() {
                        @Override public String call(ArtifactRef ref) {
                            return getModuleName( ref );
                        }
                    });

//        if ( arti.getCompileDependencies().size() > 0 ) {
//            ret += "\n           depends=\"";
//
//            for ( Count<Artifact> dep : Count.count(arti.getCompileDependencies()) ) {
//                ret += dep.obj().getChoice().gettName();
//                if ( !dep.isLast()) {
//                    ret += ", ";
//                }
//            }
//            ret += "\"";
//        }

        ret += ">\n";

        ret += "      <copy todir=\"${dependencies.basedir}/build/libs\" file=\"${dependencies.basedir}/repo/"+ getLibraryLocation( arti )+"\" />\n";

        ret += "   </target>\n\n";
        return ret;
    }

    private String getLibraryLocation(Artifact arti) {
        return arti.getGroupId().replace('.', '/')  + "/" + arti.getName() + "/" + arti.getVersion() + "/" + arti.getName() + "-" + arti.getVersion() + ".jar" ;
    }

    //    <!-- ================================================================== -->
    //    <target name= "depend.ui"
    //            depends="depend.localization, depend.application, depend.io, designgridlayout, depend.lang, guice, javagraphics-preferencepanel, RSyntaxTextArea, jgoodies-binding">
    //        <ant dir="${dependencies.basedir}/modules/ui" target="local.goal" inheritAll="false"/>
    //    </target>
    private String writeModule( ModuleRef modRef  ) {

        Artifact arti;

        String ret = "   <!-- ================================================================== -->\n";
        ret += "   <target name=\"depend."+ modRef.getName() +"\"";


        Module mod = artig.get( modRef );


        ret +=
            Strings.join( mod.getArtifact().getDepends() ).
                    prefix( "\n           depends=\"" ).
                    postfix( "\"").
                    skip( new F1<Boolean,ArtifactRef>( ) {
                        @Override public Boolean call(ArtifactRef ref) {
                            return ref.getScope() != null && !ref.getScope().equals( "compile" );
                        }
                    }).
                    trans( new F1<String,ArtifactRef>() {
                        @Override public String call(ArtifactRef ref) {
                            return getModuleName( ref );
                        }
                    });

        ret += ">\n";
        ret += "      <ant dir=\"${dependencies.basedir}/modules/" + modRef.getName() + "\""
            +      " target=\"local.goal\" inheritAll=\"false\"/>\n";

        ret += "   </target>\n\n";
        return ret;
    }

    private String getModuleName( ArtifactRef ref ) {

        if ( artig.isModule( ref )) {
            return "depend." + artig.getModuleName( ref );
        }

        return ref.getName();
    }
}
