package com.kum.service.security;

import com.kum.domain.entity.SysLogin;
import com.kum.utils.RequestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @version V1.0
 * @Package com.kum.service.security
 * @auhter 枯木Kum
 * @date 2021/2/10-4:08 PM
 */
@Service("ps")
public class PermissionService {
    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        return true;
//        LoginUser loginUser = RequestUtils.getCurrentLoginUser();
//        List<String> permissions = loginUser.getPermissions();
//        if(permission == null || permission.length() == 0){
//            return false;
//        }
//        return permissions.contains(permission);
    }
}
