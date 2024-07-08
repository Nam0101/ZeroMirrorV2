package io.github.nam.nv.dash

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * MPEG-DASH 用ファイル管理クラス
 *
 * - [parentFolder]
 *  - public
 *      - 生成した動画を入れるフォルダ、クライアントに返してる動画です
 *  - temp
 *      - 一時的に保存する必要のあるファイル
 *
 * @param parentFolder 保存先
 * @param audioPrefixName 音声ファイルの先頭につける文字列
 * @param videoPrefixName 映像ファイルの先頭につける文字列
 */
class DashContentManager(
    private val parentFolder: File,
    private val audioPrefixName: String,
    private val videoPrefixName: String,
) {
    // TODO ここなんか連番なファイルにするクラスにしたい
    /** 作るたびにインクリメントする、音声版 */
    private var audioCount = 0

    /** 作るたびにインクリメントする、映像版 */
    private var videoCount = 0

    /** 生成したメディアセグメントを保持する、音声用 */
    private val audioFileList = arrayListOf<File>()

    /** 生成したメディアセグメントを保持する、映像用 */
    private val videoFileList = arrayListOf<File>()

    /** 完成品を公開するフォルダ */
    private val outputFolder = getOutputFolder(parentFolder).apply { mkdir() }

    /**
     * 連番な音声ファイルを作る。
     * ファイル書き込み中は .temp がおしりにつく。
     * なんか書き込み中だったのかよくわからないのですが、Ktorがファイルデカすぎ例外を吐いたので...書き込み中はファイル名を一時的なものに
     *
     * @param fileIO この関数内でファイル操作ができます。作業が終わると、ファイル名は一時的なものから変更されます。
     * @return 完成したファイル
     */
    suspend fun createIncrementAudioFile(fileIO: suspend (File) -> Unit) = withContext(Dispatchers.IO) {
        val index = audioCount++
        val resultFile = File(outputFolder, "$audioPrefixName${index}.$WEBM_EXTENSION")
        val tempFile = File(outputFolder, "$audioPrefixName${index}.$WEBM_EXTENSION.$FILE_WRITING_SUFFIX")
        // ファイル作業する...
        fileIO(tempFile)
        tempFile.renameTo(resultFile)
        // 古くなったデータを消す
        audioFileList.add(resultFile)
        removeUnUseFile(audioFileList)
        return@withContext resultFile
    }

    /**
     * 連番な映像ファイルを生成する。
     * [createIncrementAudioFile]の映像ファイル版になります。
     *
     * @param fileIO ファイル作業
     * @return 完成したファイル
     */
    suspend fun createIncrementVideoFile(fileIO: suspend (File) -> Unit) = withContext(Dispatchers.IO) {
        val index = videoCount++
        val resultFile = File(outputFolder, "$videoPrefixName${index}.$WEBM_EXTENSION")
        val tempFile = File(outputFolder, "$videoPrefixName${index}.$WEBM_EXTENSION.$FILE_WRITING_SUFFIX")
        // ファイル作業する...
        fileIO(tempFile)
        tempFile.renameTo(resultFile)
        videoFileList.add(resultFile)
        removeUnUseFile(videoFileList)
        return@withContext resultFile
    }

    /**
     * ファイルを生成する関数。
     * ファイル名変更が出来る以外は [createIncrementAudioFile] [createIncrementVideoFile] と同じ。
     *
     * @param fileName ファイル名
     * @return [File]
     */
    suspend fun createFile(fileName: String) = withContext(Dispatchers.IO) {
        File(outputFolder, fileName).apply {
            createNewFile()
        }
    }

    /** 生成したファイルを削除する */
    suspend fun deleteGenerateFile() = withContext(Dispatchers.IO) {
        outputFolder.listFiles()?.forEach { it.delete() }
    }

    /**
     * もう利用しないであろうメディアセグメントを削除する
     * 新しいのを [FILE_HOLD_COUNT]個 残して後は消す
     *
     * @param fileList メディアセグメントの配列
     */
    private suspend fun removeUnUseFile(fileList: ArrayList<File>) = withContext(Dispatchers.IO) {
        val deleteItemSize = fileList.size - FILE_HOLD_COUNT
        if (deleteItemSize >= 0) {
            fileList.take(deleteItemSize).forEach { it.delete() }
        }
    }

    companion object {
        /** 拡張子 */
        private const val WEBM_EXTENSION = "webm"

        /** 完成品の動画が入るフォルダの名前 */
        private const val OUTPUT_VIDEO_FOLDER_NAME = "dist"

        /** 書き込み中ファイルの末尾につける */
        private const val FILE_WRITING_SUFFIX = "temp"

        /** セグメントファイル保持数、少なすぎると途中から MPEG-DASH 再生が出来ない？ */
        private const val FILE_HOLD_COUNT = 10

        /**
         * 生成した 動画/音声 の保存先を返す
         *
         * @param parentFolder 親の [File]
         * @return 保存先 [File]
         */
        fun getOutputFolder(parentFolder: File): File = parentFolder.resolve(
            OUTPUT_VIDEO_FOLDER_NAME
        )
    }

}