package org.jeecg.modules.pokemon.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Description
 * @Author linminfeng
 * @Date 2019-12-10 14:58
 */
@Slf4j
public class PokemonJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("进来了111");
    }
}
