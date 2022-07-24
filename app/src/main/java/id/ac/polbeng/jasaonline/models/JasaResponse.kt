package id.ac.polbeng.jasaonline.models

data class JasaResponse (
    val message: String,
    val error: Boolean,
    val datas: List<Jasa>
)
