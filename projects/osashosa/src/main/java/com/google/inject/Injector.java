package com.google.inject;

public interface Injector {

    <T> T getInstance( Class<T> tClass);

    void injectMembers(java.lang.Object o);
}
