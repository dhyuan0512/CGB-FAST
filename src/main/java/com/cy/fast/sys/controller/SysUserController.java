package com.cy.fast.sys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.fast.common.vo.JsonResult;
import com.cy.fast.common.vo.PageObject;
import com.cy.fast.sys.entity.SysUser;
import com.cy.fast.sys.service.FastUsersService;
import com.cy.fast.sys.service.SysUserService;
import com.cy.fast.sys.vo.SysUserDeptVo;

@RestController
@RequestMapping("/user/")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private FastUsersService fastUsersService;

    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
        PageObject<SysUserDeptVo> pageObject = sysUserService.findPageObjects(username, pageCurrent);
        return new JsonResult(pageObject);
    }

    @RequestMapping("doValidById")
    public JsonResult doValidById(Integer id, Integer valid) {
        sysUserService.validById(id, valid, "admin");// "admin"用户将来是登陆用户
        return new JsonResult("修改成功!");
    }

    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysUser entity, Integer[] roleIds) {
        sysUserService.saveObject(entity, roleIds);
        return new JsonResult("保存成功!");
    }

    @RequestMapping("doFindObjectById")
    public JsonResult doFindObjectById(Integer id) {
        Map<String, Object> map = sysUserService.findObjectById(id);
        return new JsonResult(map);
    }

    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysUser entity, Integer[] roleIds) {
        sysUserService.updateObject(entity, roleIds);
        return new JsonResult("修改成功!");
    }

    @RequestMapping("doUpdatePassword")
    public JsonResult doUpdatePassword(String pwd, String newPwd, String cfgPwd) {
        sysUserService.updatePassword(pwd, newPwd, cfgPwd);
        return new JsonResult("修改成功!");
    }

    @RequestMapping("doLogin")
    public JsonResult doLogin(String username, String password) {
        // 1.获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        // 2.通过Subject提交用户信息,交给shiro框架进行认证操作
        // 2.1对用户进行封装
        UsernamePasswordToken token = new UsernamePasswordToken(username, // 身份信息
                password);// 凭证信息
        // 2.2对用户信息进行身份认证
        subject.login(token);
        // 分析:
        // 1)token会传给shiro的SecurityManager
        // 2)SecurityManager将token传递给认证管理器
        // 3)认证管理器会将token传递给realm
        return new JsonResult("登录成功!");
    }

    @RequestMapping("login1")
    public JsonResult login1(String username, String password) {
        Subject subject1 = SecurityUtils.getSubject();
        UsernamePasswordToken token1 = new UsernamePasswordToken(username, password);
        subject1.login(token1);
        return new JsonResult("登陆成功!");
    }

    @RequestMapping("/doSaveUser")
    public JsonResult doSaveUser(SysUser entity) {
        fastUsersService.saveObjet(entity);
        return new JsonResult("注册成功");
    }

    @RequestMapping("checkName")
    public Map<String, Object> checkName(HttpServletRequest request, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        String username = request.getParameter("username");
        String name = fastUsersService.findNameByName(username);
//			 System.out.println(username);
//		        System.out.println(name);
        session.setAttribute("name", name);
        if (name != null) {
            map.put("name", name);
        }
        return map;
    }

}
