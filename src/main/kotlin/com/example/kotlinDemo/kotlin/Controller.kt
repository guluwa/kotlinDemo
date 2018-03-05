package com.example.kotlinDemo.kotlin

import cn.guluwa.kotlindemo.retrofit.ResultBean
import com.example.kotlinDemo.StoreRepository
import com.example.kotlinDemo.UserBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class CustomerController
@Autowired constructor(private val repository: StoreRepository) {

    @RequestMapping("/")
    fun findAll() = repository.findAll()

    @RequestMapping("/create", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun create(@RequestBody userBean: UserBean): UserBean = repository.save(userBean)

    // 这里是新增的
    @RequestMapping("/{username}")
    fun findByLastName(@PathVariable username: String) = repository.findByUsername(username)

    @PutMapping("/update")
    fun updateUser(@RequestBody user: UserBean) {
        repository.save(user)
    }

    @RequestMapping("/del/{id}")
    @ResponseBody
    fun deleteEmployee(@PathVariable id: Long) {
        repository.delete(id)
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    @ResponseBody
    fun login(username: String, password: String): ResultBean {
        return if (username.isEmpty() || password.isEmpty()) {
            ResultBean(201, "The username and password can not be empty")
        } else {
            val users = repository.findByUsername(username)
            if (users.isEmpty()) {
                ResultBean(201, "user does not exist")
            } else {
                if (password == users[0].password) {
                    ResultBean(200, "login success")
                } else {
                    ResultBean(201, "wrong password")
                }
            }
        }
    }

    /**
     * 注册
     */
    @RequestMapping("/register")
    @ResponseBody
    fun register(username: String, password: String): ResultBean {
        return if (username.isEmpty() || password.isEmpty()) {
            ResultBean(201, "The username and password can not be empty")
        } else {
            val users = repository.findByUsername(username)
            return if (users.isEmpty()) {
                val userBean = repository.save(UserBean(username, password))
                if (userBean != null) {
                    ResultBean(200, "register success")
                } else {
                    ResultBean(201, "register failed")
                }
            } else {
                ResultBean(201, "The user has already existed")
            }
        }
    }
}