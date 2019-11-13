package com.example.mallgateway.config;

import com.example.mallgateway.database.GatewayInterfaceBean;
import com.example.mallgateway.database.dao.GatewayInterfaceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description:白名单管理
* @Author: yl
* @Date: 2019/11/13
*/
@Component
public class URLWhritList implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(URLWhritList.class);
    @Resource
    GatewayInterfaceDao interfaceDao;
    //缓存白名单
    private static Map<String, GatewayInterfaceBean> maps = new HashMap<>();
    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("------------------网关接口数据初始化开始-------------------");
        List<GatewayInterfaceBean> list = interfaceDao.listAll();
        for(GatewayInterfaceBean interfaceBean : list){
            maps.put(interfaceBean.getUrl()+interfaceBean.getMethod().toUpperCase(),interfaceBean);
        }
        LOGGER.info("------------------网关接口数据初始化完成-------------------");
    }

    public void reload(){
        maps.clear();
        List<GatewayInterfaceBean> list = interfaceDao.listAll();
        for(GatewayInterfaceBean interfaceBean : list){
            maps.put(interfaceBean.getUrl()+interfaceBean.getMethod().toUpperCase(),interfaceBean);
        }
        LOGGER.info("------------------网关接口数据重新加载完成-------------------");
    }

    public static Map<String,GatewayInterfaceBean> getMaps(){
        return maps;
    }

    //url+method
    public static GatewayInterfaceBean getInterface(String key){
        return maps.get(key);
    }
    /***
     * 增加URL到白名单数据库
     * @param service
     * @param url
     * @param method
     */
    public void insertInterface(String service,  String url, String method){

        GatewayInterfaceBean vgib = new GatewayInterfaceBean();
        vgib.setService(service);
        vgib.setUrl(url);
        vgib.setMethod(method);
        vgib.setToken(true);
        vgib.setCreateTime(new Date());
        interfaceDao.insert(vgib);
    }
}
