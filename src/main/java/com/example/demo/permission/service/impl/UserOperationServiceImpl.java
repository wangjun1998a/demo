package com.example.demo.permission.service.impl;

import com.example.demo.permission.repository.UserOperationRepository;
import com.example.demo.permission.service.UserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author alin
 */
@Service
public class UserOperationServiceImpl implements UserOperationService {

    @Autowired
    private UserOperationRepository userOperationRepository;

    public PasswordEncoder myPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 修改密码
     *
     * @param oldPwd 原密码
     * @param newPwd 新密码
     * @return modifyPwd
     */

    @Override
    public String modifyPwd(String oldPwd, String newPwd) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
//        从数据库中获取加密的密码。
        String passwordInDataBase = userOperationRepository.getPasswdWithUserName(userName);
        boolean matches = myPasswordEncoder().matches(oldPwd, passwordInDataBase);
        if (matches) {
            userOperationRepository.modifyPwd(userName, oldPwd, myPasswordEncoder().encode(newPwd));
            return "200";
        }
        return "500";
    }

    @Override
    public Map<String, Object> getHeadImagePath() {
        Map<String, Object> map = new HashMap<>();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String imgPath = userOperationRepository.getHeadImagePath(userName);
        map.put("code", 0);
        map.put("data", imgPath);
        return map;
    }

    /**
     * 修改头像
     *
     * @param file    文件路径
     * @param request http
     * @return map
     */
    @Override
    public Map modifyHeadPortrait(MultipartFile file, HttpServletRequest request) {
        String prefix = "";
        String dateStr = "";
        //保存上传
        OutputStream out = null;
        InputStream fileInput = null;
        try {
            if (file != null) {
                String userName = SecurityContextHolder.getContext().getAuthentication().getName();
                String originalName = file.getOriginalFilename();
                prefix = originalName.substring(originalName.lastIndexOf(".") + 1);
                Date date = new Date();
                String uuid = UUID.randomUUID() + "";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateStr = simpleDateFormat.format(date);
                String picPath = "/img/headPortrait/" + userName + "/" + dateStr + "/" + uuid + "." + prefix;
                Boolean bool = delAllFile("/Users/alin/privateCode/demo/src/main/resources/static/img/headPortrait" + userName);
                String filepath = "/Users/alin/privateCode/demo/src/main/resources/static" + picPath;
//                String filepath = "/Users/alin/privateCode/demo/src/main/resources/static/img/headPortrait/defult." + prefix;
                File files = new File(filepath);
                //打印查看上传路径
                System.out.println(filepath);
                if (!files.getParentFile().exists()) {
                    files.getParentFile().mkdirs();
                }
                file.transferTo(files);
                Map<String, Object> map2 = new HashMap<>();
                Map<String, Object> map = new HashMap<>();
                map.put("code", 0);
                map.put("msg", "");
                map.put("data", map2);
                map2.put("src", picPath);
//                更新数据库图片
                userOperationRepository.updateUserPictures(userName, picPath);
                return map;
            }

        } catch (Exception e) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (fileInput != null) {
                    fileInput.close();
                }
            } catch (IOException e) {
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", "");
        return map;
    }

    /***
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }
}
