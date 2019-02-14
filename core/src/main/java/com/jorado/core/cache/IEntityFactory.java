package com.jorado.core.cache;


public interface IEntityFactory<T> {
    T get(String key) throws ExpiryException;
}
