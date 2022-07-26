package id.ac.polbeng.jasaonline.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import id.ac.polbeng.jasaonline.R
import id.ac.polbeng.jasaonline.helpers.Config
import id.ac.polbeng.jasaonline.models.LoginResponse
import id.ac.polbeng.jasaonline.models.User
import id.ac.polbeng.jasaonline.services.ServiceBuilder
import id.ac.polbeng.jasaonline.services.UserService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if(TextUtils.isEmpty(email)){
                etEmail.error = "Email tidak boleh kosong!"
                etEmail.requestFocus()
                return@setOnClickListener

            }
            if(TextUtils.isEmpty(password)){
                etPassword.error = "Password tidak boleh kosong!"
                etPassword.requestFocus()
                return@setOnClickListener

            }
            val filter = HashMap<String, String>()
            filter["email"] = email
            filter["password"] = password
            val userService: UserService =
                ServiceBuilder.buildService(UserService::class.java)
            val requestCall: Call<LoginResponse> =
                userService.loginUser(filter)
            requestCall.enqueue(object :
                retrofit2.Callback<LoginResponse>{
                override fun onFailure(call: Call<LoginResponse>, t:
                Throwable) {
                    Toast.makeText(this@LoginActivity, "Error terjadi ketika sedang login: " + t.toString(), Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<LoginResponse>,
                                        response: Response<LoginResponse>
                ) {
                    if(!response.body()?.error!!) {
                        val loginResponse: LoginResponse? =
                            response.body()
                        loginResponse?.let {
                            val user: User = loginResponse.data

                            Toast.makeText(this@LoginActivity, "Pengguna${user.nama} dengan email ${user.email} berhasil login!", Toast.LENGTH_LONG).show()
                            loadMainActivity()
                        }
                    }else{
                        Toast.makeText(this@LoginActivity, "Gagal login: "
                                + response.body()?.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
        tvDaftar.setOnClickListener {
            val intent = Intent(applicationContext,
                RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loadMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra(Config.EXTRA_FRAGMENT_ID, R.id.nav_beranda)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}