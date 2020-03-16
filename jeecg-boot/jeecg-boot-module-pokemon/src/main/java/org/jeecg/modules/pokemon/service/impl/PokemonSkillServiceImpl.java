package org.jeecg.modules.pokemon.service.impl;

import org.jeecg.modules.pokemon.entity.PokemonSkill;
import org.jeecg.modules.pokemon.entity.PokemonSkill;
import org.jeecg.modules.pokemon.mapper.PokemonSkillMapper;
import org.jeecg.modules.pokemon.mapper.PokemonSkillMapper;
import org.jeecg.modules.pokemon.service.IPokemonSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 宝可梦技能
 * @Author: jeecg-boot
 * @Date:   2019-12-01
 * @Version: V1.0
 */
@Service
public class PokemonSkillServiceImpl extends ServiceImpl<PokemonSkillMapper, PokemonSkill> implements IPokemonSkillService {
    @Autowired
    public PokemonSkillMapper pokemonSkillMapper;

    @Override
    public void deletAll() {
        pokemonSkillMapper.deleteAll();
    }
}
