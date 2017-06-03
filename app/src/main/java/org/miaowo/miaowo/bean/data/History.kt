package org.miaowo.miaowo.bean.data

import javax.annotation.Generated
import com.google.gson.annotations.SerializedName

@Generated("com.robohorse.robopojogenerator")
data class History(

	@field:SerializedName("reasons")
	val reasons: List<Any?>? = null,

	@field:SerializedName("flags")
	val flags: List<Any?>? = null,

	@field:SerializedName("bans")
	val bans: List<Any?>? = null
)