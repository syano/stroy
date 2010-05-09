package org.openCage.lang.artifact;

import com.sun.istack.internal.NotNull;
import org.openCage.lang.structure.ESet;

import java.util.ArrayList;
import java.util.List;

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

public class Project {

    private final String name;
    private List<Artifact> modules = new ArrayList<Artifact>();
    private List<Artifact> externals = new ArrayList<Artifact>();
    private ESet<Artifact> all = new ESet<Artifact>();

    public Project( @NotNull String name ) {
        this.name = name;
    }

    public Artifact module( @NotNull Class resourceBase, @NotNull String groupid, @NotNull String name ) {

        Artifact mod = all.getAdd( new Artifact(groupid, name ));
        mod.base( resourceBase );
        modules.add( mod );
        mod.fullName( groupid + "-" + name );
        return mod;
    }

    public Artifact external( @NotNull String groupid, @NotNull String name ) {
        Artifact ext = all.getAdd( new Artifact(groupid, name ));
        externals.add( ext );
        return ext;
    }


    public Author author( @NotNull String name ) {
        return new Author( name );
    }

    public List<Artifact> getModules() {
        return modules;
    }

    public boolean isModule( Artifact arti ) {
        return modules.contains( arti );
    }

    public List<Artifact> getExternals() {
        return externals;
    }

    public Artifact get(String group, String name) {

        Artifact arti = all.get( new Artifact( group, name ));

        if ( arti != null ) {
            return arti;
        }

        throw new IllegalStateException( "artifact not found " + group + " " + name );
    }

    public void validate() {
        for ( Artifact mod : modules ) {
            mod.validate();
        }

        for ( Artifact ext : externals ) {
            ext.validate();
        }
    }

    public void include( Project other ) {
        for ( Artifact arti : other.modules ) {
            Artifact old = all.get( arti );
            if ( old != null ) {
                if ( !isModule( old )) {
                    throw new IllegalArgumentException( "can't change artifact type" );
                }
                old.merge( arti );
            } else {
                modules.add( arti );
                all.add( arti );
            }
        }

        for ( Artifact arti : other.externals ) {
            Artifact old = all.get( arti );
            if ( old != null ) {
                if ( isModule( old )) {
                    throw new IllegalArgumentException( "can't change artifact type" );
                }
                old.merge( arti );
            } else {
                externals.add( arti );
                all.add( arti );
            }
        }
    }

    public String getName() {
        return name;
    }
}
