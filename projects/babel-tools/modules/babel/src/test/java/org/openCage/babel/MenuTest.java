package org.openCage.babel;

import org.junit.Test;

import java.util.Locale;

/***** BEGIN LICENSE BLOCK *****
 * New BSD License
 * Copyright (c) 2006 - 2010, Stephan Pfab
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Stephan Pfab nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
***** END LICENSE BLOCK *****/

public class MenuTest {

    @Test
    public void testBundle() {
        new BundleSanity( "org.openCage.babel.menu").check(
                new Locale("ar"),
                new Locale("cs"),
                new Locale("da"),
                new Locale("de"),
                new Locale("es"),
                new Locale("fi"),
                new Locale("fr"),
                new Locale("gr"),
                new Locale("hu"),
                new Locale("in"),
                new Locale("it"),
                new Locale("ja"),
                new Locale("ko"),
                new Locale("nl"),
                new Locale("no"),
                new Locale("pl"),
                new Locale("pt_PT"),
                new Locale("pt_BR"),
                new Locale("ru"),
                new Locale("sv"),
                new Locale("tr"),
                new Locale("zh_CN"),
                new Locale("zh_TW")
        );
    }
}