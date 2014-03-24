/* 
 * Copyright 2013-2014 TECO - Karlsruhe Institute of Technology.
 * 
 * This file is part of TACET.
 * 
 * TACET is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TACET is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with TACET.  If not, see <http://www.gnu.org/licenses/>.
 */
 
package squirrel.util;

public abstract class Producer {
    private Producer other = identity;
    public final double eval(long timestamp) {
        return other.f(f((double) timestamp));
    }

    protected abstract double f(double x);

    public final Producer then(Producer other) {
        this.other = other;
        return this;
    }

    public static Producer identity = new Producer() {
            @Override public double f(double x) { return x; }};
    public static Producer sinus = new Producer() {
            @Override public double f(double x) { return Math.sin(x); }};
    public static Producer cosinus = new Producer() {
            @Override public double f(double x) { return Math.cos(x); }};
    public static Producer tangens = new Producer() {
            @Override public double f(double x) { return Math.tan(x); }};
    public static Producer exp = new Producer() {
            @Override public double f(double x) { return Math.exp(x); }};

    public static Producer stretch2 = new Stretch(2);
    public static Producer stretch4 = new Stretch(4);
    public static Producer stretch8 = new Stretch(8);
    public static Producer stretch16 = new Stretch(16);
    public static Producer stretch32 = new Stretch(32);
    public static Producer stretch64 = new Stretch(64);
    public static Producer stretch128 = new Stretch(128);
    public static Producer stretch256 = new Stretch(256);

    static class Stretch extends Producer {
        protected double factor;
        public Stretch(double factor) {
            this.factor = factor;
        }

        @Override protected double f(double x) {
            return x / factor;
        }
    }
}
