package com.ad.web;

import com.ad.web.entity.AdPo;
import com.ad.web.entity.AdPoCategory;
import com.ad.web.entity.Category;
import com.ad.web.mapper.AdPoCategoryMapper;
import com.ad.web.mapper.AdPoMapper;
import com.ad.web.mapper.CategoryMapper;
import com.ad.web.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class AdWebTest {

    static List<String> categories = Arrays.asList(
            "搜索广告",
            "展示广告",
            "视频广告",
            "社交媒体广告",
            "原生广告",
            "程序化广告",
            "移动广告",
            "横幅广告",
            "推送通知广告",
            "内容推荐广告",
            "重定向广告",
            "电子邮件广告",
            "地点基于广告",
            "视频前贴广告",
            "影响者营销广告",
            "互惠广告",
            "联盟营销广告",
            "互动广告",
            "虚拟现实广告",
            "信息流广告",
            "购物广告",
            "竞价广告",
            "应用内广告",
            "广播广告",
            "户外广告");

    static List<String> types = Arrays.asList(
            "赞助",
            "品牌",
            "音频",
            "AR",
            "电台",
            "电视");

    static List<String> platforms = Arrays.asList(
            "百度",
            "腾讯",
            "阿里",
            "字节跳动",
            "京东",
            "搜狗",
            "360",
            "美团",
            "抖音",
            "快手",
            "小红书",
            "知乎",
            "腾讯社",
            "百度信息流",
            "今日头条",
            "微信",
            "腾讯新闻",
            "优酷",
            "爱奇艺",
            "淘宝");

    @Autowired
    AdPoMapper adPoMapper;
    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    AdPoCategoryMapper adPoCategoryMapper;

    @Test
    public void TestInsertAdPo(){
        CountDownLatch latch = new CountDownLatch(10);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                m1insert();
                latch.countDown();
            }).start();

        }

        try {
            latch.await(); // 等待所有线程完成
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        }
        long end = System.currentTimeMillis();
        log.info("{}ms", end - start);
//        for (long i = 100; i < 1092; i++) {
//            String platform = platforms.get((int) (Math.random() * platforms.size()));
//            String type = platforms.get((int) (Math.random() * types.size()))+"营销";
//            AdPo adPo = new AdPo(i,null,null,null,null,platform,type,null,null,null);
//            adPoMapper.updateByPrimaryKeySelective(adPo);
//        }

//        for (long i = 1; i <= types.size(); i++) {
//            String name = types.get((int) i);
//            Category category = new Category(i, name, new Date(), null, 0);
//            categoryMapper.insertSelective(category);
//        }

//        for (long i = 1; i < 993; i++) {
//            long adPoId = (long)(Math.random()*991)+100L;
//            long categoryId = (long)(Math.random()*6);
//            AdPoCategory adPoCategory = new AdPoCategory(null,adPoId,categoryId,new Date(),null,0);
//            adPoCategoryMapper.insertSelective(adPoCategory);
//        }
    }

    private void m1insert() {
        for (long i = 0; i < 5000; i++) {
            String name = platforms.get((int) (Math.random() * platforms.size()))+"广告位";
            String content = name;
            long price = (long) (Math.random() * 10000);
            long clickNum = (long) (Math.random() * 5000);
            String platform = platforms.get((int) (Math.random() * platforms.size()));
            String type = platforms.get((int) (Math.random() * types.size()))+"营销";
            Date createTime = new Date();
            AdPo adPo = new AdPo(null,name,content,price,clickNum,platform,type,createTime,null,0);
            adPoMapper.insert(adPo);
        }
    }

    public static void main(String[] args) {
        double random = Math.random();
        System.out.println(platforms.get((int) (Math.random() * platforms.size())));
        System.out.println(random);

        System.out.println(categories.get((int) (Math.random() * categories.size())));
    }

    @Autowired
    CategoryService categoryService;
    @Test
    public void TestForGetSomeAdPoVo(){
        List<AdPo> adPoList = adPoMapper.getAdPoOrderByClickNum(7);
        for (int i = 0; i < 7; i++) {
            AdPo adPo = adPoList.get(i);
            List<String> list = categoryService.getCategoryListByAdPoId(adPo.getId())
                    .stream()
                    .map(Category::getName)
                    .filter(Objects::nonNull)
                    .toList();
            log.info(list.toString());
        }
    }

    @Test
    public void TestForGetCategories(){
        List<Long> categoryIdsByAdPoId = adPoCategoryMapper.getCategoryIdsByAdPoId(1L);
        categoryIdsByAdPoId.forEach(System.out::println);
        List<Category> list = new ArrayList<>();
        for (Long categoryId : categoryIdsByAdPoId){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            list.add(category);
        }
        list.forEach(System.out::println);

        System.out.println(categoryMapper.selectByPrimaryKey(7L));
    }
}
