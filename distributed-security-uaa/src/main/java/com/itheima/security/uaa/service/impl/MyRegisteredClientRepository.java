package com.itheima.security.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.security.uaa.dao.ClientDao;
import com.itheima.security.uaa.entity.ClientEntity;
import com.itheima.security.uaa.entity.MyRegisteredClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义的客户端存储库服务
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/26 16:38
 */
public class MyRegisteredClientRepository implements RegisteredClientRepository {

    @Resource
    private ClientDao clientDao;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(RegisteredClient registeredClient) {
        ClientEntity clientEntity = new ClientEntity();
        BeanUtils.copyProperties(registeredClient,clientEntity);
        clientEntity.setClientSecret(passwordEncoder.encode(clientEntity.getClientSecret()));

        //权限转换存储
        Set<String> scopes = registeredClient.getScopes();
        if (scopes!=null&&!scopes.isEmpty()){
            clientEntity.setScopes(setToStr(scopes));
        }
        clientDao.insert(clientEntity);
    }

    @Override
    public RegisteredClient findById(String id) {
        //1.从数据库查询用户
        ClientEntity clientEntity = clientDao.selectById(id);
        //2.没查到返回null
        if (clientEntity == null){
            return null;
        }
        //3.查到封装，返回
        MyRegisteredClient myRegisteredClient = new MyRegisteredClient();
        BeanUtils.copyProperties(clientEntity,myRegisteredClient);

        //4.字符串转set
        String scopes = clientEntity.getScopes();
        if (!StringUtils.isEmpty(scopes)){
            myRegisteredClient.setScopes(strToSet(scopes));
        }
        return myRegisteredClient;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        //从数据库查询用户
        ClientEntity clientEntity = clientDao.selectOne(
                new QueryWrapper<ClientEntity>().eq("client_id", clientId));
        if (clientEntity == null){
            return null;
        }
        //查询封装，返回
        MyRegisteredClient myRegisteredClient = new MyRegisteredClient();
        BeanUtils.copyProperties(clientEntity,myRegisteredClient);

        String scopes = clientEntity.getScopes();
        if (!StringUtils.isEmpty(scopes)){
            myRegisteredClient.setScopes(strToSet(scopes));
        }
        return myRegisteredClient;
    }

    //Set转字符串
    private String setToStr(Set<String> scopes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : scopes) {
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    //字符串转set
    private Set<String> strToSet(String scopes) {
        String[] strings = scopes.split(",");
        return new HashSet<>(Arrays.asList(strings));
    }
}
