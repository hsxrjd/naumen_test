package com.wagon.hsxrjd.computerdatabase.other

import android.content.Context
import android.widget.Toast

/**
 * Created by erychkov on 7/19/17.
 */
class ToastAdapter(val context: Context) {

    var toast: Toast? = null

    fun makeToast(toastMessage: ToastMessage) {
        toastMessage.message
                ?.let { msg: String ->
                    toast?.let {
                        it.cancel()
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                            ?: let {
                        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                        toast?.show()
                    }
                }
                ?: let {
            toastMessage.resource?.let {
                msg: Int ->
                toast?.let {
                    it.cancel()
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
                        ?: let {
                    toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                    toast?.show()
                }
            }
        }
    }
}