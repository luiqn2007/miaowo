package org.miaowo.miaowo.interfaces.apiInterface

import okhttp3.ResponseBody
import org.miaowo.miaowo.API
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * /util
 * Util
 */
interface IApiUtil {

    /**
     * POST /upload
     * Uploads a File
     * Accepts: A multipart files array files[]
     */
    @Multipart
    @POST("api/v2/upload")
    fun uploadFile(@Part file: Iterable<ResponseBody>,
                   @Header("Authorization") authorization: String = "Bearer ${API.token[0]}"): Call<ResponseBody>
}