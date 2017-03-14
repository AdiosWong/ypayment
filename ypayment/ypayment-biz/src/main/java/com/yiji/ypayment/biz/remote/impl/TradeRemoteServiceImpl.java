/*
 * www.yiji.com Inc.
 * Copyright (c) 2015 All Rights Reserved.
 */

/*
 * 修订记录：
 * @author faZheng 下午4:01:10 创建
 */
package com.yiji.ypayment.biz.remote.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yiji.ypayment.biz.enums.PaymentInstructionAction;
import com.yiji.ypayment.biz.enums.TransferTradeMode;
import com.yiji.ypayment.biz.exception.PaymentException;
import com.yiji.ypayment.biz.remote.TradeRemoteService;
import com.yiji.ypayment.biz.remote.base.RemoteServiceBase;
import com.yiji.ypayment.biz.remote.info.OrderInfo;
import com.yiji.ypayment.common.constant.Constant;
import com.yiji.ypayment.dal.entity.business.PaymentOrder;
import com.yiji.ypayment.dal.entity.business.PaymentTrade;
import com.yiji.ypayment.dal.entity.business.PlatformType;
import com.yiji.ypayment.dal.entity.business.UndoPayment;
import com.yiji.ypayment.dal.enums.FavourableEnum;
import com.yiji.ypayment.dal.enums.TransferTradeStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentItemStatusEnum;
import com.yiji.ypayment.facade.enums.PaymentResultCode;
import com.yiji.ypayment.facade.enums.UndoPaymentStatusEnum;
import com.yjf.accounttrans.service.enums.FreezeBalanceTypeEnum;
import com.yjf.accounttrans.service.enums.TransResultEnum;
import com.yjf.accounttrans.service.order.freeze.FreezeOrder;
import com.yjf.accounttrans.service.result.AccountTransResult;
import com.yjf.common.lang.result.Status;
import com.yjf.common.lang.util.money.Money;
import com.yjf.trade.product.transfer.order.BatchTransferOrder;
import com.yjf.trade.product.transfer.order.BatchTransferOrder.SubEntity;
import com.yjf.trade.product.transfer.order.SingleTransferOrder;
import com.yjf.trade.service.enums.GatheringTypeEnum;
import com.yjf.trade.service.enums.TradeTypeEnum;
import com.yjf.trade.service.enums.TransferActionEnum;
import com.yjf.trade.service.order.UserOrder;
import com.yjf.trade.service.result.TradeResult;

/**
 * 缴费值金额处理
 *
 * @author faZheng
 *
 */
@Service("tradeRemoteService")
public class TradeRemoteServiceImpl extends RemoteServiceBase implements TradeRemoteService {
	
	/**
	 * 解冻转账
	 * 
	 * @param paymentTrade
	 * @param tradeMode
	 * @return
	 * @see com.yiji.ypayment.biz.remote.TradeRemoteService#transferTrade(com.yiji.ypayment.dal.entity.business.PaymentTrade,
	 * com.yiji.ypayment.biz.enums.TransferTradeMode)
	 */
	@Override
	public Status transferTrade(PaymentTrade paymentTrade, TransferTradeMode tradeMode) {
		try {
			SingleTransferOrder order = new SingleTransferOrder();
			order.setInlet(paymentTrade.getInlet());
			order.setSystemId(Constant.SYSTEMID);
			order.setOrderNo(paymentTrade.getBizOrderNo());
			order.setMerchantOrderBizNo(paymentTrade.getMerchOrderNo());
			order.setProductCode(Constant.BIZCODE);
			order.setTradeName("缴费充值");
			order.setTradeMemo("缴费充值");
			//统一订单号新增
			order.setGid(paymentTrade.getGid());
			order.setPartnerId(paymentTrade.getPartnerId());
			order.setMerchOrderNo(paymentTrade.getMerchOrderNo());
			
			// 平台商
			PlatformType platformType = platformTypeService.findByPlatformType(paymentTrade.getPayFrom());
			if (platformType == null) {
				logger.error("查询平台交易收费码失败， 平台来源：{}", paymentTrade.getPayFrom());
				throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "查询平台交易收费码失败");
			}
			UserOrder merchant = new UserOrder();
			merchant.setUserId(platformType.getPartnerId());
			merchant.setCardNo(platformType.getPartnerId());
			merchant.setAccountNo(platformType.getPartnerId());
			order.setTradeType(TradeTypeEnum.TRANSFER);
			order.setGatheringType(GatheringTypeEnum.GOODS_BUY);
			order.setMerchant(merchant);
			
			// 付款方
			UserOrder payer = new UserOrder();
			payer.setUserId(paymentTrade.getPayerUserId());
			payer.setCardNo(paymentTrade.getPayerCardNo());
			payer.setAccountNo(paymentTrade.getPayerAcctNo());
			order.setPayer(payer);
			
			// 收款方
			UserOrder payee = new UserOrder();
			payee.setUserId(paymentTrade.getPayeeUserId());
			payee.setCardNo(paymentTrade.getPayeeCardNo());
			payee.setAccountNo(paymentTrade.getPayeeAcctNo());
			order.setPayee(payee);
			order.setTradeAmount(paymentTrade.getAmount());
			
			FreezeBalanceTypeEnum FreezeType = FreezeBalanceTypeEnum.TRANSFER_FREEZE;
			if (tradeMode == TransferTradeMode.TRANSFER_UNFREEZE) {
				order.setTransferAction(TransferActionEnum.UN_FREEZE_TRANSFER);
				order.setUnFreezeType(FreezeType);
				order.setUnfreezeAmount(paymentTrade.getAmount());
			} else if (tradeMode == TransferTradeMode.TRANSFER) {
				order.setTransferAction(TransferActionEnum.NORMAL);
				order.setUnfreezeAmount(new Money(0));
				order.setFreezeAmount(new Money(0));
			}
			
			//更新转账订单状态为处理中
			paymentTrade.setTradeStatus(TransferTradeStatusEnum.PROCESSING);
			paymentTradeService.update(paymentTrade);
			
			//转账
			TradeResult result = tradeClient.transferWarp(order);
			if (result == null) {
				throw new PaymentException(PaymentResultCode.UNKNOWN_STATUS);
			}
			
			// 交易转账
			switch (result.getStatus()) {
				case SUCCESS:
					paymentTrade.setTradeStatus(TransferTradeStatusEnum.SUCCESS);
					logger.info("交易成功，message：{}", result.getDescription());
					break;
				case FAIL:
					paymentTrade.setTradeStatus(TransferTradeStatusEnum.FAIL);
					logger.info("交易失败，message：{}", result.getDescription());
					break;
				case PROCESSING:
					paymentTrade.setTradeStatus(TransferTradeStatusEnum.PROCESSING);
					logger.info("交易处理中，message：{}", result.getDescription());
					break;
				default:
					throw new PaymentException(PaymentResultCode.UNKNOWN_STATUS, "未知的转账结果状态");
			}
			paymentTradeService.update(paymentTrade);
			return result.getStatus();
		} catch (Exception e) {
			logger.error("解冻转账发生系统异常！");
			logger.error(e.getMessage(), e);
		}
		//更新转账表为失败
		paymentTrade.setTradeStatus(TransferTradeStatusEnum.FAIL);
		paymentTradeService.update(paymentTrade);
		return Status.FAIL;
	}
	
	/**
	 * @param paymentTrades
	 * @param tradeMode
	 * @return
	 * @see com.yiji.ypayment.biz.remote.TradeRemoteService#batchTransfer(java.util.List, com.yiji.ypayment.biz.enums.TransferTradeMode)
	 */
	@Override
	public Status batchTransfer(List<PaymentTrade> paymentTrades, TransferTradeMode tradeMode) {
		try {
			BatchTransferOrder order = new BatchTransferOrder();
			PaymentTrade tradeDemo = paymentTrades.get(0);
			order.setInlet(tradeDemo.getInlet());
			order.setSystemId(Constant.SYSTEMID);
			order.setMerchantOrderBizNo(tradeDemo.getMerchOrderNo());
			order.setProductCode(Constant.BIZCODE);
			order.setTradeMemo("缴费充值");
			//统一订单号新增
			order.setGid(tradeDemo.getGid());
			order.setPartnerId(tradeDemo.getPartnerId());
			order.setMerchOrderNo(tradeDemo.getMerchOrderNo());
			
			List<SubEntity> subEntities = Lists.newArrayList();
			for (PaymentTrade paymentTrade : paymentTrades) {
				SubEntity subEntity = new SubEntity();
				subEntity.setOrderNo(paymentTrade.getBizOrderNo());
				subEntity.setTradeName("缴费充值");
				subEntity.setMemo("缴费充值"); 
				//统一订单号新增
				subEntity.setGid(paymentTrade.getGid());
				subEntity.setPartnerId(paymentTrade.getPartnerId());
				subEntity.setMerchOrderNo(paymentTrade.getMerchOrderNo());
				// 平台商
				PlatformType platformType = platformTypeService.findByPlatformType(paymentTrade.getPayFrom());
				if (platformType == null) {
					logger.error("查询平台交易收费码失败， 平台来源：{}", paymentTrade.getPayFrom());
					throw new PaymentException(PaymentResultCode.EXECUTE_FAIL, "查询平台交易收费码失败");
				}
				UserOrder merchant = new UserOrder();
				merchant.setUserId(platformType.getPartnerId());
				merchant.setCardNo(platformType.getPartnerId());
				merchant.setAccountNo(platformType.getPartnerId());
				
				subEntity.setMerchant(merchant);
				
				// 付款方
				UserOrder payer = new UserOrder();
				payer.setUserId(paymentTrade.getPayerUserId());
				payer.setCardNo(paymentTrade.getPayerCardNo());
				payer.setAccountNo(paymentTrade.getPayerAcctNo());
				subEntity.setPayer(payer);
				
				// 收款方
				UserOrder payee = new UserOrder();
				payee.setUserId(paymentTrade.getPayeeUserId());
				payee.setCardNo(paymentTrade.getPayeeCardNo());
				payee.setAccountNo(paymentTrade.getPayeeAcctNo());
				subEntity.setPayee(payee);
				
				subEntity.setTradeAmount(paymentTrade.getAmount());
				subEntity.setGatheringType(GatheringTypeEnum.GOODS_BUY);
				if (tradeMode == TransferTradeMode.TRANSFER_UNFREEZE) {
					subEntity.setUnFreezeType(FreezeBalanceTypeEnum.TRANSFER_FREEZE);
					subEntity.setUnfreezeAmount(paymentTrade.getAmount());
				} else if (tradeMode == TransferTradeMode.TRANSFER) {
					subEntity.setUnfreezeAmount(new Money(0));
					subEntity.setFreezeAmount(new Money(0));
				}
				subEntities.add(subEntity);
			}
			order.setSubEntities(subEntities);
			//转账
			TradeResult result = tradeClient.batchTransfer(order);
			if (result == null) {
				throw new PaymentException(PaymentResultCode.UNKNOWN_STATUS);
			}
			
			TransferTradeStatusEnum tradeStatus = null;
			// 交易转账
			switch (result.getStatus()) {
				case SUCCESS:
					updatePaymentTradeDB(paymentTrades, TransferTradeStatusEnum.SUCCESS);
					tradeStatus = TransferTradeStatusEnum.SUCCESS;
					logger.info("交易成功，message：{}", result.getDescription());
					break;
				case FAIL:
					updatePaymentTradeDB(paymentTrades, TransferTradeStatusEnum.FAIL);
					tradeStatus = TransferTradeStatusEnum.FAIL;
					logger.info("交易失败，message：{}", result.getDescription());
					break;
				case PROCESSING:
					updatePaymentTradeDB(paymentTrades, TransferTradeStatusEnum.PROCESSING);
					tradeStatus = TransferTradeStatusEnum.PROCESSING;
					logger.info("交易处理中，message：{}", result.getDescription());
					break;
				default:
					throw new PaymentException(PaymentResultCode.UNKNOWN_STATUS, "未知的转账结果状态");
			}
			updatePaymentTradeDB(paymentTrades, tradeStatus);
			return result.getStatus();
		} catch (Exception e) {
			logger.error("解冻转账发生系统异常！");
			logger.error(e.getMessage(), e);
		}
		//更新转账表为失败
		updatePaymentTradeDB(paymentTrades, TransferTradeStatusEnum.FAIL);
		return Status.FAIL;
	}
	
	private void updatePaymentTradeDB(List<PaymentTrade> paymentTrades, TransferTradeStatusEnum transferTradeStatus){
		for(PaymentTrade paymentTrade : paymentTrades){
			paymentTrade.setTradeStatus(transferTradeStatus);
			paymentTradeService.update(paymentTrade);
		}
	}
	
	@Override
	public boolean freeze(OrderInfo orderInfo, String userId, Money amount) {
		boolean isSuccess = false;
		try {
			FreezeOrder order = new FreezeOrder();
			order.setAccountNo(userId);
			order.setFreezeAmount(amount);
			FreezeBalanceTypeEnum freeType = FreezeBalanceTypeEnum.TRANSFER_FREEZE;
			order.setFreezeType(freeType);
			order.setOrderNo(commonService.generateBizNo(PaymentInstructionAction.MONEY_FREEZE));
			order.setMemo("缴费充值冻结");
			//统一订单号修改
			order.setGid(orderInfo.getGid());
			order.setPartnerId(orderInfo.getPartnerId());
			order.setMerchOrderNo(orderInfo.getMerchOrderNo());
			AccountTransResult result = accountClient.freeze(order);
			if (result != null && TransResultEnum.EXECUTE_SUCCESS == result.getResultCode() && result.isSuccess()) {
				isSuccess = true;
			}
		} catch (Exception e) {
			logger.error("资金冻结发生系统异常！");
			logger.error(e.getMessage(), e);
		}
		return isSuccess;
	}
	
	@Override
	public boolean unfreeze(OrderInfo orderInfo, String userId, Money amount) {
		boolean isSuccess = false;
		try {
			FreezeOrder order = new FreezeOrder();
			order.setAccountNo(userId);
			order.setFreezeAmount(amount);
			order.setFreezeType(FreezeBalanceTypeEnum.TRANSFER_FREEZE);
			order.setOrderNo(commonService.generateBizNo(PaymentInstructionAction.MONEY_UNFREEZE));
			order.setMemo("缴费充值解冻");
			//统一订单号修改
			order.setGid(orderInfo.getGid());
			order.setPartnerId(orderInfo.getPartnerId());
			order.setMerchOrderNo(orderInfo.getMerchOrderNo());
			AccountTransResult result = accountClient.unfreeze(order);
			if (result != null && TransResultEnum.EXECUTE_SUCCESS == result.getResultCode() && result.isSuccess()) {
				isSuccess = true;
			}
		} catch (Exception e) {
			logger.error("资金冻结发生系统异常！");
			logger.error(e.getMessage(), e);
		}
		return isSuccess;
	}

	/**
	 * @param paymentOrder
	 * @see com.yiji.ypayment.biz.remote.TradeRemoteService#unfreezeAccount(com.yiji.ypayment.dal.entity.business.PaymentOrder)
	 */
	@Override
	public void unfreezeAccount(PaymentOrder paymentOrder) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setGid(paymentOrder.getGid());
		orderInfo.setMerchOrderNo(paymentOrder.getMerchOrderNo());
		orderInfo.setPartnerId(paymentOrder.getPartnerId());
		//如果是充值且商户有优惠
		if(FavourableEnum.TRUE == paymentOrder.getFavourable()){
			boolean partnerUnfreeze = unfreeze(orderInfo, paymentOrder.getPartnerId(), paymentOrder.getPayable());
			if (!partnerUnfreeze) {
				logger.info("商户解冻失败, 订单号:{}", paymentOrder.getPaymentOrderNo());
			}
			boolean userUnfreeze = unfreeze(orderInfo, paymentOrder.getUserId(), paymentOrder.getPaymentAmount());
			if (!userUnfreeze) {
				logger.info("用户解冻失败, 订单号:{}", paymentOrder.getPaymentOrderNo());
			}
		}else{
			boolean unfreeze = unfreeze(orderInfo, paymentOrder.getUserId(), paymentOrder.getPaymentAmount());
			if (!unfreeze) {
				logger.info("缴费解冻失败, 订单号:{}", paymentOrder.getPaymentOrderNo());
			}
		}
	}

	/**
	 * @param paymentOrder
	 * @see com.yiji.ypayment.biz.remote.TradeRemoteService#transferTrade(com.yiji.ypayment.dal.entity.business.PaymentOrder)
	 */
	@Override
	public void transferTrade(PaymentOrder paymentOrder) {
		if (PaymentItemStatusEnum.SUCCESS == paymentOrder.getPaymentStatus()) {
			if (TransferTradeStatusEnum.INIT == paymentOrder.getTradeStatus()
				|| TransferTradeStatusEnum.FAIL == paymentOrder.getTradeStatus()) {
				//如果是充值且商户有优惠
				if (FavourableEnum.TRUE == paymentOrder.getFavourable()) {
					//商家转账给渠道
					PaymentTrade partnerPaymentTrade = buildTransferTradeOrder(paymentOrder.getPartnerId(),
						paymentOrder.getPayAccount(), paymentOrder.getPayable(), paymentOrder);
					
					//用户转账给商家
					PaymentTrade userPaymentTrade = buildTransferTradeOrder(paymentOrder.getUserId(),
						paymentOrder.getPartnerId(), paymentOrder.getPaymentAmount(), paymentOrder);
					
					//转账
					List<PaymentTrade> paymentTrades = Lists.newArrayList();
					paymentTrades.add(userPaymentTrade);
					paymentTrades.add(partnerPaymentTrade);
					Status status = batchTransfer(paymentTrades, TransferTradeMode.TRANSFER_UNFREEZE);
					
					//更新订单转账状态
					paymentOrder.setTradeStatus(convertTradeStatus(status));
					paymentOrderService.update(paymentOrder);
				} else {
					PaymentTrade paymentTrade = buildTransferTradeOrder(paymentOrder.getUserId(),
						paymentOrder.getPayAccount(), paymentOrder.getPaymentAmount(), paymentOrder);
					
					//转账
					Status status = transferTrade(paymentTrade, TransferTradeMode.TRANSFER_UNFREEZE);
					
					//更新订单转账状态
					paymentOrder.setTradeStatus(convertTradeStatus(status));
					paymentOrderService.update(paymentOrder);
				}
			}
		}
	}
	
	/**
	 * 构造普通转账订单
	 * @param fromId
	 * @param tragetId
	 * @param payAmount
	 * @param paymentOrder
	 * @return
	 */
	public PaymentTrade buildTransferTradeOrder(String fromId, String tragetId, Money payAmount, PaymentOrder paymentOrder) {
		PaymentTrade paymentTrade = new PaymentTrade();
		paymentTrade.setBizOrderNo(commonService.generateBizNo(PaymentInstructionAction.MONEY_TRANSFER));
		paymentTrade.setPayerUserId(fromId);
		paymentTrade.setPayerCardNo(fromId);
		paymentTrade.setPayerAcctNo(fromId);
		paymentTrade.setAmount(payAmount);
		paymentTrade.setPayeeUserId(tragetId);
		paymentTrade.setPayeeCardNo(tragetId);
		paymentTrade.setPayeeAcctNo(tragetId);
		paymentTrade.setRefBizOrderNo(paymentOrder.getPaymentOrderNo());
		paymentTrade.setPaymentType(paymentOrder.getPaymentType());
		paymentTrade.setTradeStatus(TransferTradeStatusEnum.INIT);
		paymentTrade.setTradeType(com.yiji.ypayment.dal.enums.TradeTypeEnum.DO_PAYMENT);
		paymentTrade.setPayFrom(paymentOrder.getPayFrom());
		paymentTrade.setInlet(paymentOrder.getInlet());
		//全站统一订单号增加
		paymentTrade.setGid(paymentOrder.getGid());
		paymentTrade.setPartnerId(paymentOrder.getPartnerId());
		paymentTrade.setMerchOrderNo(paymentOrder.getMerchOrderNo());
		paymentTradeService.save(paymentTrade);
		return paymentTrade;
	}
	
	private TransferTradeStatusEnum convertTradeStatus(Status status) {
		if (Status.SUCCESS == status) {
			return TransferTradeStatusEnum.SUCCESS;
		} else if (Status.FAIL == status) {
			return TransferTradeStatusEnum.FAIL;
		} else {
			return TransferTradeStatusEnum.PROCESSING;
		}
	}

	/**
	 * @param undoPayment
	 * @see com.yiji.ypayment.biz.remote.TradeRemoteService#transferTrade(com.yiji.ypayment.dal.entity.business.UndoPayment)
	 */
	@Override
	public void transferTrade(UndoPayment undoPayment) {
		if (UndoPaymentStatusEnum.SUCCESS == undoPayment.getUndoStatus()) {
			if (TransferTradeStatusEnum.INIT == undoPayment.getTradeStatus()
				|| TransferTradeStatusEnum.FAIL == undoPayment.getTradeStatus()) {
				//构造返销转账订单
				PaymentTrade paymentTrade = buildUndoTransferTradeOrder(undoPayment);
				//保存订单
				paymentTradeService.save(paymentTrade);
				Status status = transferTrade(paymentTrade, TransferTradeMode.TRANSFER);
				undoPayment.setTradeStatus(convertTradeStatus(status));
				undoPaymentService.save(undoPayment);
			}
		}
	}

	/**
	 * 构造返销转账订单
	 * @param undoPayment
	 * @return
	 */
	private PaymentTrade buildUndoTransferTradeOrder(UndoPayment undoPayment){
		PaymentTrade paymentTrade = new PaymentTrade();
		paymentTrade.setBizOrderNo(commonService.generateBizNo(PaymentInstructionAction.MONEY_TRANSFER));
		paymentTrade.setPayerUserId(undoPayment.getPayAccount());
		paymentTrade.setPayerCardNo(undoPayment.getPayAccount());
		paymentTrade.setPayerAcctNo(undoPayment.getPayAccount());
		paymentTrade.setAmount(undoPayment.getUndoAmount());
		paymentTrade.setPayeeUserId(undoPayment.getUserId());
		paymentTrade.setPayeeCardNo(undoPayment.getUserId());
		paymentTrade.setPayeeAcctNo(undoPayment.getUserId());
		paymentTrade.setRefBizOrderNo(undoPayment.getUndoPaymentNo());
		paymentTrade.setPaymentType(undoPayment.getPaymentType());
		paymentTrade.setTradeStatus(TransferTradeStatusEnum.INIT);
		paymentTrade.setTradeType(com.yiji.ypayment.dal.enums.TradeTypeEnum.UNDO_PAYMENT);
		paymentTrade.setPayFrom(undoPayment.getPayFrom());
		paymentTrade.setInlet(undoPayment.getInlet());
		//全站统一订单号增加
		paymentTrade.setGid(undoPayment.getGid());
		paymentTrade.setPartnerId(undoPayment.getPartnerId());
		paymentTrade.setMerchOrderNo(undoPayment.getMerchOrderNo());
		return paymentTrade;
	}

	/**
	 * @param paymentOrderNo
	 * @see com.yiji.ypayment.biz.remote.TradeRemoteService#transferTradePayment(java.lang.String)
	 */
	@Override
	@Transactional
	public void transferTradePayment(String paymentOrderNo) {
		PaymentOrder paymentOrder = paymentOrderService.lockByPaymentOrderNo(paymentOrderNo);
		transferTrade(paymentOrder);
	}

	/**
	 * @param undoPaymentNo
	 * @see com.yiji.ypayment.biz.remote.TradeRemoteService#transferTradeUndoPayment(java.lang.String)
	 */
	@Override
	@Transactional
	public void transferTradeUndoPayment(String undoPaymentNo) {
		UndoPayment undoPayment = undoPaymentService.lockByUndoPaymentNo(undoPaymentNo);
		transferTrade(undoPayment);
	}

}
