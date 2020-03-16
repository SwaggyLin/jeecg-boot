package org.jeecg.modules.pokemon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.pokemon.entity.PokemonSkill;

/**
 * @Description: 宝可梦技能
 * @Author: jeecg-boot
 * @Date:   2019-12-01
 * @Version: V1.0
 */
public interface PokemonSkillMapper extends BaseMapper<PokemonSkill> {

    @Update("truncate table pokemon_skill")
    void deleteAll();

}
