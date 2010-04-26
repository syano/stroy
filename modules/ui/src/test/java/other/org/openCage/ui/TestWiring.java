package other.org.openCage.ui;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import org.openCage.lang.artifact.Artifact;
import org.openCage.property.protocol.NonPersistingPropStore;
import org.openCage.property.protocol.PropStore;
import org.openCage.ui.wiring.UIWiring;

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
public class TestWiring implements Module {
    public void configure(Binder binder) {
        binder.install( new UIWiring());

        binder.bind( Artifact.class ).toProvider( TestArtiProvider.class );
        binder.bind( PropStore.class ).annotatedWith(Names.named("std")).to( NonPersistingPropStore.class );
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof TestWiring;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}