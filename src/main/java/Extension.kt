import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Locale

class Extension : ITarnhelmExt {
    override fun handleLoadString(charSequence: CharSequence): String {
        return Regex(TarnhelmExtHelper.getUrlRegex())
            .replace(charSequence){
                val okHttpClient = OkHttpClient.Builder().followRedirects(false).build()
                val request = Request.Builder().url(it.value.replace("http","https")).build()
                val response = okHttpClient.newCall(request).execute().header("location", "")!!
                if (response.contains("video/BV")){
                    val av = BvAv.b2a(response.substring(response.indexOf("BV"),response.indexOf('?')))
                    "https://www.bilibili.com/video/$av"
                }else{
                    response.substring(0,response.indexOf('?'))
                }.replace("http","https")
        }
    }

    override fun handleInformation() = arrayOf(
        "cn.ac.tarnhelm.ext.b23",
        "b23.tv",
        when (Locale.getDefault().language) {
            "zh" -> "通过请求 Bilibili API 转换 b23.tv 链接。"
            else -> "Convert b23.tv via Bilibili API."
        },
        "b23.tv",
        "lz233",
        "https://github.com/lz233/Tarnhelm-Ext-b23"
    )

    override fun handleUpdate(): String {
        TODO("Not yet implemented")
    }

}