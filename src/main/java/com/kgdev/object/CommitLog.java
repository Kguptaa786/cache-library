package com.kgdev.object;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class CommitLog {

    @NonNull
    private CacheKey key;
    private CacheValue value;
    @NonNull
    private MethodEnum method;
}
