package io.github.micopiira.mylauncher;

import java.util.List;

public interface CrudRepository<T> {
    long count();
    List<T> findAll();
}
