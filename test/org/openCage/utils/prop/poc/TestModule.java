package org.openCage.utils.prop.poc;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import org.openCage.utils.prop.PersistentPropStoreFactory;
import org.openCage.utils.prop.PropStore;
import org.openCage.utils.prop.PropStoreFactory;
import org.openCage.utils.prop.Prop;
import org.openCage.utils.persistence.Persistence;
import org.openCage.utils.persistence.InMemoryPeristence;

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
public class TestModule extends AbstractModule {

    protected void configure() {

        // this binds a property with name "foo"
        // the singleton is necesarry to share this one prop
        bind( new TypeLiteral<Prop<String>>() {}).annotatedWith( Names.named( "foo"  )).
                toProvider( StringPropFooProvider.class ).in( Singleton.class );

        bind( new TypeLiteral<Prop<String>>() {}).annotatedWith( Names.named( "woo"  )).
                toProvider( StringPropWooProvider.class ).in( Singleton.class );

        bind( new TypeLiteral<Prop<Double>>() {}).annotatedWith( Names.named( "pi" )).
                toProvider( PiPropProvider.class ).in( Singleton.class );

        // there should be no store sharing
        // TODO Singleton where?
        bind( PropStoreFactory.class ).to( PersistentPropStoreFactory.class );
        bind( new TypeLiteral<Persistence<PropStore>>() {} ).
                to( new TypeLiteral<InMemoryPeristence<PropStore>>() {} ).
                in( Singleton.class );
    }
}
