//package org.openCage.property.protocol;
//
//import com.google.inject.Binder;
//import com.google.inject.Module;
//import com.google.inject.name.Names;
//import org.openCage.lang.BackgroundExecutorImpl;
//import org.openCage.lang.BackgroundExecutor;
//import org.openCage.property.PropStore;
//
//public class PropertyWiring implements Module {
//
//    @Override public void configure(Binder binder) {
//
//        binder.bind(BackgroundExecutor.class ).
//                to(BackgroundExecutorImpl.class );
//
//        binder.bind( PropStore.class ).
//                annotatedWith( Names.named( NonPersistingPropStore.NAME )).
//                to( NonPersistingPropStore.class );
//    }
//
//    @Override public int hashCode() {
//        return PropertyWiring.class.hashCode();
//    }
//
//    @Override public boolean equals(Object o) {
//        return o != null && (o instanceof PropertyWiring);
//    }
//}
