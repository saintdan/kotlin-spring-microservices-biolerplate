package com.saintdan.framework.param

import com.saintdan.framework.annotation.NotNullField
import com.saintdan.framework.annotation.SizeField
import io.swagger.annotations.ApiModelProperty
import org.springframework.http.HttpMethod

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 17/12/2017
 * @since JDK1.8
 */
data class UserParam(
    @ApiModelProperty(hidden = true)
    var id: Long? = null,

    @ApiModelProperty(value = "username", required = true, notes = "usr must greater than or equal to 4 and less than or equal to 50.")
    @NotNullField(method = [(HttpMethod.POST)], message = "usr cannot be null.")
    @SizeField(min = 4, max = 50, method = [(HttpMethod.POST)], message = "usr must greater than or equal to 4 and less than or equal to 50.")
    var usr: String? = null, // username

    @ApiModelProperty(value = "password", required = true, notes = "pwd must greater than or equal to 4 and less than or equal to 16.")
    @NotNullField(method = [(HttpMethod.POST)], message = "pwd cannot be null.")
    @SizeField(min = 4, max = 16, method = [(HttpMethod.POST)], message = "pwd must greater than or equal to 4 and less than or equal to 16.")
    var pwd: String? = null, // password

    @ApiModelProperty(value = "user's nickname")
    var nickname: String? = null, // user's nickname

    var description: String? = null,

    @ApiModelProperty(value = "ids of roles", example = "[1,2,3]")
    var roleIds: Set<Long>? = null // role ids string
) : BaseParam()