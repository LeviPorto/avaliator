package com.levi.evaluator.api

import com.levi.evaluator.dto.UserDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "\${feign.api.manager}")
@Component
interface ManagerApi {

    @RequestMapping(method = [RequestMethod.GET], value = ["/manager/user/findByIds"])
    fun retrieveByIds(@RequestParam("ids") ids: List<Int>): List<UserDTO>

}
