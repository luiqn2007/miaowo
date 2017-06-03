package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class Session(

	@field:SerializedName("datetime")
	val datetime: Long? = null,

	@field:SerializedName("current")
	val current: Boolean? = null,

	@field:SerializedName("ip")
	val ip: String? = null,

	@field:SerializedName("browser")
	val browser: String? = null,

	@field:SerializedName("datetimeISO")
	val datetimeISO: String? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null,

	@field:SerializedName("version")
	val version: String? = null,

	@field:SerializedName("platform")
	val platform: String? = null
)