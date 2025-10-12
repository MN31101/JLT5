package lab4;

import java.util.Collection;

public interface MyList<E>{
    void add(E e);

    void add(int index, E element);

    void addAll(Collection<? extends E> c);

    void addAll(int index, Collection<? extends E> c);

    E get(int index);

    E remove(int index);

    void set(int index, E element);

    int indexOf(E o);

    int size();

    E[] toArray();
}
