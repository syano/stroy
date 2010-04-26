package org.openCage.gpad;

import com.google.inject.Provider;
import org.openCage.IOArtifact;
import org.openCage.lang.artifact.Artifact;
import org.openCage.lang.artifact.ArtifactProvider;
import org.openCage.lang.artifact.Project;
import org.openCage.localization.LocalizationArtifact;
import org.openCage.property.PropertyArtifact;
import org.openCage.ui.UIArtifact;

public class FausterizeArtifact implements ArtifactProvider, Provider<Artifact> {

    private final Project proj;
    private final Artifact fausterize;

    public FausterizeArtifact() {
        proj = new UIArtifact().getProject();

        fausterize = proj.module( "openCage", "fausterize" ).
                version( "0.10.0" ).
                address( "http://stroy.wikidot.com/fausterize", "stroy.wikidot.com").
                mpl11().
                depends( proj.get("openCage", "property")).
                depends( proj.get("openCage", "localization")).
                depends( proj.get("openCage", "io")).
                depends( proj.get("openCage", "lang")).
//                depends( proj.external( "net.java.dev.designgridlayout  ", "designgridlayout" ).
//                        depends( proj.external( "net.java.dev.swing-layout", "swing-layout" ).
//                                address( "https://swing-layout.dev.java.net/", "dev.java").
//                                lgpl().
//                                version("1.0.2")).
//                        descriptionShort( "Swing layout manager based on the use of canonical grids for user interface design." ).
//                        address( "https://designgridlayout.dev.java.net/", "dev.java").
//                        lgpl().
//                        version( "0.9" )).
//                depends( proj.external( "fifesoft", "RSyntaxTextArea" ).
//                        descriptionShort( "A textarea subclass enhanced by a lot of typical texteditor feature").
//                        address( "http://fifesoft.com/rsyntaxtextarea/", "fifesoft.org" ).
//                        lgpl().
//                        version( "1.4.1" )).
//                depends( proj.external( "com.jgoodies", "binding" ).
//                        descriptionShort( "Twoway binding of domain objects and ui objects. Changes to the domain object are displayed by the ui object, changes to the ui object modify the domain object." ).
//                        address( "http://www.jgoodies.com/downloads/libraries.html", "jgoodies").
//                        bsd().
//                        version( "2.0.6" )).
//                depends( proj.external("javagraphics", "preferencepanel").
//                        descriptionShort( "A OSX style preference panel." ).
//                        address( "https://javagraphics.dev.java.net/", "dev.java").
//                        bsd().
//                        version( "1.0" )).
//                depends( proj.external( "macwidgets", "macwidgets" ).
//                        descriptionShort( "Library with OSX inspired swing components. e.g. a toolbar that blends in with the title bar, better buttons ..." ).
//                        address( "http://code.google.com/p/macwidgets/", "code.google.com").
//                        lgpl().
//                        version( "0.9.5" ).
//                            depends( proj.external( "com.jgoodies", "forms" ).
//                                descriptionShort( "JGoodies Forms library," ).
//                                address( "http://www.jgoodies.com/downloads/libraries.html", "jgoodies").
//                                bsd().
//                                version( "1.2.1" ))).
                testDepends( proj.get( "junit", "junit" ));
    }


    @Override
    public Artifact get() {
        return fausterize;
    }

    @Override
    public Artifact getArtifact() {
        return fausterize;
    }

    @Override
    public Project getProject() {
        return proj;
    }
}