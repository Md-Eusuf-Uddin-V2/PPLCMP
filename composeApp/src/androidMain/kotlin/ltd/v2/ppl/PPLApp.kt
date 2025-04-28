package ltd.v2.ppl

import android.app.Application
import ltd.v2.ppl.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class PPLApp : Application()  {
    override fun onCreate() {
        super.onCreate()
        multiplatform.network.cmptoast.AppContext.apply {
            set(applicationContext)
        }

        initKoin {
            androidContext(this@PPLApp)
        }
    }
}