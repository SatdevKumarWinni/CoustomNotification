package `in`.practical.coustomnotification.stories.helper
import android.view.View

interface MomentzCallback{
    fun done()

    fun onNextCalled(view: View, momentz : Momentz, index: Int)
    fun onMoreDetails(index: Int)
}
