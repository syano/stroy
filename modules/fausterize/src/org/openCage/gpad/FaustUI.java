package org.openCage.gpad;

import com.explodingpixels.macwidgets.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.openCage.application.protocol.Application;
import org.openCage.fspath.clazz.FSPathBuilder;
import org.openCage.lang.protocol.BackgroundExecutor;
import org.openCage.lang.protocol.F0;
import org.openCage.lang.protocol.FE0;
import org.openCage.lang.protocol.FE1;
import org.openCage.localization.protocol.Localize;
import org.openCage.ui.clazz.PreferenceFrame;
import org.openCage.ui.protocol.AboutSheet;
import org.openCage.ui.protocol.FileChooser;
import org.openCage.ui.protocol.PrefBuilder;
import org.openCage.ui.protocol.MenuBuilder;
import org.openCage.ui.protocol.OSXStandardEventHandler;
import org.openCage.withResource.protocol.FileLineIterable;
import org.openCage.withResource.protocol.With;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Writer;
import java.net.URI;

import static org.openCage.gpad.Constants.*;
import static org.openCage.ui.Constants.*;

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

public class FaustUI extends JFrame {

    private final Application             application;
    private final With                    with;
    private final FileChooser             fileChooser;
    private final AboutSheet              about;
    private final OSXStandardEventHandler osxEventHandler;
    private final Localize                localize;
    private final BackgroundExecutor      executor;

//    private URI                     pad;
    private String                  message;
    private JTextArea               textUI = new JTextArea();
    private TextEncoderIdx<String>  textEncoder;
    final private JButton           padButton = new JButton( new ImageIcon( getClass().getResource("lock_closed.png")));

    @Inject
    public FaustUI( Application application,
                    With wth,
                    FileChooser chooser,
                    AboutSheet about,
                    OSXStandardEventHandler osxEventHandler,
                    BackgroundExecutor executor,
                    @Named(FAUSTERIZE) Localize localize,
                    MenuBuilder menubuilder,
                    @Named(LOCALE) PrefBuilder localePrefBuilder,
                    @Named( FAUSTERIZE ) PrefBuilder codePref,
                    final PreferenceFrame prefFrame ) {

        this.application     = application;
        this.with            = wth;
        this.fileChooser     = chooser;
        this.about           = about;
        this.osxEventHandler = osxEventHandler;
        this.executor        = executor;
        this.localize        = localize;

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );        

        // TODO remove magic strings
        message = FSPathBuilder.getHome().add(PROJECT_DIR, "1.fst1").toString();
        new File( message ).getParentFile().mkdirs();


        padButton.setBackground( Color.DARK_GRAY);
        final JFrame theFrame = this;
        padButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = fileChooser.open( theFrame, FSPathBuilder.getARoot().toString());
                if ( path != null ) {
                    setPad( new File( path ).toURI(), message );
                    setTextEnabled( true );
                }
            }

        });

        JScrollPane scroll =  new JScrollPane(textUI);
        scroll.setSize( 800, 600 );
        scroll.setMinimumSize( new Dimension(400,400));
        textUI.setMinimumSize( new Dimension( 400, 400 ));
        getContentPane().setLayout( new BorderLayout());
        getContentPane().add( scroll, BorderLayout.CENTER  );


        // For some versions of Mac OS X, Java will handle painting the Unified Tool Bar.
        // Calling this method ensures that this painting is turned on if necessary.
        MacUtils.makeWindowLeopardStyle( getRootPane());

        UnifiedToolBar toolBar = new UnifiedToolBar();
//        save.putClientProperty("JButton.buttonType", "textured");
//        toolBar.addComponentToLeft(save);
        toolBar.addComponentToLeft( new LabeledComponentGroup( localize.localize( "org.openCage.localization.dict.decode"),
                                                               padButton).getComponent());

        //padButton.putClientProperty("JButton.buttonType", "textured");
        final JTextField textField = new JTextField(10);
        textField.putClientProperty("JTextField.variant", "search");
        toolBar.addComponentToRight( new LabeledComponentGroup( localize.localize( "org.openCage.localization.dict.search"),
                                                                textField).getComponent());
        

        getContentPane().add( toolBar.getComponent(), BorderLayout.NORTH );

        BottomBar bottomBar = new BottomBar(BottomBarSize.SMALL);
        bottomBar.addComponentToCenter(MacWidgetFactory.createEmphasizedLabel( message ));
        getContentPane().add( bottomBar.getComponent(), BorderLayout.SOUTH );

        setTitle( application.gettName());
        setSize( 800, 600 );

        setTextEnabled( false );
        setInitial( message );
        

        textField.addKeyListener( new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                super.keyReleased( keyEvent );
                String searchStr = textField.getText();
                String text = textUI.getText();
                int pos = text.indexOf( searchStr );
                System.out.println("" + searchStr + " " + pos );
                if ( pos > -1 ) {
                    textUI.setCaretPosition( pos );
                    Highlighter h = textUI.getHighlighter();
                    h.removeAllHighlights();
                    try {
                        h.addHighlight( pos, pos + searchStr.length(), DefaultHighlighter.DefaultPainter);
                    } catch (BadLocationException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }

        });

        pack();

        executor.addPeriodicAndExitTask( new FE0<Void>() {
            @Override
            public Void call() throws Exception {

                if ( textUI.getText().length() > 0 && textEncoder != null && textEncoder.isSet()) {
                    with.withWriter( new File(message), new FE1<Void, Writer>() {
                        public Void call(Writer writer) throws Exception {
                             writer.write( textEncoder.encode( textUI.getText(), 0 ));
                            return null;
                        }
                    });
                }
                return null;
            }
        });


        // TODO 
        menubuilder.setMenuOnFrame( this );

        prefFrame.addRow( "woo" ).add( codePref).add( localePrefBuilder ).build();

        F0<Void> showPrefs = new F0<Void>() {
            @Override
            public Void call() {
                prefFrame.setVisible( true );
                return null;
            }
        };

        osxEventHandler.addPrefsDelegate( showPrefs );
        menubuilder.addPrefsDelegate( showPrefs );



    }

    private synchronized void setPad( URI pad, String message) {
        textEncoder = new FaustString();
        textEncoder.setPad( pad);

        File filem = new File(message);
        String text = "";

        if ( filem.exists() ) {
            textUI.setText("");
            FileLineIterable it =  with.getLineIteratorCloseInFinal( filem );
            try {
                for ( String str : it ) {
                    text += str + "\n";
                }
            } finally {
                it.close();
            }
            textUI.append( textEncoder.decode( text, 0 ));
        }
    }

    private void setInitial( String message) {
        File filem = new File(message);
        String text = "";

        if ( filem.exists() ) {
            FileLineIterable it =  with.getLineIteratorCloseInFinal( filem );
            try {
                for ( String str : it ) {
                    text += str + "\n";
                }
            } finally {
                it.close();
            }

            textUI.append( text );
            
        } else {

            textUI.append( localize.localize("org.openCage.fausterize.intro"));
        }
    }

    private void setTextEnabled( boolean enable ) {
        if ( enable ) {
            textUI.setEditable(true);
            textUI.setBackground( Color.WHITE);
            padButton.setIcon( new ImageIcon( getClass().getResource("lock_open.png")) );
        } else {
            textUI.setEditable(false);
            textUI.setBackground( Color.LIGHT_GRAY);
            padButton.setIcon( new ImageIcon( getClass().getResource("lock_closed.png")) );
        }
    }

}
