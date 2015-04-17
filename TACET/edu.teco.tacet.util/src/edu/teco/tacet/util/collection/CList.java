package edu.teco.tacet.util.collection;

import java.util.Iterator;

public abstract class CList<T> implements Iterable<T> {
    public abstract T getHead();

    public abstract CList<T> getTail();
    
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            CList<? extends T> currentCList = CList.this;

            @Override
            public boolean hasNext() {
                return !currentCList.isEmpty();
            }

            @Override
            public T next() {
                T head = currentCList.getHead();
                currentCList = currentCList.getTail();
                return head;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }
    
    public static <T> CList<T> cons(T head, CList<T> tail) {
        return new Cons<T>(head, tail);
    }
    
    public static <T> CList<T> nil() {
        return new Nil<T>();
    }

    public static <T> CList<T> cList(T t) {
        return new Cons<T>(t);
    }

    @SafeVarargs
    public static <T> CList<T> cList(T... ts) {
        if (ts.length == 0) { // called with no args
            return new Nil<T>();
        }
        return new LazyCListFromArray<T>(ts, 0);
    }
    
    public static <T> CList<T> cList(Iterable<T> iterable) {
        return new LazyCListFromIterator<T>(iterable.iterator());
    }
    
    private static class Nil<T> extends CList<T> {
        
        @Override
        public T getHead() {
            return null;
        }

        @Override
        public CList<T> getTail() {
            return null;
        }
        
        @Override
        public boolean isEmpty() {
            return true;
        }
        
        @Override
        public String toString() {
            return "Nil";
        }
        
    }
    
    private static class Cons<T> extends CList<T> {
        private CList<T> tail;
        private T head;

        public Cons(T value, CList<T> acc) {
            this.head = value;
            this.tail = acc;
        }

        public Cons(T value) {
            this(value, new Nil<T>());
        }

        @Override
        public T getHead() {
            return head;
        }

        @Override
        public CList<T> getTail() {
            return tail;
        }
        
        @Override
        public String toString() {
            return "(" + head + " . " + tail.toString() + ")";
        }
    }

    private static class LazyCListFromArray<T> extends CList<T> {
        final T[] ts;
        final int pos;
        CList<T> tail;
        
        public LazyCListFromArray(T[] ts, int pos) {
            this.ts = ts;
            this.pos = pos;
        }

        @Override
        public T getHead() {
            return ts[pos];
        }

        @Override
        public CList<T> getTail() {
            if (this.tail == null) {
                if (pos + 1 < ts.length) {
                    this.tail = new LazyCListFromArray<T>(ts, pos + 1);
                } else {
                    this.tail = CList.<T>nil();
                }
            }
            return this.tail;
        }
        
        @Override
        public String toString() {
            return "(" + getHead().toString() + " . " +
                tail == null ? "..." : tail.toString() + ")";
        }
        
    }
    
    private static class LazyCListFromIterator<T> extends CList<T> {
        final Iterator<T> iter;
        final T head;
        CList<T> tail; 
        
        public LazyCListFromIterator(Iterator<T> iterator) {
            this.iter = iterator;
            this.head = iter.hasNext() ? iter.next() : null;
        }

        @Override
        public T getHead() {
            return head;
        }

        @Override
        public CList<T> getTail() {
            if (this.tail == null) {
                if (iter.hasNext()) {
                    this.tail = new LazyCListFromIterator<T>(iter);
                } else {
                    this.tail = new Nil<T>();
                }
            }
            return this.tail;
        }
        
        @Override
        public boolean isEmpty() {
            return this.head == null;
        }
        
        @Override
        public String toString() {
            return "(" + head.toString() + " . " + tail == null ? "..." : tail.toString() + ")";
        }
        
    }
}
