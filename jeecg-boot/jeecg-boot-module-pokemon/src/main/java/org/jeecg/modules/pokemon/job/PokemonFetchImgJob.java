package org.jeecg.modules.pokemon.job;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.pokemon.util.PokemonUtil;
import org.jeecg.modules.pokemon.entity.PokemonHandbook;
import org.quartz.*;


import java.util.List;

/**
 * 获取宝可梦图鉴定时任务
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class PokemonFetchImgJob implements Job {
    @Override
    public synchronized void execute(JobExecutionContext jobExecutionContext) {
        List<PokemonHandbook> pokemonHandbookList= PokemonUtil.fetchPokemonHandBook();
        if (pokemonHandbookList==null){
            log.info("抓取失败");
        }else {
            PokemonUtil.batchSavePokemonImg(pokemonHandbookList);
            log.info("图片抓取完毕！");
        }
    }
}
