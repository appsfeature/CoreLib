package com.corelib.basic.util;

import java.util.List;

public interface CallbackList<T> {
    void call(List<T> data);
}