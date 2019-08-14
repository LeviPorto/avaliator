package com.levi.avaliator.apis

import com.levi.avaliator.entities.User
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "\${feign.api.manager}")
@Component
interface ManagerApi {

    @RequestMapping(method = [RequestMethod.GET], value = ["/manager/user/findByIds"])
    fun retrieveByIds(ids : List<Int>): List<User>

}