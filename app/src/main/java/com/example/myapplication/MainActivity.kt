package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.utils.Database
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val db = Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun signUp(view: View) {
        startActivity(Intent(this, register::class.java))
        finish()
    }

    fun signIn(view: View) {
        val mail : String = findViewById<EditText>(R.id.email_edit).text.toString()
        val pass : String = findViewById<EditText>(R.id.pass_edit).text.toString()
        val emailPattern : Regex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if(mail == "" || pass == "" || !mail.matches(emailPattern) || pass.length < 6){
            Toast.makeText(this, "Введите правильность введенных данных!", Toast.LENGTH_SHORT).show()
            return
        }
        val toast =  Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_SHORT)
        val int = Intent(this, ListActivity::class.java)
        lifecycleScope.launch {
            if(db.Auth(mail, pass)){
                startActivity(int)
                finish()
            }else{
                toast.show()
            }
        }
    }
}