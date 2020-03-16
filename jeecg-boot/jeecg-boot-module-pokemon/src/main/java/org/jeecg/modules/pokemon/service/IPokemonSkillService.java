package org.jeecg.modules.pokemon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.pokemon.entity.PokemonSkill;

/**
 * @Description: 宝可梦技能
 * @Author: jeecg-boot
 * @Date: 2019-12-01
 * @Version: V1.0
 */
public interface IPokemonSkillService extends IService<PokemonSkill> {

    void deletAll();

}
