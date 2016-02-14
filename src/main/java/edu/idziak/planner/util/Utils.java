package edu.idziak.planner.util;

import com.google.common.base.Strings;
import com.google.common.primitives.Ints;

import java.util.Optional;

public class Utils {

    public static Optional<Integer> parseInt(String text) {
        if (Strings.isNullOrEmpty(text))
            return Optional.empty();
        return Optional.ofNullable(Ints.tryParse(text));
    }
}
