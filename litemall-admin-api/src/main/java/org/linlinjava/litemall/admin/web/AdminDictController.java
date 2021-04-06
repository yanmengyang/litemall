package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.LogHelper;
import org.linlinjava.litemall.core.util.RegexUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallAdmin;
import org.linlinjava.litemall.db.service.LitemallAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.linlinjava.litemall.admin.util.AdminResponseCode.ADMIN_INVALID_NAME;
import static org.linlinjava.litemall.admin.util.AdminResponseCode.ADMIN_INVALID_PASSWORD;

@RestController
@RequestMapping("/admin/dict")
@Validated
public class AdminDictController {
    private final Log logger = LogFactory.getLog(AdminAdminController.class);

    @Autowired
    private LitemallAdminService adminService;
    @Autowired
    private LogHelper logHelper;

    @RequiresPermissions("admin:dict:list")
    @RequiresPermissionsDesc(menu = {"家政管理", "字典管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String username,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallAdmin> adminList = adminService.querySelective(username, page, limit, sort, order);
        return ResponseUtil.okList(adminList);
    }

    private Object validate(LitemallAdmin admin) {
        String name = admin.getUsername();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isUsername(name)) {
            return ResponseUtil.fail(ADMIN_INVALID_NAME, "管理员名称不符合规定");
        }
        String password = admin.getPassword();
        if (StringUtils.isEmpty(password) || password.length() < 6) {
            return ResponseUtil.fail(ADMIN_INVALID_PASSWORD, "管理员密码长度不能小于6");
        }
        return null;
    }

    @RequiresPermissions("admin:dict:create")
    @RequiresPermissionsDesc(menu = {"家政管理", "字典管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody LitemallAdmin admin) {

        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:dict:read")
    @RequiresPermissionsDesc(menu = {"家政管理", "字典管理"}, button = "详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        LitemallAdmin admin = adminService.findById(id);
        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:dict:update")
    @RequiresPermissionsDesc(menu = {"家政管理", "字典管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody LitemallAdmin admin) {

        return ResponseUtil.ok(admin);
    }

    @RequiresPermissions("admin:dict:delete")
    @RequiresPermissionsDesc(menu = {"家政管理", "字典管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody LitemallAdmin admin) {

        return ResponseUtil.ok();
    }
}
