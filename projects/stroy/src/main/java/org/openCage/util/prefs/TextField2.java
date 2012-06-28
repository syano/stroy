package org.openCage.util.prefs;

import com.google.inject.Inject;
import org.openCage.comphy.ImmuProp;
import org.openCage.comphy.Observable;
import org.openCage.comphy.Observer;
import org.openCage.comphy.StringProperty;
import org.openCage.lang.inc.Str;
import org.openCage.lang.listeners.VoidListenerControl;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static org.openCage.lang.inc.Strng.S;

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

public class TextField2 extends JTextField implements Observable {

    private final ImmuProp<Str> prop;

    @Inject
    public TextField2( final ImmuProp<Str> prop ) {
        this.prop = prop;
        super.setText( prop.get().get() );

        addListeners();


    }

    private void addListeners() {

        final TextField2 that = this;

        addKeyListener( new KeyAdapter() {
            public void keyReleased(KeyEvent keyEvent) {
                // first make sure the key changed the text
                super.keyReleased( keyEvent );
                prop.set(S(that.getText()));
            }
        });
    }


    @Override
    public void setText(String txt ) {
        // prevents update loops
        if ( txt.equals( super.getText() ) ) {
            return;
        }

        super.setText(txt);
        prop.set(S(txt));
    }


    @Override
    public VoidListenerControl getListenerControl() {
        return prop.getListenerControl();
    }
}
