package it.unical.demacs.prova

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable

class Applicazione(var nome: String,
    var icona: Drawable) {

    constructor(resolveInfo: ResolveInfo,
                packageManager: PackageManager) :
            this(nome = resolveInfo.loadLabel(packageManager).toString(),
            icona = resolveInfo.loadIcon(packageManager)) {}

    companion object {
        fun getAllInstalledApps(pm: PackageManager) : List<ResolveInfo> {
            var mainIntent: Intent = Intent(Intent.ACTION_MAIN, null)
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)

            return pm.queryIntentActivities(mainIntent, 0)
        }
    }


}