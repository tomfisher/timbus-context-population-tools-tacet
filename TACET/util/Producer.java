/*
 * Copyright 2013 TecO - Karlsruhe Institute of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
