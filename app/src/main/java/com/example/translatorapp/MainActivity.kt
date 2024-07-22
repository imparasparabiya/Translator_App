package com.example.translatorapp

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.translatorapp.Client.TranslatorClient
import com.example.translatorapp.Interface.TranslatorInterface
import com.example.translatorapp.databinding.ActivityMainBinding
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val Mic_Request_Code = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnMic.setOnClickListener {
            startVoiceInput()
        }

        binding.btnTranslate.setOnClickListener {
            var text = binding.inputedittxt.text.toString()
            getTranslator("en", "hi",text)
        }

    }

    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak Something")

        try {
                startActivityForResult(intent,Mic_Request_Code)
        }catch (e: Exception){
        e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Mic_Request_Code && resultCode == RESULT_OK){
            val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.inputedittxt.setText(result!![0])
        }
    }

    private fun getTranslator(source: String, target: String, text: String) {

        var Interface =
            TranslatorClient.getTranslatorClient()!!.create(TranslatorInterface::class.java)
        Interface.translateText(source= source,target= target,text= text).enqueue(object : Callback<TranslatorModal> {
            override fun onResponse(
                call: Call<TranslatorModal>,
                response: Response<TranslatorModal>
            ) {
                if (response.isSuccessful) {
                    binding.txtresult.text = response.body()!!.data!!.translatedText
                }
            }

            override fun onFailure(call: Call<TranslatorModal>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
}
    }