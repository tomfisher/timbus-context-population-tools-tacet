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
