package com.wms.service;


import com.wms.entity.GspOperteLicenseTime;
import com.wms.mybatis.dao.GspOperateLicenseMybatisDao;
import com.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *  查询证件的效期 和计算结束天数
*/
@Service("gspOperateDateTimeervice")
public class GspOperateDateTimeService {

    @Autowired
    GspOperateLicenseMybatisDao gspOperateLicenseMybatisDao;


    //根据证件和类型查询到起始时间和计算出当前系统时间到结束时间还要多少天。

    public List<GspOperteLicenseTime> queryGspOperateDateTime(String licenseId, String licenseType) throws ParseException {

        //计算天数
        Calendar cal = Calendar.getInstance();
        //Calendar calRun = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d3 = sdf.parse(sdf.format(new Date()));//获得当前时间
        GspOperteLicenseTime gspOperteLicenseTime = new GspOperteLicenseTime();
        //查询
        List<GspOperteLicenseTime> gspOperateDateTimeList = gspOperateLicenseMybatisDao.queryByListLicenseTime(licenseId, licenseType);
        if (gspOperateDateTimeList.size() > 0) {
            for (GspOperteLicenseTime gspTime : gspOperateDateTimeList
            ) {
                if (gspTime.getEndTime() != null && !gspTime.getEndTime().equals("")) {
                    BeanUtils.copyProperties(gspTime,gspOperteLicenseTime);
                    //距离结束日期还要多少天
                    cal.setTime(d3);
                    long time1 = cal.getTimeInMillis();
                    //结束日期
                    cal.setTime(gspTime.getEndTime());
                    long time2 = cal.getTimeInMillis();
                    long between_days = (time2 - time1) / (1000 * 3600 * 24);
                    gspOperteLicenseTime.setRemainDay(String.valueOf(between_days));//相差的天数
                }else{

                }
            }
            gspOperateDateTimeList.add(gspOperteLicenseTime);
        }

        return gspOperateDateTimeList;
    }

}
