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

import java.util.ArrayList;
import java.util.Iterator;

public class CircularList<E> implements Iterable<E> {
    private ArrayList<E> array;
    private int start;
    private int size;
    private int capacity;

    public CircularList(int capacity) {
        this.capacity = capacity;
        array = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; ++i) array.add(null);
        size = 0;
        start = 0;
    }

    public E get(int index) {
        if (index >= size) throw new IndexOutOfBoundsException();
        return array.get((start + index) % capacity);
    }

    public void set(int index, E e) {
        if (index >= size) throw new IndexOutOfBoundsException();
        array.set((start + index) % capacity, e);
    }

    public void pushFront(E e) {
        dec();
        if (size < capacity) ++size;
        array.set(start, e);
    }

    public void pushBack(E e) {
        array.set((start + size) % capacity, e);
        if (size < capacity) ++size;
        else inc();
    }

    public void popFront() {
        if (size == 0) throw new IndexOutOfBoundsException();
        inc();
        --size;
    }

    public void popBack() {
        if (size == 0) throw new IndexOutOfBoundsException();
        --size;
    }

    public E getLast() {
        return get(size - 1);
    }

    public int size() {
        return size;
    }

    private void dec() {
        start = (start - 1 + capacity) % capacity;
    }

    private void inc() {
        start = (start + 1) % capacity;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public E next() {
                return get(i++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }
}
