package org.openCage.ui.protocol;

import javax.swing.JButton;
import javax.swing.JPanel;

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

/**
 * delivers matching button and panel for the preference frame
 */
public interface PrefBuilder {
    /**
     * Construct a panel for the preference frame
     * @return A panel for the preference frame
     */
    JPanel getPrefPanel();

    /**
     * Construct a button for the preference frame
     * A button with test and icon 
     * @return A button for the preference frame
     */
    JButton getPrefButton();
}
