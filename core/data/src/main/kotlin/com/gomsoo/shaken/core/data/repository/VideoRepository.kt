package com.gomsoo.shaken.core.data.repository

import com.gomsoo.shaken.core.asset.model.LexicalData

interface VideoRepository {
    suspend fun decodeAssetBase64(fileName: String): String
    suspend fun getTextFromAsset(fileName: String): String
    suspend fun getLexicalDataFromAsset(fileName: String): LexicalData
}
