package edu.teco.tacet.util.collection;

import edu.teco.tacet.util.function.F1;

public class CharSequenceAdditions {
    public static <A> F1<Integer, Character> indexFrom(final CharSequence s) {
        return new F1<Integer, Character>() {
            @Override
            public Character apply(Integer index) {
                return s.charAt(index);
            }
        };
    }
}
