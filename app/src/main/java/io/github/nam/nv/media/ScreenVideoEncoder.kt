package io.github.nam.nv.media

import android.graphics.Bitmap
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaCodec
import android.media.MediaFormat
import android.media.projection.MediaProjection
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer

/**
 * 画面録画して H.264 / VP9 でエンコードする
 *
 * ファイル保存は別クラスに譲渡している
 *
 * @param displayDpi DPI
 * @param mediaProjection [MediaProjection]、画面収録するのに使います。
 */
class ScreenVideoEncoder(private val displayDpi: Int, private val mediaProjection: MediaProjection) {

    /** 映像エンコーダー、MediaCodecをラップした */
    private val videoEncoder = VideoEncoder()

    /** 画面録画に使う */
    private var virtualDisplay: VirtualDisplay? = null

    /**
     * エンコーダーを初期化する
     *
     * @param videoWidth 動画の幅
     * @param videoHeight 動画の高さ
     * @param bitRate ビットレート
     * @param frameRate フレームレート
     * @param iFrameInterval キーフレーム生成間隔
     * @param isMirroringExternalDisplay 外部ディスプレイ出力をミラーリングする場合は true
     * @param codecName [MediaFormat.MIMETYPE_VIDEO_VP9]や[MediaFormat.MIMETYPE_VIDEO_AVC]など
     * @param altImageBitmap 単一アプリミラーリング時に、アプリが画面に表示されていないときに代わりに表示する画像。アプリを切り替えたとき等。参照：[MediaProjection.Callback.onCapturedContentVisibilityChanged]
     */
    suspend fun prepareEncoder(
        videoWidth: Int,
        videoHeight: Int,
        bitRate: Int,
        frameRate: Int,
        iFrameInterval: Int = 1,
        isMirroringExternalDisplay: Boolean,
        codecName: String,
        altImageBitmap: Bitmap
    ) {
        videoEncoder.prepareEncoder(
            videoWidth = videoWidth,
            videoHeight = videoHeight,
            bitRate = bitRate,
            frameRate = frameRate,
            iFrameInterval = iFrameInterval,
            codecName = codecName,
            altImageBitmap = altImageBitmap
        )
        // Android 14 から全画面以外にアプリの画面を指定できるようになった
        // UI スレッドで呼び出す
        withContext(Dispatchers.Main) {
            mediaProjection.registerCallback(object : MediaProjection.Callback() {
                override fun onCapturedContentVisibilityChanged(isVisible: Boolean) {
                    super.onCapturedContentVisibilityChanged(isVisible)
                    // 単一アプリが非表示になった
                    // 代わりに代替画像を流す
                    videoEncoder.isDrawAltImage = !isVisible
                }

            }, null)
        }
        virtualDisplay = mediaProjection.createVirtualDisplay(
            "io.github.nam.nv",
            videoWidth,
            videoHeight,
            displayDpi,
            if (isMirroringExternalDisplay) {
                // 外部ディスプレイ出力をミラーリングします
                // HDMI に接続したような挙動になります
                DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC
            } else {
                // 端末側のミラーリング
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR
            },
            videoEncoder.drawSurface,
            null,
            null
        )
    }

    /**
     * エンコーダーを起動する。
     * ずっと一時停止します。
     *
     * @param onOutputBufferAvailable エンコードされたデータが流れてきます
     * @param onOutputFormatAvailable エンコード後のMediaFormatが入手できる
     */
    suspend fun start(
        onOutputBufferAvailable: suspend (ByteBuffer, MediaCodec.BufferInfo) -> Unit,
        onOutputFormatAvailable: suspend (MediaFormat) -> Unit,
    ) {
        videoEncoder.startVideoEncode(
            onOutputBufferAvailable = { byteBuffer, bufferInfo ->
                // エンコードされたデータが来る
                // 書き込む...
                // なんか例外が出る、コンテナファイルを切り替えてるせいなのかもしれない
                // java.lang.IllegalStateException: writeSampleData returned an error
                try {
                    onOutputBufferAvailable(byteBuffer, bufferInfo)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            onOutputFormatAvailable = onOutputFormatAvailable
        )
    }

    /**
     * このエンコーダー内部で持っている時間をリセットします
     * 次の動画ファイルに切り替えた際に呼び出す
     */
    fun resetInternalTime() {
        videoEncoder.resetInternalTime()
    }

    /** 終了時に呼ぶ */
    fun release() {
        videoEncoder.release()
        virtualDisplay?.release()
    }

}