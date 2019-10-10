package com.jetbrains.rd.framework.base

import com.jetbrains.rd.util.lifetime.Lifetime
import com.jetbrains.rd.util.reactive.*


private fun<T> synchronize(lifetime: Lifetime, a: ISignal<T>, b: ISignal<T>) {
    a.flowInto(lifetime, b)
    b.flowInto(lifetime, a)
}

private fun<T> synchronize(lifetime: Lifetime, a: IMutablePropertyBase<T>, b: IMutablePropertyBase<T>) {
    a.map { it.deepClonePolymorphic() }. flowInto(lifetime, b)
    b.flowInto(lifetime, a)
}

private fun<T: Any> synchronize(lifetime: Lifetime, a: IMutableViewableSet<T>, b: IMutableViewableSet<T>) {
    a.flowInto(lifetime, b) { it }
    b.flowInto(lifetime, a) { it }
}

private fun<K: Any, V: Any> synchronize(lifetime: Lifetime, a: IMutableViewableMap<K, V>, b: IMutableViewableMap<K, V>) {
    a.flowInto(lifetime, b) { it.deepClonePolymorphic() }
    b.flowInto(lifetime, a) { it.deepClonePolymorphic() }
}

private fun<T: Any> synchronize(lifetime: Lifetime, a: IMutableViewableList<T>, b: IMutableViewableList<T>) {
    synchronizeImmutableLists(lifetime, a, b)

    a.change.flowInto(lifetime, b) { x -> x.deepClonePolymorphic().also { synchronizePolymorphic(lifetime, x, it) } }
    b.change.flowInto(lifetime, a) { x -> x.deepClonePolymorphic().also { synchronizePolymorphic(lifetime, x, it) } }
}

private fun<T: Any> synchronizeImmutableLists(lifetime: Lifetime, a: List<T>, b: List<T>) {
    require(a.size == b.size) { "Collection initial sizes must be equal, but sizes are different: ${a.size} != ${b.size}" }
    for (i in 0..a.lastIndex) {
        synchronizePolymorphic(lifetime, a[i], b[i])
    }
}

private fun<T: Any> synchronizeImmutableArrays(lifetime: Lifetime, a: Array<T>, b: Array<T>) {
    require(a.size == b.size) { "Collection initial sizes must be equal, but sizes are different: ${a.size} != ${b.size}" }
    for (i in 0..a.lastIndex) {
        synchronizePolymorphic(lifetime, a[i], b[i])
    }
}

@Suppress("UNCHECKED_CAST")
internal fun synchronizePolymorphic(lifetime: Lifetime, first: Any?, second: Any?) {

    if (first is ISignal<*> && second is ISignal<*>) {
        synchronize(lifetime, first as ISignal<Any>, second as ISignal<Any>)

    } else if (first is IMutablePropertyBase<*> && second is IMutablePropertyBase<*>) {
        synchronize(lifetime, first as IMutablePropertyBase<Any>, second as IMutablePropertyBase<Any>)

    } else if (first is IMutableViewableList<*> && second is IMutableViewableList<*>) {
        synchronize(lifetime, first as IMutableViewableList<Any>, second as IMutableViewableList<Any>)

    } else if (first is IMutableViewableSet<*> && second is IMutableViewableSet<*>) {
        synchronize(lifetime, first as IMutableViewableSet<Any>, second as IMutableViewableSet<Any>)

    } else if (first is IMutableViewableMap<*, *> && second is IMutableViewableMap<*, *>) {
        synchronize(lifetime, first as IMutableViewableMap<Any, Any>, second as IMutableViewableMap<Any, Any>)

    } else if (first is RdBindableBase && second is RdBindableBase) {
        first.synchronizeWith(lifetime, second)

    //collections
    } else if (first is List<*> && second is List<*>) {
        synchronizeImmutableLists(lifetime, first as List<Any>, second as List<Any>)

    } else if (first is Array<*> && second is Array<*>) {
        synchronizeImmutableArrays(lifetime, first as Array<Any>, second as Array<Any>)

    } else
        error("Objects are not mutually synchronizable: 1) $first   2) $second")
}