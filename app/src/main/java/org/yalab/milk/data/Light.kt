package org.yalab.milk.data

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

class Light(context: Context, surfaceProvider: Preview.SurfaceProvider, lifecycleOwner: LifecycleOwner)  {
    companion object {
        private const val TAG = "Milk.light"
    }

    lateinit var cameraControl: CameraControl

    init {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(surfaceProvider)
                }
            try {
                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview
                )
                cameraControl = camera.cameraControl
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun on () : Boolean {
        cameraControl.enableTorch(true)
        return true
    }

    fun off () : Boolean {
        cameraControl.enableTorch(false)
        return false
    }
}
