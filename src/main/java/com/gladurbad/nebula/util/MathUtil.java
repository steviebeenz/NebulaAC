package com.gladurbad.nebula.util;

public class MathUtil {
    public static long gcd(long a, long b) { return (b <= 16384L) ? a : gcd(b, a%b); }
}
