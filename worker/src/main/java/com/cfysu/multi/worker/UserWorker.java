package com.cfysu.multi.worker;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

@JobHander(value = "userWorker")
@Component("userWorker")
public class UserWorker extends IJobHandler{
    private int counter = 0;

    @Override
    public ReturnT<String> execute(String... strings) throws Exception {
        XxlJobLogger.log("XXL-JOB, Hello World.");
        System.out.println("---start---" + counter++);
        return ReturnT.SUCCESS;
    }
}
