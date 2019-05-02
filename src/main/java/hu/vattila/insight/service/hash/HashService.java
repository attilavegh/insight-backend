package hu.vattila.insight.service.hash;

import lombok.RequiredArgsConstructor;
import net.jpountz.xxhash.XXHashFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Service
public class HashService {

    private static final int OFFSET = 0;

    private final XXHashFactory xxHashFactory;
    private final HashProperties hashProperties;

    public long hash(byte[] byteArray) {
        if (byteArray == null || ObjectUtils.isEmpty(byteArray)) {
            throw new IllegalArgumentException("byteArray cannot be null or empty");
        }

        return xxHashFactory.hash64().hash(byteArray, OFFSET, byteArray.length, hashProperties.getSeed());
    }
}
