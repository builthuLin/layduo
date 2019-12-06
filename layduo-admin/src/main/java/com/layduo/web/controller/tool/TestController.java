package com.layduo.web.controller.tool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.layduo.common.core.controller.BaseController;
import com.layduo.common.core.domain.AjaxResult;
import com.layduo.common.utils.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * swagger 用户测试方法
 * swagger注解详解https://www.liangzl.com/get-article-detail-133618.html
 * @author layduo
 */
/**
 * @Api()用于类； 表示标识这个类是swagger的资源 
 * @ApiIgnore()用于类，方法，方法参数 表示这个方法或者类被忽略 
 */
@Api(tags = "用户信息管理", description = "TestController")
@RestController
@RequestMapping("/api/test/user")
public class TestController extends BaseController {
	private final static Map<Integer, UserEntity> users = new LinkedHashMap<Integer, UserEntity>();
	{
		users.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
		users.put(2, new UserEntity(2, "ry", "admin123", "15666666666"));
	}

	@ApiOperation("获取用户列表")
	@GetMapping("/list")
	public AjaxResult userList() {
		List<UserEntity> userList = new ArrayList<UserEntity>(users.values());
		return AjaxResult.success(userList);
	}

	/**
	 * @ApiOperation()用于方法； 表示一个http请求的操作 
	 * @ApiParam()用于方法，参数，字段说明； 表示对参数的添加元数据（说明或是否必填等） 
	 * @ApiImplicitParam() 用于方法,表示单独的请求参数 
	 * @ApiImplicitParams() 用于方法，包含多个 @ApiImplicitParam
	 * @param userId
	 * @return
	 */
	@ApiOperation(value = "获取用户详细", notes = "根据用户ID获取")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
	@GetMapping("/{userId}")
	public AjaxResult getUser(@PathVariable Integer userId) {
		if (!users.isEmpty() && users.containsKey(userId)) {
			return AjaxResult.success(users.get(userId));
		} else {
			return error("用户不存在");
		}
	}

	@ApiOperation("新增用户")
	@ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
	@PostMapping("/save")
	public AjaxResult save(UserEntity user) {
		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
			return error("用户ID不能为空");
		}
		return AjaxResult.success(users.put(user.getUserId(), user));
	}

	@ApiOperation("更新用户")
	@ApiImplicitParam(name = "userEntity", value = "新增用户信息", dataType = "UserEntity")
	@PutMapping("/update")
	public AjaxResult update(UserEntity user) {
		if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId())) {
			return error("用户ID不能为空");
		}
		if (users.isEmpty() || !users.containsKey(user.getUserId())) {
			return error("用户不存在");
		}
		users.remove(user.getUserId());
		return AjaxResult.success(users.put(user.getUserId(), user));
	}

	@ApiOperation("删除用户信息")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path")
	@DeleteMapping("/{userId}")
	public AjaxResult delete(@PathVariable Integer userId) {
		if (!users.isEmpty() && users.containsKey(userId)) {
			users.remove(userId);
			return success();
		} else {
			return error("用户不存在");
		}
	}
}
//@ApiModel()用于类 ,表示对类进行说明，用于参数用实体类接收 
@ApiModel("用户实体")
class UserEntity {
	//@ApiModelProperty用于方法，字段 ,表示对model属性的说明或者数据操作更改 
	@ApiModelProperty("用户ID")
	private Integer userId;

	@ApiModelProperty("用户名称")
	private String username;

	@ApiModelProperty("用户密码")
	private String password;

	@ApiModelProperty("用户手机")
	private String mobile;

	public UserEntity() {

	}

	public UserEntity(Integer userId, String username, String password, String mobile) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.mobile = mobile;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
