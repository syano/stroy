package org.openCage.stroy.dir;

import org.openCage.lang.Lazy1;
import org.openCage.lang.Lazy;
import org.openCage.util.io.FileUtils;
import org.openCage.vfs.protocol.Content;
import org.openCage.lang.protocol.HasDistance;
import org.openCage.util.checksum.FullFileMD5;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.openCage.lang.protocol.FE0;
import org.openCage.lang.protocol.FE1;

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
* Portions created by Stephan Pfab are Copyright (C) 2006 - 2009.
* All Rights Reserved.
*
* Contributor(s):
***** END LICENSE BLOCK *****/
public class FileContent implements Content {

    private Logger LOG = Logger.getLogger( FileContent.class.getName());

    private final File                     file;
    private final Lazy<String>             checksum;
    private final Lazy1<HasDistance, File>   fuzzy;

    public FileContent( final FE1<HasDistance, File> fuzzyGen, final File file ) {
        this.file     = file;
        this.checksum = new Lazy<String>( new FE0<String>() {
            public String call() {
                try {
                    LOG.finer( "computing fingerprint of " + file.getAbsolutePath() );
                    return new FullFileMD5().getChecksum( file );
                } catch (IOException e) {
                    throw new Error(e);
                }
            }
        } );

        this.fuzzy = new Lazy1<HasDistance, File>( fuzzyGen );
    }

    public String getName() {
        return file.getName();
    }                              

    public String getChecksum() {
        return checksum.get();
    }

    public HasDistance getFuzzyHash() {
        return fuzzy.get( file );
    }

    public String getType() {
        return FileUtils.getExtension( file.getName() );
    }

    // lets refactor
    public String getLocation() {
        return file.getAbsolutePath();
    }


    public String toString() {
        return file.getPath();
    }

    public File getFile() {
        return file;
    }

}
