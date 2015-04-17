package edu.teco.tacet.util.function;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class Functions {
    public static <A> F1<A, A> id() {
        return new Identity<>();
    }
    
    public static <A,B> F1<A, B> cast() {
        return new F1<A, B>() {
            @SuppressWarnings("unchecked")
            @Override
            public B apply(A a) {
                return (B)a;
            }
        };
    }

    public static <A, B> F1<A, B> compose(final F0<A> g, final F1<A, B> f) {
        return new F1<A, B>() {
            @Override
            public B apply(A a) {
                return f.apply(g.apply());
            }
        };
    }

    public static <A, B, C> F1<A, C> compose(final F1<A, B> g, final F1<B, C> f) {
        return new F1<A, C>() {
            @Override
            public C apply(A a) {
                return f.apply(g.apply(a));
            }
        };
    }
    
    
    public static <A, B, C, D> F2<A, B, D> compose(final F2<A, B, C> g, final F1<C, D> f) {
        return new F2<A, B, D>() {
            @Override
            public D apply(A a, B b) {
                return f.apply(g.apply(a, b));
            }
        };
    }

    // Ordering functions

    public static <A extends Comparable<A>> F2<A, A, Boolean> greaterThan() {
        return new F2<A, A, Boolean>() {
            @Override
            public Boolean apply(A a1, A a2) {
                return a1.compareTo(a2) > 0;
            }
        };
    }

    public static <A extends Comparable<A>> F1<A, Boolean> greaterThan(A a) {
        return Functions.<A>lessThan().apply(a);
    }

    public static <A extends Comparable<? super A>> F2<A, A, Boolean> greaterThanOrEqualTo() {
        return new F2<A, A, Boolean>() {
            @Override
            public Boolean apply(A a1, A a2) {
                return a1.compareTo(a2) >= 0;
            }
        };
    }

    public static <A extends Comparable<A>> F1<A, Boolean> greaterThanOrEqualTo(A a) {
        return Functions.<A> lessThanOrEqualTo().apply(a);
    }

    public static <A extends Comparable<A>> F2<A, A, Boolean> lessThan() {
        return new F2<A, A, Boolean>() {
            @Override
            public Boolean apply(A a1, A a2) {
                return a1.compareTo(a2) < 0;
            }
        };
    }

    public static <A extends Comparable<A>> F1<A, Boolean> lessThan(A a) {
        return Functions.<A> greaterThan().apply(a);
    }

    public static <A extends Comparable<A>> F2<A, A, Boolean> lessThanOrEqualTo() {
        return new F2<A, A, Boolean>() {
            @Override
            public Boolean apply(A a1, A a2) {
                return a1.compareTo(a2) <= 0;
            }
        };
    }

    public static <A extends Comparable<A>> F1<A, Boolean> lessThanOrEqualTo(A a) {
        return Functions.<A> greaterThanOrEqualTo().apply(a);
    }

    public static <A extends Comparable<A>> F2<A, A, Boolean> equalTo() {
        return new F2<A, A, Boolean>() {
            @Override
            public Boolean apply(A a1, A a2) {
                return a1.compareTo(a2) == 0;
            }
        };
    }

    public static <A extends Comparable<A>> F1<A, Boolean> equalTo(A a) {
        return Functions.<A> equalTo().apply(a);
    }

    public static <A extends Comparable<A>> F2<A, A, Boolean> notEqualTo() {
        return new F2<A, A, Boolean>() {
            @Override
            public Boolean apply(A a1, A a2) {
                return a1.compareTo(a2) != 0;
            }
        };
    }
    
    public static <A extends Comparable<A>> F1<A, Boolean> notEqualTo(A a) {
        return Functions.<A> notEqualTo().apply(a);
    }

    // Integer math

    public static F2<Integer, Integer, Integer> iAdd =
        new F2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer a, Integer b) {
                return a + b;
            }
        };

    public static F2<Integer, Integer, Integer> iSub =
        new F2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer a, Integer b) {
                return a - b;
            }
        };

    public static F2<Integer, Integer, Integer> iMul =
        new F2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer a, Integer b) {
                return a * b;
            }
        };

    public static F2<Integer, Integer, Integer> iDiv =
        new F2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer a, Integer b) {
                return a / b;
            }
        };

    public static F2<Integer, Integer, Integer> iMod =
        new F2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer a, Integer b) {
                return a % b;
            }
        };

    // Double math

    public static F2<Double, Double, Double> dAdd =
        new F2<Double, Double, Double>() {
            @Override
            public Double apply(Double a, Double b) {
                return a + b;
            }
        };

    public static F2<Double, Double, Double> dSub =
        new F2<Double, Double, Double>() {
            @Override
            public Double apply(Double a, Double b) {
                return a - b;
            }
        };

    public static F2<Double, Double, Double> dMul =
        new F2<Double, Double, Double>() {
            @Override
            public Double apply(Double a, Double b) {
                return a * b;
            }
        };

    public static F2<Double, Double, Double> dDiv =
        new F2<Double, Double, Double>() {
            @Override
            public Double apply(Double a, Double b) {
                return a / b;
            }
        };

    public static F2<Double, Double, Double> dMod =
        new F2<Double, Double, Double>() {
            @Override
            public Double apply(Double a, Double b) {
                return a % b;
            }
        };

    // Float math

    public static F2<Float, Float, Float> fAdd =
        new F2<Float, Float, Float>() {
            @Override
            public Float apply(Float a, Float b) {
                return a + b;
            }
        };

    public static F2<Float, Float, Float> fSub =
        new F2<Float, Float, Float>() {
            @Override
            public Float apply(Float a, Float b) {
                return a - b;
            }
        };

    public static F2<Float, Float, Float> fMul =
        new F2<Float, Float, Float>() {
            @Override
            public Float apply(Float a, Float b) {
                return a * b;
            }
        };

    public static F2<Float, Float, Float> fDiv =
        new F2<Float, Float, Float>() {
            @Override
            public Float apply(Float a, Float b) {
                return a / b;
            }
        };

    public static F2<Float, Float, Float> fMod =
        new F2<Float, Float, Float>() {
            @Override
            public Float apply(Float a, Float b) {
                return a % b;
            }
        };

    // Long math

    public static F2<Long, Long, Long> lAdd =
        new F2<Long, Long, Long>() {
            @Override
            public Long apply(Long a, Long b) {
                return a + b;
            }
        };

    public static F2<Long, Long, Long> lSub =
        new F2<Long, Long, Long>() {
            @Override
            public Long apply(Long a, Long b) {
                return a - b;
            }
        };

    public static F2<Long, Long, Long> lMul =
        new F2<Long, Long, Long>() {
            @Override
            public Long apply(Long a, Long b) {
                return a * b;
            }
        };

    public static F2<Long, Long, Long> lDiv =
        new F2<Long, Long, Long>() {
            @Override
            public Long apply(Long a, Long b) {
                return a / b;
            }
        };

    public static F2<Long, Long, Long> lMod =
        new F2<Long, Long, Long>() {
            @Override
            public Long apply(Long a, Long b) {
                return a % b;
            }
        };

    // BigInteger math

    public static F2<BigInteger, BigInteger, BigInteger> biAdd =
        new F2<BigInteger, BigInteger, BigInteger>() {
            @Override
            public BigInteger apply(BigInteger a, BigInteger b) {
                return a.add(b);
            }
        };

    public static F2<BigInteger, BigInteger, BigInteger> biSub =
        new F2<BigInteger, BigInteger, BigInteger>() {
            @Override
            public BigInteger apply(BigInteger a, BigInteger b) {
                return a.subtract(b);
            }
        };

    public static F2<BigInteger, BigInteger, BigInteger> biMul =
        new F2<BigInteger, BigInteger, BigInteger>() {
            @Override
            public BigInteger apply(BigInteger a, BigInteger b) {
                return a.multiply(b);
            }
        };

    public static F2<BigInteger, BigInteger, BigInteger> biDiv =
        new F2<BigInteger, BigInteger, BigInteger>() {
            @Override
            public BigInteger apply(BigInteger a, BigInteger b) {
                return a.divide(b);
            }
        };

    public static F2<BigInteger, BigInteger, BigInteger> biMod =
        new F2<BigInteger, BigInteger, BigInteger>() {
            @Override
            public BigInteger apply(BigInteger a, BigInteger b) {
                return a.mod(b);
            }
        };

    // BigDecimal math

    public static F2<BigDecimal, BigDecimal, BigDecimal> bdAdd =
        new F2<BigDecimal, BigDecimal, BigDecimal>() {
            @Override
            public BigDecimal apply(BigDecimal a, BigDecimal b) {
                return a.add(b);
            }
        };

    public static F2<BigDecimal, BigDecimal, BigDecimal> bdSub =
        new F2<BigDecimal, BigDecimal, BigDecimal>() {
            @Override
            public BigDecimal apply(BigDecimal a, BigDecimal b) {
                return a.subtract(b);
            }
        };

    public static F2<BigDecimal, BigDecimal, BigDecimal> bdMul =
        new F2<BigDecimal, BigDecimal, BigDecimal>() {
            @Override
            public BigDecimal apply(BigDecimal a, BigDecimal b) {
                return a.multiply(b);
            }
        };

    public static F2<BigDecimal, BigDecimal, BigDecimal> bdDiv =
        new F2<BigDecimal, BigDecimal, BigDecimal>() {
            @Override
            public BigDecimal apply(BigDecimal a, BigDecimal b) {
                return a.divide(b);
            }
        };
}
