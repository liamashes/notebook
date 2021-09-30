    /**
     * 获取最新的任务记录
     */
        ContractTaskEntity contractTask = getLastTaskConf();
                if(contractTask == null){
                return new ResponseVo(new ResponseHeader(ResultEnum.RESULT_CONTRACT_SYNC_INIT_CONF_ERROR));
                }

                if(ContractConstant.TASK_STATUS_WAITING.equals(contractTask.getStatus())
                || ContractConstant.TASK_STATUS_DEALING.equals(contractTask.getStatus())){
                redisKeyLock.releaseDistributedLock(ContractConstant.PRODUCER_LOCK_KEY);
                LOGGER.warn("平台添加合同任务失败-当前有任务正在执行");
                return new ResponseVo(new ResponseHeader(ResultEnum.RESULT_CONTRACT_SYNC_DEALING_ERROR));
                }

                /**
                 * 新加一条任务记录
                 */
                addTask(updateTimed, userAccount);

                redisKeyLock.releaseDistributedLock(ContractConstant.PRODUCER_LOCK_KEY);

                return new ResponseVo(new ResponseHeader(ResultEnum.RESULT_SUCCESS));
