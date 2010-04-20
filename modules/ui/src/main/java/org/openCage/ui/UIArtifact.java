package org.openCage.ui;

import org.openCage.IOArtifact;
import org.openCage.lang.artifact.Artifact;
import org.openCage.lang.artifact.ArtifactProvider;
import org.openCage.lang.artifact.Project;
import org.openCage.property.PropertyArtifact;

/**
 * Created by IntelliJ IDEA.
 * User: stephan
 * Date: Apr 19, 2010
 * Time: 5:26:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class UIArtifact implements ArtifactProvider {

    private final Project proj;
    private final Artifact ui;

    public UIArtifact() {
        proj = new PropertyArtifact().getProject();

        ui = proj.module( "openCage", "ui" ).
                version( "0.1.0" ).
                address( "http://stroy.wikidot.com", "stroy.wikidot.com").
                mpl11().
                depends( proj.get("openCage", "property")).
                depends( new org.openCage.localization.Arti().getArtifact()).
                depends( new IOArtifact().getArtifact()).
                depends( proj.get("openCage", "lang")).
                depends( proj.external( "net.java.dev.designgridlayout  ", "designgridlayout" ).
                        depends( proj.external( "net.java.dev.swing-layout", "swing-layout" ).
                                address( "https://swing-layout.dev.java.net/", "dev.java").
                                lgpl().
                                version("1.0.2")).
                        descriptionShort( "Swing layout manager based on the use of canonical grids for user interface design." ).
                        address( "https://designgridlayout.dev.java.net/", "dev.java").
                        lgpl().
                        version( "0.9" )).
                depends( proj.external( "fifesoft", "RSyntaxTextArea" ).
                        descriptionShort( "A textarea subclass enhanced by a lot of typical texteditor feature").
                        address( "http://fifesoft.com/rsyntaxtextarea/", "fifesoft.org" ).
                        lgpl().
                        version( "1.4.1" )).
                depends( proj.external( "com.jgoodies", "jgoodies-binding" ).
                        descriptionShort( "Twoway binding of domain objects and ui objects. Changes to the domain object are displayed by the ui object, changes to the ui object modify the domain object." ).
                        address( "http://www.jgoodies.com/downloads/libraries.html", "jgoodies").
                        bsd().
                        version( "2.0.6" )).
                depends( proj.external("javagraphics", "javagraphics-preferencepanel").
                        descriptionShort( "A OSX style preference panel." ).
                        address( "https://javagraphics.dev.java.net/", "dev.java").
                        bsd().
                        version( "1.0" )
                );
    }

    @Override
    public Artifact getArtifact() {
        return ui;
    }

    @Override
    public Project getProject() {
        return proj;
    }
}