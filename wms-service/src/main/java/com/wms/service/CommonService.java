package com.wms.service;

import com.wms.entity.BasSerialNum;
import com.wms.entity.BasSku;
import com.wms.entity.ProductLine;
import com.wms.mybatis.dao.BasSerialNumMybatisDao;
import com.wms.mybatis.dao.BasSkuMybatisDao;
import com.wms.mybatis.dao.CommonMybatisDao;
import com.wms.mybatis.dao.ProductLineMybatisDao;
import com.wms.query.BasSerialNumQuery;
import com.wms.query.BasSkuQuery;
import com.wms.query.ProductLineQuery;
import com.wms.query.pda.PdaBasSkuQuery;
import com.wms.utils.StringUtil;
import com.wms.vo.form.pda.ScanResultForm;
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
    @Autowired
    private ProductLineMybatisDao productLineMybatisDao;

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
     * @param form 详见form内的字段描述
     * @return ~
     */
    public CommonVO adaptScanResult4SKU(ScanResultForm form) {

        CommonVO commonVO = new CommonVO();
        //默认值
        commonVO.setBatchNum(form.getLotatt04());
        commonVO.setSerialNum(form.getLotatt05());
        commonVO.setSerialManagement(false);

        BasSerialNum basSerialNum = null;
        if (StringUtil.isNotEmpty(form.getLotatt05()) ||
                StringUtil.isNotEmpty(form.getOtherCode())) {

            BasSerialNumQuery serialNumQuery = new BasSerialNumQuery(StringUtil.isNotEmpty(form.getOtherCode()) ? form.getOtherCode() : form.getLotatt05());
            basSerialNum = basSerialNumMybatisDao.queryById(serialNumQuery);
            if (basSerialNum != null) {

                //序列号扫码数据缺失 效期、生产批号（注：序列号不需要传，效期不参与查询）
                //如果扫了序列号管理中的序列号，自动匹配出批号进行SKU查询
                form.setLotatt04(basSerialNum.getBatchNum());
                commonVO.setBatchNum(basSerialNum.getBatchNum());//返回批号，为之后的业务逻辑
            }
        }

        //获取BasSku
        PdaBasSkuQuery pdaBasSkuQuery = new PdaBasSkuQuery(
                form.getGTIN(),
                form.getCustomerid(),
                StringUtil.fixNull(form.getOtherCode()).contains(ScanResultForm.ALTERNATE_SKU_ID) ? form.getOtherCode() : "",//如果包含自赋码的规则字符串，则赋上自赋码
                form.getLotatt04(),
                basSerialNum != null ? "" : form.getLotatt05());//如果是序列号存在于序列号导入模块，则说明产品是批号入，出库记录序列号的.
        BasSku basSku = basSkuMybatisDao.queryForScan(pdaBasSkuQuery);

        //如果获取不到，可能属于适配情况3（基本上）
        if (basSku == null) {

            BasSkuQuery basSkuQuery = new BasSkuQuery(form.getCustomerid(), form.getOtherCode());
            basSku = basSkuMybatisDao.queryById(basSkuQuery);
        }

        //如果还没有，就说明扫码错误
        if (basSku == null) {

            commonVO.setSuccess(false);
            commonVO.setMessage("未能获取到对应的产品档案！");
            return commonVO;
        }

        commonVO.setBasSku(basSku);

        //产品线(BasSku.skuGroup1) - 序列号管理flag
        ProductLineQuery productLineQuery = new ProductLineQuery(basSku.getSkuGroup1());
        ProductLine productLine = productLineMybatisDao.queryById(productLineQuery);
        if (productLine != null && productLine.getSerialFlag() == 1) {

            commonVO.setSerialManagement(true);
            commonVO.setSerialNum("");
        }

        commonVO.setSuccess(true);

        return commonVO;
    }

//    public boolean ()
}