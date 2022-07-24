package id.ac.polbeng.jasaonline.services

import id.ac.polbeng.jasaonline.models.JasaResponse
import retrofit2.Call
import retrofit2.http.GET
interface JasaService {
    @GET("services")
    fun getJasa() : Call<JasaResponse>
}