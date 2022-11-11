package sparespark.sketchnotes.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T>(
    private val uiContext: CoroutineContext
) : ViewModel(), CoroutineScope {

    abstract fun handleEvent(event: T)

    // cancellation
    private var jobTracker: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = uiContext + jobTracker

    // liveData references
    protected val errorState = MutableLiveData<String>()
    val error: LiveData<String> get() = errorState

    protected val loadingState = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = loadingState

}