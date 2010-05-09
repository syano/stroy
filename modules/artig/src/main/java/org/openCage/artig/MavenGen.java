package org.openCage.artig;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.openCage.fspath.protocol.FSPath;
import org.openCage.io.clazz.FileExistence;
import org.openCage.lang.artifact.Artifact;
import org.openCage.lang.artifact.JavaVersion;
import org.openCage.lang.artifact.Project;
import org.openCage.lang.artifact.Scope;
import org.openCage.lang.errors.Unchecked;

import java.io.FileWriter;
import java.io.IOException;

import static org.openCage.lang.artifact.Scope.*;
import static org.openCage.lang.artifact.Scope.TEST;

public class MavenGen {

    private final Project project;

    public MavenGen( @NotNull Project project ) {
        this.project = project;
    }

    public void generate( FSPath projectRoot ) {

        FSPath rootPom = projectRoot.add( "pom.xml" );
        FileExistence.ensurePath( rootPom );

        FileWriter writer = null;
        try {
            writer = new FileWriter( rootPom.toFile() );

            writer.write( getProjectPom());

        } catch (IOException e) {
            throw Unchecked.wrap( e );
        } finally {
            IOUtils.closeQuietly( writer );
        }

        for ( Artifact mod : project.getModules() ) {
            FSPath modPom = projectRoot.add( "modules", mod.gettName(), "pom.xml" );
            FileExistence.ensurePath( modPom );

            try {
                writer = new FileWriter( modPom.toFile() );

                writer.write( getModulePom( mod ));

            } catch (IOException e) {
                throw Unchecked.wrap( e );
            } finally {
                IOUtils.closeQuietly( writer );
            }

        }                    
    }

    public String getModulePom( Artifact arti ) {
        String pom = "<project>\n" +
                "   <modelVersion>4.0.0</modelVersion>\n";
        pom += "   " + leaf( "groupId", arti.getGroupId()) + "\n";
        pom += "   " + leaf( "artifactId", arti.gettName()) + "\n";
        pom += "   " + leaf( "version", arti.getVersion().getShort()) + "\n\n";

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

        // TODO
        System.out.println("TODO" + arti.getJavaVersion() );
        if ( arti.getJavaVersion().equals(JavaVersion.V6) ) {
            pom += "               <configuration>\n" +
                    "                    <source>1.6</source>\n" +
                    "                    <target>1.6</target>\n" +
                    "                </configuration>\n";
        }

        pom += "           </plugin>\n" +
                "\n" +
                "        </plugins>\n" +
                "    </build>\n";



        pom += "   <dependencies>\n";
        for ( Artifact dep : arti.getCompileDependencies() ) {
            pom += dependency( dep, COMPILE );
        }

        for ( Artifact dep : arti.getTestDependencies() ) {
            pom += dependency( dep, TEST );
        }


        pom += "   </dependencies>\n";

        pom += "</project>\n";


        return pom;
    }

    private String dependency(Artifact arti, Scope scope  ) {
        String dep = "      <dependency>\n";
        dep += "         " + leaf( "groupId", arti.getGroupId()  ) + "\n";
        dep += "         " + leaf( "artifactId", arti.gettName()  ) + "\n";
        System.out.println( arti );
        dep += "         " + leaf( "version", arti.getVersion().getShort()  ) + "\n";

        switch ( scope ) {
            case TEST: dep += "         " + leaf( "scope", "test"  ) + "\n"; break;
            case COMPILE: dep += "         " + leaf( "scope", "compile"  ) + "\n"; break;
            default:
                throw new IllegalStateException( "(yet) unsupported scope " + scope );
        }

        dep += "      </dependency>\n";
        return dep;
    }


    private String leaf( String tag, String val ) {
        return "<" + tag + ">" + val + "</" + tag + ">";
    }

    public String getProjectPom() {

        String pom = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" \n" +
                "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "  xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 \n" +
                "                      http://maven.apache.org/maven-v4_0_0.xsd\">\n\n" +
                "  <!-- ===== generated by openCage-artigen ======== -->\n\n" +                
                "  <modelVersion>4.0.0</modelVersion>\n";

        pom += "   " + leaf( "groupId", "TODO-proj-groupID" ) + "\n";
        pom += "   " + leaf( "artifactId", project.getName() ) + "\n";
        pom += "   " + leaf( "packaging", "pom" ) + "\n";
        pom += "   " + leaf( "version", "1.0" ) + "\n";
        pom += "   " + leaf( "name", project.getName() ) + "\n";

        pom += "    <modules>\n";

        for ( Artifact arti : project.getModules() ) {
            pom += "      " + leaf( "module", "modules/" + arti.gettName())  + "\n";
        }

        pom += "    </modules>\n";
        pom += "</project>\n";


        return pom;
    }
}
