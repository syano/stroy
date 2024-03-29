package org.openCage.stroy.algo.tree.zip;

import org.openCage.lang.Lazy;
import org.openCage.stroy.algo.tree.Fiel;
import org.openCage.lang.protocol.HasDistance;
import org.openCage.util.string.Strings;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.io.IOException;
import java.io.InputStream;

import com.twmacinta.util.MD5;
import com.JavaExchange.www.RandomGUID;

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

public class ZipFiel implements Fiel {
    private String   rootPath;
    private ZipEntry zipEntry;
    private String   checkSum;
    private boolean  readError = false;
    private String   type;
    private Lazy<HasDistance> lazyFuzzy;

    public ZipFiel( String rootPath, ZipEntry zipEntry, String type ) { //, Lazy<FuzzyHash> lazyFuzzy ) {
        this.rootPath = rootPath;
        this.zipEntry = zipEntry;
        this.type     = type;
//        this.lazyFuzzy = lazyFuzzy;
    }

    public String getFingerprint() {
        if ( checkSum == null ) {
            try {
                ZipFile zf = new ZipFile( rootPath );
                InputStream is = zf.getInputStream( zipEntry );
                checkSum = Strings.asHex( MD5.getHash( is, getSize() ));
                zf.close();
            } catch( IOException e ) {
                checkSum = new RandomGUID().toString();
                readError = true;
            }
        }

        return checkSum;
    }

    public String getType() {
        return type;
    }

    public HasDistance getFuzzyHash() {
        return lazyFuzzy.get();
    }

    public long getSize() {
        return zipEntry.getSize();
    }

    public boolean hasReadError() {
        return readError;
    }
}
