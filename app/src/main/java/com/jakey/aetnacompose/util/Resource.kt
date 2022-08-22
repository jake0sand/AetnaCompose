package com.jakey.aetnacompose.util

/**
 * Wrapper class that helps handle errors, loading, and organize state. Simply wrap this around
 * a network call or a data request of any kind to handle whether Success, Failed, or Loading state
 * is current. Allows easy passing of states/messages from data, to ViewModel to UI layers.
 */

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
