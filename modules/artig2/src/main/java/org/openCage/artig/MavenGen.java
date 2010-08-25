package org.openCage.artig;


import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.openCage.artig.stjx.*;
import org.openCage.io.FileUtils;
import org.openCage.io.fspath.FSPath;
import org.openCage.lang.errors.Unchecked;

import java.io.FileWriter;
import java.io.IOException;


public class MavenGen {

    private final Artig artig;
    private FSPath projectRoot;

    public MavenGen( @NotNull Artig artig ) {
        this.artig = artig;
        this.projectRoot = artig.getRootPath();
    }

    public void generate() {

        FSPath rootPom = projectRoot.add( "pom.xml" );
        FileUtils.ensurePath( rootPom );

        FileWriter writer = null;
        try {
            writer = new FileWriter( rootPom.toFile() );

            writer.write( getProjectPom());

        } catch (IOException e) {
            throw Unchecked.wrap( e );
        } finally {
            IOUtils.closeQuietly( writer );
        }

        for ( ModuleRef mod : artig.getProject().getModules() ) {
            FSPath modPom = projectRoot.add( "modules", mod.getName(), "pom.xml" );
            FileUtils.ensurePath( modPom );

            try {
                writer = new FileWriter( modPom.toFile() );


                writer.write( getModulePom( artig.get( mod ).getArtifact()));

            } catch (IOException e) {
                throw Unchecked.wrap( e );
            } finally {
                IOUtils.closeQuietly( writer );
            }

        }
    }

    public String getModulePom( Artifact arti ) {
        String pom = "<project>\n" +
                "  <!-- ===== generated by artig ======== -->\n\n" +
                "   <modelVersion>4.0.0</modelVersion>\n";
        pom += "   " + leaf( "groupId", arti.getGroupId()) + "\n";
        pom += "   " + leaf( "artifactId", arti.getName()) + "\n";
        pom += "   " + leaf( "version", arti.getVersion()) + "\n\n";

        pom += "    <repositories>\n" +
                "        <repository>\n" +
                "            <id>openCage-repo</id>\n" +
                "            <name>openCage-repo</name>\n" +
                "            <url>file://${basedir}/../../repo</url>\n" +
                "        </repository>\n" +
                "    </repositories>\n\n";

        pom += "    <build>\n" +
                "        <plugins>\n" +
                "            <plugin>\n" +
                "                <groupId>org.apache.maven.plugins</groupId>\n" +
                "                <artifactId>maven-compiler-plugin</artifactId>\n" +
                "                <version>2.2</version>\n";


        if ( arti.getJava() != null ) {
            if ( arti.getJava().getMin().equals( "6" )) { // TODO improve
            pom += "               <configuration>\n" +
                    "                    <source>1.6</source>\n" +
                    "                    <target>1.6</target>\n" +
                    "                </configuration>\n";
            }
        }

        pom += "           </plugin>\n" +
                "\n" +
                "        </plugins>\n" +
                "    </build>\n";



        pom += "   <dependencies>\n";
        for ( ArtifactRef dep : arti.getDepends() ) {
            pom += dependency( dep );
        }

//        for ( Artifact dep : arti.getTestDependencies() ) {
//            pom += dependency( dep.getChoice(), TEST );
//        }


        pom += "   </dependencies>\n";

        pom += "</project>\n";


        return pom;
    }

    private String dependency( ArtifactRef arti  ) {

        Artifact artifact = artig.find( arti );
        if ( artifact == null ) {
            throw new IllegalArgumentException( "artiref not declared " + arti );
        }

        String dep = "      <dependency>\n";
        dep += "         " + leaf( "groupId", arti.getGroupId()  ) + "\n";
        dep += "         " + leaf( "artifactId", arti.getName()) + "\n";
        dep += "         " + leaf( "version", artifact.getVersion()  ) + "\n";

        if ( arti.getScope() == null || arti.getScope().equals( "compile" ) ) {
            dep += "         " + leaf( "scope", "compile"  ) + "\n";
        } else if ( arti.getScope().equals( "test" ) ) {
            dep += "         " + leaf( "scope", "test"  ) + "\n";
        } else {
                throw new IllegalStateException( "(yet) unsupported scope " + arti.getScope() );
        }

        dep += "      </dependency>\n";
        return dep;
    }


    private static String leaf( String tag, String val ) {
        return "<" + tag + ">" + val + "</" + tag + ">";
    }

    public String getProjectPom() {

        String pom = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" \n" +
                "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "  xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 \n" +
                "                      http://maven.apache.org/maven-v4_0_0.xsd\">\n\n" +
                "  <!-- ===== generated by artig ======== -->\n\n" +
                "  <modelVersion>4.0.0</modelVersion>\n";

        pom += "   " + leaf( "groupId", artig.getProject().getGroupId() ) + "\n";
        pom += "   " + leaf( "artifactId", artig.getProject().getName() ) + "\n";
        pom += "   " + leaf( "packaging", "pom" ) + "\n";
        pom += "   " + leaf( "version", "1.0" ) + "\n";
        pom += "   " + leaf( "name", artig.getProject().getName() ) + "\n";

        pom += "    <modules>\n";

        for ( ModuleRef mod : artig.getProject().getModules() ) {
            pom += "      " + leaf( "module", "modules/" +  mod.getName())  + "\n";
        }

        pom += "    </modules>\n";
        pom += "</project>\n";


        return pom;
    }

//    private String getModuleName( Artifact arti ) {
//        if ( project.isModule( arti )) {
//            return arti.getGroupId() + "-" + arti.gettName();
//        }
//
//        return arti.gettName();
//
//    }
}
