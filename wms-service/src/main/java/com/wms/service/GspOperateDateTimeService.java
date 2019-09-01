package com.wms.service;


import com.wms.entity.GspOperteLicenseTime;
import com.wms.entity.InvLotLocId;
import com.wms.mybatis.dao.GspOperateLicenseMybatisDao;
import com.wms.query.DocMtHeaderQuery;
import com.wms.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 查询证件的效期 和计算结束天数 养护产品的day
 */
@Service("gspOperateDateTimeervice")
public class GspOperateDateTimeService {

    @Autowired
    private GspOperateLicenseMybatisDao gspOperateLicenseMybatisDao;
    @Autowired
    private DocMtHeaderService docMtHeaderService;


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
                    BeanUtils.copyProperties(gspTime, gspOperteLicenseTime);
                    //距离结束日期还要多少天
                    cal.setTime(d3);
                    long time1 = cal.getTimeInMillis();
                    //结束日期
                    cal.setTime(gspTime.getEndTime());
                    long time2 = cal.getTimeInMillis();
                    long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
                    gspOperteLicenseTime.setRemainDay(String.valueOf(betweenDays));//相差的天数
                } else {

                }
            }
            gspOperateDateTimeList.add(gspOperteLicenseTime);
        }
        return gspOperateDateTimeList;
    }

    /**
     * 根据当前系统时间查询最近30天需要养护的产品距离下一次养护还有多少天
     */
    public List<InvLotLocId> getSkuDisDay(String systemTime) throws Exception {
        String remoteDate = "1990-01-01";
        long betweenDays;//保存天数
        Calendar cal = Calendar.getInstance();//计算天数
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式
        //Date fromdate = new Date(); //测试用的当前系统时间
        Date newDate = addDate(dateFormat.parse(systemTime), 30); //传一个当前日期，基础上增加30天进行查询
        DocMtHeaderQuery query = new DocMtHeaderQuery();
        query.setFromdate(remoteDate);
        query.setTodate(dateFormat.format(newDate));
        InvLotLocId invLotLocIdDay = new InvLotLocId();
        List<InvLotLocId> invLotLocIdListDay = new ArrayList<InvLotLocId>();
        List<InvLotLocId> invLotLocIdList = docMtHeaderService.getGenerationInfo(query);
        if (invLotLocIdList.size() > 0) {
            for (InvLotLocId invLotLocId : invLotLocIdList) {
                BeanUtils.copyProperties(invLotLocId, invLotLocIdDay);
                cal.setTime(dateFormat.parse(systemTime));
                long time1 = cal.getTimeInMillis();//当前时间
                cal.setTime(invLotLocIdDay.getLotatt03test()); //预期养护时间
                long time2 = cal.getTimeInMillis();
                if (time2 < time1) {//养护日期小于当前时间
                    betweenDays = (time2 - time1) / (1000 * 3600 * 24);
                } else {
                    betweenDays = (time2 - time1) / (1000 * 3600 * 24);
                }
                invLotLocIdDay.setRemainDay(String.valueOf(betweenDays));
                invLotLocIdListDay.add(invLotLocIdDay);
            }
        }
        return invLotLocIdListDay;
    }


    public static Date addDate(Date date, long day) throws ParseException {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

}
