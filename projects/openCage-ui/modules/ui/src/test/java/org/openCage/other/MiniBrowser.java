package org.openCage.other;

import org.openCage.lang.structure.BFStack;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.html.*;

//from:
//http://www.java-tips.org/java-se-tips/javax.swing/how-to-create-a-simple-browser-in-swing.html

// The Simple Web Browser.
public class MiniBrowser extends JFrame

        implements HyperlinkListener {
    // These are the buttons for iterating through the page list.
    private JButton backButton, forwardButton;

    // Page location text field.
    private JTextField locationTextField;

    // Editor pane for displaying pages.
    private JEditorPane displayEditorPane;

    // Browser's list of pages that have been visited.
    private ArrayList pageList = new ArrayList();
    private boolean doGetImage = false;

    private BFStack<URL> bfstack = new BFStack<URL>();

    private Pattern pics = Pattern.compile( ".*\\.(gif|jpg|jpeg|png)" );
    private Pattern docs = Pattern.compile( ".*\\.(pdf|avi|mpeg3|mp3|doc)" );

    // Constructor for Mini Web Browser.
    public MiniBrowser() {
        // Set application title.
        super("Mini Browser");

        // Set window size.
        setSize(640, 480);

        // Handle closing events.
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                actionExit();
            }
        });

        // Set up file menu.
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem fileExitMenuItem = new JMenuItem("Exit",
                KeyEvent.VK_X);
        fileExitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionExit();
            }
        });
        fileMenu.add(fileExitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Set up button panel.
        JPanel buttonPanel = new JPanel();
        backButton = new JButton("< Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionBack();
            }
        });
        backButton.setEnabled(false);
        buttonPanel.add(backButton);
        forwardButton = new JButton("Forward >");
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionForward();
            }
        });
        forwardButton.setEnabled(false);
        buttonPanel.add(forwardButton);
        locationTextField = new JTextField(35);
        locationTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionGo();
                }
            }
        });
        buttonPanel.add(locationTextField);
        JButton goButton = new JButton("GO");
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionGo();
            }
        });
        buttonPanel.add(goButton);

        JButton getImageButton = new JButton( "take" );
        buttonPanel.add( getImageButton );
        getImageButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                actionGI();
            }
        });

        // Set up page display.
        displayEditorPane = new JEditorPane();
        displayEditorPane.setContentType("text/html");
        displayEditorPane.setEditable(false);
        displayEditorPane.addHyperlinkListener(this);

        displayEditorPane.addMouseListener( new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                if ( SwingUtilities.isRightMouseButton( mouseEvent)) {//mouseEvent.isMetaDown() ) {
                    mouseEvent.consume();

                    int pos = displayEditorPane.viewToModel( mouseEvent.getPoint());
                    Element el = ( (HTMLDocument)displayEditorPane.getDocument()).getCharacterElement( pos );
                    int i = 0;

                    AttributeSet attis = el.getAttributes();

                    Enumeration en = attis.getAttributeNames();

                    Object key = null;
                    //HTML.Tag.
                    while ( en.hasMoreElements() ) {
                        Object n = en.nextElement();
                        if ( n.toString().equals( "src" ) ) {
                            key = n;
                            Object srcsrc = el.getAttributes().getAttribute( key );
                            int j =0;
                            try {
                                showPage( new URL(displayEditorPane.getPage(), (String)srcsrc), true);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                            return;
                        }
                    }
                }

                //mouseEvent.getSource();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                //System.out.println( "entered" + mouseEvent.getSource());
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(displayEditorPane),
                BorderLayout.CENTER);


        try {
            showPage( new URL("http://www.miglayout.com"), true );
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void actionGI() {
        doGetImage = !doGetImage;
    }

    // Exit this program.
    private void actionExit() {
        System.exit(0);
    }

    // Go back to the page viewed before the current page.
    private void actionBack() {
        if ( bfstack.hasBackward() )
            showPage( bfstack.backward(), false );
    }

    // Go forward to the page viewed after the current page.
    private void actionForward() {
        if ( bfstack.hasForward() ) {
            showPage( bfstack.forward(), false );
        }
    }

    // Load and show the page specified in the location text field.
    private void actionGo() {
        URL verifiedUrl = verifyUrl(locationTextField.getText());
        if (verifiedUrl != null) {
            showPage(verifiedUrl, true);
        } else {
            showError("Invalid URL");
        }
    }

    // Show dialog box with error message.
    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Verify URL format.
    private URL verifyUrl(String url) {
        // Only allow HTTP URLs.
        if (!url.toLowerCase().startsWith("http://"))
            return null;

        // Verify format of URL.
        URL verifiedUrl = null;
        try {
            verifiedUrl = new URL(url);
        } catch (Exception e) {
            return null;
        }

        return verifiedUrl;
    }

    /* Show the specified page and add it to
the page list if specified. */
    private void showPage(URL pageUrl, boolean addToList) {
        // Show hour glass cursor while crawling is under way.
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            // Get URL of page currently being displayed.
            URL currentUrl = displayEditorPane.getPage();

            // Load and display specified page.

            URL newUrl = pageUrl;

            if ( pics.matcher( pageUrl.toString()).matches() ) {// pageUrl.toString().endsWith( ".png") || pageUrl.toString().endsWith( ".jpg") || pageUrl.toString().endsWith( ".gif")) {


                String newPage = "<html><body><img src=\"" +
                        pageUrl.toString() + "\"/>" +
                        "</body></html>";
                displayEditorPane.setText( newPage );

            } if ( docs.matcher( pageUrl.toString()).matches()){ //pageUrl.toString().endsWith( ".pdf") ) {

                String newPage = "<html><body><a href=\"" +
                        pageUrl.toString() + "\"/>" + pageUrl.toString() + "</a>" +
                        "</body></html>";
                displayEditorPane.setText( newPage );
                

            } else {
                displayEditorPane.setPage(pageUrl);
                // Get URL of new page being displayed.
                displayEditorPane.getPage();
            }

            if ( addToList ) {
                bfstack.push( newUrl );
            }

            // Update location text field with URL of current page.
            locationTextField.setText(newUrl.toString());

            // Update buttons based on the page being displayed.
            updateButtons();
        } catch (Exception e) {
            // Show error messsage.
            showError("Unable to load page");
        } finally {
            // Return to default cursor.
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /* Update back and forward buttons based on
the page being displayed. */
    private void updateButtons() {

        backButton.setEnabled( bfstack.hasBackward());
        forwardButton.setEnabled( bfstack.hasForward());
    }

    // Handle hyperlink's being clicked.
    public void hyperlinkUpdate(HyperlinkEvent event) {
        HyperlinkEvent.EventType eventType = event.getEventType();
        if (eventType == HyperlinkEvent.EventType.ACTIVATED) {

            event.getSourceElement();
            if (event instanceof HTMLFrameHyperlinkEvent) {
                HTMLFrameHyperlinkEvent linkEvent =
                        (HTMLFrameHyperlinkEvent) event;
                HTMLDocument document =
                        (HTMLDocument) displayEditorPane.getDocument();
                document.processHTMLFrameHyperlinkEvent(linkEvent);

            } else {
                showPage(event.getURL(), true);
            }
        }
    }

    // Run the Mini Browser.
    public static void main(String[] args) {
        MiniBrowser browser = new MiniBrowser();
        browser.setVisible( true );
    }
}
