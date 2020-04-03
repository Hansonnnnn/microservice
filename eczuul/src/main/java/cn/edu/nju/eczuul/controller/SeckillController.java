package cn.edu.nju.eczuul.controller;

import cn.edu.nju.eczuul.entity.SecGood;
import cn.edu.nju.eczuul.entity.SecGoodsOrder;
import cn.edu.nju.eczuul.redis.UserKey;
import cn.edu.nju.eczuul.service.GoodsService;
import cn.edu.nju.eczuul.service.OrderService;
import cn.edu.nju.eczuul.service.RedisUtil;
import cn.edu.nju.eczuul.service.SecGoodsOrderService;
import cn.edu.nju.eczuul.util.CookieUtil;
import entity.order.Order;
import entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rest.CodeMsg;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SeckillController {
    private RedisUtil redisUtil;

    private GoodsService goodsService;

    private SecGoodsOrderService secGoodsOrderService;

    private OrderService orderService;

    @Autowired
    public SeckillController(RedisUtil redisUtil,
                             GoodsService goodsService,
                             SecGoodsOrderService secGoodsOrderService,
                             OrderService orderService) {
        this.redisUtil = redisUtil;
        this.goodsService = goodsService;
        this.secGoodsOrderService = secGoodsOrderService;
        this.orderService = orderService;
    }

    //秒杀购买api
    @GetMapping("/seckill")
    public String seckill(Model model,
                          HttpServletRequest request,
                          @RequestParam("goodsId") Long goodsId) {
        String token = CookieUtil.readLoginToken(request);
        User user = (User) redisUtil.get(UserKey.getByName, token);
        if (user == null) {
            return "login";
        }
        //查询秒杀库存
        SecGood secGood = goodsService.getSecGoodById(goodsId);
        int stock = secGood.getStockCount();
        if (stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.SEC_KILL_OVER.getMsg());
            return "miaosha_fail";
        }

        //防止再次秒杀
        SecGoodsOrder secGoodsOrder = secGoodsOrderService.getSecGoodsOrderByGoodsIdAndUserId(goodsId, user.getId());
        if (secGoodsOrder != null) {
            model.addAttribute("errmsg", CodeMsg.SEC_KILL_REPEATED.getMsg());
            return "miaosha_fail";
        }
        //秒杀系统单独生成订单，为保证实时性
        //有定时任务，同步秒杀生成的订单到订单系统

        Order order = orderService.createOrder(user, secGood);
        model.addAttribute("order", order);
        model.addAttribute("goods", secGood);
        model.addAttribute("user", user);
        return "order_detail";
    }
}
