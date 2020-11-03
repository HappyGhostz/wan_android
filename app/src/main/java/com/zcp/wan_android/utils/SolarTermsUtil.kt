package com.zcp.wan_android.utils

import java.lang.Exception

enum class SolarTermsEnum {
    LICHUN,  // --立春
    YUSHUI,  // --雨水
    JINGZHE,  // --惊蛰
    CHUNFEN,  // 春分
    QINGMING,  // 清明
    GUYU,  // 谷雨
    LIXIA,  // 立夏
    XIAOMAN,  // 小满
    MANGZHONG,  // 芒种
    XIAZHI,  // 夏至
    XIAOSHU,  // 小暑
    DASHU,  // 大暑
    LIQIU,  // 立秋
    CHUSHU,  // 处暑
    BAILU,  // 白露
    QIUFEN,  // 秋分
    HANLU,  // 寒露
    SHUANGJIANG,  // 霜降
    LIDONG,  // 立冬
    XIAOXUE,  // 小雪
    DAXUE,  // 大雪
    DONGZHI,  // 冬至
    XIAOHAN,  // 小寒
    DAHAN
    // 大寒
}

/// 抄的网上的一个，原文：https to//blog.csdn.net/Sun_LeiLei/article/details/50148297
class SolarTermsUtil {
    ///依次代表立春、雨水...大寒节气的C值
    var centuryArray = mapOf<SolarTermsEnum, Double>(
        SolarTermsEnum.LICHUN to 3.87,
        SolarTermsEnum.YUSHUI to 18.73,
        SolarTermsEnum.JINGZHE to 5.63,
        SolarTermsEnum.CHUNFEN to 20.646,
        SolarTermsEnum.QINGMING to 4.81,
        SolarTermsEnum.GUYU to 20.1,
        SolarTermsEnum.LIXIA to 5.52,
        SolarTermsEnum.XIAOMAN to 21.04,
        SolarTermsEnum.MANGZHONG to 5.678,
        SolarTermsEnum.XIAZHI to 21.37,
        SolarTermsEnum.XIAOSHU to 7.108,
        SolarTermsEnum.DASHU to 22.83,
        SolarTermsEnum.LIQIU to 7.5,
        SolarTermsEnum.CHUSHU to 23.13,
        SolarTermsEnum.BAILU to 7.646,
        SolarTermsEnum.QIUFEN to 23.042,
        SolarTermsEnum.HANLU to 8.318,
        SolarTermsEnum.SHUANGJIANG to 23.438,
        SolarTermsEnum.LIDONG to 7.438,
        SolarTermsEnum.XIAOXUE to 22.36,
        SolarTermsEnum.DAXUE to 7.18,
        SolarTermsEnum.DONGZHI to 21.94,
        SolarTermsEnum.XIAOHAN to 5.4055,
        SolarTermsEnum.DAHAN to 20.12
    )

    var D = 0.2422
    var increaseOffsetMap = mapOf<SolarTermsEnum, List<Int>>() // +1偏移
    var decreaseOffsetmap = mapOf<SolarTermsEnum, List<Int>>() // -1偏移

    var mYear: Int = 0

    var mSolarData = mutableListOf<String>()
    var mSolarName = mutableListOf<String>()

    ///判断一天是什么节气
    ///@data2015-12-2下午2:49:32
    ///@param year
    ///@param data 月份占两位，日不确定，如一月一日为：011，五月十日为0510
    ///@return
    fun getSolatName(year: Int, month: Int, day: Int): SolarTermsEnum? {
        if (year != mYear) {
            return solarTermToString(year, month, day)
        }
        return null
    }

    ///
    /// @param year
    ///            年份
    /// @param name
    ///            节气的名称
    /// @return 返回节气是相应月份的第几天
    private fun getSolarTermNum(year: Int, solarTermName: SolarTermsEnum): Int {
        var centuryValue = 0.0 // 节气的世纪值，每个节气的每个世纪值都不同
        if (year < 2001 || year > 2100) {
            throw Exception("不支持此年份：\" + $year + \"，目前只支持1901年到2100年的时间范围")
        }
        centuryValue = centuryArray[solarTermName]!!
        var dateNum = 0

        /// 计算 num =[Y///D+C]-L这是传说中的寿星通用公式
        /// 公式解读：年数的后2位乘0.2422加C(即：centuryValue)取整数后，减闰年数
        var y = year % 100 // 步骤1:取年分的后两位数
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            // 闰年
            if (solarTermName == SolarTermsEnum.XIAOHAN ||
                solarTermName == SolarTermsEnum.DAHAN ||
                solarTermName == SolarTermsEnum.LICHUN ||
                solarTermName == SolarTermsEnum.YUSHUI
            ) {
                // 注意：凡闰年3月1日前闰年数要减一，即：L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1日,所以
                // y = y-1
                y -= 1 /// 步骤2
            }
        }
        dateNum = (y * D + centuryValue).toInt() - (y / 4).toInt() // 步骤3，使用公式[Y///D+C]-L计算
        dateNum += specialYearOffset(year, solarTermName) // 步骤4，加上特殊的年分的节气偏移量
        return dateNum;
    }

    ///特例,特殊的年分的节气偏移量,由于公式并不完善，所以算出的个别节气的第几天数并不准确，在此返回其偏移量
    ///
    /// @param year
    ///            年份
    /// @param name
    ///            节气名称
    /// @return 返回其偏移量
    private fun specialYearOffset(year: Int, name: SolarTermsEnum): Int {
        initOffsetMap()
        var offset = 0
        offset += getOffset(decreaseOffsetmap, year, name, -1)
        offset += getOffset(increaseOffsetMap, year, name, 1)

        return offset
    }

    private fun getOffset(
        map: Map<SolarTermsEnum, List<Int>>,
        year: Int,
        name: SolarTermsEnum,
        offset: Int
    ): Int {
        var off = 0
        var years = map[name]
        if (null != years) {
            for (i in years) {
                if (i == year) {
                    off = offset
                    break
                }
            }
        }
        return off
    }

    private fun solarTermToString(year: Int, month: Int, day: Int): SolarTermsEnum {
        mYear = year
        mSolarData.clear()
        mSolarName.clear()
        when (month) {
            2 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.YUSHUI)) {
                    return SolarTermsEnum.YUSHUI
                } else if (day < getSolarTermNum(year, SolarTermsEnum.LICHUN)) {
                    return SolarTermsEnum.DAHAN
                }
                return SolarTermsEnum.LICHUN
            }

            3 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.CHUNFEN)) {
                    return SolarTermsEnum.CHUNFEN;
                } else if (day < getSolarTermNum(year, SolarTermsEnum.JINGZHE)) {
                    return SolarTermsEnum.YUSHUI;
                }
                return SolarTermsEnum.JINGZHE
            }
            4 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.GUYU)) {
                    return SolarTermsEnum.GUYU;
                } else if (day < getSolarTermNum(year, SolarTermsEnum.QINGMING)) {
                    return SolarTermsEnum.CHUNFEN;
                }
                return SolarTermsEnum.QINGMING
            }

            5 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.XIAOMAN)) {
                    return SolarTermsEnum.XIAOMAN;
                } else if (day < getSolarTermNum(year, SolarTermsEnum.LIXIA)) {
                    return SolarTermsEnum.GUYU;
                }
                return SolarTermsEnum.LIXIA
            }

            6 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.XIAZHI)) {
                    return SolarTermsEnum.XIAZHI;
                } else if (day < getSolarTermNum(year, SolarTermsEnum.MANGZHONG)) {
                    return SolarTermsEnum.XIAOMAN;
                }
                return SolarTermsEnum.MANGZHONG
            }

            7 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.DASHU)) {
                    return SolarTermsEnum.DASHU;
                } else if (day < getSolarTermNum(year, SolarTermsEnum.XIAOSHU)) {
                    return SolarTermsEnum.XIAZHI;
                }
                return SolarTermsEnum.XIAOSHU
            }

            8 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.CHUSHU)) {
                    return SolarTermsEnum.CHUSHU;
                } else if (day < getSolarTermNum(year, SolarTermsEnum.LIQIU)) {
                    return SolarTermsEnum.DASHU;
                }
                return SolarTermsEnum.LIQIU
            }

            9 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.QIUFEN)) {
                    return SolarTermsEnum.QIUFEN;
                } else if (day < getSolarTermNum(year, SolarTermsEnum.BAILU)) {
                    return SolarTermsEnum.CHUSHU;
                }
                return SolarTermsEnum.BAILU
            }

            10 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.SHUANGJIANG)) {
                    return SolarTermsEnum.SHUANGJIANG;
                } else if (day < getSolarTermNum(year, SolarTermsEnum.HANLU)) {
                    return SolarTermsEnum.QIUFEN;
                }
                return SolarTermsEnum.HANLU
            }

            11 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.XIAOXUE)) {
                    return SolarTermsEnum.XIAOXUE;
                } else if (day < getSolarTermNum(year, SolarTermsEnum.LIDONG)) {
                    return SolarTermsEnum.SHUANGJIANG;
                }
                return SolarTermsEnum.LIDONG
            }

            12 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.DONGZHI)) {
                    return SolarTermsEnum.DONGZHI;
                } else if (day < getSolarTermNum(year, SolarTermsEnum.DAXUE)) {
                    return SolarTermsEnum.XIAOXUE;
                }
                return SolarTermsEnum.DAXUE
            }

            1 -> {
                if (day >= getSolarTermNum(year, SolarTermsEnum.DAHAN)) {
                    return SolarTermsEnum.DAHAN;
                } else if (day < getSolarTermNum(year, SolarTermsEnum.XIAOHAN)) {
                    return SolarTermsEnum.DONGZHI;
                }
                return SolarTermsEnum.XIAOHAN
            }
            else -> return SolarTermsEnum.XIAZHI
        }
    }

    private fun initOffsetMap() {
        decreaseOffsetmap = mapOf(
            SolarTermsEnum.YUSHUI to listOf(2026),
            SolarTermsEnum.DONGZHI to listOf(1918, 2021),
            SolarTermsEnum.XIAOHAN to listOf(2019)
        )
        increaseOffsetMap = mapOf(
            SolarTermsEnum.CHUNFEN to listOf(2084),
            SolarTermsEnum.XIAOMAN to listOf(2008),
            SolarTermsEnum.MANGZHONG to listOf(1902),
            SolarTermsEnum.XIAZHI to listOf(1928),
            SolarTermsEnum.XIAOSHU to listOf(1925, 2016),
            SolarTermsEnum.DASHU to listOf(1922),
            SolarTermsEnum.LIQIU to listOf(2002),
            SolarTermsEnum.BAILU to listOf(1927),
            SolarTermsEnum.QIUFEN to listOf(1942),
            SolarTermsEnum.SHUANGJIANG to listOf(2089),
            SolarTermsEnum.LIDONG to listOf(2089),
            SolarTermsEnum.XIAOXUE to listOf(1978),
            SolarTermsEnum.DAXUE to listOf(1954),
            SolarTermsEnum.XIAOHAN to listOf(1982),
            SolarTermsEnum.DAHAN to listOf(2082)
        )
    }
}