package com.ioffeivan.core.presentation

/**
 * Interface for reducers in the MVI pattern, responsible for computing new UI states
 * and optional effects based on the previous state and incoming events.
 *
 * Reducers should be pure functions without side effects to ensure predictability and testability.
 *
 * @param State The type of UI state, must implement [UiState].
 * @param Event The type of UI event, must implement [UiEvent].
 * @param Effect The type of UI effect, must implement [UiEffect].
 */
interface Reducer<State : Reducer.UiState, Event : Reducer.UiEvent, Effect : Reducer.UiEffect> {
    /**
     * A marker interface representing the state of a UI component (e.g., a screen).
     */
    interface UiState

    /**
     * A marker interface representing an action or event that can trigger a state change.
     */
    interface UiEvent

    /**
     * A marker interface for one-time side effects that should be consumed by the UI.
     */
    interface UiEffect

    /**
     * Reduces the given [event] with the [previousState] to produce a new result.
     * This function should be pure (free of side effects).
     *
     * @param previousState The current [UiState] before the event.
     * @param event The [UiEvent] to process.
     * @return A [ReducerResult] containing the new [UiState] and an optional [UiEffect].
     */
    fun reduce(previousState: State, event: Event): ReducerResult<State, Effect>
}

/**
 * A data class that holds the result of a [Reducer.reduce] operation.
 * It contains the new state and an optional side effect.
 *
 * @param State The new state.
 * @param Effect The optional side effect.
 * @property state The new [Reducer.UiState] to be emitted.
 * @property effect An optional [Reducer.UiEffect] to be triggered. `null` if no effect is needed.
 */
data class ReducerResult<out State, out Effect>(
    val state: State,
    val effect: Effect? = null,
)
