package com.example.translatorapp

import com.google.gson.annotations.SerializedName

data class TranslatorModal(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("translatedText")
	val translatedText: String? = null
)
