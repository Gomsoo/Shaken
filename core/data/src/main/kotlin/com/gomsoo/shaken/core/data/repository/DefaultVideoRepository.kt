package com.gomsoo.shaken.core.data.repository

import android.util.Base64
import com.gomsoo.shaken.core.asset.AssetDataSource
import com.gomsoo.shaken.core.asset.model.LexicalData
import com.gomsoo.shaken.core.network.Dispatcher
import com.gomsoo.shaken.core.network.ShakenDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultVideoRepository @Inject constructor(
    private val assetDataSource: AssetDataSource,
    @Dispatcher(ShakenDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : VideoRepository {

    override suspend fun decodeAssetBase64(fileName: String): String = withContext(ioDispatcher) {
        val decoded = Base64.decode(assetDataSource.getTextFromFile(fileName), Base64.DEFAULT)
        String(decoded)
    }

    override suspend fun getTextFromAsset(fileName: String): String = withContext(ioDispatcher) {
        assetDataSource.getTextFromFile(fileName)
    }

    override suspend fun getLexicalDataFromAsset(
        fileName: String
    ): LexicalData = withContext(ioDispatcher) {
        assetDataSource.getLexicalDataFromFile(fileName)
    }
}
