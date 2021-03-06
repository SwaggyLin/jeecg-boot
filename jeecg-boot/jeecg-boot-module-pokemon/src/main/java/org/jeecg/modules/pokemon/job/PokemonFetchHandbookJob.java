package org.jeecg.modules.pokemon.job;

import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.pokemon.service.IPokemonHandbookService;
import org.jeecg.modules.pokemon.util.PokemonUtil;
import org.jeecg.modules.pokemon.entity.PokemonHandbook;
import org.jeecg.modules.pokemon.service.IPokemonHandbookService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 获取宝可梦图鉴定时任务
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class PokemonFetchHandbookJob  implements Job {
    @Autowired
    private IPokemonHandbookService pokemonHandbookService;

    @Override
    public synchronized void execute(JobExecutionContext jobExecutionContext) {
        List<PokemonHandbook> pokemonHandbookList= PokemonUtil.fetchPokemonHandBook();
        if (pokemonHandbookList==null){
            log.info("抓取失败");
        }else {
            //保存到主数据库（不得超过1000）
            pokemonHandbookService.saveBatch(pokemonHandbookList);
        }
    }
}
