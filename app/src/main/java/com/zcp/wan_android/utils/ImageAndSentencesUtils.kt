package com.zcp.wan_android.utils

import com.zcp.wan_android.R


var splashImgs = mapOf<SolarTermsEnum, Int>(
    SolarTermsEnum.LICHUN to R.drawable.lichun,
    SolarTermsEnum.YUSHUI to R.drawable.yushui,
    SolarTermsEnum.JINGZHE to R.drawable.jingzhe,
    SolarTermsEnum.CHUNFEN to R.drawable.chunfen,
    SolarTermsEnum.QINGMING to R.drawable.qingming,
    SolarTermsEnum.GUYU to R.drawable.guyu,
    SolarTermsEnum.LIXIA to R.drawable.lixia,
    SolarTermsEnum.XIAOMAN to R.drawable.xiaoman,
    SolarTermsEnum.MANGZHONG to R.drawable.mangzhong,
    SolarTermsEnum.XIAZHI to R.drawable.xiazhi,
    SolarTermsEnum.XIAOSHU to R.drawable.xiaoshu,
    SolarTermsEnum.DASHU to R.drawable.dashu,
    SolarTermsEnum.LIQIU to R.drawable.liqiu,
    SolarTermsEnum.CHUSHU to R.drawable.chushu,
    SolarTermsEnum.BAILU to R.drawable.bailu,
    SolarTermsEnum.QIUFEN to R.drawable.qiufen,
    SolarTermsEnum.HANLU to R.drawable.hanlu,
    SolarTermsEnum.SHUANGJIANG to R.drawable.shuangjiang,
    SolarTermsEnum.LIDONG to R.drawable.lidong,
    SolarTermsEnum.XIAOXUE to R.drawable.xiaoxue,
    SolarTermsEnum.DAXUE to R.drawable.daxue,
    SolarTermsEnum.DONGZHI to R.drawable.dongzhi,
    SolarTermsEnum.XIAOHAN to R.drawable.xiaohan,
    SolarTermsEnum.DAHAN to R.drawable.dahan
)

var famousSentences = mapOf<SolarTermsEnum, List<String>>(
    SolarTermsEnum.LICHUN to listOf("今朝一岁大家添", "不是人间偏我老"),
    SolarTermsEnum.YUSHUI to listOf("最是一年春好处", "绝胜烟柳满皇都"),
    SolarTermsEnum.JINGZHE to listOf("闻道新年入山里", "蛰虫惊动春风起"),
    SolarTermsEnum.CHUNFEN to listOf("等闲识得东风面", "万紫千红总是春"),
    SolarTermsEnum.QINGMING to listOf("清明时节雨纷纷", "路上行人欲断魂"),
    SolarTermsEnum.GUYU to listOf("牡丹破萼樱桃熟", "未许飞花减却春"),
    SolarTermsEnum.LIXIA to listOf("谢却海棠飞尽絮", "困人天气日初长"),
    SolarTermsEnum.XIAOMAN to listOf("一春多雨慧当悭", "今岁还防似去年"),
    SolarTermsEnum.MANGZHONG to listOf("乙酉甲申雷雨惊", "乘除却贺芒种晴"),
    SolarTermsEnum.XIAZHI to listOf("漠漠水田飞白鹭", "阴阴夏木啭黄鹂"),
    SolarTermsEnum.XIAOSHU to listOf("幸有心期当小暑", "葛衣纱帽望回车"),
    SolarTermsEnum.DASHU to listOf("竹风荷雨来消暑", "玉李冰瓜可疗饥"),
    SolarTermsEnum.LIQIU to listOf("苦热恨无行脚处", "微凉喜到立秋时"),
    SolarTermsEnum.CHUSHU to listOf("寂寞柴门人不到", "空林独与白云期"),
    SolarTermsEnum.BAILU to listOf("白云映水摇空城", "白露垂珠滴秋月"),
    SolarTermsEnum.QIUFEN to listOf("试上高楼清入骨", "岂如春色嗾人狂"),
    SolarTermsEnum.HANLU to listOf("关塞极天唯鸟道", "江湖满地一渔翁"),
    SolarTermsEnum.SHUANGJIANG to listOf("独出前门望野田", "月明荞麦花如雪"),
    SolarTermsEnum.LIDONG to listOf("砌下梨花一堆雪", "明年谁此凭阑干"),
    SolarTermsEnum.XIAOXUE to listOf("愁人正在书窗下", "一片飞来一片寒"),
    SolarTermsEnum.DAXUE to listOf("幽州思妇十二月", "停歌罢笑双蛾摧"),
    SolarTermsEnum.DONGZHI to listOf("邯郸驿里逢冬至", "抱膝灯前影伴身"),
    SolarTermsEnum.XIAOHAN to listOf("满庭木叶愁风起", "透幌纱窗惜月沉"),
    SolarTermsEnum.DAHAN to listOf("寻常一样窗前月", "才有梅花便不同")
)


fun getSplashImg(solarTermsEnum: SolarTermsEnum?): Int {
    return if(solarTermsEnum==null){
        splashImgs[SolarTermsEnum.LICHUN]!!
    }else{
        splashImgs[solarTermsEnum]!!
    }

}

fun getFamousSentence(solarTermsEnum: SolarTermsEnum?): List<String> {
    return if(solarTermsEnum==null){
        famousSentences[SolarTermsEnum.LICHUN]!!
    }else{
        famousSentences[solarTermsEnum]!!
    }
}