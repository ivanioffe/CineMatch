package com.ioffeivan.core.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * A sealed class representing text that can be displayed on the UI,
 * abstracting the source of the string (either a literal string or a resource ID).
 */
sealed class UiText {
    /**
     * Represents a hardcoded, literal string value.
     * @param value Literal string value
     */
    data class StaticString(val value: String) : UiText()

    /**
     * Represents a string resource ID, potentially with format arguments.
     * @param id The Android string resource ID.
     * @param args Arguments to be used for string formatting.
     */
    data class StringResource(
        @param:StringRes val id: Int,
        val args: Array<Any> = arrayOf(),
    ) : UiText() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as StringResource

            if (id != other.id) return false
            if (!args.contentEquals(other.args)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + args.contentHashCode()

            return result
        }
    }

    /**
     * Resolves the UiText into a displayable string within a [Composable] context.
     * @return The final string resolved from its source.
     */
    @Composable
    fun asString(): String {
        return when (this) {
            is StaticString -> value
            is StringResource -> LocalContext.current.getString(id, *args)
        }
    }

    /**
     * Resolves the [UiText] into a displayable string using an explicit [Context].
     * Used outside of Composition.
     * @param context The Android Context used to access resources.
     * @return The final string resolved from its source.
     */
    fun asString(context: Context): String {
        return when (this) {
            is StaticString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}
