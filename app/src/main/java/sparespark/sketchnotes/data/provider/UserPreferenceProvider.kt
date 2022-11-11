package sparespark.sketchnotes.data.provider

interface UserPreferenceProvider {

    fun isLocalDataItemsAdded(): Boolean
    fun updateLocalDataItems()

}