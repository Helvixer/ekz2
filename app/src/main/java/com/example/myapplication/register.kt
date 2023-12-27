package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.User
import com.example.myapplication.utils.Database
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class register : AppCompatActivity() {

    private val db = Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun signUp(view: View) {
        val mail : String = findViewById<EditText>(R.id.email_edit).text.toString()
        val pass : String = findViewById<EditText>(R.id.pass_edit).text.toString()
        val username : String = findViewById<EditText>(R.id.name_edit).text.toString()
        val pass_conf = findViewById<EditText>(R.id.pass_conf_edit).text.toString()
        val emailPattern : Regex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if(mail == "" || username == "" || pass == "" || !mail.matches(emailPattern) || pass.length < 6 || pass_conf != pass){
            Toast.makeText(this, "Введите правильность введенных данных!", Toast.LENGTH_SHORT).show()
            return
        }
        val toast =  Toast.makeText(this, "Такой пользователь уже существует", Toast.LENGTH_SHORT)
        val int = Intent(this, ListActivity::class.java)
        lifecycleScope.launch {
            if(db.Register(mail, pass)){
                val user = db.getUser()
                if(user != null){
                    db.getDatabase().postgrest["users"].insert(User(user.id, username))
                }
                startActivity(int)
                finish()
            }else{
                toast.show()
            }
        }
    }
    fun signIn(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}