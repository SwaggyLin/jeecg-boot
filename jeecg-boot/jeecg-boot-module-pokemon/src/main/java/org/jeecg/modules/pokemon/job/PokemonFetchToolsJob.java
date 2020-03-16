package org.jeecg.modules.pokemon.job;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.pokemon.entity.PokemonTool;
import org.jeecg.modules.pokemon.service.IPokemonToolService;
import org.jeecg.modules.pokemon.util.PokemonSpider;
import org.jeecg.modules.pokemon.util.PokemonToolsSpider;
import org.jeecg.modules.pokemon.util.PokemonSpider;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class PokemonFetchToolsJob implements Job {
    @Autowired
    public IPokemonToolService pokemonToolService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        PokemonSpider pokemonSpider=new PokemonToolsSpider();
        pokemonSpider.webClientInit();
        String fetchResult= null;
        try {
            fetchResult = pokemonSpider.getHtmlPage(PokemonToolsSpider.BASE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pokemonSpider.closeWebClient();
        System.out.println(fetchResult);
        List<PokemonTool> pokemonToolss= JSON.parseArray(fetchResult,PokemonTool.class);
        if (pokemonToolss.size()>0){
            pokemonToolService.saveBatch(pokemonToolss);
        }
    }
}
