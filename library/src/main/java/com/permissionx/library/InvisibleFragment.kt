package com.permissionx.library

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {
    private var callback : PermissionCallback? = null
    fun requestNow(callback: PermissionCallback, vararg permissions: String) {
        this.callback = callback
        requestPermissions(permissions, 1)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            val deniedList = mutableListOf<String>()
            grantResults.forEachIndexed { index, grantResult ->
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let { it(allGranted, deniedList) }
        }
    }
}

