package com.gomsoo.shaken.core.asset

import com.gomsoo.shaken.core.asset.model.LexicalData

interface AssetDataSource {
    suspend fun getTextFromFile(fileName: String): String
    suspend fun getLexicalDataFromFile(fileName: String): LexicalData
}
