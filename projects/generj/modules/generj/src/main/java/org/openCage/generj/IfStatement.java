package org.openCage.generj;


/**
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
 *     * Neither the name of the <organization> nor the
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
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.public interface Statement {
 */

public class IfStatement<T> implements Statement {
    private Expr cond;
    private T base;
    private Block<IfStatement<T>> thn;
    private Block<IfStatement<T>> els;

    public IfStatement( T base, Expr cond ) {
        this.cond = cond;
        this.base = base;
    }


    @Deprecated
    public Block<IfStatement<T>> _then() {
        if ( thn == null ) {
            thn = new Block<IfStatement<T>>( this );
        }
        return thn;
    }

    public Block<IfStatement<T>> then_() {
        if ( thn == null ) {
            thn = new Block<IfStatement<T>>( this );
        }
        return thn;
    }


    @Deprecated
    public Block<IfStatement<T>> _else() {
        if ( els == null ) {
            els = new Block<IfStatement<T>>( this );
        }
        return els;
    }

    public Block<IfStatement<T>> else_() {
        if ( els == null ) {
            els = new Block<IfStatement<T>>( this );
        }
        return els;
    }

    public String toString() {
        return toString( "" );
    }

    @Override public String toString(String prefix) {
        String ret = prefix + "if( " + cond.toString() + " )";

        ret += thn.toString( prefix );

        if ( els != null ) {
            ret += " else ";
            ret += els.toString( prefix );
        }

        return ret;
    }

    public T r() {
        return base;
    }

}
