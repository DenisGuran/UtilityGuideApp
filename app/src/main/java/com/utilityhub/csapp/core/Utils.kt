package com.utilityhub.csapp.core

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.utilityhub.csapp.R

class Utils {
    companion object{
        fun getMapBackgroundBlurDrawable(map: String, context: Context): Drawable? {
            val backgroundId = when(map){
                context.getString(R.string.mirage) -> R.drawable.mirage_background_blur
                context.getString(R.string.inferno) -> R.drawable.inferno_background_blur
                context.getString(R.string.dust2) -> R.drawable.dust2_background_blur
                context.getString(R.string.overpass) -> R.drawable.overpass_background_blur
                context.getString(R.string.vertigo) -> R.drawable.vertigo_background_blur
                context.getString(R.string.nuke) -> R.drawable.nuke_background_blur
                context.getString(R.string.ancient) -> R.drawable.ancient_background_blur
                else -> R.drawable.main_bg
            }
            return AppCompatResources.getDrawable(context, backgroundId)
        }

        fun getMapBackgroundBlur(map: String, context: Context): Int {
            return when(map){
                context.getString(R.string.mirage) -> R.drawable.mirage_background_blur
                context.getString(R.string.inferno) -> R.drawable.inferno_background_blur
                context.getString(R.string.dust2) -> R.drawable.dust2_background_blur
                context.getString(R.string.overpass) -> R.drawable.overpass_background_blur
                context.getString(R.string.vertigo) -> R.drawable.vertigo_background_blur
                context.getString(R.string.nuke) -> R.drawable.nuke_background_blur
                context.getString(R.string.ancient) -> R.drawable.ancient_background_blur
                else -> R.drawable.main_bg
            }
        }

        fun getUtilityPin(utilityType: String): Int{
            return when(utilityType){
                Constants.SMOKES_REF -> R.drawable.ic_smoke
                Constants.FLASHES_REF -> R.drawable.ic_flash
                Constants.MOLOTOVS_REF -> R.drawable.ic_molotov
                Constants.HE_GRENADES_REF -> R.drawable.ic_he_grenade
                else -> R.drawable.ic_smoke
            }
        }
    }

}