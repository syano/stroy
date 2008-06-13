package org.openCage.util.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Level;

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
* Portions created by Stephan Pfab are Copyright (C) 2006, 2007, 2008.
* All Rights Reserved.
*
* Contributor(s):
***** END LICENSE BLOCK *****/

public class GraphicalHandler extends Handler {

    private final LogHandlerPanel logHandlerPanel;
    
    public GraphicalHandler(LogHandlerPanel logHandlerPanel) {
        this.logHandlerPanel = logHandlerPanel;
        //setLevel( Level.INFO );
    }

    public void publish(LogRecord logRecord) {
        if ( this.isLoggable( logRecord  )) {
            logHandlerPanel.add( logRecord );
        }
    }

    public void flush() {
    }

    public void close() throws SecurityException {
    }
}
