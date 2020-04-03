package cn.edu.nju.eczuul.controller;

import cn.edu.nju.eczuul.dao.SecGoodsRepository;
import cn.edu.nju.eczuul.entity.SecGood;
import cn.edu.nju.eczuul.redis.UserKey;
import cn.edu.nju.eczuul.service.RedisUtil;
import cn.edu.nju.eczuul.util.CookieUtil;
import entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private SecGoodsRepository secGoodsRepository;

    private RedisUtil redisUtil;

    @Autowired
    public GoodsController(SecGoodsRepository secGoodsRepository, RedisUtil redisUtil) {
        this.secGoodsRepository = secGoodsRepository;
        this.redisUtil = redisUtil;
    }

    @RequestMapping("/list")
    public String list(Model model) {
        //goods service get goods list
        List<SecGood> secGoods = secGoodsRepository.findAll();
        //set model
        model.addAttribute("goodsList", secGoods);
        return "goods_list";
    }

    //detail - 基础信息、秒杀开始还是结束信息
    @RequestMapping("/detail/{id}")
    public String detail(HttpServletRequest request,
                         Model model,
                         @PathVariable("id")Long id) {
        String token = CookieUtil.readLoginToken(request);
        User user = (User) redisUtil.get(UserKey.getByName, token);
        model.addAttribute("user", user);
        SecGood secGood = secGoodsRepository.getOne(id);
        model.addAttribute("goods", secGood);
        long start = secGood.getStartTime().getTime();
        long end = secGood.getEndTime().getTime();
        long now = System.currentTimeMillis();
        int seckillStatus;
        int remainSeconds;
        if (now < start) {
            seckillStatus = 0;
            remainSeconds = (int)((start - now) / 1000);
        } else if (now > end) {
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
