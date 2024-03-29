package org.openCage.webserv;//package webs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//import com.borland.jbcl.layout.*;


/**
 * Title: A simple Webserver Tutorial
 NO warranty, NO guarantee, MAY DO damage to FILES, SOFTWARE, HARDWARE!!
 * Description:
  This is a simple tutorial on making a webserver
  posted on http://turtlemeat.com .
  Go there to read the tutorial!
  This program and sourcecode is free for all, and you can
  copy and modify it as you like, but you should
  give credit and maybe a link to turtlemeat.com,
  you know R-E-S-P-E-C-T. You gotta respect the
  work that has been put down.

 * Copyright:    Copyright (c) 2002
 * Company: TurtleMeat
 * @author: Jon Berg <jon.berg[on_server]turtlemeat.com
 * @version 1.0
 */

//file: webserver_starter.java
//declare a class wich inherit JFrame
public class webserver_starter
    extends JFrame {
  //declare some panel, scrollpanel, textarea for gui
  JPanel jPanel1 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea jTextArea2 = new JTextArea();
  static Integer listen_port = null;

  //basic class constructor
  public webserver_starter() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  //the JavaAPI entry point
  //where it starts this class if run
  public static void main(String[] args) {
    //start WebServ on port x, default 80
    //use argument to main for what port to start on
    try {
      listen_port = new Integer(args[0]);
      //catch parse error
    }
    catch (Exception e) {
      listen_port = new Integer(9090);
    }
    //create an instance of this class
    webserver_starter webserver = new webserver_starter();
  }

  //set up the user interface
  private void jbInit() throws Exception {
    //oh the pretty colors
    jTextArea2.setBackground(new Color(16, 12, 66));
    jTextArea2.setForeground(new Color(151, 138, 255));
    jTextArea2.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextArea2.setToolTipText("");
    jTextArea2.setEditable(false);
    jTextArea2.setColumns(30);
    jTextArea2.setRows(15);
    //change this to impress your friends
    this.setTitle("Jon\'s fancy prancy webserver");
    //ugly inline listener, it's for handling closing of the window
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosed(e);
      }
    });
    //add the various to the proper containers
    jScrollPane1.getViewport().add(jTextArea2);
    jPanel1.add(jScrollPane1);
    this.getContentPane().add(jPanel1, BorderLayout.EAST);

    //tveak the apearance
    this.setVisible(true);
    this.setSize(420, 350);
    this.setResizable(true);
    //make sure it is drawn
    this.validate();
    //create the actual serverstuff,
    //all that is implemented in another class
    new WebServ(listen_port.intValue(), this);
  }

  //exit program when "X" is pressed.
  void this_windowClosed(WindowEvent e) {
    System.exit(1);
  }

  //this is a method to get messages from the actual
  //WebServ to the window
  public void send_message_to_window(String s) {
    jTextArea2.append(s);
  }
} //class end
