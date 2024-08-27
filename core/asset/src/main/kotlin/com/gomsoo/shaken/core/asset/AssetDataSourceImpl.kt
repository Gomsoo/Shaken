package com.gomsoo.shaken.core.asset

import android.content.Context
import com.gomsoo.shaken.core.asset.model.LexicalData
import com.gomsoo.shaken.core.network.Dispatcher
import com.gomsoo.shaken.core.network.ShakenDispatchers
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(ShakenDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : AssetDataSource {
    override suspend fun getTextFromFile(fileName: String): String = withContext(ioDispatcher) {
        context.assets.open(fileName).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).readText()
        }
    }

    override suspend fun getLexicalDataFromFile(
        fileName: String
    ): LexicalData = withContext(ioDispatcher) {
        Json.decodeFromString(getTextFromFile(fileName))
    }
}
