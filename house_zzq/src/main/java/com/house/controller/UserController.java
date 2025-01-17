package com.house.controller;

import java.util.HashMap;
import java.util.Map;

import com.house.dto.LoginUser;
import com.house.common.Result;
import com.house.common.StatusCode;
import com.house.dto.UserExecution;
import com.house.pojo.User;
import com.house.pojo.UserList;
import com.house.service.UserService;
import com.house.utils.JwtUtil;
import com.house.vo.PasswordVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户管理")
@RestController
@CrossOrigin  //跨域请求
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "登录")
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Map<String,Object> login(@RequestBody LoginUser loginUser) {
		Map<String,Object> map = new HashMap<>();
		User user = userService.login(loginUser.getAccount(),loginUser.getPassword());
		//未找到数据
		if(user == null){
			map.put("flag",false);
			return map;
		}
		//找到数据
		UserList userList = userService.findUserInfoByCondition(null,user.getId(),null);
		//生成令牌
		JwtUtil jwtUtil = new JwtUtil();
		String token = null;
		if(userList.getType() == 1){
			map.put("systemRole","admin");
			token = jwtUtil.createJWT(String.valueOf(userList.getId()),user.getUsername(),"admin");

		}else {
			map.put("systemRole","user");
			token = jwtUtil.createJWT(String.valueOf(userList.getId()),user.getUsername(),"user");
		}

		map.put("userInfo",userList);
		map.put("token",token);
		map.put("flag",true);
		return map;

	}

	@ApiOperation(value = "查询所有用户列表")
	@RequestMapping(value = "/getalluserlist",method = RequestMethod.GET)
	public Result getAllUserList(){
		return new Result(true, StatusCode.SUCCESS,"查找用户列表成功",userService.findUserListByCondition(null,null));
	}

	@ApiOperation(value = "按条件查找用户列表")
	@RequestMapping(value = "/getuserlistbycondition",method = RequestMethod.POST)
	public Result getuUerListByCondition(@RequestBody UserList userList){
		return new Result(true, StatusCode.SUCCESS,"按条件查找用户列表成功",userService.findUserListByCondition(userList.getName(),userList.getId()));
	}

	@ApiOperation(value = "按条件查找用户信息(登陆时使用)")
	@RequestMapping(value = "/getuserinfobycondition",method = RequestMethod.POST)
	public Result getUserInfoByCondition(@RequestBody UserList userList){
		return new Result(true, StatusCode.SUCCESS,"按条件查找用户列表成功",userService.findUserInfoByCondition(userList.getName(),userList.getUserId(),userList.getId()));

	}
	@ApiOperation(value = "添加用户")
	@RequestMapping(value="/adduser",method = RequestMethod.POST)
	public Result addUser(@RequestBody UserList userList){
		UserExecution ue;
		try{
			ue = userService.addUserListAndUserAccount(userList);
			if(ue.isFlag()){
				return new Result(true,StatusCode.SUCCESS,"添加用户成功");
			}else {
				return new Result(false,StatusCode.ERROR,"添加用户失败：" + ue.getReason());
			}
		}catch (Exception e){
			return new Result(false,StatusCode.ERROR,"添加用户失败：" + e.toString());
		}
	}

	@ApiOperation(value = "更新用户信息")
	@RequestMapping(value="/updateuser",method = RequestMethod.POST)
	public Result updateUser(@RequestBody UserList userList){
		UserExecution ue;
		try{
			ue = userService.updateUserList(userList);
			if(ue.isFlag()){
				return new Result(true,StatusCode.SUCCESS,"更新用户成功");
			}else {
				return new Result(false,StatusCode.ERROR,"更新用户失败：" + ue.getReason());
			}
		}catch (Exception e){
			return new Result(false,StatusCode.ERROR,"更新用户失败：" + e.toString());
		}
	}

	@ApiOperation(value = "删除用户")
	@RequestMapping(value="/deleteuser",method = RequestMethod.DELETE)
	public Result deleteUser(@RequestParam("userListid")Integer userListid){
		UserExecution ue;
		try{
			ue = userService.deleteUser(userListid);
			if(ue.isFlag()){
				return new Result(true,StatusCode.SUCCESS,"删除用户成功");
			}else {
				return new Result(false,StatusCode.ERROR,"删除用户失败：" + ue.getReason());
			}
		}catch (Exception e){
			return new Result(false,StatusCode.ERROR,"删除用户失败：" + e.toString());
		}
	}

	@ApiOperation(value = "修改密码")
	@RequestMapping(value="/editpassword",method = RequestMethod.POST)
	public Result updateUser(@RequestBody PasswordVO passwordVO){
		UserExecution ue;
		try{
			ue = userService.updatePassword(passwordVO);
			if(ue.isFlag()){
				return new Result(true,StatusCode.SUCCESS,"修改密码成功");
			}else {
				return new Result(false,StatusCode.ERROR,ue.getReason());
			}
		}catch (Exception e){
			return new Result(false,StatusCode.ERROR,"修改密码失败：" + e.toString());
		}
	}


}
