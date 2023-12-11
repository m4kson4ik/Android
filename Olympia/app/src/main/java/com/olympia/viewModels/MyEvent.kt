package com.olympia.viewModels

object MyEvent {
    private val listeners = mutableListOf<(Any) -> Unit>()
    fun sendEvent(event: Any) {
        listeners.forEach { listener ->
            listener.invoke(event)
        }
    }
    fun addListener(listener: (Any) -> Unit) {
        listeners.add(listener)
    }
    fun removeListener(listener: (Any) -> Unit) {
        listeners.remove(listener)
    }
}