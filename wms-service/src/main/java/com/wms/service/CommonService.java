package com.wms.service;

import com.wms.entity.BasSerialNum;
import com.wms.entity.BasSku;
import com.wms.mybatis.dao.BasSerialNumMybatisDao;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.CommonMybatisDao;
import com.wms.query.BasSerialNumQuery;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.utils.StringUtil;
import com.wms.vo.pda.CommonVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/7/8
 */
@Service("commonService")
public class CommonService extends BaseService{

    @Autowired
    private CommonMybatisDao commonMybatisDao;
    @Autowired
    private BasSerialNumMybatisDao basSerialNumMybatisDao;
    @Autowired
    private BasSkuMybatisDao basSkuMybatisDao;

    public String generateSeq(String seqType,String warehouseid){
        Map<String,Object> map = new HashMap<>();
        map.put("warehouseid",warehouseid);
        map.put("no", seqType);
        map.put("resultNo","");
        map.put("resultCode","");
        commonMybatisDao.getIdSequence(map);
        return map.get("resultNo").toString();
    }

    /**
     * 扫描条码适配判断
     * 1，扫描GS1条码，无操作；
     * 2，扫描序列号条码，匹配之前导入的bas_serial_num数据表，自动赋上生产批号（一般到序列号记录的都是批号入，序列号出的） -> otherCode传参；
     * 3，扫描产品代码条码，做完了上验证再检查SKU是否存在 -> otherCode传参；
     * 4，扫描自赋码条码，todo 暂时还没做
     * @param customerid 客户代码
     * @param lotatt04 生产批号
     * @param lotatt05 序列号
     * @param otherCode 序列号 || sku || 自赋码
     * @return ~
     */
    public CommonVO adaptSerialNum(String customerid, String lotatt04, String lotatt05, String otherCode, String GTIN) {

        CommonVO commonVO = new CommonVO();
        //默认值
        commonVO.setBatchNum(lotatt04);
        commonVO.setSerialNum(lotatt05);

        BasSerialNum basSerialNum = null;
        if (StringUtil.isNotEmpty(lotatt05) ||
                StringUtil.isNotEmpty(otherCode)) {

            BasSerialNumQuery serialNumQuery = new BasSerialNumQuery(StringUtil.isNotEmpty(otherCode) ? otherCode : lotatt05);
            basSerialNum = basSerialNumMybatisDao.queryById(serialNumQuery);
            if (basSerialNum != null) {

                //序列号扫码数据缺失 效期、生产批号（注：序列号不需要传，效期不参与查询）
                commonVO.setBatchNum(basSerialNum.getBatchNum());
            }
        }

        //获取SKU
        //获取BasSku
        PdaBasSkuQuery basSkuQuery = new PdaBasSkuQuery();

        if (basSerialNum != null) basSkuQuery.setLotatt05("");
        BasSku basSku = basSkuMybatisDao.queryForScan(basSkuQuery);


        return commonVO;
    }
}