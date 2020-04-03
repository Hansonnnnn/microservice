package cn.edu.nju.zuulroute.configuration;

import cn.edu.nju.zuulroute.dao.ZuulRouteRepository;
import cn.edu.nju.zuulroute.entity.ZuulRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MicroserviceRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    private final static Logger logger = LoggerFactory.getLogger(MicroserviceRouteLocator.class);

    private ZuulProperties properties;

    @Autowired
    private ZuulRouteRepository zuulRouteRepository;

    public MicroserviceRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        logger.info("servletPath: {}", servletPath);
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        Map<String, ZuulProperties.ZuulRoute> values = new HashMap<>();
        values.putAll(super.locateRoutes());
        values.putAll(this.locateRoutesDynamic());
        Map<String, ZuulProperties.ZuulRoute> routeMap = new HashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : values.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            routeMap.put(path, entry.getValue());
        }
        return routeMap;
    }

    private Map<String, ZuulProperties.ZuulRoute> locateRoutesDynamic(){
        Map<String, ZuulProperties.ZuulRoute> routes = new HashMap<>();
        List<ZuulRoute> routeFromDB = zuulRouteRepository.findAll();
        for (ZuulRoute zuulRoute : routeFromDB) {
            if(StringUtils.isEmpty(zuulRoute.getPath())){
                continue;
            }
            if(StringUtils.isEmpty(zuulRoute.getServiceId()) && StringUtils.isEmpty(zuulRoute.getUrl())){
                continue;
            }
            ZuulProperties.ZuulRoute prop = new ZuulProperties.ZuulRoute();
            try {
                BeanUtils.copyProperties(zuulRoute, prop);
            } catch (Exception e) {
                logger.error("=============load zuul route info from db with error==============", e);
            }
            routes.put(zuulRoute.getPath(), prop);
        }
        return routes;
    }
}
