package com.wms.dao;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.wms.entity.ViewInvTran;
import com.wms.easyui.EasyuiDatagridPager;
import com.wms.query.ViewInvTranQuery;

@Repository("viewInvTranDao")
public class ViewInvTranDao extends BaseDao<ViewInvTran, String, ViewInvTranQuery> {

	public List<ViewInvTran> getPagedDatagrid(EasyuiDatagridPager pager, ViewInvTranQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setFirstResult((pager.getPage() - 1) * pager.getRows());
		criteria.setMaxResults(pager.getRows());
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Long countAll(ViewInvTranQuery query) {
		Criteria criteria = this.getQueryCriteria(query);
		criteria.setProjection(Projections.count("id"));
		return (Long)criteria.uniqueResult();
	}

	protected Criteria getQueryCriteria(ViewInvTranQuery query){
		StringBuilder sb = new StringBuilder();
		Criteria criteria = this.createCriteria();
		if(StringUtils.isNotEmpty(query.getAddtime())){
			criteria.add(Restrictions.like("addtime", query.getAddtime(), MatchMode.ANYWHERE));
		}
		if(query.getDoclineno() != null){
			criteria.add(Restrictions.eq("doclineno", query.getDoclineno()));
		}
		if(StringUtils.isNotEmpty(query.getDocno())){
			criteria.add(Restrictions.like("docno", query.getDocno(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDoctype())){
			criteria.add(Restrictions.like("doctype", query.getDoctype(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getDoctypeName())){
			criteria.add(Restrictions.like("doctypeName", query.getDoctypeName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getEditwho())){
			criteria.add(Restrictions.like("editwho", query.getEditwho(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmcustomerid())){
			criteria.add(Restrictions.like("fmcustomerid", query.getFmcustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmid())){
			criteria.add(Restrictions.like("fmid", query.getFmid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmlocation())){
			criteria.add(Restrictions.like("fmlocation", query.getFmlocation(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmlotatt01())){
			criteria.add(Restrictions.like("fmlotatt01", query.getFmlotatt01(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmlotatt02())){
			criteria.add(Restrictions.like("fmlotatt02", query.getFmlotatt02(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmlotatt03())){
			criteria.add(Restrictions.like("fmlotatt03", query.getFmlotatt03(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmlotatt04())){
			criteria.add(Restrictions.like("fmlotatt04", query.getFmlotatt04(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmlotatt05())){
			criteria.add(Restrictions.like("fmlotatt05", query.getFmlotatt05(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmlotatt06())){
			criteria.add(Restrictions.like("fmlotatt06", query.getFmlotatt06(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmlotnum())){
			criteria.add(Restrictions.like("fmlotnum", query.getFmlotnum(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmpackid())){
			criteria.add(Restrictions.like("fmpackid", query.getFmpackid(), MatchMode.ANYWHERE));
		}
		if(query.getFmqty() != null){
			criteria.add(Restrictions.eq("fmqty", query.getFmqty()));
		}
		if(query.getFmqtyEach() != null){
			criteria.add(Restrictions.eq("fmqtyEach", query.getFmqtyEach()));
		}
		if(StringUtils.isNotEmpty(query.getFmsku())){
			criteria.add(Restrictions.like("fmsku", query.getFmsku(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmuom())){
			criteria.add(Restrictions.like("fmuom", query.getFmuom(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getFmuomName())){
			criteria.add(Restrictions.like("fmuomName", query.getFmuomName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getOperator())){
			criteria.add(Restrictions.like("operator", query.getOperator(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getReason())){
			criteria.add(Restrictions.like("reason", query.getReason(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getReasoncode())){
			criteria.add(Restrictions.like("reasoncode", query.getReasoncode(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getStatus())){
			criteria.add(Restrictions.like("status", query.getStatus(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getStatusName())){
			criteria.add(Restrictions.like("statusName", query.getStatusName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTocustomerid())){
			criteria.add(Restrictions.like("tocustomerid", query.getTocustomerid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getToid())){
			criteria.add(Restrictions.like("toid", query.getToid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTolocation())){
			criteria.add(Restrictions.like("tolocation", query.getTolocation(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTolotatt01())){
			criteria.add(Restrictions.like("tolotatt01", query.getTolotatt01(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTolotatt02())){
			criteria.add(Restrictions.like("tolotatt02", query.getTolotatt02(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTolotatt03())){
			criteria.add(Restrictions.like("tolotatt03", query.getTolotatt03(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTolotatt04())){
			criteria.add(Restrictions.like("tolotatt04", query.getTolotatt04(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTolotatt05())){
			criteria.add(Restrictions.like("tolotatt05", query.getTolotatt05(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTolotatt06())){
			criteria.add(Restrictions.like("tolotatt06", query.getTolotatt06(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTolotnum())){
			criteria.add(Restrictions.like("tolotnum", query.getTolotnum(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTopackid())){
			criteria.add(Restrictions.like("topackid", query.getTopackid(), MatchMode.ANYWHERE));
		}
		if(query.getToqty() != null){
			criteria.add(Restrictions.eq("toqty", query.getToqty()));
		}
		if(query.getToqtyEach() != null){
			criteria.add(Restrictions.eq("toqtyEach", query.getToqtyEach()));
		}
		if(StringUtils.isNotEmpty(query.getTosku())){
			criteria.add(Restrictions.like("tosku", query.getTosku(), MatchMode.ANYWHERE));
		}
		if(query.getTotalcubic() != null){
			criteria.add(Restrictions.eq("totalcubic", query.getTotalcubic()));
		}
		if(query.getTotalgrossweight() != null){
			criteria.add(Restrictions.eq("totalgrossweight", query.getTotalgrossweight()));
		}
		if(query.getTotalnetweight() != null){
			criteria.add(Restrictions.eq("totalnetweight", query.getTotalnetweight()));
		}
		if(query.getTotalprice() != null){
			criteria.add(Restrictions.eq("totalprice", query.getTotalprice()));
		}
		if(StringUtils.isNotEmpty(query.getTouom())){
			criteria.add(Restrictions.like("touom", query.getTouom(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTouomName())){
			criteria.add(Restrictions.like("touomName", query.getTouomName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTransactionid())){
			criteria.add(Restrictions.like("transactionid", query.getTransactionid(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTransactiontime())){
			criteria.add(Restrictions.like("transactiontime", query.getTransactiontime(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTransactiontype())){
			criteria.add(Restrictions.like("transactiontype", query.getTransactiontype(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getTransactiontypeName())){
			criteria.add(Restrictions.like("transactiontypeName", query.getTransactiontypeName(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine1())){
			criteria.add(Restrictions.like("userdefine1", query.getUserdefine1(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine2())){
			criteria.add(Restrictions.like("userdefine2", query.getUserdefine2(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine3())){
			criteria.add(Restrictions.like("userdefine3", query.getUserdefine3(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine4())){
			criteria.add(Restrictions.like("userdefine4", query.getUserdefine4(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(query.getUserdefine5())){
			criteria.add(Restrictions.like("userdefine5", query.getUserdefine5(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
}