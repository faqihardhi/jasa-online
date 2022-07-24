package id.ac.polbeng.jasaonline.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.polbeng.jasaonline.R
import id.ac.polbeng.jasaonline.adapters.JasaAdapter
import id.ac.polbeng.jasaonline.models.Jasa
import id.ac.polbeng.jasaonline.models.JasaResponse
import id.ac.polbeng.jasaonline.services.JasaService
import id.ac.polbeng.jasaonline.services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_beranda.view.*
import retrofit2.Call
import retrofit2.Response
/**
 * A simple [Fragment] subclass.
 */
class BerandaFragment : Fragment() {
    lateinit var rvData: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(
            R.layout.fragment_beranda,
            container, false)
        rvData = rootView.findViewById(R.id.rvData)
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rvData.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }
    // override method dari onresume
    override fun onResume() {
        super.onResume()
        loadService() // memanggil method load gallery
    }
    private fun loadService() {
        val loading = ProgressDialog(context)
        loading.setMessage("Meload Jasa...")
        loading.show()
        val jasaService: JasaService =
            ServiceBuilder.buildService(JasaService::class.java)
        val requestCall: Call<JasaResponse> = jasaService.getJasa()
        requestCall.enqueue(object : retrofit2.Callback<JasaResponse>{
            override fun onFailure(call: Call<JasaResponse>, t: Throwable)
            {
                loading.dismiss()
                Toast.makeText(context, "Error terjadi ketika sedang mengambil data jasa: " + t.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onResponse(
                call: Call<JasaResponse>,
                response: Response<JasaResponse>
            ) {
                loading.dismiss()
                if(!response.body()?.error!!) {
                    val jasaResponse: JasaResponse? = response.body()
                    jasaResponse?.let {
                        val daftarJasa: List<Jasa> = jasaResponse.datas
                        val jasaAdapter = JasaAdapter(daftarJasa) {
                                service ->
                            Toast.makeText(context, "service clicked ${service.namaJasa}", Toast.LENGTH_SHORT).show()
                        }
                        jasaAdapter.notifyDataSetChanged()
                        rvData.adapter = jasaAdapter
                    }
                }else{
                    Toast.makeText(context, "Gagal menampilkan data jasa: " + response.body()?.message, Toast.LENGTH_LONG).show()
                }
            }
        });
    }
}
