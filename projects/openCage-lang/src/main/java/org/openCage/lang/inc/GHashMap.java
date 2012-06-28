package org.openCage.lang.inc;

import java.util.HashMap;

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

public class GHashMap<K,V> extends HashMap<K,V> implements GMap<K,V> {

    @Override
    public boolean containsKeyG(K key) {
        return super.containsKey(key);
    }

    @Override
    public boolean containsValueG(V value) {
        return super.containsValue(value);
    }

    @Override
    public V getG(K key) {
        return super.get(key);
    }

    @Override
    public V removeG(K key) {
        return super.remove(key);
    }

    @Override
    public GMap<K, V> putF(K key, V value) {
        put(key,value);
        return this;
    }

    @Override
    public V get(Object key) {
        throw new IllegalAccessError( "call of non generic method" );
    }

    @Override
    public boolean containsKey(Object key) {
        throw new IllegalAccessError( "call of non generic method" );
    }

    @Override
    public boolean containsValue(Object value) {
        throw new IllegalAccessError( "call of non generic method" );
    }

    @Override
    public V remove(Object key) {
        throw new IllegalAccessError( "call of non generic method" );
    }
}
