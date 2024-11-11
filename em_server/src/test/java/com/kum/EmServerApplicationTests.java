package com.kum;

import com.kum.domain.entity.SysUser;
import com.kum.service.*;
import com.kum.utils.QuartzUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmServerApplicationTests {

    @Autowired
    private QuartzUtils quartzUtils;

    @Autowired
    private SysNoticeService sysNoticeService;

    @Autowired
    private SysUserPlayRecordService sysUserPlayRecordService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Test
    void QuartzTest() {
        quartzUtils.delete("job1","group1");

    }

    @Test
    void backups() {
        String cmd = "mysqldump -u root -p123456 estate_management -r \\Volumes\\data\\temp\\a.sql";
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            System.out.println("备份数据库成功!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void userInfoTest() {
        System.out.println(sysNoticeService.getNumber());
    }

    @Test
    void register(){
        SysUser sysUser = new SysUser().builder()
                .userName("hhhh")
                .fullName("王五")
                .password("123456")
                .phone("13444444444")
                .status("0")
                .build();

        String userId = sysUserService.register(sysUser);
        System.out.println(userId);

    }

    @Test
    void findUserAcl() {
        System.out.println(sysUserService.findUserAcl("aaa"));
    }

    @Test
    void FindUserRole() {
        System.out.println(sysUserRoleService.findUserRole("aaa"));
    }

    @Test
    void userPayRecordFindByOfMonth() {
        System.out.println(sysUserPlayRecordService.findByOfMonth("aaa"));
    }
}
