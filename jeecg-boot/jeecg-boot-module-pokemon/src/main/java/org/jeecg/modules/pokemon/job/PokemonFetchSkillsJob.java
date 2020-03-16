package org.jeecg.modules.pokemon.job;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.pokemon.entity.PokemonSkill;
import org.jeecg.modules.pokemon.service.IPokemonSkillService;
import org.jeecg.modules.pokemon.util.PokemonSkillsSpider;
import org.jeecg.modules.pokemon.util.PokemonSpider;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class PokemonFetchSkillsJob implements Job {
    @Autowired
    public IPokemonSkillService pokemonSkillService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        PokemonSpider pokemonSpider=new PokemonSkillsSpider();
        pokemonSpider.webClientInit();
        String fetchResult= null;
        try {
            fetchResult = pokemonSpider.getHtmlPage(PokemonSkillsSpider.BASE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pokemonSpider.closeWebClient();
        List<PokemonSkill> pokemonSkills= JSON.parseArray(fetchResult,PokemonSkill.class);
        if (pokemonSkills.size()>0){
            pokemonSkillService.deletAll();
            pokemonSkillService.saveBatch(pokemonSkills);
        }
    }
}
