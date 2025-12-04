package com.ioffeivan.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * A base class for [ViewModel]s implementing the MVI architecture.
 *
 * It manages the state lifecycle, processes events using a [Reducer],
 * and emits states and side effects to the UI.
 *
 * @param State The specific [Reducer.UiState] for this ViewModel.
 * @param Event The specific [Reducer.UiEvent] for this ViewModel.
 * @param Effect The specific [Reducer.UiEffect] for this ViewModel.
 * @property initialState The initial [State] of the screen.
 * @property reducer The [Reducer] responsible for processing events and updating the state.
 */
abstract class BaseViewModel<State : Reducer.UiState, Event : Reducer.UiEvent, Effect : Reducer.UiEffect>(
    val initialState: State,
    private val reducer: Reducer<State, Event, Effect>,
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)

    /**
     * The public, observable [StateFlow] of the current [State].
     * It automatically triggers [initialDataLoad] when collection starts
     * and persists the subscription for 5 seconds after the last collector unsubscribes.
     */
    val state: StateFlow<State> by lazy {
        _state.onStart {
            viewModelScope.launch {
                initialDataLoad()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = initialState,
        )
    }

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event>
        get() = _event.asSharedFlow()

    private val _effect = Channel<Effect>(capacity = Channel.CONFLATED)
    val effect = _effect.receiveAsFlow()

    /**
     * The main entry point for sending [Event] from the UI to the ViewModel.
     * Child ViewModels must implement this to handle UI events.
     *
     * @param event The [Reducer.UiEvent] triggered by the UI.
     */
    abstract fun onEvent(event: Event)

    /**
     * Processes an [Event] through the [reducer], updates the [state],
     * and triggers any resulting [effect].
     *
     * @param event The [Reducer.UiEvent] to process.
     */
    protected fun sendEvent(event: Event) {
        val (newState, effect) = reducer.reduce(_state.value, event)

        _state.tryEmit(newState)

        effect?.let { _effect.trySend(effect) }
    }

    /**
     * An open suspend function that is called when the [state] flow is first collected.
     *
     * Override this to perform initial data loading (e.g., fetching data from a repository).
     */
    protected open suspend fun initialDataLoad() {}
}
