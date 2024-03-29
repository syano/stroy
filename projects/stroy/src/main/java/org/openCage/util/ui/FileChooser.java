package org.openCage.util.ui;

import com.muchsoft.util.Sys;

import javax.swing.*;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

/***** BEGIN LICENSE BLOCK *****
 * BSD License (2 clause)
 * Copyright (c) 2006 - 2012, Stephan Pfab
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Stephan Pfab BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
***** END LICENSE BLOCK *****/

/**
 * Filechoosers for OSX Windows Linux
 */
public class FileChooser {

    /**
     * open a file filebrowser and allow directory selection only
     * @param fr Frame a swing frame
     * @param path A path to start from.
     * @return A valid directory or null
     */
    static public String getDir( Frame fr, String path ) {

        if ( false /* Sys.isMacOSX() */ ) {    // trouble with java 7 or lion ?

            // OSX has filters for directories: use native osx finder

            System.setProperty( "apple.awt.fileDialogForDirectories", "true" );
            FileDialog fd = new FileDialog( fr, "Choose a Directory", FileDialog.LOAD );
            fd.setDirectory( path );
            fd.setVisible( true );

            String dir = fd.getDirectory();
            String name = fd.getFile();

            if ( name == null ) {
                return null;
            }

            return dir + File.pathSeparator + name;
        } else {

            // Windows does not have a filter for directories lets  use the Java one
            // TODO Linux?

            JFileChooser chooser = new JFileChooser( path );
            chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
            if ( 0 == chooser.showOpenDialog( fr )) {
                return chooser.getSelectedFile().getAbsolutePath();
            } else {
                return null;
            }
        }
    }
    /**
     * open a file filebrowser and allow directory selection only
     * @param fr Frame a swing frame
     * @param path A path to start from.
     * @return A valid directory or null
     */
    static public String getAnyFile( Frame fr, String path ) {

        JFileChooser chooser = new JFileChooser( path );
        chooser.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
        if ( 0 == chooser.showOpenDialog( fr )) {
            return chooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * return a path to a file via the os native dialog.
     * @param fr
     * @param path
     * @return A valid filepath or null
     */
    static public String open( Frame fr, String path ) {
    //	System.setProperty( "apple.awt.fileDialogForDirectories", "true" );
        FileDialog fd = new FileDialog( fr, "Choose a File", FileDialog.LOAD );
        fd.setDirectory( path );
        fd.setVisible( true );

        String dir = fd.getDirectory();
        String name = fd.getFile();

        if ( name == null ) {
        	return null;
        }

        return dir + name;
    }

    /**
     * return a path to a file via the os native dialog. (saveas title)
     * @param fr
     * @param path
     * @return A valid filepath or null
     */
    static public String saveas( Frame fr, String path ) {
    	//System.setProperty( "apple.awt.fileDialogForDirectories", "true" );
        FileDialog fd = new FileDialog( fr, "Save As", FileDialog.SAVE );
        fd.setDirectory( path );
        fd.setVisible( true );

        String dir = fd.getDirectory();
        String name = fd.getFile();
        if ( name == null ) {
        	return null;
        }

        return dir + File.pathSeparator + name;
    }
}
