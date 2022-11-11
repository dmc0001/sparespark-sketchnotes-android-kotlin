package sparespark.sketchnotes.data.provider

import android.content.Context

private const val LOCAL_ITEMS = "local_items"

class UserPreferenceProviderImpl(
    context: Context
) : PreferenceProvider(context), UserPreferenceProvider {

    override fun isLocalDataItemsAdded(): Boolean {
        return preferences.getBoolean(LOCAL_ITEMS, false)
    }

    override fun updateLocalDataItems() {
        preferencesEditor.putBoolean(LOCAL_ITEMS, true)
        preferencesEditor.apply()
    }
}