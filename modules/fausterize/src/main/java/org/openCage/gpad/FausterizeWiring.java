package org.openCage.gpad;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import org.openCage.application.protocol.Application;
import org.openCage.application.wiring.ApplicationWiring;
import org.openCage.gpad.providers.ApplicationProvider;
import org.openCage.gpad.providers.LocalizeProvider;
import org.openCage.gpad.providers.PropStoreProvider;
import org.openCage.gpad.providers.TransPropStoreProvider;
import org.openCage.lang.MRU;
import org.openCage.lang.impl.BackgroundExecutorImpl;
import org.openCage.lang.protocol.BackgroundExecutor;
import org.openCage.lang.protocol.SingletonApp;
import org.openCage.localization.protocol.Localize;
import org.openCage.localization.wiring.LocalizeWiring;
import org.openCage.property.protocol.PropStore;
import org.openCage.property.protocol.Property;
import org.openCage.ui.protocol.PrefBuilder;
import org.openCage.ui.wiring.UIWiring;
import org.openCage.withResource.wiring.IoWiring;


import static org.openCage.gpad.Constants.FAUSTERIZE;

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
 * Wiring for GUICE classes used by fausterize 
 */
public class FausterizeWiring implements Module {
    public void configure(Binder binder) {
        binder.install( new ApplicationWiring());
        binder.install( new IoWiring());
        binder.install( new UIWiring());
        binder.install( new LocalizeWiring());

        binder.bind(BackgroundExecutor.class ).
                to(BackgroundExecutorImpl.class );

        binder.bind( Application.class ).toProvider( ApplicationProvider.class );
        binder.bind( PropStore.class ).
                annotatedWith( Names.named("std")).
                toProvider( PropStoreProvider.class );
        binder.bind( PropStore.class ).
                annotatedWith( Names.named("trans")).
                toProvider( TransPropStoreProvider.class );
        binder.bind( Localize.class).
                annotatedWith( Names.named(FAUSTERIZE)).
                toProvider( LocalizeProvider.class);

        binder.bind(PrefBuilder.class ).annotatedWith( Names.named(FAUSTERIZE)).to(CodePrefBuilder.class);

        binder.bind( new TypeLiteral<Property<MRU<String>>>() {} ).
                //annotatedWith( Names.named( LocaleProperty.THE_LOCALE)).
                toProvider( FaustMRUProperty.class ).
                in( Singleton.class );

        binder.bind(SingletonApp.class).
                toProvider( FausterizeSingleton.class ).
                in( Singleton.class ) ;


    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof FausterizeWiring;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}