package com.wms.entity;

import lombok.Data;

import javax.persistence.Entity;
@Data
@Entity
public class InvLotLocIdSkuInvLotAtt extends InvLotLocId{


	private  BasSku basSku;
	private  InvLotAtt invLotAtt;

}
