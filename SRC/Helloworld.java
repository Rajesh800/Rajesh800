	public BaseResponse<OrdTickerDetails> getOrderTickerDashboardDtls(String inputJson, String logId) {

		BaseResponse<OrdTickerDetails> baseResponse = new BaseResponse<OrdTickerDetails>();
		Type baseRequestType = new TypeToken<BaseRequest>() {
		}.getType();

		/* convert input json to object */
		BaseRequest baseRequest = (BaseRequest) JSonStringToObjectConverter.JSonStringToObject(inputJson, logId,
				BaseRequest.class, baseRequestType);
		/*
		 * In case any json to object conversion fail it gives error response
		 */
		if (baseRequest == null) {
			baseResponse = MessageHelper.getResponseMessage(CommonConstants.USERLANGUAGE, baseResponse,
					CommonConstants.RET_CODE_OBJECT_CONVERSION_FAIL,
					MessageConstants.INPUT_STRING_CONVERSION_OBJECT_FAIL, logId);
		} else {
			/* Check for action ID */
			if (baseRequest.getActionInfo().getActionId().equals(ServiceConstants.ACTIONID_DASHORDTICKER)) {
				/* call bd class */
				baseResponse = new DashboardBDImpl().getOrderTickerDashboardDtls(baseRequest, baseResponse, logId);
			} else {
				/* Start - OD1-T374 */
//				LOG.info(logId + MessageConstants.INVALID_ACTION_ID);
				LOG.info(new StringBuilder().append(logId).append(MessageConstants.INVALID_ACTION_ID));
				/* End - OD1-T374 */
				baseResponse = MessageHelper.getResponseMessage(
						baseRequest.getSessionData().getUserDetails().getLangCode(), baseResponse,
						MessageConstants.RET_CODE_ERR_ACTIONID, MessageConstants.INVALID_ACTION_ID, logId);
			}
		}
		/* Start - OD1-T374 */
		baseRequestType = null;
		baseRequest = null;
		/* End - OD1-T374 */
		return baseResponse;

	}